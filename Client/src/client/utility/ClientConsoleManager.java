package client.utility;

import client.ClientApp;
import common.data.*;
import common.exceptions.CommandUsageException;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.ScriptRecursionException;
import common.communication.AuthorForRequest;
import common.communication.LabWorkForRequest;
import common.communication.Request;
import common.communication.ResponseCode;
import common.utility.Outputer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

/**
 * Receives user requests.
 */
public class ClientConsoleManager {
    private final int maxRewriteAttempts = 1;

    private BufferedReader reader;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<BufferedReader> scannerStack = new Stack<>();

    public ClientConsoleManager(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Метод, обрабатывающий ввод с консоли и создающий из него запрос на сервер
     * @param serverResponseCode Последний код ответа на запрос от сервера
     * @return Новый запрос к серверу
     */
    public Request handle(ResponseCode serverResponseCode) throws IOException {
        String userInput;
        String[] userCommand = {""};
        ProcessingCode processingCode = null;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.ERROR))
                        throw new IncorrectInputInScriptException();
                    if (!fileMode()) {
                        Outputer.print(ClientApp.PS1);
                    }
                    userInput = reader.readLine();
                    if (fileMode()) {
                        if (userInput == null) {
                            reader.close();
                            reader = scannerStack.pop();
                            Outputer.print(ClientApp.PS1);
                            Outputer.println("Возвращаюсь из скрипта '" + scriptStack.pop().getName() + "'...");
                            continue;
                        } else if (!userInput.isEmpty()) {
                            Outputer.print(ClientApp.PS1);
                            Outputer.println(userInput);
                        }
                    } else {
                        if (userInput == null) {
                            Outputer.printerror("ctrl d");
                            System.exit(0);
                        }
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (IOException exception) {
                    Outputer.println();
                    Outputer.printerror("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Outputer.printerror("Превышено количество попыток ввода!");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR))
                    throw new IncorrectInputInScriptException();
                switch (processingCode) {
                    case OBJECT:
                        LabWorkForRequest labWorkAddRaw = generateLabWorkAdd();
                        return new Request(userCommand[0], userCommand[1], labWorkAddRaw);
                    case UPDATE_OBJECT:
                        LabWorkForRequest labWorkUpdateRaw = generateLabWorkUpdate();
                        return new Request(userCommand[0], userCommand[1], labWorkUpdateRaw);
                    case AUTHOR_OBJECT:
                        AuthorForRequest authorForRequest = generateAuthorAdd();
                        return new Request(userCommand[0], userCommand[1], authorForRequest);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(reader);
                        scriptStack.push(scriptFile);
                        reader = new BufferedReader(new FileReader(scriptFile));
                        Outputer.println("Выполняю скрипт '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception) {
                Outputer.printerror("Файл со скриптом не найден!");
            } catch (ScriptRecursionException exception) {
                Outputer.printerror("Скрипты не могут вызываться рекурсивно!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            Outputer.printerror("Выполнение скрипта прервано!");
            while (!scannerStack.isEmpty()) {
                reader.close();
                reader = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    /**
     * Обрабатывает вводимую команду
     * @return ProcessingCode - код обработки, показывающий статус вводимой команды
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ProcessingCode.ERROR;
                case "help":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingCode.SCRIPT;
                case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add_if_min":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "remove_lower":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "history":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "remove_all_by_author":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    return ProcessingCode.AUTHOR_OBJECT;
                case "count_less_than_minimal_point":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "filter_contains_name":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<weapon_type>");
                    break;
                default:
                    Outputer.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Outputer.println("Некорректно введена команда: '" + command + "'");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }

    /**
     * Метод, генерирующий новый объект лабораторной работы для передачи его на сервер
     * @return Объект лабораторной работы без автоматически генерируемых полей
     * @throws IncorrectInputInScriptException
     */
    private LabWorkForRequest generateLabWorkAdd() throws IncorrectInputInScriptException {
        LabWorkAsker labWorkAsker = new LabWorkAsker(reader);
        if (fileMode()) labWorkAsker.setFileMode();
        return new LabWorkForRequest(
                labWorkAsker.askName(),
                labWorkAsker.askCoordinates(),
                labWorkAsker.askMinimalPoint(),
                labWorkAsker.askPersonalQualitiesMinimum(),
                labWorkAsker.askAveragePoint(),
                labWorkAsker.askDifficulty(),
                labWorkAsker.askAuthor()
        );
    }
    /**
     * Метод, генерирующий новый объект автора лабораторной работы для передачи его на сервер
     * @return Объект автора лабораторной работы
     * @throws IncorrectInputInScriptException
     */
    private AuthorForRequest generateAuthorAdd() throws IncorrectInputInScriptException {
        LabWorkAsker labWorkAsker = new LabWorkAsker(reader);
        if (fileMode()) labWorkAsker.setFileMode();
        return new AuthorForRequest(
                labWorkAsker.askPersonalName(),
                labWorkAsker.askPersonBirthday(),
                labWorkAsker.askPersonPassportID()
        );
    }
    /**
     * Метод, генерирующий обновлённый объект автора лабораторной работы для передачи его на сервер
     * @return Обновлённый объект лабораторной работы
     * @throws IncorrectInputInScriptException
     */
    private LabWorkForRequest generateLabWorkUpdate() throws IncorrectInputInScriptException {
        LabWorkAsker labWorkAsker = new LabWorkAsker(reader);
        if (fileMode()) labWorkAsker.setFileMode();
        String name = labWorkAsker.askQuestion("Хотите изменить название работы?") ?
                labWorkAsker.askName() : null;
        Coordinates coordinates = labWorkAsker.askQuestion("Хотите изменить координаты работы?") ?
                labWorkAsker.askCoordinates() : new Coordinates((double) -1, (long) -1);
        int minimalPoint = labWorkAsker.askQuestion("Хотите изменить минимальный балл за работу?") ?
                labWorkAsker.askMinimalPoint() : -1;
        Float personalQualitiesMinimum = labWorkAsker.askQuestion("Хотите изменить минимум личностных данных для работы?") ?
                labWorkAsker.askPersonalQualitiesMinimum() : -1;
        long averagePoint = labWorkAsker.askQuestion("Хотите изменить средний балл за работу?") ?
                labWorkAsker.askAveragePoint() : -1;
        Difficulty difficulty = labWorkAsker.askQuestion("Хотите изменить уровень сложности работы?") ?
                labWorkAsker.askDifficulty() : Difficulty.TESTING;
        Person author = labWorkAsker.askQuestion("Хотите изменить автора работы?") ?
                labWorkAsker.askAuthor() : new Person("-", LocalDateTime.parse("0000-01-01t00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME),"-");
        return new LabWorkForRequest(
                name,
                coordinates,
                minimalPoint,
                personalQualitiesMinimum,
                averagePoint,
                difficulty,
                author
        );
    }

    /**
     * Метод, проверяющий находится ли менеджер консоли в режиме обработки файла
     * @return Условие, что менеджер консоли находится в режиме обработки файла
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}

