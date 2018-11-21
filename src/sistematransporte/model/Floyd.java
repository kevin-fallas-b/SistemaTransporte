/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Floyd {

    private ArrayList<Integer> rutInteger = new ArrayList<>();
    private ArrayList<Nodo> nodosRuta = new ArrayList<>();
    private ArrayList<Arista> aristaRuta = new ArrayList<>();
    private Mapa grafo;

    public Floyd(Mapa grafo) {
        this.grafo = grafo;
    }

    public ArrayList <Integer> floyd_cam(Integer[][] mady, int ini, int fin) {

        int aux;

        Integer mCaminos[][] = new Integer[mady.length][mady.length];
        Integer mAux[][] = new Integer[mady.length][mady.length];
        for (int x = 0; x < mady.length; x++) {
            for (int y = 0; y < mady.length; y++) {
                mCaminos[y][x] = y;
                mAux[x][y] = mady[x][y];
            }
        }

        for (int x = 0; x < mady.length; x++) {
            for (int y = 0; y < mady.length; y++) {
                if (x != y) {
                    for (int z = 0; z < mady.length; z++) {
                        if (z != x) {
                            aux = mAux[x][y] + mAux[z][x];
                            if (aux < mAux[z][y]) {
                                mAux[z][y] = aux;
                                mCaminos[z][y] = x;
                            }
                        }
                    }
                }
            }
        }

        recuperaCamino(ini, fin, mCaminos);

        rutInteger.stream().forEach((y) -> {
            grafo.getDestinos().stream().forEach((t) -> {
                if (t.getNumNodo().equals(y)) {
                    t.setMarca(true);
                    nodosRuta.add(t);
                }
            });
        });

        grafo.getAristas().stream().forEach((t) -> {
            nodosRuta.stream().forEach((y) -> {
                int i = nodosRuta.indexOf(y) + 1;
                if (i < nodosRuta.size()) {
                    Nodo auxNodo = nodosRuta.get(i);
                    if (auxNodo.equals(t.getDestino()) && y.equals(t.getOrigen()) || auxNodo.equals(t.getOrigen()) && y.equals(t.getDestino())){
                        aristaRuta.add(t); 
                    }
                }
            });
        });
        
        return rutInteger;
        
    }

    public void recuperaCamino(int i, int j, Integer[][] mRecorrido) {
        rutInteger.add(i);
        recupera(i, j, mRecorrido);
        rutInteger.add(j);
    }

    public void recupera(int i, int j, Integer[][] mRecorrido) {
        int k = mRecorrido[i][j];
        if (k != i) {
            recupera(i, k, mRecorrido);
            System.out.println(k);
            rutInteger.add(k);
            recupera(k, j, mRecorrido);
        }
    }

    public void marcarRuta() {
        aristaRuta.stream().forEach((t) -> {
            t.setStroke(Color.BLACK);
            t.setStrokeWidth(5);
        });
    }

    public ArrayList<Nodo> getNodosRuta() {
        return nodosRuta;
    }

    public void setNodosRuta(ArrayList<Nodo> nodosRuta) {
        this.nodosRuta = nodosRuta;
    }

    public ArrayList<Arista> getAristaRuta() {
        return aristaRuta;
    }

    public void setAristaRuta(ArrayList<Arista> aristaRuta) {
        this.aristaRuta = aristaRuta;
    }

}