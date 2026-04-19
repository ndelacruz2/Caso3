package Implementación;

import java.util.Random;

public class ManejadorCuarentena extends Thread
{
    private final BuzonCuarentena buzonAlertas;
    private final BuzonClasificacion buzonClasificacion;
    private final int nc;
    private final Random random = new Random();

    public ManejadorCuarentena(BuzonCuarentena buzonAlertas, BuzonClasificacion buzonClasificacion, int nc)
    {
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.nc = nc;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Mensaje m = buzonAlertas.retirar();

                if (m.esFinal())
                {
                    for (int i = 0; i < nc; i++)
                    {
                        buzonClasificacion.enviar(new Mensaje(true));
                    }
                    System.out.println("Administrador: terminó, envió " + nc + " FIN a clasificadores.");
                    return;
                }

                int r = random.nextInt(21);
                if (r % 4 == 0)
                {
                    buzonClasificacion.enviar(m);
                    System.out.println("Administrador: evento inofensivo -> " + m);
                }
                else
                {
                    System.out.println("Administrador: evento malicioso descartado -> " + m);
                }
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
