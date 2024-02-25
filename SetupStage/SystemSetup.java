package SetupStage;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;


public class SystemSetup {
    private final Field G1;
    private final Field Zr;
    private final Element generatorG;
    private final Pairing pairing;


    public Field getG1() {
        return G1;
    }

    public Field getZr() {
        return Zr;
    }

    public Element getGeneratorG() {
        return generatorG;
    }


    public Pairing getPairing() {
        return pairing;
    }


    public SystemSetup() {
        this.pairing = PairingFactory.getPairing("params.properties");
        this.Zr = pairing.getZr();
        this.G1 = pairing.getG1();
        this.generatorG = G1.newRandomElement();

    }
}
