package presentation;

import domain.CardType;
import domain.GameInvoker;
import domain.SimpleCardCommandFactory;

public class GameDurationUI {
  private final SimpleCardCommandFactory commandFactory;
  private final GameInvoker gameInvoker;
  private final InputReader inputReader;

  GameDurationUI(InputReader inputReader, SimpleCardCommandFactory commandFactory, GameInvoker gameInvoker) {
    this.inputReader = inputReader;
    this.commandFactory = commandFactory;
    this.gameInvoker = gameInvoker;
  }

  void runGameLoop() {

  }

  void playCard(CardType card) {

  }

  void checkIfGameOver() {

  }

  void endTurn() {

  }

  void endGame() {

  }
}
