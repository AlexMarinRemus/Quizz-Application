 ## Multiplayer Screen


<u>Name label</u>
* The name chosen by the user is displayed together with a colon sign and the accumulated points.


<u>Points label</u>
* The points accumulated by the user are displayed next to the chosen name. The label is automatically updated after answering the question.

<b>Possible bugs</b>
* The username may be too long for the label, which could result in it getting trimmed or being too big to fit in the label box, thus affecting the UI appearance.
* The user might accumulate too many points for it to be possible to be displayed in the label box, which can affect the game quality and the UI appearance.
  
<b>Our solutions</b>
* We increased the size of the labels and of the label boxes.
* The maximum amount of points a user can obtain is 4200, therefore we increased the size of the points label in such a way that this number fits.

<b>Other possible solutions</b>
* Limit the number of characters the user can use while selecting the name.


<u>Correct/Incorrect answers region and question counter</u>
* The question counter is increased every time a new question is displayed. The game has a total of 20 questions, therefore the counter starts from 1 and goes up to 20.
* We keep track of the player's answers by updating the corresponding region as follows: if the user answers correctly, a green check mark is displayed; otherwise, a red X is displayed. The region is updated after answering each question.


<u>Jokers region and jokers buttons</u>
* The three jokers buttons are placed in a special region so that they are easy to use and to be seen.
* Each of the joker buttons has a comprehensible name. Each of them can be used once per game.
* Once a joker has been used, the corresponding button becomes invisible for the rest of the game.


<b>Possible bugs</b>
* The buttons may still be enabled after the user has submitted the answer, in which case the jokers have no effect. The exception is the “Less time for others” joker in multiplayer mode.
* Questions of type 5 are not in the multiple choice format, therefore the “Eliminate wrong” joker has no use. If it is enabled, it can be easily wasted.


<b>Our solutions</b>
* Disable the jokers after the answer has been submitted, except for the “Less time for others” in multiplayer mode.
* Disable the “Eliminate joker” button when the question is of type 5.


<u>Emoji region and emoji buttons</u>
* There is a special region for emoji interaction.
* If the emoji button is pressed, 4 other buttons become visible, each of them representing one emoji. Once pressed, they become invisible again and the selected emoji is displayed to all the other players.
* Emojis can be used indefinitely many times.


<u>Question label</u>
* Each of the questions is displayed in a certain area.
* The question gets updated after the timer runs out.


<b>Possible bugs</b>
* The question might be too long for the label, resulting in it being cropped out which affects the overall game quality.
  
<b>Our solution</b>
* Expand the question label and change the font size.


<u>Answer labels</u>
* For multiple choice questions there are 4 answers, each of them being written in a specific label, placed next to a button. Once the user has selected and submitted an answer, it will either turn green or red depending on if it is correct or not.
* For questions of type 5, the user can write the answer in a textbox and after submitting, the correct answer will be displayed.
* The answers are updated according to each question.


<b>Possible bugs</b>
* The answer might be too long for the label, resulting in it being cropped out which affects the overall game quality.
* The user introduces an invalid answer, such as a non-int, or no answer at all.
  
<b>Our solution</b>
* Expand the answer labels and change the font size.
* The application treats invalid answers as non-existent, therefore an error is thrown and nothing happens except that the points remain unchanged.


<u>Submit button</u>
* Once an answer is selected, the submit button is enabled and the user can check if the choice was correct or not.
* Once everyone submits or the timer runs out, the question is changed.

<b>Possible bugs</b>
* The submit button may be enabled before the answer is selected. The user can submit blank answers, which not only brings no points, but it also can interfere with the functionality of the timer.
  
<b>Our solution</b>
* Unable the submit button unless an answer is selected.


<u>Timer label</u>
* The timer is set to 20 seconds for each question. It is synchronized for all the players.
* Once everyone answers, if the time left is more than 3 seconds, the timer will be set to 3 seconds for a faster transition. This way the user will also have enough time to see whether the answer is correct or not.


<b>Possible bugs</b>
* The timers might be slightly unsynchronized for players.


<u>Players label</u>
* A list of all the players in the game is displayed.
* Once a player submits an answer, their username in the players label will turn green if the answer was correct, or red otherwise, so that everyone can see how the others have answered.
* The player label colors are updated back to black for everyone once the question is refreshed.
* The total number of players that can play at once is 10, therefore there is a maximum of 9 players displayed, representing the opponents.


