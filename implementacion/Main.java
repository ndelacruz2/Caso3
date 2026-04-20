package implementacion;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        if (args.length < 1)
        {
            System.out.println("Uso: java Main <archivo_config>");
            return;
        }

        // Leer parámetros del archivo de configuración
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        int ni       = Integer.parseInt(br.readLine().trim());
        int base     = Integer.parseInt(br.readLine().trim());
        int nc       = Integer.parseInt(br.readLine().trim());
        int ns       = Integer.parseInt(br.readLine().trim());
        int tam1     = Integer.parseInt(br.readLine().trim());
        int tam2     = Integer.parseInt(br.readLine().trim());
        br.close();

        // Total de eventos: base * 1 + base * 2 + ... + base * ni = base * ni*(ni+1)/2
        int totalEventos = base * ni * (ni + 1) / 2;

        System.out.println("=== Sistema IoT ===");
        System.out.println("Sensores: " + ni + " | Base: " + base + " | Total eventos: " + totalEventos);
        System.out.println("Clasificadores: " + nc + " | Servidores: " + ns);
        System.out.println("tam1: " + tam1 + " | tam2: " + tam2);
        System.out.println("==================");

        // Crear buzones
        BuzonEntrada buzonEntrada             = new BuzonEntrada();
        BuzonAlertas buzonAlertas             = new BuzonAlertas();
        BuzonClasificacion buzonClasificacion = new BuzonClasificacion(tam1);

        BuzonConsolidacion[] buzonesConsolidacion = new BuzonConsolidacion[ns];
        for (int i = 0; i < ns; i++)
        {
            buzonesConsolidacion[i] = new BuzonConsolidacion(tam2);
        }

        // Crear threads
        Sensor[] sensores = new Sensor[ni];
        for (int i = 0; i < ni; i++)
        {
            sensores[i] = new Sensor(buzonEntrada, i + 1, base, ns);
        }

        Broker broker = new Broker(buzonEntrada, buzonAlertas, buzonClasificacion, totalEventos);

        Administrador administrador = new Administrador(buzonAlertas, buzonClasificacion, nc);

        ContadorClasificadores contador = new ContadorClasificadores(nc);
        Clasificador[] clasificadores = new Clasificador[nc];
        for (int i = 0; i < nc; i++)
        {
            clasificadores[i] = new Clasificador(buzonClasificacion, buzonesConsolidacion, contador, i + 1);
        }

        Servidor[] servidores = new Servidor[ns];
        for (int i = 0; i < ns; i++)
        {
            servidores[i] = new Servidor(buzonesConsolidacion[i], i + 1);
        }

        // Arrancar todos los threads
        for (Servidor s : servidores)         s.start();
        for (Clasificador c : clasificadores) c.start();
        administrador.start();
        broker.start();
        for (Sensor sensor : sensores)        sensor.start();

        // Esperar a que todos terminen
        for (Sensor sensor : sensores)        sensor.join();
        broker.join();
        administrador.join();
        for (Clasificador c : clasificadores) c.join();
        for (Servidor s : servidores)         s.join();

        // Verificación final: todos los buzones deben quedar vacíos
        System.out.println("=== Sistema IoT finalizado ===");
        System.out.println("--- Verificación de buzones ---");
        System.out.println("Buzón entrada vacío:       " + buzonEntrada.estaVacio());
        System.out.println("Buzón alertas vacío:       " + buzonAlertas.estaVacio());
        System.out.println("Buzón clasificación vacío: " + buzonClasificacion.estaVacio());
        for (int i = 0; i < ns; i++)
        {
            System.out.println("Buzón servidor " + (i + 1) + " vacío:    " + buzonesConsolidacion[i].estaVacio());
        }
    }
}
