
package Aeropuerto;

import Hilos.Avion;
import java.util.ArrayList;

public class AreaRodaje {
    private ArrayList<String> aviones;

    public AreaRodaje() {
        this.aviones = new ArrayList<>();
    }
    
    public ArrayList<String> getAviones() {
        return aviones;
    }
    public void entrar(Avion avion){
        aviones.add(avion.getID());
        System.out.println("Avion " + avion.getID() + " entra al Area de rodaje.");
    }
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        System.out.println("Avion " + avion.getID() + " sale del Area de rodaje.");
    }
}
