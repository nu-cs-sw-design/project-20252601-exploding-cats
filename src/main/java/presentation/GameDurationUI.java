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

    // END OF TURN -> DRAW CARD (and handle explode/defuse as needed)
    endTurn(activePlayerID, activePlayerCards);

    // CHECK TO SEE IF 1 PLAYER LEFT
    // -> if so, end game
    // -> if not, SWITCH TURN TO NEXT PLAYER

  }

  private void endTurn(PlayerID activePlayerID, List<Card> activePlayerCards) {
    inputReader.printEndTurnConfirmation();
    // TODO: drawCardForPlayerID currently BOTH removes the card from the deck AND places it in the player's hand
    CardType drawnCardType = gameModel.drawCardForPlayerID(activePlayerID);
    inputReader.printCardDraw(drawnCardType);

    if (drawnCardType == CardType.EXPLODING_KITTEN) {
      if (activePlayerHasDefuse(activePlayerCards)) {
        int deckSize = gameModel.getDeckSize();
        int index = inputReader.promptForWhereToInsertExplodingKittenAfterDefuse(deckSize);

        Command removeDefuseAndKittenFromHand = commandFactory
                .createCommandWithPlayerInput(CardType.DEFUSE, activePlayerID);
        gameInvoker.addCommand(removeDefuseAndKittenFromHand);

        Command reinsertKittenIntoDeck = commandFactory
                .createCommandWithIndexInput(CardType.EXPLODING_KITTEN, index);
        gameInvoker.addCommand(reinsertKittenIntoDeck);

      } else {
        inputReader.printExplode();
        Command explodePlayer = commandFactory.createCommandWithNoInput(CardType.EXPLODING_KITTEN);
        gameInvoker.addCommand(explodePlayer);
      }

      gameInvoker.executeCommands();
    }
  }

  private boolean activePlayerHasDefuse(List<Card> activePlayerCards) {
    for (Card card : activePlayerCards) {
      if (Card.getCardType() == CardType.DEFUSE) {
        return true;
      }
    }
  }
}
