import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadsTasks {
    public static void main(String[] args) {
        //Crear n hilos de manera automática
        ExecutorService master = Executors.newFixedThreadPool(4);
        int tasks = 100;
        for(int i = 0; i < tasks ; i++){
            int id = i;
            master.submit(new Runnable(){
                @Override
                public void run(){
                    System.out.println("TaskId: "+ id + " Ejecutada por el hilo: "+Thread.currentThread().getName());
                }
            });
        }
        master.shutdown();
    }
}
