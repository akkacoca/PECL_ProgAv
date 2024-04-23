
package Aeropuerto;
import Hilos.Avion;
import java.util.ArrayList;


public class Aerovia {
    private String nombre;
    private ArrayList<Avion> aviones1;
    private ArrayList<Avion> aviones2;



    public Aerovia(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void entrar(Avion avion){
        aviones1.add(avion);
    }
    public void salir(Avion avion){
        aviones1.remove(avion);
    }
    // Puedes agregar métodos adicionales según sea necesario.
}