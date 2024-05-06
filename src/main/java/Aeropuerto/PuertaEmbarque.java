package Aeropuerto;

import Hilos.Avion;
import Main.Paso;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PuertaEmbarque {
    private final Paso paso;
    private int numero;
    private boolean ocupada;
    private Queue<Avion> colaEspera; // Cola de espera para acceder a la puerta
    private String tipo; // Puede ser "general", "embarque" o "desembarque"
    private Lock lock; // Cerrojo para controlar el acceso concurrente a la puerta
    private Condition condition; // Condición para esperar hasta que la puerta esté disponible
    private Aeropuerto aeropuerto;

    public PuertaEmbarque(int numero, String tipo, Aeropuerto aeropuerto, Paso paso) {
        this.paso = paso;
        this.numero = numero;
        this.ocupada = false;
        this.colaEspera = new ConcurrentLinkedQueue<>();
        this.tipo = tipo;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.aeropuerto = aeropuerto;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    // Método para que un avión solicite acceso a la puerta
    public Avion solicitarAcceso(Avion avion) throws InterruptedException {
        lock.lock(); // Adquirir el bloqueo
        try {
            paso.mirar();
            colaEspera.offer(avion); // Agregar el avión a la cola de espera
            // Esperar hasta que la pista esté disponible o el avión sea el siguiente en la cola
            while (ocupada || colaEspera.peek() != avion) {
                paso.mirar();
                condition.await(); // Esperar hasta ser señalado
            }
            ocupada = true; // Marcar la pista como ocupada
            avion = colaEspera.poll(); // Eliminar el avión de la cola de espera
            
        } finally {
            lock.unlock(); // Liberar el bloqueo
        }
        return avion;
    }

    // Método para liberar el acceso a la puerta
    public void liberarAcceso() throws InterruptedException {
        lock.lock(); // Adquirir el bloqueo
        try {
            paso.mirar();
            ocupada = false; // Marcar la pista como disponible
            condition.signalAll(); // Señalar a los hilos en espera que la pista está libre
        } finally { 
            lock.unlock();
        }
    }

    // Método para embarcar pasajeros
    public int embarcarPasajeros(Avion avion) throws InterruptedException {
        paso.mirar();
        Random random = new Random();
        int pasajerosVuelo = 0;
        int intentos = 0;
        while (avion.getPasajeros() < avion.getCapacidadMax() && intentos < 3) {
            paso.mirar();
            
            // Transferencia de pasajeros al avión
            int pasajerosTransferidos = Math.min(avion.getCapacidadMax() - avion.getPasajeros(), aeropuerto.getPasajeros());
            pasajerosVuelo += pasajerosTransferidos;
            
            paso.mirar();
            avion.embarcarPasajeros(pasajerosTransferidos);
            aeropuerto.disminuirPasajeros(pasajerosTransferidos);
            
            // Simulamos el tiempo de embarque
            paso.mirar();
            Thread.sleep(random.nextInt(3000) + 1000);
            // Simulamos la espera para embarcar más pasajeros
            paso.mirar();
            Thread.sleep(random.nextInt(5000) + 1000);
            intentos++;
            paso.mirar();
        }
        return pasajerosVuelo;
    }

    // Método para desembarcar pasajeros
    public int desembarcarPasajeros(Avion avion) throws InterruptedException {
        paso.mirar();
        Random random = new Random();
        
        // Simulamos la transferencia de pasajeros al aeropuerto
        int pasajerosTransferidos = avion.getPasajeros();
        aeropuerto.aumentarPasajeros(pasajerosTransferidos);
        
        paso.mirar();
        // Simulamos el tiempo de desembarque
        Thread.sleep(random.nextInt(5000) + 1000); 
        paso.mirar();
        return pasajerosTransferidos;
    }
}
