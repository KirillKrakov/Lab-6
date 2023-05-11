package server.commands;

import common.data.LabWork;
import common.exceptions.WrongCommandArgumentException;
import common.interaction.LabWorkRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Класс, представляющий команду add_if_min, добавляющую новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
 */
public class AddIfMinCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min", "{element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     * @param argument
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument == null) throw new WrongCommandArgumentException();
            LabWorkRaw labWorkRaw = (LabWorkRaw) objectArgument;
            LabWork addingLabWork = new LabWork(
                    collectionManager.generateNextId(),
                    labWorkRaw.getName(),
                    labWorkRaw.getCoordinates(),
                    LocalDateTime.now(),
                    labWorkRaw.getMinimalPoint(),
                    labWorkRaw.getPersonalQualitiesMinimum(),
                    labWorkRaw.getAveragePoint(),
                    labWorkRaw.getDifficulty(),
                    labWorkRaw.getAuthor()
            );
            if (collectionManager.collectionSize() == 0 || addingLabWork.compareTo(collectionManager.getFirst()) < 0) {
                collectionManager.addToCollection(addingLabWork);
                ResponseOutputer.appendln("Элемент добавлен в коллекцию");
            } else {
                ResponseOutputer.appendln("Значение элемента не является наименьшим среди элементов коллекции");
            }
            return true;
        } catch (WrongCommandArgumentException ex) {
            ResponseOutputer.appenderror("Аргумент этой команды должен быть пустым!");
        }
        return false;
    }
}
