package server.commands;

import common.data.Coordinates;
import common.data.Difficulty;
import common.data.LabWork;
import common.data.Person;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.LabWorkIsNotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.LabWorkRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Класс, представляющий команду update id, обновляющую значение элемента коллекции, id которого равен заданному
 */
public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", "<ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод обновляет значение элемента коллекции, id которого равен заданному
     * @param stringArgument - заданное значение id
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int id = Integer.parseInt(stringArgument);
            if (id <= 0) throw new NumberFormatException();
            LabWork oldLabWork = collectionManager.getSameId(id);
            if (oldLabWork == null) throw new LabWorkIsNotFoundException();

            LabWorkRaw labWorkRaw = (LabWorkRaw) objectArgument;
            String name = labWorkRaw.getName() == null ? oldLabWork.getName() : labWorkRaw.getName();
            Coordinates coordinates = labWorkRaw.getCoordinates() == null ? oldLabWork.getCoordinates() : labWorkRaw.getCoordinates();
            LocalDateTime creationDate = oldLabWork.getCreationDate();
            int minimalPoint = labWorkRaw.getMinimalPoint() == -1 ? oldLabWork.getMinimalPoint() : labWorkRaw.getMinimalPoint();
            Float personalQualitiesMinimum = labWorkRaw.getPersonalQualitiesMinimum() == null ? oldLabWork.getPersonalQualitiesMinimum() : labWorkRaw.getPersonalQualitiesMinimum();
            long averagePoint = labWorkRaw.getAveragePoint() == -1 ? oldLabWork.getAveragePoint() : labWorkRaw.getAveragePoint();
            Difficulty difficulty = labWorkRaw.getDifficulty() == null ? oldLabWork.getDifficulty() : labWorkRaw.getDifficulty();
            Person author = labWorkRaw.getAuthor() == null ? oldLabWork.getAuthor() : labWorkRaw.getAuthor();

            collectionManager.removeFromCollection(oldLabWork);
            collectionManager.addToCollection(new LabWork(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    minimalPoint,
                    personalQualitiesMinimum,
                    averagePoint,
                    difficulty,
                    author
            ));
            ResponseOutputer.appendln("Элемент коллекции успешно обновлён!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("В аргументе этой команды должен быть указан ID!");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("В этой коллекции нет элементов!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представлен положительным числом!");
        } catch (LabWorkIsNotFoundException exception) {
            ResponseOutputer.appenderror("Элемент с таким ID в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
