package Hilos;

import Aeropuerto.Aeropuerto;
import java.util.Random;

public class Autobus extends Thread {
    private String id;
    private Aeropuerto aeropuerto;

    public Autobus(String id, Aeropuerto aeropuerto) {
        this.id = id;
        this.aeropuerto = aeropuerto;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(randomInterval(500, 1000)); // Intervalo de creación escalonada
                llevarPasajeros();
                Thread.sleep(randomInterval(2000, 5000)); // Tiempo en espera antes de volver
                traerPasajeros();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void llevarPasajeros() {
        int numPasajeros = randomInterval(0, 50);
        System.out.println("Autobus " + id + " llega a la ciudad para llevar " + numPasajeros + " pasajeros.");
        // Lógica para llevar pasajeros al aeropuerto
    }

    private void traerPasajeros() {
        int numPasajeros = randomInterval(0, 50);
        System.out.println("Autobus " + id + " llega al aeropuerto para traer " + numPasajeros + " pasajeros.");
        // Lógica para traer pasajeros desde el aeropuerto
    }

    private int randomInterval(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
}
