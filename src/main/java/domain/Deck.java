package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
  private final List<Card> deck;
  private final int numPlayers;

  private static final String DECK_FULL_EXCEPTION = "Deck is full, cannot insert more cards.";
  private static final String DRAW_FROM_EMPTY_DECK_EXCEPTION =
          "Cannot draw card from empty deck.";
  private static final String NEGATIVE_INDEX_EXCEPTION =
          "Invalid index. Cannot insert into negative index.";
  private static final String INDEX_GREATER_THAN_DECK_SIZE_EXCEPTION =
          "Invalid index. Cannot insert into index greater than deck size.";

  public Deck(int numPlayers) {
    this.numPlayers = numPlayers;
    this.deck = new ArrayList<Card>();
    this.initializeDeck();
  }

  private void initializeDeck() {
    // NOTE: Because I am only implementing 4 types of cards,
    // I've kept the number of Exploding Kittens and Defuse cards
    // consistent with the original game, but I have increased
    // the number of Nope and Shuffle cards so that the total
    // number of cards across the Deck and all PlayerHands is still 56
    int numNopeInDeck = 23; // instead of original 4
    int numShuffleInDeck = 23; // instead of original 5
    int numExplodingKittensInDeck = numPlayers - 1;
    int numDefuseInDeck = 6 - numPlayers;

    insertCard(CardType.NOPE, numNopeInDeck, false);
    insertCard(CardType.SHUFFLE, numShuffleInDeck, false);
    insertCard(CardType.DEFUSE, numDefuseInDeck, false);
    insertCard(CardType.EXPLODING_KITTEN, numExplodingKittensInDeck, false);
  }

  void removeExplodingKittens() {
    for (int i = deck.size() - 1; i >= 0; i--) {
      Card card = deck.get(i);
      if (card.isCardType(CardType.EXPLODING_KITTEN)) {
        deck.remove(i);
      }
    }
  }

  void reinsertExplodingKittens() {
    int numExplodingKittensInDeck = numPlayers - 1;
    insertCard(CardType.EXPLODING_KITTEN, numExplodingKittensInDeck, false);
  }

  void insertCard(CardType cardType, int numberOfCards, boolean bottom) {
    if (!bottom) {
      for (int i = 0; i < numberOfCards; i++) {
        Card newCard = new Card(cardType);
        deck.add(deck.size(), newCard);
      }
    } else {
      for (int i = 0; i < numberOfCards; i++) {
        Card newCard = new Card(cardType);
        deck.add(0, newCard);
      }
    }
  }

  void shuffleDeck() {
    Collections.shuffle(deck);
  }

  public Card drawCard() {
    if (this.deck.isEmpty()) {
      throw new UnsupportedOperationException
              (DRAW_FROM_EMPTY_DECK_EXCEPTION);
    } else {
      return this.deck.remove(this.deck.size() - 1);
    }
  }

  void insertExplodingKittenAtIndex(int indexToInsert) {
    if (indexToInsert < 0) {
      throw new UnsupportedOperationException
              (NEGATIVE_INDEX_EXCEPTION);
    } else if (indexToInsert >= deck.size()) {
      throw new UnsupportedOperationException
              (INDEX_GREATER_THAN_DECK_SIZE_EXCEPTION);
    } else {
      Card newExplodingKitten = new Card(CardType.EXPLODING_KITTEN);
      deck.add(indexToInsert, newExplodingKitten);
    }
  }

  int getDeckSize() {
    return deck.size();
  }

  private boolean addedOutOfBounds(int numberOfCards) {
    final int MAX_DECK_SIZE = 56;
    return (deck.size() + numberOfCards) > MAX_DECK_SIZE;
  }
}

