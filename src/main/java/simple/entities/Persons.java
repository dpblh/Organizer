package simple.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bayurov
 * Обертка для списка клиентов
 */
@XmlRootElement
public class Persons {

    /**
     * Список клиентов
     */
    public List<PersonalData> personals = new ArrayList<>();

    /**
     * Метод добавления клиента
     * @param personalData клиент
     */
    public void add(PersonalData personalData) {
        personals.add(personalData);
    }
}
