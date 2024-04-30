package Main;

import Aeropuerto.Aeropuerto;
import Aeropuerto.Aerovia;
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
        
                // Inicializar aerovías
        Aerovia AreoviaMB = new Aerovia("Madrid-Barcelona");
        Aerovia AreoviaBM = new Aerovia("Barcelona-Madrid");
        
        Aeropuerto aerM = new Aeropuerto("Madrid", pantalla, AreoviaMB, AreoviaBM);
        Aeropuerto aerB = new Aeropuerto("Barcelona", pantalla, AreoviaMB, AreoviaBM);
        
        CreadorBuses B = new CreadorBuses(aerM, aerB, paso, pantalla);
        B.start();
        
        CreadorAviones A = new CreadorAviones(aerM, aerB, paso, pantalla, AreoviaMB, AreoviaBM);
        A.start();
    }
}
