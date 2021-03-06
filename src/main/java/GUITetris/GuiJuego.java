/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUITetris;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import JuegoTetris.Logica;
import Modelo.decorator.Casilla;
import Modelo.decorator.CasillaConcreta;
import Modelo.decorator.decorators.*;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author jrqui
 */
public class GuiJuego extends JFrame implements ActionListener, KeyListener{

    private Logica game;
    private String[] player;
    Font fuente = new Font("Castellar", 3 , 14);
    private JLabel[][] matrizLabel = new JLabel[20][10]; // Matriz de labels del juego
    private JLabel[][] matrizLabel2 = new JLabel[6][6]; // Matriz donde se muestra la ficha siguiente
    
    
    private JPanel matrizPan;
    private JPanel matrizPan2;
    
    private JLabel nombre1;
    private JLabel puntaje1;
    private JLabel juegosJu1;
    private JLabel juegosPer1;
    private JLabel juegosGa1;
    private JLabel ultimoPuntaje1;
    private JLabel nivel1;
    
    private JLabel nombre;
    private JLabel puntaje;
    private JLabel juegosJu;
    private JLabel juegosPer;
    private JLabel juegosGa;
    private JLabel ultimoPuntaje;
    private JLabel nivel;
    
    private JButton jugar;
    private JButton pausa;
    
    
    private Timer timer;
    private TimerTask task;
    
    private int estado;
    private int tamX;
    private int tamX2 = 30;
    private int tamY;
    private int tamY2 = 30;
    private int posInX = 0;
    private int posInX2 = 0;
    private int posInY = 0;
    private int posInY2 = 0;
    Border raisedbevel = BorderFactory.createBevelBorder(1, Color.DARK_GRAY , Color.BLACK);

