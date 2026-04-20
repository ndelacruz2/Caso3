package Implementación;

import java.util.LinkedList;

public class BuzonEntrada
{
    private final LinkedList<Mensaje> cola = new LinkedList<>();

    public synchronized void enviar(Mensaje m)
    {
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
}