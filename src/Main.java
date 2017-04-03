import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		// le fichier contient pour chaque ligne x1 y1 x2 y2
		// première ligne = fenêtre initale
		Scanner lecteur = new Scanner(new File("scenes/1000.txt"));
		lecteur.useLocale(Locale.US);

		// System.out.println(lecteur.hasNextFloat());
		// System.out.println(lecteur.nextFloat());
		// System.out.println((int)Float.parseFloat(lecteur.next()));

		// première ligne
		Tuple fwindow = new Tuple(
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat()
		);

		// autres lignes
		ArrayList<Tuple> segments = new ArrayList<Tuple>();
		int i = 0;
		while (lecteur.hasNextFloat()) {
			int x1 = (int) lecteur.nextFloat();
			int y1 = (int) lecteur.nextFloat();
			int x2 = (int) lecteur.nextFloat();
			int y2 = (int) lecteur.nextFloat();
			segments.add(new Tuple(x1, x2, y1, y2));
		}
		// faire un quicksort - peut être plus rapide que sort de base
		segments.sort(Tuple::compareTo);
		/*
		 * for (int j=0 ; j<segments.size();j++) {
		 * System.out.print(segments.get(j).getY1()); System.out.print(" ");
		 * System.out.println(segments.get(j).getY2()); }
		 */
		lecteur.close();
    }
}
