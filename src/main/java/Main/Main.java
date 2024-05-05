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
        Escritor escritor = new Escritor();
        Paso paso = new Paso();
        Random r = new Random();
        
        Pantalla pantalla = new Pantalla(escritor, paso);
        pantalla.setVisible(true);
        
                // Inicializar aerovías
        Aerovia AreoviaMB = new Aerovia("Madrid-Barcelona", escritor, paso);
        Aerovia AreoviaBM = new Aerovia("Barcelona-Madrid", escritor, paso);
        
        Aeropuerto aerM = new Aeropuerto("Madrid", pantalla, AreoviaMB, AreoviaBM, escritor, paso);
        Aeropuerto aerB = new Aeropuerto("Barcelona", pantalla, AreoviaMB, AreoviaBM, escritor, paso);
        
        CreadorBuses B = new CreadorBuses(aerM, aerB, paso, pantalla, escritor);
        B.start();
        
        CreadorAviones A = new CreadorAviones(aerM, aerB, paso, pantalla, AreoviaMB, AreoviaBM, escritor);
        A.start();
        
        Servidor servidor = new Servidor(aerM, aerB);
        servidor.start();
    }
}
