
import model.*;
import org.junit.*;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class model_test {

    private IModel mymodel;
    private JPanel testpanel;

    @Before
    public void setup(){
        mymodel = new Model();
        testpanel = new JPanel();
    }

    @Test
    public void testViewExists(){
        assertNotNull(mymodel);
    }


    @Test
    public void  testShapeArrayAdd(){
        mymodel.rectangle(testpanel.getGraphics(), 1,2,3,4, Color.cyan , true ,1);
        mymodel.line(testpanel.getGraphics(), 1,2,3,4, Color.red , 1);
        mymodel.ellipse(testpanel.getGraphics(), 1,2,3,4, Color.pink , false, 1);
        mymodel.star(testpanel.getGraphics(), 1,2,3,4, Color.yellow , true, 3);
        mymodel.polygon(testpanel.getGraphics(), 1,2,3,4, Color.green , 4,false, 2);
        ArrayList<Shapes> sl = mymodel.getShapeList();
        System.out.println(sl.get(0).getClass());
        assertEquals(sl.get(0).getClass() , model.Rectangle.class);
        assertEquals(sl.get(1).getClass() , model.Line.class);
        assertEquals(sl.get(2).getClass() , model.Ellipse.class);
        assertEquals(sl.get(3).getClass() , model.Star.class);
        assertEquals(sl.get(4).getClass() , model.Polygon.class);
        assertEquals( sl.size(), 5);
        mymodel.clear();
        assertEquals(mymodel.getShapeList().size() , 0);
    }

    @Test
    public void testGetShape(){
        assertEquals(mymodel.getShapeList().getClass(),ArrayList.class);
    }


    @Test
    public void testGetRedo(){
        assertEquals(mymodel.getShapeList().getClass(),ArrayList.class);
    }

    @Test
    public void  testUndoRedo(){
        mymodel.clear();
        mymodel.resetRedo(); //incase from previous test
        assertEquals(mymodel.getRedoList().size(), 0); //check reset has worked
        mymodel.rectangle(testpanel.getGraphics(), 1,2,3,4, Color.cyan , true ,1);
        mymodel.line(testpanel.getGraphics(), 1,2,3,4, Color.red , 1);
        mymodel.ellipse(testpanel.getGraphics(), 1,2,3,4, Color.pink , false, 1);
        assertEquals(mymodel.getShapeList().size(), 3);
        mymodel.undo();
        assertEquals(mymodel.getShapeList().size(), 2); //checks item has been removed from undo list
        assertEquals(mymodel.getRedoList().size(), 1); //checks item has been added to redo list
        assertEquals(mymodel.getRedoList().get(0).getClass(), model.Ellipse.class); //check ellipse is on redo list
        mymodel.redo();
        assertEquals(mymodel.getShapeList().size(), 3); //checks item has been removed from undo list
        assertEquals(mymodel.getRedoList().size(), 0); //checks item has been added to redo list
        assertEquals(mymodel.getShapeList().get(mymodel.getShapeList().size()-1).getClass(), model.Ellipse.class); //check ellipse is on redo list
    }

    @Test
    public void  testShapeArrayClear(){
        mymodel.clear(); //incase there's object remaining from previous tests
        assertEquals( mymodel.getShapeList().size(), 0); //double check that the objects have been removed,,if any
        mymodel.rectangle(testpanel.getGraphics(), 1,2,3,4, Color.cyan , true ,1);
        mymodel.line(testpanel.getGraphics(), 1,2,3,4, Color.red , 1);
        mymodel.ellipse(testpanel.getGraphics(), 1,2,3,4, Color.pink , false, 1);
        mymodel.star(testpanel.getGraphics(), 1,2,3,4, Color.yellow , true, 3);
        mymodel.polygon(testpanel.getGraphics(), 1,2,3,4, Color.green , 4,false, 2);
        assertEquals( mymodel.getShapeList().size(), 5); //double check that the objects have been added
        mymodel.clear();
        assertEquals(mymodel.getShapeList().size() , 0); //reset to zero
    }
}

