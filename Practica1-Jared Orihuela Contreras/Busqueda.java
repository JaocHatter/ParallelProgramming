import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Busqueda{
    public static int key;
    public static int[] arr;
    public static void serialSearch() {
        System.out.println("Búsqueda Secuencial Serial");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                System.out.println("Key encontrado en posición " + i);
            }
        }
        System.out.println();
    }

    public static void serialSearch_por_Batch(int a,int b){
        for(int i=a;i<b;i++){
            if(arr[i] == key){
                System.out.println("Key encontrado en posición "+ String.valueOf(i));
            }
        }
    }

    public static void parallelSearch(int len) throws InterruptedException {
        System.out.println("Búsqueda Secuencial Paralela");
        int n = arr.length;
        int batches = (n%len==0)? n/len: n/len+1;

        Thread[] hilos = new Thread[batches];
        for (int i = 0; i < batches; i++) {
            final int st = i * len; // inicio
            final int end = ((i + 1) * len > n) ? n : (i + 1) * len; // En caso me exceda!
            hilos[i] = new Thread(() -> {
                serialSearch_por_Batch(st, end);
            });
            hilos[i].start();
        }

        // Esperar a que todos los hilos terminen
        for (Thread x : hilos) {
            x.join();
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        String fileName = "DATOS.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            //Leer número de elementos
            String line = br.readLine();
            int n = Integer.parseInt(line.trim());
            //Leer elementos del array
            line = br.readLine();
            String[] elements = line.trim().split("\\s+");
            arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(elements[i]);
            }
            //En esta linea debe estar la key
            line = br.readLine();
            key = Integer.parseInt(line.trim());

            //Leer el tamaño del batch para búsqueda paralela
            line = br.readLine();
            int len = Integer.parseInt(line.trim());

            // Mostrar datos leídos
            System.out.println("=== Datos Cargados desde DATOS.TXT ===");
            System.out.println("Número de elementos (n): " + n);
            System.out.print("Array: ");
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println("\nKey a buscar: " + key);
            System.out.println("Tamaño del batch para paralelismo: " + len);
            System.out.println("======================================\n");

            serialSearch();
            parallelSearch(len);
        } catch (IOException e) {
            System.err.println("Error al abrir archivo: " + e.getMessage());
        }
    }
}
