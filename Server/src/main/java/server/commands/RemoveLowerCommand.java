package server.commands;

import common.data.LabWork;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import common.communication.LabWorkForRequest;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Класс, представляющий команду remove_lower, удаляющую из коллекции все элементы, меньшие, чем заданный
 */
public class RemoveLowerCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_greater", "<element>", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод удаляет из коллекции все элементы, меньшие, чем заданный
     * @param stringArgument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            LabWorkForRequest labWorkForRequest = (LabWorkForRequest) objectArgument;
            LabWork comparableLabWork = new LabWork(
                    collectionManager.generateNextId(),
                    labWorkForRequest.getName(),
                    labWorkForRequest.getCoordinates(),
                    LocalDateTime.now(),
                    labWorkForRequest.getMinimalPoint(),
                    labWorkForRequest.getPersonalQualitiesMinimum(),
                    labWorkForRequest.getAveragePoint(),
                    labWorkForRequest.getDifficulty(),
                    labWorkForRequest.getAuthor()
            );
            collectionManager.removeLower(comparableLabWork);
            ResponseOutputer.appendln("Элементы, меньшие заданного, успешно удалены!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Аргумент этой команды должен быть пустым!");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}