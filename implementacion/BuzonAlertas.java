package implementacion;

import java.util.LinkedList;

public class BuzonAlertas
{
    private final LinkedList<Mensaje> cola = new LinkedList<>();

    public synchronized void enviar(Mensaje m)
    {
        cola.add(m);
    }

    public Mensaje retirar() throws InterruptedException
    {
        while (true)
        {
            synchronized (this)
            {
                if (!cola.isEmpty())
                {
                    return cola.poll();
                }
            }
            Thread.yield();
        }
    }

    public synchronized boolean estaVacio()
    {
        return cola.isEmpty();
    }
}