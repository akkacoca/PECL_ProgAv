package Aeropuerto;

import Hilos.Avion;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pista {
    private int numero;
    private boolean ocupada;
    private Queue<Avion> colaEspera;
    private Lock lock;
    private Condition condition;
    private Aeropuerto aeropuerto;

    public Pista(int numero, Aeropuerto aeropuerto) {
        this.numero = numero;
        this.ocupada = false;
        this.colaEspera = new ConcurrentLinkedQueue<>();
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.aeropuerto = aeropuerto;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public Avion solicitarAcceso(Avion avion) throws InterruptedException {
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

    public void liberarAcceso() {
        lock.lock();
        try {
            ocupada = false;
            condition.signalAll();
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
