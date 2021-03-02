package Modelo.estados;

public class Reposo extends Estado {

    public Reposo(ContextoDeEstados contexto){
        super(contexto);
    }

    @Override
    public void moviendose() {
        super.contexto.cambiarEstado(new Movimiento(super.contexto));
    }

    @Override
    public void quieta() {
        System.out.println("Ficha quieta...");
    }
}
