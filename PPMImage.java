import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class PPMImage extends RGBImage{
	private File file;
	private RGBImage rgbImg;

	public PPMImage(){
	}
	
	public PPMImage(File file) {
		try{
			this.file = file;
			if (!file.exists()) {
				throw new FileNotFoundException();
			} 
			else {
				try {
					String[] pathPart = file.getAbsolutePath().split("\\.");
				
					if(!pathPart[1].equals("ppm")) {
						
						throw new UnsupportedFileFormatException();
					}
					else {
						ImageRead help = new ImageRead();
						rgbImg = new RGBImage(ImageRead.readimage(file.getAbsolutePath()));
					}
				} 
				catch (UnsupportedFileFormatException e) {
					JFrame frame = new JFrame();
					JLabel msg  = new JLabel("This file format cannot be supported. Please choose another file.");
					frame.getContentPane().add(msg,BorderLayout.CENTER);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			}
		}
		catch(FileNotFoundException e) {
			JFrame frame1 = new JFrame();
			JLabel msg1 = new JLabel("The file does not exist.");
			frame1.getContentPane().add(msg1,BorderLayout.CENTER);
			frame1.pack();
			frame1.setLocationRelativeTo(null);
			frame1.setVisible(true);
				
		}
	}
	
	public PPMImage(RGBImage img) {
		rgbImg = new RGBImage(img);
	}
	
	public File getFile() {
		return file;
	}
	
	public RGBImage getImg() {
		return rgbImg;
	}
	
	public String toString() {
		
		String str = "";
		byte []byteBuf = new byte[512];
		try(FileInputStream reader = new FileInputStream (file)){
			while(reader.read(byteBuf) != -1) {
				str = str + new String(byteBuf);
			}
			reader.close();
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
		
		int columns = getImg().getWidth();
		int rows = getImg().getHeight();
		int colorDepth = getImg().getColordepth();
		int i,j;
		String p3 = "P3\n";
		try {
			PrintWriter outImg = new PrintWriter( new FileWriter(file));
			outImg.write(p3);
			outImg.write(columns+" ");
			outImg.write(rows+"\n");
			outImg.write(colorDepth+"\n");
			
			for(i=0; i<rows; i++) {
				for(j=0; j<columns; j++) {
					outImg.write((int)getImg().getPixel(i,j).getRed()+"\n");
					outImg.write((int)getImg().getPixel(i,j).getGreen()+"\n");
					outImg.write((int)getImg().getPixel(i,j).getBlue()+"\n");
				}
			}
			outImg.close();
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}	
}
