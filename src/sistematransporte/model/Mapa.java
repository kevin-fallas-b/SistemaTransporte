/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Carlos Olivares
 */
public class Mapa extends ImageView {

    private List<Nodo> destinos;

    public Mapa(Image image) {
        
        super(image);
        //this.setOnMouseReleased(seleccionarDestino);
        /*this.prefHeight(600);
        this.prefWidth(422);*/
        //this.setLayoutX(24);
        /*this.setLayoutY(10);
        this.setOnMouseReleased(seleccionarDestino);
        this.setId("map");*/

    }

    public Mapa() {
        super();
        this.setOnMouseReleased(seleccionarDestino);
        this.setFitHeight(600);
        this.setFitWidth(422);
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setOnMouseReleased(seleccionarDestino);
        this.setId("map");
    }

    public EventHandler<MouseEvent> seleccionarDestino = (MouseEvent event) -> {

        //Node nodo = (Node) event.getSource();
        
        System.out.println("EJE X " + event.getSceneX() + " EJE Y " + event.getSceneY());

    };
}
