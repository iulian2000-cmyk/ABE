package SetupStage;


import ABDS_System.FileStored;
import SpecialDataStructures.AccessTree;
import SpecialDataStructures.CiphertextCt;
import SpecialDataStructures.NodeAccessTree;
import SpecialDataStructures.Polynomial;
import Users.Individual;
import Users.JuridicPerson;
import Users.User;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;

import java.math.BigInteger;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Logger;


public class TrustAuthority {
    private static final Logger log = Logger.getLogger(TrustAuthority.class.getName());
    private final Field G0;
    private final Field Zr;
    private final Element generator;
    private final Element h;
    private Element pairing_result;
    private final Element alpha;
    private final Element beta;
    private final Pairing pairingFunction;
    private double s;


    private CiphertextCt ciphertext;


    public Field getG0() {
        return G0;
    }

    public Element getGenerator() {
        return generator;
    }

    public Element getH() {
        return h;
    }

    public Element getPairing_result() {
        return pairing_result;
    }

    public Element getAlpha() {
        return alpha;
    }

    public Element getBeta() {
        return beta;
    }


    public TrustAuthority(Field G, Element g, Field Zr1, Pairing pairing) {
        G0 = G;
        generator = g;
        Zr = Zr1;
        beta = Zr.newRandomElement();
        alpha = Zr.newRandomElement();
        pairingFunction = pairing;
        h = generator.pow(beta.toBigInteger());
        pairing_result = pairing.pairing(generator, generator);
        pairing_result = pairing_result.pow(alpha.toBigInteger());
    }

    public void userDownloadFile(Individual individual, FileStored file, String location) throws Exception {
        if (individual == file.getOwnerFile()) {
            file.downloadFile(location);
        } else {
            AccessTree accessTree = new AccessTree();
            String infix = file.getOwnerFile().getInfixExpressionOfAccessPolicy();
            NodeAccessTree root = accessTree.constructTree(infix);
            accessTree = new AccessTree(root, (JuridicPerson) file.getOwnerFile());
            AccessTree.setInfixExpression(infix);
            if (accessTree.checkTree(individual)) {
                log.info("The individual can download the file !");
                file.downloadFile(location);
            } else {
                log.info("The individual can't access the file !");
            }
        }

    }

    public void generateSK(Individual user) {
        Element r = Zr.newRandomElement();
        Element D = generator.pow(alpha.add(r).div(beta).toBigInteger());
        user.setD(D);

        Element compareElement = r;
        compareElement.set(0);
        while (r.isEqual(compareElement)) {
            r = Zr.newRandomElement();
        }
        user.setR(r);
        for (String attribute : user.getListAttributes()) {
            Element rj = Zr.newRandomElement();
            log.info("L[" + user.getListAttributes().indexOf(attribute) + "]=" + CalculateLj(rj, generator));
            log.info("R[" + user.getListAttributes().indexOf(attribute) + "]=" + CalculateDj(rj, r, generator, attribute));
        }

    }

    public Element CalculateDj(Element rj, Element r, Element generator, String attribute) {
        Element power = rj;
        power.set(attribute.hashCode());
        power = power.pow(rj.toBigInteger());

        return generator.pow(r.toBigInteger()).mul(power.toBigInteger());
    }

    public Element CalculateLj(Element rj, Element generator) {
        return generator.pow(rj.toBigInteger());
    }

