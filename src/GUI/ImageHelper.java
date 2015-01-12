package GUI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;
/**
 * This class helps to change to color of images
 */
public class ImageHelper {
	/**
	 * Instance variables
	 */
	private int colorIndex = 0;
	private final List<Color> colors = new ArrayList<>();
	
	/**
	 * Constructor
	 */
	public ImageHelper() {
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.RED);
		colors.add(Color.YELLOW);
		colors.add(Color.PINK);
		colors.add(Color.ORANGE);
		colors.add(Color.BLACK);
		colors.add(Color.MAGENTA);
		colors.add(Color.LIGHT_GRAY);
		colors.add(Color.CYAN);
	}

	/**
	 * Changes the color of an image
	 * @param image		Images to change
	 * @param color		Color to use
	 * @return image
	 */
	public BufferedImage colorImage(BufferedImage image, Color color) {
		int width = image.getWidth();
		int height = image.getHeight();
		WritableRaster raster = image.getRaster();
		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				if (pixels[0] == 0 && pixels[1] == 12 && pixels[2] == 187) {
					pixels[0] = color.getRed();
					pixels[1] = color.getGreen();
					pixels[2] = color.getBlue();
					raster.setPixel(xx, yy, pixels);
				}
			}
		}
		return image;
	}
	
	/**
	 * Returns the next available color
	 * @return nextColor
	 */
	public Color getNextColor() {
		Color nextColor = colors.get(colorIndex);
		colorIndex++;
		return nextColor;
	}
	
}
