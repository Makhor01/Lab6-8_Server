package commands;

import data.Product;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * Command 'add'. Adds a new element to collection.
 */

public class InsertCommand extends AbstractCommand {

    private CollectionManager collectionManager;



    public InsertCommand(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
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
            Product obj = null;
            try (FileInputStream fis = new FileInputStream("buffer.txt");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                obj = (Product) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(obj);
            System.out.println( collectionManager.collectionSize());
            collectionManager.addToCollection(obj);
            System.out.println( collectionManager.collectionSize());
            System.out.println("Продукт успешно добавлен!");
            putInBuffer("Продукт успешно добавлен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
