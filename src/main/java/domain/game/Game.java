package domain.game;

import java.util.Random;

public class Game {
	private int numberOfPlayers;
	private domain.game.Deck deck;
	private domain.game.Player[] players;
	private Random rand;
	private int currentPlayerTurn;
	private int currentPlayerNumberOfTurns;
	private int[] turnTracker;
	private static final String INVALID_PLAYER_INDEX_EXCEPTION = "Invalid player index.";
	private static final String NO_PLAYERS_EXCEPTION = "No players to select from.";
	private static final String INVALID_NUMBER_OF_PLAYERS_EXCEPTION =
			"Number of players must be between 2 and 5 inclusive";

public Game (int numberOfPlayers,
						 Deck deck, domain.game.Player[] players,
						 Random rand, int[] turnTracker) {
		this.numberOfPlayers = numberOfPlayers;
		this.deck = deck;
		this.players = players;
		this.rand = rand;
		this.currentPlayerTurn = 0;
		this.currentPlayerNumberOfTurns = 0;
		this.turnTracker = turnTracker;
	}

	public boolean playExplodingKitten(int playerIndex) {
		if (checkUserOutOfBounds(playerIndex)) {
			throw new UnsupportedOperationException(INVALID_PLAYER_INDEX_EXCEPTION);
		}
		if (checkIfPlayerHasCard(playerIndex, CardType.DEFUSE)) {
			return false;
		}
		players[playerIndex].setIsDead();
		if (playerIndex == currentPlayerTurn) {
			setCurrentPlayerNumberOfTurns(0);
		}
		return true;
	}

	public void playDefuse(int idxToInsertExplodingKitten, int playerIndex) {
		if (checkUserOutOfBounds(playerIndex)) {
			throw new UnsupportedOperationException(INVALID_PLAYER_INDEX_EXCEPTION);
		}
		Player currentPlayer = players[playerIndex];
		try {
			deck.insertExplodingKittenAtIndex(idxToInsertExplodingKitten);
		} catch (UnsupportedOperationException e) {
			throw e;
		}
		int defuseIdx = currentPlayer.getIndexOfCard(CardType.DEFUSE);
		currentPlayer.removeCardFromHand(defuseIdx);

		if (playerIndex == currentPlayerTurn) {
			setCurrentPlayerNumberOfTurns(0);
		}

	}

	public void setNumberOfPlayers (int numberOfPlayers) {
		if (checkInvalidNumberOfPlayers(numberOfPlayers)) {
			throw new IllegalArgumentException
					(INVALID_NUMBER_OF_PLAYERS_EXCEPTION);
		}
		this.numberOfPlayers = numberOfPlayers;
		getDeck().setNumPlayers(numberOfPlayers);
	}

	public domain.game.Player selectRandomPlayer() {
		int randomPlayerIndex = rand.nextInt(numberOfPlayers);
		if (hasZeroPlayers()) {
			throw new UnsupportedOperationException(NO_PLAYERS_EXCEPTION);
		}
		return players[randomPlayerIndex];
	}

	public void playShuffle(int numberOfShuffles) {
		for (int i = 0; i < numberOfShuffles; i++) {
			deck.shuffleDeck();
		}
	}

	public Deck getDeck() {
		return deck;
	}

	public void incrementPlayerTurn() {
		do {
			currentPlayerTurn = (currentPlayerTurn + 1) % numberOfPlayers;
		} while (checkIfPlayerDead(currentPlayerTurn));
	}

	public domain.game.Player getPlayerAtIndex(int playerIndex) {
		return players[playerIndex];
	}

	public void setPlayerNumberOfTurns() {
		currentPlayerNumberOfTurns = turnTracker[currentPlayerTurn];
		turnTracker[currentPlayerTurn] = 1;
	}

	public int getPlayerTurn() {
		return currentPlayerTurn;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public int checkNumberOfAlivePlayers() {
		int counter = 0;
		for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
			domain.game.Player player = getPlayerAtIndex(playerIndex);
			if (!player.getIsDead()) {
				counter++;
			}
		}
		return counter;
	}

	public void setCurrentPlayerNumberOfTurns(int numberOfTurns) {
		currentPlayerNumberOfTurns = numberOfTurns;
	}

	public void decrementNumberOfTurns() {
		currentPlayerNumberOfTurns--;
	}

	public int getNumberOfTurns() {
		return currentPlayerNumberOfTurns;
	}

	public int getDeckSize() {
		return deck.getDeckSize();
	}

	public Card drawCard() {
		return deck.drawCard();
	}

	public Card getCardAtIndex(int cardIndex) {
		return deck.getCardAtIndex(cardIndex);
	}

	public void removeCardFromHand(int playerIndex, CardType cardType) {
		getPlayerAtIndex(playerIndex).removeCardFromHand(
				getIndexOfCardFromHand(playerIndex, cardType));
	}

	public int getIndexOfCardFromHand(int playerIndex, CardType cardType) {
		return getPlayerAtIndex(playerIndex)
				.getIndexOfCard(cardType);
	}

	public void addCardToHand(Card card) {
		getPlayerAtIndex(currentPlayerTurn).addCardToHand(card);
	}

	public boolean checkIfPlayerDead(int playerIndex) {
		return getPlayerAtIndex(playerIndex).getIsDead();
	}

	public boolean checkIfPlayerHasCard(int playerIndex, CardType cardType) {
		return getPlayerAtIndex(playerIndex).hasCard(cardType);
	}

	public CardType getCardType(int playerIndex, int cardIndex) {
		return getPlayerAtIndex(playerIndex).getCardAt(cardIndex).getCardType();
	}

	public int getHandSize(int playerIndex) {
		return getPlayerAtIndex(playerIndex).getHandSize();
	}

	public CardType getDeckCardType(int deckIndex) {
		return getCardAtIndex(deckIndex).getCardType();
	}

	protected void setCurrentPlayerTurn(int turn) {
		currentPlayerTurn = turn;

	}

	private boolean checkUserOutOfBounds(int userIndex) {
		return userIndex < 0 || userIndex >= getNumberOfPlayers();
	}

	private boolean checkInvalidNumberOfPlayers(int numPlayers) {
		final int minPlayerThreshold = 1;
		final int maxPlayerThreshold = 6;
		return numPlayers <= minPlayerThreshold
				|| numPlayers >= maxPlayerThreshold;
	}

	private boolean hasZeroPlayers() {
		return numberOfPlayers == 0;
	}

	public int getTurnCountOfPlayer(int playerIndex) {
		return turnTracker[playerIndex];
	}

}