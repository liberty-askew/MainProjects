package model;

import java.awt.*;

public class Ellipse extends Shapes {

    public Ellipse(int x1, int y1, int w, int h, Color color, boolean filled, int thickness)  {
        super(x1 , y1 , w , h , color, filled , thickness);
    }

    public void draw(Graphics g) { //should graphics be passed here or in  the constructor?
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        if (filled) {
            g.fillOval(x1, y1, w, h);
        }
        else {
            g.drawOval(x1, y1, w, h);
        }
    }
}
