import java.util.concurrent.CountDownLatch;

class Tarea implements Runnable {
    private String nombre;
    private CountDownLatch latch;

    public Tarea(String nombre, CountDownLatch latch) {
        this.nombre = nombre;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println("Iniciando tarea: " + nombre);
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("Finalizando tarea: " + nombre);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown(); //Marca que la tarea ha terminado.
        }
    }
}

public class Prob3EPparalela {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latchA = new CountDownLatch(1);
        CountDownLatch latchB = new CountDownLatch(1);
        CountDownLatch latchC = new CountDownLatch(1);
        CountDownLatch latchD = new CountDownLatch(1);
        CountDownLatch latchE = new CountDownLatch(1);
        CountDownLatch latchF = new CountDownLatch(1);
        //Hilos para las tareas paralelas A y B.
        //se ejecutan de manera paralela puesto que ambas no dependen de otra entre ellas
        Thread tareaA = new Thread(new Tarea("A", latchA));
        Thread tareaB = new Thread(new Tarea("B", latchB));

        //proceso C depende de A y B.
        Thread tareaC = new Thread(() -> {
            try {
                latchA.await(); // Espera a que termine A.
                latchB.await(); // Espera a que termine B.
                System.out.println("Iniciando tarea: C");
                Thread.sleep((long)1000);
                System.out.println("Finalizando tarea: C");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latchC.countDown(); // Marca que C ha terminado.
            }
        });

        //proceso D depende de B.
        Thread tareaD = new Thread(() -> {
            try {
                latchB.await(); // Espera a que termine B.
                System.out.println("Iniciando tarea: D");
                Thread.sleep((long)1000);
                System.out.println("Finalizando tarea: D");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latchD.countDown(); // Marca que D ha terminado.
            }
        });

        //E depende de C.
        Thread tareaE = new Thread(() -> {
            try {
                latchC.await(); // Espera a que termine C.
                System.out.println("Iniciando tarea: E");
                Thread.sleep((long)1000);
                System.out.println("Finalizando tarea: E");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latchE.countDown(); // Marca que E ha terminado.
            }
        });

        // Tarea F depende de D y E.
        Thread tareaF = new Thread(() -> {
            try {
                latchD.await(); // Espera a que termine D.
                latchE.await(); // Espera a que termine E.
                System.out.println("Iniciando tarea: F");
                Thread.sleep((long)1000);
                System.out.println("Finalizando tarea: F");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latchF.countDown(); // Marca que F ha terminado.
            }
        });

        // Tarea G depende de E y F.
        Thread tareaG = new Thread(() -> {
            try {
                latchE.await(); // Espera a que termine E.
                latchF.await(); // Espera a que termine F.
                System.out.println("Iniciando tarea: G");
                Thread.sleep((long)1000);
                System.out.println("Finalizando tarea: G");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long ini = System.nanoTime(); 

        //Iniciar todas las tareas
        tareaA.start();
        tareaB.start();
        tareaC.start();
        tareaD.start();
        tareaE.start();
        tareaF.start();
        tareaG.start();
        //Esperar a que finalice la tarea G.
        tareaG.join();
        long fini = System.nanoTime();   

        long duracion = fini - ini;   
        System.out.println("Duracion total del programa: "+(duracion/1_000_000.0)+" milisegundos.");
    }
}
