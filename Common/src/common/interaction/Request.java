package common.interaction;

import java.io.Serializable;

/**
 * Class for get request value.
 */
public class Request implements Serializable {
    private String commandName;
    private String commandStringArgument;
    private Serializable commandObjectArgument;

    public Request(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.commandObjectArgument = commandObjectArgument;
    }

    public Request(String commandName, String commandStringArgument) {
        this(commandName, commandStringArgument, null);
    }

    public Request() {
        this("", "");
    }

    /**
     * @return Command name.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * @return Command string argument.
     */
    public String getCommandStringArgument() {
        return commandStringArgument;
    }

    /**
     * @return Command object argument.
     */
    public Object getCommandObjectArgument() {
        return commandObjectArgument;
    }

    /**
     * @return Is this request empty.
     */
    public boolean isEmpty() {
        return commandName.isEmpty() && commandStringArgument.isEmpty() && commandObjectArgument == null;
    }
    @Override
    public String toString() {
        return "Request[" + commandName + ", " + commandStringArgument + ", " + commandObjectArgument + "]";
    }

    public String getObjectArgumentString(){
        if (commandName.equals("add") || commandName.equals("update") ||
                commandName.equals("add_if_min") || commandName.equals("remove_lower")) {
            LabWorkRaw raw = (LabWorkRaw) commandObjectArgument;
            return raw.toString();
        } else if (commandName.equals("remove_all_by_author")) {
            AuthorRaw raw = (AuthorRaw) commandObjectArgument;
            return raw.toString();
        }
        return null;
    };
    public byte[] getBytes() {
        return (commandName + "~~~" + commandStringArgument + "~~~" + getObjectArgumentString()).getBytes();
    }

    public static Request outOfString(String arg) {
        String[] request = arg.split("~~~");
        switch (request[0]) {
            case ("add"):
            case ("add_if_min"):
            case ("remove_lower"):
                return new Request(request[0], "", LabWorkRaw.outOfString(request[2]));
            case ("update"):
                return new Request(request[0], request[1], LabWorkRaw.outOfString(request[2]));
            case ("remove_all_by_author"):
                return new Request(request[0], "", AuthorRaw.outOfString(request[2]));
            case ("remove_by_id"):
            case ("execute_script"):
            case ("count_less_than_minimal_point"):
            case ("filter_contains_name"):
                return new Request(request[0], request[1], null);
            default:
                return new Request(request[0], "", null);
        }
    }
}