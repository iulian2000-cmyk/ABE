package TestEncryption;


import SetupStage.SystemSetup;
import SetupStage.TrustAuthority;
import SpecialDataStructures.AccessTree;
import SpecialDataStructures.NodeAccessTree;
import Users.Individual;
import Users.JuridicPerson;
import Users.Position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class TestEncryption {
    public static void main(String[] args) {
        System.out.print(
                """
                                d88888888b    d8888.      88888888db         ***      ******     **********       \s
                                88'     88    YP  8P8     8Y                           **   **       ***         \s
                                88AAAAAA8bo   88888888    8YYYYYYYY          ***      **    **       ***           \s
                                88      Y8b   88   88     8b                 ***       **   **       ***           \s
                                88      db    8D 8b       88yyyyyyyy         ****       ***          ***              \s

                        """);


        System.out.print("                                                                             I. SYSTEM SETUP AND KEY GENERATION                                                                                                        \n");


        SystemSetup systemSetup = new SystemSetup();
        TrustAuthority trustAuthority = new TrustAuthority(systemSetup.getG1(), systemSetup.getGeneratorG(), systemSetup.getZr(), systemSetup.getPairing());
        System.out.println("\ng=" + trustAuthority.getGenerator() + "\n");
        System.out.println("alpha=" + trustAuthority.getAlpha() + "\n");
        System.out.println("beta=" + trustAuthority.getBeta() + "\n");
        System.out.println("p=" + trustAuthority.getG0().getOrder() + "\n");
        System.out.println("e(g,g)^alpha=" + trustAuthority.getPairing_result() + "\n");
        System.out.println("h=" + trustAuthority.getH() + "\n");


        System.out.println("SK FOR A USER : \n ");
        JuridicPerson firstUser = new JuridicPerson(1, "Bitdefender", "Bucuresti", "SRL", "312313", "13231", LocalDate.of(2000, 1, 1), "((A+B+C+D+E+F+G+H+I+J)*K)");
        Individual secondUser = new Individual(2, "Rusu Alexandru", "Bucuresti", "3123123132131231", LocalDate.of(2000, 2, 6), firstUser, Position.EMPLOYEE_PRODUCTION, true);
        JuridicPerson thirdUser = new JuridicPerson(3, "Endava", "Iasi", "SA", "1323131", "123131", LocalDate.of(1990, 3, 3), "((A+B+C+D+E+F+G+H+I+J)*K)");
        Individual forthUser = new Individual(4, "Rusu Alexandru", "Bucuresti", "3123123132131231", LocalDate.of(2000, 2, 6), thirdUser, Position.EMPLOYEE_PRODUCTION, true
        );

        trustAuthority.generateSK(secondUser);
        System.out.println("\n                                                                          II . ENCRYPTION USING PP-CP-ABE SCHEME                                                                                                    ");

        System.out.println("\n  1. RUN ENCRYPTION FOR ESP ACCESS TREE ");
        List<JuridicPerson> users = new ArrayList<>();
        users.add(firstUser);
        users.add(thirdUser);

        AccessTree accessTree = new AccessTree();
        List<AccessTree> accessTrees = accessTree.buildDOsTree(users);
        NodeAccessTree TreeESP = accessTree.getTree(accessTrees);
        //TA.inOrder(TreeESP);


        trustAuthority.generateTrashHoldsAndPolynomialsAnd_C_first_values(TreeESP, 0);
        trustAuthority.generateCValues(TreeESP);

        System.out.println("\n 2. RUN ENCRYPTION FOR DO's ACCESS TREE ");
        accessTrees.clear();
        users.clear();

        users.add(firstUser);
        accessTrees = accessTree.buildDOsTree(users);
        NodeAccessTree TreeDO = accessTree.getTree(accessTrees);
        trustAuthority.generateTrashHoldsAndPolynomialsAnd_C_first_values(TreeDO, 0);
        trustAuthority.generateCValues(TreeDO);


        Vector<NodeAccessTree> Y_DO = new Vector<>();
        trustAuthority.getLeafNodes(TreeDO, Y_DO);
        trustAuthority.getLeafNodes(TreeESP, Y_DO);

        trustAuthority.setCiphertext(TreeESP, TreeDO, "Welcome here !", firstUser);

        System.out.println("\n                                                                          III . DECRYPTION USING PP-CP-ABE SCHEME                                                                                                    ");

        trustAuthority.decryptMessage(trustAuthority.getCiphertext(), secondUser);
        trustAuthority.decryptMessage(trustAuthority.getCiphertext(), forthUser);


    }
}
