package domain;

public class Card {
	public final CardType cardType;

	public Card(CardType cardType) {
		this.cardType = cardType;
	}

	// TODO: just use getCardType instead?
	public boolean isCardType(CardType otherCardType) {
		return this.cardType == otherCardType;
	}

	public CardType getCardType() {
		return cardType;
	}
}

