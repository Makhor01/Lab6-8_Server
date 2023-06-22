import commands.*;
import data.Message;
import utility.CollectionManager;
import utility.CommandManager;
import utility.FileManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {






    public static void main(String[] args) throws IOException {
        Socket clientSocket; //сокет для общения
        ServerSocket server; // серверсокет

        final String envVariable = "LABA";
        FileManager fileManager = new FileManager(envVariable);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new InsertCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveLower(collectionManager),
                new RemoveGreater(collectionManager),
                new RemoveKey(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ExitCommand(),
                new ExecuteScriptCommand(),
                new RemoveKey(collectionManager),
                new SortCommand(collectionManager),
                new HistoryCommand(),
                //new SumOfTransferredStudentsCommand(collectionManager),
                new MinById(collectionManager),
                new CountGreaterThan(collectionManager),
                new SummOfPrice(collectionManager)
        );
        fileManager.readCollection();

        boolean criticalExeption = false;
        while (!criticalExeption ){
        try {
            server = new ServerSocket(1234); // серверсокет прослушивает порт 4004
            System.out.println("Сервер запущен!"); // хорошо бы серверу
            //   объявить о своем запуске
            clientSocket = server.accept(); // accept() будет ждать пока
            //кто-нибудь не захочет подключиться
            try {

                try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                    // к созданию потоков ввода/вывода.
                    // теперь мы можем принимать сообщения
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    Message message = (Message) in.readObject();

                    System.out.println(message.getText()); // "Hello"
                    System.out.println(message.getValue()); // 42
                   // System.out.println(message.getProduct());
                    try (FileOutputStream fos = new FileOutputStream("buffer.txt");
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(message.getProduct());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ConsoleSave consoleInput = new ConsoleSave(commandManager);
                    Thread thread = new Thread(consoleInput);
                    thread.start();


                    switch (message.getText()) {
                        case "":
                            break;
                        case "help":
                            message.setText(String.valueOf(commandManager.help(message.getValue())));
                            break;
                        case "info":
                            message.setText(String.valueOf(commandManager.info(message.getValue())));
                            break;
                        case "show":
                            message.setText(String.valueOf(commandManager.show(message.getValue())));
                            break;
                        case "insert":
                            message.setText(String.valueOf(commandManager.insert(message.getValue())));
                            break;
                        case "update":
                            message.setText(String.valueOf(commandManager.update(message.getValue())));
                            break;
                        case "remove_greater":
                            message.setText(String.valueOf(commandManager.removeGreater(message.getValue())));
                            break;
                        case "remove_lower":
                            message.setText(String.valueOf(commandManager.removeLower(message.getValue())));
                            break;
                        case "clear":
                            message.setText(String.valueOf(commandManager.clear(message.getValue())));
                            break;
                        case "save":
                            message.setText(String.valueOf(commandManager.save(message.getValue())));
                            break;
                        case "execute_script":
                            message.setText(String.valueOf(commandManager.executeScript(message.getValue())));
                        case "remove_key":
                            message.setText(String.valueOf(commandManager.removeKey(message.getValue())));
                            break;
                        case "sort":
                            message.setText(String.valueOf(commandManager.sort(message.getValue())));
                            break;
                        case "history":
                            message.setText(String.valueOf(commandManager.history(message.getValue())));
                            break;
                        case "summ_of_price":
                            message.setText(String.valueOf(commandManager.summOfPrice(message.getValue())));
                            break;
                        case "min_by_id":
                            message.setText(String.valueOf(commandManager.minBySemesterEnum(message.getValue())));
                            break;
                        case "count_greater_than_unit_of_measure":
                            message.setText(String.valueOf(commandManager.counterGreaterThan(message.getValue())));
                            break;
                        case "exit":
                            message.setText(String.valueOf(commandManager.exit(message.getValue())));
                        default:
                            message.setText(String.valueOf(commandManager.noSuchCommand(message.getValue())));
                    }
                    String obj = null;
                    try {
                        FileReader reader = new FileReader("file.txt");
                        int c;
                        StringBuilder data = new StringBuilder();

                        while ((c = reader.read()) != -1) {
                            data.append((char) c);
                        }

                        reader.close();

                        obj = data.toString();

                    } catch (IOException e) {
                        System.out.println("Ошибка чтения файла: " + e.getMessage());
                    }


                    Message response = new Message(message.getText(), obj, null);
                    out.writeObject(response);
                    out.flush();

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    }
}