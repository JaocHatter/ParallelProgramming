import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Prob4secuencial {
   // Estas son las imágenes que transformaré
   public static String[] setImages = {"sample1.jpg","sample2.jpeg","sample3.jpeg","sample4.jpg"};
   public static void main(String args[]) throws Exception {
      // Leemos las imágenes
      BufferedImage[] images =  new BufferedImage[4];
      // Bucle para procesar las 4 imágenes secuencialmente
      long inicio = System.currentTimeMillis();
      for(int i=0;i<4;i++){
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
         ImageIO.write(transformedImage, "jpg", new File("imagen_transformada_secuencial"+String.valueOf(i)+".jpg"));
         System.out.println("Transformación "+String.valueOf(i)+" realizada...");
         displayImages(images[i], transformedImage);
      }
      long fin = System.currentTimeMillis();
      long duracion = fin - inicio;
      System.out.println("Tiempo total en Paralelo: " + duracion + " ms");
   }
   
   public static void displayImages(BufferedImage original, BufferedImage transformed) {
      JFrame frame = new JFrame("Imágenes");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 400);
      // Crear etiquetas de imagen para ambas
      JLabel originalLabel = new JLabel(new ImageIcon(original));
      JLabel transformedLabel = new JLabel(new ImageIcon(transformed));
      frame.getContentPane().setLayout(new java.awt.GridLayout(1, 2));
      frame.getContentPane().add(originalLabel);
      frame.getContentPane().add(transformedLabel);
      frame.setVisible(true);
   }
}
