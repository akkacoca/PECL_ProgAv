package Aeropuerto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import Main.Escritor;
import Main.Paso;
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
    private Aerovia AreoviaMB;
    private Aerovia AreoviaBM;
    private Pantalla pantalla;
    private final Escritor escritor;
    private Paso paso;
    private Lock lockPuertas;
    private Lock lockPistas;
    private Semaphore semaforoPasajeros;
    private Random r = new Random();
   
    public Aeropuerto(String nombre, Pantalla pantalla, Aerovia AreoviaMB, Aerovia AreoviaBM, Escritor escritor, Paso paso) {
        
        this.escritor = escritor;
        this.pantalla = pantalla;
        this.paso = paso;
        
        // Inicializar locks para puertas y pistas
        this.lockPuertas = new ReentrantLock();
        this.lockPistas = new ReentrantLock();
        this.semaforoPasajeros = new Semaphore(1);
        
        this.nombre = nombre;
        this.pasajeros = 0;
        
        this.hangar = new Hangar(escritor, paso);
        this.taller = new Taller(this, paso);
        this.areaEstacionamiento = new AreaEstacionamiento(escritor, paso);
        
        // Inicializar puertas de embarque
        this.puertasEmbarque = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            puertasEmbarque.add(new PuertaEmbarque(i, "general", this, paso));
        }
        puertasEmbarque.add(new PuertaEmbarque(5, "embarque", this, paso));
        puertasEmbarque.add(new PuertaEmbarque(6, "desembarque", this, paso));
        
        this.areaRodaje = new AreaRodaje(escritor, paso);
        
        // Inicializar pistas
        this.pistas = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            pistas.add(new Pista(i, this, paso));
        }
        this.AreoviaMB = AreoviaMB;
        this.AreoviaBM = AreoviaBM;
    }
    
    // Getters
    public int getPasajeros() {return pasajeros;}
    public String getNombre() {return nombre;}
    public Hangar getHangar() {return hangar;}
    public Taller getTaller() {return taller;}
    public AreaEstacionamiento getAreaEstacionamiento() {return areaEstacionamiento;}
    public AreaRodaje getAreaRodaje() {return areaRodaje;}
    public String getAreoviaMB() {return ArrayListToString(AreoviaMB.getAviones());}
    public String getAreoviaBM() {return ArrayListToString(AreoviaBM.getAviones());}
    
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
            paso.mirar();
            // Modificamos la variable compartida de manera segura
            pasajeros += cantidad;
        } finally {
            // Liberamos el semáforo en el bloque finally para asegurarnos de que siempre se libere
            semaforoPasajeros.release();
        }
        // Actualizamos la pantalla
        if (this.nombre == "Madrid"){
            this.pantalla.getPasajerosTextFieldM().setText(Integer.toString(pasajeros));
        }
        else {this.pantalla.getPasajerosTextFieldB().setText(Integer.toString(pasajeros));}
    }
    
    public void disminuirPasajeros(int cantidad) throws InterruptedException {
        // Adquirimos el semáforo
        semaforoPasajeros.acquire();
        try {
            paso.mirar();
            // Modificamos la variable compartida de manera segura
            pasajeros -= cantidad;
        } finally {
            // Liberamos el semáforo en el bloque finally para asegurarnos de que siempre se libere
            semaforoPasajeros.release();
        }
        // Actualizamos pantalla
        if (this.nombre == "Madrid"){
            this.pantalla.getPasajerosTextFieldM().setText(Integer.toString(pasajeros));
        }
        else {this.pantalla.getPasajerosTextFieldB().setText(Integer.toString(pasajeros));}
    }
    
    public void pasarHangar(Avion avion) throws InterruptedException{
        paso.mirar();
        this.hangar.entrar(avion);
        
        if (this.nombre == "Madrid"){
            this.pantalla.getHangarTextFieldM().setText(ArrayListToString(this.hangar.getAviones()));
        }
        else {this.pantalla.getHangarTextFieldB().setText(ArrayListToString(this.hangar.getAviones()));}
    }
    
    public void pasarAreaE(Avion avion) throws InterruptedException{
        paso.mirar();
        this.areaEstacionamiento.entrar(avion);
        
        if (this.nombre == "Madrid"){
            this.pantalla.getEstacionamientoTextFieldM().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));
        }
        else {this.pantalla.getEstacionamientoTextFieldB().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));}
        
        if(avion.getTipoOperacion().equals("embarque")){
            this.hangar.salir(avion);
            if (this.nombre == "Madrid"){
                this.pantalla.getHangarTextFieldM().setText(ArrayListToString(this.hangar.getAviones()));
            }
            else {this.pantalla.getHangarTextFieldB().setText(ArrayListToString(this.hangar.getAviones()));}
        }
        else{liberarPuertaEmbarque(avion.getPuerta());}
        
        if (this.nombre == "Madrid"){
            this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
        }
        else {this.pantalla.getRodajeTextFieldB().setText(ArrayListToString(this.areaRodaje.getAviones()));}
    }
    
    public void pasarAreaR(Avion avion) throws InterruptedException{
        
        paso.mirar();
        
        if(avion.getTipoOperacion().equals("embarque")){
            liberarPuertaEmbarque(avion.getPuerta());
        }
        else{liberarPista(avion.getPista());}
        
        this.areaRodaje.entrar(avion);
        
        if (this.nombre == "Madrid"){
            this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
        }
        else {this.pantalla.getRodajeTextFieldB().setText(ArrayListToString(this.areaRodaje.getAviones()));}
    }
    
    public int accederPuertaEmbarque(Avion avion) throws InterruptedException {
        while(true){
            for (PuertaEmbarque puerta : puertasEmbarque) {
                paso.mirar();
                
                if (!puerta.isOcupada()){
                    if ((puerta.getTipo().equals("general") || puerta.getTipo().equals(avion.getTipoOperacion())) && (!puerta.isOcupada() || puerta.getTipo().equals(avion.getTipoOperacion()))) {
                        
                        try {
                            paso.mirar();
                            
                            puerta.solicitarAcceso(avion);
                            
                            if(avion.getTipoOperacion().equals("embarque")){
                                this.areaEstacionamiento.salir(avion);
                                if (this.nombre == "Madrid"){
                                    this.pantalla.getEstacionamientoTextFieldM().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));
                                }   
                                else {this.pantalla.getEstacionamientoTextFieldB().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));}
                            }
                            else{
                                this.areaRodaje.salir(avion);
                                if (this.nombre == "Madrid"){
                                    this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
                                }   
                                else {this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));}
                            }

                            escritor.escribir("Avion " + avion.getID() + " accede a la Puerta de Embarque " + puerta.getNumero());

                            switch (puerta.getNumero()){
                                case 1: if (this.nombre == "Madrid"){
                                            this.pantalla.getGate1TextFieldM().setText(avion.TipoOperacion + ": " + avion.getID());
                                        }else {this.pantalla.getGate1TextFieldB().setText(avion.TipoOperacion + ": " + avion.getID());}
                                        break;
                                case 2: if (this.nombre == "Madrid"){
                                            this.pantalla.getGate2TextFieldM().setText(avion.TipoOperacion + ": " + avion.getID());
                                        }else {this.pantalla.getGate2TextFieldB().setText(avion.TipoOperacion + ": " + avion.getID());}
                                        break;
                                case 3: if (this.nombre == "Madrid"){
                                            this.pantalla.getGate3TextFieldM().setText(avion.TipoOperacion + ": " + avion.getID());
                                        }else {this.pantalla.getGate3TextFieldB().setText(avion.TipoOperacion + ": " + avion.getID());}
                                        break;
                                case 4: if (this.nombre == "Madrid"){
                                            this.pantalla.getGate4TextFieldM().setText(avion.TipoOperacion + ": " + avion.getID());
                                        }else {this.pantalla.getGate4TextFieldB().setText(avion.TipoOperacion + ": " + avion.getID());}
                                        break;
                                case 5: if (this.nombre == "Madrid"){
                                            this.pantalla.getGate5TextFieldM().setText(avion.TipoOperacion + ": " + avion.getID());
                                        }else {this.pantalla.getGate5TextFieldB().setText(avion.TipoOperacion + ": " + avion.getID());}
                                        break;
                                case 6: if (this.nombre == "Madrid"){
                                            this.pantalla.getGate6TextFieldM().setText(avion.TipoOperacion + ": " + avion.getID());
                                        }else {this.pantalla.getGate6TextFieldB().setText(avion.TipoOperacion + ": " + avion.getID());}
                            }
                            paso.mirar();
                            
                            if (avion.TipoOperacion.equals("embarque")) {
                                int n = puerta.embarcarPasajeros(avion);
                                escritor.escribir("El avion " + avion.getID() + " embarca " + n + " pasajeros.");
                            } 
                            else {
                                int n = puerta.desembarcarPasajeros(avion);
                                escritor.escribir("El avion " + avion.getID() + " desembarca " + n + " pasajeros.");
                            }
                            
                            return puerta.getNumero();

                        } catch (InterruptedException e) {
                            escritor.escribir("ERROR");
                        }
                    }
                }
            }
        }
    }

    public void liberarPuertaEmbarque(int numeroPuerta) throws InterruptedException {
        paso.mirar();
        switch (numeroPuerta){
            case 1: if (this.nombre == "Madrid"){
                        this.pantalla.getGate1TextFieldM().setText("");
                    }else {this.pantalla.getGate1TextFieldB().setText("");}
                    break;
            case 2: if (this.nombre == "Madrid"){
                        this.pantalla.getGate2TextFieldM().setText("");
                    }else {this.pantalla.getGate2TextFieldB().setText("");}
                    break;
            case 3: if (this.nombre == "Madrid"){
                        this.pantalla.getGate3TextFieldM().setText("");
                    }else {this.pantalla.getGate3TextFieldB().setText("");}
                    break;
            case 4: if (this.nombre == "Madrid"){
                        this.pantalla.getGate4TextFieldM().setText("");
                    }else {this.pantalla.getGate4TextFieldB().setText("");}
                    break;
            case 5: if (this.nombre == "Madrid"){
                        this.pantalla.getGate5TextFieldM().setText("");
                    }else {this.pantalla.getGate5TextFieldB().setText("");}
                    break;
            case 6: if (this.nombre == "Madrid"){
                        this.pantalla.getGate6TextFieldM().setText("");
                    }else {this.pantalla.getGate6TextFieldB().setText("");}
        }
        paso.mirar();
        
        PuertaEmbarque puerta = puertasEmbarque.get(numeroPuerta - 1);
        puerta.liberarAcceso();
        
        escritor.escribir("Puerta de embarque " + numeroPuerta + " del aeropuerto de " + this.nombre + " liberada."); 
    }
    
    public int accederPista(Avion avion) throws InterruptedException {
        while(true){
            for (Pista pista : pistas) {
                
                paso.mirar();
                
                if (!pista.isOcupada()){
                    try {
                        paso.mirar();
                        pista.solicitarAcceso(avion);
                        
                        if(avion.getTipoOperacion().equals("embarque")){
                            this.areaRodaje.salir(avion);
                            if (this.nombre == "Madrid"){
                                this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
                            }
                            else {this.pantalla.getRodajeTextFieldB().setText(ArrayListToString(this.areaRodaje.getAviones()));}
                        }
                        else{
                            Aerovia aerovia = avion.getAeroviaActual();
                            avion.getAeroviaActual().salir(avion);
                            if (this.nombre == "Madrid"){
                                this.pantalla.getAeroviaMBTextField().setText(ArrayListToString(aerovia.getAviones()));
                            }
                            else {this.pantalla.getAeroviaBMTextField().setText(ArrayListToString(aerovia.getAviones()));}
                        }
                        
                        escritor.escribir("Avion " + avion.getID() + "(" + avion.getPasajeros() + ") " + " accede a la pista " + pista.getNumero());

                        switch (pista.getNumero()){
                            case 1: if (this.nombre == "Madrid"){
                                        this.pantalla.getPista1TextFieldM().setText(avion.getID() + "(" + avion.getPasajeros() + ")");
                                    }else {this.pantalla.getPista1TextFieldB().setText(avion.getID() + "(" + avion.getPasajeros() + ")");}
                                    break;
                            case 2: if (this.nombre == "Madrid"){
                                        this.pantalla.getPista2TextFieldM().setText(avion.getID() + "(" + avion.getPasajeros() + ")");
                                    }else {this.pantalla.getPista2TextFieldB().setText(avion.getID() + "(" + avion.getPasajeros() + ")");}
                                    break;
                            case 3: if (this.nombre == "Madrid"){
                                        this.pantalla.getPista3TextFieldM().setText(avion.getID() + "(" + avion.getPasajeros() + ")");
                                    }else {this.pantalla.getPista3TextFieldB().setText(avion.getID() + "(" + avion.getPasajeros() + ")");}
                                    break;
                            case 4: if (this.nombre == "Madrid"){
                                        this.pantalla.getPista4TextFieldM().setText(avion.getID() + "(" + avion.getPasajeros() + ")");
                                    }else {this.pantalla.getPista4TextFieldB().setText(avion.getID() + "(" + avion.getPasajeros() + ")");}
                        }
                        paso.mirar();
                        
                        if (avion.getTipoOperacion().equals("embarque")){
                            escritor.escribir("El piloto hace las últimas comprobaciones");
                            Thread.sleep(1000+ r.nextInt(2001));
                        }

                        return pista.getNumero();
                        
                    } catch (InterruptedException e) {
                        escritor.escribir("ERROR");
                    }
                }     
            }
            if(avion.getTipoOperacion().equals("desembarque")){
                escritor.escribir("El avion " + avion.getID() + "(" + avion.getPasajeros() + ") " + " no ha podido conseguir pista, da un rodeo.");
                Thread.sleep(1000+ r.nextInt(4001));
            }
        }
    }

    public void liberarPista(int numeroPista) throws InterruptedException {
        
        paso.mirar();
        
        switch (numeroPista){
            case 1: if (this.nombre == "Madrid"){
                        this.pantalla.getPista1TextFieldM().setText("");
                    }else {this.pantalla.getPista1TextFieldB().setText("");}
                    break;
            case 2: if (this.nombre == "Madrid"){
                        this.pantalla.getPista2TextFieldM().setText("");
                    }else {this.pantalla.getPista2TextFieldB().setText("");}
                    break;
            case 3: if (this.nombre == "Madrid"){
                        this.pantalla.getPista3TextFieldM().setText("");
                    }else {this.pantalla.getPista3TextFieldB().setText("");}
                    break;
            case 4: if (this.nombre == "Madrid"){
                        this.pantalla.getPista4TextFieldM().setText("");
                    }else {this.pantalla.getPista4TextFieldB().setText("");}
        }
        paso.mirar();
        
        Pista pista = pistas.get(numeroPista - 1);
        pista.liberarAcceso();
        
        escritor.escribir("Pista " + numeroPista + " liberada.");
    }
    
    public Aerovia accederAerovia(Avion avion) throws InterruptedException{
        
        paso.mirar();
        
        liberarPista(avion.getPista());
        
        avion.setnVuelos(avion.getnVuelos() + 1);
        
        if(this.nombre.equals("Madrid")){
            AreoviaMB.entrar(avion);
            this.pantalla.getAeroviaMBTextField().setText(ArrayListToString(AreoviaMB.getAviones()));
            return AreoviaMB;
        }
        else{
            AreoviaBM.entrar(avion);
            this.pantalla.getAeroviaBMTextField().setText(ArrayListToString(AreoviaBM.getAviones()));
            return AreoviaBM;
        }
       
    }
    
    public void pasarTaller(Avion avion, String tipoRevision) throws InterruptedException{
        
        paso.mirar();
        
        this.taller.solicitarAcceso(avion);
        this.taller.entrar(avion);
        
        this.areaRodaje.salir(avion);
        if (this.nombre == "Madrid"){
            this.pantalla.getEstacionamientoTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
        }
        else {this.pantalla.getEstacionamientoTextFieldB().setText(ArrayListToString(this.areaRodaje.getAviones()));}
        
        if (this.nombre == "Madrid"){
            this.pantalla.getTallerTextFieldM().setText(ArrayListToString(this.taller.getAviones()));
        }else {this.pantalla.getTallerTextFieldB().setText(ArrayListToString(this.taller.getAviones()));}
        
        paso.mirar();
        
        if (tipoRevision.equals("profunda")){
            Thread.sleep(r.nextInt(5001) + 5000);
            escritor.escribir("Avion " + avion.getID() + " esta haciendo la revision en profundidad en el taller.");
        }
        else if (tipoRevision.equals("rapida")){
            Thread.sleep(r.nextInt(4001) + 1000);
            escritor.escribir("Avion " + avion.getID() + " esta haciendo la revision rapida en el taller.");
        }
        paso.mirar();
        
        this.taller.salir(avion);
        this.taller.liberarAcceso();
    }
}

