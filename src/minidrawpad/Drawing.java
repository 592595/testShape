package minidrawpad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Drawing implements Serializable {

    int x1,x2,y1,y2;
    int  R,G,B;
    int type;
    String s1;
    String s2;

    float stroke ;
    int cap=BasicStroke.CAP_BUTT;
    int join=BasicStroke.JOIN_ROUND;
    float miterlimit=3.5f;
    float[] dash=new float[]{15,0,};
    float dash_phase=0f;

    void draw(Graphics2D g2d ){}
}

class Line extends Drawing {
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        BasicStroke s=new BasicStroke(stroke,cap,join,miterlimit,dash,dash_phase);
        g2d.setStroke(s);
        g2d.drawLine(x1, y1, x2, y2);

    }
}

class Rect extends Drawing{
    void draw(Graphics2D g2d ){
        g2d.setPaint(new Color(R,G,B));
        //g2d.setStroke(new BasicStroke(stroke));
        BasicStroke s=new BasicStroke(stroke,cap,join,miterlimit,dash,dash_phase);
        g2d.setStroke(s);
        g2d.drawRect(Math.min(x1, x2), Math.min(y2, y2), Math.abs(x1-x2), Math.abs(y1-y2));
    }
}

class Oval extends Drawing{
    void draw(Graphics2D g2d ){
        g2d.setPaint(new Color(R,G,B));
        //g2d.setStroke(new BasicStroke(stroke));
        BasicStroke s=new BasicStroke(stroke,cap,join,miterlimit,dash,dash_phase);
        g2d.setStroke(s);
        g2d.drawOval(Math.min(x1, x2), Math.min(y2, y2), Math.abs(x1-x2), Math.abs(y1-y2));
    }
}

class Pencil extends Drawing{
    void draw(Graphics2D g2d ){
        g2d.setPaint(new Color(R,G,B));
        g2d.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1,x2, y2);
    }
}

class Rubber extends Drawing{
    void draw(Graphics2D g2d ){
        g2d.setPaint(new Color(255,255,255));
        g2d.setStroke(new BasicStroke(stroke+4,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1,x2, y2);
    }
}

class Word extends Drawing{
    void draw(Graphics2D g2d ){
        g2d.setPaint(new Color(R,G,B));
        g2d.setFont(new Font(s2,x2+y2,((int)stroke)*18));
        if(s1 != null)
            g2d.drawString( s1, x1,y1);
    }
}