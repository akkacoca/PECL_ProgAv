package Hilos;

import Aeropuerto.Aeropuerto;
import Aeropuerto.Aerovia;
import Aeropuerto.PuertaEmbarque;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Avion extends Thread {
    private String id;
    private Aeropuerto aerOrigen;
    private Aeropuerto aerDestino;
    public String TipoOperacion;  // embarque/desembarque
    private int pasajeros;
    private int capacidadMax;
    private Random r = new Random();
    private int nPuerta = -1;
    private int nPista = -1;
    private Aerovia AreoviaMB;
    private Aerovia AreoviaBM;
    private Aerovia AeroviaActual;

    public Avion(String id, Aeropuerto aerOrigen, Aeropuerto aerDestino, Aerovia AreoviaMB, Aerovia AreoviaBM) {
        this.id = id;
        this.aerOrigen = aerOrigen;
        this.aerDestino = aerDestino;
        this.AreoviaMB = AreoviaMB;
        this.AreoviaBM = AreoviaBM;
        this.TipoOperacion = "embarque";  //cuando se genera el avíon comienza el ciclo de cero
        this.pasajeros = 0;
        this.capacidadMax = r.nextInt(201) + 100;
        
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public int getPasajeros() {
        return pasajeros;
    }

    public String getID() {
        return id;
    }
    public int getPuerta() {
        return nPuerta;
    }

    public int getPista() {
        return nPista;
    }

    public String getTipoOperacion() {
        return TipoOperacion;
    }

    public Aeropuerto getAerOrigen() {
        return aerOrigen;
    }

    public Aeropuerto getAerDestino() {
        return aerDestino;
    }

    public Aerovia getAeroviaActual() {
        return AeroviaActual;
    }
    
    @Override
    public void run() {
        try{
            while (true) {
                this.TipoOperacion = "embarque";
                //se genera el avion en el hangar
                aerOrigen.pasarHangar(this);
                
                //el avion pasa a el area de estacionamiento
                aerOrigen.pasarAreaE(this);
                
                //el avion trata de acceder a la puerta de embarque y embarcar o desembarcar pasajeros
                nPuerta = aerOrigen.accederPuertaEmbarque(this);
                
                //el avion sale de la puerta de embarque y accede al area de rodaje
                aerOrigen.pasarAreaR(this);
                
                //el piloto hace las comprobaciones
                System.out.println("El piloto hace las primeras comprobaciones del avion " + this.id);
                Thread.sleep(1000 + r.nextInt(4001));

                //se solicita acceso a pista y entra
                nPista = aerOrigen.accederPista(this);
                
                //despegue
                Thread.sleep(1000 + r.nextInt(4001));
                
                //Entrar aerovia
                AeroviaActual = aerOrigen.accederAerovia(this);

                //Cambiar la operacion del avion
                this.TipoOperacion = "desembarque";

                //VUELO
                System.out.println("Avion " + this.id + " volando en " + AeroviaActual.getNombre());
                Thread.sleep(15000 + r.nextInt(30001));

                // Solicita la pista y aterriza
                nPista = aerDestino.accederPista(this);
                Thread.sleep(1000 + r.nextInt(4001));
                System.out.println("Avion " + this.id + " ha aterrizado en la pista " + nPista);                

                // Pasa al area de rodaje
                aerDestino.pasarAreaR(this);
                
                //nPuerta = aerDestino.accederPuertaEmbarque(this);
                
                System.out.println("AVION " + this.id + " FINALIZA.");
                break;
            }
        }
        catch (InterruptedException e){}
    }
    
    public void embarcarPasajeros(int cantidad) {
        pasajeros += cantidad;
    }

    public void desembarcarPasajeros(int cantidad) {
        pasajeros -= cantidad;
    }
}
