package Main;

import Aeropuerto.Aeropuerto;
import Hilos.Autobus;
import Hilos.Avion;
import java.util.Random;

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        Paso paso = new Paso();
        Random r = new Random();
        
        Pantalla pantalla = new Pantalla(paso);
        pantalla.setVisible(true);
        
        Aeropuerto aerM = new Aeropuerto("Madrid");
        Aeropuerto aerB = new Aeropuerto("Barcelona");
        
        for (int i=0000; i<=4000; i++){
            if(i%2==0){
                Autobus a = new Autobus("B-"+i, aerM, paso);
                a.start();
            }
            else{
                Autobus a = new Autobus("B-"+i, aerB, paso);
                a.start();
            }
            Thread.sleep(500 + r.nextInt(501));
        }
        
        for (int i=0000; i<=8000; i++){
            char letra1 = (char) ('A' + r.nextInt(26));
            char letra2 = (char) ('A' + r.nextInt(26));
            
            if(i%2==0){
                Avion a = new Avion(letra1 + letra2 + "-"+i, aerM);
                a.start();
            }
            else{
                Avion a = new Avion(letra1 + letra2 + "-"+i, aerB);
                a.start();
            }
        }
    }
}
