package Hilos;

import Aeropuerto.Aeropuerto;
import Aeropuerto.Aerovia;
import Aeropuerto.PuertaEmbarque;
import Main.Escritor;
import Main.Paso;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Avion extends Thread {
    private final Escritor escritor;
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
    private int nVuelos;
    private final Paso paso; 
    
    public Avion(String id, Aeropuerto aerOrigen, Aeropuerto aerDestino, Aerovia AreoviaMB, Aerovia AreoviaBM, Escritor escritor, Paso paso) {
        this.paso = paso;
        this.escritor = escritor;
        this.id = id;
        this.aerOrigen = aerOrigen;
        this.aerDestino = aerDestino;
        this.AreoviaMB = AreoviaMB;
        this.AreoviaBM = AreoviaBM;
        this.TipoOperacion = "embarque";  //cuando se genera el avíon comienza el ciclo de cero
        this.pasajeros = 0;
        this.capacidadMax = r.nextInt(201) + 100;
        this.nVuelos = 0;
        
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

    public int getnVuelos() {
        return nVuelos;
    }

    public void setnVuelos(int nVuelos) {
        this.nVuelos = nVuelos;
    }
    
    @Override
    public void run() {
        try{
            //se genera el avion en el hangar
            aerOrigen.pasarHangar(this);
            while (true) {
                paso.mirar();
                this.TipoOperacion = "embarque";
                
                //el avion pasa a el area de estacionamiento
                aerOrigen.pasarAreaE(this);
                paso.mirar();
                
                //el avion trata de acceder a la puerta de embarque y embarcar o desembarcar pasajeros
                nPuerta = aerOrigen.accederPuertaEmbarque(this);
                paso.mirar();
                
                //el avion sale de la puerta de embarque y accede al area de rodaje
                aerOrigen.pasarAreaR(this);
                paso.mirar();
                
                //el piloto hace las comprobaciones
                escritor.escribir("El piloto hace las primeras comprobaciones del avion " + this.id);
                Thread.sleep(1000 + r.nextInt(4001));
                paso.mirar();

                //se solicita acceso a pista y entra
                nPista = aerOrigen.accederPista(this);
                paso.mirar();
                
                //despegue
                Thread.sleep(1000 + r.nextInt(4001));
                
                //Entrar aerovia
                AeroviaActual = aerOrigen.accederAerovia(this);
                paso.mirar();

                //Cambiar la operacion del avion
                this.TipoOperacion = "desembarque";
                paso.mirar();
                //VUELO
                escritor.escribir("Avion " + this.id + "(" + this.pasajeros + ") " + " volando en " + AeroviaActual.getNombre());
                paso.mirar();
                Thread.sleep(15000 + r.nextInt(30001));


                // Solicita la pista y aterriza
                nPista = aerDestino.accederPista(this);
                paso.mirar();
                Thread.sleep(1000 + r.nextInt(4001));
                paso.mirar();
                escritor.escribir("Avion " + this.id + "(" + this.pasajeros + ") " + " ha aterrizado en la pista " + nPista); 


                // Pasa al area de rodaje
                aerDestino.pasarAreaR(this);
                paso.mirar();
                // Accede a la puerta de embarque y desembarca pasageros
                Thread.sleep(r.nextInt(3000 + 2001));

                nPuerta = aerDestino.accederPuertaEmbarque(this);
                
                // Accede al area de estacionamiento
                aerDestino.pasarAreaE(this);
                escritor.escribir("El piloto hace las comprobaciones a la llegada del avion " + this.id + "(" + this.pasajeros + ") ");
                paso.mirar();
                Thread.sleep(1000 + r.nextInt(4001));
                paso.mirar();
                
                // va al taller
                if(nVuelos >= 15){
                    aerDestino.pasarTaller(this, "profunda");
                }
                else{
                    aerDestino.pasarTaller(this, "rapida");
                }
                paso.mirar();
                //decide si ir reposar o continua
                int decision = r.nextInt(2) + 1;
                if(decision==1){
                    aerDestino.pasarHangar(this);
                    paso.mirar();
                    Thread.sleep(r.nextInt(15001) + 15000);
                    paso.mirar();
                    escritor.escribir("Avion " + this.id + " esta descansando en el hangar.");
                }
                // se repite el ciclo
                Aeropuerto aux = aerOrigen;
                aerOrigen = aerDestino;
                aerDestino = aux;
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
