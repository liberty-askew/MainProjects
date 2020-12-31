package view;

import controller.IController;
import model.IModel;
import model.Shapes;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import static java.lang.Integer.min;

/**
 * Main viewing panel for Paint App. Called and constructed by App main. Implements an observer to communicate with
 * IModel and IController classes.
 */
public class View extends JFrame implements Observer {

    private IModel model;
    private IController controller;
    private JMenuBar menu;
    private JMenuItem straightLine;
    private JMenuItem rectangle;
    private JMenuItem ellipse;
    private JMenuItem star;
    private JMenuItem triangle;
    private JMenuItem pentagon;
    private JMenuItem hexagon;
    private JMenuItem octagon;
    private JMenuItem undo;
    private JMenuItem redo;
    private JMenuItem clear;
    private JRadioButtonMenuItem thin;
    private JRadioButtonMenuItem medium;
    private JRadioButtonMenuItem thick;
    private JMenuItem colorSelect;
    private JRadioButtonMenuItem fill;
    private Color color;
    private MouseListener ml;
    private KeyListener kl;
    static final int WIDTH = 500; //fixed dimensions of the JFrame
    static final int HEIGHT = 500;

    public View(IModel model, IController controller) {

        this.model = model;
        this.color = Color.RED; //sets red to default colour
        this.controller = controller;
        menu = new JMenuBar();
        constructMenu();
        addActionListenerForButtons(new Listener());
        ml = new MouseListening();
        kl = new KeyListening();
        this.addMouseListener(ml); //adds on mouse listener as the start of the frame. Only acts if in conjunction with ActionListener.
        this.addKeyListener(kl); //similar to mouse listener, adds mouse listener to start of frame.
        this.setJMenuBar(menu);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    /**
     * fills the menu bar with relevant buttons and submenus. Called in the construction of view.
     */
    public void constructMenu() {

        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        clear = new JMenuItem("Clear");
        straightLine = new JMenuItem("Straight Line");
        rectangle = new JMenuItem("Rectangle");
        ellipse = new JMenuItem("Ellipse");
        star = new JMenuItem("Star");
        triangle = new JMenuItem("Triangle");
        pentagon = new JMenuItem("Pentagon");
        hexagon = new JMenuItem("Hexagon");
        octagon = new JMenuItem("Octagon");
        colorSelect = new JMenuItem("Colors");
        fill = new JRadioButtonMenuItem("Fill");
        thin = new JRadioButtonMenuItem("Thin");
        medium = new JRadioButtonMenuItem("Medium");
        thick = new JRadioButtonMenuItem("Thick");
        JMenu edit = new JMenu("Edit:");
        JMenu shapes = new JMenu("Shapes:");
        JMenu drawStyle = new JMenu ("Draw Style:");
        menu.add(edit); //adds main menu buttons
        menu.add(shapes);
        menu.add(drawStyle);
        edit.add(undo); //adds sub menu buttons
        edit.add(redo);
        edit.add(clear);
        shapes.add(straightLine);
        shapes.add(rectangle);
        shapes.add(ellipse);
        shapes.add(star);
        shapes.add(triangle);
        shapes.add(pentagon);
        shapes.add(hexagon);
        shapes.add(octagon);
        drawStyle.add(thin);
        drawStyle.add(medium);
        drawStyle.add(thick);
        drawStyle.add(colorSelect);
        drawStyle.add(fill);
    }


    /**
     * adds action listener to the relevant toolbar buttons. Called in the construction of view.
     * @param al - Action Listener as defined in the subclass ActionListening
     */
    public void addActionListenerForButtons(ActionListener al) {

        undo.addActionListener(al);
        redo.addActionListener(al);
        clear.addActionListener(al);
        straightLine.addActionListener(al);
        rectangle.addActionListener(al);
        ellipse.addActionListener(al);
        star.addActionListener(al);
        triangle.addActionListener(al);
        pentagon.addActionListener(al);
        hexagon.addActionListener(al);
        octagon.addActionListener(al);
        thin.addActionListener(al);
        medium.addActionListener(al);
        thick.addActionListener(al);
        colorSelect.addActionListener(al);
        fill.addActionListener(al);
    }

    /**
     * @return this JFrame object.
     */
    public JFrame getViewPanel(){
        return this;
    }


    /**
     * called by repaint() methods. Overrides the paint method from JPanel and repaints canvas with shapes from ShapeList.
     * @param g - graphics for this JFrame.
     */
    public void paint(Graphics g){

        super.paintComponents(g);
        ArrayList<Shapes> sl = model.getShapeList();
        for ( Shapes s : sl) {
            s.draw(g);
        }
    }

    /**
     * Interacts with the update() method in model class after each method to update the viewer.
     * @param arg0 - observable object in model class.
     * @param arg1
     */
    public void update(Observable arg0, Object arg1) {

        SwingUtilities.invokeLater(
                () -> repaint());
    }


    /**
     * New ActionListener class which is used by JPanel main class above. Watches actions on all submenu buttons.
     */
    public class Listener implements ActionListener {

        /**
         *
         * Method to call appropriate action when specific menu options are selected.
         * @param e - ActionEvent
         */
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == colorSelect) {
                JColorChooser chooser = new JColorChooser(); //most of the following if from external source: http://www.java2s.com/Tutorial/Java/0240__Swing/ListeningforOKandCancelEventsinaJColorChooserDialog.html
                ActionListener okListener = evt -> {
                    Color newColor = chooser.getColor();
                    View.this.color = newColor;
                };
                ActionListener cancelListener = evt -> {
                    Color newColor = chooser.getColor();
                    View.this.color = newColor;
                };
                boolean modal = true;
                JDialog dialog = JColorChooser.createDialog(null, "Dialog Title", modal, chooser, okListener, cancelListener);
                dialog.setVisible(true); //end of external source.
            }

            else if (e.getSource() == fill) { //radio button selected if shape drawn should be filled.
                ((MouseListening) ml).setFill(fill.isSelected());
            }
            else if (e.getSource() == thin) { //select weight of line for image drawing.
                ((MouseListening) ml).setThickness(1); //passes attribute to mouselistener for when shape is drawn.
                medium.setSelected(false); //resets medium and thick to deselected because only 1 type of thickness can
                thick.setSelected(false); //be selected at a time.
            }
            else if (e.getSource() == medium) { //see above.
                ((MouseListening) ml).setThickness(3);
                thin.setSelected(false);
                thick.setSelected(false);
            }
            else if (e.getSource() == thick) { //see above.
                ((MouseListening) ml).setThickness(5);
                thin.setSelected(false);
                medium.setSelected(false);
            }
            else if (e.getSource() == undo) {
                controller.controlUndo();
                repaint();
            }
            else if (e.getSource() == redo) {
                controller.controlRedo();
                repaint();
            }
            else if (e.getSource() == clear) {
                controller.controlClear();
                repaint();
            }
            else {  //only other actions left here will be shapes e.g square, triangle...
                ((MouseListening) ml).setEvent(e); //updates event attribute for mouse listener, so mouse listener can see which shape has been selected.
            }
        }
    }


    /**
     * Subclass for MouseListener object used on JPanel object above.
     */
    public class MouseListening implements MouseListener{

        public int x1;
        public int y1;
        public int h;
        public int w;
        public boolean lock; //if ratio of shape dimensions should be fixed e.g square or circle.
        public boolean filled; //if shape should be filled instead of just outlined.
        public int thickness; //thickness of line drawing shape.
        private ActionEvent actionevent; //attribute passed from ActionListener above.

        /**
         * Updates actionevent attribute, called by actionlistener above.
         * @param e - tpye of action event e.g Rectangle button selected.
         */
        public void setEvent(ActionEvent e){

            this.actionevent = e;
        }

        /**
         * Updates lock attribute called by key listener below.
         * @param shift - true if shift pressed, false if not.
         */
        public void setLock(boolean shift) {

            this.lock = shift;
        }

        /**
         * Sets filled attribute if fill radio button selected. Called by actionlistner above.
         * @param filled - true if filled selected, false if not.
         */
        public void setFill(boolean filled) {

            this.filled = filled;
        }

        /**
         * Sets thickness attribute according to thickness radio buttons as defined in actionlistener above.
         * @param thickness - 1,3,5
         */
        public void setThickness(int thickness) {

            this.thickness = thickness;
        }

        public void mouseClicked(MouseEvent m) { }

        public void mouseEntered(MouseEvent m) { }

        public void mouseExited(MouseEvent m) { }

        @Override
        public void mousePressed(MouseEvent m) {

            x1 = m.getX();
            y1 = m.getY();
        }

        /**
         * Sorts action to appropriate controller methods for when mouse is released after shape drag.
         * Sets so no action occurs if the mouse is released outside of the JPanel bounds (0-500) in both dimensions
         * or if an actionlistner event (selecting a shape) has not occured.
         */
        @Override
        public void mouseReleased(MouseEvent m) {

            int x2 = m.getX();
            int y2 = m.getY();
            int width = x1 - x2;
            int height = y1 - y2;
            if(actionevent!= null && x2 <= WIDTH && x2 >= 0 && y2 <= HEIGHT && y2 >= 0) { //checks conditions are met.
                if (actionevent.getSource() == straightLine) {
                    controller.controlStraight(getGraphics(), x1, y1, x2, y2, color, lock, thickness);
                    getViewPanel().repaint();
                } else if (actionevent.getSource() == rectangle) {
                    controller.controlRectangle(getGraphics(), min(x1, x2), min(y1, y2), Math.abs(width), Math.abs(height), color, lock, filled, thickness);
                    repaint();
                } else if (actionevent.getSource() == ellipse) {
                    controller.controlEllipse(getGraphics(), min(x1, x2), min(y1, y2), Math.abs(width), Math.abs(height), color, lock, filled, thickness);
                    repaint();
                } else if (actionevent.getSource() == star) {
                    controller.controlStar(getGraphics(), min(x1, x2), min(y1, y2), Math.abs(width), Math.abs(height), color, lock, filled, thickness);
                    repaint();
                } else if (actionevent.getSource() == triangle) {
                    controller.controlPolygon(getGraphics(), min(x1, x2), min(y1, y2), Math.abs(width), Math.abs(height), color, 3, filled, thickness);
                    repaint();
                } else if (actionevent.getSource() == pentagon) {
                    controller.controlPolygon(getGraphics(), min(x1, x2), min(y1, y2), Math.abs(width), Math.abs(height), color, 5, filled, thickness);
                    repaint();
                } else if (actionevent.getSource() == hexagon) {
                    controller.controlPolygon(getGraphics(), min(x1, x2), min(y1, y2), Math.abs(width), Math.abs(height), color, 6, filled, thickness);
                    repaint();
                } else if (actionevent.getSource() == octagon) {
                    controller.controlPolygon(getGraphics(), min(x1, x2), min(y1, y2), Math.abs(width), Math.abs(height), color, 8, filled, thickness);
                    repaint();
                }
                model.resetRedo(); //resets redo list to empty if a shape is drawn as cannot move forward from this point.
            }
        }
    }
    public class KeyListening implements KeyListener{

        boolean shift = false; //attribute for if the shift key is pressed. Triggers locked aspect ratio for shapes if pressed.

        /**
         * sets shift key attribute to false and updates mouselistener when shift key is released. Does not matter if
         * key released is not shift key.
         * @param e - key release
         */
        @Override
        public void keyReleased(KeyEvent e) {

            resetShift();
            ((MouseListening)ml).setLock(shift);
        }

        public void keyTyped(KeyEvent e) { }

        /**
         * sets shift to true if shift key is pressed on keyboard and updates mouselistener.
         * @param e - key pressed
         */
        @Override
        public void keyPressed(KeyEvent e) {

            shift = (e.getKeyCode() == 16);
            ((MouseListening)ml).setLock(shift);
        }

        public void resetShift() {

            this.shift = false;
        }
    }
}
