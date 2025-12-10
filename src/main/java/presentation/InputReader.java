package presentation;

import domain.Card;
import domain.CardType;
import domain.PlayerID;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

class InputReader {
  private ResourceBundle messages;
  private final Scanner scanner;
  private int numPlayers;

  InputReader(Scanner scanner) {
    this.scanner = scanner;
  }

  void promptAndSetLanguage() {
    final String language = "1. English\n2. 한국어\n";
    final String askLanguage = "Enter the number to choose the language:";
    final String invalidChoice = "Invalid choice. Please enter 1 or 2.";
    System.out.println(language);
    System.out.println(askLanguage);

    while (true) {
      String userInput = scanner.nextLine();
      switch (userInput) {
        case "1":
          this.messages = ResourceBundle.getBundle
                  ("message", new Locale("en"));
          final String languageSetEnglish = messages.getString
                  ("setLanguage");
          System.out.println(languageSetEnglish);
          return;
        case "2":
          this.messages = ResourceBundle.getBundle
                  ("message", new Locale("ko"));
          final String languageSetKorean = messages.getString
                  ("setLanguage");
          System.out.println(languageSetKorean);
          return;
        default:
          System.out.println(invalidChoice);
      }
    }
  }

  int promptForNumPlayers() {
    final String numOfPlayersPrompt = messages.getString("numOfPlayersPrompt");
    final String numOfPlayersTwo = messages.getString("numOfPlayersTwo");
    final String numOfPlayersThree = messages.getString("numOfPlayersThree");
    final String numOfPlayersFour = messages.getString("numOfPlayersFour");
    final String invalidPlayersNum = messages.getString("invalidPlayersNum");

    System.out.println(numOfPlayersPrompt);

    while (true) {
      String userInput = scanner.nextLine();
      final int twoPlayers = 2;
      final int threePlayers = 3;
      final int fourPlayers = 4;
      switch (userInput) {
        case "2":
          this.numPlayers = twoPlayers;
          System.out.println(numOfPlayersTwo);
          return numPlayers;
        case "3":
          this.numPlayers = threePlayers;
          System.out.println(numOfPlayersThree);
          return numPlayers;
        case "4":
          this.numPlayers = fourPlayers;
          System.out.println(numOfPlayersFour);
          return numPlayers;
        default:
          System.out.println(invalidPlayersNum);
      }
    }
  }

  void printStartTurn(PlayerID activePlayerID, List<Card> hand) {
    int currentPlayer = activePlayerID.ordinal();

    final String dividerLine = messages.getString("dividerLine");
    final String currentPlayerTurnMessage = MessageFormat.format(
            messages.getString("currentPlayerTurn"), currentPlayer);

    System.out.println(dividerLine);
    System.out.println(currentPlayerTurnMessage);
    printHand(hand);
  }

  void printHand(List<Card> hand) {
    final StringBuilder handMessage =
            new StringBuilder(messages.getString("playerHand"));

    for (Card card : hand) {
      CardType cardType = card.getCardType();
      handMessage.append(" ").append(cardType);
    }

    System.out.println(handMessage);
  }

  boolean promptForPlayersWantsToEndTurn() {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    final String endTurnPrompt = messages.getString("endTurnPrompt");
    final String typeOptionPrompt = messages.getString("typeOptionPrompt");
    final String optionYes = messages.getString("optionYes");
    final String optionNo = messages.getString("optionNo");
    final String invalidChoice = messages.getString("invalidChoice");

    System.out.println(endTurnPrompt);
    System.out.println(optionYes);
    System.out.println(optionNo);
    System.out.println(typeOptionPrompt);

    while (true) {
      String userInput = scanner.nextLine();
      switch (userInput) {
        case "1":
          return true;
        case "2":
          return false;
        default:
          System.out.println(messages.getString("invalidChoice"));
      }
    }
  }

  void printEndTurnConfirmation() {
    final String endTurnConfirmed = messages.getString("endTurnConfirmed");
    System.out.println(endTurnConfirmed);
  }

  void printExplode() {
    final String noDefuseCardMessage = messages.getString("noDefuseCardMessage");
    final String youExplodedMessage = messages.getString("youExplodedMessage");

    System.out.println(noDefuseCardMessage);
    System.out.println(youExplodedMessage);
  }

