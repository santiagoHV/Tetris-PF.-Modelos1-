package Modelo.decorator.decorators;

import Modelo.decorator.Casilla;

import javax.swing.*;
import java.awt.*;

public class CasillaCyan extends CasillaDecorator {

    public CasillaCyan(Casilla casilla) {
        super(casilla);
    }

    @Override
    public JLabel cambiarColor(Color color) {
        return super.casilla.cambiarColor(Color.CYAN);
    }
}
