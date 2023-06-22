package commands;


import exceptions.WrongAmountOfElementsException;


/**
 * Command 'history'. It's here just for logical structure.
 */

public class HistoryCommand extends AbstractCommand{
    public HistoryCommand() {super("history", "вывести историю использованных команд");}

    @Override
    public String getDescription() {
        return null;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */

    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        }
        return false;
    }
}
