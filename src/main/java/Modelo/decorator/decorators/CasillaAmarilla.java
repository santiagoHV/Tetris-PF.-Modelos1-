package Modelo.decorator.decorators;

import Modelo.decorator.Casilla;

import javax.swing.*;
import java.awt.*;

public class CasillaAmarilla extends CasillaDecorator {

    public CasillaAmarilla(Casilla casilla) {
        super(casilla);
    }

    @Override
    public JLabel cambiarColor(Color color) {
        return this.casilla.cambiarColor(Color.YELLOW);
    }
}
