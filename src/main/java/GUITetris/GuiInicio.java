/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUITetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author Estudiantes
 */
public class GuiInicio extends JFrame implements ActionListener {

    private JTextField usuarioTxt;
    Font fuente = new Font("Castellar", 3 , 14);
    
    private JLabel usuarioLabel;
    private JLabel lImg;
    private Image img;
    private ImageIcon img2;
    
    private JButton iniciar;

    public GuiInicio(){

        // Creacion de botones -----------------------------------------------------------------------------------------
        iniciar = new JButton(" Iniciar ");
        iniciar.setBackground(Color.BLUE);
        iniciar.setFont(fuente);
        iniciar.setForeground(Color.WHITE);
        iniciar.setFocusable(false);
        iniciar.addActionListener(this);//Escuchar al boton
        iniciar.setActionCommand("iniciar");// Comando del boton

        //Creacion campos de texto -------------------------------------------------------------------------------------
        usuarioTxt = new JTextField();

        //Creacion etiquetas de texto ----------------------------------------------------------------------------------
        usuarioLabel = new JLabel("Ingresa tu nombre:");
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setFont(fuente);



        //Creacion img
        img = new ImageIcon("assets/tetris.png").getImage();
        lImg = new JLabel();
        lImg.setIcon(new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

        this.getContentPane().setLayout(null);
        this.setBounds( 400 , 170 , 620 , 400 );
        this.getContentPane().setBackground(Color.BLACK);
        
        this.getContentPane().add(iniciar);
        iniciar.setBounds( 250 , 280 , 120 , 50 );
        this.getContentPane().add(usuarioTxt);
        usuarioTxt.setFont(fuente);
        usuarioTxt.setBounds(180 , 210 , 260 , 30);
        this.getContentPane().add(usuarioLabel);
        usuarioLabel.setBounds( 210 , 170 , 250 , 30 );

        this.getContentPane().add(lImg);
        lImg.setBounds(230,20,150,150);
        lImg.setOpaque(false);

        this.setTitle("Inicio");
        this.setResizable(true);      
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("iniciar")){
            String[] player = new String[7];
            for(int i=0;i<player.length;i++){
                player[i] = "0";
            }
            player[0] = usuarioTxt.getText();
            player[6] = "1";
            GuiJuego game = new GuiJuego(player);
            this.dispose();
        }
    }
}
