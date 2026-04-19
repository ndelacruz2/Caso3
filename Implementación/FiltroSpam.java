package Implementación;

import java.util.Random;

public class FiltroSpam extends Thread
{
    private final BuzonEntrada buzonEntrada;
    private final BuzonCuarentena buzonAlertas;
    private final BuzonClasificacion buzonClasificacion;
    private final int totalEventos;
    private final Random random = new Random();

    public FiltroSpam(BuzonEntrada buzonEntrada, BuzonCuarentena buzonAlertas,
                      BuzonClasificacion buzonClasificacion, int totalEventos)
    {
        this.buzonEntrada = buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run()
    {
        try
        {
            for (int i = 0; i < totalEventos; i++)
            {
                Mensaje m = buzonEntrada.retirar();

                int r = random.nextInt(201);
                if (r % 8 == 0)
                {
                    buzonAlertas.enviar(m);
                    System.out.println("Broker: anomalía detectada -> " + m);
                }
                else
                {
                    buzonClasificacion.enviar(m);
                    System.out.println("Broker: evento normal -> " + m);
                }
            }

            buzonAlertas.enviar(new Mensaje(true));
            System.out.println("Broker: terminó, envió FIN al administrador.");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
