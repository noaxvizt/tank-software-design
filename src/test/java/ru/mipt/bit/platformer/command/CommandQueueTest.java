package ru.mipt.bit.platformer.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandQueueTest {

    @Test
    public void shouldExecuteAndRemoveCommands() {
        int[] calls = {0};
        CommandQueue commandQueue = new CommandQueue();
        commandQueue.add(() -> calls[0]++);
        commandQueue.add(() -> calls[0]++);

        commandQueue.executeAll();

        assertEquals(2, calls[0]);
        assertEquals(0, commandQueue.size());
    }
}
