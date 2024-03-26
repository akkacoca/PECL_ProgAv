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
            //se genera el avion en el hangar
            aeropuerto.pasarHangar(this);
            //el avion pasa a el area de estacionamiento
            aeropuerto.pasarArea(this);
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
}
