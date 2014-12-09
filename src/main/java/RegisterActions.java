import simple.entities.PersonalData;
import simple.entities.Persons;
import simple.function.Action;
import simple.function.RequireParamAction;
import simple.function.SimpleAction;
import simple.storage.Storage;
import utils.IOHelper;
import utils.Triplet;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.IOHelper.inNextLine;
import static utils.IOHelper.print;

/**
 * @author Bayurov
 * Статический класс для работы с действиями
 */
public class RegisterActions {

    private static final String NOT_FOUND_COMMAND = "Извините команда не найдена";

    /**
     * Регистрация действий
     */
    public static void onRegister() {

//        Регистрация хелпера
        new SimpleAction("help", "Просмотр всех доступных команд 'help' или конкретной 'help exit'", (args) -> {

            if (args.isEmpty()) {
                SimpleAction.actions.values().forEach(triple -> print(triple.first + " - " + triple.second));
                return;
            }

            Triplet<String, String, Action> command = SimpleAction.actions.get(args);

            print(command.first + " - " + command.second);

        });

//        Регистрация метода вставки
        new SimpleAction("insert", "Вставка клиента", (args) -> {

            PersonalData personalData = new PersonalData();

            print("Введите ФИО");               personalData.fio = inNextLine();
            print("Введите должность");         personalData.job = inNextLine();
            print("Введите e-mail");            personalData.email = inNextLine();
            print("Введите номер телефона");    personalData.phone = inNextLine();

            if (personalData.validate().isEmpty()) {
                Storage.add(personalData);
                print("Данные сохранены");
            } else {
                personalData.getInvalidMessage().stream().forEach(IOHelper::print);
            }

        });

//        Регистрация метода обнавления
        new SimpleAction("update", "Обновление клиента", (args) -> {

            print("Выберите клиента");

            Persons persons = Storage.all();

            int[] index = { 0 };

            persons.personals.stream().forEachOrdered((person) ->  {
                print(index[ 0 ] + ". " + person.fio);
                index[ 0 ]++;
            });

            PersonalData personalData = persons.personals.get(Integer.parseInt(inNextLine()));

            print("Введите ФИО");               personalData.fio = inNextLine();
            print("Введите должность");         personalData.job = inNextLine();
            print("Введите e-mail");            personalData.email = inNextLine();
            print("Введите номер телефона");    personalData.phone = inNextLine();

            if (personalData.validate().isEmpty()) {
                Storage.updateAll(persons);
                print("Данные сохранены");
            } else {
                personalData.getInvalidMessage().stream().forEach(IOHelper::print);
            }

        });

//        Регистрация метода обнавления
        new RequireParamAction("delete", "Удаление клиента", (args) -> {

            Storage.deleteByFio(args);

            print("Удален");

        });

//        Регистрация метода простотра
        new SimpleAction("list", "Список клиентов", args -> {

            List<PersonalData> persons = Storage.all().personals;

            if (args.contains("fio"))       persons.sort((a, b) -> a.fio.compareTo(b.fio));
            if (args.contains("job"))       persons.sort((a, b) -> a.job.compareTo(b.job));
            if (args.contains("email"))     persons.sort((a, b) -> a.email.compareTo(b.email));
            if (args.contains("phone"))     persons.sort((a, b) -> a.phone.compareTo(b.phone));

            persons.forEach(person -> print(person.toString()));

        });

//        Регистрация метода поиска
        new RequireParamAction("find", "Поиск клиента по номеру телефона", (args) -> {

            PersonalData person = Storage.findByPhone(args);

            if (person != null){
                print(person.toString());
            } else {
              print("По вашему запросу ничего не найдено");
            }

        });

//        Регистрация команды выхода из приложения
        new SimpleAction("exit", "Выход из программы", args -> System.exit(0));
        new SimpleAction("test", "Заполнить тестовыми данными", args -> Storage.generateTestData());

    }

    /**
     * Поиск и вызов найденного действия
     * @param str ввод пользователя
     */
    public static void invokeAction(String str) {

        Pattern pattern = Pattern.compile("^(\\w+)\\b(.*)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {

//            Название действия
            String actionName = matcher.group(1);
//            Параметр действия
            String actionParams = matcher.group(2);
            if (actionParams != null && !actionParams.isEmpty())    actionParams = actionParams.trim();

            Triplet<String, String, Action> action = SimpleAction.actions.get(actionName);
            if(action != null) {
                action.third.invoke(actionParams);
                return;
            }
        }

        print(NOT_FOUND_COMMAND);

    }
}
