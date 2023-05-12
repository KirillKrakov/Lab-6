package common.data;

import javax.xml.bind.annotation.*;
import java.util.TreeSet;

/**
 * Класс, представляющий каталог (коллекцию) из объектов (Лабораторных работ)
 */
@XmlRootElement(name = "labWorkCatalog")
@XmlAccessorType(XmlAccessType.NONE)
public class LabWorkCatalog {
    @XmlElement(name = "labWork")
    private TreeSet<LabWork> labWorkCatalog;
    private String name;

    /**
     * Метод задаёт Каталог из Лабораторных работ
     * @param labWorkCatalog
     */
    public void setLabWorkCatalog(TreeSet<LabWork> labWorkCatalog){
        this.labWorkCatalog = labWorkCatalog;
    }

    /**
     * Метод возвращает Каталог из Лабораторных работ
     * @return labWorkCatalog
     */
    public TreeSet<LabWork> getLabWorkCatalog(){
        return labWorkCatalog;
    }

    /**
     * Метод возвращает имя Каталога
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Метод задаёт имя Каталога
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }
}
