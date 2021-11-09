# <ins>**AI-CheckersGame**</ins>
# Description
This is a Checkers Game played between the human user and the AI where you can choose which algorithm to use while playing whether be it random or mini-max. The GUI has been created through **JavaFX** and code written through **JAVA**.

First, we will define the rules of a Checkers Game and then will describe the objective of each class on how it helps in creating the gameplay.

## A. Rules

### <ins>Movement Rules</ins>
 **The ordinary movement of a man:**<br />
   1. A man moves diagonally forward left or right from one square to an immediately
           neighboring vacant square.<br />
   2. When a man reaches the farthest row forward (the king-row: top or bottom row
          for opponent player – marked with red in the diagram)</br>it becomes a king.<br /><br />
 **The ordinary movement of a king (crowned man):**<br />
    3. The king moves from one square diagonally forward or backward, left or right, to
       an immediately neighbor vacant square.<br /><br />

### <ins>Capturing Movement Rules</ins><br />
**While capturing:**<br />
    4. To capture the opponent’s piece, a man moves from one square over a diagonally adjacent and forward square that is occupied by an opponent’s piece and onto a
        vacant square immediately beyond it. On completion of the jump, the captured piece is removed from the board.<br />
    5. The capturing movement of a king is similar to a man, but it can move in both directions, forward and backward direction.<br />
    6. The capturing move of the piece (man or king) is continued until all the jumps are completed.<br /><br />
**Exception: if a man reaches the king-row by means of a capturing move, it then becomes
a king but may not make any further jumps in the same turn.**<br /><br />
    7. All capturing moves are compulsory, whether offered actively or passively. If there are two or more ways to jump, a player might select anyone he wishes, not necessarily that which gains most pieces. Once started, multiple jumps must be carried through to completion.<br /><br />
        
### <ins>Definition of a Win</ins>

**In the end,**<br />
     8. The winner of the game is one who can make the last move; that is, no move is
available to the opponent on his turn to play, either because all his pieces have
been captured or his remaining pieces are all blocked.<br /><br />
### <ins>Definition of a Draw</ins>

**Sometimes the game may lead to a draw, then**<br />
     9. **<ins>The 50-move rule:</ins>** The game shall be declared drawn if, at any stage of the game
         satisfy one of the followings:<br />
       a. Neither player has advanced an uncrowned man towards the king-row
during the previous 50 moves.<br />
       b. No pieces have been removed from the board during the previous 50
moves.<br /><br />

## B. Objective of Each Class

### 1. [AlreadyOccupied](https://github.com/blank0826/AI-CheckersGame/blob/master/AlreadyOccupiedException.java)
----
This class gives an exception when the piece is already occupied.<br />

### 2. [Checkers](https://github.com/blank0826/AI-CheckersGame/blob/master/Checkers.java)
----
This class initializes the game board. it sets properties for the javaFX UI and adds menu giving the option of Algorithms and Settings.<br /><br />
**1. <ins>Algorithms:-</ins>** Random and Mini-max Algorithms.<br /><br/>
**2. <ins>Settings:-</ins>** Reset and Exit.<br />

### 3. [Logic](https://github.com/blank0826/AI-CheckersGame/blob/master/Logic.java)
----
This is the main class of the Checkers Game where the whole game takes place updating the board, moving pieces, and making all the algorithm-based decisions.

### **<ins>Member Functions</ins>**
**1.	Logic (int algoNum)**<br />

Constructor to initialize the board, maintain what functions to be called whenever a piece is moved or <br />

selected, display appropriate messages after each move is played.

**2.	public chooseAlgo(int algo, char[][] game, int depth, String player)**<br />

Function defined so as to switch between the two algorithms.

**3.	public moveMaker()**<br />

Function that determines how the player has moved and then will play for the AI based on the algorithm <br />

that we have choosen.

**4.	public taken (char [][] game, int x1, int y1, int x2, int y2)**<br />

Function implements the feature of capturing a piece. It checks which piece has been captured re-adjust <br />

the board and pieces according to that. Also sees whether the piece should be converted to a king or not.

**5.	public checkKing(char [][] game)**<br />

Function to check if the piece has landed on the last row of its opponent and if it has it should be <br />

converted to a king.

**6.	public checkPos(int x1, int y1, int x2, int y2, char[][] game, boolean errorMessage)**<br />

This ensures that if a piece can be captured then no other move can be made. Besides that it also checks <br />

that if the piece that is being moved stays within the limits or is not breaking the rules of the game.

**7.	public getCorrectPos(String team, char[][] game)**<br />

Functions checks for all the possible moves for capturing a piece and if no piece can be captured then it <br />

will just look for possible moves that can be done in the game. Used when the AI has to play its chance. 

**8.	public captureTurnPerformed(char[][] game, int x1, int y1, int x2, int y2)**<br />

Function to check if the move just performed was a capturing move or not.

**9.	public captureTurnAt(char[][] game, int col, int row)**<br />

