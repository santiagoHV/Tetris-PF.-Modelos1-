package Modelo.factories;

import JuegoTetris.Fichas;

public interface Factory {

    int cicloDeTiempo = 0;

    public Fichas retornarFicha();
    public int retornarTiempo();
}
