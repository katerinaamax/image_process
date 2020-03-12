public class YUVPixel {

	private int pixelVal;

	public YUVPixel(short Y, short U, short V) {
		pixelVal = (Y << 16 | U <<8 | V);
	}

	public YUVPixel(RGBPixel pixel){
		
		int y, u, v;
		
		y = ((66 * pixel.getRed() + 129 * pixel.getGreen() + 25 * pixel.getBlue() + 128) >> 8) + 16;
		u = ((-38 * pixel.getRed() - 74 * pixel.getGreen() + 112 * pixel.getBlue() + 128) >> 8) + 128;
		v = ((112 * pixel.getRed() - 94 * pixel.getGreen() - 18 * pixel.getBlue() + 128) >> 8) + 128;
		
		pixelVal = (y << 16 | u << 8 | v);
		
	}
	
	public short clip(int value){
		
		if (value < 0){
			value = 0;
		}
		else if (value > 255){
			value = 255;
		}
		
		return (short)value;
	}
	
	public int getPixel() {
		return pixelVal;
	}
	
	public short getY() {
		return (short) ((pixelVal >> 16) & 0xff);
	}
	
	public short getU() {
		return (short) ((pixelVal >>8) & 0xff);
	}
	
	public short getV() {
		return (short) (pixelVal & 0xff);
	}

	public void setPixel(int pixel) {
		pixelVal = pixel;
	}
	
	public void setY(short Y) {
		pixelVal = pixelVal << 16;
		pixelVal = pixelVal >>> 16;
		pixelVal = (pixelVal | (Y << 16));
	}
	
	public void setU(short U) {
		int help1 , help2;
		help1 = pixelVal;
		help2 = pixelVal;
		
		help1 = help1 >>> 16;
		help1 = help1 << 16;
		
		help2 = help2 << 24;
		help2 = help2 >>> 24;
		
		pixelVal = (U << 8) | help1 |help2;
	}
	
	public void setV(short V) {
		pixelVal = pixelVal >>> 8;
		pixelVal = pixelVal << 8;
		pixelVal = (V | pixelVal);
	}
}

