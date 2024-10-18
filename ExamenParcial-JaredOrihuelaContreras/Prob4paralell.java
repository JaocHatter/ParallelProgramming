import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Prob4paralell {
    public static String[] setImages = {"sample1.jpg","sample2.jpeg","sample3.jpeg","sample4.jpg"};
    public static BufferedImage[] images =  new BufferedImage[4];

    public static void main(String args[]) throws Exception {
        // Leemos las imágenes
        // Bucle para procesar las 4 imágenes secuencialmente
        Thread[] multithread = new Thread[4];
        long inicio = System.currentTimeMillis();

        for(int i=0;i<4;i++){
            final int k = i;
            multithread[i] = new Thread(()->{
                try {
                    RGB2GRAY(k);      

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            multithread[i].start();
        }
        int it = 0;
        // Ahora esperemos que todos los hilos hagan su trabajo
        for(Thread x: multithread){
            System.out.println("Transformación "+String.valueOf(it)+" realizada...");
            x.join();
        }

        long fin = System.currentTimeMillis();
        long duracion = fin - inicio;
        // Imprimir el tiempo total de ejecución
        System.out.println("Tiempo total en Paralelo: " + duracion + " ms");
        }

        //Esta función será ejecutada por cada hilo
        public static void RGB2GRAY(int i) throws IOException{
            images[i] = ImageIO.read(new File(setImages[i]));
            // Dimensiones de la imagen
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            
            // Crear una imagen nueva para la imagen transformada
            BufferedImage transformedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
            // Recorrer cada píxel y transformar los valores RGB
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                // Obtener el valor RGB del píxel
                int pixel = images[i].getRGB(x, y);
                
                // Extraer los componentes RGB
                Color color = new Color(pixel);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                // En esta ocasión nuestro programa se encargará de convertir las imágenes de Blanco a Negro
                int average = (r + g + b) / 3;
                r = g = b = average; // Ahora todos los valores son el mismo (escala de grises)               
                Color newColor = new Color(r, g, b);
                transformedImage.setRGB(x, y, newColor.getRGB());
                }
            }
            // Guardar la imagen transformada
            ImageIO.write(transformedImage, "jpg", new File("imagen_transformada"+String.valueOf(i)+".jpg")); 
    }

    // Función para mostrar las imágenes en un JFrame
    public static void displayImages(BufferedImage original, BufferedImage transformed) {
        JFrame frame = new JFrame("Imágenes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        JLabel originalLabel = new JLabel(new ImageIcon(original));
        JLabel transformedLabel = new JLabel(new ImageIcon(transformed));      
        frame.getContentPane().setLayout(new java.awt.GridLayout(1, 2));
        frame.getContentPane().add(originalLabel);
        frame.getContentPane().add(transformedLabel);
        frame.setVisible(true);
    }
    }
