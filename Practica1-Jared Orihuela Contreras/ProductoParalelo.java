import java.util.Random;
import java.util.Scanner;

class ProductoParalelo{
    public static int n,potencia;
    public static double[][] matrixA, n_esima, aux;
    public static Random rand = new Random();

    public static void rellenar(){
        // Nuestras matrices serán de 0's y 1's
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                matrixA[i][j] = rand.nextInt(2);
            }
        }
    }
    public static void imprimir(double[][] mat){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(mat[i][j]+"\t");
            }
            System.out.println(); // mi salto de linea
        }
    }

    //Algoritmo que se va a paralelizar
    public static synchronized void dotProd(int a,int b){
        aux[a][b] = 0;
        for(int i=0;i<n;i++){
            aux[a][b] += matrixA[a][i] * n_esima[i][b];
        }
    }
    public static void main(String[] args) throws InterruptedException{
        Scanner scan = new Scanner(System.in);

        System.out.println("Ingresar dimensión de la matriz:");
        n = scan.nextInt();
        System.out.println("Ingresar potencia");
        potencia = scan.nextInt();
        
        System.out.println("Ingrese dimension de matriz: ");
        matrixA = new double[n][n];
        n_esima = new double[n][n];
        aux = new double[n][n];

        //relleno mi matriz "matrizA" (esta se usará para los calculos paralelos)
        rellenar();
        n_esima = matrixA;
        System.out.println("Matriz A:");
        imprimir(matrixA);
        System.out.println("Matriz A^N");
        
        // Auxiliares
        long[][] times = new long[potencia][n*n];
        //ALGORITMO PARALELO
        //Si potencia - 1 == 1 
        for(int k = 0; k < potencia-1; k++){
            // Con cada iteración generar nuevos hilos
            // Lista de threads
            Thread[] my_threads = new Thread[n*n];
            int idx = 0;
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    final int a = i;
                    final int b = j;
                    my_threads[idx] = new Thread(()->{dotProd(a, b);});
                    //Calculo el tiempo
                    long startTimeWithThreads = System.nanoTime();
                    my_threads[idx].start();
                    long endTimeWithThreads = System.nanoTime();
                    long durationWithThreads = (endTimeWithThreads - startTimeWithThreads);
                    times[k][idx] = durationWithThreads;
                    idx+=1;
                }
            }
            //Hacemos que cada hilo espere que termine el otro y así evitamos la asincronia
            for(Thread x: my_threads){
                x.join();
            }
            //Ahora igualamos a nuestra matriz Auxiliar con la matriz n_esima
            n_esima = aux;
        }

        imprimir(n_esima);
        System.out.println("ESTADISTICAS EN PARALELO:");
        long media = 0;
        for(int i=0; i<potencia; i++){
                for(int k=0;k<n*n;k++){
                    media += times[i][k];
                }
        }
        System.out.println("Media de tiempo en paralelo (ms):" + (double)((media/(potencia*n*n)))/1_000_000);
        System.out.println("Total de tiempo (ms): " + (double)(media)/1_000_000);
        scan.close();

        System.out.println("=====================================");
        System.out.println("AHORA EN TIEMPO SERIAL!");
        double[][] serialA = new double[n][n];
        double[][] serialB = new double[n][n];
        double[][] serialC = new double[n][n];
        copiarMatriz(matrixA, serialA);
        copiarMatriz(matrixA, serialB);

        System.out.println("Matriz A:");
        imprimir(serialB);
        System.out.println("Matriz A^N");

        long startTimeWithThreads = System.nanoTime();
        for(int k = 0; k < potencia-1; k++){
            serialMultiplicacion(serialB, serialA, serialC);
            copiarMatriz(serialC, serialB);
        }
        long endTimeWithThreads = System.nanoTime();
        long durationWithThreads = (endTimeWithThreads - startTimeWithThreads);

        imprimir(serialB);
        System.out.println("ESTADISTICAS EN SERIAL:");
        System.out.println("Tiempo total (ms): " + (double)(durationWithThreads));
    }

    //Lo hago de esta manera por problemas de referencia
    public static void serialMultiplicacion(double[][] A, double[][] B, double[][] C) {
        for(int i=0; i<n;i++) {
            for(int j=0;j<n;j++) {
                C[i][j] = 0; 
                for(int k=0;k<n;k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }
    
    public static void copiarMatriz(double[][] fuente, double[][] destino) {
        for (int i=0; i<n;i++) {
            for (int j=0; j<n;j++) {
                destino[i][j] = fuente[i][j];
            }
        }
    }
}