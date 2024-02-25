package SpecialDataStructures;

import java.util.Vector;

public class Polynomial {

    private Vector<Integer> coefficients;
    private Vector<Integer> powers;
    private int degree;


    public Vector<Integer> getCoefficients() {
        return coefficients;
    }

    public Vector<Integer> getPowers() {
        return powers;
    }

    public void setPowers(Vector<Integer> Powers) {
        powers = Powers;
    }

    public void setCoefficients(Vector<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }


    public Polynomial(Vector<Integer> coefficients, Vector<Integer> powers, Integer degree) {
        setCoefficients(coefficients);
        setDegree(degree);
        this.powers = powers;
    }

    public int evaluate(int x) {
        int sum = 0;
        for (int i = 0; i < coefficients.size(); i++) {
            sum = sum + coefficients.get(i) * (int) Math.pow(x, powers.get(i));
        }
        return sum;

    }
}