  int promptForWhereToInsertExplodingKittenAfterDefuse(int deckSize) {
    final String defusedMessage = messages.getString("defusedMessage");
    final String whereToInsertMessage = messages.getString("whereToInsertMessage");
    final String validRangeMessage = MessageFormat.format(
            messages.getString("validRangeMessage"), deckSize - 1);
    final String invalidInputMessage = messages.getString("invalidInputMessage");
    final String invalidIndexMessage = messages.getString("invalidIndex");

    System.out.println(defusedMessage);
    System.out.println(whereToInsertMessage);
    System.out.println(validRangeMessage);

    while (true) {
      String userInput = scanner.nextLine();
      int userIndex;

      try {
        userIndex = Integer.parseInt(userInput);

        if (indexWithinBounds(userIndex, deckSize)) {
          return userIndex;
        }

        final String formattedInvalidIndexMessage =
                MessageFormat.format(invalidIndexMessage, deckSize - 1);
        System.out.println(formattedInvalidIndexMessage);
      } catch (NumberFormatException e) {
        System.out.println(invalidInputMessage);
      } catch (UnsupportedOperationException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  void printCardRequirementsNotMet() {
    final String cardRequirementsNotMet = messages.getString("cardRequirementsNotMet");
    System.out.println(cardRequirementsNotMet);
  }


  int promptForCardIndexToPlay(int activePlayerHandSize) {
    final String promptMessage = messages.getString("playedCardPrompt");
    final String invalidIndexMessage = messages.getString("invalidIndex");
    final String invalidInputMessage = messages.getString("invalidNumber");
    System.out.println(promptMessage);

    while (true) {
      String userInput = scanner.nextLine();
      try {
        int userIndex = Integer.parseInt(userInput);

        if (indexWithinBounds(userIndex, activePlayerHandSize)) {
          final String successMessage = MessageFormat.format(
                  messages.getString("playedCardSuccess"),
                  userIndex
          );
          System.out.println(successMessage);
          return userIndex;
        } else {
          final String formattedInvalidIndexMessage =
                  MessageFormat.format(
                          invalidIndexMessage,
                          activePlayerHandSize - 1
                  );
          System.out.println(formattedInvalidIndexMessage);
        }
      } catch (NumberFormatException e) {
        System.out.println(invalidInputMessage);
      }
    }
  }

  void printCardDraw(CardType drawnCardType) {
    final String cardDrawnMessage = MessageFormat.format
            (messages.getString("cardDrawnMessage"), drawnCardType);
    System.out.println(cardDrawnMessage);
  }

  private boolean indexWithinBounds(int index, int maxBound) {
    return index >= 0 && index < maxBound;
  }

  int promptOtherPlayersForNope(List<PlayerID> activePlayers, int lastActorID) {
    final String hasNopeCard = MessageFormat.format(messages.getString("playerHasNopeCard"), lastActorID);
    final String wouldYouPlayNope = messages.getString("wouldYouPlayNope");
    final String optionYes = messages.getString("optionYes");
    final String optionNo = messages.getString("optionNo");
    final String invalidChoice = messages.getString("invalidChoiceForNope");

    for (PlayerID playerID : activePlayers) {
      System.out.println(hasNopeCard);
      System.out.println(wouldYouPlayNope);
      System.out.println(optionYes);
      System.out.println(optionNo);

      while (true) {
        String userInput = scanner.nextLine();

        switch (userInput) {
          case "1":
            final String decidedToPlayNope = MessageFormat.format(
                    messages.getString("decidedToPlayNope"), playerID.ordinal());
            System.out.println(decidedToPlayNope);
            return playerID.ordinal();
          case "2":
            final String didNotPlayNope = MessageFormat.format(
                    messages.getString("playerDidNotPlayNope"), playerID.ordinal());
            System.out.println(didNotPlayNope);
            return -1;
          default:
            System.out.println(invalidChoice);
        }
      }
    }

    return -1;
  }

  void printPlayShuffle() {
    final String decidedShuffle = messages.getString("decidedShuffle");
    System.out.println(decidedShuffle);
  }

  void printEndGame() {
    final String gameOverMessage = messages.getString("gameOverMessage");
    System.out.println(gameOverMessage);
  }

  // TODO: if decide that want to
//  void printNopeDuelResult(int nopeCount) {
//    // TODO: internationalization
//    if (nopeCount % 2 != 0) {
//      System.out.println("Final Result: The action was NOPED.");
//    } else {
//      System.out.println("Final Result: The action EXECUTED.");
//    }
//  }
}
