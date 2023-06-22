package commands;

import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;

import java.io.IOException;


/**
 * Command 'show'. Shows information about all elements of the collection.
 */

public class ShowCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести все элементы коллекции");
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
            System.out.println(collectionManager.getCollection());
            putInBuffer(String.valueOf(collectionManager.getCollection()));
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
