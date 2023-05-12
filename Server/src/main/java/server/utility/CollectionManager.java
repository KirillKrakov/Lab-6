package server.utility;

import common.data.LabWork;
import common.data.Person;

import java.time.LocalDateTime;
import java.util.TreeSet;

/**
 * Класс, управляющий самой коллекцией
 */
public class CollectionManager {
    private TreeSet<LabWork> labWorksCollection;
    private LocalDateTime lastInitialisationTime = null;
    private CollectionFileManager fileManager;

    public CollectionManager(CollectionFileManager fileManager){
        this.fileManager = fileManager;
        this.labWorksCollection = fileManager.fromXMLtoCollection();
        lastInitialisationTime = LocalDateTime.now();
    }

    /**
     * Метод возвращает коллекцию, с которой работает пользователь
     * @return labWorksCollection
     */
    public TreeSet<LabWork> getCollection() {
        return labWorksCollection;
    }

    /**
     * Метод задаёт коллекцию, с которой будет работать пользователь
     * @param collection
     */
    public void setCollection(TreeSet<LabWork> collection) {
        this.labWorksCollection = collection;
    }

    /**
     * Метод возвращает дату и время инициализации коллекции
     * @return lastInitialisationTime
     */
    public LocalDateTime getInitTime() {
        return lastInitialisationTime;
    }

    /**
     * Метод возвращает тип коллекции
     * @return collectionType
     */
    public String collectionType(){
        return labWorksCollection.getClass().getName();
    }

    /**
     * Метод возвращает размер коллекции (количество элементов в нём)
     * @return collectionSize
     */
    public int collectionSize(){
        return labWorksCollection.size();
    }

    /**
     * Метод возвращает первый элемент в коллекции
     * @return first
     */
    public LabWork getFirst(){
        return labWorksCollection.stream().findFirst().orElse(null);
    }

    /**
     * Возвращает элемент коллекции с таким же ID
     * @param id
     * @return labWork
     */
    public LabWork getSameId(int id){
        return labWorksCollection.stream().filter(labWork -> (labWork.getId() ==id)).findFirst().orElse(null);
    }

    /**
     * Метод добавляет элемент в коллекцию
     * @param labWork
     */
    public void addToCollection(LabWork labWork) {
        labWorksCollection.add(labWork);
    }

    /**
     * Метод удаляет элемент из коллекции
     * @param labWork
     */
    public void removeFromCollection(LabWork labWork) {
        labWorksCollection.remove(labWork);
    }

    /**
     * Метод удаляет из коллекции все элементы, меньшие заданного
     * @param comparableLabWork
     */
    public void removeLower(LabWork comparableLabWork){
        labWorksCollection.removeIf(labWork -> labWork.compareTo(comparableLabWork) < 0);
    }

    /**
     * Метод очищает коллекцию (удаляет все элементы)
     */
    public void clearCollection(){
        labWorksCollection.clear();
    }

    /**
     * Метод генерирует значение ID для нового элемента в коллекции
     * @return lastID
     */
    public int generateNextId() {
        if (labWorksCollection.isEmpty()) {
            return 1;
        } else {
            int id = 0;
            for (LabWork labWork : labWorksCollection) {
                if (labWork.getId() > id) {
                    id = labWork.getId();
                }
            }
            return id + 1;
        }
    }

    /**
     * Метод удаляет из коллекции все элементы, имеющие того же автора
     * @param author
     */
    public void removeAllByAuthor(Person author) {
        labWorksCollection.removeIf(labWork -> labWork.getAuthor().equals(author));
    }

    /**
     * Метод возвращает количество элементов, у которых минимальный балл меньше заданного
     * @param point
     * @return
     */
    public int countLessThanMinimalPoint(int point) {
        return (int) labWorksCollection.stream().filter(labWork -> labWork.getMinimalPoint() < point).count();
    }

    /**
     * Метод сохраняет (загружает) коллекцию в XML-файл
     */
    public void saveCollection() {
        fileManager.fromCollectionToXML(labWorksCollection);
    }

    public String filterContainsName(String name) {
        return labWorksCollection.stream().filter(labWork -> labWork.getName().equals(name))
                .reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    /**
     * Метод выводить коллекцию в строковом формате
     * @return CollectionToString
     */
    @Override
    public String toString() {
        if (labWorksCollection.isEmpty()) return "Колекция пуста!";
        String str = "Все элементы коллекции:";
        int x = 0;
        for (LabWork labWork : labWorksCollection) {
            str = str + "\n\n" + labWork;
            x++;
            if (x >= 128) {
                break;
            }
        }
        return str;
    }
}
