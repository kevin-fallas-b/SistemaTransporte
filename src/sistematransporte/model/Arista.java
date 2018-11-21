/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import sistematransporte.controller.PantPrincipalController;
import static sistematransporte.controller.PantPrincipalController.agregarAccidente;
import static sistematransporte.controller.PantPrincipalController.agregarReparacion;
import static sistematransporte.controller.PantPrincipalController.anchorPane;

/**
 *
 * @author Kevin F
 */
public class Arista extends Line {

    private Nodo origen;
    private Nodo destino;
    private Integer peso;
    private Boolean accidente;
    private Boolean reparacion;
    private Integer pesoOriginal;

    public Integer getPesoOriginal() {
        return pesoOriginal;
    }

    public void setPesoOriginal(Integer pesoOriginal) {
        this.pesoOriginal = pesoOriginal;
    }

    public Boolean getReparacion() {
        return reparacion;
    }

    public void setReparacion(Boolean reparacion) {
        this.reparacion = reparacion;
    }

    public Boolean getAccidente() {
        return accidente;
    }

    public void setAccidente(Boolean accidente) {
        this.accidente = accidente;
    }

    public EventHandler<MouseEvent> getClick() {
        return click;
    }

    public void setClick(EventHandler<MouseEvent> click) {
        this.click = click;
    }

    private CierreCosevi cierre;

    public Nodo getOrigen() {
        return origen;
    }

    public Arista() {
        super();
    }

    public Arista(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        this.setStrokeWidth(10.00);
        this.setStroke(Color.TRANSPARENT);
        this.setOnMouseClicked(click);
        this.accidente = false;
        this.reparacion = false;
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
        Nodo aux = new Nodo();
        Nodo aux2 = new Nodo();
        for (Nodo nodo : nodos) {
            if (nodo.getCenterX() == getStartX() && nodo.getCenterY() == getStartY()) {
                setOrigen(nodo);
                nodo.getAristasAdyacentes().add(this);
                inicio = nodo.getPuntoMapa();
                aux=nodo;
            } else if (nodo.getCenterX() == getEndX() && nodo.getCenterY() == getEndY()) {
                setDestino(nodo);
                nodo.getAristasAdyacentes().add(this);
                fin = nodo.getPuntoMapa();
                aux2=nodo;
            }
        }
        Double p = inicio.distance(fin);
        this.peso = p.intValue();
        this.pesoOriginal = this.peso;
    }

    EventHandler<MouseEvent> click = (MouseEvent event) -> {
        if (agregarAccidente) {
            Accidente accidente = new Accidente();

            Point2D pMedio = this.destino.getPuntoMapa().midpoint(this.origen.getPuntoMapa());

            accidente.setLayoutX(pMedio.getX() - accidente.getFitHeight() / 2);
            accidente.setLayoutY(pMedio.getY() - accidente.getFitWidth() / 2);

            anchorPane.getChildren().add(accidente);
            agregarAccidente = false;
            PantPrincipalController.accidentes.add(this);
            PantPrincipalController.imagenesAccidentes.add(accidente);
            this.accidente = true;
            PantPrincipalController.rutaNueva = true;
        } else if (agregarReparacion) {
            cierre = new CierreCosevi();
            Point2D pMedio = this.destino.getPuntoMapa().midpoint(this.origen.getPuntoMapa());
            cierre.setLayoutX(pMedio.getX() - cierre.getFitHeight() / 2);
            cierre.setLayoutY(pMedio.getY() - cierre.getFitWidth() / 2);
            anchorPane.getChildren().add(cierre);
            agregarReparacion = false;
            PantPrincipalController.imagenesCierres.add(cierre);
            PantPrincipalController.cierresCosevi.add(cierre);
            PantPrincipalController.rutaNueva = true;
            this.reparacion = true;

        }
    };
}
