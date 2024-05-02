
package Aeropuerto;
import Hilos.Avion;
import java.util.ArrayList;

public class Aerovia {
    private String nombre;
    private ArrayList<String> aviones;

    public Aerovia(String nombre) {
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
        System.out.println("Avion " + avion.getID() + " accede a la aerovia "+ getNombre());
    }
    public void salir(Avion avion){
        aviones.remove(avion.getID());
        System.out.println("Avion " + avion.getID() + " sale de la aerovia.");
    }
    // Puedes agregar métodos adicionales según sea necesario.
}