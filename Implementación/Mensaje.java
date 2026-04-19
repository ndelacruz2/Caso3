package Implementación;

public class Mensaje {
    private final String contenido;
    private boolean esSpam;
    private final boolean esFinal;

    public Mensaje(String contenido)
    {
        this.contenido = contenido;
        this.esSpam = false;
        this.esFinal = false;
    }

    public Mensaje (boolean esFinal)
    {
        this.contenido = "Este fue el último mensaje";
        this.esSpam = false;
        this.esFinal = esFinal;
    }

    public boolean esFinal()
    {
        return esFinal;
    }

    public boolean isEsSpam()
    {
        return esSpam;
    }

    public void setEsSpam(boolean esSpam)
    {
        this.esSpam = esSpam;
    }

    public String getContenido()
    {
        return contenido;
    }
    
}
