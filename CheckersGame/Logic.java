package CheckersGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import static java.lang.Integer.min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
    
public class Logic extends JComponent{
    private final static int tileSize = (int) (Pieces.getSize() * 1.25);
    private final int boardSize = 8 * tileSize;
    private Dimension size;
    private boolean isDragging = false;
    private int distanceA, distanceB;
    
    private PieceLocation pieceLoc;
    private int oldDistanceA, oldDistanceB;
    private List<PieceLocation> pieceLocations;
    List<PieceLocation> removePieces = new ArrayList<PieceLocation>();
    private List<Turn> turns = new ArrayList<>();
    
    private List<Turn> turnsCaptured = new ArrayList<>();
    private List<Turn> possibleTurns = new ArrayList<>();
    public boolean captured[][] = new boolean[80][80];
    private char gameRep[][];
    
    int bestODistanceA, bestODistanceB, bestNDistanceA, bestNDistanceB;
    boolean playerMove = true;
    boolean captureFlag = false;
    boolean AIExtraMoveFlag = false;
    boolean checkPromotion = false;
    
    public static int algoNum = 0;
    int movesNum =0;
    private boolean isCapturing = false;
    
    public Logic(int algoNum) {
        this.algoNum = algoNum;
        pieceLocations = new ArrayList<>();
        size = new Dimension(boardSize, boardSize);
        
        gameRep = new char[][] {
            {'x', 'w', 'x', 'w', 'x', 'w', 'x', 'w'},
            {'w', 'x', 'w', 'x', 'w', 'x', 'w', 'x'},
            {'x', 'w', 'x', 'w', 'x', 'w', 'x', 'w'},
            {'o', 'x', 'o', 'x', 'o', 'x', 'o', 'x'},
            {'x', 'o', 'x', 'o', 'x', 'o', 'x', 'o'},
            {'b', 'x', 'b', 'x', 'b', 'x', 'b', 'x'},
            {'x', 'b', 'x', 'b', 'x', 'b', 'x', 'b'},
            {'b', 'x', 'b', 'x', 'b', 'x', 'b', 'x'},
        };
        
        addMouseListener(new MouseAdapter()
                       {
                          @Override
                          public void mousePressed(MouseEvent event)
                          {
                             int a = event.getX();
                             int b = event.getY();
                             int i = 0;
                             while(i<pieceLocations.size()){
                                 PieceLocation pieceLoc = pieceLocations.get(i);
                                 if (Pieces.within(a, b, pieceLoc.a, pieceLoc.b) && ((pieceLoc.pieces.pieceType == PieceType.BLACK_REGULAR || pieceLoc.pieces.pieceType == PieceType.BLACK_KING)))
                                {
                                   Logic.this.pieceLoc = pieceLoc;
                                   oldDistanceA = pieceLoc.a;
                                   oldDistanceB = pieceLoc.b;
                                   distanceA = a - pieceLoc.a;
                                   distanceB = b - pieceLoc.b;
                                   isDragging = true;
                                   return;
                                }
                                 i++;
                             }                           
                          }

                          @Override
                          public void mouseReleased(MouseEvent event)
                          { 
                             if (isDragging)
                                isDragging = false;
                             else
                                return;
                             
                             int a = event.getX();
                             int b = event.getY();
                             pieceLoc.a = (a - distanceA) / tileSize * tileSize + 
                                           tileSize / 2;
                             pieceLoc.b = (b - distanceB) / tileSize * tileSize + 
                                           tileSize / 2;

                             if (!checkPos(oldDistanceA/tileSize, oldDistanceB/tileSize, pieceLoc.a/tileSize, pieceLoc.b/tileSize, gameRep, true)) {
                                 Logic.this.pieceLoc.a = oldDistanceA;
                                 Logic.this.pieceLoc.b = oldDistanceB;
                             } else {
                                 Checkers.turn1 = false;
                                 moveMaker();
                                 if (AIExtraMoveFlag) {
                                    chooseAlgo(algoNum, gameRep,0, "white");
                                    updateGame(bestODistanceA*tileSize, bestODistanceB*tileSize, bestNDistanceA*tileSize, bestNDistanceB*tileSize);
                                    checkKing(gameRep);
                                    alterGUI();
                                    repaint();
                                    AIExtraMoveFlag = false;
                                    playerMove = true;
                                 }
                                 
                                 System.out.println("AI Move: (" + (bestODistanceA + 1) + "," + (bestODistanceB + 1) + ") to (" + (bestNDistanceA + 1) + "," + (bestNDistanceB + 1) + ")"); 
                                 
                                 if (blackWon()) {
                                     Checkers.dialogWindow("BLACK WINS!", "Game Over!");
                                 }
                                 if (whiteWon()) {
                                     Checkers.dialogWindow("WHITE WINS!", "Game Over!");
                                 }
                                                                  
                                 if(movesNum >= 50){
                                     Checkers.dialogWindow("DRAW!","Game Over!");
                                 }
                             }
                             
                             pieceLoc = null;
                             repaint();
                          }
                       });
        addMouseMotionListener(new MouseMotionAdapter()
                             {
                                @Override
                                public void mouseDragged(MouseEvent event)
                                {
                                   if (isDragging)
                                   {
                                      pieceLoc.a = event.getX() - distanceA;
                                      pieceLoc.b = event.getY() - distanceB;
                                      repaint();
                                   }
                                }
                             });  
    }  

