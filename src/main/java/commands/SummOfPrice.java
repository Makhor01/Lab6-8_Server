package commands;


import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;

import java.io.IOException;


public class SummOfPrice extends AbstractCommand{
    private CollectionManager collectionManager;

    public SummOfPrice(CollectionManager collectionManager) {
        super("summ", "выводит сумму цен всех продуктов");
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
            System.out.println(collectionManager.getSumOfPrice());
            putInBuffer(String.valueOf(collectionManager.getSumOfPrice()));
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование не удалось: '" + getName() + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
