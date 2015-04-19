import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Created by nyari on 2015.04.19..
 */
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

    public void generateOutputTo(BufferedWriter bw) {
        boolean succeeded = true;





        if (succeeded)
            bw.
    }
}
