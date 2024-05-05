package Aeropuerto;

import Hilos.Avion;
import Main.Escritor;
import Main.Paso;
import java.util.ArrayList;

public class AreaEstacionamiento {
    private ArrayList<String> aviones;
    private final Escritor escritor;
    private final Paso paso;
    
    public AreaEstacionamiento(Escritor escritor,Paso paso) {
        this.paso = paso;
        this.escritor = escritor;
        this.aviones = new ArrayList<String>();
    }
   public ArrayList<String> getAviones() {
        return aviones;
    }
    public void entrar(Avion avion){
        aviones.add(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " entra al Area de estacionamiento.");
    }
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " sale del Area de estacionamiento.");
    }
}
   