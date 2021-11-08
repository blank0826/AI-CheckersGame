# AI-CheckersGame
This is an AI-Human played Checkers Game where you can choose which algorithm to use while playing whether be it random or mini-max.

First, we will define the rules of a Checkers Game and then will describe the objective of Each Class on how it helps in creating the gameplay.

## A. Rules

### <ins>Movement Rules</ins>
 **The ordinary movement of a man:**<br />
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. A man moves diagonally forward left or right from one square to an immediately
           neighboring vacant square.<br />
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. When a man reaches the farthest row forward (the king-row: top or bottom row
          for opponent player – marked with red in the diagram)</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;it becomes a king.<br /><br />
 **The ordinary movement of a king (crowned man):**<br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. The king moves from one square diagonally forward or backward, left or right, to
       an immediately neighbor vacant square.<br /><br />

### <ins>Capturing Movement Rules</ins><br />
**While capturing:**<br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4. To capture the opponent’s piece, a man moves from one square over a diagonally adjacent and forward square that is occupied by an opponent’s piece and onto a
        vacant square immediately beyond it. On completion of the jump, the captured piece is removed from the board.<br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5. The capturing movement of a king is similar to a man, but it can move in both directions, forward and backward direction.<br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6. The capturing move of the piece (man or king) is continued until all the jumps are completed.<br /><br />
**Exception: if a man reaches the king-row by means of a capturing move, it then becomes
a king but may not make any further jumps in the same turn.**<br /><br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7. All capturing moves are compulsory, whether offered actively or passively. If there are two or more ways to jump, a player might select anyone he wishes, not necessarily that which gains most pieces. Once started, multiple jumps must be carried through to completion.<br /><br />
        
### <ins>Definition of a Win</ins>

**In the end,**<br />
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8. The winner of the game is one who can make the last move; that is, no move is
available to the opponent on his turn to play, either because all his pieces have
been captured or his remaining pieces are all blocked.<br /><br />
### <ins>Definition of a Draw</ins>

**Sometimes the game may lead to a draw, then**<br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 9. **<ins>The 50-move rule:</ins>** The game shall be declared drawn if, at any stage of the game
         satisfy one of the followings:<br />
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a. Neither player has advanced an uncrowned man towards the king-row
during the previous 50 moves.<br />
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b. No pieces have been removed from the board during the previous 50
moves.<br /><br />

## B. Objective of Each Class

### AlreadyOccupied.Java
----
This class gives an exception when the piece is already occupied.<br />

### Checkers.Java
----
This class initializes the game board. it sets properties for the javaFX UI and adds menu giving the option of Algorithms and Settings.<br />
**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. <ins>Algorithms:-</ins>** Random and Mini-max Algorithms.<br />
**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. <ins>Settings:-</ins>** Reset and Exit.<br />

### Logic.Java
----
This is the main class of the Checkers Game where the whole game takes place updating board, moving pieces and making all the algorithm based decisions.

#### Functions Used
**1.	Logic (int algoNum)**<br />
Constructor to initialize the board and display appropriate messages after each move is played.

**2.	public chooseAlgo(int algo, char[][] game, int depth, String player)**<br />
Function to switch between the two algorithms.

**3.	public moveMaker()**<br />
Function to determine which player should play when.

**4.	public taken (char [][] game, int x1, int y1, int x2, int y2)**<br />
Function to implement capturing moves by the opponent.

**5.	public checkKing(char [][] game)**<br />
Function to check if a piece is eligible to become a king or not.

**6.	public checkPos(int x1, int y1, int x2, int y2, char[][] game, boolean errorMessage)**<br />
Function to check if a move is valid or not.

**7.	public getCorrectPos(String team, char[][] game)**<br />
Function to create a list of moves for either of the teams.

**8.	public captureTurnPerformed(char[][] game, int x1, int y1, int x2, int y2)**<br />
Function to check if the move just performed was a capturing move or not.

**9.	public captureTurnAt(char[][] game, int col, int row)**<br />
Function to find whether a capture move exists at the given co-ordinates or not.

**10.	public checkCaptureMove(char[][] game, String player)**<br />
Function to check if a capture move exists anywhere on the board for a given team or not.

**11.	public updateGame(int x1, int y1, int x2, int y2)**<br />
Function to update the underlying board representation.

**12.	public alterGUI()**<br />
Function to update the GUI of the board.

**13.	public randomAlgo(char[][] game, String player)**<br />
Function which contains the logic for “Random” algorithm.

**14.	public minimaxAlgo(char[][] game,int depth, String player)**<br />
Function which contains the logic for “Minimax” algorithm.

**15.	public AMove(char[][] game, int x1, int y1, int x2, int y2)**<br />
Function to move a piece within the board in random algorithm.

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
Function to prevent the use of white blocks while playing the game i.e., it provides the suitable format of the board for checkers.


### PieceLocation.Java
----
This class keeps in the current location and checks if there is any piece left to catch or not.

### PieceType.Java
----
This contains all the types of pieces that can be on the board. This includes:<br />
1. BLACK_KING
2. BLACK_REGULAR
3. WHITE_REGULAR
4. WHITE_KING

### Pieces.Java
----
These help to create each piece in the game. The main two functions used in this class:
####  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1. public void make(Graphics graphics, int j, int i)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   This one gets the graphics and create the piece at the location (i, j) on the board.

#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2. public static boolean within(int a, int b, int j, int i)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   This checks if the piece is moving it stays within the board and doesn't try to move beyond the range of it.

### Turn.Java
----
These keep in the coordinates of the piece. Coordinates of the current position and to where the piece has moved.
