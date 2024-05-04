package Hilos;

import Aeropuerto.Aeropuerto;
import Main.Pantalla;
import java.util.Random;
import Main.Paso;

public class Autobus extends Thread {
    private String id;
    private Aeropuerto aeropuerto;
    private Paso paso;
    private int pasajeros;
    private int aforoBus;
    private Random r = new Random();
    private Pantalla pantalla;

    public Autobus(String id, Aeropuerto aeropuerto, Paso paso, Pantalla pantalla) {
        this.id = id;
        this.aforoBus = 50;
        this.aeropuerto = aeropuerto;
        this.paso = paso;
        this.pantalla = pantalla;
    }

    @Override
    public void run() {
        while (true) {
            try {
                paso.mirar();
                
                //Llegada a la parada de la ciudad
                Thread.sleep(r.nextInt(3001) + 2000);
                //suben pasajeros
                pasajeros = r.nextInt(51);
                
                if (this.aeropuerto.getNombre() == "Madrid"){
                    this.pantalla.getTransferAeropuertoTextFieldM().setText(id);
                }
                else{this.pantalla.getTransferAeropuertoTextFieldB().setText(id);}
                
                //viaje al aeropuerto
                Thread.sleep(r.nextInt(5001) + 5000);
                //dejar pasajeros en el aeropuerto
                aeropuerto.aumentarPasajeros(pasajeros);
                System.out.println("El autobus " + this.id + " deja a " + pasajeros + " en el aeropuerto " + aeropuerto.getNombre());
                pasajeros = 0;
                //espera a que suban pasajeros en el aeropuerto
                Thread.sleep(r.nextInt(3001) + 2000);
                //suben pasajeros en el aeropuerto
                pasajeros = r.nextInt(Math.min(aeropuerto.getPasajeros() + 1, aforoBus));
                System.out.println("El autobus " + this.id + " recoje a " + pasajeros + " en el aeropuerto " + aeropuerto.getNombre());
                aeropuerto.disminuirPasajeros(pasajeros);
                
                if (this.aeropuerto.getNombre() == "Madrid"){
                    this.pantalla.getTransferCiudadTextFieldM().setText(id);
                }
                else{this.pantalla.getTransferCiudadTextFieldB().setText(id);}
                
                //viaje a la ciudad
                Thread.sleep(r.nextInt(5001) + 5000);
                pasajeros = 0;
                
                
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
