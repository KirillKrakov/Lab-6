package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Класс, представляющий команду help, выводящую справку по доступным командам
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "", "вывести справку по доступным командам");
    }

    /**
     * Метод выводит справку по доступным командам
     * @param stringArgument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appenderror("Аргумент команды должен быть пустым!");
        }
        return false;
    }
}
