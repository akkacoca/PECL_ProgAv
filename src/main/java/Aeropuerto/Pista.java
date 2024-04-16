package Aeropuerto;

import Hilos.Avion;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pista {
private int numero;
    private boolean ocupada;
    private Lock lock;
    private Condition condition;

    public Pista(int numero) {
        this.numero = numero;
        this.ocupada = false;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void solicitarAcceso(Avion avion) throws InterruptedException {
        lock.lock();
        try {
            while (ocupada) {
                condition.await();
            }
            ocupada = true;
        } finally {
            lock.unlock();
        }
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
}
