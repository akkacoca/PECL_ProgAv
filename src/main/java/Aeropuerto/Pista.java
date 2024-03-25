package Aeropuerto;

public class Pista {
    private int numero;
    private boolean ocupada;

    public Pista(int numero) {
        this.numero = numero;
        this.ocupada = false;
    }

    public int getNumero() {
        return numero;
    }

    public synchronized void asignar() {
        ocupada = true;
    }

    public synchronized void liberar() {
        ocupada = false;
    }
}
