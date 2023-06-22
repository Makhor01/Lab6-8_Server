package commands;


import exceptions.WrongAmountOfElementsException;


/**
 * Command 'execute_script'. Executes scripts from a file. Actually only checks argument and prints messages.
 */

public class ExecuteScriptCommand extends AbstractCommand{

    public ExecuteScriptCommand() {super("execute_script <file_name>", "исполнить скрипт из указанного файла");}

    @Override
    public String getDescription() {
        return null;
    }

    /**
     * Executes the command, but partially.
     * @return Command exit status.
     */

    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            System.out.println("Выполняю скрипт '" + argument + "'...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        }
        return false;
    }
}
