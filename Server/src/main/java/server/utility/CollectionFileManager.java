package server.utility;

import common.data.LabWork;
import common.data.LabWorkCatalog;
import common.utility.Outputer;
import server.App;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.TreeSet;

/**
 * Класс, отвечающий за загрузку коллекции в XML-файл и чтению коллекции из XML-файла
 */
public class CollectionFileManager {
    private String envVar;
    public CollectionFileManager(String envVar) {
        this.envVar = envVar;
    }

    /**
     * Метод осуществляет запись коллекции в XML-файл
     * @param collection
     */
    public void fromCollectionToXML(TreeSet<LabWork> collection){
        try {
            var catalog = new LabWorkCatalog();
            catalog.setLabWorkCatalog(collection);
            var context = JAXBContext.newInstance(LabWorkCatalog.class);
            var m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(envVar),"UTF-8");
            m.marshal(catalog, outputStreamWriter);
            ResponseOutputer.appendln("Коллекция успешно сохранена в файл");
        } catch (JAXBException ex) {
            ResponseOutputer.appenderror("Произошла ошибка при загрузке коллекции в файл!");
        } catch (FileNotFoundException ex) {
            ResponseOutputer.appenderror("Файл с таким именем не найден!");
        } catch (UnsupportedEncodingException ex) {
            ResponseOutputer.appenderror("Кодировка символов не поддерживается!");
        }
    }

    /**
     * Метод осуществляет чтение коллекции из XML-файла
     * @return collection.xml
     */
    public TreeSet<LabWork> fromXMLtoCollection() {
        try {
            var context = JAXBContext.newInstance(LabWorkCatalog.class);
            var um = context.createUnmarshaller();
            var catalog = (LabWorkCatalog) um.unmarshal(new BufferedReader(new InputStreamReader(new FileInputStream(envVar), StandardCharsets.UTF_8)));
            Outputer.println("Коллекция успешно загружена");
            App.logger.info("Коллекция успешно загружена.");
            if (catalog.getLabWorkCatalog() == null) {
                return new TreeSet<>();
            }
            return catalog.getLabWorkCatalog();
        } catch (JAXBException ex) {
            Outputer.printerror("Произошла ошибка при чтении xml-файла в коллекцию!");
            App.logger.error("Произошла ошибка при чтении xml-файла в коллекцию!");
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Outputer.printerror("Файл с таким именем не найден!");
            App.logger.warn("Файл с таким именем не найден!");
        }
        return new TreeSet<>();
    }
}
