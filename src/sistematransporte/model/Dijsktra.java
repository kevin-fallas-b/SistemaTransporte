/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.model;

/**
 *
 * @author Carlos Olivares
 */
public class Dijsktra {
    /*
    public String DijkstraPrincipal(E v_origen, E v_destino) {
        String camino = "";//camino final que sera devuelto.
 
 
        LinkedList<E> listtemp = new LinkedList<>();//aqui guardo la lista que devuelve el metodo al que llamo abajo
        listtemp = RutaMasCortaDijkstra(v_origen, v_destino);//para invertir la lista
 
 
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
 
    public LinkedList<E> RutaMasCortaDijkstra(E v_origen, E v_destino) {
        //posVertpadre: va a guardar en el como valor interno la pos del vertice padre de acuerdo a su posicion(posision en el array es la del vertice hijo y el valor en esa posicion es la posicion del padre).
        int[] posVertPadre = new int[list_vertices.size()];
        for (int i = 0; i < posVertPadre.length; i++) {
            posVertPadre[i] = -1;
        }
 
        //hago una copia de la matrizpara no modificar la original.
        int[][] matrizAdyacentes = new int[list_vertices.size()][list_vertices.size()];//creo la nueva matriz.
        for (int i = 0; i < matriz_adyac.length; i++) {
            for (int j = 0; j < matriz_adyac.length; j++) {
                matrizAdyacentes[i][j] = matriz_adyac[i][j];
            }
        }
 
        //hago una copia de la lista de vertices para no modificar la original.
        LinkedList<Vertice<E>> listVertVisitados = new LinkedList<>();//lista que sera la de los vertices sin visitar.
        for (Vertice<E> i : list_vertices) {
            listVertVisitados.add(i);//lista q va a ir eliminando los vertices que sean visitados
        }
 
        LinkedList<E> ruta = new LinkedList<>();//esta lista es la final que devuelve el camino minimo.
 
        //inicializo todos los valores de la diagonal de la matriz en el valor mas alto posible
        for (int i = 0; i < matrizAdyacentes.length; i++) {
            matrizAdyacentes[i][i] = 9999999;
        }
        //inicializo el grado del vertice de origen en 0;
        matrizAdyacentes[PosVertice(v_origen)][PosVertice(v_origen)] = 0;
        BuscarRutaMasCortaR(v_origen, v_destino, matrizAdyacentes, listVertVisitados, posVertPadre);
        ruta = ConstruirCamino(posVertPadre, v_origen, v_destino);//a ruta le asigno la lista de la ruta que devuelve el metodo.
 
        costoDijkstra = matrizAdyacentes[PosVertice(v_destino)][PosVertice(v_destino)];//variable creada en el constructor de la clase, se le asign el costo de dijkstra
//        for (int i = 0; i < ruta.size(); i++) {
//            for (int j = 1; j < ruta.size(); j++) {
//                costoDijkstra = costoDijkstra + matriz_adyac[PosVertice(ruta.get(j))][PosVertice(ruta.get(i))];
//            }
//        }
 
        return ruta;
    }
 
    public int[] BuscarRutaMasCortaR(E v_actual, E v_destino, int[][] matrizAdyacentes, LinkedList<Vertice<E>> listVertVisitados, int[] posVertPadre) {
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
            for (Vertice v : ListVertDestino(v_actual)) {
                //ahora suma el valor del vertice q se esta visitando(actual) con el peso de la arista entre el que se esta visitando y el adyacente actual en que esta parado el for.
                suma = matrizAdyacentes[PosVertice(v_actual)][PosVertice(v_actual)] + matrizAdyacentes[PosVertice(v_actual)][v.getPos()];
 
                //busca el minimo entre el vertice q se esta parado en el for y el de la suma anterior
                if (matrizAdyacentes[v.getPos()][v.getPos()] < suma) {
                    min = matrizAdyacentes[v.getPos()][v.getPos()];
                } else {
                    min = suma;
                }
                if (matrizAdyacentes[v.getPos()][v.getPos()] != min) {//control para q no vuelva adarle el mismo valor y que no cambie el padre
                    matrizAdyacentes[v.getPos()][v.getPos()] = min;//en la diagonal de la matriz guarda el grado que va tomando cada vertice, si el valor actual es mayor lo cambia por el nuevo que es menor.
                    posVertPadre[v.getPos()] = PosVertice(v_actual);//a la pos del vertice hijo le asigno la pos del vertice padre(el que le dio el valor)
                }
            }
            //para eliminar el de a lista de vertices visitados el que ya fue visitado.
            for (Vertice v : list_vertices) {//recorre la lista original de vertices
                if (v.getInfo().equals(v_actual)) {//si el vertice de la lista es igual al v_actual
                    for (int i = 0; i < listVertVisitados.size(); i++) {//comienza recorrido por la lista de vertices visitados
                        if (listVertVisitados.get(i).getInfo().equals(v.getInfo())) {//si la info del vertice en q esta el for es igual a la del q esta el for anterior
                            listVertVisitados.remove(i);//elimina el vertice de acuerdo a la pos actual de este for.
                            break;//rompe el do for, si encontro al vertice
                        }
                    }
                    break;//rompe el primer for, si encontro al vertice
                }
            }
//                listVertVisitados.remove(PosVertice(v_actual));//como es visitado lo saco de la lista.
            //recorrer la lista de vertices visitados y buscar el de menor grado y volver a hacer lo mismo hasta que los vertices actual y visitado sean el mismo o que la lista de visitados este vacia.
            for (Vertice v : listVertVisitados) {
                if (matrizAdyacentes[v.getPos()][v.getPos()] < aux) {//si es menor el valor de la pos en la matriz al guardado en aux.
                    aux = matrizAdyacentes[v.getPos()][v.getPos()];//aux se actualiza a ese valor
                    temp = v.getPos();//y guardo la pos del vertice menor anteriormente guardado en aux.
                }
            }
 
            return BuscarRutaMasCortaR(list_vertices.get(temp).getInfo(), v_destino, matrizAdyacentes, listVertVisitados, posVertPadre);
            //la E es para castear.
        }
    }
 
    public LinkedList<Vertice> ListVertDestino(E v_origen) {//devuelve una lista de vertices de destino a uno dado como origen.
        LinkedList<Vertice> listdestino = new LinkedList<>();
 
        for (int i = 0; i < matriz_adyac.length; i++) {
            if (matriz_adyac[PosVertice(v_origen)][i] != -1) {
                listdestino.add(list_vertices.get(i));
 
            }
        }
        return listdestino;
    }
 
    public LinkedList<E> ConstruirCamino(int[] posVertPadre, E v_origen, E v_destino) {
        LinkedList<E> aux = new LinkedList<>();//list q guarda la ruta de atras para alante
        int posVertAnterior = -1;//variable temporal para moverse por el arreglo de vertices padres
        aux.add(v_destino);//añade vertice de destino
        return ConstruirCaminoR(posVertPadre, v_origen, v_destino, aux, posVertAnterior);
    }
//recursivo
 
    public LinkedList<E> ConstruirCaminoR(int[] posVertPadre, E v_origen, E v_destino, LinkedList<E> aux, int posVertAnterior) {
        if (posVertAnterior == PosVertice(v_origen)) {
//            aux.add(v_origen);//añade vertice de origen antes de devolverla
            return aux;
        } else {
            posVertAnterior = posVertPadre[PosVertice(v_destino)];//a pospadre le asigno la pos del vertice adyacente al destino que le dio el valor
            aux.add(list_vertices.get(posVertAnterior).getInfo());//añade el vertice a la lista
            return ConstruirCaminoR(posVertPadre, v_origen, list_vertices.get(posVertAnterior).getInfo(), aux, posVertAnterior);
        }
    }
    
    */
}
