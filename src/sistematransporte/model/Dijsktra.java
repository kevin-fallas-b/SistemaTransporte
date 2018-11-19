/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.paint.Color;
import static sistematransporte.controller.PantPrincipalController.matPeso;
import static sistematransporte.model.Mapa.destinos;

/**
 *
 * @author Carlos Olivares
 */
/*public class Dijsktra {

    public Dijsktra() {
    }
    
    
    public String DijkstraPrincipal(Nodo n_origen, Nodo n_destino) {
        String camino = "";//camino final que sera devuelto.
 
 
        LinkedList <Nodo> listtemp = new LinkedList<>();//aqui guardo la lista que devuelve el metodo al que llamo abajo
        listtemp = RutaMasCortaDijkstra(n_origen, n_destino);//para invertir la lista
 
 
        //recorrido para crear el string
        for (int i = listtemp.size() - 1; i >= 0; i--) {//recorre la lista de atras para alante
            if (camino.isEmpty()) {
                camino = listtemp.get(i).toString();//como es el primer elemento(ultimo en la lista) lo añade.
            } else {
                camino = camino.concat(", " + listtemp.get(i).toString());
            }
        }
        return camino;
    }
 
    public LinkedList<Nodo> RutaMasCortaDijkstra(Nodo v_origen, Nodo v_destino) {
        //posVertpadre: va a guardar en el como valor interno la pos del vertice padre de acuerdo a su posicion(posision en el array es la del vertice hijo y el valor en esa posicion es la posicion del padre).
        int[] posVertPadre = new int[destinos.size()];
        for (int i = 0; i < posVertPadre.length; i++) {
            posVertPadre[i] = -1;
        }
 
        //hago una copia de la matrizpara no modificar la original.
        Integer[][] matrizAdyacentes = new Integer[destinos.size()][destinos.size()];//creo la nueva matriz.
        for (int i = 0; i < matPeso.length; i++) {
            for (int j = 0; j < matPeso.length; j++) {
                matrizAdyacentes[i][j] = matPeso[i][j];
            }
        }
 
        //hago una copia de la lista de vertices para no modificar la original.
        LinkedList<Nodo> listVertVisitados = new LinkedList<>();//lista que sera la de los vertices sin visitar.
        for (Nodo i : destinos) {
            listVertVisitados.add(i);//lista q va a ir eliminando los vertices que sean visitados
        }
 
        LinkedList<Nodo> ruta = new LinkedList<>();//esta lista es la final que devuelve el camino minimo.
 
        //inicializo todos los valores de la diagonal de la matriz en el valor mas alto posible
        for (int i = 0; i < matrizAdyacentes.length; i++) {
            matrizAdyacentes[i][i] = 9999999;
        }
        //inicializo el grado del vertice de origen en 0;
        matrizAdyacentes[v_origen.getNumNodo()][v_origen.getNumNodo()] = 0;
        BuscarRutaMasCortaR(v_origen, v_destino, matrizAdyacentes, listVertVisitados, posVertPadre);
        ruta = ConstruirCamino(posVertPadre, v_origen, v_destino);//a ruta le asigno la lista de la ruta que devuelve el metodo.
 
        int costoDijkstra = matrizAdyacentes[v_destino.getNumNodo()][v_destino.getNumNodo()];//variable creada en el constructor de la clase, se le asign el costo de dijkstra
//        for (int i = 0; i < ruta.size(); i++) {
//            for (int j = 1; j < ruta.size(); j++) {
//                costoDijkstra = costoDijkstra + matriz_adyac[PosVertice(ruta.get(j))][PosVertice(ruta.get(i))];
//            }
//        }
 
        return ruta;
    }
 
    public int[] BuscarRutaMasCortaR(Nodo v_actual, Nodo v_destino, Integer[][] matrizAdyacentes, LinkedList<Nodo> listVertVisitados, int[] posVertPadre) {
        //FORMULA: Min{L(x),L(v)+w(v,x)}, T{lista de vertices}, V=vertice actual q se esta visitando
        if (v_actual.equals(v_destino) || listVertVisitados.isEmpty()) {
            return posVertPadre;//retorna el arreglo
        } else {
            //declaracion de variables
            int suma;//guarda la distancia desde el valor del vertice+peso de la arista.
            int min;//guarda el valor minimo encontrado en la formula
            int aux = 9999999;//inicializo la variable en el mayor valor posible.Guarda el valor de la diagonal principal, para compararlo.
            int temp = 0;
 
            //COMENZANDO        
            //lo siguiente busca en los adyacentes al vertice de origen el de menor distancia
            for (Nodo v : ListVertDestino(v_actual)) {
                //ahora suma el valor del vertice q se esta visitando(actual) con el peso de la arista entre el que se esta visitando y el adyacente actual en que esta parado el for.
                suma = matrizAdyacentes[v_actual.getNumNodo()][v_actual.getNumNodo()] + matrizAdyacentes[v_actual.getNumNodo()][v.getNumNodo()];
 
                //busca el minimo entre el vertice q se esta parado en el for y el de la suma anterior
                if (matrizAdyacentes[v.getNumNodo()][v.getNumNodo()] < suma) {
                    min = matrizAdyacentes[v.getNumNodo()][v.getNumNodo()];
                } else {
                    min = suma;
                }
                if (matrizAdyacentes[v.getNumNodo()][v.getNumNodo()] != min) {//control para q no vuelva adarle el mismo valor y que no cambie el padre
                    matrizAdyacentes[v.getNumNodo()][v.getNumNodo()] = min;//en la diagonal de la matriz guarda el grado que va tomando cada vertice, si el valor actual es mayor lo cambia por el nuevo que es menor.
                    posVertPadre[v.getNumNodo()] = v_actual.getNumNodo();//a la pos del vertice hijo le asigno la pos del vertice padre(el que le dio el valor)
                }
            }
            //para eliminar el de a lista de vertices visitados el que ya fue visitado.
            for (Nodo v : destinos) {//recorre la lista original de vertices
                if (v.equals(v_actual)) {//si el vertice de la lista es igual al v_actual
                    for (int i = 0; i < listVertVisitados.size(); i++) {//comienza recorrido por la lista de vertices visitados
                        if (listVertVisitados.get(i).equals(v)) {//si la info del vertice en q esta el for es igual a la del q esta el for anterior
                            listVertVisitados.remove(i);//elimina el vertice de acuerdo a la pos actual de este for.
                            break;//rompe el do for, si encontro al vertice
                        }
                    }
                    break;//rompe el primer for, si encontro al vertice
                }
            }
//                listVertVisitados.remove(PosVertice(v_actual));//como es visitado lo saco de la lista.
            //recorrer la lista de vertices visitados y buscar el de menor grado y volver a hacer lo mismo hasta que los vertices actual y visitado sean el mismo o que la lista de visitados este vacia.
            for (Nodo v : listVertVisitados) {
                if (matrizAdyacentes[v.getNumNodo()][v.getNumNodo()] < aux) {//si es menor el valor de la pos en la matriz al guardado en aux.
                    aux = matrizAdyacentes[v.getNumNodo()][v.getNumNodo()];//aux se actualiza a ese valor
                    temp = v.getNumNodo();//y guardo la pos del vertice menor anteriormente guardado en aux.
                }
            }
 
            return BuscarRutaMasCortaR(destinos.get(temp), v_destino, matrizAdyacentes, listVertVisitados, posVertPadre);
            //la E es para castear.
        }
    }
 
    public LinkedList<Nodo> ListVertDestino(Nodo v_origen) {//devuelve una lista de vertices de destino a uno dado como origen.
        LinkedList<Nodo> listdestino = new LinkedList<>();
 
        for (int i = 0; i < matPeso.length; i++) {
            if (matPeso[v_origen.getNumNodo()][i] != -1) {
                listdestino.add(destinos.get(i));
 
            }
        }
        return listdestino;
    }
 
    public LinkedList<Nodo> ConstruirCamino(int[] posVertPadre, Nodo v_origen, Nodo v_destino) {
        LinkedList<Nodo> aux = new LinkedList<>();//list q guarda la ruta de atras para alante
        int posVertAnterior = -1;//variable temporal para moverse por el arreglo de vertices padres
        aux.add(v_destino);//añade vertice de destino
        return ConstruirCaminoR(posVertPadre, v_origen, v_destino, aux, posVertAnterior);
    }
//recursivo
 
    public LinkedList<Nodo> ConstruirCaminoR(int[] posVertPadre, Nodo v_origen, Nodo v_destino, LinkedList<Nodo> aux, int posVertAnterior) {
        if (posVertAnterior == v_origen.getNumNodo()) {
//            aux.add(v_origen);//añade vertice de origen antes de devolverla
            return aux;
        } else {
            posVertAnterior = posVertPadre[v_destino.getNumNodo()];//a pospadre le asigno la pos del vertice adyacente al destino que le dio el valor
            aux.add(destinos.get(posVertAnterior));//añade el vertice a la lista
            return ConstruirCaminoR(posVertPadre, v_origen, destinos.get(posVertAnterior), aux, posVertAnterior);
        }
    }
    
    
}*/
public class Dijsktra {

