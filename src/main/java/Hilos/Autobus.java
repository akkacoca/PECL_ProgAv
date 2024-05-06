package Hilos;

import Aeropuerto.Aeropuerto;
import Main.Escritor;
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
    private final Escritor escritor;

    public Autobus(String id, Aeropuerto aeropuerto, Paso paso, Pantalla pantalla, Escritor escritor) {
        this.escritor = escritor;
        this.id = id;
        this.aforoBus = 50;
        this.aeropuerto = aeropuerto;
        this.paso = paso;
        this.pantalla = pantalla;
    }

    @Override
    public void run() {
        while (true) { // Bucle infinito para simular el funcionamiento del autobús
            try {
                paso.mirar(); // Sincroniza el hilo con el Paso
                
                //Llegada a la parada de la ciudad
                Thread.sleep(r.nextInt(3001) + 2000);
                
                //suben pasajeros
                pasajeros = r.nextInt(51);
                
                paso.mirar();
                // Actualiza la pantalla con la información del autobús según el aeropuerto de origen
                if (this.aeropuerto.getNombre() == "Madrid"){
                    this.pantalla.getTransferAeropuertoTextFieldM().setText(id + "(" + pasajeros + ")");
                }
                else{this.pantalla.getTransferAeropuertoTextFieldB().setText(id + "(" + pasajeros + ")");}
                
                paso.mirar();
                
                //viaje al aeropuerto
                Thread.sleep(r.nextInt(5001) + 5000);
                
                //dejar pasajeros en el aeropuerto
                aeropuerto.aumentarPasajeros(pasajeros);
                escritor.escribir("El autobus " + this.id + " deja a " + pasajeros + " en el aeropuerto " + aeropuerto.getNombre());
                pasajeros = 0;
                
                paso.mirar();
                
                //espera a que suban pasajeros en el aeropuerto
                Thread.sleep(r.nextInt(3001) + 2000);
                
                //suben pasajeros en el aeropuerto
                // Limita la cantidad de pasajeros según la capacidad del autobús y la cantidad de pasajeros disponibles en el aeropuerto
                paso.mirar();
                pasajeros = r.nextInt(Math.min(aeropuerto.getPasajeros() + 1, aforoBus));
                escritor.escribir("El autobus " + this.id + " recoje a " + pasajeros + " en el aeropuerto " + aeropuerto.getNombre());
                paso.mirar();
                aeropuerto.disminuirPasajeros(pasajeros);
                paso.mirar();
                // Actualiza la pantalla con la información del autobús según el aeropuerto de destino
                if (this.aeropuerto.getNombre() == "Madrid"){
                    this.pantalla.getTransferCiudadTextFieldM().setText(id + "(" + pasajeros + ")");
                }
                else{this.pantalla.getTransferCiudadTextFieldB().setText(id + "(" + pasajeros + ")");}
                paso.mirar();
                //viaje a la ciudad
                Thread.sleep(r.nextInt(5001) + 5000);
                pasajeros = 0; // Reinicia el contador de pasajeros en el autobús
                
                
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
