package common.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс, представляющий координаты
 */
@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.NONE)
public class Coordinates {
    @XmlElement
    private Double x;
    @XmlElement
    private Long y;
    public Coordinates(){}
    public Coordinates(Double x, Long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод возвращает координату X
     * @return x
     */
    public Double getX() {
        return x;
    }

    /**
     * Метод возвращает координату Y
     * @return y
     */
    public Long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Координаты: \nКоордината X: " + x + "\nКоордината Y: " + y;
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates){
            Coordinates coordObj = (Coordinates) obj;
            return x.equals(coordObj.getX()) && y.equals(coordObj.getY());
        }
        return false;
    }
}
