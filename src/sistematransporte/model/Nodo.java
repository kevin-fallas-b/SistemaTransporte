/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.util.List;

/**
 *
 * @author Kevin F
 */
public class Nodo {
    private Double posX;
    private Double posY;
    List <Arista> saliente;
    
    public Nodo(){
        this.posX=0.00;
        this.posY=0.00;
    }
    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }
    
}
