import utility.CommandManager;

import java.util.Scanner;

public class ConsoleSave implements Runnable {
    private CommandManager commandManager;
    private final Scanner scanner;
    private boolean running;

    public ConsoleSave(CommandManager commandManager) {
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        while (running) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (input.equals("save")){
                    System.out.println("Save command executed");
                    commandManager.save("");
                }
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
