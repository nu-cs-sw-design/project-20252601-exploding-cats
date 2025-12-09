package domain.game;

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
    if (isIrreversible(command)) {
      commandHistory.add(command);
      return;
    }

    if (isNope(command)) {
      this.undoLastCommand();
      return;
    }

    commandHistory.add(command);
  }

  public void executeCommands() {
    for (Command command : commandHistory) {
      command.execute();
    }

    commandHistory.clear();
    nopedCommandHistory.clear();
  }

  private boolean isIrreversible(Command command) {
    return (command instanceof IrreversibleCommand);
  }

  private boolean isNope(Command command) {
    return (command instanceof NopeCommand);
  }

  private void undoLastCommand() {
    int lastCommandIndex = commandHistory.size() - 1;
    Command lastCommand = commandHistory.get(lastCommandIndex);

    if (isNope(lastCommand)) {
      redoLastCommand();
    }
    else {
      commandHistory.remove(lastCommandIndex);
      nopedCommandHistory.add(lastCommand);
    }
  }

  private void redoLastCommand() {
    int lastNopedCommandIndex = nopedCommandHistory.size() - 1;
    Command lastNopedCommand = nopedCommandHistory.remove(lastNopedCommandIndex);
    commandHistory.add(lastNopedCommand);
  }
}
