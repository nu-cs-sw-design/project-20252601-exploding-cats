package domain.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
  // TODO: update this deck size if I add more card types
  private final int MAX_DECK_SIZE = 52;
  private final List<Card> deck;
  private int numPlayers;

//  private Random rand;
//  private int maxDeckSize;
//  private Instantiator instantiator;

  private static final String DECK_FULL_EXCEPTION = "Deck is full, cannot insert more cards.";
  private static final String DRAW_FROM_EMPTY_DECK_EXCEPTION =
          "Cannot draw card from empty deck.";
  private static final String NEGATIVE_INDEX_EXCEPTION =
          "Invalid index. Cannot insert into negative index.";
  private static final String INDEX_GREATER_THAN_DECK_SIZE_EXCEPTION =
          "Invalid index. Cannot insert into index greater than deck size.";
  private static final String MISMATCH_ORDER_SIZE_EXCEPTION =
          "Mismatch: The number of indices (%d) does not match the number of cards (%d).";
  private static final String INDEX_OUT_OF_RANGE_EXCEPTION =
          "Index out of range: %d. Valid range is 0 to %d.";

//  public Deck(List<Card> deck, Random rand, int numPlayers, int maxDeckSize,
//              Instantiator instantiator) {
//    this.deck = deck;
//    this.rand = rand;
//
//    this.numPlayers = numPlayers;
//    this.maxDeckSize = maxDeckSize;
//    this.instantiator = instantiator;
//  }

  public Deck(int numPlayers) {
    this.numPlayers = numPlayers;
    this.deck = new ArrayList<Card>();
    this.initializeDeck();
  }

  private void initializeDeck() {
    final int cardAddedFourTimes = 4;
    final int cardAddedFiveTimes = 5;

    // TODO: where are the exploding kittens added?
    // TODO: and how can we make sure to have enough cards for both draw pile and player hands?
    insertCard(CardType.NOPE, cardAddedFourTimes, false);
    insertCard(CardType.SHUFFLE, cardAddedFourTimes, false);
    insertCard(CardType.DEFUSE,
            cardAddedFiveTimes - numPlayers, false);
  }

  void insertCard(CardType cardType, int numberOfCards, boolean bottom) {
    if (addedOutOfBounds(numberOfCards)) {
      throw new UnsupportedOperationException(DECK_FULL_EXCEPTION);
    }

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

//  int getDeckSize() {
//    return deck.size();
//  }

//  public Card getCardAtIndex(int index) {
//    return deck.get(index);
//  }

  void shuffleDeck() {
    Collections.shuffle(deck);
//    for (int deckIndex = deck.size() - 1; deckIndex > 0; deckIndex--) {
//
//      int indexToSwap = rand.nextInt(deckIndex + 1);
//      Card temporaryCard = deck.get(indexToSwap);
//      deck.set(indexToSwap, deck.get(deckIndex));
//      deck.set(deckIndex, temporaryCard);
//    }
  }

  public Card drawCard() {
    if (this.deck.isEmpty()) {
      throw new UnsupportedOperationException
              (DRAW_FROM_EMPTY_DECK_EXCEPTION);
    } else {
      return this.deck.remove(this.deck.size() - 1);
    }
  }

//  public void setNumPlayers(int numPlayers) {
//    this.numPlayers = numPlayers;
//  }

  // TODO: figure out where this was called and if should keep
  // TOOD: i imagine it would be necessary during deck instantiation?
//  private int removeBombs() {
//    int counter = 0;
//    for (int index = 0; index < deck.size(); index++) {
//      if (checkIfExplodingKitten(index)) {
//        counter++;
//        deck.remove(index);
//        index--;
//      }
//    }
//    return counter;
//  }

  void insertExplodingKittenAtIndex(int indexToInsert) {
    if (indexToInsert < 0) {
      throw new UnsupportedOperationException
              (NEGATIVE_INDEX_EXCEPTION);
    } else if (indexToInsert > deck.size()) {
      throw new UnsupportedOperationException
              (INDEX_GREATER_THAN_DECK_SIZE_EXCEPTION);
    } else {
      Card newExplodingKitten = new Card(CardType.EXPLODING_KITTEN);
      deck.add(indexToInsert, newExplodingKitten);
    }
  }

//  protected CardType getCardTypeAtIndex(int index) {
//    return deck.get(index).getCardType();
//  }

  private boolean addedOutOfBounds(int numberOfCards) {
    return (deck.size() + numberOfCards) > MAX_DECK_SIZE;
  }

//  private boolean explodingKittenIsAtIndex(int index) {
//    Card cardAtIndex = deck.get(index);
//    return cardAtIndex.isCardType(CardType.EXPLODING_KITTEN);
//  }

//  private boolean checkIfIndexOutOfRange(int index) {
//    return index < 0 || index >= deck.size();
//  }

}