Function to find whether a capture move can be done at the given co-ordinates or not.

**10.	public checkCaptureMove(char[][] game, String player)**<br />

Function to check if a capture move exists anywhere on the board for a any player.

**11.	public updateGame(int x1, int y1, int x2, int y2)**<br />

Function that updates the game board matrix after each move.

**12.	public alterGUI()**<br />

Function that updates the GUI of the board.

**13.	public randomAlgo(char[][] game, String player)**<br />

This is the random algorithm and it choose any random possible turn possible by the AI in the game.

**14.	public minimaxAlgo(char[][] game,int depth, String player)**<br />

This is the minimax algorithm which contains a depth i.e., how much in the future will the algorithm look <br />

for the best possible turn.

**15.	public AMove(char[][] game, int x1, int y1, int x2, int y2)**<br />

Function to move a piece within the board.

**16.	public getPoints(char[][] game, String winningPlayer)**<br />

Function to return the real-time score of either of the players.

**17.	public blackWon()**<br />

Function to check if Black (Human) won or not.

**18.	public whiteWon()**<br />

Function to check if White (AI) won or not.

**19.	public changePlayer(String player)**<br />

Function used in Minimax algorithm to switch between MIN and MAX players.

**20.	public resetGame()**<br />

Function to reset the game.

**21.	public putPieces(Pieces piece, int u, int v)**<br />

Function to put the pieces back to their initial positions once the game resets.

**22.	protected paintComponent(Graphics graphics)**<br />

Function to paint the pieces Black and White respectively.

**23.	private paintGame(Graphics graphics)**<br />

Function to paint the board blocks as required by the game.

**24.	public getPreferredSize()**<br />

Function to pass on the size of blocks of the board.

**25.	public getGame()**<br />

Function to prevent the use of white blocks while playing the game i.e., it provides the suitable format of <br />

the board for checkers.


### 4. [PieceLocation](https://github.com/blank0826/AI-CheckersGame/blob/master/PieceLocation.java)
----
This class keeps in the current location and checks if there is any piece left to catch or not.

### 5. [PieceType](https://github.com/blank0826/AI-CheckersGame/blob/master/PieceType.java)
----
This contains all the types of pieces that can be on the board. This includes:<br />
1. BLACK_KING
2. BLACK_REGULAR
3. WHITE_REGULAR
4. WHITE_KING

### 6. [Pieces](https://github.com/blank0826/AI-CheckersGame/blob/master/Pieces.java)
----
These help to create each piece in the game. The main two functions used in this class:
####   1. public void make(Graphics graphics, int j, int i)
   This one gets the graphics and create the piece at the location (i, j) on the board.

####  2. public static boolean within(int a, int b, int j, int i)
   This checks if the piece is moving it stays within the board and doesn't try to move beyond the range of it.

### 7. [Turn](https://github.com/blank0826/AI-CheckersGame/blob/master/Turn.java)
----
These keep in the coordinates of the piece. Coordinates of the current position and to where the piece has moved.

# Local Setup

## Pre-Requisites
An IDE that supports Java and JavaFX.
## Installation and Execution
1. Pull this code into any folder.<br />
2. Open this folder in your preferred IDE.<br />
3. Build the Project.<br />
4. Run the [Logic.java](https://github.com/blank0826/AI-CheckersGame/blob/master/Logic.java) file and your Checkers board will be displayed.<br />


# Screenshots of the Gameplay
## Initial Board

<img src="https://user-images.githubusercontent.com/33955028/140733653-8bd93c39-a3d8-4f6e-aaa0-ef4c91dcad36.png" width="450" height="450">


## Menu 
### <ins>Algorithms</ins><br /><br />
<img src="https://user-images.githubusercontent.com/33955028/140733721-30040e87-76bc-417d-a835-b0f55ed22c06.png" width="450" height="450">


### <ins>Settings</ins><br /><br />
<img src="https://user-images.githubusercontent.com/33955028/140733745-211690b1-f4e1-4fe3-8687-4bfd503fd516.png" width="450" height="450">


## First Move
After the human moves, the AI will move automatically<br /><br />

<img src="https://user-images.githubusercontent.com/33955028/140734707-b187088b-b7db-4870-a320-75e807aac10f.png" width="450" height="450">

## Middle of the Match

<img src="https://user-images.githubusercontent.com/33955028/140734897-c83482a1-8422-4873-ab6c-a8da5df2b9c5.png" width="450" height="450">

## Win Condition
<img src="https://user-images.githubusercontent.com/33955028/140735144-363cc81c-9ade-4f26-9f13-6da3ab571f99.png" width="450" height="450">

## Draw Condition
<img src="https://user-images.githubusercontent.com/33955028/140735044-eadb4d1d-bdf3-48ad-9ab1-3f721849f577.png" width="450" height="450">

# Contact
## [Aditya Srivastava](mailto:aditya26052002@gmail.com?subject=GitHub)
