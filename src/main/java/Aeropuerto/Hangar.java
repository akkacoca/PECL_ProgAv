package Aeropuerto;

import Hilos.Avion;
import java.util.ArrayList;

public class Hangar {
    private ArrayList<Avion> aviones;

    public Hangar() {
        this.aviones = new ArrayList<>();
    }
    
    
    public void entrar(Avion avion){
        aviones.add(avion);
    }
}
