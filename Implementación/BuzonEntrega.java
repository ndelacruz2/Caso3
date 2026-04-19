package Implementación;

import java.util.LinkedList;

public class BuzonEntrega 
{
    private final LinkedList<Mensaje>  cola = new LinkedList<>();
    private final int capacidad;
    
    public BuzonEntrega(int capacidad)
    {
        this.capacidad = capacidad;
    }

    public synchronized boolean Lleno()
    {
        return cola.size() >= capacidad;
    }

    public synchronized void enviar(Mensaje m)
    {
        cola.add(m);
    }

    public Mensaje retirar()
    {
        while(true)
            {
                synchronized(this)
                {
                    if (!cola.isEmpty())
                        {
                            return cola.poll();
                        }
                }
            }
    }
}
