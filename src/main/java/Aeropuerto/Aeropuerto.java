package Aeropuerto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

import Hilos.Avion;
public class Aeropuerto {
    private String nombre;
    private int pasajeros;
    private Hangar hangar;
    private Taller taller;
    private ArrayList<PuertaEmbarque> puertasEmbarque;
    private ArrayList<Pista> pistas;
    private AreaEstacionamiento areaEstacionamiento;
    private AreaRodaje areaRodaje;
    private ArrayList<Aerovia> aerovias;
    private Lock lockPuertas;
    private Lock lockPistas;

    public Aeropuerto(String nombre) {
        this.nombre = nombre;
        this.hangar = new Hangar();
        this.taller = new Taller();
        this.puertasEmbarque = new ArrayList<>();
        // Inicializar puertas de embarque
        for (int i = 0; i < 6; i++) {
            puertasEmbarque.add(new PuertaEmbarque(i + 1));
        }
        this.pistas = new ArrayList<>();
        // Inicializar pistas
        for (int i = 0; i < 4; i++) {
            pistas.add(new Pista(i + 1));
        }
        this.areaEstacionamiento = new AreaEstacionamiento();
        this.areaRodaje = new AreaRodaje();
        this.aerovias = new ArrayList<>();
        // Inicializar aerovías
        aerovias.add(new Aerovia("Madrid-Barcelona"));
        aerovias.add(new Aerovia("Barcelona-Madrid"));
        // Inicializar locks para puertas y pistas
        this.lockPuertas = new ReentrantLock();
        this.lockPistas = new ReentrantLock();
    }
    
    public void entrarpasajeros(int pasajeros){
        this.pasajeros += pasajeros;
    }
    
    public void salirpasajeros(int pasajeros){
        this.pasajeros -= pasajeros;
    }
    
    public void pasarHangar(Avion avion){
        this.hangar.entrar(avion);
    }
    
    public void pasarArea(Avion avion){
        this.areaEstacionamiento.entrar(avion);
    }
    
    public void asignarPuertaEmbarque(int numeroPuerta) {
        try {
            lockPuertas.lock();
            for (PuertaEmbarque puerta : puertasEmbarque) {
                if (puerta.getNumero() == numeroPuerta) {
                    puerta.asignar();
                    System.out.println("Puerta de embarque " + numeroPuerta + " asignada.");
                    break;
                }
            }
        } finally {
            lockPuertas.unlock();
        }
    }

    public void liberarPuertaEmbarque(int numeroPuerta) {
        try {
            lockPuertas.lock();
            for (PuertaEmbarque puerta : puertasEmbarque) {
                if (puerta.getNumero() == numeroPuerta) {
                    puerta.liberar();
                    System.out.println("Puerta de embarque " + numeroPuerta + " liberada.");
                    break;
                }
            }
        } finally {
            lockPuertas.unlock();
        }
    }

    public void asignarPista(int numeroPista) {
        try {
            lockPistas.lock();
            for (Pista pista : pistas) {
                if (pista.getNumero() == numeroPista) {
                    pista.asignar();
                    System.out.println("Pista " + numeroPista + " asignada.");
                    break;
                }
            }
        } finally {
            lockPistas.unlock();
        }
    }

    public void liberarPista(int numeroPista) {
        try {
            lockPistas.lock();
            for (Pista pista : pistas) {
                if (pista.getNumero() == numeroPista) {
                    pista.liberar();
                    System.out.println("Pista " + numeroPista + " liberada.");
                    break;
                }
            }
        } finally {
            lockPistas.unlock();
        }
    }
}
