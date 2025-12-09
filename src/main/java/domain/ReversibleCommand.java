package domain;

public abstract class ReversibleCommand implements Command {
  public void execute() {
  }

  void undo() {
  }
}
