//Nuestra estrategia será separar la secuencia en batches

import java.util.Scanner;

public class BusquedaSecuencial {
    public static int key;
    public static int[] arr;
    public static void serialSearch(int a,int b){
        for(int i=a;i<b;i++){
            if(arr[i] == key){
                System.out.println("Key encontrado en posición "+ String.valueOf(i));
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        int n;
        System.out.println("Cuantos elementos deseas agregar?");
        n = scan.nextInt();
        System.out.println("Ingresa los elementos que desees");
        arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = scan.nextInt();
        }        
        System.out.println("Que elemento estas buscando?");
        key = scan.nextInt();

        //ALGORITMO
        //Eligiré k batches
        System.out.println("Longitud de los batches para dividir tu array:");
        int len = scan.nextInt();
        int batches = (n % len == 0)? n/len: n/len + 1;

        //Instanciando mis hilos a usar
        Thread[] hilos = new Thread[batches];

        for(int i=0;i<batches;i++){
            final int st = i * len; // inicio
            final int end = ((i+1)*len > n)? n:(i+1)*len; //En caso me exceda!
            hilos[i] = new Thread(()->{
                serialSearch(st, end);
            });
            hilos[i].start();
        }
        for(Thread x: hilos){
            x.join();
        }
        scan.close();
    }
}
