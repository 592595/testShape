package minidrawpad;

import java.awt.*;


public class Ball {
    private int x;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    private int y;
   public Ball(int x1, int y1, int dx1, int dy1, Color color1, int r1)
   {
       x=x1;y=y1;dx=dx1;dy=dy1;color=color1;r=r1;
   }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    private  int dx,dy;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

    public int getR() {
        return r;
    }
    public void setR(int r) {
        this.r = r;
    }
    private int r;
}
