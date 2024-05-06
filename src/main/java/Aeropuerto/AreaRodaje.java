package Aeropuerto;

import Hilos.Avion;
import Main.Escritor;
import Main.Paso;
import java.util.ArrayList;

public class AreaRodaje {
    private ArrayList<String> aviones; // Lista de aviones en el área de rodaje
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
    
    // Método para que un avión entre al área de rodaje
    public void entrar(Avion avion) throws InterruptedException{
        paso.mirar();
        
        aviones.add(avion.getID() + "(" + avion.getPasajeros() + ")" );
        escritor.escribir("Avion " + avion.getID() + " entra al Area de rodaje.");
    }
    
    // Método para que un avión salga del área de rodaje
    public void salir(Avion avion) throws InterruptedException{
        paso.mirar();
        
        aviones.remove(avion.getID());
        escritor.escribir("Avion " + avion.getID() + " sale del Area de rodaje.");
    }
}
