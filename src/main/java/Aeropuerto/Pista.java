package Aeropuerto;

import Hilos.Avion;
import Main.Paso;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pista {
    private int numero;
    private boolean ocupada;
    private Queue<Avion> colaEspera; // Cola para acceder a la pista
    private Lock lock; // Cerrojo para sincronización
    private Condition condition; // Condición para señalar cuando la pista está disponible
    private Aeropuerto aeropuerto;
    private final Paso paso;
    
    public Pista(int numero, Aeropuerto aeropuerto, Paso paso) {
        this.paso = paso;
        this.numero = numero;
        this.ocupada = false;
        this.colaEspera = new ConcurrentLinkedQueue<>(); // Cola para seguridad en hilos
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition(); // Condición asociada al bloqueo
        this.aeropuerto = aeropuerto;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    
    // Método para que un avión solicite acceso a la pista
    public Avion solicitarAcceso(Avion avion) throws InterruptedException {
        lock.lock(); // Adquirir el bloqueo
        try {
            colaEspera.add(avion); // Agregar el avión a la cola
            // Esperar hasta que la pista esté disponible o el avión sea el siguiente en la cola
            while (ocupada || colaEspera.peek() != avion) {
                condition.await(); // Esperar hasta ser señalado
            }
            ocupada = true; // Marcar la pista como ocupada
            avion = colaEspera.remove(); // Eliminar el avión de la cola
            
        } finally {
            lock.unlock(); // Liberar el bloqueo
        }
        return avion;
    }

    // Método para liberar el acceso a la pista
    public void liberarAcceso() {
        lock.lock(); // Adquirir el bloqueo
        try {
            ocupada = false; // Marcar la pista como disponible
            condition.signalAll(); // Señalar a los hilos en espera que la pista está libre
        } finally {
            lock.unlock();
        }
    }
    
    
    
    public Avion solicitarAccesoAterrizar(Avion avion) throws InterruptedException {
        lock.lock();
        try {
            colaEspera.add(avion);
            while (ocupada || colaEspera.peek() != avion) {
                condition.await();
            }
            ocupada = true;
            avion = colaEspera.remove();
            
        } finally {
            lock.unlock();
        }
        return avion;
    }
}
