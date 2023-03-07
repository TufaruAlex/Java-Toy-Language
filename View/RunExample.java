package View;

import Controller.Controller;

import java.io.IOException;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            controller.allStep();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
