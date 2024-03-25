package Hilos;

public class Avion extends Thread{
    private String identificador;
    private int capacidadMaxima;
    private int vuelosRealizados;
    private boolean enTaller;
    
    public Avion(String identificador, int capacidadMaxima) {
        this.identificador = identificador;
        this.capacidadMaxima = capacidadMaxima;
        this.vuelosRealizados = 0;
        this.enTaller = false;
    }
    
     @Override
    public void run() {
        while (true) {
        }
    }
}
