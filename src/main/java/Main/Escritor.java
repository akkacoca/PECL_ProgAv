/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author Manuel
 */
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Abel
 */
public class Escritor {
    
    private FileWriter fw;
    
    /**
     * Variable compartida que abre un escritor de archivos
     */
    public Escritor() {
        try {
            fw = new FileWriter("aeropuerto.txt");
            
        } catch (IOException ioe) {
            System.out.println("Problema en Escritor");
        }
    }
    
    /**
     * Funcion que escribe en el fichero la fecha y hora actual ademas de una cadena pasada como argumento
     * @param str 
     */
    public synchronized void escribir(String str) {
        try {
            String segundo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy | HH:mm:ss.SSSSSS"));

            System.out.println(str);
            fw.write(segundo + " -> " + str + "\n");
            fw.flush();
        } catch (IOException ioe) {
            System.out.println("Problema en Escritor");
        }
    }
    
    /**
     * Funcion que cierra el escritor de archivos
     */
    public void cerrarEscritor() {
        try {
            fw.close();
        } catch (IOException ex) {
            System.out.println("Problema en Escritor");
        }
    }
}