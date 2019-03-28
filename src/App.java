import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Insert your photo directory");
		String imageDir = in.nextLine();
		System.out.println("Insert the directory where the text will be saved");
		String textDir = in.nextLine();

		new Importer(imageDir, textDir);
	}

}