    public void generateTrashHoldsAndPolynomialsAnd_C_first_values(NodeAccessTree node, int indexNode) {
        if (node != null) {
            Random rand = new Random();
            Vector<Integer> coefficients = new Vector<>();
            Vector<Integer> powers = new Vector<>();

            if (node.value == '*') {
                node.k_threshold = getNumberChildren(node) - 1;
                int degree = Math.abs(node.k_threshold - 1);
                int numberCoefficients = rand.nextInt(0, 10);
                for (int index = 0; index < numberCoefficients; index++) {
                    coefficients.add(rand.nextInt(-20, 20));
                }
                powers.add(degree);
                for (int index = 1; index < numberCoefficients - 1; index++) {
                    if (degree > 0) {
                        powers.add(rand.nextInt(0, degree - 1));
                    } else {
                        powers.add(0);
                    }
                }
                powers.add(0);
                node.setPolynomial(new Polynomial(coefficients, powers, degree));
                if (indexNode == 0) {
                    s = node.getPolynomial().evaluate(0);
                }
            } else {
                node.k_threshold = 0;
                int degree = Math.abs(node.k_threshold - 1);
                int numberCoefficients = rand.nextInt(0, 10);
                for (int index = 0; index < numberCoefficients; index++) {
                    if (index != numberCoefficients - 1) {
                        coefficients.add(rand.nextInt(-20, 20));
                    } else {
                        coefficients.add(node.getParent().getPolynomial().evaluate(indexNode));
                    }
                }
                for (int index = 0; index < numberCoefficients; index++) {
                    if (index == numberCoefficients - 1) {
                        powers.add(0);
                    } else {
                        powers.add(degree);
                    }
                }
                node.setPolynomial(new Polynomial(coefficients, powers, degree));
                if (node.value != '+') {
                    String attribute = switch (node.value) {
                        case 'A' -> "EMPLOYEE_PRODUCTION";
                        case 'B' -> "LEADER_PRODUCTION";
                        case 'C' -> "DIRECTOR_PRODUCTION";
                        case 'D' -> "DIRECTOR";
                        case 'E' -> "DIRECTOR_ECONOMIC";
                        case 'F' -> "ACCOUNTANT";
                        case 'G' -> "ADMINISTRATOR";
                        case 'H' -> "CHIEF_ACCOUNTANT";
                        case 'I' -> "SECURITY_ADMIN";
                        case 'J' -> "CEO";
                        case 'K' -> "ACCORD_SUPERIOR-YES";
                        default -> "NO";
                    };
                    node.setC_(Math.pow(attribute.hashCode(), node.getPolynomial().evaluate(0)));
                    log.info("C'_" + attribute + "  :=" + node.getC_());
                }
            }
            indexNode++;
            if (node.left != null) {
                node.left.setParent(node);
                generateTrashHoldsAndPolynomialsAnd_C_first_values(node.left, indexNode);
            }
            indexNode++;
            if (node.right != null) {
                node.right.setParent(node);
                generateTrashHoldsAndPolynomialsAnd_C_first_values(node.right, indexNode);
            }
        }
    }

    public int getNumberChildren(NodeAccessTree node) {
        if (node.left == null && node.right == null) {
            return 0;
        } else {
            if (node.left == null) {
                return 1 + getNumberChildren(node.right);
            } else {
                if (node.right == null) {
                    return 1 + getNumberChildren(node.left);
                } else {
                    return 2 + getNumberChildren(node.left) + getNumberChildren(node.right);
                }
            }
        }
    }

    public void generateCValues(NodeAccessTree root) {
        if (root != null) {
            if (root.value != '*' && root.value != '+') {
                String attribute = switch (root.value) {
                    case 'A' -> "EMPLOYEE_PRODUCTION";
                    case 'B' -> "LEADER_PRODUCTION";
                    case 'C' -> "DIRECTOR_PRODUCTION";
                    case 'D' -> "DIRECTOR";
                    case 'E' -> "DIRECTOR_ECONOMIC";
                    case 'F' -> "ACCOUNTANT";
                    case 'G' -> "ADMINISTRATOR";
                    case 'H' -> "CHIEF_ACCOUNTANT";
                    case 'I' -> "SECURITY_ADMIN";
                    case 'J' -> "CEO";
                    case 'K' -> "ACCORD_SUPERIOR-YES";
                    default -> null;
                };
                root.setC(generator.pow(BigInteger.valueOf(root.getPolynomial().evaluate(0))));
                log.info("C_" + attribute + "  :=" + root.getC());
            }
            generateCValues(root.left);
            generateCValues(root.right);
        }
    }

    public NodeAccessTree getProductTree(NodeAccessTree A, NodeAccessTree B) {
        NodeAccessTree resultTree = new NodeAccessTree('*');
        resultTree.setIndex(0);
        resultTree.setLeft(A);
        resultTree.setRight(B);
        return resultTree;
    }

    public void getLeafNodes(NodeAccessTree root, Vector<NodeAccessTree> leafNodes) {
        if (root != null) {
            if (root.value != '*' && root.value != '+') {
                leafNodes.add(root);
            }
            getLeafNodes(root.left, leafNodes);
            getLeafNodes(root.right, leafNodes);
        }
    }

    public CiphertextCt getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(NodeAccessTree TreeESP, NodeAccessTree TreeDO, String message, User DO_user) {
        Vector<NodeAccessTree> leafNodesTreeESP = new Vector<>();
        getLeafNodes(TreeESP, leafNodesTreeESP);
        Vector<NodeAccessTree> leafNodesDO = new Vector<>();
        getLeafNodes(TreeDO, leafNodesDO);

        Vector<NodeAccessTree> leafNodes = new Vector<>();
        leafNodes.addAll(leafNodesDO);
        leafNodes.addAll(leafNodesTreeESP);

        ciphertext = new CiphertextCt(getProductTree(TreeESP, TreeDO), message, h.pow(BigInteger.valueOf((long) s)), leafNodes, (JuridicPerson) DO_user
                , TreeDO);
    }

