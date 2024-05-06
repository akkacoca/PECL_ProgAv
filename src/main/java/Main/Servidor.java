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
        Socket socketServidor;
        DataInputStream entradaS;
        DataOutputStream salidaS;
        
        try {
            servidor = new ServerSocket(5000);
            while(true) {
                // Aceptar conexiones de clientes
                socketServidor = servidor.accept();
                entradaS = new DataInputStream(socketServidor.getInputStream());
                salidaS = new DataOutputStream(socketServidor.getOutputStream());
                int msg = entradaS.readInt(); // Lee el mensaje del cliente
                
                int respuesta = 0; // Variable para almacenar la respuesta numérica
                String respuestaTexto = ""; // Variable para almacenar la respuesta en texto
                // Realiza acciones según el mensaje recibido
                switch (msg) {
                    case 1: respuesta = (aeropuertoM.getPasajeros()); break;
                    case 2: respuesta = (aeropuertoB.getPasajeros()); break;
                    case 3: respuesta = (aeropuertoM.getHangar().getAviones().size()); break;
                    case 4: respuesta = (aeropuertoB.getHangar().getAviones().size()); break;
                    case 5: respuesta = (aeropuertoM.getTaller().getAviones().size()); break;
                    case 6: respuesta = (aeropuertoB.getTaller().getAviones().size()); break;
                    case 7: respuesta = (aeropuertoM.getAreaEstacionamiento().getAviones().size()); break;
                    case 8: respuesta = (aeropuertoB.getAreaEstacionamiento().getAviones().size()); break;
                    case 9: respuesta = (aeropuertoM.getAreaRodaje().getAviones().size()); break;
                    case 10: respuesta = (aeropuertoB.getAreaRodaje().getAviones().size()); break;
                    case 11: respuestaTexto = (aeropuertoM.getAreoviaMB()); break;
                    case 12: respuestaTexto = (aeropuertoB.getAreoviaBM());
                }
                // Envía la respuesta al cliente
                salidaS.writeInt(respuesta);
                salidaS.writeUTF(respuestaTexto);

                // Cierra los flujos y el socket del servidor
                entradaS.close();
                salidaS.close();
                socketServidor.close();
            }            
        } catch (IOException ex) {
            System.out.println("Ha habido un error");
        }
    }
}