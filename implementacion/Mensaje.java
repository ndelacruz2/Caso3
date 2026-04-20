package implementacion;

public class Mensaje {
    private final String id;
    private final int tipoServidor;
    private final boolean esFinal;

    public Mensaje(int sensorId, int secuencial, int tipoServidor)
    {
        this.id = "sensor" + sensorId + "-" + secuencial;
        this.tipoServidor = tipoServidor;
        this.esFinal = false;
    }

    public Mensaje(boolean esFinal)
    {
        this.id = "FIN";
        this.tipoServidor = -1;
        this.esFinal = esFinal;
    }

    public String getId()
    {
        return id;
    }

    public int getTipoServidor()
    {
        return tipoServidor;
    }

    public boolean esFinal()
    {
        return esFinal;
    }

    @Override
    public String toString()
    {
        return esFinal ? "[FIN]" : "[Mensaje " + id + " -> servidor " + tipoServidor + "]";
    }
}
