# AI-CheckersGame
This is an AI-Human played Checkers Game where you can choose of which algorithm to use while playing whether be it random or mini-max.
This has been designed so that if for 50 moves no piece is captured or no piece is made into king then it will end in a draw.

First we will define the rules of a Checkers Game and then will describe the objective of Each Class.

### Movement Rules:
 **The ordinary movement of a man:**<br />
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. A man moves diagonally forward left or right from one square to an immediately
           neighboring vacant square.<br />
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. When a man reaches the farthest row forward (the king-row: top or bottom row
          for opponent player – marked with red in the diagram)</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;it becomes a king.<br /><br />
 **The ordinary movement of a king (crowned man):**<br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. The king moves from one square diagonally forward or backward, left or right, to
       an immediately neighbor vacant square.<br /><br />

### Capturing Movement Rules:<br />
    4. To capture the opponent’s piece, a man moves from one square over a diagonally
        adjacent and forward square that is occupied by an opponent’s piece and on to a
        vacant square immediately beyond it. On completion of the jump the captured
        piece is removed from the board.<br />
    5. The capturing movement of a king is similar to a man, but it can move both
        directions, forward and backward direction.<br />
    6. The capturing move of the piece (man or king) is continued until all the jumps are
        completed.<br />
**Exception: if a man reaches the king-row by means of a capturing move, it then becomes
a king but may not make any further jumps in the same turn.**<br />
    7. All capturing moves are compulsory, whether offered actively or passively. If
        there are two or more ways to jump, a player might select any one he wishes, not
        necessarily that which gains most pieces. Once started, a multiple jump must be
        carried through to completion.<br />
        
### Definition of a Win

    8. The winner of the game is one who can make the last move; that is, no move is
available to the opponent on his turn to play, either because all his pieces have
been captured or his remaining pieces are all blocked.<br />
### Definition of a Draw

    9. The 50-move rule: The game shall be declared drawn if, at any stage of the game
         satisfy one of the followings:<br />
      a. Neither player has advanced an uncrowned man towards the king-row
during the previous 50 moves.<br />
      b. No pieces have been removed from the board during the previous 50
moves.<br />

## AlreadyOccupied.Java
This class gives an exception when the piece is already occupied.
