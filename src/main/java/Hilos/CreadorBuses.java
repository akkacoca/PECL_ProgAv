package Hilos;

import Aeropuerto.Aeropuerto;
import Main.Escritor;
import Main.Pantalla;
import Main.Paso;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreadorBuses extends Thread{
    private final Escritor escritor;
    private Aeropuerto aerM;
    private Aeropuerto aerB;
    private Paso paso;
    private Pantalla pantalla;
    private Random r = new Random();
    
    public CreadorBuses(Aeropuerto aerM, Aeropuerto aerB, Paso paso, Pantalla pantalla,Escritor escritor) {
        this.escritor = escritor;
        this.aerB = aerB;
        this.aerM = aerM;
        this.paso = paso;
        this.pantalla = pantalla;
    }

    @Override
    public void run() {
        for (int i=0000; i<=4000; i++){
            try {
                paso.mirar();
                // Crea un autobús y lo inicia alternando entre los aeropuertos de Madrid y Barcelona
                if(i%2==0){
                    Autobus a = new Autobus("B-"+i, aerM, paso, pantalla, escritor);
                    escritor.escribir("Autobus " + "B-"+i + " es creado");
                    a.start();
                }
                else{
                    Autobus a = new Autobus("B-"+i, aerB, paso, pantalla, escritor);
                    escritor.escribir("Autobus " + "B-"+i + " es creado");
                    a.start();
                }
                
                try {
                    // Espera un tiempo aleatorio antes de crear el siguiente autobús
                    Thread.sleep(500 + r.nextInt(501));
                } catch (InterruptedException ex) {
                    System.out.println("Problema en CreadorBuses");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(CreadorBuses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
