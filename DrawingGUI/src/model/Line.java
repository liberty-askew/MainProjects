package model;

import java.awt.*;

public class Line extends Shapes {

    public Line(int x1, int y1, int w, int h, Color color, int thickness)  {
        super(x1 , y1 , w , h , color, thickness);
    }

    public void draw(Graphics g) { //should graphics be passed here or in  the constructor?
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g.drawLine(x1, y1, w, h);
    }
}
