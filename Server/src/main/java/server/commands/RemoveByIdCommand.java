package server.commands;

import common.data.LabWork;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.LabWorkIsNotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Класс, представляющий команду remove_by_id, удаляющую элемент из коллекции по его id
 */
public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "<ID>", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод удаляет элемент из коллекции по его id
     * @param stringArgument - заданное значение id
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int id = Integer.parseInt(stringArgument);
            LabWork removingLabWork = collectionManager.getSameId(id);
            if (removingLabWork == null) throw new LabWorkIsNotFoundException();
            collectionManager.removeFromCollection(removingLabWork);
            ResponseOutputer.appendln("Элемент успешно удален из коллекции!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("В аргументе этой команды должен быть указан ID!");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("В этой коллекции нет элементов!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("В аргументе команды должно быть указано число формата Integer!");
        } catch (LabWorkIsNotFoundException exception) {
            ResponseOutputer.appenderror("Элемент с таким ID не найден!");
        }
        return false;
    }
}
