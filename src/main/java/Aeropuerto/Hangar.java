package Aeropuerto;

import Hilos.Avion;
import Main.Escritor;
import Main.Paso;
import java.util.ArrayList;

public class Hangar {
    private ArrayList<String> aviones; // ArrayList para almacenar los aviones
    private final Escritor escritor;
    private final Paso paso;
    
    public Hangar(Escritor escritor, Paso paso) {
        this.paso = paso;
        this.escritor = escritor;
        this.aviones = new ArrayList<String>();
    }

    // Método para obtener la lista de aviones en el hangar
    public ArrayList<String> getAviones() {
        return aviones;
    }
    
    // Método para que un avión entre al hangar
    public void entrar(Avion avion){
        aviones.add(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " entra al hangar.");
    }
    
    // Método para que un avión salga del hangar
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " sale del hangar.");
    }
}
