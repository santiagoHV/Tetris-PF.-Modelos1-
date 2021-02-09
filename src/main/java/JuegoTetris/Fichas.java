/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTetris;

import java.util.ArrayList;

/**
 *
 * @author jrqui
 */
public abstract class Fichas {
    /* Aqui empieza lo chido puta
     * Estos son los metodos que se heredan y manejan cada una de las fichas */
    public abstract void crearFicha();
    public abstract void ubicarFicha(int[][] matriz);
    public abstract void moverFichaIzq(int[][] matriz);
    public abstract void moverFichaDer(int[][] matriz);
    public abstract void moverFichaAba(int[][] matriz);
    public abstract void girarFicha(int[][] matriz);
    public abstract void reset();
    public abstract boolean analizarBordeAbajo(int[][] matriz);
    
    /* Toda esta basura se hereda as well */
    public boolean var;
    public boolean bloqueo;
    public boolean movimiento;
    public boolean ubicacion;
    public int numFicha;
    public int numFicha2;
    public String posicion;
    public String direccion;
    ArrayList<ArrayList<Integer>>matrizFicha;
    public int filas;
    public int columnas;
    public int fila;
    public int columna;
    
    /* Bueno, con este metodo vamos a saber la posicion de la ficha analizando la matriz desde arriba
    * de izq a derecha */
    public void saberPosArri(int[][] matriz, Fichas ficha){
        ficha.var = false;
        for(int i = 0 ; i<matriz.length ; i++){
            for(int j = 0 ; j<matriz[i].length ; j++){
                if(!ficha.movimiento){
                    if(matriz[i][j]== ficha.numFicha2){
                       ficha.fila = i;
                       ficha.columna = j;
                       ficha.var = true;
                       break;
                    }
                }else{
                    if(matriz[i][j]== ficha.numFicha){
                        ficha.fila = i;
                        ficha.columna = j;
                        ficha.var = true;
                        break;
                    }
                }
            }
            if(ficha.fila == i && ficha.var){
                break;
            }
        }
    }
    /* y con este vamos a saber la posicion de la ficha analizando la matriz desde abajo
    * de izq a derecha */
    public void saberPosAba(int[][] matriz, Fichas ficha){
        ficha.var = false;
        for(int i = matriz.length-1 ; i>=0 ; i--){
            for(int j = 0 ; j<matriz[i].length ; j++){
                if(matriz[i][j] == ficha.numFicha){
                    ficha.fila = i;
                    ficha.columna = j;
                    ficha.var = true;
                    break;
                }
            }
            if(ficha.fila == i && ficha.var){
                break;
            }
        }
    }

    /*Esta mondae se encarga de bloquear la ficha cuando llega al final o
     cuando se encuentra con otra ficha debajo*/

    public void bloquearFicha(int[][]matriz,Fichas ficha){
        saberPosArri(matriz,ficha);
        if(!ficha.movimiento){
            for(int x = matriz.length-1; x>=0 ; x--){
                for(int y = 0; y<matriz[x].length; y++){
                    if(matriz[x][y] == ficha.numFicha){
                        matriz[x][y] = ficha.numFicha2;
                    }
                }
            }
        }
        ubicacion = false;
        bloqueo = true;
    }
}