    public int chooseAlgo(int algo, char[][] game,int depth, String player){
        if(algo==1){
            return randomAlgo(game,player);
        }else if(algo==2){
            return minimaxAlgo(game,depth,player);
        }
        return -1;
    }
    
    public void moveMaker(){       
        if (playerMove) {
            
            updateGame(oldDistanceA, oldDistanceB, pieceLoc.a, pieceLoc.b);
            checkKing(gameRep);

            if (captureFlag && captureTurnAt(gameRep, pieceLoc.a/tileSize, pieceLoc.b/tileSize)) {
                System.out.println("You can take another piece!");
            } else {
                movesNum++;
                playerMove = false;
            }
        }

        if (!playerMove){
            //movesNum++;
            chooseAlgo(algoNum, gameRep, 0,"white");
            updateGame(bestODistanceA*tileSize, bestODistanceB*tileSize, bestNDistanceA*tileSize, bestNDistanceB*tileSize);
            checkKing(gameRep);
            if (captureFlag && captureTurnAt(gameRep, bestNDistanceA, bestNDistanceB)) {
                alterGUI();
                repaint();
                movesNum = 0;
                AIExtraMoveFlag = true;
            } else {
                movesNum++;
                AIExtraMoveFlag = false;
                playerMove = true;
            }
        }
        
        captureFlag = false;
        alterGUI();
        repaint();
        checkPromotion = false;
    }
    
    public void taken(char[][] game, int x1, int y1, int x2, int y2) {
        int capturedX = ((x1 + x2)/2);
        int capturedY = ((y1 + y2)/2);
        
        if (Math.abs(x2 - x1) == 2 * tileSize && Math.abs(y2 - y1) == 2 * tileSize) {
            System.out.println("Captured at block (" + capturedX/tileSize + "," + capturedY/tileSize+")");
            captureFlag = true;
                     
            if (game[capturedY/tileSize][capturedX/tileSize] == 'W') {
                checkPromotion = true;
                gameRep[y2/tileSize][x2/tileSize] = 'B';
                int i=0;
                while(i<pieceLocations.size()){
                    PieceLocation pieceLocation = pieceLocations.get(i);
                    if (pieceLocation.b == y2 && pieceLocation.a == x2) {
                        pieceLocation.pieces.pieceType = PieceType.BLACK_KING;
                        repaint();
                    }
                    i++;
                }
            }
            
            if (game[capturedY/tileSize][capturedX/tileSize] == 'B') {
                checkPromotion = true;
                gameRep[y2/tileSize][x2/tileSize] = 'W';
                int i=0;
                while(i<pieceLocations.size()){
                    PieceLocation pieceLocation = pieceLocations.get(i);
                    if (pieceLocation.b == y2 && pieceLocation.a == x2) {
                        pieceLocation.pieces.pieceType = PieceType.WHITE_KING;
                        repaint();
                    }
                    i++;
                }
            }

            game[capturedY/tileSize][capturedX/tileSize] = 'o'; 
            int i=0;
            while(i<pieceLocations.size()){
                PieceLocation pieceLocation = pieceLocations.get(i);
                if (pieceLocation.a == capturedX && pieceLocation.b == capturedY) {
                    pieceLocations.remove(pieceLocation);
                    alterGUI();
                    repaint();
                    break;
                }
                i++;
            }
        } 
    }
    
