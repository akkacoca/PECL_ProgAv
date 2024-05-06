/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Aeropuerto.Aeropuerto;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Manuel
 */
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
            int n = 0;
            while(n <10000) {
                
                conexion = servidor.accept();
                entrada = new DataInputStream(conexion.getInputStream());
                salida = new DataOutputStream(conexion.getOutputStream());
                int envio = entrada.readInt();
                for(int i = 1; i<=2; i++){
                    switch (i) {
                        case 1: salida.writeInt(aeropuertoM.getPasajeros());
                        case 2: salida.writeInt(aeropuertoB.getPasajeros());
                        default: aeropuertoM.getPasajeros();
                    };
                }
                
                

                entrada.close();
                salida.close();
                conexion.close();
            }
            
            servidor.close();
            
        } catch (IOException ex) {
            System.out.println("Problema en Servidor");
        }
    }
}