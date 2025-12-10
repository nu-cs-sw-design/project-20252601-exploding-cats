package domain;

import java.util.ArrayList;
import java.util.List;

public class GameInvoker {
  private final List<Command> commandHistory;
  private final List<Command> nopedCommandHistory;

  public GameInvoker() {
    commandHistory = new ArrayList<Command>();
    nopedCommandHistory = new ArrayList<Command>();
  }

  public void addCommand(Command command) {
    if (isNope(command)) {
      handleNope();
      return;
    }

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
    // Nothing to nope
    if (commandHistory.isEmpty()) {
      return;
    }

    // Noping a Nope
    int lastCommandIndex = commandHistory.size() - 1;
    if (!nopedCommandHistory.isEmpty()) {
      Command resurrectedCommand = nopedCommandHistory.remove(lastCommandIndex);

      commandHistory.add(resurrectedCommand);
      return;
    }

    // Standard, single Nope
    Command lastCommand = commandHistory.get(lastCommandIndex);
    if (lastCommand.isIrreversible()) {
      throw new UnsupportedOperationException("Cannot nope an irreversible command.");
    }

    commandHistory.remove(lastCommandIndex);
    nopedCommandHistory.add(lastCommand);
  }

  private boolean isNope(Command command) {
    return (command instanceof domain.NopeCommand);
  }
}
