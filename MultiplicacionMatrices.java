import java.util.Random;

public class MultiplicacionMatrices {
    private static final int rows1 = 20, rows2 = 10;
    private static final int cols = 15;
    private static int matrixA[][] = new int[rows1][cols];
    private static int matrixB[][] = new int[cols][rows2];
    private static int matrixC[][] = new int[rows1][rows2];

    private static Random RND = new Random();
    // Este proceso es paralelizable
    public static void DotProduct(int i, int j){
        matrixC[i][j] = 0;
        for(int iter = 0; iter < cols; iter++){
            matrixC[i][j] += matrixA[i][iter] + matrixB[iter][j];
        }
    }

    public static void LoadData(int MTX[][], int AA, int BB, int a, int b) {
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                MTX[i][j] = RND.nextInt(AA, BB + 1);
            }
        }
    }

    // Método para imprimir matrices
    public static void PrintMatrix(int MTX[][], int rows, int cols, String name) {
        System.out.println(name + ":");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(MTX[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void main(String[] args) throws InterruptedException{
        // Iniciamos la multiplicacion de matrices
        LoadData(matrixA, -10, 10, rows1, cols); 
        LoadData(matrixB, -10, 10, cols, rows2);

        PrintMatrix(matrixA, rows1, cols, "Matriz A");
        PrintMatrix(matrixB, cols, rows2, "Matriz B");

        for(int x = 0; x < rows1; x++){
            for(int y = 0; y < rows2; y++){
                final int a = x;
                final int b = y;
                //Distribuimos la tarea en hilos
                new Thread(()->{
                    DotProduct(a, b);
                }).start(); 
            }
        }
        Thread.sleep(2000);

        // Imprimir el resultado (no garantizado debido a la falta de sincronización)
        PrintMatrix(matrixC, rows1, rows2, "Matriz C (Resultado)");
    }
}
