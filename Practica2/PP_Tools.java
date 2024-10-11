import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class PP_Tools {
//--------------------------------------------------------
public PP_Tools() {
}
//--------------------------------------------------------
public String Replicate(char E, int N) {
int L;
String CAD;
    L = 0;
    CAD = "";
    for(int i=0;i<=N-1;i++) {
    	CAD = CAD + E;
    }
    return CAD;
}
//--------------------------------------------------------
public void PrintMatrixToScreen(int MTX[][], String RES[], String TITLE) {
int N = MTX.length;
	System.out.println(TITLE);
	for(int i=0;i<=N-1;i++) {
		for(int j=0;j<=N-1;j++) {
			System.out.print(MTX[i][j] + "\t");
		}
		System.out.print(" | " + RES[i] + "\n");
	}
	System.out.println();
}
//--------------------------------------------------------
public void PrintMatrixToFile(int MTX[][], String TITLE, String FILENAME) {
	int N = MTX.length;
	try {
		FileWriter fw = new FileWriter(FILENAME);
		fw.write(TITLE);
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				fw.write(MTX[i][j] + "\t");
			}
			fw.write("\n");
		}
		fw.write("\n");
		fw.close();
	}
	catch (IOException E) {
	    System.out.print(E.getMessage());
	}
}
//--------------------------------------------------------
public void PrintMatrixToHTML(int MTX[][], String TITLE, String FILENAME) {
int N = MTX.length;
	try {
	    FileWriter FW = new FileWriter(FILENAME);
		FW.write("<H1>" + TITLE + "</H1>");
		FW.write("<HTML>");
		FW.write("<HEAD>");
		FW.write("</HEAD>");
		FW.write("<BODY>");
		FW.write("<TABLE BORDER=1 CELLPADDING=7  CELLSPACING=0  BGCOLOR=#AED6F1>"); //#A3E4D7
		for(int i=0;i<=N-1;i++) {
		    FW.write("<TR>");
			for(int j=0;j<=N-1;j++) {
				if(i==j) {
				   FW.write("<TD BGCOLOR=#FADBD8>");
				}
				else {
				   FW.write("<TD>");
				}
			    FW.write(MTX[i][j] + "</TD>");
			}
		    FW.write("</TR>");
		}

		FW.write("</TABLE>");
		FW.write("</BODY>");
		FW.write("</HTML>");

	    FW.close();
	}
	catch (IOException E) {
	    System.out.print(E.getMessage());
	}
}
//--------------------------------------------------------
// METODOS DE LECTURA/ESCRITURA EN DISCO 
//--------------------------------------------------------
public void WriteData1ToFile(int N, int A, int B, String FILENAME, char SEPARATOR, int LONGITUD) {
double X;
long num;
String ITEM;
    try {
        FileWriter FW = new FileWriter(FILENAME);
        for(int i=1;i<=N;i++) {
            X = A + (int)(Math.random()*(B-A+1));
            num = (long)X;
            ITEM = "" + num + "";
            if(SEPARATOR=='?') {
               if(LONGITUD>0) {
               	  ITEM = Replicate(' ',LONGITUD - ITEM.length()) + ITEM;
               }
               else {
               	  ITEM = ITEM + "\n";
               }
            }
            else {
               if(LONGITUD>0) {
               	  ITEM = Replicate(' ',LONGITUD - ITEM.length()) + ITEM + (i<N?SEPARATOR:"");
               }
               else {
               	  ITEM = ITEM + (i<N?SEPARATOR:"");
               }
            }
            FW.write(ITEM);
        }
        FW.close();
    }
    catch (IOException E) {
        System.out.print(E.getMessage());
    }
}
//--------------------------------------------------------
public void WriteData2ToFile(int M, int N, int A, int B, String FILENAME) {
double X;
long num;
    try {
        FileWriter FW = new FileWriter(FILENAME);
        for(int i=1;i<=M;i++) {
	        for(int j=1;j<=M;j++) {
	            X = A + (int)(Math.random()*(B-A+1));
	            num = (long)X;
	            FW.write(num + "\t");
	        }
            FW.write("\n");
        }
        FW.close();
    }
    catch (IOException E) {
        System.out.print(E.getMessage());
    }
}
//--------------------------------------------------------
public static String ReadDataFromFile(String FILENAME) {
String LINE="",CADENA="";
   try {
     File FILE = new File(FILENAME);
     BufferedReader BR = new BufferedReader(new FileReader(FILE));
     while ((LINE = BR.readLine()) != null) {
       CADENA = CADENA + LINE;
     }
   }
   catch(IOException e) {
   }
   return CADENA;
}
//--------------------------------------------------------
//--------------------------------------------------------
//--------------------------------------------------------
//--------------------------------------------------------
} //class