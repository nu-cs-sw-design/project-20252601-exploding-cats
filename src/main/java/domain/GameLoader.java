package domain;

import java.util.HashMap;
import java.util.Map;

public class GameLoader {
  private final int numPlayers;
  private Deck deck;
  private TurnOrder turnOrder;
  private Map<PlayerID, PlayerHand> playerHands;
  private SimpleCardCommandFactory commandFactory;
  private GameInvoker gameInvoker;

  public GameLoader(int numPlayers) {
    this.numPlayers = numPlayers;
  }

  public void setUpGame() {
    this.instantiateDeck();
    this.instantiateTurnOrder();
    this.instantiatePlayerHands();
    this.instantiateCommandFactory();
    this.instantiateGameInvoker();
  }

  private void instantiateDeck() {
    this.deck = new Deck(numPlayers);
  }

  private void instantiateTurnOrder() {
    this.turnOrder = new TurnOrder(numPlayers);
  }

  private void instantiatePlayerHands() {
    this.playerHands = new HashMap<PlayerID, PlayerHand>();
    int numPlayersLeft = numPlayers;

    for (PlayerID playerID : PlayerID.values()) {
      if (numPlayersLeft == 0) {
        break;
      }

      PlayerHand playerHand = new PlayerHand(playerID);
      this.playerHands.put(playerID, playerHand);
      numPlayersLeft--;
    }
  }

  public SimpleCardCommandFactory instantiateCommandFactory() {
    SimpleCardCommandFactory factory = new SimpleCardCommandFactory(deck, turnOrder, playerHands);
    this.commandFactory = factory;
    return factory;
  }

  public GameInvoker instantiateGameInvoker() {
    GameInvoker gameInvoker = new GameInvoker();
    this.gameInvoker = gameInvoker;
    return gameInvoker;
  }
}
