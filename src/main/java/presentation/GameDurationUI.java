package presentation;

import domain.*;

import java.util.List;

public class GameDurationUI {
  private final List<CardType> reactiveCardTypes = List.of(CardType.EXPLODING_KITTEN, CardType.DEFUSE);
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
      activePlayerCards = gameModel.getHandForPlayerID(activePlayerID);
      inputReader.printHand(activePlayerCards);
      int cardIndexToPlay = inputReader.promptForCardIndexToPlay(activePlayerHandSize);
      Card cardToPlay = activePlayerCards.get(cardIndexToPlay);
      CardType cardTypeToPlay = cardToPlay.getCardType();

      if (reactiveCardTypes.contains(cardTypeToPlay)) {
        System.out.println(cardTypeToPlay + " IS REACTIVE");
        inputReader.printCardRequirementsNotMet();
        continue;
      }

      playCardType(cardTypeToPlay, activePlayerID);
    }

    // END OF TURN -> DRAW CARD (and handle explode/defuse as needed)
    endTurn(activePlayerID, activePlayerCards);

    if (gameIsOver()) {
      inputReader.printEndGame();
      return;
    } else {
      gameModel.nextPlayerTurn();
      runGameLoop();
    }
  }

  private void playCardType(CardType cardTypeToPlay, PlayerID activePlayerID) {
    // Because I'm only implementing 4 cards, the only card this could be is the Shuffle card
    inputReader.printPlayShuffle();
    Command initialCommand = commandFactory.createCommandWithNoInput(cardTypeToPlay);
    gameInvoker.addCommand(initialCommand);

    List<PlayerID> playersWithNopeCards = gameModel.getOtherPlayersWithNopeCards(activePlayerID);
    if (!playersWithNopeCards.isEmpty()) {
      enterNopeDuel(activePlayerID);
    }

    gameInvoker.executeCommands();
  }

  private void enterNopeDuel(PlayerID activePlayerID) {
    int nopeCount = 0;
    boolean nopeDuelActive = true;
    int lastActorID = activePlayerID.ordinal();

    while (nopeDuelActive) {
      List<PlayerID> playersWithNopeCards = gameModel.getOtherPlayersWithNopeCards(activePlayerID);
      lastActorID = inputReader.promptOtherPlayersForNope(playersWithNopeCards, lastActorID);

      if (lastActorID != -1) {
        PlayerID lastActorPlayerID = PlayerID.values()[lastActorID];
        Command nope = commandFactory.createCommandWithPlayerInput(CardType.NOPE, lastActorPlayerID);
        gameInvoker.addCommand(nope);
        nopeCount++;
      } else {
        nopeDuelActive = false;
      }
    }

    // TODO maybe: inputReader.printNopeDuelResult(nopeCount);
  }

  private void endTurn(PlayerID activePlayerID, List<Card> activePlayerCards) {
    inputReader.printEndTurnConfirmation();
    CardType drawnCardType = gameModel.drawCardForPlayerID(activePlayerID);
    inputReader.printCardDraw(drawnCardType);

    if (drawnCardType == CardType.EXPLODING_KITTEN) {
      if (gameModel.playerHasCardType(activePlayerID, CardType.DEFUSE)) {
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

  private boolean gameIsOver() {
    return gameModel.gameIsOver();
  }
}
