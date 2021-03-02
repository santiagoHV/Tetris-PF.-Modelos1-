/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTetris;

import Modelo.estados.ContextoDeEstados;
import Modelo.factories.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author jrqui
 */
public class Logica {
    private String[] player;
    private String puntajeMax;
    public boolean juego;
    public int lineaLlena;
    public int puntaje;
    public int matriz[][];
    public int matrizSig[][];
    private int estado;
    private int estado2;
    public int nivel;
    private boolean var;
    public int lineasLlenas[];
    private Timer timer;
    private Timer timer2;
    private TimerTask task;
    private TimerTask task2;

    private Fichas fichaSig;
    public Fichas fichaActual;
    private Factory factory = new FactoryLevel1();

    public Logica(String [] jugador) {
        ContextoDeEstados contextoDeEstados = new ContextoDeEstados();

        puntajeMax = "200";
        player = jugador;
        puntaje = 0;
        juego = false;
        nivel = 1;
        lineaLlena = 0;
        lineasLlenas = new int[20];
        matriz = new int[20][10];
        matrizSig = new int[6][6];
        estado = 0;
        estado2 = 0;
        var = true;

        //Matriz llena de 0
        iniciarMatriz(matriz);
        iniciarMatriz(matrizSig);
        //crea fichas
        escogerFicha(factory);


        timer = new Timer();
        timer2 = new Timer();


        task = new TimerTask() {
            @Override
            public void run() {
                /* Si el estado es 1 selecciona una ficha y la agrega a la matriz, luego la empieza a mover hacia
                 * abajo, marca la ficha siguiente, y la ubica en la matriz 2, luego setea el estado a 2 */
                if(estado == 1 ){
                    if(fichaSig == null){
                        fichaSig = escogerFicha(factory);
                    }
                    contextoDeEstados.getEstado().moviendose();
                    fichaActual = fichaSig;
                    fichaActual.ubicarFicha(matriz);
                    fichaActual.moverFichaAba(matriz);
                    fichaSig = escogerFicha(factory);
                    iniciarMatriz(matrizSig);
                    fichaSig.ubicarFicha(matrizSig);
                    fichaSig.moverFichaAba(matrizSig);
                    estado = 2;
                }else{
                    /* En este estado se encarga de bajar la ficha sin agregar mas fichas mientras baja,
                     * tambien se evalua cuando la ficha detiene movimiento si gana o no y
                     *  dependiendo de esto se guarda la victoria o perdida*/
                    if(estado == 2){
                        contextoDeEstados.getEstado().moviendose();
                        fichaActual.moverFichaAba(matriz);
                        if(!fichaActual.movimiento){
                            estado2 = 1;
                            if(evaluarVictoria(matriz,fichaActual)){
                                estado = 1;
                                fichaActual.reset();
                            }else{
                                estado = 3;
                            }
                        }
                    }else{
                        contextoDeEstados.getEstado().quieta();
                        if(estado == 3){
                            perder(player);
                            estado = 0;
                            iniciarMatriz(matriz);
                        }else{
                            if(estado == 4){
                                ganar(player);
                                niveles(player);
                                estado = 0;
                                iniciarMatriz(matriz);
                            }
                        }
                    }
                }
            }
        };
        task2 = new TimerTask(){
            @Override
            public void run() {
                /* Este segundo timer es para manejar otro estado, evalua la matriz y el puntaje maximo del
                 *  jugador, si no hay lineas llenas y el puntaje es maximo se gana y se apaga el timer*/
                if(estado2 == 1 ){
                    evaluarMatriz(matriz,player);
                    if(lineaLlena == 0){
                        if(player[1].equals(puntajeMax)){
                            estado = 4;
                        }
                        estado2 = 0;
                    }
                }
            }
        };
        timer2.schedule(task2, 0, 100);
        timer.schedule(task, 0, 1000);

    }


