package Modelo.estados;

public abstract class Estado {

    ContextoDeEstados contexto;

    public Estado(ContextoDeEstados contexto){
        this.contexto = contexto;
    }

    public abstract void moviendose();
    public abstract void quieta();
}