    public void checkKing(char[][] game) {
        for (int col = 0; col < 8; col++) {
            if (game[0][col] == 'b') {
                game[0][col] = 'B';
                for(int i=0;i<pieceLocations.size();i++){
                    PieceLocation pieceLoc = pieceLocations.get(i);
                    if (pieceLoc.b == tileSize /2 && pieceLoc.pieces.pieceType == PieceType.BLACK_REGULAR) {
                        if(pieceLoc.pieces.pieceType == PieceType.BLACK_KING){
                            movesNum=0;
                        }else{
                            pieceLoc.pieces.pieceType = PieceType.BLACK_KING;
                        }
                        repaint();
                    }
                }
            }
            if (gameRep[7][col] == 'w') {
                gameRep[7][col] = 'W';
                for(int i=0;i<pieceLocations.size();i++){
                    PieceLocation pieceLoc = pieceLocations.get(i);
                    if (pieceLoc.b == boardSize - tileSize/2 && pieceLoc.pieces.pieceType == PieceType.WHITE_REGULAR) {
                        if(pieceLoc.pieces.pieceType == PieceType.WHITE_KING){
                            movesNum=0;
                        }else{
                            pieceLoc.pieces.pieceType = PieceType.WHITE_KING;
                        }
                        repaint();
                    }
                }
            }
        }
    }
    
    public boolean checkPos(int x1, int y1, int x2, int y2, char[][] game, boolean errorMessage) {
        boolean check = true;
        int inX = x1;
        int inY = y1;
        int endX = x2;
        int endY = y2;
        
        if(game[inY][inX] == 'b' || game[inY][inX] == 'B') {
            if (checkCaptureMove(game, "black") && !captureTurnPerformed(game, inX, inY, endX, endY)) {
                if (errorMessage) System.out.println("You can capture a piece!");
                check = false;
            }
        }
        if(game[inY][inX] == 'w' || game[inY][inX] == 'W') {
            if (checkCaptureMove(game, "white") && !captureTurnPerformed(game, inX, inY, endX, endY)) {
                if (errorMessage) System.out.println("You can capture a piece!");
                check = false;
            }
        }
        
        if (endX > 7 || endX <0 || endY > 7 || endY < 0) {
            if (errorMessage) System.out.println("Out of bounds!");
            return false;
        }
        
        if (game[endY][endX] == 'b' || game[endY][endX] == 'w' || game[endY][endX] == 'B' || game[endY][endX] == 'W') {
            check = false;
            if (errorMessage) System.out.println("Invalid move!");
        }
        
        if (endX == inX || endY == inY) {
            check = false;
            if (errorMessage) System.out.println("You have to move diagonally!");
        }
        //Only allow moving forwards
        if (game[inY][inX] == 'b' && endY > inY){
            check = false;
            if (errorMessage) System.out.println("You can only move forward!");
        }
        if (game[inY][inX] == 'w' && endY < inY){
            check = false;
            if (errorMessage) System.out.println("You can only move forward!");
        }
        
        if ((Math.abs(endX - inX) > 1 || Math.abs(endY - inY) > 1) && !captureTurnAt(game, inX, inY)){ 
            check = false;
            if (errorMessage) System.out.println("You can't move more than 1 block!");
        } else if (Math.abs(endX - inX) > 2 || Math.abs(endY - inY) > 2) {
            check = false;
            if (errorMessage) System.out.println("You can't move more than 2 blocks when taking a piece!");
        }
        return check;
    }
    
