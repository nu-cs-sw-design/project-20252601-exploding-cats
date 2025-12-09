package domain.game;

import domain.Card;
import domain.CardType;

import java.util.ArrayList;
import java.util.Random;
import java.security.SecureRandom;

public class Instantiator {
	public Card createCard(CardType cardType) {
		return new Card(cardType);
	}

	public Random createRandom() {
		return new SecureRandom();
	}

	public ArrayList<Card> createCardList() {
		return new ArrayList<Card>();
	}

}
