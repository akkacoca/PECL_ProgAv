package Aeropuerto;

import Hilos.Avion;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PuertaEmbarque {
    private int numero;
    private boolean ocupada;
    private Queue<Avion> colaEspera;
    private String tipo; // Puede ser "general", "embarque" o "desembarque"
    private Lock lock;
    private Condition condition;
    private Aeropuerto aeropuerto;

    public PuertaEmbarque(int numero, String tipo, Aeropuerto aeropuerto) {
        this.numero = numero;
        this.ocupada = false;
        this.colaEspera = new ArrayDeque<>();
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

    public void solicitarAcceso(Avion avion) throws InterruptedException {
        lock.lock();
        try {
            colaEspera.add(avion);
            while (ocupada || colaEspera.peek() != avion) {
                condition.await();
            }
            ocupada = true;
            colaEspera.remove(avion);
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

    public void embarcarPasajeros(Avion avion) throws InterruptedException {
        Random random = new Random();
        int intentos = 0;
        while (avion.getPasajeros() < avion.getCapacidadMax() && intentos < 3) {
            // Transferencia de pasajeros al avión
            int pasajerosTransferidos = Math.min(avion.getCapacidadMax() - avion.getPasajeros(), aeropuerto.getPasajeros());
            avion.embarcarPasajeros(pasajerosTransferidos);
            aeropuerto.salirpasajeros(pasajerosTransferidos);
            // Simulamos el tiempo de embarque
            Thread.sleep(random.nextInt(3000) + 1000);
            // Simulamos la espera para embarcar más pasajeros
            Thread.sleep(random.nextInt(5000) + 1000);
            intentos++;
        }
    }

    public void desembarcarPasajeros(Avion avion) throws InterruptedException {
        Random random = new Random();
        // Simulamos la transferencia de pasajeros al aeropuerto
        int pasajerosTransferidos = avion.getPasajeros();
        aeropuerto.entrarpasajeros(pasajerosTransferidos);
        Thread.sleep(random.nextInt(5000) + 1000); // Simulamos el tiempo de desembarque
    }
}
