package sistematransporte.model;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Nodo extends Circle {
   
    private final List <Arista> aristas_Adyacentes = new ArrayList<>();
    private Point2D puntoMapa; 

    public Point2D getPuntoMapa() {
        return puntoMapa;
    }

    public void setPuntoMapa(Point2D puntoMapa) {
        this.puntoMapa = puntoMapa;
    }
    
    public Nodo(double radius) {
        super(radius);
    }

    public Nodo(double radius, Paint fill) {
        super(radius, fill);
    }

    public Nodo() {
    }

    public Nodo(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
    }

    public Nodo(double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
    }

    public List<Arista> getAristasAdyacentes() {
        return aristas_Adyacentes;
    }
}