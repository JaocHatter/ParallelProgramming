class SaldoBancario{
    private int saldo; 
    public SaldoBancario(int saldo){
        this.saldo = saldo;
    }
    public synchronized void gastar(int k){ // debe ser syncronized
        this.saldo -= k;
    }
    public int retornarSaldo(){
        return this.saldo;
    }
}

class Compras implements Runnable{
    private SaldoBancario saldo;
    public Compras(SaldoBancario saldo){
        this.saldo = saldo;
    }
    @Override
    public void run(){
        for(int i=1;i<6;i++){
            this.saldo.gastar(i);
        }
    }
}

public class CuentaBancaria2 {
    public static void main(String[] args) throws InterruptedException {
        SaldoBancario saldo = new SaldoBancario(500);
        Thread hilo1 = new Thread(new Compras(saldo)), hilo2 = new Thread(new Compras(saldo)),hilo3 = new Thread(new Compras(saldo));
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo1.join();
        hilo2.join();
        hilo3.join();
        System.out.println("Saldo final: " + String.valueOf(saldo.retornarSaldo()));
    }
}
