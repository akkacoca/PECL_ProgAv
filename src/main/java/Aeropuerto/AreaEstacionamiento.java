package Aeropuerto;

import Hilos.Avion;
import java.util.ArrayList;

public class AreaEstacionamiento {
    private ArrayList<String> aviones;

    public AreaEstacionamiento() {
        this.aviones = new ArrayList<String>();
    }
   public ArrayList<String> getAviones() {
        return aviones;
    }
    public void entrar(Avion avion){
        aviones.add(avion.getID());
        System.out.println("Avion " + avion.getID() + " entra al Area de estacionamiento.");
    }
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        System.out.println("Avion " + avion.getID() + " sale del Area de estacionamiento.");
    }
}
   