
package Aeropuerto;

import Hilos.Avion;
import java.util.ArrayList;

public class AreaRodaje {
    private ArrayList<Avion> aviones;

    public AreaRodaje() {
        this.aviones = new ArrayList<>();
    }
    
    public void entrar(Avion avion){
        aviones.add(avion); 
    }
    public void salir(Avion avion){
        aviones.remove(avion); 
    }
}
