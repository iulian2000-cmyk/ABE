package Users;

import it.unisa.dia.gas.jpbc.Element;

import java.util.List;


public class User {
    private int idUser;
    private Element D;
    private Element t;
    private Element blindedD;
    protected Element r;
    protected List<String> listAttributes;
    private String infixExpressionOfAccessPolicy;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Element getR() {
        return r;
    }

    public void setR(Element r) {
        this.r = r;
    }

    public List<String> getListAttributes() {
        return listAttributes;
    }

    public void setListAttributes(List<String> listAttributes) {
        this.listAttributes = listAttributes;
    }


    public Element getD() {
        return D;
    }

    public void setD(Element d) {
        D = d;
    }

    public Element getBlindedD() {
        return blindedD;
    }

    public void setBlindedD(Element blinded_D) {
        blindedD = blinded_D;
    }

    public Element getT() {
        return t;
    }

    public void setT(Element T) {
        t = T;
    }

    public String getInfixExpressionOfAccessPolicy() {
        return infixExpressionOfAccessPolicy;
    }

    public void setInfixExpressionOfAccessPolicy(String infixExpressionOfAccessPolicy) {
        this.infixExpressionOfAccessPolicy = infixExpressionOfAccessPolicy;
    }


}
