package domain.game;

import domain.Card;
import domain.CardType;
import domain.Deck;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeckTest {
  @Test
  public void initializeDeckSizeInitializeSizeof2() {
    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    List<Card> deckOfSize2 =
            new ArrayList<>(Arrays.asList(firstCard, secondCard));
    EasyMock.replay(rand, firstCard, secondCard, instantiator);

    Deck deck = new Deck(deckOfSize2, rand, 0, 2, instantiator);

    assertEquals(deck.getDeckSize(), 2);
    EasyMock.verify(rand, firstCard, secondCard, instantiator);
  }

  @Test
  public void initializeDeckSizeInitializeSizeof3() {
    final int maxDeckSize = 3;

    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    Card thirdCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    List<Card> deckOfSize3 =
            new ArrayList<>(Arrays.asList(firstCard, secondCard, thirdCard));
    EasyMock.replay(rand, firstCard, secondCard, thirdCard, instantiator);
    Deck deck = new Deck(deckOfSize3, rand, 0, maxDeckSize, instantiator);

    assertEquals(deck.getDeckSize(), maxDeckSize);
    EasyMock.verify(rand, firstCard, secondCard, thirdCard, instantiator);
  }

  @Test
  public void initializeDeckSizeInitializeSizeof0() {
    final int maxDeckSize = 3;

    List<Card> deckOfSize0 = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    EasyMock.replay(rand, instantiator);

    Deck deck = new Deck(deckOfSize0, rand, 0, maxDeckSize, instantiator);
    assertEquals(deck.getDeckSize(), 0);
    EasyMock.verify(rand, instantiator);
  }

  @Test
  public void initializeDeckSizeInitializeSizeof2Dupes() {
    final int maxDeckSize = 3;
    Card firstCard = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    List<Card> deckOfSize2 =
            new ArrayList<>(Arrays.asList(firstCard, firstCard));
    EasyMock.replay(rand, firstCard, instantiator);
    Deck deck = new Deck(deckOfSize2, rand, 0, maxDeckSize, instantiator);

    assertEquals(deck.getDeckSize(), 2);
    EasyMock.verify(rand, firstCard, instantiator);
  }

  @Test
  public void shuffleDeckTwoCards() {
    final int maxDeckSize = 3;

    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    List<Card> deckOfSize2 =
            new ArrayList<>(Arrays.asList(secondCard, firstCard));
    EasyMock.expect(rand.nextInt(2)).andReturn(0);
    EasyMock.replay(rand, firstCard, secondCard, instantiator);
    Deck deck = new Deck(deckOfSize2, rand, 0, maxDeckSize, instantiator);

    deck.shuffleDeck();

    assertEquals(deck.getCardAtIndex(0), firstCard);
    EasyMock.verify(rand, firstCard, secondCard, instantiator);
  }

  @Test
  public void shuffleDeckZeroCards() {
    final int maxDeckSize = 3;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    List<Card> deckOfSize2 = new ArrayList<>();
    EasyMock.replay(rand, instantiator);
    Deck deck = new Deck(deckOfSize2, rand, 0, maxDeckSize, instantiator);

    deck.shuffleDeck();

    assertEquals(deck.getDeckSize(), 0);
    EasyMock.verify(rand, instantiator);
  }

  @Test
  public void shuffleDeckThreeCardsDupe() {
    final int maxDeckSize = 3;

    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    final int firstRandNumber = 3;
    final int secondRandNumber = 2;
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    List<Card> deckOfSize3 =
            new ArrayList<>(Arrays.asList(firstCard, firstCard, secondCard));
    EasyMock.expect(rand.nextInt(firstRandNumber)).andReturn(0);
    EasyMock.expect(rand.nextInt(secondRandNumber)).andReturn(0);
    EasyMock.replay(rand, firstCard, secondCard, instantiator);
    Deck deck = new Deck(deckOfSize3, rand, 0, maxDeckSize, instantiator);

    deck.shuffleDeck();

    assertEquals(deck.getCardAtIndex(0), firstCard);
    assertEquals(deck.getCardAtIndex(1), secondCard);
    assertEquals(deck.getCardAtIndex(2), firstCard);
    EasyMock.verify(rand, firstCard, secondCard, instantiator);
  }

  @Test
  public void insertCardEmptyDeck() {
    final int maxDeckSize = 3;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Card expectedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(instantiator.createCard(CardType.SHUFFLE)).andReturn(expectedCard);
    CardType cardType = CardType.SHUFFLE;

    List<Card> deckOfSize3 = new ArrayList<>();
    Deck deck = new Deck(deckOfSize3, rand, 0, maxDeckSize, instantiator);
    EasyMock.replay(rand, instantiator, expectedCard);
    deck.insertCard(cardType, 1, false);
    assertEquals(1, deck.getDeckSize());
    assertEquals(deck.getCardAtIndex(0), expectedCard);
    EasyMock.verify(rand, instantiator, expectedCard);
  }

  @Test
  public void insertCardTwoCards() {
    final int maxDeckSize = 3;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Card expectedCard = EasyMock.createMock(Card.class);
    Card expectedCardTwo = EasyMock.createMock(Card.class);

    EasyMock.expect(instantiator.createCard(CardType.SHUFFLE))
            .andReturn(expectedCard);
    EasyMock.expect(instantiator.createCard(CardType.NOPE))
            .andReturn(expectedCardTwo);
    CardType firstCard = CardType.SHUFFLE;
    CardType secondCard = CardType.NOPE;
    List<Card> deckOfSize2 = new ArrayList<>();
    Deck deck = new Deck(deckOfSize2, rand, 0, maxDeckSize, instantiator);
    EasyMock.replay(rand, instantiator, expectedCard, expectedCardTwo);
    deck.insertCard(firstCard, 1, false);
    deck.insertCard(secondCard, 1, false);
    assertEquals(2, deck.getDeckSize());
    assertEquals(deck.getCardAtIndex(1), expectedCardTwo);
    EasyMock.verify(rand, instantiator, expectedCard, expectedCardTwo);
  }

  @Test
  public void insertCardThreeCardsWithDupes() {
    final int maxDeckSize = 3;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Card expectedCard = EasyMock.createMock(Card.class);
    Card expectedCardTwo = EasyMock.createMock(Card.class);

    EasyMock.expect(instantiator.createCard(CardType.NOPE))
            .andReturn(expectedCard);
    EasyMock.expect(instantiator.createCard(CardType.SHUFFLE))
            .andReturn(expectedCardTwo).times(2);

    CardType firstCard = CardType.NOPE;
    CardType secondCard = CardType.SHUFFLE;

    List<Card> deckOfSize3 = new ArrayList<>();
    Deck deck = new Deck(deckOfSize3, rand, 0, maxDeckSize, instantiator);
    EasyMock.replay(rand, instantiator, expectedCard, expectedCardTwo);
    deck.insertCard(firstCard, 1, false);
    deck.insertCard(secondCard, 1, false);
    deck.insertCard(secondCard, 1, false);

    assertEquals(maxDeckSize, deck.getDeckSize());
    assertEquals(deck.getCardAtIndex(2), expectedCardTwo);

    EasyMock.verify(rand, instantiator, expectedCard, expectedCardTwo);
  }

  @Test
  public void insertCardMoreThanMaxCardsThrowsIllegalStateException() {
    final int maxDeckSize = 3;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Card expectedCard = EasyMock.createMock(Card.class);
    Card expectedCardTwo = EasyMock.createMock(Card.class);

    EasyMock.expect(instantiator.createCard(CardType.NOPE))
            .andReturn(expectedCard);
    EasyMock.expect(instantiator.createCard(CardType.SHUFFLE))
            .andReturn(expectedCardTwo).times(2);

    CardType firstCard = CardType.NOPE;
    CardType secondCard = CardType.SHUFFLE;
    CardType thirdCard = CardType.SHUFFLE;
    CardType extraCard = CardType.SHUFFLE;

    List<Card> deckOfSize3 = new ArrayList<>();
    Deck deck = new Deck(deckOfSize3, rand, 0, maxDeckSize, instantiator);

    EasyMock.replay(rand, instantiator, expectedCard, expectedCardTwo);

    deck.insertCard(firstCard, 1, false);
    deck.insertCard(secondCard, 1, false);
    deck.insertCard(thirdCard, 1, false);

    assertThrows(UnsupportedOperationException.class, () -> {
      deck.insertCard(extraCard, 1, false);
    });

    EasyMock.verify(rand, instantiator, expectedCard, expectedCardTwo);
  }

  @Test
  public void insertCardMoreThanOneCard() {
    final int maxDeckSize = 8;
    final int expectedDeckSize = 5;
    final int fourthCardIndex = 4;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Card expectedCard = EasyMock.createMock(Card.class);
    Card expectedCardTwo = EasyMock.createMock(Card.class);
    Card expectedCardThree = EasyMock.createMock(Card.class);
    Card expectedCardFour = EasyMock.createMock(Card.class);

    EasyMock.expect(instantiator.createCard(CardType.DEFUSE))
            .andReturn(expectedCard);
    EasyMock.expect(instantiator.createCard(CardType.SHUFFLE))
            .andReturn(expectedCardTwo);
    EasyMock.expect(instantiator.createCard(CardType.NOPE))
            .andReturn(expectedCardThree);
    EasyMock.expect(instantiator.createCard(CardType.SHUFFLE))
            .andReturn(expectedCardFour).times(2);

    CardType firstCard = CardType.DEFUSE;
    CardType secondCard = CardType.SHUFFLE;
    CardType thirdCard = CardType.NOPE;
    CardType newCard = CardType.SHUFFLE;
    List<Card> deckOfSize8 = new ArrayList<>();
    Deck deck = new Deck(deckOfSize8, rand, 0, maxDeckSize, instantiator);

    EasyMock.replay(rand, instantiator, expectedCard,
            expectedCardTwo, expectedCardThree, expectedCardFour);
    deck.insertCard(firstCard, 1, false);
    deck.insertCard(secondCard, 1, false);
    deck.insertCard(thirdCard, 1, false);
    deck.insertCard(newCard, 2, false);
    assertEquals(expectedDeckSize, deck.getDeckSize());
    assertEquals(deck.getCardAtIndex(fourthCardIndex), expectedCardFour);
    EasyMock.verify(rand, instantiator, expectedCard,
            expectedCardTwo, expectedCardThree, expectedCardFour);
  }

  @Test
  public void initializeDeckEXPLODINGKITTENSWithFivePlayers() {
    final int maxDeckSize = 56;

    final int threeCards = 3;

    final int expectedDeckSize = 8;
    final int fourCards = 4;
    final int numberOfPlayers = 5;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    Card expectedCardThree = EasyMock.createMock(Card.class);

    Card expectedCardFive = EasyMock.createMock(Card.class);

    EasyMock.expect(instantiator.createCard(CardType.SHUFFLE))
            .andReturn(expectedCardThree).times(fourCards);

    EasyMock.expect(instantiator.createCard(CardType.NOPE))
            .andReturn(expectedCardFive).times(fourCards);

    List<Card> initialDeck = new ArrayList<>();
    Deck deck = new Deck(initialDeck, rand,
            numberOfPlayers, maxDeckSize, instantiator);

    EasyMock.replay(rand, instantiator, expectedCardThree, expectedCardFive);
    deck.setNumberOfPlayers(numberOfPlayers);
    deck.initializeDeck();

    assertEquals(expectedDeckSize, deck.getDeckSize());

    EasyMock.verify(rand, instantiator, expectedCardThree, expectedCardFive);
  }

  @Test
  public void drawCardEmptyDeckThrowsException() {
    final int maxDeckSize = 42;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    List<Card> emptyDeck = EasyMock.createMock(List.class);

    EasyMock.expect(emptyDeck.isEmpty()).andReturn(true);

    EasyMock.replay(rand, instantiator, emptyDeck);

    Deck deck = new Deck(emptyDeck, rand,
            0, maxDeckSize, instantiator);

    String expectedMessage = "Cannot draw card from empty deck.";

    Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
      deck.drawCard();
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(rand, instantiator, emptyDeck);
  }

  @Test
  public void drawCardOneCardDeckReturnsOnlyCard() {
    final int maxDeckSize = 42;

    Card card1 = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    List<Card> oneCardDeck = EasyMock.createMock(List.class);

    EasyMock.expect(oneCardDeck.isEmpty()).andReturn(false).times(1);
    EasyMock.expect(oneCardDeck.size()).andReturn(1).times(1);
    EasyMock.expect(oneCardDeck.remove(0)).andReturn(card1).times(1);
    EasyMock.expect(oneCardDeck.size()).andReturn(0).times(1);

    EasyMock.replay(card1, rand, instantiator, oneCardDeck);

    Deck deck = new Deck(oneCardDeck, rand,
            0, maxDeckSize, instantiator);

    assertEquals(deck.drawCard(), card1);
    assertEquals(0, deck.getDeckSize());

    EasyMock.verify(card1, rand, instantiator, oneCardDeck);
  }

  @Test
  public void drawCardTwoCardsDeckReturnsLastCard() {
    final int maxDeckSize = 42;
    final int expectedDeckSize = 1;

    Card card2 = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    List<Card> twoCardsDeck = EasyMock.createMock(List.class);

    EasyMock.expect(twoCardsDeck.isEmpty()).andReturn(false).times(1);
    EasyMock.expect(twoCardsDeck.size()).andReturn(2).times(1);
    EasyMock.expect(twoCardsDeck.remove(1)).andReturn(card2).times(1);
    EasyMock.expect(twoCardsDeck.size()).andReturn(1).times(1);

    EasyMock.replay(card2, rand, instantiator, twoCardsDeck);

    Deck deck = new Deck(twoCardsDeck, rand,
            0, maxDeckSize, instantiator);
    assertEquals(deck.drawCard(), card2);
    assertEquals(expectedDeckSize, deck.getDeckSize());

    EasyMock.verify(card2, rand, instantiator, twoCardsDeck);
  }

  @Test
  public void removeBombOneCard() {
    final int maxDeckSize = 42;
    final int numOfBombs = 1;

    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    List<Card> maxSizeDeck = EasyMock.createMock(List.class);

    EasyMock.expect(maxSizeDeck.size()).andReturn(1).times(1);
    EasyMock.expect(maxSizeDeck.get(0)).andReturn(firstCard).times(1);
    EasyMock.expect(maxSizeDeck.remove(0)).andReturn(firstCard).times(1);
    EasyMock.expect(maxSizeDeck.size()).andReturn(0).times(2);

    EasyMock.expect(firstCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN).anyTimes();

    EasyMock.expect(instantiator.createCard(CardType.EXPLODING_KITTEN))
            .andReturn(firstCard).anyTimes();

    EasyMock.replay(rand, instantiator, firstCard, maxSizeDeck);

    Deck deck = new Deck(maxSizeDeck, rand,
            0, maxDeckSize, instantiator);

    int numberOfBombs = deck.removeBombs();

    assertEquals(numOfBombs, numberOfBombs);
    assertEquals(0, deck.getDeckSize());

    EasyMock.verify(rand, instantiator, firstCard, maxSizeDeck);
  }

  @Test
  public void removeOneBombTwoCards() {
    final int maxDeckSize = 42;
    final int expectedDeckSize = 1;
    final int numOfBombs = 1;
    final int deckSize = 2;

    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    List<Card> maxSizeDeck = EasyMock.createMock(List.class);

    EasyMock.expect(maxSizeDeck.size()).andReturn(deckSize).times(1);
    EasyMock.expect(maxSizeDeck.get(0)).andReturn(firstCard).times(1);
    EasyMock.expect(maxSizeDeck.size()).andReturn(deckSize).times(1);
    EasyMock.expect(maxSizeDeck.get(1)).andReturn(secondCard).times(1);
    EasyMock.expect(maxSizeDeck.remove(1)).andReturn(secondCard).times(1);
    EasyMock.expect(maxSizeDeck.size()).andReturn(1).times(2);

    EasyMock.expect(firstCard.getCardType())
            .andReturn(CardType.DEFUSE).anyTimes();

    EasyMock.expect(instantiator.createCard(CardType.DEFUSE))
            .andReturn(firstCard).anyTimes();

    EasyMock.expect(secondCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN).anyTimes();

    EasyMock.expect(instantiator.createCard(CardType.EXPLODING_KITTEN))
            .andReturn(secondCard).anyTimes();

    EasyMock.replay(rand, instantiator, firstCard, maxSizeDeck, secondCard);

    Deck deck = new Deck(maxSizeDeck, rand,
            0, maxDeckSize, instantiator);
    int numberOfBombs = deck.removeBombs();
    assertEquals(numOfBombs, numberOfBombs);
    assertEquals(expectedDeckSize, deck.getDeckSize());

    EasyMock.verify(rand, instantiator, firstCard, maxSizeDeck, secondCard);
  }

  @Test
  public void removeThreeBombsFourCards() {
    final int maxDeckSize = 42;
    final int deckSize = 4;
    final int deckSizeRemoveOne = 3;
    final int deckSizeRemoveTwo = 2;
    final int numOfBombs = 3;

    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    List<Card> maxSizeDeck = EasyMock.createMock(List.class);

    EasyMock.expect(maxSizeDeck.size()).andReturn(deckSize).times(1);
    EasyMock.expect(maxSizeDeck.get(0)).andReturn(firstCard).times(1);

    EasyMock.expect(maxSizeDeck.size()).andReturn(deckSize).times(1);
    EasyMock.expect(maxSizeDeck.get(1)).andReturn(secondCard).times(1);
    EasyMock.expect(maxSizeDeck.remove(1)).andReturn(secondCard).times(1);

    EasyMock.expect(maxSizeDeck.size()).andReturn(deckSizeRemoveOne).times(1);
    EasyMock.expect(maxSizeDeck.get(1)).andReturn(secondCard).times(1);
    EasyMock.expect(maxSizeDeck.remove(1)).andReturn(secondCard).times(1);

    EasyMock.expect(maxSizeDeck.size()).andReturn(deckSizeRemoveTwo).times(1);
    EasyMock.expect(maxSizeDeck.get(1)).andReturn(secondCard).times(1);
    EasyMock.expect(maxSizeDeck.remove(1)).andReturn(secondCard).times(1);

    EasyMock.expect(maxSizeDeck.size()).andReturn(1).times(2);

    EasyMock.expect(firstCard.getCardType()).andReturn(CardType.DEFUSE).anyTimes();

    EasyMock.expect(instantiator.createCard(CardType.DEFUSE))
            .andReturn(firstCard).anyTimes();

    EasyMock.expect(secondCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN).anyTimes();

    EasyMock.expect(instantiator.createCard(CardType.EXPLODING_KITTEN))
            .andReturn(secondCard).anyTimes();

    EasyMock.replay(rand, instantiator, firstCard, maxSizeDeck, secondCard);

    Deck deck = new Deck(maxSizeDeck, rand,
            0, maxDeckSize, instantiator);
    int numberOfBombs = deck.removeBombs();
    assertEquals(numOfBombs, numberOfBombs);
    assertEquals(1, deck.getDeckSize());

    EasyMock.verify(rand, instantiator);
  }

  @Test
  public void removeBombExplodingKittens() {
    final int maxDeckSize = 42;
    final int expectedDeckSize = 41;
    final int numOfBombs = 1;

    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    List<Card> maxSizeDeck = EasyMock.createMock(List.class);

    for (int index = 0; index < expectedDeckSize; index++) {
      EasyMock.expect(maxSizeDeck.size()).andReturn(maxDeckSize).times(1);
      EasyMock.expect(maxSizeDeck.get(index)).andReturn(firstCard).times(1);
    }

    EasyMock.expect(maxSizeDeck.size()).andReturn(maxDeckSize).times(1);
    EasyMock.expect(maxSizeDeck.get(expectedDeckSize))
            .andReturn(secondCard).times(1);
    EasyMock.expect(maxSizeDeck.remove(expectedDeckSize))
            .andReturn(secondCard).times(1);

    EasyMock.expect(maxSizeDeck.size())
            .andReturn(expectedDeckSize).times(2);

    EasyMock.expect(firstCard.getCardType()).andReturn(CardType.DEFUSE).anyTimes();

    EasyMock.expect(instantiator.createCard(CardType.DEFUSE))
            .andReturn(firstCard).anyTimes();

    EasyMock.expect(secondCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN).anyTimes();

    EasyMock.expect(instantiator.createCard(CardType.EXPLODING_KITTEN))
            .andReturn(secondCard).anyTimes();

    EasyMock.replay(rand, instantiator, firstCard, maxSizeDeck, secondCard);

    Deck deck = new Deck(maxSizeDeck, rand,
            0, maxDeckSize, instantiator);
    int numberOfBombs = deck.removeBombs();
    assertEquals(numOfBombs, numberOfBombs);
    assertEquals(expectedDeckSize, deck.getDeckSize());

    EasyMock.verify(rand, instantiator);
  }

  @Test
  public void insertExplodingKittenAtNegIndexThrowsException() {
    final int maxDeckSize = 42;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    List<Card> oneCardDeck = EasyMock.createMock(List.class);

    EasyMock.replay(rand, instantiator, oneCardDeck);

    Deck deck = new Deck(oneCardDeck, rand,
            0, maxDeckSize, instantiator);
    int indexToInsert = -1;

    UnsupportedOperationException exception =
            assertThrows(UnsupportedOperationException.class, () -> {
              deck.insertExplodingKittenAtIndex(indexToInsert);
            });
    assertEquals("Invalid index. Cannot insert into negative index.",
            exception.getMessage());

    EasyMock.verify(rand, instantiator, oneCardDeck);
  }

  @Test
  public void insertExplodingKittenAtIndexZero() {
    final int maxDeckSize = 42;
    final int cardOneIndex = 1;
    final int cardTwoIndex = 2;
    final int cardThreeIndex = 3;
    final int cardFourIndex = 4;
    final int deckSize = 5;
    final int deckSizeNoExplodingKitten = 4;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    Card card1 = EasyMock.createMock(Card.class);
    Card card2 = EasyMock.createMock(Card.class);
    Card card3 = EasyMock.createMock(Card.class);
    Card card4 = EasyMock.createMock(Card.class);

    EasyMock.expect(card1.getCardType()).andReturn(CardType.NOPE).anyTimes();
    EasyMock.expect(card2.getCardType()).andReturn(CardType.NOPE).anyTimes();
    EasyMock.expect(card3.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card4.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();

    Card explodingKittenCard = EasyMock.createMock(Card.class);
    EasyMock.expect(instantiator.createCard(CardType.EXPLODING_KITTEN))
            .andReturn(explodingKittenCard).once();
    EasyMock.expect(explodingKittenCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN).once();

    List<Card> fourCardsDeck = EasyMock.createMock(List.class);
    EasyMock.expect(fourCardsDeck.size())
            .andReturn(deckSizeNoExplodingKitten).once();

    fourCardsDeck.add(0, explodingKittenCard);
    EasyMock.expectLastCall().once();

    EasyMock.expect(fourCardsDeck.get(0)).andReturn(explodingKittenCard).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardOneIndex)).andReturn(card1).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardTwoIndex)).andReturn(card2).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardThreeIndex)).andReturn(card3).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardFourIndex)).andReturn(card4).anyTimes();

    EasyMock.expect(fourCardsDeck.size()).andReturn(deckSize).once();

    EasyMock.replay(rand, instantiator, card1, card2, card3, card4,
            explodingKittenCard, fourCardsDeck);

    Deck deck = new Deck(fourCardsDeck, rand,
            0, maxDeckSize, instantiator);

    int indexToInsert = 0;

    deck.insertExplodingKittenAtIndex(indexToInsert);

    assertEquals(CardType.EXPLODING_KITTEN, deck.getCardTypeAtIndex(0));
    assertEquals(deckSize, deck.getDeckSize());

    EasyMock.verify(rand, instantiator, card1, card2, card3, card4,
            explodingKittenCard, fourCardsDeck);
  }

  @Test
  public void insertExplodingKittenAtIndexOne() {
    final int maxDeckSize = 42;
    final int cardOneIndex = 1;
    final int cardTwoIndex = 2;
    final int cardThreeIndex = 3;
    final int cardFourIndex = 4;
    final int deckSize = 5;
    final int explodingKittenIndex = 1;
    final int deckSizeNoExplodingKitten = 4;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    Card card1 = EasyMock.createMock(Card.class);
    Card card2 = EasyMock.createMock(Card.class);
    Card card3 = EasyMock.createMock(Card.class);
    Card card4 = EasyMock.createMock(Card.class);

    EasyMock.expect(card1.getCardType()).andReturn(CardType.NOPE).anyTimes();
    EasyMock.expect(card2.getCardType()).andReturn(CardType.NOPE).anyTimes();
    EasyMock.expect(card3.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card4.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();

    Card explodingKittenCard = EasyMock.createMock(Card.class);
    EasyMock.expect(instantiator.createCard(CardType.EXPLODING_KITTEN))
            .andReturn(explodingKittenCard).once();
    EasyMock.expect(explodingKittenCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN).once();

    List<Card> fourCardsDeck = EasyMock.createMock(List.class);
    EasyMock.expect(fourCardsDeck.size())
            .andReturn(deckSizeNoExplodingKitten).once();

    fourCardsDeck.add(explodingKittenIndex, explodingKittenCard);
    EasyMock.expectLastCall().once();

    EasyMock.expect(fourCardsDeck.get(0)).andReturn(card1).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardOneIndex))
            .andReturn(explodingKittenCard).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardTwoIndex)).andReturn(card2).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardThreeIndex)).andReturn(card3).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardFourIndex)).andReturn(card4).anyTimes();

    EasyMock.expect(fourCardsDeck.size()).andReturn(deckSize).once();

    EasyMock.replay(rand, instantiator, card1, card2, card3, card4,
            explodingKittenCard, fourCardsDeck);

    Deck deck = new Deck(fourCardsDeck, rand,
            0, maxDeckSize, instantiator);
    int indexToInsert = 1;

    deck.insertExplodingKittenAtIndex(indexToInsert);

    assertEquals(CardType.EXPLODING_KITTEN, deck.getCardTypeAtIndex(1));
    assertEquals(deckSize, deck.getDeckSize());

    EasyMock.verify(rand, instantiator, card1, card2, card3, card4,
            explodingKittenCard, fourCardsDeck);
  }

  @Test
  public void insertExplodingKittenAtLastIndex() {
    final int maxDeckSize = 42;
    final int cardOneIndex = 1;
    final int cardTwoIndex = 2;
    final int cardThreeIndex = 3;
    final int cardFourIndex = 4;
    final int deckSize = 5;
    final int explodingKittenIndex = 4;
    final int deckSizeNoExplodingKitten = 4;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    Card card1 = EasyMock.createMock(Card.class);
    Card card2 = EasyMock.createMock(Card.class);
    Card card3 = EasyMock.createMock(Card.class);
    Card card4 = EasyMock.createMock(Card.class);

    EasyMock.expect(card1.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card2.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card3.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card4.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();

    Card explodingKittenCard = EasyMock.createMock(Card.class);
    EasyMock.expect(instantiator.createCard(CardType.EXPLODING_KITTEN))
            .andReturn(explodingKittenCard).once();
    EasyMock.expect(explodingKittenCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN).once();

    List<Card> fourCardsDeck = EasyMock.createMock(List.class);
    EasyMock.expect(fourCardsDeck.size())
            .andReturn(deckSizeNoExplodingKitten).once();

    fourCardsDeck.add(explodingKittenIndex, explodingKittenCard);
    EasyMock.expectLastCall().once();

    EasyMock.expect(fourCardsDeck.get(0)).andReturn(card1).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardOneIndex)).andReturn(card2).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardTwoIndex)).andReturn(card3).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardThreeIndex)).andReturn(card4).anyTimes();
    EasyMock.expect(fourCardsDeck.get(cardFourIndex))
            .andReturn(explodingKittenCard).anyTimes();

    EasyMock.expect(fourCardsDeck.size()).andReturn(deckSize).once();

    EasyMock.replay(rand, instantiator, card1, card2, card3, card4,
            explodingKittenCard, fourCardsDeck);

    Deck deck = new Deck(fourCardsDeck, rand,
            0, maxDeckSize, instantiator);
    final int indexToInsert = 4;
    final int indexInserted = 4;

    deck.insertExplodingKittenAtIndex(indexToInsert);

    assertEquals(CardType.EXPLODING_KITTEN, deck.getCardTypeAtIndex(indexInserted));
    assertEquals(deckSize, deck.getDeckSize());

    EasyMock.verify(rand, instantiator, card1, card2, card3, card4,
            explodingKittenCard, fourCardsDeck);
  }

  @Test
  public void insertExplodingKittenAtMaxPlusOneIndexThrowsException() {
    final int maxDeckSize = 42;
    final int deckSizeNoExplodingKitten = 4;

    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    List<Card> cardsDeck = EasyMock.createMock(List.class);

    EasyMock.expect(cardsDeck.size()).andReturn(deckSizeNoExplodingKitten).once();

    EasyMock.replay(rand, instantiator, cardsDeck);

    Deck deck = new Deck(cardsDeck, rand,
            0, maxDeckSize, instantiator);
    final int indexToInsert = 5;

    UnsupportedOperationException exception =
            assertThrows(UnsupportedOperationException.class, () -> {
              deck.insertExplodingKittenAtIndex(indexToInsert);
            });
    assertEquals("Invalid index. Cannot insert into index greater than deck size.",
            exception.getMessage());

    EasyMock.verify(rand, instantiator, cardsDeck);
  }

  @Test
  public void reorderThreeCardsDeckWithThreeNewOrderIndices() {
    final int maxDeckSize = 42;
    final int cardOneIndex = 1;
    final int cardTwoIndex = 2;
    final int deckSizeNoExplodingKitten = 3;
    List<Card> threeCardsDeck = EasyMock.createMock(List.class);

    Card card0 = EasyMock.createMock(Card.class);
    Card card1 = EasyMock.createMock(Card.class);
    Card card2 = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Deck deck = new Deck(threeCardsDeck, rand,
            0, maxDeckSize, instantiator);

    EasyMock.expect(card0.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card1.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card2.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN)
            .anyTimes();

    EasyMock.expect(threeCardsDeck.size())
            .andReturn(deckSizeNoExplodingKitten).anyTimes();
    EasyMock.expect(threeCardsDeck.set(cardTwoIndex, card0))
            .andReturn(card2).anyTimes();
    EasyMock.expect(threeCardsDeck.set(cardOneIndex, card1))
            .andReturn(card1).anyTimes();
    EasyMock.expect(threeCardsDeck.set(0, card2)).andReturn(card0).anyTimes();

    EasyMock.expect(threeCardsDeck.get(0)).andReturn(card0).once();
    EasyMock.expect(threeCardsDeck.get(cardOneIndex)).andReturn(card1).once();
    EasyMock.expect(threeCardsDeck.get(cardTwoIndex)).andReturn(card2).once();
    EasyMock.expect(threeCardsDeck.get(0)).andReturn(card2).once();
    EasyMock.expect(threeCardsDeck.get(cardOneIndex)).andReturn(card1).once();
    EasyMock.expect(threeCardsDeck.get(cardTwoIndex)).andReturn(card0).once();

    final int zeroIndex = 0;
    final int oneIndex = 1;
    final int twoIndex = 2;

    int[] newOrderIndices = {twoIndex, oneIndex, zeroIndex};

    EasyMock.replay(rand, instantiator, threeCardsDeck, card0, card1, card2);
    deck.reorderDeckCards(newOrderIndices, threeCardsDeck);

    assertEquals(card2, threeCardsDeck.get(0));
    assertEquals(card1, threeCardsDeck.get(cardOneIndex));
    assertEquals(card0, threeCardsDeck.get(cardTwoIndex));

    EasyMock.verify(rand, instantiator, threeCardsDeck, card0, card1, card2);
  }

  @Test
  public void reorderThreeCardsDeckWithFourNewOrderIndices() {
    final int maxDeckSize = 42;
    final int deckSizeNoExplodingKitten = 3;
    List<Card> threeCardsDeck = EasyMock.createMock(List.class);

    Card card0 = EasyMock.createMock(Card.class);
    Card card1 = EasyMock.createMock(Card.class);
    Card card2 = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Deck deck = new Deck(threeCardsDeck, rand,
            0, maxDeckSize, instantiator);

    EasyMock.expect(card0.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card1.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card2.getCardType()).
            andReturn(CardType.EXPLODING_KITTEN).anyTimes();

    EasyMock.expect(threeCardsDeck.size())
            .andReturn(deckSizeNoExplodingKitten).anyTimes();

    final int zeroIndex = 0;
    final int oneIndex = 1;
    final int twoIndex = 2;
    final int threeIndex = 3;
    int[] newOrderIndices = {twoIndex, oneIndex, zeroIndex, threeIndex};

    EasyMock.replay(rand, instantiator, threeCardsDeck, card0, card1, card2);

    IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> {
              deck.reorderDeckCards(newOrderIndices, threeCardsDeck);
            });
    assertEquals("Mismatch: The number of indices (4) does not " +
                    "match the number of cards (3).",
            exception.getMessage());

    EasyMock.verify(rand, instantiator, threeCardsDeck, card0, card1, card2);
  }

  @Test
  public void reorderThreeCardsDeckWithInvalidOrderIndices() {
    final int maxDeckSize = 42;
    final int deckSizeNoExplodingKitten = 3;
    List<Card> threeCardsDeck = EasyMock.createMock(List.class);

    Card card0 = EasyMock.createMock(Card.class);
    Card card1 = EasyMock.createMock(Card.class);
    Card card2 = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Deck deck = new Deck(threeCardsDeck, rand, 0,
            maxDeckSize, instantiator);

    EasyMock.expect(card0.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card1.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card2.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN).anyTimes();

    EasyMock.expect(threeCardsDeck.size())
            .andReturn(deckSizeNoExplodingKitten).anyTimes();

    final int negativeOneIndex = -1;
    final int oneIndex = 1;
    final int twoIndex = 2;

    int[] newOrderIndices = {twoIndex, oneIndex, negativeOneIndex};

    EasyMock.replay(rand, instantiator, threeCardsDeck, card0, card1, card2);

    IndexOutOfBoundsException exception =
            assertThrows(IndexOutOfBoundsException.class, () -> {
              deck.reorderDeckCards(newOrderIndices, threeCardsDeck);
            });
    assertEquals("Index out of range: -1. Valid range is 0 to 2.",
            exception.getMessage());

    EasyMock.verify(rand, instantiator, threeCardsDeck, card0, card1, card2);
  }

  @Test
  public void reorderThreeCardsDeckWithThreeIndicesGreaterDeckSize() {
    final int maxDeckSize = 42;
    final int deckSizeNoExplodingKitten = 3;
    List<Card> threeCardsDeck = EasyMock.createMock(List.class);

    Card card0 = EasyMock.createMock(Card.class);
    Card card1 = EasyMock.createMock(Card.class);
    Card card2 = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Deck deck = new Deck(threeCardsDeck, rand,
            0, maxDeckSize, instantiator);

    EasyMock.expect(card0.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card1.getCardType()).andReturn(CardType.SHUFFLE).anyTimes();
    EasyMock.expect(card2.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN)
            .anyTimes();

    EasyMock.expect(threeCardsDeck.size())
            .andReturn(deckSizeNoExplodingKitten).anyTimes();

    final int threeIndex = 3;

    int[] newOrderIndices = {threeIndex, threeIndex, threeIndex};

    EasyMock.replay(rand, instantiator, threeCardsDeck, card0, card1, card2);

    IndexOutOfBoundsException exception =
            assertThrows(IndexOutOfBoundsException.class, () -> {
              deck.reorderDeckCards(newOrderIndices, threeCardsDeck);
            });
    assertEquals("Index out of range: 3. Valid range is 0 to 2.",
            exception.getMessage());

    EasyMock.verify(rand, instantiator, threeCardsDeck, card0, card1, card2);
  }
}
