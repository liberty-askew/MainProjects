package controller;

import java.awt.*;

public interface IController {

    void controlStraight(Graphics g,int x1,int y1,int h,int w, Color color,boolean lock, int thickness);
    void controlRectangle(Graphics g,int x1,int y1,int h,int w, Color color,boolean lock, boolean filled, int thickness);
    void controlEllipse(Graphics g,int x1,int y1,int w,int h, Color color,boolean lock , boolean filled, int thickness);
    void controlStar(Graphics g,int x1,int y1,int w,int h, Color color,boolean lock, boolean filled, int thickness);
    void controlPolygon(Graphics g,int x1,int y1,int w,int h, Color color,int sides,  boolean filled, int thickness);
    void controlUndo();
    void controlRedo();
    void controlClear();
}
