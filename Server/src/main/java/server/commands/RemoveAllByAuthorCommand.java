package server.commands;

import common.data.Person;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongCommandArgumentException;
import common.communication.AuthorForRequest;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;


/**
 * Класс, представляет команду remove_all_by_author, которая удаляет из коллекции все элементы, значение поля author которого эквивалентно заданному
 */
public class RemoveAllByAuthorCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public RemoveAllByAuthorCommand(CollectionManager collectionManager) {
        super("remove_all_by_author", "<author>", "удалить из коллекции все элементы, значение поля author которого эквивалентно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод удаляет из коллекции все элементы, значение поля author которого эквивалентно заданному
     * @param argument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if(!stringArgument.isEmpty() || objectArgument == null) throw new WrongCommandArgumentException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            AuthorForRequest authorForRequest = (AuthorForRequest) objectArgument;
            Person checkingAuthor = new Person(
                    authorForRequest.getName(),
                    authorForRequest.getBirthday(),
                    authorForRequest.getPassportID()
            );
            collectionManager.removeAllByAuthor(checkingAuthor);
            ResponseOutputer.appendln("Элементы, имеющие такого же автора, успешно удалены!");
            return true;
        } catch (WrongCommandArgumentException ex) {
            ResponseOutputer.appenderror("Аргумент этой команды должен быть пустым!");
        } catch (CollectionIsEmptyException ex) {
            ResponseOutputer.appenderror("В коллекции нет элементов!");
        }
        return false;
    }
}
