package commands;


import data.*;
import exceptions.CollectionIsEmptyException;
import exceptions.ProductNotFoundException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;

import java.io.IOException;


/**
 * Command 'remove_key'. Removes the element by index.
 */
public class RemoveKey extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveKey(CollectionManager collectionManager) {
        super("remove_key", "удалить элемент из коллекции по key");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int index = Integer.parseInt(argument);
            Product productToRemove = collectionManager.getById(index);
            if (productToRemove == null) throw new ProductNotFoundException();
            collectionManager.removeFromCollection(productToRemove);
            System.out.println("Product успешно удален!");
            putInBuffer("Продукты успешно удалены!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            System.out.println("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            System.out.println("Index должен быть представлен числом!");
        } catch (ProductNotFoundException exception) {
            System.out.println("Product с таким ID в коллекции нет!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}

