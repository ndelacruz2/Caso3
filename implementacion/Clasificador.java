package implementacion;

public class Clasificador extends Thread
{
    private final BuzonClasificacion buzonClasificacion;
    private final BuzonConsolidacion[] buzonesConsolidacion;
    private final ContadorClasificadores contador;
    private final int id;

    public Clasificador(BuzonClasificacion buzonClasificacion, BuzonConsolidacion[] buzonesConsolidacion,
                        ContadorClasificadores contador, int id)
    {
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesConsolidacion = buzonesConsolidacion;
        this.contador = contador;
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

            if (contador.registrarTerminacion())
            {
                for (BuzonConsolidacion b : buzonesConsolidacion)
                {
                    b.enviar(new Mensaje(true));
                }
                System.out.println("Clasificador " + id + ": último en terminar, envió FIN a todos los servidores.");
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
