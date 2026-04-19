    package Implementación;

    import java.util.Random;

    public class ServidorEntrega extends Thread
    {
        private final BuzonConsolidacion buzon;
        private final int id;
        private final Random random = new Random();

        public ServidorEntrega(BuzonConsolidacion buzon, int id)
        {
            this.buzon = buzon;
            this.id = id;
        }

        @Override
        public void run()
        {
            try
            {
                while (true)
                {
                    Mensaje m = buzon.retirar();

                    if (m.esFinal())
                    {
                        System.out.println("Servidor " + id + ": recibió FIN, terminando.");
                        return;
                    }

                    int tiempoProcesamiento = 100 + random.nextInt(901);
                    System.out.println("Servidor " + id + ": procesando " + m + " (" + tiempoProcesamiento + "ms)");
                    Thread.sleep(tiempoProcesamiento);
                    System.out.println("Servidor " + id + ": terminó de procesar " + m);
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
