package commands;

import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.*;

import java.io.IOException;

/**
 * Command 'clear'. Clears the collection.
 */

public class ClearCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
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
            collectionManager.clearCollection();
            System.out.println("Коллекция очищена!");
            putInBuffer("Коллекция очищена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
