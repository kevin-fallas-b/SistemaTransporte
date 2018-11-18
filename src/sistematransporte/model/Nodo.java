package sistematransporte.model;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Nodo extends Circle {
   
    private final List <Arista> aristas_Adyacentes = new ArrayList<>();
    private final List <Nodo> nodos_Adyacentes = new ArrayList<>();
    
    private Point2D puntoMapa; 
    private Integer numNodo;

    public Point2D getPuntoMapa() {
        return puntoMapa;
    }

    public void setPuntoMapa(Point2D puntoMapa) {
        this.puntoMapa = puntoMapa;
    }
    
    public Nodo(double radius) {
        super(radius);
        this.numNodo=0;
    }

    public Nodo(double radius, Paint fill) {
        super(radius, fill);
        this.numNodo=0;
    }

    public Nodo() {
        this.numNodo=0;
    }

    public Nodo(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        this.numNodo=0;
    }

    public Nodo(double centerX, double centerY, double radius, Paint fill,Integer num) {
        super(centerX, centerY, radius, fill);
        this.numNodo=num;
    }

    public Integer getNumNodo() {
        return numNodo;
    }

    public void setNumNodo(Integer numNodo) {
        this.numNodo = numNodo;
    }

    public List<Arista> getAristasAdyacentes() {
        return aristas_Adyacentes;
    }
    
    public List<Nodo> getNodosAdyacentes() {
        return nodos_Adyacentes;
    }
}