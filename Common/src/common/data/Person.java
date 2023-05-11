package common.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, представляющий автора Лабораторной работы
 */
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.NONE)
public class Person implements Serializable {
    @XmlElement
    private String name;
    @XmlElement
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private LocalDateTime birthday;
    @XmlElement
    private String passportID;
    public Person(){}
    public Person(String name, LocalDateTime birthday, String passportID) {
        this.name = name;
        this.birthday = birthday;
        this.passportID = passportID;
    }
    public String toString2() {
        return name + "~0~" + birthday.toString() + "~0~" + passportID;
    }
    public static Person outOfString(String arg) {
        String[] p = arg.split("~0~");
        return new Person(p[0], LocalDateTime.parse(p[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME), p[2]);
    }

    /**
     * Метод возвращает имя автора Лабораторной работы
     * @return author_name
     */
    public String getName() {
        return name;
    }

    /**
     * Метод возвращает дату рождения автора Лабораторной работы
     * @return author birthday
     */
    public LocalDateTime getBirthday() {
        return birthday;
    }

    /**
     * Метод возвращает пасспортные данные автора Лабораторной работы
     * @return author_passportID
     */
    public String getPassportID() {
        return passportID;
    }

    @Override
    public String toString() {
        return "Автор работы: \nИмя: " + name + "\nДата рождения: " + birthday + "\nПаспортные данные: " + passportID;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + birthday.hashCode() + passportID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Person){
            Person personObj = (Person) obj;
            return name.equals(personObj.getName()) && birthday.equals(personObj.getBirthday())
                    && passportID.equals(personObj.getPassportID());
        }
        return false;
    }

}
