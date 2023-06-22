package commands;

import data.*;
import exceptions.CollectionIsEmptyException;
import exceptions.IncorrectInputInScriptException;
import exceptions.ProductNotFoundException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;

import java.io.*;


/**
 * Command 'update'. Updates the information about selected group.
 */

public class UpdateCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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
            int id = Integer.parseInt(argument);

            Product previousProduct = collectionManager.getById(id);
            if (previousProduct == null) throw new ProductNotFoundException();


            collectionManager.removeFromCollection(previousProduct);
            FileInputStream fileIn = new FileInputStream("buffer.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));
            try (FileInputStream fis = new FileInputStream("file.bin");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                Product obj = (Product) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            collectionManager.addToCollection(null);

            System.out.println("Product успешно изменен!");
            putInBuffer("Product успешно изменен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            System.out.println("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            System.out.println("ID должен быть представлен числом!");
        } catch (ProductNotFoundException exception) {
            System.out.println("Product с таким ID в коллекции нет!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
