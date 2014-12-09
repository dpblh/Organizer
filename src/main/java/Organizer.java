import static utils.IOHelper.inNextLine;
import static utils.IOHelper.print;

/**
 * @author Bayurov
 * Точка входа в программу
 */
public class Organizer {

    static {
//        register action
        RegisterActions.onRegister();
    }

    public static void main(String[] args) {

        print("Органайзер запущен");

        while (true) {
            RegisterActions.invokeAction(inNextLine());
        }


    }
}