<u>Radio buttons</u>
* There are four radio buttons associated with the answers. Pressing one of them means selecting an answer and they will be disabled after the player presses the submit button. The buttons will be then enabled again once the question is refreshed.


<u>Leave button</u>
* In case a player wants to leave the current game, the leave button will return to the splash screen.
* If the button is pressed, a pop-up window will appear, asking for confirmation.


## Splash Screen


<u>Single player button</u>
* Pressing the button while the name text field is empty will lead to displaying an error.
* Pressing the button while the name text field has a string will start the singleplayer scene giving access for playing the game and will add the player to the single player database.


<u>Help button</u>
* Pressing the help button will lead to opening the scene that contains information about the game for the player.


<u>Multiplayer button</u>
* Pressing the button while the name text field is empty will lead to displaying an error with the message “Fill all required fields!”.
* Pressing the button while the name text field contains a name that is already in the waiting room will lead to displaying an error with the message “This name already exists!”.
* Pressing the button while the waiting room is full will lead to displaying an error with the message “Waiting Room is full, please join later!”.
* Pressing the button while the name text field has a string which does not appear in the list of names from the waiting room will lead to displaying the waiting room scene.


<u>Name text field</u>
* After pressing the text field, the user will be able to input an username which will be used in the game.
* Pressing the button multiple times will not lead to any error or bug.


<u>Leaderboard labels</u>
* The user and corresponding score for each of the top ten players from the singleplayer database will be displayed in the labels.
* Pressing the button multiple times will not lead to any error or bug.


## Help Screen

<u>Back button</u>
* Pressing the back button will lead to opening the splash screen.
* It cannot be pressed multiple times.


## Single Player Screen 
<u>Name label</u>

* The name chosen by the user is displayed together with a colon sign and the accumulated points.


<u>Points label</u>
* The points accumulated by the user are displayed next to the chosen name. The label is automatically updated after answering the question.


<b>Possible bugs</b>
* The username may be too long for the label, which could result in it getting trimmed or being too big to fit in the label box, thus affecting the UI appearance.
* The user might accumulate too many points for it to be possible to be displayed in the label box, which can affect the game quality and the UI appearance.
  
<b>Our solutions</b>
* We increased the size of the labels and of the label boxes.
* The maximum amount of points a user can obtain is 4200, therefore we increased the size of the points label in such a way that this number fits.
  
<b>Other possible solutions</b>
* Limit the number of characters the user can use while selecting the name.


<u>Correct/Incorrect answers region and question counter</u>
* The question counter is increased every time a new question is displayed. The game has a total of 20 questions, therefore the counter starts from 1 and goes up to 20.
* We keep track of the player's answers by updating the corresponding region as follows: if the user answers correctly, a green check mark is displayed; otherwise, a red X is displayed. The region is updated after answering each question.


<u>Jokers region and jokers buttons</u>
* The three jokers buttons are placed in a special region so that they are easy to use and to be seen.
* Each of the joker buttons has a comprehensible name. Each of them can be used once per game.
* Once a joker has been used, the corresponding button becomes invisible for the rest of the game.

<b>Possible bugs</b>
* The buttons may still be enabled after the user has submitted the answer, in which case the jokers have no effect.
* Questions of type 5 are not in the multiple choice format, therefore the “Eliminate wrong” joker has no use. If it is enabled, it can be easily wasted.
  
<b>Our solutions</b>
* Disable the jokers after the answer has been submitted.
* Disable the “Eliminate joker” button when the question is of type 5.


<u>Question label</u>
* Each of the questions is displayed in a certain area.
* The question gets updated after the timer runs out.


<b>Possible bugs</b>
* The question might be too long for the label, resulting in it being cropped out which affects the overall game quality.

<b>Our solution</b>
* Expand the question label and change the font size.


<u>Answer labels</u>
* For multiple choice questions there are 4 answers, each of them being written in a specific label, placed next to a button. Once the user has selected and submitted an answer, it will either turn green or red depending on if it is correct or not.
* For questions of type 5, the user can write the answer in a textbox and after submitting, the correct answer will be displayed.
* The answers are updated according to each question.


