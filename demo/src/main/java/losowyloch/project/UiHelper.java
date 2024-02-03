package losowyloch.project;

import java.util.Scanner;

public class UiHelper {
    private Scanner scanner;
    
    public UiHelper() {
        this.scanner = new Scanner(System.in);
    }

    public void clnTer() {
        System.out.print("\033\143");
    }

    public char showAndCollectInput(String[] msgs, char[] validInputs) {
        for (String msg : msgs) {
            System.out.println(msg);
        }
        char input = scanner.nextLine().charAt(0);
        this.clnTer();
        for (char validInput : validInputs) {
            if (input == validInput) {
                return input;
            }
        }
        System.out.println("Złe dane wejściowe! Wpisz znak z pomiedzy nawiasów żeby wybrać opcje!\n");
        return this.showAndCollectInput(msgs, validInputs);
    }
}
