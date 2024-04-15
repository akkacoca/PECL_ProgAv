package Hilos;

import Aeropuerto.Aeropuerto;
import Aeropuerto.PuertaEmbarque;
import java.util.Random;

public class Avion extends Thread {
    private String id;
    private Aeropuerto aeropuerto;
    private String TipoOperacion;  // embarque/desembarque
    private int pasajeros;
    private int capacidadMax;
    private Random r;

    public Avion(String id, Aeropuerto aeropuerto) {
        this.id = id;
        this.aeropuerto = aeropuerto;
        this.TipoOperacion = "embarque";  //cuando se genera el avíon comienza el ciclo de cero
        this.pasajeros = 0;
        this.capacidadMax = r.nextInt(201) + 100;
        
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public int getPasajeros() {
        return pasajeros;
    }


    public String getTipoOperacion() {
        return TipoOperacion;
    }
    
    @Override
    public void run() {
        while (true) {
            //se genera el avion en el hangar
            aeropuerto.pasarHangar(this);
            //el avion pasa a el area de estacionamiento
            aeropuerto.pasarArea(this);
            //el avion trata de acceder a la puerta de embarque
            aeropuerto.accederPuertaEmbarque(this);
        }
    }

    private void despegar() {
        System.out.println("Avion " + id + " despegando.");
        // Lógica para el despegue del avión
    }

    private void aterrizar() {
        System.out.println("Avion " + id + " aterrizando.");
        // Lógica para el aterrizaje del avión
    }
    
    public void embarcarPasajeros(int cantidad) {
        pasajeros += cantidad;
    }

    public void desembarcarPasajeros(int cantidad) {
        pasajeros -= cantidad;
    }

    public void accederPuertaEmbarque(PuertaEmbarque puerta) {
        try {
            puerta.solicitarAcceso(this);
            if (TipoOperacion.equals("embarque")) {
                puerta.embarcarPasajeros(this);
            } else {
                puerta.desembarcarPasajeros(this);
            }
            System.out.println("Avion " + id + " completó la operación en la Puerta de Embarque " + puerta.getNumero());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            puerta.liberarAcceso();
        }
    }
}
