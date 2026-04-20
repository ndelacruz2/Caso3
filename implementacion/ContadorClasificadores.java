package implementacion;

public class ContadorClasificadores
{
    private int activos;

    public ContadorClasificadores(int nc)
    {
        this.activos = nc;
    }

    public synchronized boolean registrarTerminacion()
    {
        activos--;
        return activos == 0;
    }
}
