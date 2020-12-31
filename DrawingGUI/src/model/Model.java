package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable implements IModel{


    public ArrayList<Shapes> shapeList; //this will be where we handle undo / redo.
    public ArrayList<Shapes> redoList;

    public Model(){
        shapeList = new ArrayList<Shapes>();
        redoList = new ArrayList<Shapes>();
    }

    private void update() {
        this.setChanged();
        this.notifyObservers();
    }

    public void line(Graphics g, int x1,int y1,int w,int h,Color color, int thickness ){ //todo: remove here?
        Line l = new Line(x1 , y1 , w , h , color, thickness);
        shapeList.add(l);
        update();
    }

    public void rectangle(Graphics g, int x1,int y1,int w,int h,Color color, boolean filled, int thickness){
        Rectangle r = new Rectangle(x1 , y1 , w , h , color, filled,thickness);
        shapeList.add(r);
        update();
    }

    public void ellipse(Graphics g, int x1,int y1,int w,int h,Color color, boolean filled, int thickness){
        Ellipse e = new Ellipse(x1 , y1 , w , h , color, filled, thickness);
        shapeList.add(e);
        update();
    }


    public void star(Graphics g, int x1,int y1,int w,int h,Color color, boolean filled, int thickness){
        Star s = new Star(x1 , y1 , w , h , color, filled, thickness);
        shapeList.add(s);
        update();
    }

    public void polygon(Graphics g, int x1,int y1,int w,int h,Color color,int sides, boolean filled, int thickness){
        Polygon s = new Polygon(x1 , y1 , w , h , color, sides, filled , thickness);
        shapeList.add(s);
        update();
    }

    public ArrayList<Shapes> getShapeList(){
        return shapeList;
    }

    public ArrayList<Shapes> getRedoList(){
        return redoList;
    }

    public void resetRedo(){
        this.redoList = new ArrayList<Shapes>();
        update();
    }

    public void undo(){
        redoList.add(shapeList.get(shapeList.size()-1));
        shapeList.remove(shapeList.size()-1);
        update();
    }

    public void redo(){
        shapeList.add(redoList.get(redoList.size()-1));
        redoList.remove(redoList.size()-1);
        update();
    }

    public void clear(){
        this.redoList = this.shapeList;
        this.shapeList = new ArrayList<Shapes>();
        update();
    }
}
