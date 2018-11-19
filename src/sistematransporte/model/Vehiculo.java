/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import javafx.scene.image.ImageView;

/**
 *
 * @author Kevin F
 */
public class Vehiculo extends ImageView {
    Integer tiempo;//tiempo que lleva en movimiento el vehiculo, guardado en segundos

    public Vehiculo() {
        super();
        this.setId("vehiculo");
        this.setFitHeight(50);
        this.setFitWidth(35);
        this.setRotate(180);
    }
    
}
