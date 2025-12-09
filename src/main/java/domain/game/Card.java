package domain.game;

public class Card {
	private CardType cardType;

	public Card(domain.game.CardType cardType) {
		this.cardType = cardType;
	}

	public domain.game.CardType getCardType() {
		return cardType;
	}

}

