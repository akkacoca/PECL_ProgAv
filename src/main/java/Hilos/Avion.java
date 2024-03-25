package Hilos;

import Aeropuerto.Aeropuerto;
import java.util.Random;

public class Avion extends Thread {
    private String id;
    private Aeropuerto aeropuerto;

    public Avion(String id, Aeropuerto aeropuerto) {
        this.id = id;
        this.aeropuerto = aeropuerto;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(randomInterval(1000, 3000)); // Intervalo de creación escalonada
                despegar();
                Thread.sleep(randomInterval(15000, 30000)); // Duración del vuelo
                aterrizar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void despegar() {
        System.out.println("Avion " + id + " despegando.");
        // Lógica para el despegue del avión
    }

    private void aterrizar() {
        System.out.println("Avion " + id + " aterrizando.");
        // Lógica para el aterrizaje del avión
    }

    private int randomInterval(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
}
