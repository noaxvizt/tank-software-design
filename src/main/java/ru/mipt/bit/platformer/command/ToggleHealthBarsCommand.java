package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.graphics.HealthBarDisplay;

public class ToggleHealthBarsCommand implements Command {

    private final HealthBarDisplay healthBarDisplay;

    public ToggleHealthBarsCommand(HealthBarDisplay healthBarDisplay) {
        this.healthBarDisplay = healthBarDisplay;
    }

    @Override
    public void execute() {
        healthBarDisplay.toggleHealthBars();
    }
}
