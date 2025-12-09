package presentation;

import java.util.ResourceBundle;
import java.util.Scanner;
import domain.PlayerID;

class InputReader {
  private final ResourceBundle messages;
  private final Scanner scanner;
  private int numPlayers;

  InputReader(ResourceBundle messages, Scanner scanner) {
    this.messages = messages;
    this.scanner = scanner;
  }

  ResourceBundle promptForLanguage() {

  }

  int promptForNumPlayers() {

  }

  PlayerID promptForCardIndex() {

  }

  boolean promptForTurnEnd() {

  }

  boolean promptForNope() {

  }
}
