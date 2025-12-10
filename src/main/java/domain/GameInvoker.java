package domain;

import java.util.ArrayList;
import java.util.List;

public class GameInvoker {
  private final List<Command> commandHistory;
  private final List<Command> nopedCommandHistory;

  public GameInvoker() {
    commandHistory = new ArrayList<>();
    nopedCommandHistory = new ArrayList<>();
  }

  public void addCommand(Command command) {
    if (isNope(command)) {
      // Execute the Nope immediately to remove the card from player's deck
      command.execute();
      handleNope();
      return;
    }

    // Standard flow for non-Nope commands
    nopedCommandHistory.clear();
    commandHistory.add(command);
  }

  public void executeCommands() {
    for (Command command : commandHistory) {
      command.execute();
    }

    commandHistory.clear();
    nopedCommandHistory.clear();
  }

  private void handleNope() {
    // Noping a nope
    if (!nopedCommandHistory.isEmpty()) {
      int lastNopedIndex = nopedCommandHistory.size() - 1;
      Command resurrectedCommand = nopedCommandHistory.remove(lastNopedIndex);

      commandHistory.add(resurrectedCommand);
      return;
    }

    // Nothing to Nope
    if (commandHistory.isEmpty()) {
      return;
    }

    // Standard, single Nope (Undo)
    int lastCommandIndex = commandHistory.size() - 1;
    Command lastCommand = commandHistory.get(lastCommandIndex);

    if (lastCommand.isIrreversible()) {
      throw new UnsupportedOperationException("Cannot nope an irreversible command.");
    }

    commandHistory.remove(lastCommandIndex);
    nopedCommandHistory.add(lastCommand);
  }

  private boolean isNope(Command command) {
    return (command instanceof NopeCommand);
  }
}
