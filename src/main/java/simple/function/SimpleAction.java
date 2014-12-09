package simple.function;

import com.sun.istack.internal.NotNull;
import utils.Triplet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bayurov
 * Простая реализация регистрации действий
 */
public class SimpleAction {

    public static Map<String, Triplet<String, String, Action>> actions = new HashMap<>();

    public SimpleAction(@NotNull String name, @NotNull String description, @NotNull Action action) {
        actions.put(name, new Triplet<>(name, description, action));
    }
}
