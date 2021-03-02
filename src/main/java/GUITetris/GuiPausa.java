/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUITetris;

import JuegoTetris.Logica;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author jrqui
 */
public class GuiPausa extends JFrame implements ActionListener {

    Font fuente = new Font("Castellar", 3 , 14);
    private GuiJuego game;
    private String[] player;
    private Logica logica;
    private JButton reanudar;
    private JButton salir;

    //  Constructor
    public GuiPausa(String[]jugador, GuiJuego juego, Logica log){
        game = juego;
        player = jugador;
        logica = log;
        reanudar = new JButton("Reanudar");
        reanudar.addActionListener(this);
        reanudar.setActionCommand("reanudar");
        reanudar.setFocusable(false);
        reanudar.setFont(fuente);
        reanudar.setForeground(Color.WHITE);
        reanudar.setBackground(Color.BLUE);

        salir = new JButton("Salir");
        salir.addActionListener(this);
        salir.setActionCommand("salir");
        salir.setFocusable(false);
        salir.setFont(fuente);
        salir.setForeground(Color.WHITE);
        salir.setBackground(Color.RED);
        
        this.getContentPane().setLayout(null);
        this.setBounds( 400 , 170 , 250 , 250 );
        this.getContentPane().setBackground(Color.BLACK);
        
        this.getContentPane().add(reanudar);
        reanudar.setBounds(45 , 50 , 140 , 40);
        this.getContentPane().add(salir);
        salir.setBounds(45 , 100 , 140 , 40);
        
        this.setTitle("Inicio");
        this.setResizable(true);      
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Botones y mas botones :v
        if(e.getActionCommand().equals("reanudar")){
            game.setEnabled(true);
            game.setEstado(1);
            logica.setEstado(2);
            this.dispose();
        }
        if(e.getActionCommand().equals("salir")){
            game.dispose();
            logica.perder(player);
            GuiInicio inicio = new GuiInicio();
            this.dispose();
        }
    }
    
}
