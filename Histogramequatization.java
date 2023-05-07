import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Histogramequatization {

	public static void main(String[] args) {
		BufferedImage image;
		
		
		try
		{
			//image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\320662173_551583520152599_593893823622786479_n.jpg"));
			image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\gettyimages.jpg"));
			System.out.println("reading completed");
		}
		catch(IOException e)
		{
			System.out.println("Error:"+e);
			return;
		}
		 try
			{
				ImageIO.write(image, "jpeg",new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\flowers_grayed.jpg"));
				System.out.println("Writing complete. ");
			}
			catch(IOException e)
			{
				System.out.println("Error: "+e);
				return;
			}
		 int[][] g=getpixel(image);		
		    BufferedImage i =getBIfromarray(g);
	        display(i);
	       int m=image.getWidth();
	        int n=image.getHeight();
	        
	        int totalpix=m*n;
	        int nk=0;
	         int[][] a=new int[m][n];
	        int[]his=new int[256];
	        int[]prob=new int[256];
	        float[] arr = new float[256];
	        int[][]transarray=new int[m][n];
	       
	        
			}  
	 static int[][] getpixel(BufferedImage image)
	  {
		    int[][] f= new int[image.getWidth()][image.getHeight()];
		     for(int y=0;y<image.getHeight();y++)
		     {
			    for(int x=0;x<image.getWidth();x++)
			     {
				    Color c =new Color(image.getRGB(x, y));
				    int red=(c.getRed());
				    int blue=(c.getBlue());
				    int green=(c.getGreen());
				
				    f[x][y]=(red+blue+green)/3;
			
			}
		}
		     return f;
		}
		static BufferedImage getBIfromarray(int[][] f)
		{
			BufferedImage image =new BufferedImage(f.length,f[0].length,BufferedImage.TYPE_BYTE_GRAY);
			
			for(int x=0;x<f.length;x++)
			{
				for(int y=0;y<f[0].length;y++)
				{
					Color newCol =new Color(f[x][y],f[x][y],f[x][y]);
					image.setRGB(x,y,newCol.getRGB());
					
				}
			}
			return image;
				
		}
		static void display(BufferedImage bi)
		{
			ImageIcon icon =new ImageIcon(bi);
			JFrame frame =new JFrame();
			frame.setLayout(new FlowLayout());
			frame.setSize(300,250);
			JLabel lbl =new JLabel();
			lbl.setIcon(icon);
			frame.add(lbl);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}

	}


