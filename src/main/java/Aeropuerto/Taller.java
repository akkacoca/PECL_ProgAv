package Aeropuerto;

import Hilos.Avion;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Taller {
    private Queue<Avion> colaEspera;
    private ArrayList<String> aviones;
    private ArrayList<Boolean> ocupacion;
    private Lock lock;
    private Condition condition;
    private Aeropuerto aeropuerto;
    private Semaphore semaforoPuerta;
    private Random r = new Random();

    public Taller(Aeropuerto aeropuerto) {
        this.aviones = new ArrayList<>();
        this.colaEspera = new ConcurrentLinkedQueue<>();
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.aeropuerto = aeropuerto;
        semaforoPuerta = new Semaphore(1);
        this.aviones = new ArrayList<>();
        this.ocupacion = new ArrayList<>();
        for(int i=0; i<=20; i++){
            ocupacion.add(false);
        }
    }

    public ArrayList<String> getAviones() {
        return aviones;
    }
    
    
    
    public void entrar(Avion avion) throws InterruptedException {
        semaforoPuerta.acquire();
        try{
            aviones.add(avion.getID());
            Thread.sleep(1000);
        }
        finally{
            semaforoPuerta.release();
        }
    }
    
    public void salir(Avion avion) throws InterruptedException {
        semaforoPuerta.acquire();
        try{
            aviones.remove(avion.getID());
            Thread.sleep(1000);
        }
        finally{
            semaforoPuerta.release();
        }
    }
    
    public void solicitarAcceso(Avion avion) throws InterruptedException{
        lock.lock();
        try {
            colaEspera.offer(avion);
            while (!ocupacion.contains(false) || colaEspera.peek() != avion) {
                condition.await();
            }
            ocupacion.set(ocupacion.indexOf(false), true);
            avion = colaEspera.poll();
        } finally {
            lock.unlock();
        }
    }
    public void liberarAcceso() {
        lock.lock();
        try {
            ocupacion.set(ocupacion.indexOf(true), false);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
