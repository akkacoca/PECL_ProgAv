package Aeropuerto;

import Hilos.Avion;
import Main.Paso;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Taller {
    private Queue<Avion> colaEspera; // Cola de espera para entrar al taller
    private ArrayList<String> aviones;
    private ArrayList<Boolean> ocupacion; // Lista de ocupación de las posiciones de trabajo en el taller
    private Lock lock; // Cerrojo para sincronización
    private Condition condition; // Condición de espera para la cola de espera
    private Aeropuerto aeropuerto;
    private Semaphore semaforoPuerta; // Semáforo para controlar el acceso a la puerta del taller
    private Random r = new Random();
    private final Paso paso;
    
    public Taller(Aeropuerto aeropuerto, Paso paso) {
        this.paso = paso;
        this.aviones = new ArrayList<>();
        this.colaEspera = new ConcurrentLinkedQueue<>();
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.aeropuerto = aeropuerto;
        // Inicializa el semáforo para controlar el acceso a la puerta del taller
        semaforoPuerta = new Semaphore(1); 
        this.aviones = new ArrayList<>();
        // Inicializa la lista de ocupación de las posiciones de trabajo
        this.ocupacion = new ArrayList<>();
        for(int i=0; i<=20; i++){
            ocupacion.add(false);
        }
    }

    public ArrayList<String> getAviones() {
        return aviones;
    }
    
    // Método para que un avión entre al taller
    public void entrar(Avion avion) throws InterruptedException {
        semaforoPuerta.acquire(); // Adquiere el semáforo para controlar el acceso a la puerta
        try{
            paso.mirar();
            aviones.add(avion.getID()); // Agrega el avión a la lista dentro del taller
            Thread.sleep(1000); // Tiempo que el avión permanece en el taller
        }
        finally{
            semaforoPuerta.release(); // Libera el semáforo después de que el avión sale del taller
        }
    }
    
    // Método para que un avión salga del taller
    public void salir(Avion avion) throws InterruptedException {
        semaforoPuerta.acquire(); // Adquiere el semáforo para controlar el acceso a la puerta
        try{
            paso.mirar();
            aviones.remove(avion.getID()); // Elimina el avión de la lista dentro del taller
            Thread.sleep(1000);
        }
        finally{
            semaforoPuerta.release(); // Libera el semáforo después de que el avión sale del taller
        }
    }
    
    // Método para que un avión solicite acceso al taller
    public void solicitarAcceso(Avion avion) throws InterruptedException{
        lock.lock(); // Adquiere el cerrojo para sincronización
        try {
            paso.mirar();
            colaEspera.offer(avion); // Agrega el avión a la cola de espera
            while (!ocupacion.contains(false) || colaEspera.peek() != avion) {
                paso.mirar();
                condition.await(); // Espera hasta que haya una posición de trabajo disponible
            }
            // Marca la primera posición de trabajo disponible como ocupada
            ocupacion.set(ocupacion.indexOf(false), true);
            avion = colaEspera.poll(); // Obtiene el avión de la cola de espera
        } finally {
            lock.unlock(); // Libera el cerrojo
        }
    }
    
    // Método para liberar una posición de trabajo en el taller
    public void liberarAcceso() throws InterruptedException {
        lock.lock(); // Adquiere el cerrojo para sincronización
        try {
            paso.mirar();
            // Marca la primera posición de trabajo ocupada como desocupada
            ocupacion.set(ocupacion.indexOf(true), false);
            condition.signalAll(); // Despierta a todos los hilos en espera
        } finally {
            lock.unlock(); // Libera el cerrojo
        }
    }
}
