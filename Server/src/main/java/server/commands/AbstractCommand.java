package server.commands;

/**
 * Класс абстрактной команды, содержащий общие для всех команд методы, их название и описание
 */
public abstract class AbstractCommand implements Command {
    private String name;
    private String usage;
    private String description;

    public AbstractCommand(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }

    /**
     * Метод возвращает название команды
     * @return command_name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return Usage of the command.
     */
    @Override
    public String getUsage() {
        return usage;
    }

    /**
     * Метод возвращает описание команды
     * @return command_description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Метод испольняет команду
     * @param commandStringArgument
     */
    @Override
    public abstract boolean execute(String commandStringArgument, Object commandObjectArgument);

    @Override
    public String toString() {
        return name + " " + usage + " (" + description + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + usage.hashCode() + description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.getName()) && usage.equals(other.getUsage()) &&
                description.equals(other.getDescription());
    }
}