import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Importer {

	private static final Integer PIXEL = 1;

	private BufferedImage image;
	private char[][] matrix;

	public Importer(String dir, String to) {
		try {
			// the line that reads the image file
			image = ImageIO.read(new File(dir));
			matrix = new char[image.getWidth()][image.getHeight()];

			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					BufferedImage subImage = image.getSubimage(i, j, PIXEL, PIXEL);

					Color color = new Color(subImage.getRGB(subImage.getMinX(), subImage.getMinTileY()), true);
					Integer grays = (color.getRed() + color.getBlue() + color.getGreen()) / 3;

					matrix = grayToChar(grays, matrix, i, j);
				}
			}
			saveMatrixToFile(matrix, to);
		} catch (IOException e) {
			// log the exception
			// re-throw if desired
		}
	}

	public Importer(Integer n, Integer m, String dir, String to) {// matriz NxM

		try {
			// the line that reads the image file
			image = ImageIO.read(new File(dir));
			matrix = new char[n][m];

			Integer metaBlockX = image.getWidth() / n;
			Integer metaBlockY = image.getHeight() / m;

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					BufferedImage subImage = image.getSubimage(i * metaBlockX, j * metaBlockY, metaBlockX, metaBlockY);

					Color color = new Color(subImage.getRGB(subImage.getMinX(), subImage.getMinTileY()), true);
					Integer grays = (color.getRed() + color.getBlue() + color.getGreen()) / 3;

					matrix = grayToChar(grays, matrix, i, j);
				}
			}
			saveMatrixToFile(matrix, to);
		} catch (IOException e) {
			// log the exception
			// re-throw if desired
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public char[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(char[][] matrix) {
		this.matrix = matrix;
	}

	private char[][] grayToChar(int grays, char[][] matrix, int i, int j) {
		if (isBetween(grays, 0, 24)) {
			matrix[i][j] = '@';

		} else if (isBetween(grays, 25, 49)) {
			matrix[i][j] = '%';

		} else if (isBetween(grays, 50, 74)) {
			matrix[i][j] = '#';

		} else if (isBetween(grays, 75, 99)) {
			matrix[i][j] = '*';

		} else if (isBetween(grays, 100, 124)) {
			matrix[i][j] = '+';

		} else if (isBetween(grays, 125, 149)) {
			matrix[i][j] = '=';

		} else if (isBetween(grays, 150, 174)) {
			matrix[i][j] = '-';

		} else if (isBetween(grays, 175, 199)) {
			matrix[i][j] = ':';

		} else if (isBetween(grays, 200, 224)) {
			matrix[i][j] = '·';

		} else if (isBetween(grays, 225, 255)) {
			matrix[i][j] = ' ';

		} else {
			matrix[i][j] = 'E';
		}
		return matrix;
	}

	private boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}

	private void printMatrix(char[][] matrix) throws IOException {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[j][i]);
			}
			System.out.println();
		}
	}

	private void saveMatrixToFile(char[][] matrix, String to) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(to));

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				writer.write(matrix[j][i]);
			}
			writer.write('\n');
		}
		writer.close();
	}

}