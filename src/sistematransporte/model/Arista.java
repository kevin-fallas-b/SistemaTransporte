/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import javafx.scene.shape.Line;

/**
 *
 * @author Kevin F
 */
public class Arista extends Line{
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
}
