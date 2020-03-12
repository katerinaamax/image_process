public class RGBPixel {

	private int pixelVal;

	public RGBPixel(short red, short green, short blue) {
		pixelVal = (red<<16|green<<8|blue);
	}
	
	public RGBPixel(RGBPixel pixel) {
		pixelVal = pixel.getPixel();
	} 
	
	public RGBPixel(YUVPixel pixel){
		int c,d,e;
		short red, green, blue;
		c = pixel.getY() - 16;
		d = pixel.getU() - 128;
		e = pixel.getV() - 128;
		
		red = clip ((298 * c + 409 * e + 128) >> 8);
		green = clip ((298 * c - 100 * d - 208 *e +128) >> 8);
		blue = clip ((298 * c + 516 * d +128) >> 8);
		
		pixelVal = (red << 16 | green << 8 | blue);
		
	}
	
	public short clip(int value){
		
		if (value < 0){
			value = 0;
		}
		else if (value > 255){
			value = 255;
		}
		
		return (short) value;
	}
	
	public int getPixel() { //optional
		return pixelVal;
	}
	
	public short getRed() {
		return (short) ((pixelVal >> 16) & 0xff);
	}
	
	public short getGreen() {
		return (short) ((pixelVal >>8) & 0xff);
	}
	
	public short getBlue() {
		return (short) (pixelVal & 0xff);
	}

	public void setPixel(int pixel) {
		pixelVal = pixel;
	}
	
	public void setRed(short red) {
		pixelVal = pixelVal << 16;
		pixelVal = pixelVal >>> 16;
		pixelVal = (pixelVal | (red << 16));
	}
	
	public void setGreen(short green) {
		int help1 , help2;
		help1 = pixelVal;
		help2 = pixelVal;
		
// 		//kratame to red
		help1 = help1 >>> 16;
		help1 = help1 << 16;
		
		
// 		kratame to blue
		help2 = help2 << 24;
		help2 = help2 >>> 24;
		
		pixelVal = (green << 8) | help1 |help2;
	}
	
	public void setBlue(short blue) {
		pixelVal = pixelVal>>>8;
		pixelVal = pixelVal<<8;
		pixelVal = (blue | pixelVal);
	}
}

