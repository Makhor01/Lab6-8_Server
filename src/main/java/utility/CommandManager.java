package utility;

import commands.Command;
import exceptions.HistoryIsEmptyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Operates the commands.
 */

public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 11;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];

    private List<Command> commands = new ArrayList<>();

    private Command helpCommand;

    private Command infoCommand;

    private Command showCommand;

    private Command insertCommand;

    private Command updateCommand;

    private Command removeKey;
    private Command removeLower;
    private Command removeGreater;

    private Command clearCommand;

    private Command saveCommand;

    private Command exitCommand;

    private Command executeScriptCommand;

    private Command historyCommand;

    private Command sumOfTransferredStudentsCommand;

    private Command sortCommand;

    private Command removeAtIndexCommand;

    private Command minById;
    private Command counterGreater;
    private Command summOfPrice;

    private Command groupCountingByCoordinatesCommand;

    public CommandManager(Command infoCommand, Command showCommand, Command insertCommand, Command updateCommand,
                          Command removeLower, Command removeGreate,Command removeKey, Command clearCommand, Command saveCommand, Command exitCommand, Command executeScriptCommand,
                          Command removeAtIndexCommand, Command sortCommand, Command historyCommand,
                          Command minById, Command CounterGreater, Command summOfPrice) {
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.insertCommand = insertCommand;
        this.updateCommand = updateCommand;
        this.removeKey = removeKey;
        this.removeLower = removeLower;
        this.removeGreater = removeGreate;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.removeAtIndexCommand = removeAtIndexCommand;
        this.sortCommand = sortCommand;
        this.historyCommand = historyCommand;
       // this.sumOfTransferredStudentsCommand = sumOfTransferredStudentsCommand;
        this.minById = minById;
        this.counterGreater = CounterGreater;
        this.summOfPrice = summOfPrice;
        //this.groupCountingByCoordinatesCommand = groupCountingByCoordinatesCommand;

        // commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(insertCommand);
        commands.add(updateCommand);
        commands.add(removeKey);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(removeAtIndexCommand);
        commands.add(sortCommand);
        commands.add(historyCommand);
        commands.add(minById);
        commands.add(CounterGreater);
        commands.add(summOfPrice);

    }

    /**
     * @return The command history.
     */

    public String[] getCommandHistory() {return commandHistory;}

    /**
     * @return List of manager's commands.
     */

    public List<Command> getCommands() {return commands;}

    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */

    public void addToHistory(String commandToStore) {

        for (Command command : commands) {
            if (command.getName().split(" ")[0].equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE-1; i>0; i--) {
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints that command is not found.
     * @param command Command, which is not found.
     * @return Command exit status.
     */

    public boolean noSuchCommand(String command) {
        System.out.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
        return false;
    }

    /**
     * Prints info about the all commands.
     * @param argument Its argument.
     * @return Command exit status.
     */

    public boolean help(String argument) {

            System.out.println("help : вывести справку по доступным командам\n" +
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                    "insert null: добавить новый элемент с заданным ключом\n" +
                    "update id: обновить значение элемента коллекции, id которого равен заданному\n" +
                    "remove_key: удалить элемент из коллекции по его ключу\n" +
                    "clear: очистить коллекцию\n" +
                    "save: сохранить коллекцию в файл\n" +
                    "execute_script: считать и исполнить скрипт из указанного файл а. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                    "exit programm: завершить программу (без сохранения в файл)\n" +
                    "remove_greater: удалить из коллекции все элементы, превышающие заданную цену\n" +
                    "remove_lower: удалить из коллекции все элементы, меньшие, чем заданную цену\n" +
                    "remove_greater_key: удалить из коллекции все элементы, ключ которых превышает заданный\n" +
                    "summ_of_price: вывести сумму значений поля price для всех элементов коллекции\n" +
                    "min_by_id: вывести любой объект из коллекции, значение поля id которого является минимальным\n" +
                    "count_greater_than_unit_of_measure unitOfMeasure: вывести количество элементов, значение поля unitOfMeasure которых больше заданного");
            return true;

    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */

    public boolean info(String argument) {
        return infoCommand.execute(argument);
    }

    public boolean show(String argument) {
        return showCommand.execute(argument);
    }

    public boolean insert(String argument) {
        return insertCommand.execute(argument);
    }

    public boolean update(String argument) {
        return updateCommand.execute(argument);
    }

    public boolean removeKey(String argument) {
        return removeKey.execute(argument);
    }

    public boolean clear(String argument) {
        return clearCommand.execute(argument);
    }

    public boolean save(String argument) {
        return saveCommand.execute(argument);
    }

    public boolean exit(String argument) {
        return exitCommand.execute(argument);
    }

    public boolean executeScript(String argument) {
        return executeScriptCommand.execute(argument);
    }

    public boolean sumOfPrice(String argument) {return sumOfTransferredStudentsCommand.execute(argument);}

    public boolean sort(String argument) {return sortCommand.execute(argument);}

    /**
     * Prints the history of used commands.
     * @param argument Its argument.
     * @return Command exit status.
     */

    public boolean history(String argument) {
        if (historyCommand.execute(argument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                System.out.println("Последние использованные команды:");
                for (int i=0; i<commandHistory.length; i++) {
                    if (commandHistory[i] != null) System.out.println(" " + commandHistory[i]);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                System.out.println("Ни одной команды еще не было использовано!");
            }
        }
        return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */

    public boolean removeLower(String argument){return removeLower.execute(argument);}
    public boolean removeGreater(String argument){return removeGreater.execute(argument);}
    public boolean minBySemesterEnum(String argument) {return minById.execute(argument);}
    public boolean summOfPrice(String argument){return summOfPrice.execute(argument); }
    public boolean counterGreaterThan(String argument) {return counterGreater.execute(argument);}

    @Override
    public String toString() {
        return "CommandManager (вспомогательный класс для работы с командами)";
    }

}
