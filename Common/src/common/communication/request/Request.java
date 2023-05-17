package common.communication.request;

import common.communication.AuthorForRequest;
import common.communication.LabWorkForRequest;
import common.communication.request.strategy.GetDataStrategy;

import java.io.Serializable;

/**
 * Class for get request value.
 */
public class Request implements Serializable {
    private String commandName;
    private String commandStringArgument;
    private Serializable commandObjectArgument;
    GetDataStrategy getDataStrategy;

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
            LabWorkForRequest raw = (LabWorkForRequest) commandObjectArgument;
            return raw.toString();
        } else if (commandName.equals("remove_all_by_author")) {
            AuthorForRequest raw = (AuthorForRequest) commandObjectArgument;
            return raw.toString();
        }
        return null;
    };
    public byte[] getBytes() {
        return (commandName + "~~~" + commandStringArgument + "~~~" + getObjectArgumentString()).getBytes();
    }

    public Request getData(String[] request) {
        return getDataStrategy.getData(request);
    }

    public void setGetDataStrategy(GetDataStrategy getDataStrategy) {
        this.getDataStrategy = getDataStrategy;
    }
}