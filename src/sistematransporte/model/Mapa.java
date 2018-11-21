/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author Carlos Olivares
 */
public class Mapa extends ImageView {

    public static LinkedList<Nodo> destinos = new LinkedList();
    private final LinkedList<Arista> aristas = new LinkedList();
    private LinkedList<Nodo> listaDirigida = new LinkedList();

    public LinkedList<Nodo> getListaDirigida() {
        return listaDirigida;
    }

    public Mapa() {
        super();
        this.setFitHeight(600);
        this.setFitWidth(422);
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setId("map");

    }

    public LinkedList<Arista> getAristas() {
        return aristas;
    }

    public Arista getArista(Nodo origen, Nodo destino) {
        Arista aux = null;
        for (Arista a : getAristas()) {
            if ((a.getOrigen().equals(origen) && a.getDestino().equals(destino)) || (a.getDestino().equals(origen) && a.getOrigen().equals(destino))) {
                aux = a;
            }
        }
        return aux;
    }

    public void agregarDestino(Nodo nod) {
        destinos.add(nod);
    }

    public LinkedList<Nodo> getDestinos() {
        return destinos;
    }

    public void guardarDestinosAArchivo() throws UnsupportedEncodingException, FileNotFoundException {
        PrintWriter writer = new PrintWriter("src/sistematransporte/util/Destinos.txt", "UTF-8");
        while (!destinos.isEmpty()) {
            Nodo nod = destinos.removeFirst();
            String cordenadas = nod.getCenterX() + "$" + nod.getCenterY();
            writer.println(cordenadas);
        }
        writer.close();
    }

    public void cargarNodo() throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/sistematransporte/util/Destinos.txt"));
        String line = null;
        Integer i = 0;
        while ((line = reader.readLine()) != null) {
            String[] parts;
            parts = line.split("\\$");
            Double posx = Double.valueOf(parts[0]);
            Double posy = Double.valueOf(parts[1]);

            Nodo nod = new Nodo(posx, posy, 5.00, Color.CORAL, i);
            nod.setPuntoMapa(new Point2D(posx, posy));
            i++;
            destinos.add(nod);
        }
    }

    public void cagarAristas() throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/sistematransporte/util/aristas.txt"));
        String line = null;

        while ((line = reader.readLine()) != null) {
            String[] parts;
            parts = line.split("\\$");
            Double posx = Double.valueOf(parts[0]);
            Double posy = Double.valueOf(parts[1]);
            Double posx2 = Double.valueOf(parts[2]);
            Double posy2 = Double.valueOf(parts[3]);

            Arista arista = new Arista(posx, posy, posx2, posy2);

            arista.agregarNodos(destinos);

            aristas.add(arista);
        }
    }

    public void cargarAristasDirigidas() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/sistematransporte/util/AristasDirigidas.txt"));
        String line = null;
        ArrayList<Arista> aristas = new ArrayList<Arista>();
        while ((line = reader.readLine()) != null) {
            String[] parts;
            parts = line.split("\\$");
            Double posx = Double.valueOf(parts[0]);
            Double posy = Double.valueOf(parts[1]);
            Double posx2 = Double.valueOf(parts[2]);
            Double posy2 = Double.valueOf(parts[3]);

            Arista aristaNuevas = new Arista(posx2, posy2, posx, posy);
            aristaNuevas.setDestino(identificarNodo(posx, posy));
            aristaNuevas.setOrigen(identificarNodo(posx2, posy2));
            aristas.add(aristaNuevas);

        }
        while (!aristas.isEmpty()) {
            Nodo nodo = aristas.get(0).getOrigen();
            if (listaDirigida.contains(nodo)) {
                if (!nodo.getNodosAdyacentes().contains(aristas.get(0).getDestino()) && aristas.get(0).getDestino() != nodo) {
                    nodo.getNodosAdyacentes().add(aristas.get(0).getDestino());
                }
            } else {
                if (!nodo.getNodosAdyacentes().contains(aristas.get(0).getDestino()) && aristas.get(0).getDestino() != nodo) {
                    nodo.getNodosAdyacentes().add(aristas.get(0).getDestino());
                }
                listaDirigida.add(nodo);
            }
            aristas.remove(0);
        }
    }

    private Nodo identificarNodo(Double x, Double y) {
        for (int i = 0; i < destinos.size(); i++) {
            if (destinos.get(i).getCenterX() == x && destinos.get(i).getCenterY() == y) {
                return destinos.get(i);
            }

        }
        return null;
    }
}
