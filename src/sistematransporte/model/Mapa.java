/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Carlos Olivares
 */
public class Mapa extends ImageView {

    private LinkedList<Nodo> destinos = new LinkedList();

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

    public void agregarDestino(Nodo nod){
        destinos.add(nod);
    }
    
    public void guardarDestinosAArchivo() throws UnsupportedEncodingException, FileNotFoundException{
        PrintWriter writer = new PrintWriter("src/sistematransporte/util/Destinos.txt", "UTF-8");
        while(!destinos.isEmpty()){
            Nodo nod = destinos.removeFirst();
            String cordenadas= nod.getPosX()+"$"+nod.getPosY();
            writer.println(cordenadas);
        }
        writer.close();
    }
    public EventHandler<MouseEvent> seleccionarDestino = (MouseEvent event) -> {

        //Node nodo = (Node) event.getSource();
        
        /*System.out.println("EJE X " + event.getSceneX() + " EJE Y " + event.getSceneY());
        System.out.println("Valor de Mostrar Nodos: "+sistematransporte.SistemaTransporte.mostrarNodos);
        if(sistematransporte.SistemaTransporte.mostrarNodos){
            Circle circle = new Circle(40.00);
            circle.setFill(javafx.scene.paint.Color.RED);
            
        }*/
    };
}
