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
    private Integer tiempo;//tiempo que lleva en movimiento el vehiculo, guardado en segundos

    public Vehiculo() {
        super();
        this.setId("vehiculo");
        this.setFitHeight(40);
        this.setFitWidth(28);
 
        
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }
    
    public Double rotarCarro(Double x , Double y, Double x2 , Double y2){
        Double resultado;
        try {
            Double m = (y - y2) / (x-x2);
            resultado = Math.toDegrees(Math.atan(m));
            if(m.isInfinite()){
                if(y2>y){
                    resultado = 270d;
                }
                else{
                    resultado = 90d;
                }
            }
            else if(resultado == 0){
                if(x2>x){
                    resultado = 180d;
                }
                else{
                    resultado = 0d;
                }
            }
            else if(x2<x){
                resultado += 360d; 
            }
            else{
                resultado += 180d;
            }
            return resultado;
        } catch (ArithmeticException ae) {
            if(y2>y){
                resultado = 270d;
            }
            else{
                resultado = 90d;
            }
            return resultado;
        }
    }
    
    
}