<b>Possible bugs</b>
* The answer might be too long for the label, resulting in it being cropped out which affects the overall game quality.
* The user introduces an invalid answer, such as a non-int, or no answer at all.

<b>Our solutions</b>
* Expand the answer labels and change the font size.
* The application treats invalid answers as non-existent, therefore an error is thrown and nothing happens except that the points remain unchanged.


<u>Submit button</u>
* Once an answer is selected, the submit button is enabled and the user can check if the choice was correct or not.
* Once everyone submits or the timer runs out, the question is changed.


<b>Possible bugs</b>
* The submit button may be enabled before the answer is selected. The user can submit blank answers, which not only brings no points, but it can throw errors.
  Our solution
* Unable the submit button unless an answer is selected.


<u>Radio buttons</u>
* There are four radio buttons associated with the answers. Pressing one of them means selecting an answer and they will be disabled after the player presses the submit button. The buttons will be then enabled again once the question is refreshed.


<u>Timer label</u>
* The timer is set to 20 seconds for each question.
* Once the user answers, if the time left is more than 3 seconds, the timer will be set to 3 seconds for a faster transition. This way the user will also have enough time to see whether the answer is correct or not.


<u>Leave button</u>
* In case a player wants to leave the current game, the leave button will return to the splash screen.
* If the button is pressed, a pop-up window will appear, asking for confirmation.




## Leaderboard screen - single player mode 


<u>Names pane</u>
* After the game is over, the player will be able to see their score in relation with other players. A global leaderboard is stored in the database, and the top 10 highest scores are displayed, along with the usernames of the players that have obtained them.
* If the player is in the top 10 players with the highest scores, their username will be displayed in the corresponding position.
* If the player is not in the top 10 players with the highest scores, an additional label is displayed, containing the position of the player, the username and the score.
* The username of the current player will be displayed in bold text format on the leaderboard.


<u>Return to main menu button</u>
* The player can return to the splash screen by pressing the “Go to menu” button.


##Leaderboard screen - multiplayer mode


The leaderboard screen is displayed twice, the first time being after 10 questions and the second time being when the game is over.</b>


<u>Names pane</u>
* The players will be able to see the leaderboard containing the usernames and corresponding scores. This is decided by sorting the accumulated points, from highest to lowest.
* The position of the player in both the intermediate and final leaderboards is displayed
  in bold text format.


<u>Return to main menu button</u>
* The player can return to the splash screen by pressing the “Go to menu” button.




<b>Possible bugs</b>
* If in the intermediary leaderboard a player has gotten the first place, but by the end of the game the user has been exceeded by another player, both of their usernames will be displayed in bold text format.


<b>Our solution</b>
* Refresh the leaderboard screen before loading it.
  Waiting room scene


<u>Players table view</u>
* When a player joins the lobby, the username will be displayed in the table view.
* We made sure that the usernames are unique by checking for each name if it has already been selected by another player before entering the waiting room.


<u>Start button</u>
* Once everyone is in the lobby, any of the players can press the “Start” button and all of the users are being sent to the multiplayer screen.


<u>Players counter</u>
* Every time a new player joins the multiplayer, the player counter will be increased for all the players in the waiting room so that everyone has a good understanding on how many users are in the lobby.
* If a player tries to enter a full waiting room, a pop-up window is going to appear, restricting them from this action.
* If a player leaves the waiting room, the counter will be decreased and the username will be removed from the table view on everyone’s screens.
* A player can join the waiting room again in case they leave.


<u>Leave button</u>
* In case a player wants to leave the current game, the leave button will return to the splash screen.
* If the button is pressed, a pop-up window will show up, asking for confirmation.
* If a player leaves the waiting room, and the game starts, and the player tries to join the lobby again, they will be redirected to a new one.

## Bugs

<b>General bugs</b>
* If a player leaves a multiplayer game, and then tries to play that mode again, that will not be possible.
* If a player leaves during the game, when the current question time runs out the player will be forced back to the question.
 
<b>Solutions</b>
* Solved by resetting the game settings to the condition it was before the game.
* The timer stops for the player who leaves so the next question won’t be advanced for them.


<b>Things to improve</b>
* The multiplayer game can be started with only one player.
* Sound effects.