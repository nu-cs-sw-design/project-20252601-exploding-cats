package presentation;

import domain.*;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

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
    // todo: get activePlayerID and activePlayerHand (and activePlayerHandSize)
    // todo: consider how to mitigate dependencies later
    PlayerID activePlayerID;
    List<Card> activePlayerCards;

    inputReader.printStartTurn(activePlayerID, activePlayerCards);
    while (inputReader.promptForPlayersWantsToEndTurn() == false) {
      int cardIndexToPlay = inputReader.promptForCardIndexToPlay(activePlayerHandSize);
      Card cardToPlay = activePlayerCards.get(cardIndexToPlay);

      // COMMAND: add command to command history
      // check if that card is reversible (i.e. SHUFFLE) AND if other players have nope

      // prompt other players for NOPE's
      // COMMAND: add any nope's to command history
      // execute commands
      // print end result (based on if number of nopes added being even or odd)
    }

    // END OF TURN -> DRAW CARD
    inputReader.printEndTurnConfirmation();
    Card drawnCardType; // call draw card
    inputReader.printCardDraw(drawnCardType);

    // if exploding kitten, check for defuse card
    if (drawnCardType == CardType.EXPLODING_KITTEN) {
      // if no defuse, printExplode
      // COMMAND: remove user from turn order (IN WHICH CASE, NO NEED TO EXPLICITLY SWITCH TO NEXT TURN)
      // COMMAND: explode

      // if defuse, print that safe but that used a defuse card
      // COMMAND: remove defuse from player hand
    }
    else {
      // COMMAND: add card to hand
    }

    // CHECK TO SEE IF 1 PLAYER LEFT
    // -> if so, end game
    // -> if not, SWITCH TURN TO NEXT PLAYER

  }

  void checkIfGameOver() {

  }

  void endTurn() {

  }

  void endGame() {

  }
}
