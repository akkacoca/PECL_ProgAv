package Aeropuerto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

import Hilos.Avion;
import Main.Pantalla;
import java.util.Random;
import java.util.concurrent.Semaphore;
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
    private Aerovia aerovia;
    private Semaphore semaforoPasajeros;
    private Pantalla pantalla;

    public Aeropuerto(String nombre, Pantalla pantalla) {
        this.nombre = nombre;
        this.hangar = new Hangar();
        this.taller = new Taller();
        this.pasajeros = 0;
        this.pantalla = pantalla;
        this.semaforoPasajeros = new Semaphore(1);
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

    public String getNombre() {
        return nombre;
    }
    
    public String ArrayListToString(ArrayList<String> lista) {
        String cadena = "";
    
        for (String avion : lista) {
            cadena += avion + ", ";
        }
    
        return cadena;
    }

    
    
    public void aumentarPasajeros(int cantidad) throws InterruptedException {
        // Adquirimos el semáforo
        semaforoPasajeros.acquire();
        try {
            // Modificamos la variable compartida de manera segura
            pasajeros += cantidad;
        } finally {
            // Liberamos el semáforo en el bloque finally para asegurarnos de que siempre se libere
            semaforoPasajeros.release();
        }
        if (this.nombre == "Madrid"){
            this.pantalla.getPasajerosTextFieldM().setText(Integer.toString(pasajeros));
        }
        else {this.pantalla.getPasajerosTextFieldB().setText(Integer.toString(pasajeros));}
    }
    
    public void disminuirPasajeros(int cantidad) throws InterruptedException {
        // Adquirimos el semáforo
        semaforoPasajeros.acquire();
        try {
            // Modificamos la variable compartida de manera segura
            pasajeros -= cantidad;
        } finally {
            // Liberamos el semáforo en el bloque finally para asegurarnos de que siempre se libere
            semaforoPasajeros.release();
        }
        if (this.nombre == "Madrid"){
            this.pantalla.getPasajerosTextFieldM().setText(Integer.toString(pasajeros));
        }
        else {this.pantalla.getPasajerosTextFieldB().setText(Integer.toString(pasajeros));}
    }
    
    public void pasarHangar(Avion avion){
        this.hangar.entrar(avion);
        if (this.nombre == "Madrid"){
            this.pantalla.getHangarTextFieldM().setText(ArrayListToString(this.hangar.getAviones()));
        }
        else {this.pantalla.getHangarTextFieldB().setText(ArrayListToString(this.hangar.getAviones()));}
    }
    
    public void pasarAreaE(Avion avion){
        this.areaEstacionamiento.entrar(avion);
        if (this.nombre == "Madrid"){
            this.pantalla.getEstacionamientoTextFieldM().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));
        }
        else {this.pantalla.getEstacionamientoTextFieldB().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));}
        
        this.hangar.salir(avion);
        if (this.nombre == "Madrid"){
            this.pantalla.getHangarTextFieldM().setText(ArrayListToString(this.hangar.getAviones()));
        }
        else {this.pantalla.getHangarTextFieldB().setText(ArrayListToString(this.hangar.getAviones()));}
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
                    
                    switch (puerta.getNumero()){
                        case 1: if (this.nombre == "Madrid"){
                                    this.pantalla.getGate1TextFieldM().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));
                                }else {this.pantalla.getGate1TextFieldB().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));}
                        case 2: if (this.nombre == "Madrid"){
                                    this.pantalla.getGate2TextFieldM().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));
                                }else {this.pantalla.getGate2TextFieldB().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));}
                        case 3: if (this.nombre == "Madrid"){
                                    this.pantalla.getGate3TextFieldM().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));
                                }else {this.pantalla.getGate3TextFieldB().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));}
                        case 4: if (this.nombre == "Madrid"){
                                    this.pantalla.getGate4TextFieldM().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));
                                }else {this.pantalla.getGate4TextFieldB().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));}
                        case 5: if (this.nombre == "Madrid"){
                                    this.pantalla.getGate5TextFieldM().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));
                                }else {this.pantalla.getGate5TextFieldB().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));}
                        case 6: if (this.nombre == "Madrid"){
                                    this.pantalla.getGate6TextFieldM().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));
                                }else {this.pantalla.getGate6TextFieldB().setText(avion.TipoOperacion + ": " + ArrayListToString(this.areaEstacionamiento.getAviones()));}
}

                    
                    
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
                    pista.liberarAcceso();
                    System.out.println("Pista " + numeroPista + " liberada.");
                    break;
                }
            }
        } finally {
            lockPistas.unlock();
        }
    }
    
    public void accederAerovia(Avion avion){
        
            
    }
        
        
    
}
