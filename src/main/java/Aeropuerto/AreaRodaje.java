
package Aeropuerto;

import Hilos.Avion;
import Main.Escritor;
import Main.Paso;
import java.util.ArrayList;

public class AreaRodaje {
    private ArrayList<String> aviones;
    private final Escritor escritor;
    private final Paso paso;
    
    public AreaRodaje(Escritor escritor, Paso paso) {
        this.paso = paso;
        this.escritor = escritor;
        this.aviones = new ArrayList<>();
    }
    
    public ArrayList<String> getAviones() {
        return aviones;
    }
    public void entrar(Avion avion){
        aviones.add(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " entra al Area de rodaje.");
    }
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " sale del Area de rodaje.");
    }
}
