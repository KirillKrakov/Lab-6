package common.data;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, использующийся для адаптации значения времени в XML-файле или коллекции под формат LOCAL_DATE_TIME
 */
public class DateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Метод возвращает адаптированное строковое представление даты времени
     * @param v - дата и время из XML-файла или коллекции
     * @return date_time_string - строковое представление даты и времени
     */
    @Override
    public String marshal(LocalDateTime v) {
        return v.format(ISO_FORMATTER);
    }

    /**
     * Метод возвращает адаптированное в формат LOCAL_DATE_TIME дату и время
     * @param v - строковое представление даты и времени из XML-файла или коллекции
     * @return date_time - дата и время в формате LOCAL_DATE_TIME
     */
    public LocalDateTime unmarshal(String v) throws ParseException{
        return LocalDateTime.parse(v);
    }
}