    public List<Turn> getCorrectPos(String team, char[][] game) {
        turnsCaptured.clear();
        turns.clear();
        possibleTurns.clear();
        isCapturing = false;
        
        for (int row = 0; row<8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((team == "white" && (game[row][col] == 'w' || game[row][col] == 'W')) || (team == "black" && (game[row][col] == 'b' || game[row][col] == 'B'))) {
                    if (captureTurnPerformed(game, col, row, col + 2, row + 2)) {
                            turnsCaptured.add(new Turn(col, row, col+2, row+2));
                            isCapturing = true;
                    }
                    if (captureTurnPerformed(game, col, row, col - 2, row + 2)) {
                        turnsCaptured.add(new Turn(col, row, col-2, row+2));
                        isCapturing = true;
                    }
                    if (captureTurnPerformed(game, col, row, col + 2, row - 2)) {
                        turnsCaptured.add(new Turn(col, row, col+2, row-2));
                        isCapturing = true;
                    }
                    if (captureTurnPerformed(game, col, row, col - 2, row - 2)) {
                        turnsCaptured.add(new Turn(col, row, col-2, row-2));
                        isCapturing = true;
                    }
                }
            }
        }
        
        if (turnsCaptured.isEmpty()) {
            for (int row = 0; row<8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ((team == "white" && (game[row][col] == 'w' || game[row][col] == 'W')) || (team == "black" && (game[row][col] == 'b' || game[row][col] == 'B'))) {
                        if (checkPos(col, row, col + 1, row + 1, game, false)) {
                            turns.add(new Turn(col, row, col+1, row+1));
                        }
                        if (checkPos(col, row, col - 1, row + 1, game, false)) {
                            turns.add(new Turn(col, row, col-1, row+1));
                        } 
                        if (checkPos(col, row, col + 1, row - 1, game, false)) {
                            turns.add(new Turn(col, row, col+1, row-1));
                        }
                        if (checkPos(col, row, col - 1, row - 1, game, false)) {
                            turns.add(new Turn(col, row, col-1, row-1));
                        }
                    }
                }
            }
            
