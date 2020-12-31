package model;

import java.awt.*;

public class Rectangle extends Shapes {

    public Rectangle(int x1, int y1, int w, int h, Color color,boolean filled,int thickness) {
        super(x1, y1, w, h, color,filled,thickness);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        if (filled){
            g.fillRect(x1, y1, w, h);
        }
        else {
            g.drawRect(x1, y1, w, h);
        }
    }
}

