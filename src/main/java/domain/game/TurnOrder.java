package domain.game;

import java.util.*;

public class TurnOrder {
  private int numPlayers;
  private PlayerID activePlayerID;
  private final List<PlayerID> turnOrder;
  private final Map<PlayerID, Integer> numTurnsPerPlayer;

  public TurnOrder(int numPlayers) {
    this.numPlayers = numPlayers;
    this.numTurnsPerPlayer = new HashMap<PlayerID, Integer>();
    this.turnOrder = new ArrayList<PlayerID>();

    int numPlayersLeft = numPlayers;
    for (PlayerID playerID : PlayerID.values()) {
      if (numPlayersLeft == 0) {
        break;
      }

      turnOrder.add(playerID);
      numTurnsPerPlayer.put(playerID, 1);
      numPlayersLeft--;
    }

    // Randomly assigns turn order
    Collections.shuffle(this.turnOrder);
    this.activePlayerID = turnOrder.get(0);
  }

  void startTurn() {

  }

  private void endTurn() {

  }
}