    public void generateBlindedSK(Individual user) {
        Element t = Zr.newRandomElement();
        user.setT(t);
        Element result_adding = alpha.add(user.getR());
        result_adding = result_adding.div(beta);
        result_adding = result_adding.mul(t);
        user.setBlindedD(generator.pow(result_adding.toBigInteger()));
    }


    public void calculateFy_values(Vector<NodeAccessTree> leafNodes, Element r) {
        for (NodeAccessTree node : leafNodes) {
            node.setFy(pairingFunction.pairing(generator, generator).pow(r.duplicate().mul(BigInteger.valueOf(node.getPolynomial().evaluate(0))).toBigInteger()));
            log.info("Fy_" + node.value + " :=" + pairingFunction.pairing(generator, generator).pow(r.mul(BigInteger.valueOf(node.getPolynomial().evaluate(0))).toBigInteger()));
        }
    }

    public void generateChildNodesSet(NodeAccessTree nodeAccessTree, Vector<NodeAccessTree> nodes) {
        if (nodeAccessTree != null) {
            if (nodeAccessTree.right != null && nodeAccessTree.left != null) {
                nodes.add(nodeAccessTree.right);
                nodes.add(nodeAccessTree.left);
                generateChildNodesSet(nodeAccessTree.right, nodes);
                generateChildNodesSet(nodeAccessTree.left, nodes);
            }
        }
    }


    public void calculateFxValues(NodeAccessTree nodeAccessTree, Element r) {
        if (nodeAccessTree != null) {
            Vector<NodeAccessTree> childNodes = new Vector<>();
            generateChildNodesSet(nodeAccessTree, childNodes);
            for (NodeAccessTree nodeChild : childNodes) {

                log.info("Fx_" + nodeChild.value + ":=" + pairingFunction.pairing(generator, generator).pow(r.duplicate().mul(BigInteger.valueOf(nodeChild.getPolynomial().evaluate(0))).toBigInteger()));
            }
            if (nodeAccessTree.right != null && nodeAccessTree.left != null) {
                calculateFxValues(nodeAccessTree.right, r.duplicate());
                calculateFxValues(nodeAccessTree.left, r.duplicate());
            }
        }
    }


    public void decryptMessage(CiphertextCt ciphertext, Individual DR) {
        if (ciphertext.getTree().checkTree(DR)) {
            generateBlindedSK(DR);
            Element r = DR.getR();

            log.info("\n 1.Calculate F_y values for each leaf node  \n");

            calculateFy_values(ciphertext.getLeafNodes(), r);

            log.info("\n 2.Calculate F_x values for each leaf node  \n");
            calculateFxValues(AccessTree.getRoot(), r);
            log.info("A :=" + pairingFunction.pairing(generator, generator).pow(r.duplicate().mul(ciphertext.getTreeDO().getPolynomial().evaluate(0)).toBigInteger()));
            Element A = pairingFunction.pairing(generator, generator).pow(r.duplicate().mul(ciphertext.getTreeDO().getPolynomial().evaluate(0)).toBigInteger());

            Element power_1 = DR.getT().mul(r.duplicate().mul(BigInteger.valueOf(ciphertext.getTreeDO().getPolynomial().evaluate(0))));
            Element power_2 = DR.getT().mul(alpha.mul(BigInteger.valueOf(ciphertext.getTreeDO().getPolynomial().evaluate(0))));
            log.info("B :=" + pairingFunction.pairing(generator, generator).pow(power_1.toBigInteger()).mul(pairingFunction.pairing(generator, generator).pow(power_2.toBigInteger())));

            pairingFunction.pairing(generator, generator).pow(power_1.toBigInteger()).mul(pairingFunction.pairing(generator, generator).pow(power_2.toBigInteger()));

            Element B_ = pairingFunction.pairing(generator, generator).pow(r.duplicate().mul(BigInteger.valueOf(ciphertext.getTreeDO().getPolynomial().evaluate(0))).toBigInteger()).mul(pairingFunction.pairing(generator, generator).pow(alpha.mul(BigInteger.valueOf(ciphertext.getTreeDO().getPolynomial().evaluate(0))).toBigInteger()));
            log.info("B' :=" + B_);

            if (B_.div(A).isEqual(ciphertext.getPairingResult())) {
                log.info("Message decrypted := " + ciphertext.getMessage());
            }
        } else {
            log.info("Access tree not satisfied ");
        }
    }


}
