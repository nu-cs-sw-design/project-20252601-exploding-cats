package domain.game;

import domain.Card;
import domain.CardType;
import domain.Deck;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.SecureRandom;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

  private final static int SIX_PLAYERS = 6;
  private final static int FIVE_PLAYERS = 5;
  private final static int ONE_PLAYER = 1;
  private final static int NEGATIVE_ONE_PLAYER = -1;
  private final static int TWO_PLAYERS = 2;
  private final static int FORTY_TWO_DECK_SIZE = 42;
  private final static int FIFTY_SIX_DECK_SIZE = 56;
  private final static int SIXTY_TWO_DECK_SIZE = 62;

  @Test
  public void selectRandomPlayerNoPlayers() {
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(rand.nextInt(0)).andReturn(0);
    EasyMock.replay(rand);
    Deck deck = EasyMock.createMock(Deck.class);

    domain.game.Player[] players = {};
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(0, deck, players, rand, turnTracker);

    try {
      game.selectRandomPlayer();
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), "No players to select from.");
    }
    EasyMock.verify(rand);
  }

  @Test
  public void selectRandomPlayerOnePlayer() {
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(rand.nextInt(1)).andReturn(0);
    EasyMock.replay(rand);
    domain.game.Player playerFirst = EasyMock.createMock(domain.game.Player.class);
    EasyMock.expect(playerFirst.getPlayerID()).andReturn(0);
    EasyMock.replay(playerFirst);
    domain.game.Player[] players = {playerFirst};

    Deck deck = EasyMock.createMock(Deck.class);
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(1, deck, players, rand, turnTracker);

    domain.game.Player player = game.selectRandomPlayer();

    assertEquals(player.getPlayerID(), 0);
    EasyMock.verify(rand);
    EasyMock.verify(playerFirst);
  }

  @Test
  public void selectRandomPlayerTwoPlayers() {
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(rand.nextInt(2)).andReturn(1);
    EasyMock.replay(rand);
    domain.game.Player playerFirst = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerSecond = EasyMock.createMock(domain.game.Player.class);
    EasyMock.expect(playerSecond.getPlayerID()).andReturn(1);
    EasyMock.replay(playerSecond);
    domain.game.Player[] players = {playerFirst, playerSecond};

    Deck deck = EasyMock.createMock(Deck.class);

    int[] turnTracker = {1, 1, 1, 1, 1};
    domain.game.Game game = new domain.game.Game(2, deck, players, rand, turnTracker);

    domain.game.Player player = game.selectRandomPlayer();
    assertEquals(player.getPlayerID(), 1);
    EasyMock.verify(rand);
    EasyMock.verify(playerSecond);
  }

  @Test
  public void selectRandomPlayerThreePlayers() {
    Random rand = EasyMock.createMock(Random.class);
    final int randNextValue = 2;
    final int randNextInput = 3;
    final int numOfPlayers = 3;
    final int expectedPlayerID = 2;
    EasyMock.expect(rand.nextInt(randNextInput))
            .andReturn(randNextValue);
    EasyMock.replay(rand);
    domain.game.Player playerFirst = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerSecond = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerThird = EasyMock.createMock(domain.game.Player.class);
    EasyMock.expect(playerThird.getPlayerID()).andReturn(2);
    EasyMock.replay(playerThird);
    domain.game.Player[] players = {playerFirst, playerSecond, playerThird};

    Deck deck = EasyMock.createMock(Deck.class);

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);
    domain.game.Player player = game.selectRandomPlayer();
    assertEquals(player.getPlayerID(), expectedPlayerID);
    EasyMock.verify(rand);
    EasyMock.verify(playerThird);
  }

  @Test
  public void selectRandomPlayerMaxPlayers() {
    final int randNextValue = 3;
    final int randNextInput = 4;
    final int numOfPlayers = 4;
    final int expectedPlayerID = 3;

    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(rand.nextInt(randNextInput))
            .andReturn(randNextValue);
    EasyMock.replay(rand);
    domain.game.Player playerFirst = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerSecond = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerThird = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerFourth = EasyMock.createMock(domain.game.Player.class);
    final int timesPlayerIDReturned = 3;
    EasyMock.expect(playerFourth.getPlayerID())
            .andReturn(timesPlayerIDReturned);
    EasyMock.replay(playerFourth);
    domain.game.Player[] players = {playerFirst, playerSecond, playerThird, playerFourth};

    Deck deck = EasyMock.createMock(Deck.class);

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    domain.game.Player player = game.selectRandomPlayer();
    assertEquals(player.getPlayerID(), expectedPlayerID);
    EasyMock.verify(rand);
    EasyMock.verify(playerFourth);
  }

  @Test
  public void selectRandomPlayerMaxPlayersSelectFirstPlayer() {
    final int randNextValue = 0;
    final int randNextInput = 4;
    final int numOfPlayers = 4;
    final int expectedPlayerID = 0;

    Random rand = EasyMock.createMock(Random.class);
    EasyMock.expect(rand.nextInt(randNextInput))
            .andReturn(randNextValue);
    EasyMock.replay(rand);
    domain.game.Player playerFirst = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerSecond = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerThird = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerFourth = EasyMock.createMock(domain.game.Player.class);
    EasyMock.expect(playerFirst.getPlayerID())
            .andReturn(expectedPlayerID);
    EasyMock.replay(playerFirst);
    domain.game.Player[] players = {playerFirst, playerSecond, playerThird, playerFourth};

    Deck deck = EasyMock.createMock(Deck.class);

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    domain.game.Player player = game.selectRandomPlayer();
    assertEquals(player.getPlayerID(), expectedPlayerID);
    EasyMock.verify(rand);
    EasyMock.verify(playerFirst);
  }

  @ParameterizedTest
  @ValueSource(ints = {ONE_PLAYER, SIX_PLAYERS})
  public void retrieveNumberOfPlayersException(int numPlayers) {
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player[] players = {};
    EasyMock.replay(deck);

    int[] turnTracker = {1, 1, 1, 1, 1};
    domain.game.Game game = new domain.game.Game(numPlayers, deck, players, new SecureRandom(), turnTracker);

    IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> {
              game.setNumberOfPlayers(numPlayers);
            });
    assertEquals("Number of players must be " +
            "between 2 and 5 inclusive", exception.getMessage());
    EasyMock.verify(deck);
  }

  @ParameterizedTest
  @ValueSource(ints = {TWO_PLAYERS, FIVE_PLAYERS})
  public void retrieveNumberOfPlayersValid(int numPlayers) {
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player[] players = {};

    deck.setNumberOfPlayers(numPlayers);
    EasyMock.expectLastCall().andVoid();

    EasyMock.replay(deck);

    int[] turnTracker = {1, 1, 1, 1, 1};
    domain.game.Game game = new domain.game.Game(numPlayers, deck, players, new SecureRandom(), turnTracker);

    game.setNumberOfPlayers(numPlayers);
    int numPlayersRetrieved = game.getNumberOfPlayers();
    assertEquals(numPlayersRetrieved, numPlayers);
    EasyMock.verify(deck);
  }

  @Test
  public void checkNumberOfPlayersAliveIsOne() {
    final int numOfPlayers = 5;
    final int numOfAlivePlayers = 1;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    EasyMock.expect(player.getIsDead()).andReturn(false);
    final int timesPlayer2DeadReturned = 4;
    EasyMock.expect(player2.getIsDead()).andReturn(true)
            .times(timesPlayer2DeadReturned);
    domain.game.Player[] players = {player, player2, player2, player2, player2};
    Random rand = EasyMock.createMock(Random.class);
    EasyMock.replay(deck, rand, player, player2);

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    assertEquals(game.checkNumberOfAlivePlayers(), numOfAlivePlayers);

    EasyMock.verify(deck, rand, player, player2);
  }

  @Test
  public void checkNumberOfPlayersAliveIsFive() {
    final int numOfPlayers = 5;
    final int numOfAlivePlayers = 5;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    EasyMock.expect(player.getIsDead()).andReturn(false);
    final int timesPlayer2DeadReturned = 4;
    EasyMock.expect(player2.getIsDead()).andReturn(false)
            .times(timesPlayer2DeadReturned);
    domain.game.Player[] players = {player, player2, player2, player2, player2};
    Random rand = EasyMock.createMock(Random.class);

    EasyMock.replay(deck, rand, player, player2);

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    assertEquals(game.checkNumberOfAlivePlayers(), numOfAlivePlayers);

    EasyMock.verify(deck, rand, player, player2);
  }

  @Test
  public void incrementPlayerTurnTwoAlivePlayers() {
    final int numOfPlayers = 2;
    final int playerTurn = 1;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    EasyMock.expect(player2.getIsDead()).andReturn(false);

    EasyMock.replay(deck, player1, player2, rand
    );
    domain.game.Player[] players = {player1, player2};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.incrementPlayerTurn();

    assertEquals(playerTurn, game.getPlayerTurn());

    EasyMock.verify(deck, player1, player2, rand
    );

  }

  @Test
  public void incrementPlayerTurnThirdDeadPlayerSkipped() {
    final int numOfPlayers = 4;
    final int playerTurnStart = 1;
    final int playerTurn = 3;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player3 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player4 = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    EasyMock.expect(player3.getIsDead()).andReturn(true);
    EasyMock.expect(player4.getIsDead()).andReturn(false);

    EasyMock.replay(deck, player1, player2, player3, player4, rand
    );
    domain.game.Player[] players = {player1, player2, player3, player4};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.setCurrentPlayerTurn(playerTurnStart);

    game.incrementPlayerTurn();

    assertEquals(playerTurn, game.getPlayerTurn());

    EasyMock.verify(deck, player1, player2, player3, player4, rand
    );

  }

  @Test
  public void incrementPlayerTurnFourthDeadPlayerSkipped() {
    final int numOfPlayers = 4;
    final int playerTurnStart = 2;
    final int playerTurn = 0;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player3 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player4 = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    EasyMock.expect(player4.getIsDead()).andReturn(true);
    EasyMock.expect(player1.getIsDead()).andReturn(false);

    EasyMock.replay(deck, player1, player2, player3, player4, rand
    );
    domain.game.Player[] players = {player1, player2, player3, player4};
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.setCurrentPlayerTurn(playerTurnStart);
    game.incrementPlayerTurn();

    assertEquals(playerTurn, game.getPlayerTurn());

    EasyMock.verify(deck, player1, player2, player3, player4, rand
    );

  }

  @Test
  public void incrementPlayerTurnFourPlayers() {
    final int numOfPlayers = 4;
    final int playerTurnStart = 3;
    final int playerTurn = 0;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player3 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player4 = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    EasyMock.expect(player1.getIsDead()).andReturn(false);

    EasyMock.replay(deck, player1, player2, player3, player4, rand
    );
    domain.game.Player[] players = {player1, player2, player3, player4};
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.setCurrentPlayerTurn(playerTurnStart);
    game.incrementPlayerTurn();

    assertEquals(playerTurn, game.getPlayerTurn());

    EasyMock.verify(deck, player1, player2, player3, player4, rand
    );
  }

  @Test
  public void playShuffleGameDeckTwoCards() {
    final int numOfPlayers = 5;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player3 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player4 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player5 = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);

    EasyMock.expect(deck.getCardAtIndex(0)).andReturn(firstCard).anyTimes();

    deck.shuffleDeck();
    EasyMock.expectLastCall().andVoid();

    EasyMock.expect(rand.nextInt(2)).andReturn(0).anyTimes();

    EasyMock.replay(deck, player1, player2, player3, player4,
            player5, rand, firstCard, secondCard);
    domain.game.Player[] players = {player1, player2, player3, player4, player5};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    int timesToShuffle = 1;
    game.playShuffle(timesToShuffle);
    assertEquals(deck.getCardAtIndex(0), firstCard);
    assertEquals(timesToShuffle, 1);

    EasyMock.verify(deck, player1, player2, player3, player4,
            player5, rand, firstCard, secondCard);
  }

  @Test
  public void playShuffleGameDeckZeroCards() {
    final int numOfPlayers = 5;
    final int deckSize = 0;
    final int timesShuffled = 1;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player3 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player4 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player5 = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);

    EasyMock.expect(deck.getDeckSize()).andReturn(0).anyTimes();

    deck.shuffleDeck();
    EasyMock.expectLastCall().andVoid();

    EasyMock.replay(deck, player1, player2, player3, player4,
            player5, rand, firstCard, secondCard);
    domain.game.Player[] players = {player1, player2, player3, player4, player5};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    int timesToShuffle = 1;
    game.playShuffle(timesToShuffle);
    assertEquals(deck.getDeckSize(), deckSize);
    assertEquals(timesToShuffle, timesShuffled);

    EasyMock.verify(deck, player1, player2, player3, player4,
            player5, rand, firstCard, secondCard);
  }

  @Test
  public void playShuffleGameDeckThreeCards() {
    final int numOfPlayers = 5;
    final int timesShuffled = 100;
    final int randomInputOne = 3;
    final int randomInputTwo = 2;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player3 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player4 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player5 = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);
    Card firstCard = EasyMock.createMock(Card.class);
    Card secondCard = EasyMock.createMock(Card.class);

    EasyMock.expect(deck.getCardAtIndex(0)).andReturn(firstCard).anyTimes();
    EasyMock.expect(deck.getCardAtIndex(1)).andReturn(firstCard).anyTimes();
    deck.shuffleDeck();
    EasyMock.expectLastCall().andVoid().anyTimes();

    EasyMock.expect(rand.nextInt(randomInputOne)).andReturn(0).anyTimes();
    EasyMock.expect(rand.nextInt(randomInputTwo)).andReturn(0).anyTimes();
    EasyMock.replay(deck, player1, player2, player3, player4,
            player5, rand, firstCard, secondCard);
    domain.game.Player[] players = {player1, player2, player3, player4, player5};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    final int timesToShuffle = 100;
    game.playShuffle(timesToShuffle);
    assertEquals(deck.getCardAtIndex(0), firstCard);
    assertEquals(deck.getCardAtIndex(1), firstCard);
    assertEquals(timesToShuffle, timesShuffled);

    EasyMock.verify(deck, player1, player2, player3, player4,
            player5, rand, firstCard, secondCard);
  }

  @ParameterizedTest
  @EnumSource(names = {"NOPE", "DEFUSE", "SHUFFLE", "EXPLODING_KITTEN",

  })
  public void removeOneCard(CardType cardType) {
    final int numOfPlayers = 2;
    final int playerIndex = 0;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    EasyMock.expect(player.getIndexOfCard(cardType)).andReturn(0);
    EasyMock.expect(player.removeCardFromHand(0))
            .andReturn(cardType);

    EasyMock.expect(player.hasCard(cardType)).andReturn(false).once();

    EasyMock.replay(deck, player, player1, player2, rand
    );
    domain.game.Player[] players = {player, player1, player2};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.removeCardFromHand(playerIndex, cardType);
    assertFalse(game.checkIfPlayerHasCard(playerIndex, cardType));

    EasyMock.verify(deck, player, player1, player2, rand
    );
  }

  @ParameterizedTest
  @EnumSource(names = {"NOPE", "DEFUSE", "SHUFFLE", "EXPLODING_KITTEN",

  })
  public void removeThreeCardDupe(CardType cardType) {
    final int numOfPlayers = 2;
    final int playerIndex = 0;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player1 = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player player2 = EasyMock.createMock(domain.game.Player.class);

    Random rand = EasyMock.createMock(Random.class);

    final int timesIndexAcquired = 3;

    EasyMock.expect(player.getIndexOfCard(cardType)).andReturn(0)
            .times(timesIndexAcquired);

    final int timesCardRemoved = 3;

    EasyMock.expect(player.removeCardFromHand(0))
            .andReturn(cardType).times(timesCardRemoved);

    EasyMock.expect(player.hasCard(cardType)).andReturn(false).once();

    EasyMock.replay(deck, player, player1, player2, rand
    );
    domain.game.Player[] players = {player, player1, player2};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.removeCardFromHand(playerIndex, cardType);
    game.removeCardFromHand(playerIndex, cardType);
    game.removeCardFromHand(playerIndex, cardType);
    assertFalse(game.checkIfPlayerHasCard(playerIndex, cardType));

    EasyMock.verify(deck, player, player1, player2, rand
    );
  }

  @ParameterizedTest
  @ValueSource(ints = {NEGATIVE_ONE_PLAYER, FIVE_PLAYERS})
  public void playExplodingKittenInvalidPlayer(int playerIndex) {
    Deck deck = EasyMock.createMock(Deck.class);

    domain.game.Player[] players = {};

    Random rand = EasyMock.createMock(Random.class);
    EasyMock.replay(deck, rand);

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(2, deck, players, rand, turnTracker);

    Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
      game.playExplodingKitten(playerIndex);
    });
    String actualMessage = exception.getMessage();
    String expectedMessage = "Invalid player index.";
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(deck, rand);
  }

  @Test
  public void playExplodingKittenWithDefuse() {
    final int numOfPlayers = 5;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player[] players = {player, player, player, player, player};
    Random rand = EasyMock.createMock(Random.class);

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    EasyMock.expect(player.hasCard(CardType.DEFUSE)).andReturn(true).anyTimes();
    EasyMock.expect(player.getIsDead()).andReturn(false).anyTimes();

    EasyMock.replay(deck, player, rand
    );

    int playerIndex = 0;
    boolean isPlayerExploded = game.playExplodingKitten(playerIndex);
    assertFalse(isPlayerExploded);

    int numberOfAlivePlayers = game.checkNumberOfAlivePlayers();
    assertEquals(numberOfAlivePlayers, numOfPlayers);

    EasyMock.verify(deck, player, rand
    );
  }

  @Test
  public void playExplodingKittenWithoutDefuse() {
    final int numOfPlayers = 5;
    final int numOfAlivePlayers = 4;
    final int playerIndex = 4;
    final int currentPlayerIndex = 4;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player otherPlayer = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    domain.game.Player[] players = {otherPlayer, otherPlayer, otherPlayer, otherPlayer, player};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.setCurrentPlayerTurn(currentPlayerIndex);
    game.setCurrentPlayerNumberOfTurns(1);
    EasyMock.expect(player.hasCard(CardType.DEFUSE)).andReturn(false).anyTimes();
    EasyMock.expect(player.getIsDead()).andReturn(true).anyTimes();
    player.setIsDead();
    EasyMock.expect(otherPlayer.getIsDead()).andReturn(false).anyTimes();

    EasyMock.replay(deck, player, otherPlayer, rand
    );

    boolean isPlayerExploded = game.playExplodingKitten(playerIndex);
    assertTrue(isPlayerExploded);

    int numberOfAlivePlayers = game.checkNumberOfAlivePlayers();
    assertEquals(numberOfAlivePlayers, numOfAlivePlayers);

    assertEquals(0, game.getNumberOfTurns());

    EasyMock.verify(deck, player, otherPlayer, rand
    );
  }

  @Test
  public void playExplodingKittenWithoutDefuseOneAlivePlayerLeft() {
    final int numOfPlayers = 5;
    final int numOfAlivePlayers = 1;
    final int playerIndex = 4;
    final int currentPlayerIndex = 3;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player deadPlayer = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player alivePlayer = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    domain.game.Player[] players = {deadPlayer, deadPlayer, deadPlayer, alivePlayer, player};

    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.setCurrentPlayerTurn(currentPlayerIndex);
    game.setCurrentPlayerNumberOfTurns(1);

    EasyMock.expect(player.hasCard(CardType.DEFUSE)).andReturn(false).anyTimes();
    EasyMock.expect(player.getIsDead()).andReturn(true).anyTimes();
    player.setIsDead();
    EasyMock.expect(deadPlayer.getIsDead()).andReturn(true).anyTimes();
    EasyMock.expect(alivePlayer.getIsDead()).andReturn(false).anyTimes();

    EasyMock.replay(deck, player, deadPlayer, alivePlayer, rand);

    boolean isPlayerExploded = game.playExplodingKitten(playerIndex);
    assertTrue(isPlayerExploded);

    int numberOfAlivePlayers = game.checkNumberOfAlivePlayers();
    assertEquals(numberOfAlivePlayers, numOfAlivePlayers);

    assertEquals(1, game.getNumberOfTurns());

    EasyMock.verify(deck, player, deadPlayer, alivePlayer, rand
    );
  }

  @Test
  public void playDefuseNegIdxToInsertExplodingKitten() {
    final int currentPlayerIndex = 0;
    final int playerIndex = 0;
    final int idxToInsertExplodingKitten = -1;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    deck.insertExplodingKittenAtIndex(idxToInsertExplodingKitten);
    EasyMock.expectLastCall()
            .andThrow(new UnsupportedOperationException(
                    "Invalid index. " +
                            "Cannot insert into negative index.")).once();
    EasyMock.replay(deck, player, rand);

    domain.game.Player[] players = {player, player, player, player, player};
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(FIVE_PLAYERS, deck, players, rand, turnTracker);

    game.setCurrentPlayerTurn(currentPlayerIndex);

    UnsupportedOperationException exception =
            assertThrows(UnsupportedOperationException.class, () -> {
              game.playDefuse(idxToInsertExplodingKitten, playerIndex);
            });
    assertEquals("Invalid index. Cannot insert into negative index.",
            exception.getMessage());

    EasyMock.verify(deck, player, rand);
  }

  @Test
  public void playDefuseMaxPlusOneIdxToInsertExplodingKitten() {
    final int currentPlayerIndex = 4;
    final int playerIndex = 4;
    final int currentDeckSize = 20;
    final int idxToInsertExplodingKitten = currentDeckSize + 1;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    deck.insertExplodingKittenAtIndex(idxToInsertExplodingKitten);
    EasyMock.expectLastCall()
            .andThrow(new UnsupportedOperationException(
                    "Invalid index. " +
                            "Cannot insert into index greater than deck size."))
            .once();
    EasyMock.replay(deck, player, rand);

    domain.game.Player[] players = {player, player, player, player, player};
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(FIVE_PLAYERS, deck, players, rand, turnTracker);
    game.setCurrentPlayerTurn(currentPlayerIndex);

    UnsupportedOperationException exception =
            assertThrows(UnsupportedOperationException.class, () -> {
              game.playDefuse(idxToInsertExplodingKitten, playerIndex);
            });
    assertEquals("Invalid index. " +
                    "Cannot insert into index greater than deck size.",
            exception.getMessage());

    EasyMock.verify(deck, player, rand);
  }

  @Test
  public void playDefuseValidIdxToInsertExplodingKittenIsCurrentPlayer() {
    final int currentPlayerIndex = 4;
    final int playerIndex = 4;
    final int fiveCardsHand = 5;
    final int defuseIdx = 0;
    final int explodingKittenIdx = 0;

    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);
    Card explodingKittenCard = EasyMock.createMock(Card.class);

    deck.insertExplodingKittenAtIndex(explodingKittenIdx);
    EasyMock.expectLastCall().once();
    EasyMock.expect(player.getIndexOfCard(CardType.DEFUSE)).andReturn(defuseIdx);
    EasyMock.expect(player.removeCardFromHand(defuseIdx)).andReturn(CardType.DEFUSE);

    EasyMock.expect(deck.getCardAtIndex(explodingKittenIdx))
            .andReturn(explodingKittenCard);
    EasyMock.expect(explodingKittenCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN);
    EasyMock.expect(player.getHandSize()).andReturn(fiveCardsHand - 1);

    EasyMock.replay(deck, explodingKittenCard, player, rand);

    domain.game.Player[] players = {player, player, player, player, player};
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(FIVE_PLAYERS, deck, players, rand, turnTracker);
    game.setCurrentPlayerTurn(currentPlayerIndex);
    game.setCurrentPlayerNumberOfTurns(1);

    game.playDefuse(explodingKittenIdx, playerIndex);
    assertEquals(game.getDeckCardType(explodingKittenIdx), CardType.EXPLODING_KITTEN);
    assertEquals(game.getHandSize(playerIndex), fiveCardsHand - 1);
    assertEquals(0, game.getNumberOfTurns());

    EasyMock.verify(deck, explodingKittenCard, player, rand);
  }

  @Test
  public void playDefuseValidIdxToInsertExplodingKittenNotCurrentPlayer() {
    final int currentPlayerIndex = 0;
    final int playerIndex = 4;
    final int fiveCardsHand = 5;
    final int defuseIdx = 0;
    final int explodingKittenIdx = 0;

    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);
    Card explodingKittenCard = EasyMock.createMock(Card.class);

    deck.insertExplodingKittenAtIndex(explodingKittenIdx);
    EasyMock.expectLastCall().once();
    EasyMock.expect(player.getIndexOfCard(CardType.DEFUSE)).andReturn(defuseIdx);
    EasyMock.expect(player.removeCardFromHand(defuseIdx)).andReturn(CardType.DEFUSE);

    EasyMock.expect(deck.getCardAtIndex(explodingKittenIdx))
            .andReturn(explodingKittenCard);
    EasyMock.expect(explodingKittenCard.getCardType())
            .andReturn(CardType.EXPLODING_KITTEN);
    EasyMock.expect(player.getHandSize()).andReturn(fiveCardsHand - 1);

    EasyMock.replay(deck, explodingKittenCard, player, rand);

    domain.game.Player[] players = {player, player, player, player, player};
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(FIVE_PLAYERS, deck, players, rand, turnTracker);
    game.setCurrentPlayerTurn(currentPlayerIndex);
    game.setCurrentPlayerNumberOfTurns(1);

    game.playDefuse(explodingKittenIdx, playerIndex);
    assertEquals(game.getDeckCardType(explodingKittenIdx), CardType.EXPLODING_KITTEN);
    assertEquals(game.getHandSize(playerIndex), fiveCardsHand - 1);
    assertEquals(1, game.getNumberOfTurns());

    EasyMock.verify(deck, explodingKittenCard, player, rand);
  }

  @ParameterizedTest
  @ValueSource(ints = {NEGATIVE_ONE_PLAYER, FIVE_PLAYERS})
  public void playDefuseInvalidPlayerIndex(int playerIndex) {
    final int currentPlayerIndex = 0;
    final int currentDeckSize = 20;
    int idxToInsertExplodingKitten = currentDeckSize + 1;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player player = EasyMock.createMock(domain.game.Player.class);
    Random rand = EasyMock.createMock(Random.class);

    EasyMock.replay(deck, player, rand);

    domain.game.Player[] players = {player, player, player, player, player};
    int[] turnTracker = {1, 1, 1, 1, 1};

    domain.game.Game game = new domain.game.Game(FIVE_PLAYERS, deck, players, rand, turnTracker);
    game.setCurrentPlayerTurn(currentPlayerIndex);

    UnsupportedOperationException exception =
            assertThrows(UnsupportedOperationException.class, () -> {
              game.playDefuse(idxToInsertExplodingKitten, playerIndex);
            });
    assertEquals("Invalid player index.",
            exception.getMessage());

    EasyMock.verify(deck, player, rand);
  }

  @Test
  public void setPlayerNumberOfTurnsTwelveTurns() {
    final int numOfPlayers = 5;
    final int initialPlayerTurn = 3;
    final int numOfTurns = 12;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player playerOne = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerTwo = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerThree = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerFour = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerFive = EasyMock.createMock(domain.game.Player.class);

    Card card = EasyMock.createMock(Card.class);
    domain.game.Player[] players = {playerOne, playerTwo, playerThree, playerFour, playerFive};

    Random rand = EasyMock.createMock(Random.class);
    ;

    EasyMock.replay(deck, rand, playerOne, playerTwo,
            playerThree, playerFour, playerFive, card);
    final int fourthPlayerNumOfTurns = 12;
    int[] turnTracker = {1, 1, 1, fourthPlayerNumOfTurns, 1};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.setCurrentPlayerTurn(initialPlayerTurn);
    game.setCurrentPlayerNumberOfTurns(0);

    game.setPlayerNumberOfTurns();

    assertEquals(game.getNumberOfTurns(), numOfTurns);

    assertEquals(game.getTurnCountOfPlayer(initialPlayerTurn), 1);

    EasyMock.verify(deck, rand, playerOne, playerTwo,
            playerThree, playerFour, playerFive, card);

  }

  @Test
  public void setPlayerNumberOfTurnsAllDifferent() {
    final int numOfPlayers = 5;
    final int initialPlayerTurn = 2;
    final int numOfTurns = 4;
    Deck deck = EasyMock.createMock(Deck.class);
    domain.game.Player playerOne = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerTwo = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerThree = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerFour = EasyMock.createMock(domain.game.Player.class);
    domain.game.Player playerFive = EasyMock.createMock(domain.game.Player.class);

    Card card = EasyMock.createMock(Card.class);
    domain.game.Player[] players = {playerOne, playerTwo, playerThree, playerFour, playerFive};

    Random rand = EasyMock.createMock(Random.class);
    ;

    EasyMock.replay(deck, rand, playerOne, playerTwo,
            playerThree, playerFour, playerFive, card);
    final int secondPlayerNumOfTurns = 2;
    final int thirdPlayerNumOfTurns = 4;
    final int fourthPlayerNumOfTurns = 6;
    final int fifthPlayerNumOfTurns = 8;
    int[] turnTracker = {1, secondPlayerNumOfTurns, thirdPlayerNumOfTurns,
            fourthPlayerNumOfTurns, fifthPlayerNumOfTurns};

    domain.game.Game game = new domain.game.Game(numOfPlayers, deck, players, rand, turnTracker);

    game.setCurrentPlayerTurn(initialPlayerTurn);
    game.setCurrentPlayerNumberOfTurns(0);

    game.setPlayerNumberOfTurns();

    assertEquals(game.getNumberOfTurns(), numOfTurns);

    assertEquals(game.getTurnCountOfPlayer(initialPlayerTurn), 1);

    EasyMock.verify(deck, rand, playerOne, playerTwo,
            playerThree, playerFour, playerFive, card);

  }

}
