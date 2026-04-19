package Implementación;


public class ClienteEmisor extends Thread
{
    private BuzonEntrada buzon;
    private int cantidad;

    public ClienteEmisor(BuzonEntrada b, int x)
    {
        this.buzon = b;
        this.cantidad = x;
    }

    @Override
    public void run()
    {
        try
        {
            for(int i = 0; i < cantidad; i++)
            {
                 Mensaje m = new Mensaje("Información " + i);
                buzon.enviar(m);
                System.out.println();
            }
        }
        catch (Exception e)
        {
        e.printStackTrace();
        }
    }
}
