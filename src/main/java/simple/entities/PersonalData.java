package simple.entities;

import com.sun.xml.internal.txw2.annotation.XmlElement;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Bayurov
 * Персональные данные клиента
 */
@XmlElement
public class PersonalData extends AbstractEntity {

    /**
     * Уникальный идентификатор клиента
     */
    public String uid;

    /**
     * ФИО клиента
     */
    @NotNull(message="ФИО не может быть пустым")
    public String fio;

    /**
     * Должность
     */
    @NotNull(message="Должность не может быть пустой")
    public String job;

    /**
     * E-mail
     */
    @NotNull(message="Имэйл должен быть задан")
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$",
            message = "Заданный имэйл не может существовать")
    public String email;

    /**
     * Телефон
     */
    @NotNull(message="Телефон не может быть пустым")
    @Pattern(regexp = "\\d+", message = "Номер телефона должен содержать только цифры")
    public String phone;

    @Override
    public String toString() {
        return String.format("fio: %s, job: %s, email: %s, phone: %s", fio, job, email, phone);
    }

    public PersonalData() {}

    public PersonalData(String uid, String fio, String job, String email, String phone) {
        this.uid = uid;
        this.fio = fio;
        this.job = job;
        this.email = email;
        this.phone = phone;
    }

}
