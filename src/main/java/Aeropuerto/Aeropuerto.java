package Aeropuerto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

import Hilos.Avion;
import java.util.Random;
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
    private Random r = new Random();

    public Aeropuerto(String nombre) {
        this.nombre = nombre;
        this.hangar = new Hangar();
        this.taller = new Taller();
        this.puertasEmbarque = new ArrayList<>();
        // Inicializar puertas de embarque
        for (int i = 0; i < 4; i++) {
            puertasEmbarque.add(new PuertaEmbarque(i + 1, "general", this));
        }
        puertasEmbarque.add(new PuertaEmbarque(5, "embarque", this));
        puertasEmbarque.add(new PuertaEmbarque(6, "desembarque", this));

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

    public int getPasajeros() {
        return pasajeros;
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
    
    public void pasarAreaE(Avion avion){
        this.hangar.salir(avion);
        this.areaEstacionamiento.entrar(avion);
    }
    public void pasarAreaR(Avion avion){
        liberarPuertaEmbarque(avion.getPuerta());
        this.areaRodaje.entrar(avion);
    }
    
    public int accederPuertaEmbarque(Avion avion) {
        for (PuertaEmbarque puerta : puertasEmbarque) {
            if ((puerta.getTipo().equals("general") || puerta.getTipo().equals(avion.getTipoOperacion())) && (!puerta.isOcupada() || puerta.getTipo().equals(avion.getTipoOperacion()))) {
                try {
                    puerta.solicitarAcceso(avion);
                    System.out.println("Avion " + avion.getID() + " accede a la Puerta de Embarque " + puerta.getNumero());
                    this.areaEstacionamiento.salir(avion);
                    
                    if (avion.TipoOperacion.equals("embarque")) {
                        puerta.embarcarPasajeros(avion);
                    } else {
                        puerta.desembarcarPasajeros(avion);
                    }
                    
                    return puerta.getNumero();
                } catch (InterruptedException e) {
                    // Manejar la interrupción si es necesario
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public void liberarPuertaEmbarque(int numeroPuerta) {
        try {
            lockPuertas.lock();
            for (PuertaEmbarque puerta : puertasEmbarque) {
                if (puerta.getNumero() == numeroPuerta) {
                    puerta.liberarAcceso();
                    System.out.println("Puerta de embarque " + numeroPuerta + " liberada.");
                    break;
                }
            }
        } finally {
            lockPuertas.unlock();
        }
    }

    public int accederPista(Avion avion) {
        for (Pista pista : pistas) {
            try {
                pista.solicitarAcceso(avion);
                System.out.println("Avion " + avion.getID() + " accede a la pista " + pista.getNumero());
                this.areaRodaje.salir(avion);
                System.out.println("El piloto hace las últimas comprobaciones");
                Thread.sleep(1000+ r.nextInt(2001));
                
                return pista.getNumero();
            } catch (InterruptedException e) {
                // Manejar la interrupción si es necesario
                e.printStackTrace();
            }  
        }
        return -1;
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
