package Implementación;

public class InterfazEvento 
{

public interface Evento {
    // Para identificar qué sensor generó la información
    public String getIdOrigen();
    
    // Para saber qué tipo de evento es (ej. "TEMPERATURA", "ALERTA")
    public String getTipo();
    
    // El contenido real del mensaje
    public Object getContenido();
    
    // Útil para métricas de latencia después
    public long getTimestamp();
}
    
} 
