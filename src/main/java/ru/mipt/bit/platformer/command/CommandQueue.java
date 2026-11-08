package ru.mipt.bit.platformer.command;

import java.util.ArrayList;
import java.util.List;

public class CommandQueue {

    private final List<Command> commands = new ArrayList<>();

    public void add(Command command) {
        commands.add(command);
    }

    public void executeAll() {
        List<Command> commandsToExecute = new ArrayList<>(commands);
        commands.clear();
        for (Command command : commandsToExecute) {
            command.execute();
        }
    }

    public int size() {
        return commands.size();
    }
}
