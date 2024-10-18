public class Prob3EPsecuencial {
    public static void main(String[] args) {
        long inicio = System.nanoTime(); 
        medirTiempoTarea("A", Prob3EPsecuencial::tareaA);
        medirTiempoTarea("B", Prob3EPsecuencial::tareaB);
        medirTiempoTarea("C", Prob3EPsecuencial::tareaC);
        medirTiempoTarea("D", Prob3EPsecuencial::tareaD);
        medirTiempoTarea("E", Prob3EPsecuencial::tareaE);
        medirTiempoTarea("F", Prob3EPsecuencial::tareaF);
        medirTiempoTarea("G", Prob3EPsecuencial::tareaG);
        long fin = System.nanoTime();   
        long duracion = fin - inicio;   
        System.out.println("Duracion total del programa: "+(duracion/1_000_000.0)+" milisegundos.");
    }
    public static void medirTiempoTarea(String nombreTarea, Runnable tarea) {
        long inicio = System.nanoTime(); 
        tarea.run();                     
        long fin = System.nanoTime();   
        long duracion = fin - inicio;   
        System.out.println("Tarea "+nombreTarea+" tomó "+(duracion/1_000_000.0)+" milisegundos.");
    }

    // Definición de las tareas
    public static void tareaA() {
        System.out.println("Ejecutando tarea A");
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public static void tareaB() {
        System.out.println("Ejecutando tarea B");
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public static void tareaC() {
        System.out.println("Ejecutando tarea C");
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public static void tareaD() {
        System.out.println("Ejecutando tarea D");
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public static void tareaE() {
        System.out.println("Ejecutando tarea E");
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public static void tareaF() {
        System.out.println("Ejecutando tarea F");
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public static void tareaG() {
        System.out.println("Ejecutando tarea G");
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
