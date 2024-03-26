package Hilos;

import Aeropuerto.Aeropuerto;
import java.util.Random;
import Main.Paso;

public class Autobus extends Thread {
    private String id;
    private Aeropuerto aeropuerto;
    private Paso paso;
    private int pasajeros;
    private Random r;

    public Autobus(String id, Aeropuerto aeropuerto, Paso paso) {
        this.id = id;
        this.aeropuerto = aeropuerto;
        this.paso = paso;
    }

    @Override
    public void run() {
        while (true) {
            try {
                paso.mirar();
                
                //Llegada a la parada de la ciudad
                Thread.sleep(r.nextInt(3001) + 2000);
                //suben pasajeros
                pasajeros = r.nextInt(51);
                //viaje al aeropuerto
                Thread.sleep(r.nextInt(5001) + 5000);
                //dejar pasajeros en el aeropuerto
                aeropuerto.entrarpasajeros(pasajeros);
                pasajeros = 0;
                //espera a que suban pasajeros en el aeropuerto
                Thread.sleep(r.nextInt(3001) + 2000);
                //suben pasajeros en el aeropuerto
                pasajeros = r.nextInt(51);
                aeropuerto.salirpasajeros(pasajeros);
                //viaje a la ciudad
                Thread.sleep(r.nextInt(5001) + 5000);
                pasajeros = 0;
                
                
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
