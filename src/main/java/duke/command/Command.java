package duke.command;

/**
 * Blueprint for a user command. Consists of a keyword and the rest of the command.
 */
public class Command {
    private CommandKeyword keyword;
    private String restOfCommand;

    /**
     * Constructs a user command.
     *
     * @param keyword The user command.
     * @param restOfCommand The rest of the command, if any.
     */
    public Command(CommandKeyword keyword, String restOfCommand) {
        this.keyword = keyword;
        this.restOfCommand = restOfCommand;
    }

    /**
     * Returns the command keyword of a command.
     *
     * @return The command keyword of a command.
     */
    public CommandKeyword getKeyword() {
        return this.keyword;
    }

    /**
     * Returns the rest of the command.
     *
     * @return The rest of the command.
     */
    public String getRestOfCommand() {
        return this.restOfCommand;
    }
}
