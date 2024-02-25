package SpecialDataStructures;

import java.util.Stack;

public class ExpressionEvaluator {
    public static int evaluate(String expression) {
        char[] words = expression.toCharArray();
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < words.length; i++) {
            if (words[i] == ' ')
                continue;
            if (words[i] >= '0' &&
                    words[i] <= '9') {
                StringBuilder buffer = new StringBuilder();
                while (i < words.length && words[i] >= '0' && words[i] <= '9')
                    buffer.append(words[i++]);
                values.push(Integer.parseInt(buffer.toString()));
                i--;
            } else if (words[i] == '(')
                operators.push(words[i]);
            else if (words[i] == ')') {
                while (operators.peek() != '(')
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                operators.pop();
            } else if (words[i] == '+' || words[i] == '-' || words[i] == '*' || words[i] == '/') {
                while (!operators.empty() && hasPrecedence(words[i], operators.peek()))
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                operators.push(words[i]);
            }
        }
        while (!operators.empty())
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        return values.pop();
    }

    public static boolean hasPrecedence(char operator_1, char operator_2) {
        if (operator_2 == '(' || operator_2 == ')')
            return false;
        return (operator_1 != '*' && operator_1 != '/') || (operator_2 != '+' && operator_2 != '-');
    }

    public static int applyOperator(char operator, int b, int a) {
        return switch (operator) {
            case '+' -> a + b;
            case '*' -> a * b;
            default -> 0;
        };
    }
}
