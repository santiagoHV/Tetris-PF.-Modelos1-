package Modelo.decorator;

import Modelo.decorator.Casilla;

import javax.swing.*;
import java.awt.*;

public class CasillaConcreta implements Casilla {

    private JLabel panel;

    public CasillaConcreta(JLabel panel){
        this.panel = panel;
    }

    @Override
    public JLabel cambiarColor(Color color) {
        panel.setForeground(color);
        panel.setBackground(color);
        return panel;
    }
}
