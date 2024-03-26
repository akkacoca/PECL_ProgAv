package Aeropuerto;

import Hilos.Avion;
import java.util.ArrayList;

public class AreaEstacionamiento {
    private ArrayList<Avion> aviones;

    public AreaEstacionamiento() {
        this.aviones = new ArrayList<>();
    }
    
    public void entrar(Avion avion){
        aviones.add(avion);
    }
}
