package Implementación;

public class Clasificador extends Thread
{
    private final BuzonClasificacion buzonClasificacion;
    private final BuzonConsolidacion[] buzonesConsolidacion;
    private final int id;

    private static int activos;
    private static final Object lockActivos = new Object();

    public static void inicializarActivos(int nc)
    {
        activos = nc;
    }

    public Clasificador(BuzonClasificacion buzonClasificacion, BuzonConsolidacion[] buzonesConsolidacion, int id)
    {
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesConsolidacion = buzonesConsolidacion;
        this.id = id;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Mensaje m = buzonClasificacion.retirar();

                if (m.esFinal())
                {
                    System.out.println("Clasificador " + id + ": recibió FIN, terminando.");
                    break;
                }

                int servidor = m.getTipoServidor() - 1;
                buzonesConsolidacion[servidor].enviar(m);
                System.out.println("Clasificador " + id + ": enrutó " + m + " a servidor " + (servidor + 1));
            }

            synchronized (lockActivos)
            {
                activos--;
                if (activos == 0)
                {
                    for (BuzonConsolidacion b : buzonesConsolidacion)
                    {
                        b.enviar(new Mensaje(true));
                    }
                    System.out.println("Clasificador " + id + ": último en terminar, envió FIN a todos los servidores.");
                }
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
