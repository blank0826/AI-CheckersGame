package CheckersGame;

import java.awt.*;

public class Pieces{
    private final static int size = 75; //size of checker
    public PieceType pieceType; //type of the checker piece
    
    public Pieces(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    
    public void remove(Graphics graphics) {
        graphics.dispose();
    }
    
    public void make(Graphics graphics, int j, int i) {
        int x = j - size/2;
        int y = i - size/2;
        //Set Colour
        graphics.setColor(pieceType == PieceType.BLACK_REGULAR || pieceType == PieceType.BLACK_KING ? Color.BLACK : Color.WHITE);
        //Draw
        graphics.fillOval(x, y, size, size);
        graphics.setColor(Color.WHITE);
        graphics.drawOval(x, y, size, size);
        

      if (pieceType == PieceType.WHITE_KING || pieceType == PieceType.BLACK_KING) {
          Font font = new Font("Arial",Font.BOLD,size/2);
          graphics.setFont(font);
          graphics.setColor(Color.ORANGE);
          graphics.drawString("K", x+(5*size/16), y+(5*size/8));
      }
    }
    
    public static int getSize()
   {
      return size;
   }
    
    public static boolean within(int a, int b, int j, int i)
   {
      return (j - a) * (j - a) + (i - b) * (i - b) < size / 2 * size / 2;
   }
    
}
