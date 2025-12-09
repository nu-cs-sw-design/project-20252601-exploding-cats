package presentation;

import domain.GameLoader;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    InputReader inputReader = new InputReader(scanner);

    // TODO: Change output type of promptForLanguage to void if don't need to give it to GameStartUI
    ResourceBundle messages = inputReader.promptForLanguage();
    int numPlayers = inputReader.promptForNumPlayers();

    GameLoader gameLoader = new GameLoader(numPlayers);
    GameStartUI gameStartUI = new GameStartUI(inputReader, gameLoader);
    gameStartUI.startGame();
  }
}
