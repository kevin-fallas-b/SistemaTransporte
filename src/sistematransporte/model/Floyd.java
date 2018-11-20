/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.PriorityQueue;
import java.util.Stack;

public class Floyd {


    public Floyd() {
    }
    
    public int [] floyd_cam(Integer[][] mady,int ini, int fin){

    //RutaCorta ruta;
    int aux;
    Integer mCaminos[][] = new Integer[mady.length][mady.length];

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
                System.out.println("saltos tamaño "+saltos.length);
                for(int i = 0; i< saltos.length;i++)
                {
                    System.out.println("camino " +saltos[i] );
                }
                return saltos;
}
    
public int[] recorrido(Integer[][] mPesos, Integer [][] mRecorrido, int ini, int fin){

    Stack<Integer> pila = new Stack<>();
    Integer[][] recorrido = mRecorrido;
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

    

