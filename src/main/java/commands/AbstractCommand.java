package commands;


import java.io.*;

/**
 * Abstract Command class contains Object methods, name and description.
 */

public abstract class AbstractCommand implements Command{
    private String name;

    private String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return Name and usage way of the command.
     */

    public String getName() {return name;}

    /**
     * @return Description of the command.
     */

    public String geDescription() {return description;}

    @Override
    public String toString() {return name + " (" + description + " )";}

    @Override
    public int hashCode() {return name.hashCode() + description.hashCode();}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return  true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return  false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.name) && description.equals(other.description);
    }
    public void putInBuffer(String str) throws IOException {
        try {
            FileWriter writer = new FileWriter("file.txt");
            writer.write(str);
            writer.close();
            System.out.println("Запись выполнена успешно.");
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

}
