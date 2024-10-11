class Contador{
    private int count = 500;
    //añadir synchronized para evitar race conditions
    public synchronized void decrement(){
        count -= 1;
    }
    public int returnCuenta(){
        return count;
    }
}

class Retirar implements Runnable{
    private Contador counter_;
    private int quant;
    public Retirar(Contador counter, int monto){
        counter_ = counter;
        quant = monto;
    }
    @Override
    public void run(){
        for(int i=0;i<quant;i++){
            counter_.decrement();
        }
    }
}

public class SincronizacionHilos {
    public static void main(String[] args) throws InterruptedException {
        Contador counter = new Contador();
        // Creemos nuestros procesos
        Thread t1 = new Thread(new Retirar(counter, 30)), t2 = new Thread(new Retirar(counter, 50)), t3 = new Thread(new Retirar(counter, 100));
        // El contador debe quedar en 320

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("Nos sobra : " + String.valueOf(counter.returnCuenta()));
    }
}
