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
    private Lock lockPuertas;
    private Lock lockPistas;
    private Random r = new Random();
    private Semaphore semaforoPasajeros;
    private Pantalla pantalla;
    private Aerovia AreoviaMB;
    private Aerovia AreoviaBM;
    private final Escritor escritor;

    public Aeropuerto(String nombre, Pantalla pantalla, Aerovia AreoviaMB, Aerovia AreoviaBM, Escritor escritor, Paso paso) {
        this.escritor = escritor;
        this.nombre = nombre;
        this.hangar = new Hangar(escritor, paso);
        this.taller = new Taller(this, paso);
        this.pasajeros = 0;
        this.pantalla = pantalla;
        this.AreoviaMB = AreoviaMB;
        this.AreoviaBM = AreoviaBM;
        this.semaforoPasajeros = new Semaphore(1);
        this.puertasEmbarque = new ArrayList<>();
        // Inicializar puertas de embarque
        for (int i = 1; i < 5; i++) {
            puertasEmbarque.add(new PuertaEmbarque(i, "general", this, paso));
        }
        puertasEmbarque.add(new PuertaEmbarque(5, "embarque", this, paso));
        puertasEmbarque.add(new PuertaEmbarque(6, "desembarque", this, paso));

        this.pistas = new ArrayList<>();
        // Inicializar pistas
        for (int i = 1; i < 5; i++) {
            pistas.add(new Pista(i, this, paso));
        }
        this.areaEstacionamiento = new AreaEstacionamiento(escritor, paso);
        this.areaRodaje = new AreaRodaje(escritor, paso);
        
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
    
    public void pasarAreaR(Avion avion){
        if(avion.getTipoOperacion().equals("embarque")){
            liberarPuertaEmbarque(avion.getPuerta());
        }
        else{
            liberarPista(avion.getPista());  
        }
        this.areaRodaje.entrar(avion);
        
        if (this.nombre == "Madrid"){
            this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
        }
        else {this.pantalla.getRodajeTextFieldB().setText(ArrayListToString(this.areaRodaje.getAviones()));}
    }
    
    public int accederPuertaEmbarque(Avion avion) {
        while(true){
            for (PuertaEmbarque puerta : puertasEmbarque) {
                if (puerta.isOcupada()){}
                else{
                    if ((puerta.getTipo().equals("general") || puerta.getTipo().equals(avion.getTipoOperacion())) && (!puerta.isOcupada() || puerta.getTipo().equals(avion.getTipoOperacion()))) {
                        try {
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
                            if (avion.TipoOperacion.equals("embarque")) {
                                int n = puerta.embarcarPasajeros(avion);
                                escritor.escribir("El avion " + avion.getID() + " embarca " + n + " pasajeros.");
                            } else {
                                int n = puerta.desembarcarPasajeros(avion);
                                escritor.escribir("El avion " + avion.getID() + " desembarca " + n + " pasajeros.");
                            }
                            //this.liberarPuertaEmbarque(puerta.getNumero());
                            return puerta.getNumero();

                        } catch (InterruptedException e) {
                            // Manejar la interrupción si es necesario
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void liberarPuertaEmbarque(int numeroPuerta) {
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
                PuertaEmbarque puerta = puertasEmbarque.get(numeroPuerta - 1);
                puerta.liberarAcceso();
                escritor.escribir("Puerta de embarque " + numeroPuerta + " del aeropuerto de " + this.nombre + " liberada."); 
    }
    
    public int accederPista(Avion avion) throws InterruptedException {
        while(true){
            for (Pista pista : pistas) {
                if (pista.isOcupada()){}
                else{
                    try {
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
                        
                        escritor.escribir("Avion " + avion.getID() + " accede a la pista " + pista.getNumero());

                        switch (pista.getNumero()){
                            case 1: if (this.nombre == "Madrid"){
                                        this.pantalla.getPista1TextFieldM().setText(avion.getID());
                                    }else {this.pantalla.getPista1TextFieldB().setText(avion.getID());}
                                    break;
                            case 2: if (this.nombre == "Madrid"){
                                        this.pantalla.getPista2TextFieldM().setText(avion.getID());
                                    }else {this.pantalla.getPista2TextFieldB().setText(avion.getID());}
                                    break;
                            case 3: if (this.nombre == "Madrid"){
                                        this.pantalla.getPista3TextFieldM().setText(avion.getID());
                                    }else {this.pantalla.getPista3TextFieldB().setText(avion.getID());}
                                    break;
                            case 4: if (this.nombre == "Madrid"){
                                        this.pantalla.getPista4TextFieldM().setText(avion.getID());
                                    }else {this.pantalla.getPista4TextFieldB().setText(avion.getID());}
                        }
                        if (avion.getTipoOperacion().equals("embarque")){
                            escritor.escribir("El piloto hace las últimas comprobaciones");
                            Thread.sleep(1000+ r.nextInt(2001));
                        }

                        return pista.getNumero();
                    } catch (InterruptedException e) {
                        // Manejar la interrupción si es necesario
                        e.printStackTrace();
                    }
                }     
            }
            if(avion.getTipoOperacion().equals("desembarque")){
                escritor.escribir("El avion " + avion.getID() + " no ha podido conseguir pista, da un rodeo.");
                Thread.sleep(1000+ r.nextInt(4001));
            }
        }
    }

    public void liberarPista(int numeroPista) {
        
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
        
        Pista pista = pistas.get(numeroPista - 1);
        pista.liberarAcceso();
        escritor.escribir("Pista " + numeroPista + " liberada.");
    }
    
    public Aerovia accederAerovia(Avion avion){
        
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
        
        
        
        if (tipoRevision.equals("profunda")){Thread.sleep(r.nextInt(5001) + 5000);
        escritor.escribir("Avion " + avion.getID() + " esta haciendo la revision en el taller.");}
        else if (tipoRevision.equals("rapida")){Thread.sleep(r.nextInt(4001) + 1000);
            escritor.escribir("Avion " + avion.getID() + " esta haciendo la revision en el taller.");}
        
        this.taller.salir(avion);
        this.taller.liberarAcceso();
    }
}

