import java.util.*;
import java.awt.event.*;
import java.awt.EventQueue;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.*;
import javafx.stage.*;

public class ImageProcessing extends JFrame implements ActionListener{
	JMenuItem openPPM ;
	JMenuItem openYUV ;
	JMenuItem savePPM ;
	JMenuItem saveYUV ;
	PPMImage ppm;
	YUVImage yuv;
	JMenuItem grayscale_M, incr_size_M, decr_size_M ,rotate_M,histogram_M,selectDir;
	
	private JTextField filename = new JTextField();
	
	public ImageProcessing(){
		initUI();
	}

	private void initUI() {
 
		my_menu();
 
		setTitle("Image Processing");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public void my_menu(){  
	
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("1. File");
		fileMenu.setMnemonic('F');
		
		JMenu open = new JMenu ("1.1 Open");
		JMenu save = new JMenu ("1.2 Save");

		fileMenu.add(open);
		fileMenu.add(save);
		
		openPPM = new JMenuItem ("1.1.1 PPM File");	
		openPPM.addActionListener(this);    

	
		openYUV = new JMenuItem ("1.1.2 YUV File");
		openYUV.addActionListener(this); 
		
 		savePPM = new JMenuItem ("1.2.1 PPM File");
 		savePPM.addActionListener(this);
 		saveYUV = new JMenuItem ("1.2.2 YUV File");
		saveYUV.addActionListener(this);
		
		open.add(openPPM);
		open.add(openYUV);
		save.add(savePPM);
		save.add(saveYUV);
		
		fileMenu.add(open);
		fileMenu.add(save);
		
		JMenu actions_M =  new JMenu("2. Actions");
		actions_M.setMnemonic('A');
		
		grayscale_M = new JMenuItem("2.1 Grayscale");
		grayscale_M.addActionListener(this);
		
		incr_size_M = new JMenuItem("2.2 Increase Size");
		incr_size_M.addActionListener(this);
		
		decr_size_M = new JMenuItem("2.3 Decrease Size");
		decr_size_M.addActionListener(this);
		
		rotate_M = new JMenuItem("2.4 Rotate Clockwise");
		rotate_M.addActionListener(this);
		
		histogram_M =  new JMenuItem("2.5 Equalize Histogram");
		histogram_M.addActionListener(this);
		
		JMenu stacking_M = new JMenu("2.6 Stacking Algorithm");
		selectDir = new JMenuItem("2.6.1 Select directory");
		selectDir.addActionListener(this);
		
		actions_M.add(grayscale_M);
		actions_M.add(incr_size_M);
		actions_M.add(decr_size_M);
		actions_M.add(rotate_M);
		actions_M.add(histogram_M);
		
		actions_M.add(stacking_M);
		stacking_M.add(selectDir);
		
		menubar.add(fileMenu);
		menubar.add(actions_M);
		setJMenuBar(menubar);
		
	}
	
	 public void actionPerformed(ActionEvent e) {    
		if(e.getSource() == openPPM){    
			
			JFileChooser fc = new JFileChooser();    
			int l = fc.showOpenDialog(this);    
		
			if(l == JFileChooser.APPROVE_OPTION){    
				File f = fc.getSelectedFile();    
				String filepath = f.getPath();    
				
				ppm = new PPMImage(f);
				yuv = new YUVImage(ppm.getImg());
				imageDisplay(ppm);
			}    
		}
		else if(e.getSource()== openYUV) {
			
			JFileChooser fc = new JFileChooser();    
			int l = fc.showOpenDialog(this);    
			
			if(l == JFileChooser.APPROVE_OPTION){    
				File f = fc.getSelectedFile();    
				String filepath = f.getPath();    
				
				yuv = new YUVImage(f);
				ppm = new PPMImage(new RGBImage(yuv));
				imageDisplay(ppm);
			}
		}
		else if(e.getSource() == savePPM) {
			JFileChooser fc1 = new JFileChooser();    
			int l1 = fc1.showSaveDialog(this); 
			
			if(l1 == JFileChooser.APPROVE_OPTION){ 
				filename.setText(fc1.getSelectedFile().getName());
				ppm.toFile(fc1.getSelectedFile());
			}
		}
		else if(e.getSource() == saveYUV) {
			JFileChooser fc2 = new JFileChooser();    
			int l2 = fc2.showSaveDialog(this); 
			
			if(l2 == JFileChooser.APPROVE_OPTION){ 
				filename.setText(fc2.getSelectedFile().getName());
				yuv.toFile(fc2.getSelectedFile());
			}
		}
		else if(e.getSource () == grayscale_M){
			
			ppm.getImg().grayscale();
			yuv = new YUVImage(ppm.getImg());
			imageDisplay(ppm);
		}
		else if(e.getSource () == incr_size_M){
			
			ppm.getImg().doublesize();
			yuv = new YUVImage(ppm.getImg());
			imageDisplay(ppm);
		}
		else if(e.getSource () == decr_size_M){
			
			ppm.getImg().halfsize();
			yuv = new YUVImage(ppm.getImg());
			imageDisplay(ppm);
		}
		else if(e.getSource () == rotate_M){
			
			ppm.getImg().rotateClockwise();
			yuv = new YUVImage(ppm.getImg());
			imageDisplay(ppm);
		}
		else if(e.getSource() == histogram_M) {
				
				Histogram newHistogram = new Histogram(yuv);
				newHistogram.equalize();
				for(int i = 0; i < yuv.getHeight(); i++) {
					for(int j = 0; j < yuv.getWidth(); j++) {
						YUVPixel pixel = new YUVPixel(yuv.getPixel(i,j).getY(), yuv.getPixel(i,j).getU(),yuv.getPixel(i,j).getV());
						pixel.setY(newHistogram.getEqualizedLuminocity(yuv.getPixel(i,j).getY()));
						yuv.setPixel(i,j, pixel );
					}
				}
				ppm = new PPMImage(new RGBImage(yuv));
				imageDisplay(ppm);
		}
		else if(e.getSource() == selectDir){
			
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File f = chooser. getCurrentDirectory();
			String filename = f.getAbsolutePath();
			System.out.println(filename);
			
			PPMImageStacker stack = new PPMImageStacker(f);
			stack.stack();
			ppm = new PPMImage(stack.getStackedImage().getImg());
			yuv = new YUVImage(ppm.getImg());
			imageDisplay(ppm);
		
		}
		
	}
	
	public void imageDisplay(PPMImage ppmImg){ //synarthsh gia thn provolh eikonas sto grafiko perivallon
	
		int height = ppmImg.getImg().getHeight();
		int width = ppmImg.getImg().getWidth();
			
		BufferedImage buffImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width ; j++){
				buffImage.setRGB(j,i,ppmImg.getImg().getPixel(i,j).getPixel());
			}
		}
	
		getContentPane().removeAll();
		JLabel jlabel = new JLabel();
		jlabel.setIcon(new ImageIcon(buffImage));
		getContentPane().add(jlabel,BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
 
		EventQueue.invokeLater(new Runnable() {
		@Override
		public void run() {
 
			ImageProcessing ex = new ImageProcessing();
			ex.setVisible(true);
		}
		});
		
	}
}
