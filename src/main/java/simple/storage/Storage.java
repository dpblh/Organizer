package simple.storage;

import simple.entities.PersonalData;
import simple.entities.Persons;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Bayurov
 * Класс для работы с хранимыми данными
 * Было ба наверное лучше поднимать все данные в память при старте. Будем считать что так более устойчиво к ошибкам
 */
public class Storage {

    private static final String FILE_NAME = "storage.txt";
    private static final File fileStorage = new File("./" + FILE_NAME);
    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext context = JAXBContext.newInstance(Persons.class, PersonalData.class);
            marshaller = context.createMarshaller();
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException ignored) {}
    }

    /**
     * Заполняем 'базу' тестовыми значениями
     */
    public static void generateTestData() {
        Persons persons = new Persons();
        persons.add(new PersonalData(UUID.randomUUID().toString(), "Петров П.В", "Прораб", "example@mail.ru", "666666"));
        persons.add(new PersonalData(UUID.randomUUID().toString(), "Петров1 П.В", "Прораб1", "example1@mail.ru", "666665"));
        persons.add(new PersonalData(UUID.randomUUID().toString(), "Петров2 П.В", "Прораб2", "example2@mail.ru", "666664"));
        persons.add(new PersonalData(UUID.randomUUID().toString(), "Петров3 П.В", "Прораб3", "example3@mail.ru", "666663"));
        persons.add(new PersonalData(UUID.randomUUID().toString(), "Петров4 П.В", "Прораб4", "example4@mail.ru", "666662"));
        updateAll(persons);
    }

    /**
     * Обнавляем сразу весь список клиентов
     * @param persons все клиенты
     */
    public static void updateAll(Persons persons) {
        marshal(persons);
    }

    /**
     * Добавляем клиента
     * @param person клиент
     */
    public static void add(PersonalData person) {
        Persons persons = unmarshal();
        persons.add(person);
        updateAll(persons);
    }

    /**
     * Достаём всех клиентов
     * @return все клиенты
     */
    public static Persons all() {
        return unmarshal();
    }

    /**
     * Поиск клиента по телефону
     * @param phone номер телефона
     * @return клиент
     */
    public static PersonalData findByPhone(String phone) {
        Persons persons = unmarshal();
        Stream<PersonalData> stream = persons.personals.stream().filter(person -> person.phone.equals(phone));
        Iterator<PersonalData> iterator = stream.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }else {
            return null;
        }
    }

    /**
     * Удаление по ФИО
     * @param fio ФИО
     */
    public static void deleteByFio(String fio) {
        Persons persons = unmarshal();
        persons.personals = persons.personals.stream().filter(person -> !person.fio.equals(fio)).collect(Collectors.toList());
        marshal(persons);
    }



//    PRIVATE приватный блок
    private static void marshal(Persons persons) {
        try {
            marshaller.marshal(persons, fileStorage);
        } catch (JAXBException ignored) {}
    }

    private static Persons unmarshal() {
        Persons persons = null;
        try {
            persons = (Persons) unmarshaller.unmarshal(fileStorage);
        } catch (JAXBException ignored) {}
        if (persons == null) persons = new Persons();
        return persons;
    }
}
