import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrimeNumberFinder {
    public static void main(String[] args) {
        int lowerBound = 1;
        int upperBound = 100000;
        int numThreads = 4;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Dividir el rango en sub-rangos
        int range = upperBound - lowerBound + 1;
        int chunkSize = range / numThreads;

        List<Future<List<Integer>>> futures = new ArrayList<>();
        long startTimeWithThreads = System.nanoTime();

        // Asignar las tareas
        //TU CODIGO VA ACÁ
        for (int i = 0; i < numThreads; i++) {
            //dividamos las tareas por lotes-hilos
            int start = lowerBound + i * chunkSize;
            int end = (i == numThreads -1) ? upperBound: start + chunkSize -1;
            Future<List<Integer>> BatchPrimes = executor.submit(new PrimeTask(start, end));
            futures.add(BatchPrimes);
        }
        long endTimeWithThreads = System.nanoTime();
        long durationWithThreads = (endTimeWithThreads - startTimeWithThreads) / 1_000_000;
        System.out.println("Tiempo con ExecutorService: " + durationWithThreads + " ms");

        // Recoger y mostrar los resultados
        List<Integer> primeNumbers = new ArrayList<>();
        for (Future<List<Integer>> future : futures) {
            try {
                primeNumbers.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        System.out.println("Números primos encontrados: " + primeNumbers.size());
    }
}

class PrimeTask implements Callable<List<Integer>> {
    private final int start;
    private final int end;

    public PrimeTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public List<Integer> call() {
        List<Integer> primes = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
