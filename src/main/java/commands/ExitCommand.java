package commands;

import exceptions.WrongAmountOfElementsException;


public class ExitCommand extends AbstractCommand{

    public ExitCommand() {super("exit", "завершить программу (без сохранения в файл)");}

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
