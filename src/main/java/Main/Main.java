package Main;

import Aeropuerto.Aeropuerto;
import Hilos.Autobus;
import Hilos.Avion;
import Hilos.CreadorAviones;
import Hilos.CreadorBuses;
import java.util.Random;

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        Paso paso = new Paso();
        Random r = new Random();
        
        Pantalla pantalla = new Pantalla(paso);
        pantalla.setVisible(true);
        
        Aeropuerto aerM = new Aeropuerto("Madrid", pantalla);
        Aeropuerto aerB = new Aeropuerto("Barcelona", pantalla);
        
        CreadorBuses B = new CreadorBuses(aerM, aerB, paso, pantalla);
        B.start();
        
        CreadorAviones A = new CreadorAviones(aerM, aerB, paso, pantalla);
        A.start();
    }
}
