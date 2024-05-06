package Aeropuerto;

import Hilos.Avion;
import Main.Escritor;
import Main.Paso;
import java.util.ArrayList;

public class AreaEstacionamiento {
    private ArrayList<String> aviones; // ArrayList para almacenar los aviones
    private final Escritor escritor;
    private final Paso paso;
    
    public AreaEstacionamiento(Escritor escritor,Paso paso) {
        this.paso = paso;
        this.escritor = escritor;
        this.aviones = new ArrayList<String>();
    }
    
    // Método para obtener la lista de aviones en el Área
   public ArrayList<String> getAviones() {
        return aviones;
    }
   
    // Método para que un avión entre al Área   
    public void entrar(Avion avion) throws InterruptedException{
        paso.mirar();
        
        aviones.add(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " entra al Area de estacionamiento.");
    }
    
    // Método para que un avión salga del Área   
    public void salir(Avion avion) throws InterruptedException{
        paso.mirar();
        
        aviones.remove(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " sale del Area de estacionamiento.");
    }
}
   