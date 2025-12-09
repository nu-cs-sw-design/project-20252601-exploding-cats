package domain.game;

import domain.Card;
import domain.CardType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

  @ParameterizedTest
  @EnumSource(names = {"NOPE", "DEFUSE", "SHUFFLE", "EXPLODING_KITTEN",
  })
  public void getCardType(CardType cardType) {
    Card card = new Card(cardType);
    assertEquals(card.getCardType(), cardType);
  }

}
