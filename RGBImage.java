import java.io.*;
import java.nio.*;
import java.lang.*;

public class RGBImage implements Image{

	private int width;
	private int height;
	private int colordepth;
	private RGBPixel image[][];
	
	public RGBImage(){
	}
	
	public RGBImage(int height, int width, int colordepth) {
		this.width = width;
		this.height = height;
		this.colordepth = colordepth;
		image = new RGBPixel[height][width];
		
	}	

	public RGBImage(RGBImage copyImg) {

		this.width = copyImg.getWidth();
		this.height = copyImg.getHeight();
		this.colordepth = copyImg.getColordepth();
		this.image = new RGBPixel[height][width];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				this.image[i][j] = copyImg.getPixel(i,j);
			}
		}
	}
	
	public RGBImage(YUVImage YUVImg){
		
		this.width = YUVImg.getWidth();
		this.height = YUVImg.getHeight();
		colordepth = 255;
		this.image = new RGBPixel[height][width];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				
				this.image[i][j] = new RGBPixel(YUVImg.getPixel(i,j)) ;
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getColordepth() {
		return colordepth;
	}
	
	public RGBPixel getPixel(int height, int width) {
		return image[height][width];
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setColordepth(int colordepth) {
		this.colordepth = colordepth;
	}
	
	public void setPixel(int row, int col, RGBPixel pixel) {
		image[row][col] = pixel;
	}
		
	public void grayscale() {
		int i, j;
		RGBPixel pixel;
		short red, green, blue;
		double gray;
		
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
			
				pixel = getPixel(i, j);
				red = pixel.getRed();
				green = pixel.getGreen();
				blue = pixel.getBlue();
				gray = red * 0.3 + green * 0.59 + blue * 0.11;
				pixel.setPixel(((short)gray<<16|(short)gray<<8|(short)gray));
			}
		}	
	}

	public void doublesize() {
		RGBImage doublesizedImg = new RGBImage(2 * getHeight(), 2 * getWidth(), colordepth);
		int i,j;
		RGBPixel thePixel;
		
		for (i = 0; i < getHeight(); i++) {
			for (j = 0; j < getWidth(); j++) {
			
				thePixel = getPixel(i,j);
			
				doublesizedImg.setPixel(2*i, 2*j, thePixel );
				doublesizedImg.setPixel(2*i+1, 2*j, thePixel );
				doublesizedImg.setPixel(2*i, 2*j+1, thePixel );
				doublesizedImg.setPixel(2*i+1, 2*j+1, thePixel );
				
			}
		}
		width = 2 * getWidth();
		height = 2 * getHeight();
		image = new RGBPixel[height][width];
		for (i = 0; i < getHeight(); i++) {
			for (j = 0; j < getWidth(); j++) {
			
				image[i][j] = doublesizedImg.getPixel(i,j);
			}
		}
	}

	public void halfsize() {
		RGBImage halfsizedImg = new RGBImage(getHeight() / 2, getWidth() / 2, colordepth);
		short red[], green[], blue[];
		int finalRed, finalGreen, finalBlue;	
		int i, j, k=0, l=0, m=0;
		
		red = new short[4];
		green = new short[4];
		blue = new short[4];
		
		for(i=0; i<getHeight()/2; i++,k++) {
			for(j=0; j<getWidth()/2; j++,l++) {
			
				red[0] = getPixel(2*i, 2*j).getRed();
				red[1] = getPixel(2*i+1, 2*j).getRed();
				red[2] = getPixel(2*i, 2*j+1).getRed();
				red[3] = getPixel(2*i+1, 2*j+1).getRed();
				
				green[0] = getPixel(2*i, 2*j).getGreen();
				green[1] = getPixel(2*i+1, 2*j).getGreen();
				green[2] = getPixel(2*i, 2*j+1).getGreen();
				green[3] = getPixel(2*i+1, 2*j+1).getGreen();
				
				blue[0] = getPixel(2*i, 2*j).getBlue();
				blue[1] = getPixel(2*i+1, 2*j).getBlue();
				blue[2] = getPixel(2*i, 2*j+1).getBlue();
				blue[3] = getPixel(2*i+1, 2*j+1).getBlue();
			
				finalRed = (red[0] + red[1] + red[2] + red[3]) / 4;
				finalGreen = (green[0] + green[1] + green[2] + green[3]) / 4;
				finalBlue = (blue[0] + blue[1] + blue[2] + blue[3]) / 4;
			
				RGBPixel pixel = new RGBPixel((short)finalRed, (short)finalGreen,(short) finalBlue);
				
				halfsizedImg.setPixel(i, j,pixel);
			}
		}
		
		width = getWidth() / 2;
		height = getHeight() / 2;
		image = new RGBPixel[height][width];
		
		for (i = 0; i < getHeight(); i++) {
			for (j = 0; j < getWidth(); j++) {
			
				image[i][j] = halfsizedImg.getPixel(i,j);
			}
		}
	}
	
	public void rotateClockwise() {
		RGBImage rotatedImg = new RGBImage(width, height, colordepth);
		int i, j,k ,l;
		
		for(i = 0, l = 0; i < getWidth() ; i++ ,l++) {
			for(j = getHeight() - 1, k = 0; j >= 0 ; j--, k++) {
				rotatedImg.setPixel(l, k, getPixel(j,i));
			}
		}
		
		width = rotatedImg.getWidth();
		height = rotatedImg.getHeight();
		image = new RGBPixel[height][width];
		
		for (i = 0; i < getHeight(); i++) {
			for (j = 0; j < getWidth(); j++) {
			
				image[i][j] = rotatedImg.getPixel(i,j);
			}
		}
	}
}
