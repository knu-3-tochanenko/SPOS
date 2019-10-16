import java.util.Scanner;

public class ConfirmDialog {
    public static int run() {
        System.out.print("Do you want to continue?\n" +
                "(1) continue\n" +
                "(2) continue without prompt\n" +
                "(3) cancel\n" +
                "Write number: ");
        Scanner scanner = new Scanner(System.in);
        int resp = scanner.nextInt();
        return resp;
    }
}