
import java.io.*;

public class Histogram {
	YUVImage myImg;
	int [] histogram_array;
	short[] newLuminocity;
	int maxIntensity;
	
	public Histogram(YUVImage img) {
		myImg = img;
		maxIntensity = 0;
		
// 		Ypologismos ths megisths fwteinothtas
		for(int i = 0; i < myImg.getHeight();i++){
			for(int j=0; j < myImg.getWidth(); j++) {
				if(myImg.getPixel(i,j).getY() >= maxIntensity) {
					maxIntensity = myImg.getPixel(i,j).getY();
				}
			}
		}
		
		histogram_array = new int[maxIntensity];
		
		for(int k = 0; k < maxIntensity; k++) {
			histogram_array[k] = 0;
		}
		
		int pixels1 = 0;
		for(int i = 0; i < myImg.getHeight(); i++) {
			for(int j = 0; j < myImg.getWidth(); j++) {
				pixels1++;
				histogram_array[myImg.getPixel(i,j).getY()-1]++;
			}
		}
	}
	
	
	public String toString(){
	
		String str = "";
		
		for (int i = 0; i < histogram_array.length; i++){
			
			str = str + histogram_array[i];
			for(int j = 0; j < histogram_array[i] && j < 80; j++){
				str = str + "*";
			}
			
			str = str + "\n";
		}
		return str;
	}
	
	public void toFile(File file) {
		byte []buff = new byte [toString().length()];
		
		try(FileOutputStream outFile = new FileOutputStream(file)) {
			buff = toString().getBytes();
			outFile.write(buff);
			outFile.close();
		} 
		catch( IOException e) {
			System.out.println(e);
		}
	}
	
	public void equalize(){
		
		double [] probability = new double[maxIntensity];
		double sum = 0;
		double [] cumulativeProb = new double[maxIntensity];
		newLuminocity = new short[maxIntensity];
		int sumOfPixels = myImg.getHeight() * myImg.getWidth();
		
		for(int i = 0; i < maxIntensity; i++){ //ypologismos ths pithanothtas gia thn kathe timh fwteinothtas
			probability[i] = (double)histogram_array[i] / (double)sumOfPixels;
		}
		
		for(int i = 0 ; i < maxIntensity; i++){ // ypologismos athroistikhs pithanothtas
			sum = sum + probability[i];
			cumulativeProb[i] = probability[i] + sum;
		}
		
		
		for(int i = 0; i < maxIntensity; i++){
			newLuminocity[i] = (short)(cumulativeProb[i] * maxIntensity); // ypologismos ths neas fwteinothtas
			
		}
	}
	
	public short getEqualizedLuminocity(int luminocity){
		return newLuminocity[luminocity-1];
	}
}
