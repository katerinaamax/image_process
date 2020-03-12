import java.io.*;

public class UnsupportedFileFormatException extends java.lang.Exception {
	private String msg;
	
	public UnsupportedFileFormatException(){
	}
	
	public UnsupportedFileFormatException(String msg) {
		this.msg = msg;
	}
}
