package common.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Класс, представляющий координаты
 */
@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.NONE)
public class Coordinates implements Serializable {
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
    public String toString2() {
        return x + "~0~" + y;
    }
    public static Coordinates outOfString(String arg) {
        String[] s = arg.split("~0~");
        return new Coordinates(Double.parseDouble(s[0]), Long.parseLong(s[1]));
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

    public byte[] getBytes() {
        byte[]a = x.toString().getBytes();
        byte[]b = y.toString().getBytes();
        byte[]c = new byte[a.length+b.length];
        int count = 0;
        for(int i = 0; i<a.length; i++) {
            c[i] = a[i];
            count++;
        }
        for(int j = 0;j<b.length;j++) {
            c[count++] = b[j];
        }
        return c;
    }


}
