package domain.game;

import domain.Card;
import domain.CardType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  private final static int PLAYER_ID_ONE = 1;
  private final static int PLAYER_ID_TWO = 2;
  private final static int PLAYER_ID_THREE = 3;
  private final static int PLAYER_ID_FOUR = 4;

  @Test
  public void addDefuseToPlayer() {
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Card defuse = EasyMock.createMock(Card.class);
    EasyMock.expect(defuse.getCardType()).andReturn(CardType.DEFUSE);

    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);

    EasyMock.replay(defuse, instantiator);
    domain.game.Player newPlayer = new domain.game.Player(1, instantiator);
    newPlayer.addDefuse(defuse);

    assertEquals(newPlayer.getHandSize(), 1);
    EasyMock.verify(defuse, instantiator);
  }

  @Test
  public void addNonDefuseToPlayerWhenShouldBeDefuse() {
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Card defuse = EasyMock.createMock(Card.class);
    EasyMock.expect(defuse.getCardType()).andReturn(CardType.SHUFFLE);

    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);

    EasyMock.replay(defuse, instantiator);
    domain.game.Player newPlayer = new domain.game.Player(1, instantiator);
    String expectedMessage = "Must Add Defuse Card";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      newPlayer.addDefuse(defuse);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
    EasyMock.verify(defuse, instantiator);
  }

  @Test
  public void addToEmptyHand() {
    Card attack = EasyMock.createMock(Card.class);
    EasyMock.expect(attack.getCardType()).andStubReturn(CardType.SHUFFLE);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();

    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.replay(attack, instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(attack);

    assertEquals(1, player.getHandSize());
    assertEquals(attack, player.getCardAt(0));

    try {
      player.getCardAt(-1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid Index");
    }

    try {
      player.getCardAt(1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid Index");
    }

    EasyMock.verify(attack, instantiator);
  }

  @Test
  public void addToNonEmptyHand() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);

    Card mark = EasyMock.createMock(Card.class);
    EasyMock.expect(mark.getCardType()).andStubReturn(CardType.SHUFFLE);
    EasyMock.replay(mark);

    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();

    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(nope);
    player.addCardToHand(mark);

    try {
      player.getCardAt(-1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid Index");
    }

    try {
      player.getCardAt(2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid Index");
    }

    assertEquals(player.getHandSize(), 2);
    assertEquals(player.getCardAt(1), mark);
    EasyMock.verify(nope, mark, instantiator);
  }

  @ParameterizedTest
  @EnumSource(names = {"DEFUSE", "NOPE", "SHUFFLE",


  })
  public void getIndexOfCard(CardType cardType) {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);


    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(nope);
    EasyMock.verify(nope, instantiator);

    assertEquals(player.getIndexOfCard(nope.getCardType()), 0);
  }

  @Test
  public void getIndexOfCardDupe() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);


    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(nope);
    player.addCardToHand(nope);
    EasyMock.verify(nope, instantiator);

    assertEquals(player.getIndexOfCard(nope.getCardType()), 0);
  }

  @Test
  public void getIndexOfCardSecond() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);

    Card mark = EasyMock.createMock(Card.class);
    EasyMock.expect(mark.getCardType()).andStubReturn(CardType.SHUFFLE);
    EasyMock.replay(mark);

    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(mark);
    player.addCardToHand(nope);
    EasyMock.verify(nope, instantiator, mark);

    assertEquals(player.getIndexOfCard(nope.getCardType()), 1);
  }

  @Test
  public void getIndexOfCardInvalid() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);

    Card mark = EasyMock.createMock(Card.class);
    EasyMock.expect(mark.getCardType()).andStubReturn(CardType.SHUFFLE);
    EasyMock.replay(mark);

    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(mark);
    player.addCardToHand(nope);

    try {
      player.getIndexOfCard(CardType.DEFUSE);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No Card Found");
    }
    EasyMock.verify(nope, instantiator, mark);
  }

  @Test
  public void removeOneCard() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);

    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(nope);
    CardType cardType = player.removeCardFromHand(0);
    assertEquals(player.getHandSize(), 0);
    assertEquals(cardType, CardType.NOPE);
    EasyMock.verify(nope, instantiator);

  }

  @Test
  public void removeTwoCards() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);

    Card mark = EasyMock.createMock(Card.class);
    EasyMock.expect(mark.getCardType()).andStubReturn(CardType.SHUFFLE);
    EasyMock.replay(mark);

    Card skip = EasyMock.createMock(Card.class);
    EasyMock.expect(skip.getCardType()).andStubReturn(CardType.DEFUSE);
    EasyMock.replay(skip);

    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(mark);
    player.addCardToHand(nope);
    player.addCardToHand(skip);
    CardType cardType = player.removeCardFromHand(2);
    assertEquals(player.getHandSize(), 2);
    assertEquals(cardType, CardType.DEFUSE);
    EasyMock.verify(nope, instantiator, mark, skip);

  }

  @Test
  public void removeThreeCards() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);

    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(nope);
    player.addCardToHand(nope);
    CardType cardType = player.removeCardFromHand(1);
    assertEquals(player.getHandSize(), 1);
    assertEquals(cardType, CardType.NOPE);
    EasyMock.verify(nope, instantiator);

  }

  @Test
  public void removeOneCardInvalid() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);

    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(nope);
    try {
      player.removeCardFromHand(1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No Card Found");
    }
    EasyMock.verify(nope, instantiator);

  }

  @Test
  public void removeOneCardNegativeOne() {
    Card nope = EasyMock.createMock(Card.class);
    EasyMock.expect(nope.getCardType()).andStubReturn(CardType.NOPE);
    EasyMock.replay(nope);

    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(nope);
    try {
      player.removeCardFromHand(-1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No Card Found");
    }
    EasyMock.verify(nope, instantiator);

  }

  @Test
  public void removeFromEmptyHand() {
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    try {
      player.removeCardFromHand(0);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No Card Found");
    }
    EasyMock.verify(instantiator);

  }


  @Test
  public void shuffleHandTwoCards() {
    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.expect(rand.nextInt(2)).andReturn(0);
    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(rand, instantiator);


    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(secondCard);
    player.addCardToHand(firstCard);
    player.shuffleHand();

    assertEquals(player.getCardAt(0), firstCard);
    EasyMock.verify(rand, instantiator);
  }

  @Test
  public void shuffleHandZeroCards() {
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.replay(rand, instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.shuffleHand();
    assertEquals(player.getHandSize(), 0);
    EasyMock.verify(rand, instantiator);
  }

  @Test
  public void shuffleDeckThreeCardsDupe() {
    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);

    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    final int randUpperBoundOne = 3;
    final int randUpperBoundTwo = 2;
    EasyMock.expect(rand.nextInt(randUpperBoundOne)).andReturn(0);
    EasyMock.expect(rand.nextInt(randUpperBoundTwo)).andReturn(0);
    EasyMock.replay(rand, firstCard, secondCard, instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);
    player.addCardToHand(firstCard);
    player.addCardToHand(firstCard);
    player.addCardToHand(secondCard);

    player.shuffleHand();

    assertEquals(player.getCardAt(0), firstCard);
    assertEquals(player.getCardAt(1), secondCard);
    assertEquals(player.getCardAt(2), firstCard);
    EasyMock.verify(rand, firstCard, secondCard, instantiator);
  }


  @Test
  public void exploded() {
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Random rand = EasyMock.createMock(Random.class);

    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(0, instantiator);

    assertFalse(player.getIsDead());

    player.setIsDead();

    assertTrue(player.getIsDead());

    EasyMock.verify(instantiator);
  }

  @ParameterizedTest
  @ValueSource(ints = {PLAYER_ID_ONE, PLAYER_ID_TWO, PLAYER_ID_THREE, PLAYER_ID_FOUR})
  public void getPlayerID(int playerID) {
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    Random rand = EasyMock.createMock(Random.class);

    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    EasyMock.replay(instantiator);

    domain.game.Player player = new domain.game.Player(playerID, instantiator);

    assertEquals(player.getPlayerID(), playerID);

    EasyMock.verify(instantiator);
  }

  @Test
  public void hasCardEmptyHand() {
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);
    EasyMock.replay(rand, instantiator);


    domain.game.Player player = new domain.game.Player(0, instantiator);

    assertFalse(player.hasCard(CardType.NOPE));
    EasyMock.verify(rand, instantiator);
  }

  @Test
  public void hasOneCardNotNope() {
    Card firstCard = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);

    EasyMock.expect(firstCard.getCardType()).andReturn(CardType.DEFUSE);

    EasyMock.replay(rand, instantiator, firstCard);


    domain.game.Player player = new domain.game.Player(0, instantiator);

    player.addCardToHand(firstCard);

    assertFalse(player.hasCard(CardType.NOPE));
    EasyMock.verify(rand, instantiator, firstCard);
  }

  @Test
  public void hasOneCardNope() {
    Card firstCard = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);

    EasyMock.expect(firstCard.getCardType()).andReturn(CardType.NOPE);

    EasyMock.replay(rand, instantiator, firstCard);


    domain.game.Player player = new domain.game.Player(0, instantiator);

    player.addCardToHand(firstCard);

    assertTrue(player.hasCard(CardType.NOPE));
    EasyMock.verify(rand, instantiator, firstCard);
  }

  @Test
  public void hasThreeCardsOneNope() {
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);
    Card thirdCard = EasyMock.createMock(Card.class);
    Random rand = EasyMock.createMock(Random.class);
    domain.game.Instantiator instantiator = EasyMock.createMock(domain.game.Instantiator.class);
    EasyMock.expect(instantiator.createRandom()).andReturn(rand);
    ArrayList<Card> cardList = new ArrayList<>();
    EasyMock.expect(instantiator.createCardList()).andReturn(cardList);

    EasyMock.expect(firstCard.getCardType()).andReturn(CardType.DEFUSE);
    EasyMock.expect(secondCard.getCardType()).andReturn(CardType.DEFUSE);
    EasyMock.expect(thirdCard.getCardType()).andReturn(CardType.NOPE);

    EasyMock.replay(rand, instantiator, firstCard, secondCard, thirdCard);

    domain.game.Player player = new domain.game.Player(0, instantiator);

    player.addCardToHand(firstCard);
    player.addCardToHand(secondCard);
    player.addCardToHand(thirdCard);

    assertTrue(player.hasCard(CardType.NOPE));
    EasyMock.verify(rand, instantiator, firstCard, secondCard, thirdCard);
  }
}
