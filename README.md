Design, plan and implement a Memory Match Game for Android native. Use Kotlin or Java. Use of frameworks / libraries is allowed and encouraged if they get the job done. It should have the following features:


+ A 4x4 grid visualization, where each cell is a Card

+ There are 8 different types of cards, 2 of each, thus 16 in total

+ Each card has the type drawn on its face: it can be an image, a character, a number, an emoji, a color, an icon, your choice

  - If you go for images: download a number of images from the internet and name them as 1.png, 2.png s.o.: https://www.pexels.com/search/animals/?size=small&orientation=square

  - If you go for characters: A, B, C, D s.o.

  - If you go for numbers: 1, 2, 3, 4 s.o.

  - If you go for emojis: https://getemoji.com/

  - If you go for colors: https://coolors.co/

  - If you go for icons: https://material.io/resources/icons/ 

+ The cards’ back sides are exactly the same, indistinguishable from one another

+ 1 button: Start game (it resets the game, randomizes the cards and hides their faces


+ Rules of the game:

  - At the beginning of the game, all cards are facing down

  - The player can only flip a card that is not yet flipped

  - There can never be more than 2 cards flipped

  - If 2 cards are flipped:

    - Either they are the same => they are removed from the grid

    - Either they are different => they are flipped back (so their faces are hidden again)

  -The game ends when there are no more cards in the grid


+ Scoring:

  - The score is shown all the time and start from 0

  - A match is valued with +2

  - A miss is valued with -1

  - The score can’t go lower than 0


+ Bonus functionality:

  - A Timer displaying the passed time

    - Factor the time of completion in the final score like this: points * time_in_seconds_rounded_up

  - A Scoreboard activity where all previous local scores are displayed: name, date, score

    - When completing the game, the player should be asked to input 3 characters as name

    - Use a database

+ A Settings activity allowing to set the size of the grid 

  - default is 4, min is 2, max is 8

  - Obviously, the main activity should display the grid in the proper size

  - The values should be persisted if I close the game and restart
  
  
