import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


public class Differ
{
    private BufferedReader left;
    private BufferedReader right;
    private boolean correct;


    public Differ(BufferedReader firstInput, BufferedReader secondInput) {
        left = firstInput;
        right = secondInput;
        if (left == null || right == null)
            correct = false;
        else
            correct = true;
    }

    public void generateOutputTo(OutputStream os) throws IOException {
        boolean succeeded = true;
        PrintWriter w = new PrintWriter(os);


        boolean running = true;
        while (running) {
            String lhs = left.readLine();
            String rhs = right.readLine();

            if (lhs != null && rhs != null) {
                if  (!lhs.equals(rhs)) {
                    if (succeeded == true) {
                        w.println("FAIL");
                        succeeded = false;
                    }
                    w.println(lhs);
                    w.println(rhs);
                }
            } else {
                running = false;
                succeeded = succeeded && ((lhs == null) && (rhs == null));
            }
        }

        if (succeeded)
            w.println("PASS");

        w.flush();
    }
}