    Mapa grafo;
    ArrayList<Nodo> listaNodosAdyacentes;
    ArrayList<Arista> aux = new ArrayList<Arista>();

    public Dijsktra(Mapa grafo) {
        this.grafo = grafo;
        listaNodosAdyacentes = new ArrayList<>();
    }

    public Dijsktra() {
        }
public boolean isContenido(Nodo nodo){
        boolean retornado = false;
        for(Nodo n:listaNodosAdyacentes){
            if(n == nodo){
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
                        aux2=enlace.getDestino();
                    }
                    else
                    {
                        aux2=enlace.getOrigen();
                    }
                    
                    if (!aux2.isMarca()) {

                        if (isContenido(aux2)) {
                            //System.out.println("aux2 "+ aux2);
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
public Nodo buscarMenor(){
        Nodo aux = new Nodo();
        aux.setLongitudCamino(9999999);

        for(Nodo nodo:listaNodosAdyacentes){
            if(nodo.getLongitudCamino() < aux.getLongitudCamino()){
                aux = nodo;                
            }
        }

        return aux;
    }
    public void ejecutar(Nodo nodoInicio) {
        nodoInicio.setLongitudCamino(0);
        if (nodoInicio != null) {
            listaNodosAdyacentes = new ArrayList <>();
            listaNodosAdyacentes.add(nodoInicio);
            while (!listaNodosAdyacentes.isEmpty()) {
                Nodo menor = buscarMenor();
                menor.setMarca(true);
                listaNodosAdyacentes.remove(menor);
                llenarConAdyacentes(menor);
            }
        }
    }

    private void rutaCorta(Nodo nodoFinal) {
        aux.clear();
        Nodo nAux = nodoFinal;
        System.out.println("nAux "+nAux.getNodoAntecesorDisjktra());
        while (nAux.getNodoAntecesorDisjktra() != null) {
//        aux.add(grafo.getArista(nAux.getCapital().getNombreCiudad(),
//                nAux.getNodoAntecesorDisjktra().getCapital().getNombreCiudad()));
            aux.add(grafo.getArista(nAux,
                    nAux.getNodoAntecesorDisjktra()));
            nAux = nAux.getNodoAntecesorDisjktra();
        }

    }

    public void marcarRutaCorta(Nodo nodoFinal, Color color) {
        if (nodoFinal != null) {
            rutaCorta(nodoFinal);
           /* for (int i = 0; i < aux.size(); i++) {
                if (!aux.isEmpty()) {
                   /* aux.get(i).getLineaQuebrada().setColor(color);
                    aux.get(i).getLineaQuebrada().setGrosorLinea(4);
                   aux.get(i).setStroke(color);
                   aux.get(i).setStrokeWidth(10);
                }*/
           if (aux.isEmpty()) {
               System.out.println("estoy vacia");
           }
           aux.stream().forEach((t) -> {
               if(t!=null){
                t.setStroke(color);
                t.setStrokeWidth(10);   
               }
               //System.out.println("t "+ t);
              // t.getPeso();
               //t.setFill(color);
               //t.setStrokeWidth(10);
           });
            }
        }
}
