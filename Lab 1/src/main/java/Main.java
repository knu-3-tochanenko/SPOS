import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("It works!");
        Process p = Runtime.getRuntime().exec("java -jar module_f.jar 0 0");
        System.out.println(p.exitValue());
        Process p2 = Runtime.getRuntime().exec("java -jar module_g.jar 0 0");
        System.out.println(p2.exitValue());
    }
}
