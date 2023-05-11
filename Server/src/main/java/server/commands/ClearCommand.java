package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Класс, представляющий команду clear, очищяющую коллекцию
 */
public class ClearCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод очищает коллекцию, удаляя из неё все элементы
     * @param stringArgument,objectArgument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            collectionManager.clearCollection();
            ResponseOutputer.appendln("Коллекция очищена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Аргумент этой команды должен быть пустым!");
        }
        return false;
    }
}