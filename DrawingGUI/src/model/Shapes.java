package model;


import javax.swing.*;
import java.awt.*;

public abstract class Shapes extends JPanel {

    public Graphics g;
    public int x1, y1, w, h, sides;
    public Color color;
    public boolean filled;
    public int thickness;



    public Shapes(int x1, int y1, int w, int h, Color color, int sides, boolean filled,int thickness) {
        //for polygons
        this.x1 = x1;
        this.y1 = y1;
        this.w = w;
        this.h = h;
        this.color = color;
        this.sides = sides;
        this.filled = filled;
        this.thickness = thickness;
    }

    public Shapes(int x1, int y1, int w, int h, Color color, int thickness) {
        //for lines
        this.x1 = x1;
        this.y1 = y1;
        this.w = w;
        this.h = h;
        this.color = color;
        this.thickness = thickness;
    }

    public Shapes(int x1, int y1, int w, int h, Color color, boolean filled ,int thickness) {
        //for all others
        this.x1 = x1;
        this.y1 = y1;
        this.w = w;
        this.h = h;
        this.color = color;
        this.filled = filled;
        this.thickness = thickness;
    }

    /**
    * this is used to calculate a list of radians for the polygon polar coordinates.
    * This is my own work, calculated with the help of online graphing tools eg.
    * Desmos and Geogebra.
    */
    public double[] theta (int n) {

        double th[] = new double[n];
        for (int i = 0; i < n; i++) {
            th[i] =((Math.PI * (2 * i + 1)) / (n))+(Math.PI/2);
        }
        return th;
    }


    public int[] xpolyPoints(int n){

        double th[] = theta(n);
        int xpoints[] = new int[n];
        for(int i = 0; i < n; i++) {
            xpoints[i] = (int) (x1+ (0.5*w)*(1+Math.cos(th[i])));
        }
        return xpoints;
    }

    public int[] ypolyPoints(int n){
        
        double th[] = theta(n);
        int ypoints[] = new int[n];
        for(int i = 0; i < n; i++) {
            ypoints[i] = (int) (y1+ (0.5*h)*(1+Math.sin(th[i])));
        }
        return ypoints;
    }
    public abstract void draw(Graphics g);


}
