package presentation;

import domain.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    // Setting up the game UI
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    InputReader inputReader = new InputReader(scanner);
    inputReader.promptAndSetLanguage();
    int numPlayers = inputReader.promptForNumPlayers();

    // Setting up the game domain
    Deck deck = new Deck(numPlayers);
    GameLoader gameLoader = new GameLoader(numPlayers, deck);
    TurnOrder turnOrder = new TurnOrder(numPlayers);
    Map<PlayerID, PlayerHand> playerHands = gameLoader.instantiatePlayerHands();
    SimpleCardCommandFactory commandFactory = new SimpleCardCommandFactory(deck, turnOrder, playerHands);
    GameInvoker gameInvoker = new GameInvoker();
    GameModel gameModel = new GameModel(deck, turnOrder, playerHands);

    // Starting game
    GameDurationUI gameDurationUI = new GameDurationUI(inputReader, commandFactory, gameInvoker, gameModel);
    gameDurationUI.runGameLoop();
  }
}
