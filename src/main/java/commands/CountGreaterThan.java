package commands;


import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;

import java.io.IOException;


public class CountGreaterThan extends AbstractCommand{
    private CollectionManager collectionManager;

    public CountGreaterThan(CollectionManager collectionManager) {
        super("count_greater_than_unit_of_measure", "выводит количества всех продуктов, чья единица измерения больше заданного");
        this.collectionManager = collectionManager;
    }

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
            putInBuffer(collectionManager.countGreaterThan(argument));
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
