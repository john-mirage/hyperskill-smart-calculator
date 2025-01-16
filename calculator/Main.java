package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        while (calculator.getState() == CalculatorState.ON) {
            String input = scanner.nextLine();
            calculator.execute(input);
        }
    }
}
