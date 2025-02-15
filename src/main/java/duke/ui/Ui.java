package duke.ui;

import java.util.ArrayList;
import java.util.HashMap;

import duke.command.Command;
import duke.command.CommandKeyword;
import duke.exception.DukeException;
import duke.exception.InvalidCommandException;
import duke.gui.DialogBox;
import duke.task.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Deals with interactions with the user.
 */
public class Ui {
    @FXML
    private VBox dialogContainer;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/mrbean.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/saitama.png"));
    private HashMap<String, CommandKeyword> listOfCommands;

    public Ui(VBox dialogContainer) {
        this.dialogContainer = dialogContainer;
    }

    private void formatMessage(String message, boolean isErrorMessage) {
        String formattedMessage = String.format("%s", message.replaceAll("\n", "\n\t"));
        DialogBox dialogBox = DialogBox.getDukeDialog(formattedMessage, dukeImage);
        dialogBox.setName("Saitama");
        if (isErrorMessage) {
            dialogBox.setBackgroundColor("ff9999");
        }
        dialogContainer.getChildren().add(dialogBox);
    }

    /**
     * Shows the input as a dialog in the GUI.
     *
     * @param input The input to be shown.
     */
    public void showInput(String input) {
        DialogBox dialogBox = DialogBox.getUserDialog(input, userImage);
        dialogBox.setUserId();
        dialogBox.setName("Me");
        dialogContainer.getChildren().add(dialogBox);
    }

    /**
     * Displays a welcome message to user when Duke starts.
     */
    public void greetUser() {
        String greetMessage = "Hello! I'm Saitama\nI do 100 sit-ups, 100 push-ups,"
                + " 100 squats \nand a 10 kilometer run every day! No cap";
        this.formatMessage(greetMessage, false);
    }

    /**
     * Displays an error message to user according to the exception.
     *
     * @param e An exception thrown due to various reasons such as incorrect user command.
     */
    public void showError(DukeException e) {
        this.formatMessage(e.getMessage(), true);
    }

    /**
     * Displays a farewell message to user before ending the program.
     */
    public void showFarewell() {
        this.formatMessage("Hope to see you again!! ^_^", false);
    }

    /**
     * Displays a message to user if there is an error loading tasks from the file.
     */
    public void showLoadingError() {
        this.formatMessage("There is an error while loading tasks or commands.", true);
    }

    /**
     * Reads user command and create a command object to represent it.
     *
     * @return A command object that consists of keyword and rest of the command.
     * @throws IllegalArgumentException If the command keyword is invalid.
     */
    public Command readCommand(String input) throws InvalidCommandException {
        String[] stringArr = input.trim().split(" ", 2);
        String commandName = stringArr[0].toUpperCase();
        CommandKeyword keyword = this.listOfCommands.get(commandName);
        if (keyword == null) {
            throw new InvalidCommandException();
        }
        String restOfCommand = stringArr.length > 1 ? stringArr[1] : "";
        return new Command(keyword, restOfCommand);
    }

    /**
     * Diplays a message to user after user successfully added a task.
     *
     * @param task Task that is added.
     * @param totalTasks Total number of tasks in the list after the task is added.
     */
    public void showAddTask(Task task, int totalTasks) {
        this.formatMessage(String.format("Got it. I've added this task:"
                + "\n\t%s"
                + "\nNow you have %d tasks in the list.", task, totalTasks), false);
    }

    /**
     * Displays to user the list of tasks.
     *
     * @param tasks The list of tasks.
     */
    public void showTasks(ArrayList<Task> tasks) {
        int len = tasks.size();
        if (len == 0) {
            this.formatMessage("No task is found!", true);
        } else {
            boolean isMoreThanOne = len > 1;
            String isOrAre = isMoreThanOne ? "are" : "is";
            String taskName = isMoreThanOne ? "tasks" : "task";
            String message = String.format("Here %s the matching %s in your list:\n", isOrAre, taskName);
            for (int i = 0; i < len; i++) {
                int num = i + 1;
                Task task = tasks.get(i);
                message += String.format("%d.%s\n", num, task);
            }
            this.formatMessage(message, false);
        }
    }

    /**
     * Displays a message to user after user try to mark a task.
     * Message displayed depends on the successfulness of the mark operation.
     *
     * @param task The task to mark.
     */
    public void showMarkedTask(Task task) {
        if (task != null) {
            this.formatMessage(String.format("Nice! I've marked this task as done: \n\t%s", task), false);
        } else {
            this.formatMessage("There is no such task to mark!", true);
        }
    }

    /**
     * Displays a message to user after user try to delete a task.
     * Message displayed depends on the successfulness of the delete operation.
     *
     * @param task The task to delete.
     * @param totalTasks The total number of tasks remaining.
     */
    public void showDeletedTask(Task task, int totalTasks) {
        if (task != null) {
            this.formatMessage(String.format("Noted. I've removed this task: \n\t%s\n"
                    + "Now you have %d tasks in the list.", task, totalTasks), false);
        } else {
            this.formatMessage("There is no such task to delete!", true);
        }
    }

    /**
     * Displays a message to user if user did not include keywored when using the command find.
     */
    public void showNoKeyword() {
        this.formatMessage("There is no keyword to search for!", true);
    }

    /**
     * Displays the filtered tasks as a message to user based on the search.
     *
     * @param tasks The arraylist of filtered tasks.
     */
    public void showFilteredTasks(ArrayList<Task> tasks) {
        this.showTasks(tasks);
    }

    public void setListOfCommands(HashMap<String, CommandKeyword> listOfCommands) {
        this.listOfCommands = listOfCommands;
    }
}
