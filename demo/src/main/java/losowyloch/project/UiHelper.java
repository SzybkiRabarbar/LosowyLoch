package losowyloch.project;

import java.io.IOException;
import java.util.Scanner;

public class UiHelper {
    private Scanner scanner;
    
    public UiHelper() {
        this.scanner = new Scanner(System.in);
    }

    public void clearTerminal() {
        try {
            System.out.println();
            new ProcessBuilder("powershell", "/c", "\"Write-Host ('-' * $Host.UI.RawUI.BufferSize.Width)\"").inheritIO().start().waitFor();
            System.out.println();
            new ProcessBuilder("powershell", "/c", "\"Write-Host ('-' * $Host.UI.RawUI.BufferSize.Width)\"").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public char showAndCollectInput(String[] msgs, char[] validInputs) {
        for (String msg : msgs) {
            System.out.println(msg);
        }
        String line = scanner.nextLine();
        clearTerminal();
        if (!line.isEmpty()) {
            char input = line.charAt(0);
            for (char validInput : validInputs) {
                if (input == validInput) {
                    return input;
                }
            }
        }
        System.out.println("Złe dane wejściowe! Wpisz znak z pomiedzy nawiasów żeby wybrać opcje!\n");
        return this.showAndCollectInput(msgs, validInputs);
    }
    public Scanner getScanner() {
        return scanner;
    }
}
