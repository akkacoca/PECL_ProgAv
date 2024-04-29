package Aeropuerto;

import Hilos.Avion;
import java.util.ArrayList;

public class Hangar {
    private ArrayList<String> aviones;

    public Hangar() {
        this.aviones = new ArrayList<String>();
    }

    public ArrayList<String> getAviones() {
        return aviones;
    }
    
    public void entrar(Avion avion){
        aviones.add(avion.getID());
        System.out.println("Avion " + avion.getID() + " entra al hangar.");
    }
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        System.out.println("Avion " + avion.getID() + " sale del hangar.");
    }
}