            possibleTurns.addAll(turns);
            return turns;
        } else {
            possibleTurns.addAll(turnsCaptured);
            return turnsCaptured;
        }  
    }
    
    public boolean captureTurnPerformed(char[][] game, int x1, int y1, int x2, int y2) {
        boolean capMove = false;
        
        try {
        if ((game[y1][x1] == 'b' || game[y1][x1] == 'B') && ((game[y1-1][x1-1] == 'w' || game[y1-1][x1-1] == 'W') && game[y1-2][x1-2] == 'o')) {
            if (x2 == x1-2 && y2 == y1-2) {
                movesNum=0;
                capMove = true;
            }
        }} catch(Exception exception) {}
        
        try {
        if((game[y1][x1] == 'b'|| game[y1][x1] == 'B') && ((game[y1-1][x1+1] == 'w'|| game[y1-1][x1+1] == 'W') && game[y1-2][x1+2] == 'o')) {
            if (x2 == x1+2 && y2 == y1-2) {
                movesNum=0;
                capMove = true;
            }
        }} catch(Exception exception) {}
        
        try{
        if ((game[y1][x1] == 'w' || game[y1][x1] == 'W') && ((game[y1+1][x1-1] == 'b' || game[y1+1][x1-1] == 'B') && game[y1+2][x1-2] == 'o')){
            if (x2 == x1-2 && y2 == y1+2) {
                movesNum=0;
                capMove = true;
            }
        }} catch(Exception exception) {}
        
        try {
        if ((game[y1][x1] == 'w' || game[y1][x1] == 'W') && ((game[y1+1][x1+1] == 'b' || game[y1+1][x1+1] == 'B') && game[y1+2][x1+2] == 'o')) {
            if (x2 == x1+2 && y2 == y1+2) {
                movesNum=0;
                capMove = true;
            }
        }} catch(Exception exception) {}
        
        try {
        if ((game[y1][x1] == 'B')&& ((game[y1+1][x1-1] == 'w' || game[y1+1][x1-1] == 'W') && game[y1+2][x1-2] == 'o')) {
            if (x2 == x1-2 && y2 == y1+2) {
                movesNum=0;
                capMove = true;
            }
        }}catch(Exception exception) {}
        
        try {
        if ((game[y1][x1] == 'B')&& ((game[y1+1][x1+1] == 'w' || game[y1+1][x1+1] == 'W') && game[y1+2][x1+2] == 'o')) {
            if (x2 == x1+2 && y2 == y1+2) {
                movesNum=0;
                capMove = true;
            }
        }}catch(Exception exception) {}
        
        try {
        if ((game[y1][x1] == 'W')&& ((game[y1-1][x1-1] == 'b' || game[y1-1][x1-1] == 'B') && game[y1-2][x1-2] == 'o')) {
            if (x2 == x1-2 && y2 == y1-2) {
                movesNum=0;
                capMove = true;
            }
        }}catch(Exception exception) {}
        
        try {
        if ((game[y1][x1] == 'W')&& ((game[y1-1][x1+1] == 'b' || game[y1-1][x1+1] == 'B') && game[y1-2][x1+2] == 'o')) {
            if (x2 == x1+2 && y2 == y1-2) {
                movesNum=0;
                capMove = true;
            }
        }}catch(Exception exception) {}
        
        return capMove;
    }
    
    public boolean captureTurnAt(char[][] game, int col, int row) {
        boolean capMove = false;

        try {
        if ((game[row][col] == 'b' || game[row][col] == 'B') && ((game[row-1][col-1] == 'w' || game[row-1][col-1] == 'W') && game[row-2][col-2] == 'o')) {
            capMove = true;
        }} catch(Exception exception) {}

        try {
        if((game[row][col] == 'b'|| game[row][col] == 'B') && ((game[row-1][col+1] == 'w'|| game[row-1][col+1] == 'W') && game[row-2][col+2] == 'o')) {
            capMove = true;
        }} catch(Exception exception) {}

        try{
        if ((game[row][col] == 'w' || game[row][col] == 'W') && ((game[row+1][col-1] == 'b' || game[row+1][col-1] == 'B') && game[row+2][col-2] == 'o')){
            capMove = true;
        }} catch(Exception exception) {}

        try {
        if ((game[row][col] == 'w' || game[row][col] == 'W') && ((game[row+1][col+1] == 'b' || game[row+1][col+1] == 'B') && game[row+2][col+2] == 'o')) {
            capMove = true;
        }} catch(Exception exception) {}
        
        try {
        if ((game[row][col] == 'B')&& ((game[row+1][col-1] == 'w' || game[row+1][col-1] == 'W') && game[row+2][col-2] == 'o')) {
            capMove = true;
        }}catch(Exception exception) {}

        try {
        if ((game[row][col] == 'B')&& ((game[row+1][col+1] == 'w' || game[row+1][col+1] == 'W') && game[row+2][col+2] == 'o')) {
            capMove = true;
        }}catch(Exception exception) {}

        try {
        if ((game[row][col] == 'W')&& ((game[row-1][col-1] == 'b' || game[row-1][col-1] == 'B') && game[row-2][col-2] == 'o')) {
            capMove = true;
        }}catch(Exception exception) {}

        try {
        if ((game[row][col] == 'W')&& ((game[row-1][col+1] == 'b' || game[row-1][col+1] == 'B') && game[row-2][col+2] == 'o')) {
            capMove = true;
        }}catch(Exception exception) {}
        
        return capMove;
        }
    
    public boolean checkCaptureMove(char[][] game, String player) {
        boolean captureTurn = false;      
        
        if (player == "black") {
            for (int col = 0; col<8; col++) {
                for (int row = 0; row<8; row++){
                    if (captureTurnAt(game, col, row) && (game[row][col] == 'b' || game[row][col] == 'B')) {
                        captureTurn = true;
                    }
                }
            }
        }
        if (player == "white") {
            for (int col = 0; col<8; col++) {
                for (int row = 0; row<8; row++){
                    if (captureTurnAt(game, col, row) && (game[row][col] == 'w' || game[row][col] == 'W')) {
                        captureTurn = true;
                    }
                }
            }
        }
        
        return captureTurn;
    }
      
   public void updateGame(int x1, int y1, int x2, int y2) {
        int inX = x1 / tileSize;
        int inY = y1/ tileSize;
        int endX = x2 / tileSize;
        int endY = y2 / tileSize;
        
        taken(gameRep, x1, y1, x2, y2);
        
        if (!checkPromotion) {
            gameRep[endY][endX] = gameRep[inY][inX];
        }
        
        gameRep[inY][inX] = 'o';
        
        alterGUI();
        repaint();
    }

   public void alterGUI() {
       pieceLocations.clear();
       repaint();
       
       for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                
                if (gameRep[row][col] == 'w') {
                   this.putPieces(new Pieces(PieceType.WHITE_REGULAR), row+1, col+1);
                }
                if (gameRep[row][col] == 'W') {
                   this.putPieces(new Pieces(PieceType.WHITE_KING), row+1, col+1);
                }
                if (gameRep[row][col] == 'B') {
                   this.putPieces(new Pieces(PieceType.BLACK_KING), row+1, col+1);
                }
                if (gameRep[row][col] == 'b') {
                   this.putPieces(new Pieces(PieceType.BLACK_REGULAR), row+1, col+1);
                }
                
            }
        } 
       repaint();
   }
   
   public int randomAlgo(char[][] game, String player){
       char[][] gameBoard = new char[game.length][game.length];
       List<Turn> possibleTurns;
       
        for (int i = 0; i< game.length; i++) {
            gameBoard[i] = Arrays.copyOf(game[i], game[i].length);
        }
       
       possibleTurns = getCorrectPos(player, game);
             
       if (player == "white") {
           if(isCapturing == true){
               int x1 = possibleTurns.get(0).X1;
               int y1 = possibleTurns.get(0).Y1;
               int x2 = possibleTurns.get(0).X2;
               int y2 = possibleTurns.get(0).Y2;
               
               bestODistanceA = x1; bestODistanceB = y1; bestNDistanceA = x2; bestNDistanceB = y2;
           }else{
               if(possibleTurns.size()>0){
                    int m = (int) Math.random() * possibleTurns.size();
                    int x1 = possibleTurns.get(m).X1;
                    int y1 = possibleTurns.get(m).Y1;
                    int x2 = possibleTurns.get(m).X2;
                    int y2 = possibleTurns.get(m).Y2;
                    
                    bestODistanceA = x1; bestODistanceB = y1; bestNDistanceA = x2; bestNDistanceB = y2;
               
               }else{
                   if (blackWon()) {
                        Checkers.dialogWindow("BLACK WINS!", "Game Over!");
                    }
                    if (whiteWon()) {
                        Checkers.dialogWindow("WHITE WINS!", "Game Over!");
                    }
               }
           }
           return 0;
       }
       return -1;
   }
   
   public int minimaxAlgo(char[][] game,int depth, String player){
       
       char[][] gameBoard = new char[game.length][game.length];
       List<Turn> possibleTurns;
       int maxPoints, max;
       
        for (int i = 0; i< game.length; i++) {
            gameBoard[i] = Arrays.copyOf(game[i], game[i].length);
        }
       
       possibleTurns = getCorrectPos(player, game);
             
       if (depth == algoNum || possibleTurns.isEmpty()){
           return getPoints(game, player);
       }
       
       if (player == "white") {
           maxPoints = Integer.MIN_VALUE;
           
           int x = 0;
           while(x<possibleTurns.size()){
               int x1 = possibleTurns.get(x).X1;
               int y1 = possibleTurns.get(x).Y1;
               int x2 = possibleTurns.get(x).X2;
               int y2 = possibleTurns.get(x).Y2;
               
               gameBoard = AMove(gameBoard, x1, y1, x2, y2);
               
               max = minimaxAlgo(gameBoard,depth+1, changePlayer(player));
               
               if (max > maxPoints) {
                bestODistanceA = x1; bestODistanceB = y1; bestNDistanceA = x2; bestNDistanceB = y2;
                maxPoints = max;
               }
               
               x++;
           }
           return maxPoints;
       }
       else {
           maxPoints = Integer.MAX_VALUE;
           
           int x =0;
           while(x<possibleTurns.size()){
               
               int x1 = possibleTurns.get(x).X1;
               int y1 = possibleTurns.get(x).Y1;
               int x2 = possibleTurns.get(x).X2;
               int y2 = possibleTurns.get(x).Y2;
               
               gameBoard = AMove(gameBoard, x1, y1, x2, y2);
               
               max = minimaxAlgo(gameBoard,depth+1, changePlayer(player));
               maxPoints = min(maxPoints, max);
               
               x++;
           }
           return maxPoints;
       }
   }
 
   public char[][] AMove(char[][] game, int x1, int y1, int x2, int y2) {

       game[y2][x2] = game[y1][x1];
       game[y1][x1] = 'o';

       return game;
    }
   
   public int getPoints(char[][] game, String winningPlayer) {
        int points = 0;
        checkKing(game);
        
        for (int row = 0; row < 8; row ++) {
            for (int col = 0; col < 8; col++) {
                if (game[row][col] == 'w' && winningPlayer == "white") {
                    points++;
                } else if (game[row][col] == 'w' && winningPlayer == "black") {
                    points--;
                }
                if(game[row][col] == 'W' && winningPlayer == "white") {
                    points+=3;
                } else if (game[row][col] == 'W' && winningPlayer == "black") {
                    points-=3;
                }
                if(game[row][col] == 'b' && winningPlayer == "white") {
                    points--;
                } else if (game[row][col] == 'b' && winningPlayer == "black") {
                    points++;
                }
                if(game[row][col] == 'B' && winningPlayer == "white") {
                    points-=3;
                } else if (game[row][col] == 'B' && winningPlayer == "black") {
                    points+=3;
                }
            }
        }
        return points;
    }
   
   public boolean blackWon() {
        boolean checkWon = false;
        if (getCorrectPos("white", gameRep).isEmpty())
            checkWon = true;
        return checkWon;
    }
 
   public boolean whiteWon() {
        boolean checkWon = false;
        if (getCorrectPos("black", gameRep).isEmpty())
            checkWon = true;
        return checkWon;
    }
   
   public String changePlayer(String player) {
        String nPlayer;
        if (player == "white")
            nPlayer = "black";
        else
            nPlayer = "white";
        return nPlayer;
    }
    
   public void resetGame() {
        for (int a = 1; a <= 8; a++) {
            for (int b = 1; b <= 8; b++) {
                if ((a + b)%2 == 1 && b <= 3) { 
                    this.putPieces(new Pieces(PieceType.WHITE_REGULAR), b, a);
                } 
                if ((b + a)%2 == 1 && b >= 6) {
                    this.putPieces(new Pieces(PieceType.BLACK_REGULAR), b, a);
                }
            }
        }
    }
    
   public void putPieces(Pieces piece, int u, int v) {
        if (u < 1 || u > 8 || v < 1 || v > 8)
            throw new IllegalArgumentException("Stay within the board!");
        PieceLocation pieceLoc = new PieceLocation();
        pieceLoc.pieces = piece;
        pieceLoc.a = (v - 1) * tileSize + tileSize /2;
        pieceLoc.b = (u - 1) * tileSize + tileSize /2;
        
        for(int i=0;i<pieceLocations.size();i++){
            PieceLocation pieceLocation = pieceLocations.get(i);
            pieceLocation.left = false;
            pieceLocation.right = false;
            if (pieceLoc.a == pieceLocation.a && pieceLoc.b == pieceLocation.b)
                throw new AlreadyOccupiedException("(" + u + "," + v + ") is already occupied!");
        }
        
        pieceLocations.add(pieceLoc);
    }
    
    @Override
   protected void paintComponent(Graphics graphics)
   {
      paintGame(graphics);
      for(int i=0;i<pieceLocations.size();i++){
          PieceLocation pieceLoc = pieceLocations.get(i);
          if (pieceLoc != Logic.this.pieceLoc)
            pieceLoc.pieces.make(graphics, pieceLoc.a, pieceLoc.b);
      }
      
      if (pieceLoc != null)
         pieceLoc.pieces.make(graphics, pieceLoc.a, pieceLoc.b);
   }
    
   private void paintGame(Graphics graphics){
      for (int row = 0; row < 8; row++){
         graphics.setColor(((row & 1) != 0) ? Color.BLACK : Color.WHITE);
         for (int col = 0; col < 8; col++){
            graphics.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            graphics.setColor((graphics.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK);
         }
      }
   }
    
    @Override
   public Dimension getPreferredSize()
   {
      return size;
   }
   
   public char[][] getGame() {
       return gameRep;
   }
}