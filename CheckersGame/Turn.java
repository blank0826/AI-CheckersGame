package CheckersGame;

public class Turn {
    public int X1; //Old X Coordinate
    public int Y1; //Old Y Coordinate
    public int X2; //New X Coordinate
    public int Y2; //New Y Coordinate
    
    public Turn(int x, int y, int X, int Y) {
        this.X1 = x;
        this.Y1 = y;
        this.X2 = X;
        this.Y2 = Y;
    }
    
    public int getStartX() {
        return X1;
    }
    
    public int getStartY() {
        return Y1;
    }
    
    public int getEndX() {
        return X2;
    }
    
    public int getEndY() {
        return Y2;
    }

}
