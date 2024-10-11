import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskTimingComparison {
    public static void main(String[] args) {
        int tasks = 100;
        
        // Medir tiempo con ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(16);
        long startTimeWithThreads = System.nanoTime();
        
        for(int i = 0; i < tasks ; i++){
            int id = i;
            executor.submit(new Runnable(){
                @Override
                public void run(){
                    // Simula una tarea que toma algo de tiempo
                    doWork(id);
                }
            });
        }
        
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Esperar a que todas las tareas terminen
        }
        
        long endTimeWithThreads = System.nanoTime();
        long durationWithThreads = (endTimeWithThreads - startTimeWithThreads) / 1_000_000;
        System.out.println("Tiempo con ExecutorService: " + durationWithThreads + " ms");
        
        // Medir tiempo sin hilos
        long startTimeWithoutThreads = System.nanoTime();
        
        for(int i = 0; i < tasks ; i++){
            int id = i;
            // Ejecutar la misma tarea sin hilos
            doWork(id);
        }
        
        long endTimeWithoutThreads = System.nanoTime();
        long durationWithoutThreads = (endTimeWithoutThreads - startTimeWithoutThreads) / 1_000_000;
        System.out.println("Tiempo sin hilos: " + durationWithoutThreads + " ms");
    }
    
    private static void doWork(int id) {
        try {
            Thread.sleep(10); // Simula una tarea que toma tiempo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
