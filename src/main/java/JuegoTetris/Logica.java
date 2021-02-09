/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTetris;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    //Array donde van las fichas para elegir una al azar
    private ArrayList<Fichas> fichas;
    //Creacion de la fichas
    private O o;
    private I iF;
    private S s;
    private Z z;
    private L l;
    private J jF;
    private T t;
    

    private Random r1;
    private int in1;
    private Fichas fichaSig;
    public Fichas fichaActual;

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
                puntajeMax = "200";
                timer.schedule(task, 0,1000);
                break;
            case 2:
                puntajeMax = "1000";
                timer.schedule(task, 0,100);
                break;
            case 3:
                puntajeMax = "1500";
                timer.schedule(task, 0,800);
                break;
            case 4:
                puntajeMax = "2000";
                timer.schedule(task, 0,700);
                break;
            case 5:
                puntajeMax = "2500";
                timer.schedule(task, 0,600);
                break;
            default:
                timer.schedule(task, 0,5000);
                break;
        }
    }
    //Añade las fichas en un arreglo de fichas :v
    public void addFichas() {
        fichas.add(o);
        fichas.add(iF);
        fichas.add(s);
        fichas.add(z);
        fichas.add(l);
        fichas.add(jF);
        fichas.add(t);
    }
    //Escoge una ficha al azar del arreglo
    public void escogerFicha() {
        in1 = r1.nextInt(7);
        fichaSig = fichas.get(in1);
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
    //Este limpia la linea como diomedes :v
    public void limpiarLinea(int linea ,int[][]matriz){
        for(int i = linea; i<linea+1;i++){
            for(int j = 0; j<matriz[i].length; j++){
                matriz[i][j] = 0;
            }
            lineasLlenas[i] = 0;
        }
        player[3] = String.valueOf(Integer.parseInt(player[3])+100);
    }
    //Iguala los puntos del jugador :v
    public void sumarPuntos(String[] jugador){
        jugador[3] = player[3];
    }

    //Esto pues guarda los puntajes en el arreglo jugador
    public void ganar(String[] jugador){
        jugador[7] = String.valueOf(Integer.parseInt(player[3]));
        jugador[3] = String.valueOf(0);
        jugador[4] = String.valueOf(Integer.parseInt(player[4])+1) ;
        jugador[5] = String.valueOf(Integer.parseInt(player[5])+1);
        jugador[8] = String.valueOf(Integer.parseInt(player[8])+1);
        
    }
    //Lo mismo que el de arriba pero este le resta por perdedor
    public void perder(String[] jugador){
        jugador[7] = String.valueOf(Integer.parseInt(player[3]));
        jugador[3] = String.valueOf(0);
        jugador[4] = String.valueOf(Integer.parseInt(player[4])+1);
        jugador[6] = String.valueOf(Integer.parseInt(player[6])+1);
        
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
    
    public Logica(String [] jugador) {
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
        fichas = new ArrayList();
        r1 = new Random();
        //Matriz llena de 0
        iniciarMatriz(matriz);
        iniciarMatriz(matrizSig);
        //crea fichas
        o = new O();
        iF = new I();
        s = new S();
        z = new Z();
        l = new L();
        jF = new J();
        t = new T();
        //Añande las fichas a un array para seleccionar una
        addFichas();
        escogerFicha();
        
        
        timer = new Timer();
        timer2 = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                /* Si el estado es 1 selecciona una ficha y la agrega a la matriz, luego la empieza a mover hacia
                * abajo, marca la ficha siguiente, y la ubica en la matriz 2, luego setea el estado a 2 */
                if(estado == 1 ){
                    fichaActual = fichaSig ;
                    fichaActual.ubicarFicha(matriz);
                    fichaActual.moverFichaAba(matriz);
                    escogerFicha();
                    iniciarMatriz(matrizSig);
                    fichaSig.ubicarFicha(matrizSig);
                    fichaSig.moverFichaAba(matrizSig);
                    estado = 2;
                }else{
                    /* En este estado se encarga de bajar la ficha sin agregar mas fichas mientras baja,
                    * tambien se evalua cuando la ficha detiene movimiento si gana o no y
                    *  dependiendo de esto se guarda la victoria o perdida*/
                    if(estado == 2){
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
                        if(player[3].equals(puntajeMax)){
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

}
