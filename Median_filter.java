import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Median_filter {
	
	
	public static void main(String[] args) 
	{
		BufferedImage image,image1,image2;
		try
		{
			image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Sumitra_lab_3\\messi_N.jpg"));
			//image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Sumitra_lab_3\\ronaldo_de_lima_N.jpg"));
			//image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Sumitra_lab_3\\ronaldo_N.jpg"));
			
			System.out.println("reading completed");
		}
		catch(IOException e)
		{
			System.out.println("Error:"+e);
			return;
		}
		
		 int[][]img=getpixel(image);
		 BufferedImage i =arrayToImage(img);
	        display(i);
	        int m=image.getWidth();
			int n=image.getHeight();
			
			int[][]arrayimage=new int[m][n];
			arrayimage=median_img(img,3);
			image1=arrayToImage(arrayimage);
			display(image1);
	     	SNR_fun(img,arrayimage);
			
			
			
		
		
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
	 
	 static BufferedImage arrayToImage(int[][] f)
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
	 
	 static int[][]median_img(int[][]F,int size)
	 {
		 int[][]arr=new int[F.length][F[0].length];
		 int a=size/2;
		 for(int x=a;x<F.length-a;x++)
			 for(int y=a;y<F[0].length-a;y++)
			 {
				 int[]s_array=new int[size*size];
				 int count=0;
				 for(int i=x-a;i<=x+a;i++)
				 {
					 for(int j=y-a;j<=y+a;j++)
					 {
						 s_array[count]=F[i][j];
						 count++;
					 }
				 }
				 Arrays.sort(s_array);
				 arr[x][y]=s_array[a+1];
			 }
		 return arr;
	 }
	 static float SNR_fun(int[][]F_org,int[][]F_signal)
	 {
		 float snr=0;
		 float f_s=0;
		 float f_o=0;
		 
		 for(int x=0;x<F_signal.length;x++)
		 {
			 for(int y=0;y<F_signal[0].length;y++)
			 {
				
				f_s+=(float)Math.pow((F_signal[x][y]),2);
				
			 }
			
		 }
		 System.out.println(f_s);
		 for(int x=0;x<F_org.length-1;x++)
		 {
			 for(int y=0;y<F_org[0].length-1;y++)
			 {
				//f_o=(F_org[x][y]);
				f_o+=(float)(Math.pow((F_signal[x][y]-F_org[x][y]),2));
				 
			 }
			
			
		 }
		 System.out.println(f_o);
		 snr=(float)f_s/f_o;
		 System.out.println(snr);
		
		return snr;
	    
	
		 
		
		 
	 }
		

}
