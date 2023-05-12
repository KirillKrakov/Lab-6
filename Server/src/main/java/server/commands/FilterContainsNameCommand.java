package server.commands;

import common.data.LabWork;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongCommandArgumentException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Класс, представляющий команду filter_contains_name, выводящую элементы, значение поля name которых содержит заданную подстроку
 */
public class FilterContainsNameCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public FilterContainsNameCommand(CollectionManager collectionManager) {
        super("filter_contains_name", "<name>", "вывести элементы, значение поля name которых содержит заданную подстроку");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод выводит элементы, значение поля name которых содержит заданную подстроку
     * @param stringArgument, objectArgument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if(stringArgument.isEmpty() || objectArgument != null) throw new WrongCommandArgumentException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int k = 0;
            ResponseOutputer.appendln("Элементы, содержащие данную подстроку: ");
            for (LabWork labWork : collectionManager.getCollection()) {
                if (labWork.getName().contains(stringArgument)) {
                    ResponseOutputer.appendln(labWork);
                    k += 1;
                }
                if (k >= 12) {
                    break;
                }
            }
            if (k == 0) ResponseOutputer.appendln("В коллекции нет подходящих элементов!");
            return true;
        } catch (WrongCommandArgumentException ex) {
            ResponseOutputer.appenderror("В аргументе данной команды должно содержаться название элемента!");
        } catch (CollectionIsEmptyException ex) {
            ResponseOutputer.appenderror("В коллекции не содержится элементов!");
        }
        return false;
    }
}
