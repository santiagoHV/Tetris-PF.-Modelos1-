/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUITetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 *
 * @author Estudiantes
 */
public class GuiInicio extends JFrame implements ActionListener {

    private JTextField usuarioTxt;
    private JPasswordField passTxt;
    Font fuente = new Font("Castellar", 3 , 14);
    
    
    private JLabel usuarioLabel;
    private JLabel passLabel;
    
    private JButton iniciar;
    private JButton registro;
    
  
    
    public GuiInicio(){

        // Creacion de botones -----------------------------------------------------------------------------------------
        iniciar = new JButton(" Iniciar ");
        iniciar.setBackground(Color.BLACK);
        iniciar.setFont(fuente);
        iniciar.setForeground(Color.WHITE);
        iniciar.setFocusable(false);
        iniciar.addActionListener(this);//Escuchar al boton
        iniciar.setActionCommand("iniciar");// Comando del boton

        //Creacion campos de texto -------------------------------------------------------------------------------------
        usuarioTxt = new JTextField();

        //Creacion etiquetas de texto ----------------------------------------------------------------------------------
        usuarioLabel = new JLabel("Usuario");
        usuarioLabel.setForeground(Color.BLACK);
        usuarioLabel.setFont(fuente); 

        this.getContentPane().setLayout(null);
        this.setBounds( 400 , 170 , 620 , 400 );
        this.getContentPane().setBackground(Color.DARK_GRAY);
        
        this.getContentPane().add(iniciar);
        iniciar.setBounds( 180 , 280 , 120 , 50 );
        this.getContentPane().add(usuarioTxt);
        usuarioTxt.setFont(fuente);
        usuarioTxt.setBounds(180 , 170 , 260 , 30);
        this.getContentPane().add(usuarioLabel);
        usuarioLabel.setBounds( 100 , 170 , 90 , 30 );

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
