/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte;

import javafx.application.Application;
import javafx.stage.Stage;
import sistematransporte.util.FlowController;

/**
 *
 * @author Kevin F
 */
public class SistemaTransporte extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goViewInWindow("PantInicio");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