    /* Esta mierda le da tamaño a cada cuadrito del tetris :v */
    public void tamañoLabel(int cantX, int cantY){
        tamX = 300/cantX;
        tamY = 600/cantY;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    /* Este metodo se encarga de llenar cada label con un '1' en ambas matrices
     * ademas las colorea pa que se vean lindas como su mami xd */

    public void crearMatriz(){
        for(int i = 0; i<20;i++){
            for(int j = 0; j<10; j++){
                JLabel lblNuevo = new JLabel("1");
                matrizLabel[i][j] = lblNuevo;
                matrizLabel[i][j].setFocusable(true);
                matrizPan.add(matrizLabel[i][j]);
                matrizLabel[i][j].setBackground(Color.BLACK);
                matrizLabel[i][j].setForeground(Color.WHITE);
                matrizLabel[i][j].setOpaque(true);
                matrizLabel[i][j].setBounds(posInX , posInY , tamX , tamY );
                matrizLabel[i][j].setBorder(raisedbevel);
                posInX += tamX;
            }
            posInX = 0;
            posInY+=tamY;
        }
        for(int i = 0; i<6;i++){
            for(int j = 0; j<6;j++){
                JLabel lblNew = new JLabel("1");
                matrizLabel2[i][j] = lblNew;
                matrizLabel2[i][j].setFocusable(true);
                matrizPan2.add(matrizLabel2[i][j]);
                matrizLabel2[i][j].setBackground(Color.BLACK);
                matrizLabel2[i][j].setForeground(Color.WHITE);
                matrizLabel2[i][j].setOpaque(true);
                matrizLabel2[i][j].setBounds(posInX2 ,posInY2, tamX2, tamY2);
                matrizLabel2[i][j].setBorder(raisedbevel);
                posInX2 += tamX2;
            }
            posInX2 = 0;
            posInY2+= tamY2;
        }
        
    }

    /* Ok, aqui es donde le decia que tengo dos matrices una logica
     * y una grafica entonces serian 4 matrices en total :v
     * pues esto las iguala pa que siempre esten coordinadas.*/

    public void igualarMatrices(){
        for(int i = 0; i<20;i++){
            for(int j = 0; j<10; j++){
                matrizLabel[i][j].setText(String.valueOf(game.matriz[i][j]));  
            }
        }
        for(int i = 0; i<6;i++){
            for(int j = 0; j<6; j++){
                matrizLabel2[i][j].setText(String.valueOf(game.matrizSig[i][j]));  
            }
        }
       
    }

    /**Las matrices estan llenas de numeros, por lo cual cada ficha tiene un numero asociado,
     * luego cada que la matriz se actualiza toca pintarla pa que no se vean los numeros
     * y para que le cambie el color a la casilla de negro a x color */

    public void pintarMatriz(JLabel[][] matriz){
        for(int i = 0; i<matriz.length;i++){
            for(int j = 0; j<matriz[i].length; j++){

                Casilla casilla = new CasillaConcreta(matriz[i][j]);

                casilla.cambiarColor(Color.BLACK);

                if(matriz[i][j].getText().equals("1") || matriz[i][j].getText().equals("11")){

                    CasillaDecorator casillaDecorator = new CasillaAmarilla(casilla);
                    casillaDecorator.cambiarColor(Color.BLACK);

                }
                if(matriz[i][j].getText().equals("2") || matriz[i][j].getText().equals("12")){
                    CasillaDecorator casillaDecorator = new CasillaCyan(casilla);
                    casillaDecorator.cambiarColor(Color.BLACK);
                }
                if(matriz[i][j].getText().equals("3") || matriz[i][j].getText().equals("13")){
                        CasillaDecorator casillaDecorator = new CasillaRoja(casilla);
                    casillaDecorator.cambiarColor(Color.BLACK);
                }
                if(matriz[i][j].getText().equals("4") || matriz[i][j].getText().equals("14")){
                    CasillaDecorator casillaDecorator = new CasillaVerde(casilla);
                    casillaDecorator.cambiarColor(Color.BLACK);
                }
                if(matriz[i][j].getText().equals("5") || matriz[i][j].getText().equals("15")){
                    CasillaDecorator casillaDecorator = new CasillaNaranja(casilla);
                    casillaDecorator.cambiarColor(Color.BLACK);
                }
                if(matriz[i][j].getText().equals("6") || matriz[i][j].getText().equals("16")){
                    CasillaDecorator casillaDecorator = new CasillaRosada(casilla);
                    casillaDecorator.cambiarColor(Color.BLACK);
                }
                if(matriz[i][j].getText().equals("7") || matriz[i][j].getText().equals("17")){
                    CasillaDecorator casillaDecorator = new CasillaMagenta(casilla);
                    casillaDecorator.cambiarColor(Color.BLACK);
                }
            }
        }
    }

    /*Este metodo evalua el estado del juego de un jugador especifico y guarda resultados */
    public void mostrarResultado( Logica juego, String[] jugador) throws IOException{
        if(juego.getEstado()==3){
            JOptionPane.showMessageDialog(null, "Has Perdido" , "Perdedor" , JOptionPane.ERROR_MESSAGE);
            juego.setEstado(0);
            this.dispose();
            GuiJuego gui = new GuiJuego(jugador);
        }else{
            if(juego.getEstado()==4){
                JOptionPane.showMessageDialog(null, "Has Ganado" , "Ganador" , JOptionPane.INFORMATION_MESSAGE);
                juego.setEstado(0);
                this.dispose();
                GuiJuego gui = new GuiJuego(jugador);
            }
        }
    }

    //Actualiza los datos en la pantalla para saber el puntaje y demas.
    public void actualizarDatos(String[]jugador){
        puntaje.setText(jugador[1]);
        juegosJu.setText(jugador[2]);
        juegosPer.setText(jugador[4]);
        juegosGa.setText(jugador[3]);
        ultimoPuntaje.setText(jugador[5]);
        nivel.setText(jugador[6]);
    }
    //Contructor -------------------------------------------------------------------------------------------------------
    public GuiJuego(String[] jugador){
        player = jugador;
        game = new Logica(player);
        matrizPan = new JPanel();
        matrizPan2 = new JPanel();
        estado = 0;

        jugar = new JButton("Jugar");
        jugar.addActionListener(this);
        jugar.setActionCommand("jugar");
        jugar.setFocusable(false);
        jugar.setFont(fuente);
        jugar.setForeground(Color.WHITE);
        jugar.setBackground(Color.BLACK);

        pausa = new JButton("Pausa");
        pausa.addActionListener(this);
        pausa.setEnabled(false);
        pausa.setActionCommand("pausa");
        pausa.setFocusable(false);
        pausa.setFont(fuente);
        pausa.setForeground(Color.WHITE);
        pausa.setBackground(Color.BLACK);

        nombre1 = new JLabel("Nombre :");
        nombre1.setFont(fuente);
        nombre1.setForeground(Color.CYAN);
        puntaje1 = new JLabel("Puntaje:");
        puntaje1.setFont(fuente);
        puntaje1.setForeground(Color.blue);
        juegosJu1 = new JLabel("Juegos jugados:");
        juegosJu1.setFont(fuente);
        juegosJu1.setForeground(Color.MAGENTA);
        juegosPer1 = new JLabel("Juegos perdidos:");
        juegosPer1.setFont(fuente);
        juegosPer1.setForeground(Color.RED);
        juegosGa1 = new JLabel("Juegos ganados:");
        juegosGa1.setFont(fuente);
        juegosGa1.setForeground(Color.GREEN);
        ultimoPuntaje1 = new JLabel("Ultimo puntaje:");
        ultimoPuntaje1.setFont(fuente);
        ultimoPuntaje1.setForeground(Color.YELLOW);
        nivel1= new JLabel("Nivel:");
        nivel1.setFont(fuente);
        nivel1.setForeground(Color.BLACK);


        nombre = new JLabel(player[0]);
        nombre.setFont(fuente);
        nombre.setForeground(Color.WHITE);
        puntaje = new JLabel(player[1]);
        puntaje.setFont(fuente);
        puntaje.setForeground(Color.WHITE);
        juegosJu = new JLabel(player[2]);
        juegosJu.setFont(fuente);
        juegosJu.setForeground(Color.WHITE);
        juegosGa = new JLabel(player[3]);
        juegosGa.setFont(fuente);
        juegosGa.setForeground(Color.WHITE);
        juegosPer = new JLabel(player[4]);
        juegosPer.setFont(fuente);
        juegosPer.setForeground(Color.WHITE);
        ultimoPuntaje = new JLabel(player[5]);
        ultimoPuntaje.setFont(fuente);
        ultimoPuntaje.setForeground(Color.WHITE);
        nivel = new JLabel("1");
        nivel.setFont(fuente);
        nivel.setForeground(Color.WHITE);



        tamañoLabel(10,20);

        this.getContentPane().setLayout(null);
        this.setBounds( 50 , 50 , 900 , 700 );
        this.setFocusable(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.addKeyListener(this);

        this.getContentPane().add(nombre1);
        nombre1.setBounds( 10 , 10 , 200 , 60 );
        this.getContentPane().add(puntaje1);
        puntaje1.setBounds( 10 , 80 , 200 , 60 );
        this.getContentPane().add(juegosJu1);
        juegosJu1.setBounds( 10 , 150 , 200 , 60 );
        this.getContentPane().add(juegosPer1);
        juegosPer1.setBounds( 10 , 220 , 200 , 60 );
        this.getContentPane().add(juegosGa1);
        juegosGa1.setBounds( 10 , 290 , 200 , 60 );
        this.getContentPane().add(ultimoPuntaje1);
        ultimoPuntaje1.setBounds( 10 , 360 , 200, 60 );
        this.getContentPane().add(nivel1);
        nivel1.setBounds( 10 , 430 , 120 , 60 );

        this.getContentPane().add(nombre);
        nombre.setBounds( 10 , 30 , 200 , 60 );
        this.getContentPane().add(puntaje);
        puntaje.setBounds( 10 , 100 , 100 , 60 );
        this.getContentPane().add(juegosJu);
        juegosJu.setBounds( 10 , 170 , 100 , 60 );
        this.getContentPane().add(juegosPer);
        juegosPer.setBounds( 10 , 240 , 100 , 60 );
        this.getContentPane().add(juegosGa);
        juegosGa.setBounds( 10 , 310 , 100 , 60 );
        this.getContentPane().add(ultimoPuntaje);
        ultimoPuntaje.setBounds( 10 , 380 , 100, 60 );
        this.getContentPane().add(nivel);
        nivel.setBounds( 10 , 450 , 120 , 60 );





        this.getContentPane().add(matrizPan);
        matrizPan.setLayout(null);
        matrizPan.setBorder(raisedbevel);
        matrizPan.setBounds( 240 , 30 , 300 , 600 );
        matrizPan.setVisible(true);

        this.getContentPane().add(matrizPan2);
        matrizPan2.setLayout(null);
        matrizPan2.setBorder(raisedbevel);
        matrizPan2.setBounds(590 , 30 , 180 , 180);
        matrizPan2.setVisible(true);

        this.getContentPane().add(jugar);
        jugar.setBounds( 600 , 450 , 110 , 50 );
        this.getContentPane().add(pausa);
        pausa.setBounds( 600 , 550 , 110 , 50 );




        crearMatriz();
        igualarMatrices();
        pintarMatriz(matrizLabel);
        pintarMatriz(matrizLabel2);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            /* Aqui es donde se evalua el estado de la interfaz para saber si se tiene que actualizar la matriz
            *  actualiza datos de jugador dependiendo del puntaje o muestra resultado segun sea el caso :v,
            *  se ejecuta bastante creo, asi que consume memoria, deberiamos optimizar eso :v */

            public void run() {
                if(estado == 1 ){
                    igualarMatrices();
                    pintarMatriz(matrizLabel);
                    pintarMatriz(matrizLabel2);
                    if(game.getEstado2()== 1){
                        player = jugador;
                        actualizarDatos(player);
                    }
                    try {
                        mostrarResultado(game,player);
                    } catch (IOException ex) {
                        Logger.getLogger(GuiJuego.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        timer.schedule(task, 0, 70);
        this.setTitle("TETRIS");
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Botones

        if(e.getActionCommand().equals("jugar")){
            jugar.setEnabled(false);
            pausa.setEnabled(true);
            estado = 1;
            game.setEstado(1);
            game.setEstado2(1);
            game.niveles(player);
        }
        if(e.getActionCommand().equals("pausa")){
            this.setEnabled(false);
            estado = 2;
            GuiPausa pausa = new GuiPausa(player,this, game);
            game.setEstado(5);
        }
        if(e.getActionCommand().equals("salir")){
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {

        //Manejo de las fichas con las flechas y la barra espaciadora :v

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(!game.fichaActual.bloqueo){
                if(game.fichaActual.ubicacion){
                    game.fichaActual.moverFichaIzq(game.matriz);
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(!game.fichaActual.bloqueo){
                if(game.fichaActual.ubicacion){
                    game.fichaActual.moverFichaDer(game.matriz);
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(game.fichaActual.ubicacion){
                game.fichaActual.girarFicha(game.matriz);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(!game.fichaActual.bloqueo){
                if(game.fichaActual.ubicacion){
                    if(game.fichaActual.analizarBordeAbajo(game.matriz)){
                        game.fichaActual.moverFichaAba(game.matriz);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
