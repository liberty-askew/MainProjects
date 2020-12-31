package app;

import controller.Controller;
import controller.IController;
import model.IModel;
import model.Model;
import view.View;

public class App {
    public static void main(String[] args) {
        IModel model = new Model();
        IController controller = new Controller(model);
        new View(model, controller);
    }
}