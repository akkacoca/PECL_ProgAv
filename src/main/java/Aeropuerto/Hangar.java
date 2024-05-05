package Aeropuerto;

import Hilos.Avion;
import Main.Escritor;
import Main.Paso;
import java.util.ArrayList;

public class Hangar {
    private ArrayList<String> aviones;
    private final Escritor escritor;
    private final Paso paso;
    
    public Hangar(Escritor escritor, Paso paso) {
        this.paso = paso;
        this.escritor = escritor;
        this.aviones = new ArrayList<String>();
    }

    public ArrayList<String> getAviones() {
        return aviones;
    }
    
    public void entrar(Avion avion){
        aviones.add(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " entra al hangar.");
    }
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " sale del hangar.");
    }
}
