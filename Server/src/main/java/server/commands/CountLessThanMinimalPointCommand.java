package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongCommandArgumentException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Класс, представляющий команду count_less_than_minimal_point, выводящую количество элементов, значение поля minimalPoint которых меньше заданного
 */
public class CountLessThanMinimalPointCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public CountLessThanMinimalPointCommand(CollectionManager collection) {
        super("count_less_than_minimal_point", "<minimalPoint>", "вывести количество элементов, значение поля minimalPoint которых меньше заданного");
        this.collectionManager = collection;
    }

    /**
     * Метод выводит количество элементов, значение поля minimalPoint которых меньше заданного
     * @param stringArgument,objectArgument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if(stringArgument.isEmpty() || objectArgument != null) throw new WrongCommandArgumentException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int checkingMinimalPoint = Integer.parseInt(stringArgument);
            int count = collectionManager.countLessThanMinimalPoint(checkingMinimalPoint);
            System.out.println(count);
            ResponseOutputer.appendln("Количество элементов: " + count);
            return  true;
        } catch (WrongCommandArgumentException exc) {
            ResponseOutputer.appenderror("В аргументе данной команды должен быть указан минимальный балл!");
        } catch (CollectionIsEmptyException exc) {
            ResponseOutputer.appenderror("В коллекции нет элементов!");
        } catch (NumberFormatException exc) {
            ResponseOutputer.appenderror("В аргументе команды должно быть указано число формата int!");
        }
        return false;
    }
}
