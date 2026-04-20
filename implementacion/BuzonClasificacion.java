package implementacion;

import java.util.LinkedList;

public class BuzonClasificacion
{
    private final LinkedList<Mensaje> cola = new LinkedList<>();
    private final int capacidad;

    public BuzonClasificacion(int capacidad)
    {
        this.capacidad = capacidad;
    }

    public synchronized void enviar(Mensaje m) throws InterruptedException
    {
        while (cola.size() == capacidad)
        {
            wait();
        }
        cola.add(m);
        notifyAll();
    }

    public synchronized Mensaje retirar() throws InterruptedException
    {
        while (cola.isEmpty())
        {
            wait();
        }
        Mensaje m = cola.poll();
        notifyAll();
        return m;
    }

    public synchronized boolean estaVacio()
    {
        return cola.isEmpty();
    }
}
