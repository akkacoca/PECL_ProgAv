
package Main;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Paso {
    
    private boolean cerrado;
    private final Lock cerrojo; // Cerrojo para la exclusión mutua
    private final Condition parar; // Condición para pausar el flujo de ejecución
    
    
    public Paso() {
        cerrado = false; // Inicialmente el paso está abierto
        cerrojo = new ReentrantLock();
        parar = cerrojo.newCondition();
    }
    
    /**
     * Funcion que en caso de true la variable booleana bloquea a todos los hilos que lleguen
     * @throws InterruptedException 
     */
    public void mirar() throws InterruptedException {
        cerrojo.lock(); // Adquiere el cerrojo para garantizar la exclusión mutua
        try {
            while(cerrado) // Mientras el paso esté cerrado
                parar.await(); // El hilo espera en la condición de pausa
        } finally {
            cerrojo.unlock(); // Libera el cerrojo después de la espera
        }
    }
    
    /**
     * Funcion que desbloquea a todos los hilos que estaban bloqueados en mirar()
     */
    public void abrir() {
        cerrojo.lock(); // Adquiere el cerrojo para garantizar la exclusión mutua
        try {
            // Establece la variable cerrado como false, indicando que el paso está abierto
            cerrado = false; 
            // Despierta a todos los hilos que están esperando en la condición de pausa
            parar.signalAll();
        } finally {
            cerrojo.unlock(); // Libera el cerrojo después de la señalización
        }
    }
    
    /**
     * Funcion que pone a true la variable booleana para empezar a bloquear a los nuevos hilos que lleguen
     */
    public void cerrar() {
        cerrojo.lock(); // Adquiere el cerrojo para garantizar la exclusión mutua
        try {
            // Establece la variable cerrado como true, indicando que el paso está cerrado
            cerrado = true;
        } finally {
            // Libera el cerrojo después de establecer el paso como cerrado
            cerrojo.unlock();
        }
    }
}