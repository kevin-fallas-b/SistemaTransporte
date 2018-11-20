/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.PriorityQueue;
import java.util.Stack;

public class Floyd {
    
    public int [] floyd_cam(int[][] mady,int ini, int fin){
    //RutaCorta ruta;
    int aux;
    int mCaminos[][] = new int[mady.length][mady.length];

        for(int x= 0; x < mady.length;x++){
            for(int y = 0; y < mady.length;y++){
                mCaminos[y][x] = y;
            }
        }
        
                for(int x = 0; x < mady.length;x++){
                    for(int y = 0; y < mady.length; y++){
                        if(x!=y){
                            for(int z = 0; z < mady.length;z++)
                            {
                                if(z!=x){
                                    aux=mady[x][y]+mady[z][x];
                                    
                                    if(aux<=mady[z][y]){                                        
                                        mady[z][y]=aux;
                                        mCaminos[z][y] =x;
                                    }
                                }
                            }                        
                        }
                    }
                }

                //imprimir(mady);
                //imprimir(mCaminos);
                int[] saltos=recorrido( mady, mCaminos, ini, fin);
                //ruta= new RutaCorta(mady[ini][fin],saltos.length-2,0,saltos);
                return saltos;
}
    
public int[] recorrido(int[][] mPesos, int [][] mRecorrido, int ini, int fin){

    Stack<Integer> pila = new Stack<>();
    int[][] recorrido = mRecorrido;
    int[] nodos;
    int i=fin;
    int j=ini;
    int cont=0;

    //System.out.print("ini "+ini+" fin= "+fin);
    pila.add(ini);
    while(recorrido[i][j]!=j){
       //System.out.print(" "+recorrido[i][j]+" ");
       pila.push(recorrido[i][j]);
       j =recorrido[i][j];
        cont++;
    }
    nodos=new int[cont+1];
    cont=0;
      while(!pila.isEmpty()){
        
            nodos[cont]=pila.pop();
			cont++;
        }
          

    return nodos;
}
    
}

    

