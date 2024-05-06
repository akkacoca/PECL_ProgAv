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
        
        // Creación de un escritor para registrar eventos
        Escritor escritor = new Escritor();
        // Creación de un paso para controlar la sincronización entre hilos
        Paso paso = new Paso();
        Random r = new Random();
        
        // Creación de la interfaz gráfica de la pantalla
        Pantalla pantalla = new Pantalla(escritor, paso);
        pantalla.setVisible(true);
        
        // Inicializar aerovías
        Aerovia AreoviaMB = new Aerovia("Madrid-Barcelona", escritor, paso);
        Aerovia AreoviaBM = new Aerovia("Barcelona-Madrid", escritor, paso);
        
        // Creación de los aeropuertos
        Aeropuerto aerM = new Aeropuerto("Madrid", pantalla, AreoviaMB, AreoviaBM, escritor, paso);
        Aeropuerto aerB = new Aeropuerto("Barcelona", pantalla, AreoviaMB, AreoviaBM, escritor, paso);
        
        // Inicialización del creador de autobuses para los aeropuertos
        CreadorBuses B = new CreadorBuses(aerM, aerB, paso, pantalla, escritor);
        B.start();
        
        // Inicialización del creador de aviones para los aeropuertos
        CreadorAviones A = new CreadorAviones(aerM, aerB, paso, pantalla, AreoviaMB, AreoviaBM, escritor);
        A.start();
        
        // Inicialización del servidor para comunicarse con los clientes
        Servidor servidor = new Servidor(aerM, aerB);
        servidor.start();
    }
}
