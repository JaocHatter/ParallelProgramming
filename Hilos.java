class MyThread extends Thread{
    @Override
    public void run(){
        System.out.println("Holaaa, soy el hilo #" + Thread.currentThread().getName());
    }
}
class MyRunnable implements Runnable{
    @Override
    public void run(){
        System.out.println("Holaaa, soy el hilo #" + Thread.currentThread().getName());
    }
}


public class Hilos{
    public static void main(String[] args) {
        System.out.println("Hola mundo , de nuevo...");
        //Clase que hereda metodos de Thread
        MyThread t1 = new MyThread(),t2 = new MyThread();
        t1.start();
        t2.start();
        //Clase que hereda metodos de la Interfaz Runnable
        Thread t3 = new Thread(new MyRunnable()), t4 = new Thread(new MyRunnable());
        t3.start();
        t4.start();
    }
}