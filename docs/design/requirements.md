# Software Requirement Document: Use Cases

_Note: All of the following use cases assume that Exploding Kittens is being played without any expansion packs._

## UC1: Start the Game
**Actor:** Any player\
**Precondition:** All players must share a device to run and play the game altogether.

**Basic Flow:**
1) The system prompts the user for their desired language.
2) The user selects their desired language, and the system sets the resource bundle for the game to that language.
3) The system prompts for the number of players, which must be between 2-5. Let’s call this number N.
4) Excluding any Exploding Kitten and Defuse cards, the system deals 4 random cards from the deck to each player. 
5) The system deals 1 Defuse card to each player, finalizing a hand of 5 cards per player. 
6) The system adds (N-1) Exploding Kitten cards into the remaining deck. 
7) The system adds the remaining (6-N) Defuse cards to the deck. 
8) The system randomly shuffles the deck to create a draw pile. 
9) The system randomly selects one player to take the first turn. 
10) The system notifies the first player that it is their turn.

**Postcondition:** The beginning hands and draw pile for all players are established. The first player’s turn begins.\

## UC2: Draw an Exploding Kitten Card
**Actor:** Player who’s actively taking their turn\
**Precondition:** Player just ended their turn and drew an Exploding Kitten card\

**Basic Flow:**
1) The system notifies the player that they have drawn an Exploding Kitten.
2) The system checks the player’s hand for a Defuse card.
3) **Alternate Flow 1:** No Defuse card is found in the player's deck
   - The system updates the status of the player to dead.
   - The system removes the player from the turn rotation.
   - The system moves all of the player’s cards to the discard pile, including the Exploding Kitten.
   - The system checks the number of players remaining. 
   - **Alternate Flow 1a:** Only 1 player remains 
      - The system notifies all players of the end of the game. 
      - The system declares the last remaining player the winner. 
      - The system ends execution.
   - **Alternate Flow 1b:** More than 1 player remains 
      - The system continues its execution of the next turn, prompting the next player in the turn rotation to take their turn.
4) **Alternate Flow 2:** At least one Defuse card is found.
   - The system automatically uses the Defuse card and informs the user of this action. See UC3 for more details on what playing a Defuse card entails.
   - The system continues its execution and prompts the next player in the turn rotation to take their turn.

**Postcondition:** The player who drew an exploding kitten card is either dead or now down an Exploding Kitten card.\
**Special Condition:** Exploding Kitten cards cannot be “Noped”.

## UC3: Playing a Defuse Card
**Actor:** Player\
**Precondition:** The player has just ended their turn and drawn an Exploding Kitten, but they had at least 1 Defuse card in their hand that the system has automatically decided to apply.\

**Basic Flow:**
1)	The system informs the player that they will be using a Defuse card from their deck.
2)	The system removes one Defuse card from the player’s hand and moves it to the discard pile.
3)	The system prompts the active player to choose a position to re-insert the Exploding Kitten anywhere in the draw pile.
4)	Upon user response, the system inserts the Exploding Kitten card into the specified position in the draw pile.
5)	The system continues its execution and prompts the next player in the turn rotation to take their turn.

**Alternative Flow:** If the user enters an invalid position to re-insert the Exploding Kitten at step 3, the system re-prompts the user until a valid position is entered.\
**Postcondition:** The Defuse card that was previously in the player’s hand has now been moved into a position of the player’s choice in the draw pile.\
**Special Condition:** Playing Defuse cards cannot be “Noped”.

## UC4: Playing a Shuffle Card
**Actor:** Player\
**Precondition:** A player chooses to play the Shuffle card during their active turn.

**Basic Flow:**
1) The user specifies that they wish to play the Shuffle card.
2) The system executes UC5 to allow other players to potentially “Nope” this Shuffle card.
3) The system moves the Shuffle card from the player’s hand to the discard pile.
4) **Alternative Flow 1:** No players “Nope” the Shuffle card
   - The system randomizes the order of the draw pile.
   - The system informs all players that the deck order has been randomized.
5) **Alternative Flow 2:** A player “Nopes” the Shuffle card
   - The system informs the active player that their card has been “Noped”, so the order of the deck has not been randomized.
6) The turn for the active player continues, allowing them to take more actions or end their turn as desired.

**Postcondition:** The Shuffled card has been moved from the active player’s hand to the discard pile. Additionally, either the draw pile may have been randomized, or a Nope card has also been added to the discard pile.

## UC5: Playing a Nope Card
**Actor:** Player \
**Precondition:** The active player has played an action card (or a special combo) that can be “Noped”.

**Basic Flow:**
1) The system detects that the active player has decided to play an action card or special combo that can be “Noped” (e.g. Shuffle, Skip, See the Future, another Nope, 2-3 matching Cat cards, etc.)
2) The system pauses the active player’s turn and asks each player if they would like to play a “Nope” card.
3) **Alternative Flow 1:** No player wants to play a “Nope” card
   - The system completes the action associated with the card that the active player had just played and continues execution of the active player’s turn.
4) **Alternative Flow 2:** A player states that they want to play a “Nope” card.
   - The system checks whether that player has a “Nope” card in their hand.
   - **Alternative Flow 2a:** The player has a “Nope” card. 
     - The system moves the “Nope” card to the discard pile.
     - The system informs the active player that their action has been “noped”.
     - The system re-enters this use case to determine if this any other player wishes to “Nope” this “Nope” card. This cycle continues until no players respond with another “Nope” card.
     - **Alternative Flow 3a:** An odd number of “Nope” cards were played. 
       - The system moves all Nope cards involved and the original action card that was "Noped" to the discard pile.
       - The system performs the original action that set off the chain of “Nope” cards and prompts the active player to continue executive of their turn. 
     - **Alternative Flow 3b:** An even number of “Nope” cards were played. 
       - The system moves all Nope cards involved and the original action card that was "Noped" to the discard pile.
       - The system does not perform the original action that set off the chain of “Nope” cards and instead prompts the active player to continue execution of their turn. 
   - **Alternative Flow 2b:** The player does not have a “Nope” card. 
     - The system tells the player who wished to play a “Nope” card that they do not have one. 
     - The system completes the action associated with the card that the active player had just played, moves the card to the discard pile, and continues execution of the active player’s turn.

**Postcondition:** The original action card and all played ‘Nope’ cards are moved to the discard pile. Moreover,
depending on the number of “Nope” cards in a chain, the original action card that was responded to with a “Nope” may 
or may not have been taken.