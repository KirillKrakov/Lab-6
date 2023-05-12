package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Класс, представляющий команду execute_script, считывающий и исполняющий скрипт из указанного файла
 */
public class ExecuteScriptCommand extends AbstractCommand {
    public ExecuteScriptCommand() {
        super("execute_script", "<file_name>", "исполнить скрипт из указанного файла");
    }

    /**
     * Метод проверяет наличие скрипта по указанному адресу. Само чтение и выполнение скрипта реализовано в ConsoleManager
     * @param stringArgument,objectArgument
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("В аргументе командной строки должен быть указан адрес файла со скриптом");
        }
        return false;
    }
}
