import java.util.Random;

public class MultiplicacionMatrixPractica{
    public static Random rand = new Random();
    public static int x = 3,y = 2, z = 4;
    public static double[][] matrixA = new double[x][y], matrixB = new double[y][z], matrixC = new double[x][z];

    public static void rellenar(double[][] mat, int h, int w, Random rand){
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                mat[i][j] = rand.nextInt(11)-5;
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        
        rellenar(matrixA, x, y, rand);
        rellenar(matrixB, y, z, rand);

        // Almacenar los hilos para luego esperar con join()
        Thread[] hilos = new Thread[x * z];
        int indice = 0;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < z; j++) {
                final int a = i;
                final int b = j;
                hilos[indice] = new Thread(() -> dotProd(a, b));
                hilos[indice].start();
                indice++;
            }
        }

        // Esperar a que todos los hilos terminen
        for (Thread hilo : hilos) {
            hilo.join();
        }

        // Imprimir matriz
        imprimir(matrixC, x, z);
    }

    public static synchronized void dotProd(int x_, int y_) {
        matrixC[x_][y_] = 0;
        for (int k = 0; k < y; k++) {  // La longitud de iteración debe ser 'y'
            matrixC[x_][y_] += matrixA[x_][k] * matrixB[k][y_];
        }
    }

    public static void imprimir(double[][] mat, int l1, int l2){
        for(int i=0;i<l1;i++){
            for(int j=0;j<l2;j++){
                System.out.print(mat[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
