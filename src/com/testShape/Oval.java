package com.testShape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Oval extends Shape{

    public Oval(){

    }

    public Oval(int x1,int y1,int x2,int y2,Color color ,int width){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.color=color;
        this.width=width;
    }


    public void Draw(Graphics2D g) {
        g.setColor(this.color);
        g.setStroke(new BasicStroke(width));
        g.drawOval(x1, y1, x2, y2);
    }


}