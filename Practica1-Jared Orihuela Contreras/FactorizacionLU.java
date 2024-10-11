import java.util.Arrays;

public class FactorizacionLU {
    public static void main(String[] args) {
        // Example matrix (3x3)
        double[][] A = {
            {2, 3, 1},
            {4, 7, 7},
            {-2, 4, 5}
        };
        int n = A.length;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];
        if (luFactorization(A, L, U, n)) {
            System.out.println("Matrix A:");
            printMatrix(A);
            System.out.println("Lower Triangular Matrix L:");
            printMatrix(L);
            System.out.println("Upper Triangular Matrix U:");
            printMatrix(U);
        } else {
            System.out.println("Matriz Singular detectada");
        }
    }
    public static boolean luFactorization(double[][] A, double[][] L, double[][] U, int n) {
        for (int i = 0; i < n; i++) {
            L[i][i] = 1.0;
            Arrays.fill(U[i], 0.0);
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                double sum = 0.0;
                for (int k = 0; k < i; k++) {
                    sum += L[i][k] * U[k][j];
                }
                U[i][j] = A[i][j] - sum;
            }
            if (U[i][i] == 0) {
                System.out.println("Pivot cero encontrado en " + i);
                return false;
            }
            for (int j = i + 1; j < n; j++) {
                double sum = 0.0;
                for (int k = 0; k < i; k++) {
                    sum += L[j][k] * U[k][i];
                }
                L[j][i] = (A[j][i] - sum) / U[i][i];
            }
        }

        return true;
    }
    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.printf("%.2f\t", val);
            }
            System.out.println();
        }
        System.out.println();
    }
}
