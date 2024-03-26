
package Main;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Abel
 */
public class Paso {
    
    private boolean cerrado;
    private final Lock cerrojo;
    private final Condition parar;
    
    /**
     * Variable compartida para pausar el flujo de ejecucion de todas las funcionalidades del campamento
     */
    public Paso() {
        cerrado = false;
        cerrojo = new ReentrantLock();
        parar = cerrojo.newCondition();
    }
    
    /**
     * Funcion que en caso de true la variable booleana bloquea a todos los hilos que lleguen
     * @throws InterruptedException 
     */
    public void mirar() throws InterruptedException {
        cerrojo.lock();
        try {
            while(cerrado) parar.await();
        } finally {
            cerrojo.unlock();
        }
    }
    
    /**
     * Funcion que desbloquea a todos los hilos que estaban bloqueados en mirar()
     */
    public void abrir() {
        cerrojo.lock();
        try {
            cerrado = false;
            parar.signalAll();
        } finally {
            cerrojo.unlock();
        }
    }
    
    /**
     * Funcion que pone a true la variable booleana para empezar a bloquear a los nuevos hilos que lleguen
     */
    public void cerrar() {
        cerrojo.lock();
        try {
            cerrado = true;
        } finally {
            cerrojo.unlock();
        }
    }
}