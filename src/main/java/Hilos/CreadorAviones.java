package Hilos;

import Aeropuerto.Aeropuerto;
import Aeropuerto.Aerovia;
import Main.Pantalla;
import Main.Paso;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreadorAviones extends Thread{
    
    private Aeropuerto aerM;
    private Aeropuerto aerB;
    private Paso paso;
    private Pantalla pantalla;
    private Random r = new Random();
    private Aerovia AreoviaMB;
    private Aerovia AreoviaBM;
    
    public CreadorAviones(Aeropuerto aerM, Aeropuerto aerB, Paso paso, Pantalla pantalla, Aerovia AreoviaMB, Aerovia AreoviaBM) {
        this.aerB = aerB;
        this.aerM = aerM;
        this.paso = paso;
        this.AreoviaMB = AreoviaMB;
        this.AreoviaBM = AreoviaBM;
    }

    @Override
    public void run() {
        Random r = new Random();

        for (int i = 1; i <= 8000; i++) {
            char letra1 = (char) ('A' + r.nextInt(26));
            char letra2 = (char) ('A' + r.nextInt(26));
    
            String id = String.format("%c%c-%04d", letra1, letra2, i);
    
            // Usar el ID para crear y empezar un avión
            if (i % 2 == 0) {
                Avion a = new Avion(id, aerM, aerB, AreoviaMB, AreoviaBM);
                a.start();
            } else {
                Avion a = new Avion(id, aerB, aerM, AreoviaMB, AreoviaBM);
                a.start();
            }
            try {
                Thread.sleep(1000 + r.nextInt(2001));
            } catch (InterruptedException ex) {
                
            }
        }
    }
}

