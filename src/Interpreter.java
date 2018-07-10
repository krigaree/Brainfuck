import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;

public class Interpreter {

    private Scanner in = new Scanner(System.in);
    private final int LENGTH = 30000;
    private byte[] data = new byte[LENGTH];
    private int pointer = 0;
    private Stack<Integer> point = new Stack<>();

    public void interpreter(String code) {

        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '>') {
                pointer = (pointer == LENGTH - 1) ? 0 : pointer + 1;                //Jump from last memory cell to first memory cell
            } else if (code.charAt(i) == '<') {
                pointer = (pointer == 0) ? pointer = LENGTH - 1 : pointer - 1;      //Jump from last first cell to last memory cell
            } else if (code.charAt(i) == '+') {
                ++data[pointer];
            } else if (code.charAt(i) == '-') {
                --data[pointer];
            } else if (code.charAt(i) == '.') {
                System.out.printf("%c", data[pointer]);
            } else if (code.charAt(i) == ',') {
                data[pointer] = (byte) in.next().charAt(0);
            } else if (code.charAt(i) == '[') {         //Push iterator onto the stack to store position where the loop starts
                if (data[pointer] != 0) {
                    point.push(i);
                }
            } else if (code.charAt(i) == ']') {
                if (!point.empty()) {
                    if (data[pointer] != 0) {
                        i = point.peek();               //Jump to the position where the loop is started
                    } else {
                        point.pop();                    //Delete the position from the stack
                    }
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        if (args[0].equals("--help")) {                                                  //Manual and informations
            System.out.println("Brainfuck interpreter created by Piotr Wyrwiński\n" +
                    "To execute:\n" +
                    "java Interpreter [ Brainfuck code ] [ -f path to file ]");
        } else {
            if (args.length > 1 && args[0].equals(("-f"))) {                            //Path to file option
                String code = "";
                try {
                    code = new String(Files.readAllBytes(Paths.get(args[1])));
                } catch (IOException e) {
                    System.out.println("Error, try again");
                }
                new Interpreter().interpreter(code);
            } else {
                new Interpreter().interpreter(args[0]);                                 //Code from console
            }
        }
    }
}
