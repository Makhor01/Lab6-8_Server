package commands;


import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;

import java.io.IOException;


/**
 * Command 'min_by_semester_enum'. Prints the element of the collection with minimum semester.
 */

public class MinById extends AbstractCommand{
    private CollectionManager collectionManager;

    public MinById(CollectionManager collectionManager) {
        super("min_by_semester_enum", "вывести элемент, значение поля Semester которого минимально");
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
            System.out.println(collectionManager.minById());
            putInBuffer(collectionManager.minById().toString());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            System.out.println("Коллекция пуста!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
