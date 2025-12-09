package domain.game;

public class Card {
	private final CardType cardType;

	public Card(domain.game.CardType cardType) {
		this.cardType = cardType;
	}

	public boolean isCardType(CardType otherCardType) {
		return this.cardType == otherCardType;
	}
}