    //Con esto se crea la matriz logica llena de 0's
    public void iniciarMatriz(int[][]matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = 0;
            }
        }
    }
    /* Aqui se manejan los niveles, en cada nivel se aumenta la velocidad con la que caen las fichas, y
    *  asi mismo el puntaje necesario para ganar cada lvl*/
    public void niveles(String[]jugador){
        nivel = Integer.parseInt(jugador[8]);
        switch(nivel){
            case 1:
                this.factory = new FactoryLevel1();
                puntajeMax = "200";
                timer.schedule(task, 0,this.factory.retornarTiempo());//1000
                break;
            case 2:
                this.factory = new FactoryLevel2();
                puntajeMax = "1000";
                timer.schedule(task, 0,this.factory.retornarTiempo());//100
                break;
            case 3:
                this.factory = new FactoryLevel3();
                puntajeMax = "1500";
                timer.schedule(task, 0,this.factory.retornarTiempo());//800
                break;
            case 4:
                this.factory = new FactoryLevel4();
                puntajeMax = "2000";
                timer.schedule(task, 0,this.factory.retornarTiempo());//700
                break;
            case 5:
                this.factory = new FactoryLevel5();
                puntajeMax = "2500";
                timer.schedule(task, 0,this.factory.retornarTiempo());//600
                break;
            default:
                this.factory = new FactoryLevel1();
                timer.schedule(task, 0,5000);
                break;
        }
    }

    //Escoge una ficha al azar del arreglo
    public Fichas escogerFicha(Factory factory) {
        return factory.retornarFicha();
    }

    //Este analiza la matriz en busca de una matriz llena de numeros diferentes de 0
    public void buscarLineaLlena(int[][] matriz){
        lineaLlena = 0;
        boolean lineaLlenaValidacion  = true;
        for(int i = matriz.length-1; i>=0 ;i--){
            for(int j = 0; j < matriz[i].length ; j++){
                if(matriz[i][j]== 0){
                    lineaLlenaValidacion = false;
                    break;
                }else{
                   lineaLlenaValidacion = true;
                }
            }
            if(lineaLlenaValidacion){
                lineaLlena = i ;
                break;
            }
        }
    }

    public void limpiarLinea(int linea ,int[][]matriz){
        for(int i = linea; i<linea+1;i++){
            for(int j = 0; j<matriz[i].length; j++){
                matriz[i][j] = 0;
            }
            lineasLlenas[i] = 0;
        }
        player[1] = String.valueOf(Integer.parseInt(player[1])+100);
    }
    //Iguala los puntos del jugador :v
    public void sumarPuntos(String[] jugador){
        jugador[1] = player[1];
    }

    //Esto pues guarda los puntajes en el arreglo jugador
    public void ganar(String[] jugador){
        jugador[5] = String.valueOf(Integer.parseInt(player[1]));
        jugador[1] = String.valueOf(0);
        jugador[2] = String.valueOf(Integer.parseInt(player[2])+1) ;
        jugador[3] = String.valueOf(Integer.parseInt(player[3])+1);
        jugador[6] = String.valueOf(Integer.parseInt(player[6])+1);
        
    }
    //Lo mismo que el de arriba pero este le resta por perdedor
    public void perder(String[] jugador){
        jugador[5] = String.valueOf(Integer.parseInt(player[1]));
        jugador[1] = String.valueOf(0);
        jugador[2] = String.valueOf(Integer.parseInt(player[2])+1);
        jugador[4] = String.valueOf(Integer.parseInt(player[4])+1);
        
    }

    //Reajusta la matriz despues de quitar la linea llena
    public void ajustarMatriz(int linea, int[][]matriz){
        for(int i = linea-1; i>=0; i--){
            for(int j = 0; j<matriz[i].length; j++){
                matriz[i+1][j] = matriz[i][j];
            }
        }
    }
    //Evalua la matriz en busca de lineas llenas
    public void evaluarMatriz(int[][] matriz, String[]jugador){
        buscarLineaLlena(matriz);
        if(lineaLlena!=0){
            if(fichaActual.ubicacion){
                limpiarLinea(lineaLlena,matriz);
                sumarPuntos(jugador);
                ajustarMatriz(lineaLlena,matriz);
            }
        }
    }
    //Metodo que da la victoria o la derrota
    public boolean evaluarVictoria(int[][]matriz, Fichas ficha){
        boolean ganar = true;
        ficha.saberPosArri(matriz,ficha);
        if(ficha.fila == 0){
            ganar = false;
        }
        return ganar;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado2() {
        return estado2;
    }

    public void setEstado2(int estado2) {
        this.estado2 = estado2;
    }



}
