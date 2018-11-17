/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Kevin F
 */
public class Arista extends Line {

    private Nodo origen;
    private Nodo destino;
    private Integer peso;

    public Nodo getOrigen() {
        return origen;
    }

    public Arista() {
        super();
    }

    public Arista(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        this.setStrokeWidth(5.00);
        this.setStroke(Color.BLUE);
        
        
    }

    public void setOrigen(Nodo origen) {
        this.origen = origen;
    }

    public Nodo getDestino() {
        return destino;
    }

    public void setDestino(Nodo destino) {
        this.destino = destino;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public void agregarNodos(List<Nodo> nodos) {
        Point2D inicio = new Point2D(0, 0);
        Point2D fin = new Point2D(0, 0);
        
        for (Nodo nodo : nodos) {
            
            if (nodo.getCenterX() == getStartX() && nodo.getCenterY() == getStartY()) {
                setOrigen(nodo);
                nodo.getAristasAdyacentes().add(this);
                inicio = nodo.getPuntoMapa();
                
            } else if (nodo.getCenterX() == getEndX() && nodo.getCenterY() == getEndY()) {
                setDestino(nodo);
                nodo.getAristasAdyacentes().add(this);
                fin = nodo.getPuntoMapa();
            }
        }
        
        Double p = inicio.distance(fin);
        this.peso = p.intValue();
    }
    
}
