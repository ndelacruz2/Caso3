package Implementación;
import Implementación.InterfazEvento;

public class Evento implements InterfazEvento
{
    private String idOrigen;
    private String tipo;
    private Object contenido;
    private long timestamp;

    public Evento(String id, String tipo, Object contenido) 
    {
        this.idOrigen = id;
        this.tipo = tipo;
        this.contenido = contenido;
        this.timestamp = System.currentTimeMillis();
    }

}
