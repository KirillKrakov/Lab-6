package common.interaction;

import common.data.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LabWorkRaw implements Serializable{
    private String name;
    private Coordinates coordinates;
    private int minimalPoint;
    private Float personalQualitiesMinimum;
    private long averagePoint;
    private Difficulty difficulty;
    private Person author;

    public LabWorkRaw(String name, Coordinates coordinates, int minimalPoint,
                      Float personalQualitiesMinimum, long averagePoint,
                   Difficulty difficulty, Person author) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.personalQualitiesMinimum = personalQualitiesMinimum;
        this.averagePoint = averagePoint;
        this.difficulty = difficulty;
        this.author = author;
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

    @Override
    public String toString() {
        return "'Сырая' лабораторная работа"+ "\nНазвание: " + name + "\n" + coordinates.toString()
                + "\nМинимальный балл: " + minimalPoint + "\nМинимум персональных качеств: "
                + personalQualitiesMinimum + "\nСредний балл: " + averagePoint
                + "\nСложность: " + difficulty.getTitle() + "\n" + author.toString();
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() + minimalPoint
                + personalQualitiesMinimum.hashCode() + (int) averagePoint
                + difficulty.hashCode() + author.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof LabWork) {
            LabWork labWorkObj = (LabWork) obj;
            return (name.equals(labWorkObj.getName()) && coordinates.equals(labWorkObj.getCoordinates()) &&
                    (minimalPoint == labWorkObj.getMinimalPoint()) && personalQualitiesMinimum.equals(labWorkObj.getPersonalQualitiesMinimum()) &&
                    (averagePoint == labWorkObj.getAveragePoint()) && difficulty.equals(labWorkObj.getDifficulty()) && author.equals(labWorkObj.getAuthor()));
        }
        return false;
    }
}
