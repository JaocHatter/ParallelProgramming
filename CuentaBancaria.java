class cuenta{
    private int saldo = 0;
    public synchronized void depositar(int monto){
        saldo += monto;
        System.out.println(Thread.currentThread().getName() + " depositó: " + monto + " | Saldo actual: " + saldo);
    }
    public synchronized void retirar(int monto){
        saldo -= monto;
        System.out.println(Thread.currentThread().getName() + " retiró: " + monto + " | Saldo actual: " + saldo);
    }
    public void saldo(){
        System.out.println(saldo);
    }
}
class Cliente implements Runnable{
    private cuenta cliente;
    public Cliente(cuenta cliente){
        this.cliente = cliente;
    }
    @Override
    public void run(){
        for(int i=0;i<5;i++){
            cliente.depositar(i);
        }
    }
}
public class CuentaBancaria{
    public static void main(String[] args) throws InterruptedException {
        cuenta myCuenta = new cuenta();
        Thread pepito = new Thread(new Cliente(myCuenta)), juanito = new Thread(new Cliente(myCuenta)), sexito = new Thread(new Cliente(myCuenta));

        pepito.start();
        pepito.join();

        juanito.start();
        juanito.join();

        sexito.start();

        sexito.join();

        myCuenta.saldo();
    }
}