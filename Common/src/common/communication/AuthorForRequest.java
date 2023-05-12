package common.communication;

import common.data.Person;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuthorForRequest implements Serializable {
    private String name;
    private LocalDateTime birthday;
    private String passportID;

    public AuthorForRequest(String name, LocalDateTime birthday, String passportID) {
        this.name=name;
        this.birthday=birthday;
        this.passportID=passportID;
    }


    /**
     * Метод возвращает имя автора Лабораторной работы
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Метод возвращает день рождения автора Лабораторной работы
     * @return birthday
     */
    public LocalDateTime getBirthday() {
        return birthday;
    }

    public String getPassportID() {
        return passportID;
    }

    public byte[] getBytes() {
        byte[] a = name.getBytes();
        byte[] b = birthday.toString().getBytes();
        byte[] c = passportID.getBytes();
        byte[] d = new byte[a.length+b.length+c.length];
        int count = 0;
        for(int i = 0; i<a.length; i++) {
            d[i] = a[i];
            count++;
        }
        for(int j = 0;j<b.length;j++) {
            d[count++] = b[j];
        }
        for(int j = 0;j<c.length;j++) {
            d[count++] = c[j];
        }
        return d;
    }

    @Override
    public String toString() {
        return name + "~!~" + birthday.toString() + "~!~" + passportID;
    }
    public static AuthorForRequest outOfString(String arg){
        String[] author = arg.split("~!~");
        return new AuthorForRequest(author[0], LocalDateTime.parse(author[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME), author[2]);
    }
    @Override
    public int hashCode() {
        return name.hashCode() + birthday.hashCode() + passportID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Person) {
            Person personObj = (Person) obj;
            return (name.equals(personObj.getName()) && birthday.equals(personObj.getBirthday())
                    && passportID.equals(personObj.getPassportID()));
        }
        return false;
    }
}
