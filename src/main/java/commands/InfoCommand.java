package commands;

import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;


import java.io.IOException;
import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
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
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            System.out.println("Сведения о коллекции:");
            System.out.println(" Тип: " + collectionManager.collectionType());
            System.out.println(" Количество элементов: " + collectionManager.collectionSize());
            System.out.println(" Дата последнего сохранения: " + lastSaveTimeString);
            System.out.println(" Дата последней инициализации: " + lastInitTimeString);
            putInBuffer("Сведения о коллекции:"+'\n'+" Тип: " + collectionManager.collectionType() +'\n'+
                    " Количество элементов: " + collectionManager.collectionSize()+'\n'+
                    " Дата последнего сохранения: " + lastSaveTimeString+'\n'+
                    " Дата последней инициализации: " + lastInitTimeString);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
