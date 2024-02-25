package SpecialDataStructures;


import it.unisa.dia.gas.jpbc.Element;

public class NodeAccessTree {

    public char value;
    public int k_threshold;
    public int index;
    public NodeAccessTree left,right;
    public  Polynomial polynomial;
    public  NodeAccessTree parent;
    public double C_;
    public Element C;
    public Element fy;

    public void setFy(Element fy) {
        this.fy = fy;
    }

    public double getC_() {
        return C_;
    }

    public void setC_(double c_) {
        C_ = c_;
    }

    public Element getC() {
        return C;
    }

    public void setC(Element c) {
        C = c;
    }


    public NodeAccessTree(char value){
        this.value = value;
    }

    public NodeAccessTree getParent() {
        return parent;
    }

    public void setParent(NodeAccessTree parent) {
        this.parent = parent;
    }

    public  Polynomial getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(Polynomial polynomialToSet) {
        polynomial = polynomialToSet;
    }

    public  NodeAccessTree getLeft() {
        return left;
    }

    public void setLeft(NodeAccessTree left) {
        this.left = left;
    }

    public  NodeAccessTree getRight() {
        return right;
    }

    public void setRight(NodeAccessTree right) {
        this.right = right;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}