package common.data;

import common.data.DateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

/**
 * Класс, представляющий Лабораторную работу
 */
@XmlRootElement(name = "labWork")
@XmlAccessorType(XmlAccessType.NONE)
public class LabWork implements Comparable<LabWork>{
    @XmlElement
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private Coordinates coordinates;
    @XmlElement
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private LocalDateTime creationDate;
    @XmlElement
    private int minimalPoint;
    @XmlElement
    private Float personalQualitiesMinimum;
    @XmlElement
    private long averagePoint;
    @XmlElement
    private Difficulty difficulty;
    @XmlElement
    private Person author;

    public LabWork(){}
    public LabWork(int id, String name, Coordinates coordinates, LocalDateTime creationDate,
                   int minimalPoint, Float personalQualitiesMinimum, long averagePoint,
                   Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.personalQualitiesMinimum = personalQualitiesMinimum;
        this.averagePoint = averagePoint;
        this.difficulty = difficulty;
        this.author = author;
    }

    /**
     * Метод возвращает id Лабораторной работы
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * Метод возвращает имя Лабораторной работы
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Метод возвращает координаты Лабораторной работы
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод возвращает дату создания Лабораторной работы
     * @return creationDate
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Метод возвращает минимальный балл за Лабораторную работу
     * @return minimalPoint
     */
    public int getMinimalPoint() {
        return minimalPoint;
    }

    /**
     * Метод возвращает необходимый минимальный уровень для Лабораторной работы
     * @return personalQualitiesMinimum
     */
    public Float getPersonalQualitiesMinimum() {
        return personalQualitiesMinimum;
    }

    /**
     * Метод возвращает средний балл за Лабораторную работу
     * @return averagePoint
     */
    public long getAveragePoint() {
        return averagePoint;
    }
    /**
     * Метод возвращает сложность Лабораторной работы
     * @return difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }
    /**
     * Метод возвращает автора Лабораторной работы
     * @return author
     */
    public Person getAuthor() {
        return author;
    }

    /**
     * Метод сравнения двух объектов (Лабораторных работ) между собой
     * @param obj the object to be compared.
     * @return id
     */
    @Override
    public int compareTo(LabWork obj) {
        return (this.id - obj.getId());
    }

    @Override
    public String toString() {
        return "Элемент № " + id + "\nНазвание: " + name + "\n" + coordinates.toString()
                + "\nДата создания: " + creationDate + "\nМинимальный балл: " + minimalPoint
                + "\nМинимум персональных качеств: " + personalQualitiesMinimum + "\nСредний балл: " + averagePoint
                + "\nСложность: " + difficulty.getTitle() + "\n" + author.toString();
    }

    @Override
    public int hashCode() {
        return id + name.hashCode() + coordinates.hashCode() + creationDate.hashCode()
                + minimalPoint + personalQualitiesMinimum.hashCode() + (int) averagePoint
                + difficulty.hashCode() + author.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof LabWork) {
            LabWork labWorkObj = (LabWork) obj;
            return (id == labWorkObj.getId()) && name.equals(labWorkObj.getName()) && coordinates.equals(labWorkObj.getCoordinates()) &&
                    creationDate.equals(labWorkObj.getCreationDate()) && (minimalPoint == labWorkObj.getMinimalPoint()) &&
                    personalQualitiesMinimum.equals(labWorkObj.getPersonalQualitiesMinimum()) && (averagePoint == labWorkObj.getAveragePoint()) &&
                    difficulty.equals(labWorkObj.getDifficulty()) && author.equals(labWorkObj.getAuthor());
        }
        return false;
    }
}
