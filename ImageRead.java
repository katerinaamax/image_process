import java.io.*;
import java.nio.*;
import java.lang.*;
import java.util.*;

public class ImageRead {

	public ImageRead(){}

	public static RGBImage readimage(String path){
		
		try(Scanner file = new Scanner(new FileReader(path))){
	
			String code = file.next(); 
			String help = null;
			String helpChar = null;
			
			short columns = Short.parseShort(file.next());
			short rows = Short.parseShort(file.next());
			short colorDepth = Short.parseShort(file.next());

			RGBImage myImage = new RGBImage(rows, columns, colorDepth);

			int i = 0,j = 0;
			short red,green,blue;
			
			while(file.hasNext()){
				
				for(i = 0; i < rows; i++){
				
					for (j = 0 ; j < columns; j++){
					
						red = Short.parseShort(file.next());
						green = Short.parseShort(file.next());
						blue = Short.parseShort(file.next());

						RGBPixel currPxl = new RGBPixel(red, green, blue);
						myImage.setPixel(i,j, currPxl);
					}
				}
			}
			
			file.close();
			return myImage;	
		}
			
		catch(IOException e) {
			System.out.println(e);
		}
		return null;
		
	}	
}
