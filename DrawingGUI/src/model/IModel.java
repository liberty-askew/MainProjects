package model;

import java.awt.*;
import java.util.ArrayList;

public interface IModel {

    public ArrayList<Shapes> shapeList  = new ArrayList<Shapes>();

    void line(Graphics g ,int x1,int y1,int h,int w, Color color,int thickness);
    void rectangle(Graphics g ,int x1,int y1,int h,int w, Color color,boolean filled,int thickness);
    void ellipse(Graphics g ,int x1,int y1,int h,int w, Color color,boolean filled,int thickness);
    void star(Graphics g ,int x1,int y1,int h,int w, Color color,boolean filled,int thickness);
    void polygon(Graphics g,int x1,int y1,int w,int h, Color color,int sides,boolean filled,int thickness);
    ArrayList<Shapes> getShapeList();
    ArrayList<Shapes> getRedoList();
    void resetRedo();
    void undo();
    void redo();
    void clear();
}
