package Aeropuerto;

import Hilos.Avion;
import Main.Escritor;
import Main.Paso;
import java.util.ArrayList;

public class Aerovia {
    private String nombre;
    private ArrayList<String> aviones; // Lista de aviones en la aerovía
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
    
    // Método para que un avión entre a la aerovía
    public void entrar(Avion avion) throws InterruptedException{
        paso.mirar();
        
        aviones.add(avion.getID() + "(" + avion.getPasajeros() + ")");
        escritor.escribir("Avion " + avion.getID() + " accede a la aerovia " + getNombre());
    }
    
    // Método para que un avión salga de la aerovía
    public void salir(Avion avion) throws InterruptedException{
        paso.mirar();
        
        aviones.remove(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " sale de la aerovia " + getNombre());
    }
}