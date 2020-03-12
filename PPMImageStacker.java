import java.io.*;
import java.util.*;

public class PPMImageStacker{

	private int numOfFiles;
 	protected LinkedList<PPMImage> myList;
 	private PPMImage myImg;
	
	public PPMImageStacker(File dir){
		
		if( !dir.isDirectory()){
			System.out.println("[ERROR]"+dir.toString()+" is not a directory!");
		}
		if( ! dir.exists()){
			System.out.println("[ERROR] Directory " + dir.toString() + " does not exist!" );
		}
		
		int numOfFiles = dir.listFiles().length;
		this.numOfFiles = numOfFiles;

		myList = new LinkedList<PPMImage>();
		PPMImage thePPMimg = null;
		String indexPPM;
		File [] files;
		
		files = dir.listFiles();
		
		for(int i = 0; i < numOfFiles ; i++){
			thePPMimg = new PPMImage(files[i]);
			boolean add = myList.add(thePPMimg); // dimiourgia listas
		}
	}
	
	public LinkedList<PPMImage> getList() {
		return myList;
	}
	
 	public void stack(){
		
		PPMImage first = new PPMImage();
		first = (PPMImage) getList().getFirst();
		RGBImage newImg = new RGBImage (first.getImg().getHeight(),first.getImg().getWidth(), 255);
		int sumRed = 0, sumGreen = 0, sumBlue = 0;
		int finalRed = 0, finalBlue = 0, finalGreen = 0;
		int j;
		for(int i=0; i < newImg.getHeight(); i++) {
			for(j=0; j < newImg.getWidth(); j++) {
				for(int k=0; k < numOfFiles; k++){
					sumRed = sumRed + getList().get(k).getImg().getPixel(i, j).getRed();
					sumGreen = sumGreen +  getList().get(k).getImg().getPixel(i, j).getGreen();
					sumBlue = sumBlue + getList().get(k).getImg().getPixel(i, j).getBlue();
				}
				
				finalRed = sumRed / numOfFiles; //mesos oros gia to kokkino
				finalGreen = sumGreen / numOfFiles;//mesos oros gia to prasino
				finalBlue = sumBlue / numOfFiles;// mesos oros gia to mple
				
				sumRed = 0;
				sumGreen = 0;
				sumBlue = 0;
				
				RGBPixel pxl = new RGBPixel((short)finalRed, (short)finalGreen, (short) finalBlue);
				newImg.setPixel(i, j, pxl);
			}
		}
		
		myImg = new PPMImage(newImg);
	}
	
	public PPMImage getStackedImage(){
		return myImg;
 	}
}
