package SpecialDataStructures;

import java.util.Vector;

import Users.JuridicPerson;
import it.unisa.dia.gas.jpbc.Element;

public class CiphertextCt {
    private AccessTree tree;
    private String message;
    private Element pairingResult;
    private Vector<NodeAccessTree> leafNodes;
    private NodeAccessTree TreeDO;

    public Vector<NodeAccessTree> getLeafNodes() {
        return leafNodes;
    }

    public void setLeafNodes(Vector<NodeAccessTree> leafNodesTree) {
        leafNodes = leafNodesTree;
    }


    public AccessTree getTree() {
        return tree;
    }

    public void setTree(AccessTree accessTree) {
        tree = accessTree;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String messageDecrypt) {
        message = messageDecrypt;
    }

    public Element getPairingResult() {
        return pairingResult;
    }

    public void setPairingResult(Element pairingFunction) {
        pairingResult = pairingFunction;
    }

    public CiphertextCt(NodeAccessTree tree, String msg, Element pairingResult, Vector<NodeAccessTree> leafNodes, JuridicPerson juridicPerson, NodeAccessTree TreeDO) {
        AccessTree accessTree = new AccessTree(tree, juridicPerson);
        setTree(accessTree);
        setMessage(msg);
        setMessage(msg);
        setLeafNodes(leafNodes);
        setTreeDO(TreeDO);
        setPairingResult(pairingResult);
    }

    public NodeAccessTree getTreeDO() {
        return TreeDO;
    }

    public void setTreeDO(NodeAccessTree treeDO) {
        TreeDO = treeDO;
    }
}
