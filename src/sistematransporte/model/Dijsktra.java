/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import sistematransporte.controller.PantPrincipalController;

/**
 *
 * @author Carlos Olivares
 */
public class Dijsktra {

    Mapa grafo;
    ArrayList<Nodo> listaNodosAdyacentes;
    ArrayList<Arista> aux = new ArrayList<Arista>();

    public Dijsktra(Mapa grafo) {
        this.grafo = grafo;
        listaNodosAdyacentes = new ArrayList<Nodo>();
    }

    public Dijsktra() {
    }

    public boolean isContenido(Nodo nodo) {
        boolean retornado = false;
        for (Nodo n : listaNodosAdyacentes) {
            if (n == nodo) {
                retornado = true;
            }
        }
        return retornado;
    }

    private void llenarConAdyacentes(Nodo nodo) {
        if (nodo != null) {
            List<Arista> listaAux = nodo.getAristasAdyacentes();
            if (listaAux != null) {
                for (Arista enlace : listaAux) {
                    Nodo aux2;
                    if (nodo.equals(enlace.getOrigen())) {
                        aux2 = enlace.getDestino();
                    } else {
                        aux2 = enlace.getOrigen();
                    }

                    if (!aux2.isMarca()&&!enlace.getAccidente()&&!enlace.getReparacion()) {

                        if (isContenido(aux2)) {
                            int longitud = nodo.getLongitudCamino() + enlace.getPeso();
                            if (aux2.getLongitudCamino() > longitud) {
                                aux2.setLongitudCamino(longitud);
                                aux2.setNodoAntecesorDisjktra(nodo);
                            }
                        } else {
                            aux2.setLongitudCamino(nodo.getLongitudCamino() + enlace.getPeso());
                            aux2.setNodoAntecesorDisjktra(nodo);
                            listaNodosAdyacentes.add(aux2);
                        }

                    }
                }
            }
        }
        
    }

    public Nodo buscarMenor() {
        Nodo aux = new Nodo();
        aux.setLongitudCamino(9999999);

        for (Nodo nodo : listaNodosAdyacentes) {
            if (nodo.getLongitudCamino() < aux.getLongitudCamino()) {
                aux = nodo;
            }
        }

        return aux;
    }

    public ArrayList ejecutar(Nodo nodoInicio) {
        nodoInicio.setLongitudCamino(0);
        if (nodoInicio != null) {
            listaNodosAdyacentes = new ArrayList<>();
            listaNodosAdyacentes.add(nodoInicio);
            while (!listaNodosAdyacentes.isEmpty()) {
                Nodo menor = buscarMenor();
                menor.setMarca(true);
                listaNodosAdyacentes.remove(menor);
                llenarConAdyacentes(menor);
            }
        }
        return aux;
    }

    public ArrayList<Arista> getAux() {
        return aux;
    }

    private void rutaCorta(Nodo nodoFinal) {
        aux.clear();
        Nodo nAux = nodoFinal;
        for (int i = 0; i < PantPrincipalController.accidentes.size(); i++) {
            Arista accidente = PantPrincipalController.accidentes.get(i);
            for (Arista arista : aux) {
                if(arista.equals(accidente)){
                    aux.remove(accidente);
                }
                
            }
        }
        while (nAux.getNodoAntecesorDisjktra() != null) {
            aux.add(grafo.getArista(nAux,
                    nAux.getNodoAntecesorDisjktra()));
            nAux = nAux.getNodoAntecesorDisjktra();
        }

    }

    public void marcarRutaCorta(Nodo nodoFinal, Color color) {      
        if (nodoFinal != null) {
            rutaCorta(nodoFinal);
            aux.stream().forEach((t) -> {
                if (t != null) {
                    t.setStroke(color);
                    t.setStrokeWidth(5);
                }
            });
        }
    }
}
