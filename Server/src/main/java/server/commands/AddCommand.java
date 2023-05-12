package server.commands;

import common.data.LabWork;
import common.exceptions.WrongCommandArgumentException;
import common.communication.LabWorkForRequest;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Класс, представляющий команду add, которая добавляет новый элемент в коллекцию
 */
public class AddCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "{element}","добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод возвращает используемый командой менеджер коллекций
     * @return collectionManager
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }


    /**
     * Метод добавляет новый элемент в коллекцию
     * @param stringArgument,objectArgument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null)  throw new WrongCommandArgumentException();
            LabWorkForRequest labWorkForRequest = (LabWorkForRequest) objectArgument;
            collectionManager.addToCollection(new LabWork(
                    collectionManager.generateNextId(),
                    labWorkForRequest.getName(),
                    labWorkForRequest.getCoordinates(),
                    LocalDateTime.now(),
                    labWorkForRequest.getMinimalPoint(),
                    labWorkForRequest.getPersonalQualitiesMinimum(),
                    labWorkForRequest.getAveragePoint(),
                    labWorkForRequest.getDifficulty(),
                    labWorkForRequest.getAuthor()
            ));
            ResponseOutputer.appendln("Элемент добавлен в коллекцию");
            return true;
        } catch (WrongCommandArgumentException ex) {
            ResponseOutputer.appenderror("Аргумент этой команды должен быть пустым!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
