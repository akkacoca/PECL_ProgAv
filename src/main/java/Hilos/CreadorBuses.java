package Hilos;

import Aeropuerto.Aeropuerto;
import Main.Pantalla;
import Main.Paso;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreadorBuses extends Thread{
    
    private Aeropuerto aerM;
    private Aeropuerto aerB;
    private Paso paso;
    private Pantalla pantalla;
    private Random r = new Random();
    
    public CreadorBuses(Aeropuerto aerM, Aeropuerto aerB, Paso paso, Pantalla pantalla) {
        this.aerB = aerB;
        this.aerM = aerM;
        this.paso = paso;
        this.pantalla = pantalla;
    }

    @Override
    public void run() {
        for (int i=0000; i<=4000; i++){
            if(i%2==0){
                Autobus a = new Autobus("B-"+i, aerM, paso, pantalla);
                a.start();
            }
            else{
                Autobus a = new Autobus("B-"+i, aerB, paso, pantalla);
                a.start();
            }
            try {
                Thread.sleep(500 + r.nextInt(501));
            } catch (InterruptedException ex) {
                Logger.getLogger(CreadorBuses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
