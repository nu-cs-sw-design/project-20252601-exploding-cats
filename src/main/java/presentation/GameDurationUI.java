package presentation;

import domain.*;

import java.util.List;

public class GameDurationUI {
  private final SimpleCardCommandFactory commandFactory;
  private final GameInvoker gameInvoker;
  private final InputReader inputReader;
  private final GameModel gameModel;

  GameDurationUI(InputReader inputReader, SimpleCardCommandFactory commandFactory, GameInvoker gameInvoker, GameModel gameModel) {
    this.inputReader = inputReader;
    this.commandFactory = commandFactory;
    this.gameInvoker = gameInvoker;
    this.gameModel = gameModel;
  }

  void runGameLoop() {
    PlayerID activePlayerID = gameModel.getCurrentPlayer();
    List<Card> activePlayerCards = gameModel.getHandForPlayerID(activePlayerID);
    int activePlayerHandSize = activePlayerCards.size();

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
    CardType drawnCardType = gameModel.drawCardForPlayerID(activePlayerID);
    inputReader.printCardDraw(drawnCardType);

    // if exploding kitten, check for defuse card
    if (drawnCardType == CardType.EXPLODING_KITTEN) {
      inputReader.printExplode();
      // make sure that exploding kitten was still added to player's hand (so that no longer in deck)
      // COMMAND: remove user from turn order (IN WHICH CASE, NO NEED TO EXPLICITLY SWITCH TO NEXT TURN)
      // COMMAND: explode

      // if player owns defuse, print that safe but that used a defuse card
      int index = inputReader.promptForWhereToInsertExplodingKittenAfterDefuse( int deckSize)
      // COMMAND: remove defuse from player hand
      // COMMAND: insert ExplodingKitten at index into deck
    } else {

      // COMMAND: add card to hand
    }

    // CHECK TO SEE IF 1 PLAYER LEFT
    // -> if so, end game
    // -> if not, SWITCH TURN TO NEXT PLAYER

  }
}
