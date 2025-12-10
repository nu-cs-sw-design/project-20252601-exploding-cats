package domain;

import java.util.*;

public class TurnOrder {
  private PlayerID activePlayerID;
  private final List<PlayerID> turnOrder;

  public TurnOrder(int numPlayers) {
    this.turnOrder = new ArrayList<PlayerID>();

    int numPlayersLeft = numPlayers;
    for (PlayerID playerID : PlayerID.values()) {
      if (numPlayersLeft == 0) {
        break;
      }

      turnOrder.add(playerID);
      numPlayersLeft--;
    }

    // Randomly assigns turn order
    Collections.shuffle(this.turnOrder);
    this.activePlayerID = turnOrder.get(0);
  }

  void playExplode() {
    turnOrder.remove(activePlayerID);
    activePlayerID = turnOrder.get(0);
  }

  void nextPlayerTurn() {
    turnOrder.remove(activePlayerID);
    turnOrder.add(activePlayerID);
    activePlayerID = turnOrder.get(0);
  }

  PlayerID getCurrentPlayer() {
    return activePlayerID;
  }

  int checkNumberOfAlivePlayers() {
    return turnOrder.size();
  }
}
