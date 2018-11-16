/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author Carlos Olivares
 */
public class Mapa extends ImageView {

    private final LinkedList <Nodo> destinos = new LinkedList();
    
    public Mapa() {
        super();
        this.setFitHeight(600);
        this.setFitWidth(422);
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setId("map");
        
    }

    public void agregarDestino(Nodo nod) {
        destinos.add(nod);
    }

    public LinkedList <Nodo> getDestinos() {
        return destinos;
    }

    public void guardarDestinosAArchivo() throws UnsupportedEncodingException, FileNotFoundException {
        PrintWriter writer = new PrintWriter("src/sistematransporte/util/Destinos.txt", "UTF-8");
        while (!destinos.isEmpty()) {
            Nodo nod = destinos.removeFirst();
            String cordenadas = nod.getCenterX() + "$" + nod.getCenterY();
            writer.println(cordenadas);
        }
        writer.close();
    }

    public void cargarNodo() throws FileNotFoundException, IOException {
        
        BufferedReader reader = new BufferedReader(new FileReader("src/sistematransporte/util/Destinos.txt"));
        String line = null;

        while ((line = reader.readLine()) != null) {
            String[] parts;
            parts = line.split("\\$");
            Double posx = Double.valueOf(parts[0]);
            Double posy = Double.valueOf(parts[1]);
            
            Nodo nod = new Nodo(posx, posy, 5.00, Color.CORAL);
            nod.setPuntoMapa(new Point2D(posx, posy));
            
            destinos.add(nod);
        }
        
        //System.out.println("Nodos Cargados.");
    }
}
