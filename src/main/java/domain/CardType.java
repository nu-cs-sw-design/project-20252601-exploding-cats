package domain;

public enum CardType {
	NOPE(InputType.NO_INPUT),
	DEFUSE(InputType.INDEX_INPUT),
	SHUFFLE(InputType.NO_INPUT),
	EXPLODING_KITTEN(InputType.NO_INPUT);

  private final InputType inputType;

  CardType(InputType inputType) {
    this.inputType = inputType;
  }

  public InputType getInputType() {
		return inputType;
	}
}
