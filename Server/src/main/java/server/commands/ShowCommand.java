package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Класс, предлставляющий команду show, выводящую в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class ShowCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "", "вывести все элементы коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     * @param argument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ResponseOutputer.appendln(collectionManager.toString());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appenderror("Аргумент этой команды должен быть пустым!");
        }
        return false;
    }
}
