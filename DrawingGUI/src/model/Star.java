package model;

import java.awt.*;

public class Star extends Shapes {

    public Star(int x1, int y1, int w, int h, Color color , boolean filled , int thickness) {
        super(x1, y1, w, h, color,filled,thickness);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(4));
        double[] th = theta(10);
        int xpoints[] = new int[10];
        int ypoints[] = new int[10];
        for(int i = 0; i < 5; i++) {
            xpoints[2*i+1] = (int) (x1+ (0.5*w)*(1+Math.cos(th[2*i+1]-(Math.PI/10))));
            ypoints[2*i+1] = (int) (y1+ (0.5*h)*(1+Math.sin(th[2*i+1]-(Math.PI/10))));
        }
        for(int i = 0; i < 5; i++) {
            xpoints[2*i] = (int) (x1+ (0.5*w)*(1+0.5*Math.cos(th[2*i]-(Math.PI/10))));
            ypoints[2*i] = (int) (y1+ (0.5*h)*(1+0.5*Math.sin(th[2*i]-(Math.PI/10))));
        }
        if(filled){
            g.fillPolygon(xpoints, ypoints, 10);
        }
        else{
            g.drawPolygon(xpoints, ypoints, 10);
        }

    }
}
