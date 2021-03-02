package Modelo.decorator.decorators;

import Modelo.decorator.Casilla;

import javax.swing.*;

public abstract class CasillaDecorator implements Casilla {

    protected Casilla casilla;

    public CasillaDecorator(Casilla casilla){
        this.casilla = casilla;
    }

}
