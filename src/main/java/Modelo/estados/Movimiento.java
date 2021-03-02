package Modelo.estados;

import JuegoTetris.Fichas;

public class Movimiento extends Estado {
    private Fichas ficha;
    private int posicion = 0;

    public Movimiento(ContextoDeEstados contexto) {
        super(contexto);
        this.ficha = ficha;
    }

    @Override
    public void moviendose() {
        System.out.println("Ficha moviendose...");
        posicion++;
    }

    @Override
    public void quieta() {
        super.contexto.cambiarEstado(new Reposo(super.contexto));
    }
}
