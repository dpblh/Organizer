package simple.function;

import com.sun.istack.internal.NotNull;

import static utils.IOHelper.print;

/**
 * @author Bayurov
 * Чуть сложнее чем SimpleAction
 * Проверяет что действие должно содержать аргумент
 */
public class RequireParamAction extends SimpleAction {

    public RequireParamAction(@NotNull String name, @NotNull String description, @NotNull Action action) {
        super(name, description, (args) -> {
            if (args.isEmpty()) {
                print("Параметр не может быть пустым");
                return;
            }
            action.invoke(args);
        });
    }

}
