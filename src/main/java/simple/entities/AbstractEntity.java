package simple.entities;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Bayurov
 * Абстрактный класс всех сущьностей
 * Содержит часто используемые методы
 */
public abstract class AbstractEntity {

    private static Validator validator;

    static {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    /**
     * Валидация обьекта
     * @return список невылидных полей
     */
    public Set<ConstraintViolation<Object>> validate() {
        return validator.validate(this);
    }

    /**
     * Возвращает список форматированных строк ошибок
     * @return список сообщений об ошибках
     */
    public List<String> getInvalidMessage() {
        return validate().stream().map(error -> (String.format(
                "Внимание, ошибка! property: [%s], value: [%s], message: [%s]",
                error.getPropertyPath(), error.getInvalidValue(), error.getMessage()))).collect(Collectors.toList());
    }

}
