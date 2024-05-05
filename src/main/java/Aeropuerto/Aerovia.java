
package Aeropuerto;
import Hilos.Avion;
import Main.Escritor;
import Main.Paso;
import java.util.ArrayList;

public class Aerovia {
    private String nombre;
    private ArrayList<String> aviones;
    private final Escritor escritor;
    private final Paso paso;
    
    public Aerovia(String nombre, Escritor escritor, Paso paso) {
        this.paso = paso;
        this.escritor = escritor;
        this.nombre = nombre;
        this.aviones = new ArrayList<>();
    }
    
    public String getNombre(){
        return nombre;
    }
    public ArrayList<String> getAviones() {
        return aviones;
    }
    public void entrar(Avion avion){
        aviones.add(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " accede a la aerovia " + getNombre());
    }
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " sale de la aerovia." + getNombre());
    }
    // Puedes agregar métodos adicionales según sea necesario.
}