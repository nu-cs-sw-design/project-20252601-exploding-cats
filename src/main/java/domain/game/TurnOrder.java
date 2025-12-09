package domain.game;

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
    // TODO: add anything more that's needed here later
    turnOrder.remove(activePlayerID);
    activePlayerID = turnOrder.get(0);
  }

  // TODO: create commands that utilize the following functions?
  void nextPlayerTurn() {
    turnOrder.remove(activePlayerID);
    turnOrder.add(activePlayerID);
    activePlayerID = turnOrder.get(0);
  }

  int checkNumberOfAlivePlayers() {
    return turnOrder.size();
  }

  boolean playerIsAlive(PlayerID playerID) {
    return turnOrder.contains(playerID);
  }
}
