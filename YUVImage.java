import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class YUVImage {

	private int width;
	private int height;
	private YUVPixel image[][];
	private short Y,U,V;
	private File file;
	
	public YUVImage(int height, int width) {
		this.width = width;
		this.height = height;
		Y = 16;
		U = 128;
		V = 128;
		image = new YUVPixel[height][width];
	}

 	public YUVImage(YUVImage copyImg) { 
 		this.width = copyImg.getWidth();
 		this.height = copyImg.getHeight();
 		this.image = new YUVPixel[height][width];
 		
 		for(int i =0; i < height; i++) {
			for(int j = 0; j < width; j++){
 				this.image[i][j] = copyImg.getPixel(i,j);
			}
		}
 	}

	public YUVImage(RGBImage RGBImg) {  //metatropi apo RGB se YUV
		
		width = RGBImg.getWidth();
		height = RGBImg.getHeight();
		image = new YUVPixel[height][width];
		YUVPixel pixelYUV;
		for (int i = 0 ; i < RGBImg.getHeight(); i++){
			for(int j = 0 ; j < RGBImg.getWidth(); j++){
				pixelYUV =new YUVPixel (RGBImg.getPixel(i,j));
				image[i][j] = pixelYUV;
			}
		}	
	}
	
	public YUVImage(File file){
		try{
			this.file = file;
			
			if (!file.exists()) {
				throw new FileNotFoundException();
			} 
			else {
				try {
					String[] pathPart = file.getAbsolutePath().split("\\.");

					if(!pathPart[1].equals("yuv")) {
						throw new UnsupportedFileFormatException();
					}
					else{
						try(Scanner fileScanned = new Scanner(file)){
	
							String code = fileScanned.next(); 
			
							int columns = Integer.parseInt(fileScanned.next());
							int rows = Integer.parseInt(fileScanned.next());
							width = columns;
							height = rows;
							image = new YUVPixel[rows][columns];

							int i = 0,j = 0;
							short y,u,v;
			
							while(fileScanned.hasNext()){
				
								for(i = 0; i < rows; i++){
				
									for (j = 0 ; j < columns; j++){
					
										y = Short.parseShort(fileScanned.next());
										u = Short.parseShort(fileScanned.next());
										v = Short.parseShort(fileScanned.next());

										YUVPixel currPxl = new YUVPixel(y,u,v);
										image[i][j] = currPxl;
									}
								}
							}
			
							fileScanned.close();
			
						}catch (FileNotFoundException e) {
							JFrame frame1 = new JFrame();
							JLabel msg1 = new JLabel("The file does not exist.");
							frame1.getContentPane().add(msg1,BorderLayout.CENTER);
							frame1.pack();
							frame1.setLocationRelativeTo(null);
							frame1.setVisible(true);
						}
					}
				}
				catch(UnsupportedFileFormatException e) {
					JFrame frame = new JFrame();
					JLabel msg  = new JLabel("This file format cannot be supported. Please choose another file.");
					frame.getContentPane().add(msg,BorderLayout.CENTER);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			
			}
		}catch(FileNotFoundException e) {
			JFrame frame1 = new JFrame();
			JLabel msg1 = new JLabel("The file does not exist.");
			frame1.getContentPane().add(msg1,BorderLayout.CENTER);
			frame1.pack();
			frame1.setLocationRelativeTo(null);
			frame1.setVisible(true);
		}
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public YUVPixel getPixel(int height, int width) {
		return image[height][width];
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setPixel(int row, int col, YUVPixel pixel) {
		image[row][col] = pixel;
	}
	
	public String toString() {
		String str = "";
		byte []byteBuf = new byte[512];
		try(FileInputStream reader = new FileInputStream (file)){
			while(reader.read(byteBuf) != -1) {
				str = str + new String(byteBuf);
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		
		return str;
	}
	
	public void toFile(File file) {
	
		if(file.exists()) {
			file.delete();
		}
		
		int columns = getWidth();
		int rows = getHeight();
		int i,j;
		String yuv = "YUV3\n";
		try {
			PrintWriter outImg = new PrintWriter( new FileWriter(file));
			outImg.write(yuv);
			outImg.write(columns+" ");
			outImg.write(rows+"\n");
			
			
			for(i=0; i<rows; i++) {
				for(j=0; j<columns; j++) {
					outImg.write((int)getPixel(i,j).getY()+"\n");
					outImg.write((int)getPixel(i,j).getU()+"\n");
					outImg.write((int)getPixel(i,j).getV()+"\n");
				}
			}
			outImg.close();
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}
}
