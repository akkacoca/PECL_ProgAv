package Main;

import Aeropuerto.Aeropuerto;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread {
    
    private final Aeropuerto aeropuertoM;
    private final Aeropuerto aeropuertoB;
    
    public Servidor(Aeropuerto aeropuertoM, Aeropuerto aeropuertoB) {
        this.aeropuertoM = aeropuertoM;
        this.aeropuertoB = aeropuertoB;
    }
    
    @Override
    public void run() {
        
        ServerSocket servidor;
        Socket conexion;
        DataInputStream entrada;
        DataOutputStream salida;
        
        try {
            servidor = new ServerSocket(5000);
            while(true) {
                
                conexion = servidor.accept();
                entrada = new DataInputStream(conexion.getInputStream());
                salida = new DataOutputStream(conexion.getOutputStream());
                int envio = entrada.readInt();
                
                int vuelta = 0;
                String texto = "";
                switch (envio) {
                    case 1: vuelta = (aeropuertoM.getPasajeros()); break;
                    case 2: vuelta = (aeropuertoB.getPasajeros()); break;
                    case 3: vuelta = (aeropuertoM.getHangar().getAviones().size()); break;
                    case 4: vuelta = (aeropuertoB.getHangar().getAviones().size()); break;
                    case 5: vuelta = (aeropuertoM.getTaller().getAviones().size()); break;
                    case 6: vuelta = (aeropuertoB.getTaller().getAviones().size()); break;
                    case 7: vuelta = (aeropuertoM.getAreaEstacionamiento().getAviones().size()); break;
                    case 8: vuelta = (aeropuertoB.getAreaEstacionamiento().getAviones().size()); break;
                    case 9: vuelta = (aeropuertoM.getAreaRodaje().getAviones().size()); break;
                    case 10: vuelta = (aeropuertoB.getAreaRodaje().getAviones().size()); break;
                    case 11: texto = (aeropuertoM.getAreoviaMB()); break;
                    case 12: texto = (aeropuertoB.getAreoviaBM());
                }
                
                salida.writeInt(vuelta);
                salida.writeUTF(texto);

                entrada.close();
                salida.close();
                conexion.close();
            }            
        } catch (IOException ex) {
            System.out.println("Ha habido un error");
        }
    }
}