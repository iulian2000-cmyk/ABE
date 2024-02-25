package ABDS_System;

import SetupStage.SystemSetup;
import SetupStage.TrustAuthority;
import Users.Individual;
import Users.JuridicPerson;
import Users.Position;

import java.time.LocalDate;

public class TestABDS {
    public static void main(String[] args) throws Exception {
        JuridicPerson firstUser = new JuridicPerson(1, "Bitdefender", "Bucuresti", "SRL", "312313", "13231", LocalDate.of(2000, 1, 1), "((A+B+C+D+E+F+G+H+I+J)*K)");
        FileStored fileStored = new FileStored("/home/iulian/Public/FII/Licenta/book/book-license.pdf", firstUser, "storageSpace/book-license.pdf");
        Individual secondUser = new Individual(2, "Rusu Alexandru", "Bucuresti", "3123123132131231", LocalDate.of(2000, 2, 6), firstUser, Position.EMPLOYEE_PRODUCTION, true);
        SystemSetup systemSetup = new SystemSetup();
        TrustAuthority trustAuthority = new TrustAuthority(systemSetup.getG1(), systemSetup.getGeneratorG(), systemSetup.getZr(), systemSetup.getPairing());

        trustAuthority.userDownloadFile(secondUser, fileStored, "/home/iulian/test.pdf");
    }
}
