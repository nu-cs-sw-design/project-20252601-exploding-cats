package ui;

import domain.game.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class GameUI {
	private Game game;
	private ResourceBundle messages;

	public GameUI (Game game) { this.game = game; }

	public void chooseLanguage() {
		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
		final String language  = "1. English\n2. 한국어\n";
		final String askLanguage = "Enter the number to choose the language:";
		final String invalidChoice = "Invalid choice. Please enter 1 or 2.";
		System.out.println(language);
		System.out.println(askLanguage);

		while (true) {
			String userInput = scanner.nextLine();
			switch (userInput) {
				case "1":
					messages = ResourceBundle.getBundle
						("message", new Locale("en"));
					final String languageSetEnglish = messages.getString
						("setLanguage");
					System.out.println(languageSetEnglish);
					return;
				case "2":
					messages = ResourceBundle.getBundle
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

	public void chooseNumberOfPlayers() {
		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
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
					game.setNumberOfPlayers(twoPlayers);
					System.out.println(numOfPlayersTwo);
					return;
				case "3":
					game.setNumberOfPlayers(threePlayers);
					System.out.println(numOfPlayersThree);
					return;
				case "4":
					game.setNumberOfPlayers(fourPlayers);
					System.out.println(numOfPlayersFour);
					return;
				default:
					System.out.println(invalidPlayersNum);
			}
		}
	}

	private int checkValidPlayerIndexInput() {
		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
		final String targetedAttackPrompt = messages.getString("targetedAttackPrompt");
		final String userPlayedCardAtIndex = messages.getString("userPlayedCardAtIndex");
		final String playerChooseDifferent = messages.getString("playerChooseDifferent");
		final String invalidIndex = messages.getString("invalidIndex");
		final String invalidNumber = messages.getString("invalidNumber");

		while (true) {
			System.out.println(targetedAttackPrompt);
			String userInputTwo = scanner.nextLine();
			try {
				int userIndex = Integer.parseInt(userInputTwo);
				if (checkUserWithinBounds(userIndex)) {
					if (checkIfPlayerIsAlive(userIndex)) {
						final String formattedMessage = MessageFormat.format
							(userPlayedCardAtIndex, userIndex);
						System.out.println(formattedMessage);
						return userIndex;
					} else {
						System.out.println(playerChooseDifferent);
					}
				} else {
					final String formattedMessage = MessageFormat.format
						(invalidIndex, game.getNumberOfPlayers() - 1);
					System.out.println(formattedMessage);
				}
			} catch (NumberFormatException e) {
				System.out.println(invalidNumber);
			}
		}
	}

	private void printPlayerTurn() {
		int currentPlayer = game.getPlayerTurn();
		final String dividerLine = messages.getString("dividerLine");
		final String currentPlayerTurnMessage;

			currentPlayerTurnMessage = MessageFormat.format(
					messages.getString("currentPlayerTurn"), currentPlayer);

		Player player = game.getPlayerAtIndex(currentPlayer);

		System.out.println(dividerLine);

			System.out.println(currentPlayerTurnMessage);
			final StringBuilder handMessage =
					new StringBuilder(messages.getString("playerHand"));
			for (int handIndex = 0;
					 handIndex < getHandSize(currentPlayer);
					 handIndex++) {
				handMessage.append(" ").append(
				getLocalizedCardType(
					game.getCardType(currentPlayer, handIndex)));
			}

			System.out.println(handMessage);

	}

	private int playedCard() {
		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
		final String promptMessage = messages.getString("playedCardPrompt");
		final String invalidIndexMessage = messages.getString("invalidIndex");
		final String invalidInputMessage = messages.getString("invalidNumber");
		int currentPlayer = game.getPlayerTurn();

		System.out.println(promptMessage);

		while (true) {
			String userInput = scanner.nextLine();
			try {
				int userIndex = Integer.parseInt(userInput);
				if (checkCardWithinBoundsIndexed(userIndex, currentPlayer)) {
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
						game.getHandSize(currentPlayer) - 1
						);
					System.out.println(formattedInvalidIndexMessage);
				}
			} catch (NumberFormatException e) {
				System.out.println(invalidInputMessage);
			}
		}
	}

	private void playNope(int playerIndex) {
		final String decidedToPlayNope = MessageFormat.format(
				messages.getString("decidedToPlayNope"), playerIndex);
		final String successfullyPlayedNope = MessageFormat.format(
				messages.getString("successfullyPlayedNope"), playerIndex);

		System.out.println(decidedToPlayNope);
		game.removeCardFromHand(playerIndex, CardType.NOPE);
		System.out.println(successfullyPlayedNope);
	}

	private boolean checkAllPlayersForNope(int playerIndex) {
		for (int playerCounter = 0;
			playerCounter < getNumberOfPlayers(); playerCounter++) {
			if (playerCounter != playerIndex) {
				if (game.checkIfPlayerHasCard(playerCounter, CardType.NOPE)) {
					final String hasNopeCard = MessageFormat.format(
						messages.getString
							("playerHasNopeCard"), playerCounter);
					final String wouldYouPlayNope = messages.getString
							("wouldYouPlayNope");
					final String optionYes = messages.getString("optionYes");
					final String optionNo = messages.getString("optionNo");
					final String invalidChoice = messages.getString
							("invalidChoiceForNope");

					System.out.println(hasNopeCard);
					System.out.println(wouldYouPlayNope);
					System.out.println(optionYes);
					System.out.println(optionNo);

					Scanner scanner = new Scanner(System.in,
							StandardCharsets.UTF_8);
					String userInput = scanner.nextLine();
					switch (userInput) {
						case "1":
							playNope(playerCounter);
							return !checkAllPlayersForNope
									(playerCounter);
						case "2":
							final String didNotPlayNope =
								MessageFormat.format
								(messages.getString
									("playerDidNotPlayNope"),
										playerCounter);
							System.out.println(didNotPlayNope);
							break;
						default:
							System.out.println(invalidChoice);
							playerCounter--;
							break;
					}
				}
			}
		}
		return false;
	}

	private boolean playExplodingKitten(int playerIndex) {
		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

		final String explodingKittenMessage = messages.getString("explodingKittenMessage");
		final String noDefuseCardMessage = messages.getString("noDefuseCardMessage");
		final String youExplodedMessage = messages.getString("youExplodedMessage");
		final String defusedMessage = messages.getString("defusedMessage");
		final String whereToInsertMessage = messages.getString("whereToInsertMessage");
		final String validRangeMessage = MessageFormat.format(
		messages.getString("validRangeMessage"), game.getDeckSize());
		final String invalidInputMessage = messages.getString("invalidInputMessage");
		final String cursedMessage = messages.getString("cursedExplodingMessage");
		final String notDefuseCardMessage = messages.getString("notDefuseCardMessage");
		final String discardCardMessage = messages.getString("discardCardMessage");
		final String reenterDefuseMessage = messages.getString("reenterDefuseMessage");

		final String anotherExplodingKittenMessage =
				messages.getString("anotherExplodingKittenMessage");
		final String defusedFirstExplodingKitten =
				messages.getString("defusedFirstExplodingKitten");
		final String discardStreakingKittenMessage =
				messages.getString("discardStreakingKittenMessage");

		System.out.println(explodingKittenMessage);
		if (checkExplodingKitten(playerIndex)) {
			System.out.println(noDefuseCardMessage);
			System.out.println(youExplodedMessage);
			return false;
		} else {
			Player player = game.getPlayerAtIndex(playerIndex);

			System.out.println(defusedMessage);
			System.out.println(whereToInsertMessage);
			System.out.println(validRangeMessage);
			while (true) {
				String userInput = scanner.nextLine();
				try {
					int userIndex = Integer.parseInt(userInput);
					game.playDefuse(userIndex, playerIndex);

					return true;
				} catch (NumberFormatException e) {
					System.out.println(invalidInputMessage);
				} catch (UnsupportedOperationException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private boolean endTurn() {
		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

		Card cardDrawn = game.drawCard();
		if (checkIfNumberOfTurnsGreaterThanZero()) {
			game.decrementNumberOfTurns();
		}

		int currentPlayerIdx = game.getPlayerTurn();
		final String cardDrawnMessage = MessageFormat.format
				(messages.getString("cardDrawnMessage"),
						getLocalizedCardType(cardDrawn.getCardType()));
		final String streakingKittenMessage = messages.getString("streakingKittenMessage");
		final String invalidNumber = messages.getString("invalidNumber");
		if (checkMatchingCardType(cardDrawn.getCardType(), CardType.EXPLODING_KITTEN)) {
			System.out.println(cardDrawnMessage);

			return playExplodingKitten(currentPlayerIdx);

		} else {
			System.out.println(cardDrawnMessage);
			game.addCardToHand(cardDrawn);

			return true;
		}
	}

	private boolean checkIfTheyEndTurn() {
		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
		final String endTurnPrompt = messages.getString("endTurnPrompt");
		final String typeOptionPrompt = messages.getString("typeOptionPrompt");
		final String endTurnConfirmed = messages.getString("endTurnConfirmed");
		final String playCardOrCombo = messages.getString("playCardOrCombo");
		final String optionYes = messages.getString("optionYes");
		final String optionNo = messages.getString("optionNo");

		System.out.println(endTurnPrompt);
		System.out.println(optionYes);
		System.out.println(optionNo);

		while (true) {
			System.out.println(typeOptionPrompt);
			String userInput = scanner.nextLine();
			switch (userInput) {
				case "1":
					System.out.println(endTurnConfirmed);
					boolean isPlayerAlive = endTurn();
					if (!isPlayerAlive || checkIfNumberOfTurnsIsZero()) {
						game.incrementPlayerTurn();
					}
					return true;
				case "2":
					System.out.println(playCardOrCombo);
					return false;
				default:
					break;
			}
		}
	}

	private void playShuffle() {
		final String decidedShuffle = messages.getString("decidedShuffle");
		final String enterShuffleTimes = messages.getString("enterShuffleTimes");
		final String enterPositiveInteger = messages.getString("enterPositiveInteger");
		final String enterInteger = messages.getString("enterInteger");

		System.out.println(decidedShuffle);

		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
		int numberOfShuffle;
		final int maxNumberOfShuffles = 100;
		while (true) {
			System.out.print(enterShuffleTimes);
			try {
				numberOfShuffle = scanner.nextInt();
				if (numberOfShuffle > maxNumberOfShuffles) {
					final String maxShuffleMessage =
							messages.getString("maxShuffleMessage");
					System.out.println(maxShuffleMessage);
				}
				else if (numberOfShuffle > 0) {
					break;
				} else {
					System.out.println(enterPositiveInteger);
				}
			} catch (Exception e) {
				System.out.println(enterInteger);
				scanner.next();
			}
		}
		game.playShuffle(numberOfShuffle);
	}

	private boolean checkCardIfInvalid(Card card) {

		CardType[] inValidCards = {CardType.NOPE,
						CardType.DEFUSE, CardType.EXPLODING_KITTEN};
		for (CardType inValidCard : inValidCards) {
			if (checkMatchingCardType(card.getCardType(), inValidCard)) {
				return true;
			}
		}
		return false;
	}

	public void startTurn() {
		if (checkIfNumberOfTurnsIsZero()) {

			game.setPlayerNumberOfTurns();
		}
		printPlayerTurn();
		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
		final String playerTurnsMessage = MessageFormat.format
				(messages.getString("playerTurnsMessage"), game.getNumberOfTurns());
		final String enterValidIntegerMessage = messages.getString("enterValidInteger");
		final String notEnoughCardsComboMessage = messages.getString
				("notEnoughCardsComboMessage");
		final String notEnoughCardsMessage = messages.getString("notEnoughCardsMessage");
		final String howManyFeralCatsMessage = messages.getString("howManyFeralCats");
		final String invalidFeralCatsNumberMessage =
				messages.getString("invalidFeralCatsNumber");
		final String invalidCardTypeMessage = messages.getString("invalidCardTypeMessage");

		System.out.println(playerTurnsMessage);

		while (!checkIfTheyEndTurn()) {
			printPlayerTurn();

			int cardIndex = playedCard();
			int playerIndex = game.getPlayerTurn();
			Player player = game.getPlayerAtIndex(playerIndex);
			CardType cardType = game.getCardType(playerIndex, cardIndex);

			if (checkIfDifferentCardType(cardType, CardType.EXPLODING_KITTEN)
					&& checkIfDifferentCardType(cardType, CardType.DEFUSE)) {
				if (checkAllPlayersNope()) {
					continue;
				}
			}

			switch (cardType) {

				case SHUFFLE:
					playShuffle();
					break;

				default:
					break;
			}
		}
	}

	public void endGame() {
		final String gameOverMessage = messages.getString("gameOverMessage");
		System.out.println(gameOverMessage);
	}

	public boolean checkIfGameOver() {
		return game.checkNumberOfAlivePlayers() == 1;
	}

	private boolean checkUserWithinBounds(int userIndex) {
		return userIndex >= 0 && userIndex < game.getNumberOfPlayers();
	}

	private boolean checkUserOutOfBounds(int userIndex) {
		return userIndex < 0 || userIndex >= game.getNumberOfPlayers();
	}

	private boolean checkIfPlayerIsAlive(int userIndex) {
		return !game.checkIfPlayerDead(userIndex);
	}

	private boolean hasCard(int userIndex, CardType cardType) {
		return game.checkIfPlayerHasCard(userIndex, cardType);
	}

	private boolean hasCardDirect(Player player, CardType cardType) {
		return player.hasCard(cardType);
	}

	private boolean checkCardWithinBounds(int cardIndex, Player player) {
		return cardIndex >= 0 && cardIndex < player.getHandSize();
	}

	private boolean checkCardWithinBoundsIndexed(int cardIndex, int playerIndex) {
		return cardIndex >= 0
				&& cardIndex < game.getHandSize(playerIndex);
	}

	private boolean checkMatchingCardType(CardType cardType, CardType cardTypeTwo) {
		return cardType == cardTypeTwo;
	}

	private int getHandSize(int playerIndex) {
		return game.getHandSize(playerIndex);
	}

	private int getNumberOfPlayers() {
		return game.getNumberOfPlayers();
	}

	private boolean checkExplodingKitten(int playerIndex) {
		final String invalidPlayerIndexExplodingKitten = messages.
				getString("invalidPlayerIndexExplodingKitten");
		boolean isPlayerExploded = false;
		try {
			isPlayerExploded = game.playExplodingKitten(playerIndex);
		} catch (UnsupportedOperationException e) {
			System.out.println(invalidPlayerIndexExplodingKitten);
		}
		return isPlayerExploded;
	}

	private boolean checkIfNumberOfTurnsGreaterThanZero() {
		return game.getNumberOfTurns() > 0;
	}

	private boolean checkIfNumberOfTurnsIsZero() {
		return game.getNumberOfTurns() == 0;
	}

	private boolean checkIfCurrentPlayerTurn(int playerIndex) {
		return playerIndex == game.getPlayerTurn();
	}

	private int getDeckSize() {
		return game.getDeckSize();
	}

	private boolean checkFeralCat(int numberOfFeralCatsToPlay,
					int numberOfFeralCats, int numberOfCatType, int threshold) {
		return numberOfFeralCatsToPlay > numberOfFeralCats ||
				numberOfFeralCatsToPlay + numberOfCatType < threshold;
	}

	private boolean checkIfDifferentCardType(CardType cardType, CardType cardTypeTwo) {
		return cardType != cardTypeTwo;
	}

	private boolean checkAllPlayersNope() {
		return checkAllPlayersForNope(game.getPlayerTurn());
	}

	private boolean checkNumberOfCards(Player player, CardType cardtype, int threshold) {
		return player.checkNumberOfCardsInHand(cardtype) >= threshold;
	}

	private String getLocalizedCardType(CardType cardType) {
		String cardTypeKey = "card." + cardType.name();
		return messages.getString(cardTypeKey);
	}

	private boolean checkNegativeIndexDeck(int indexToInsert) {
		return indexToInsert < 0;
	}

	private boolean checkIfGreaterThanMaxIndexDeck(int indexToInsert) {
		return indexToInsert > game.getDeck().getDeckSize();
	}
}

