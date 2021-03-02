package Modelo.estados;

public class ContextoDeEstados {
    private Estado estado;
    private boolean moviendose = false;

    public ContextoDeEstados(){
        this.estado = new Reposo(this);
    }

    public void cambiarEstado(Estado estado){
        this.estado = estado;
        moviendose = !moviendose;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public boolean isMoviendose() {
        return moviendose;
    }

    public void setMoviendose(boolean moviendose) {
        this.moviendose = moviendose;
    }
}
