package commands;

import data.*;
import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;


import java.io.IOException;
import java.util.ArrayList;


/**
 * Command 'remove_greater'. Removes the elements by greater price.
 */

public class RemoveGreater extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveGreater(CollectionManager collectionManager) {
        super("remove_greater price", "удалить элемент из коллекции больше по price");
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
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Double price = Double.parseDouble(argument);
            ArrayList<Integer> keys= new ArrayList<>();
            for (Product product: collectionManager.getCollection().values()) {
                if (product.getPrice()>price)
                {
                   keys.add(product.getId());
                };

            }
            for (Integer index: keys) {
                collectionManager.removeFromCollection(collectionManager.getById(index));
            }
            putInBuffer("Продукты успешно удалены!");
            System.out.println("Продукты успешно удалены!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            System.out.println("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            System.out.println("Price должен быть представлен числом!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
