package controller;

import model.IModel;
import java.awt.*;

public class Controller implements IController{

    private IModel model;

    /**
     * Controller handles requests from view and passes them to model. Implements some checks and error handling.
     * Inherits form abstract class IController which is called in App and View constructor.
     */

    public Controller(IModel model ){
        this.model = model;
    }

    public void controlStraight(Graphics g,int x1,int y1,int x2, int y2,Color color,  boolean lock, int thickness){
        try{
            if(lock){ //if lock then draws a horizontal line.
                y2 = y1;
            }
            model.line(g,x1,y1,x2,y2,color,thickness); }
        catch (Exception e){
            System.out.println("Line Error");
        }
    }

    public void controlRectangle(Graphics g,int x1,int y1,int w,int h, Color color, boolean lock, boolean filled,int thickness){
        try{
            if(lock){ //if lock then aspect ratios are fixed according to the smaller of height or width.
                w = h = Math.min(w,h);
            }
            model.rectangle(g,x1,y1,w,h,color,filled,thickness); }
        catch (Exception e){
            System.out.println("Rectangle Error");
        }
    }

    public void controlEllipse(Graphics g,int x1,int y1,int w,int h, Color color , boolean lock , boolean filled,int thickness){
        try{
            if(lock){ //if lock then aspect ratios are fixed according to the smaller of height or width.
                w = h = Math.min(w,h);
            }
            model.ellipse(g,x1,y1,w,h,color,filled,thickness); }
        catch (Exception e){
            System.out.println("Ellipse Error");
        }
    }

    public void controlStar(Graphics g,int x1,int y1,int w,int h, Color color , boolean lock, boolean filled,int thickness){
        try{
            if(lock){ //if lock then aspect ratios are fixed according to the smaller of height or width.
                w = h = Math.min(w,h);
            }
            model.star(g,x1,y1,w,h,color,filled, thickness); }
        catch (Exception e){
            System.out.println("Star Error");
        }
    }

    public void controlPolygon(Graphics g,int x1,int y1,int w,int h, Color color , int sides, boolean filled,int thickness){
        try{ model.polygon(g,x1,y1,w,h,color,sides,filled,thickness); }
        catch (Exception e){
            System.out.println("Triangle Error");
        }
    }

    public void controlUndo() {
        try {
            if (model.getShapeList().size() != 0) {
                model.undo();
            }
        }
        catch (Exception e){
            System.out.println("Undo Error");
        }
    }

    public void controlRedo() {
        try {
            if (model.getShapeList().size() != 0) {
                model.redo();
            }
        }
        catch (Exception e){
            System.out.println("Undo Error");
        }
    }

    public void controlClear() {
        try {
            model.clear();
        }
        catch (Exception e){
            System.out.println("Clear Error");
        }
    }
}
