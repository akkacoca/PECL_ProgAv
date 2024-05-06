package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Escritor {
    
    // Objeto FileWriter para escribir en el archivo
    private FileWriter fw;
    
    //Variable compartida que abre un escritor de archivos
    //Constructor de la clase Escritor que inicializa el objeto FileWriter para el archivo de registro
    public Escritor() {
        try {
            // Crea el FileWriter para el archivo "aeropuerto.txt"
            fw = new FileWriter("evolucionAeropuerto.txt");
            
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
            // Obtiene la fecha y hora actual formateada
            String segundo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy | HH:mm:ss"));

            // Escribe el evento en el archivo de registro junto con la fecha y hora
            System.out.println(str);
            fw.write(segundo + " -> " + str + "\n");
            fw.flush(); // Vacía el búfer para asegurar que los datos se escriban en el archivo
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