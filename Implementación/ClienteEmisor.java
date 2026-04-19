package Implementación;

import java.util.Random;

public class ClienteEmisor extends Thread
{
    private final BuzonEntrada buzon;
    private final int sensorId;
    private final int cantidad;
    private final int ns;
    private final Random random = new Random();

    public ClienteEmisor(BuzonEntrada buzon, int sensorId, int eventoBase, int ns)
    {
        this.buzon = buzon;
        this.sensorId = sensorId;
        this.cantidad = eventoBase * sensorId;
        this.ns = ns;
    }

    @Override
    public void run()
    {
        for (int i = 1; i <= cantidad; i++)
        {
            int tipoServidor = random.nextInt(ns) + 1;
            Mensaje m = new Mensaje(sensorId, i, tipoServidor);
            buzon.enviar(m);
            System.out.println("Sensor " + sensorId + " envió " + m);
        }
    }
}
