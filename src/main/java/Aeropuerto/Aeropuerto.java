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
    private Semaphore semaforoPasajeros;
    private Pantalla pantalla;
    private Aerovia AreoviaMB;
    private Aerovia AreoviaBM;

    public Aeropuerto(String nombre, Pantalla pantalla, Aerovia AreoviaMB, Aerovia AreoviaBM) {
        this.nombre = nombre;
        this.hangar = new Hangar();
        this.taller = new Taller();
        this.pasajeros = 0;
        this.pantalla = pantalla;
        this.AreoviaMB = AreoviaMB;
        this.AreoviaBM = AreoviaBM;
        this.semaforoPasajeros = new Semaphore(1);
        this.puertasEmbarque = new ArrayList<>();
        // Inicializar puertas de embarque
        for (int i = 1; i < 5; i++) {
            puertasEmbarque.add(new PuertaEmbarque(i, "general", this));
        }
        puertasEmbarque.add(new PuertaEmbarque(5, "embarque", this));
        puertasEmbarque.add(new PuertaEmbarque(6, "desembarque", this));

        this.pistas = new ArrayList<>();
        // Inicializar pistas
        for (int i = 1; i < 5; i++) {
            pistas.add(new Pista(i, this));
        }
        this.areaEstacionamiento = new AreaEstacionamiento();
        this.areaRodaje = new AreaRodaje();
        
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
        if(avion.getTipoOperacion().equals("embarque")){
            liberarPuertaEmbarque(avion.getPuerta());
            if (this.nombre == "Madrid"){
                this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
            }
            else {this.pantalla.getRodajeTextFieldB().setText(ArrayListToString(this.areaRodaje.getAviones()));}
        }
        else{
            liberarPista(avion.getPista());
            if (this.nombre == "Madrid"){
                this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
            }
            else {this.pantalla.getRodajeTextFieldB().setText(ArrayListToString(this.areaRodaje.getAviones()));}
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
                            this.areaEstacionamiento.salir(avion);
                            if (this.nombre == "Madrid"){
                                this.pantalla.getEstacionamientoTextFieldM().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));
                            }   
                            else {this.pantalla.getEstacionamientoTextFieldB().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));}

                            System.out.println("Avion " + avion.getID() + " accede a la Puerta de Embarque " + puerta.getNumero());

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
                                puerta.embarcarPasajeros(avion);
                            } else {
                                puerta.desembarcarPasajeros(avion);
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
                System.out.println("Puerta de embarque " + numeroPuerta + " del aeropuerto de " + this.nombre + " liberada."); 
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
                        
                        System.out.println("Avion " + avion.getID() + " accede a la pista " + pista.getNumero());

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

                        System.out.println("El piloto hace las últimas comprobaciones");
                        Thread.sleep(1000+ r.nextInt(2001));

                        return pista.getNumero();
                    } catch (InterruptedException e) {
                        // Manejar la interrupción si es necesario
                        e.printStackTrace();
                    }
                }     
            }
            if(avion.getTipoOperacion().equals("desembarque")){
                System.out.println("El avion " + avion.getID() + " no ha podido conseguir pista, da un rodeo.");
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
        System.out.println("Pista " + numeroPista + " liberada.");
    }
    
    public Aerovia accederAerovia(Avion avion){
        
        liberarPista(avion.getPista());
        
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
    
    public void pasarAreaRAterrizar(Avion avion){
        liberarPista(avion.getPista());
        this.areaRodaje.entrar(avion);
        
        if (this.nombre == "Madrid"){
            this.pantalla.getRodajeTextFieldM().setText(ArrayListToString(this.areaRodaje.getAviones()));
        }
        else {this.pantalla.getRodajeTextFieldB().setText(ArrayListToString(this.areaRodaje.getAviones()));}
    }
    
    public int accederPuertaEmbarqueAterrizar(Avion avion) {
        while(true){
            for (PuertaEmbarque puerta : puertasEmbarque) {
                if (puerta.isOcupada()){}
                else{
                    if ((puerta.getTipo().equals("general") || puerta.getTipo().equals(avion.getTipoOperacion())) && (!puerta.isOcupada() || puerta.getTipo().equals(avion.getTipoOperacion()))) {
                        try {

                            puerta.solicitarAccesoAterrizar(avion);
                            this.areaRodaje.salir(avion);
                            if (this.nombre == "Madrid"){
                                this.pantalla.getEstacionamientoTextFieldM().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));
                            }   
                            else {this.pantalla.getEstacionamientoTextFieldB().setText(ArrayListToString(this.areaEstacionamiento.getAviones()));}

                            System.out.println("Avion " + avion.getID() + " accede a la Puerta de Embarque " + puerta.getNumero());

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
                                puerta.embarcarPasajeros(avion);
                            } else {
                                puerta.desembarcarPasajeros(avion);
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

}

