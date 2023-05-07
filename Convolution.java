import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Convolution {

	public static void main(String[] args) {
		BufferedImage image,image1,image2,image3,image4;
		try
		{
			//image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Sumitra_lab_3\\messi_N.jpg"));
			image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\gettyimages.jpg"));
			//image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\gettyimages.jpg"));
			//image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\flowers_img.jpg"));
			//image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\greyed_img1.jpg"));
			System.out.println("reading completed");
		}
		catch(IOException e)
		{
			System.out.println("Error:"+e);
			return;
		}
		int m=image.getWidth();
		int n=image.getHeight();
		int[][]arrayimage=new int[m][n];
		arrayimage=getpixel(image);
		image1=arrayToImage(arrayimage);
		display(image1);
		
		
		
	double[][]kernel=new double[3][3];
		double[][]kernelx=new double[3][3];
		double[][]kernely=new double[3][3];
	 /*
	  * laplacian kernel
	  */
		kernel[0][0]=1;
		kernel[0][1]=1;
		kernel[0][2]=1;
		kernel[1][0]=1;
		kernel[1][1]=-8;
		kernel[1][2]=1;
		kernel[2][0]=1;
		kernel[2][1]=1;
		kernel[2][2]=1;
		/*
		 * averaging filter kernel	
		 */
	/*	kernel[0][0]=(double)1/9;
		kernel[0][1]=(double)1/9;
		kernel[0][2]=(double)1/9;
		kernel[1][0]=(double)1/9;
		kernel[1][1]=(double)1/9;
		kernel[1][2]=(double)1/9;
		kernel[2][0]=(double)1/9;
		kernel[2][1]=(double)1/9;
		kernel[2][2]=(double)1/9;
		*/
		/*
		 
		
	
		 *  Sobel kernel
		 */
		
		kernelx[0][0]=-1;
		kernelx[0][1]= 0;
		kernelx[0][2]=1;
		kernelx[1][0]=-2;
		kernelx[1][1]=0;
		kernelx[1][2]=2;
		kernelx[2][0]=-1;
		kernelx[2][1]=0;
		kernelx[2][2]=1;
		
		kernely[0][0]=-1 ;
		kernely[0][1]=-2 ;
		kernely[0][2]=-1 ;
		kernely[1][0]=0;
		kernely[1][1]=0;
		kernely[1][2]=0;
		kernely[2][0]=1;
		kernely[2][1]=2;
		kernely[2][2]=1;
		
	/*	kernel[0][0]=-1;
		kernel[0][1]=0;
		kernel[0][2]=1;
		kernel[1][0]=-2;
		kernel[1][1]=0;
		kernel[1][2]=2;
		kernel[2][0]=-1;
		kernel[2][1]=0;
		kernel[2][2]=1;*/
		int a=(kernel.length-1)/2;
		int b=(kernel[0].length-1)/2;
		int[][] f_padded=new int[2*a+arrayimage.length][2*b+arrayimage[0].length];
		for(int x=0;x<arrayimage.length;x++)
		{
			for(int y=0;y<arrayimage[0].length;y++)
			{
				f_padded[a+x][b+y]=arrayimage[x][y];
			}
		}
		
		int ma=maxintensity(arrayimage);
		int mi=minintensity(arrayimage);
		System.out.println(ma);
		System.out.println(mi);
		
		int[][]Con=new int[m][n];
		int[][]rescale=new int[m][n];
	Con=Convolution(arrayimage,f_padded,kernel);
	rescale=Rescale_fun(Con);
	image3=arrayToImage(rescale);
	display(image3);
	threshold(rescale,150);
	}
	static int[][]Rescale_fun(int[][]con_image)
	{
		int[][]rescale1=new int[con_image.length][con_image[0].length];
		int ma=maxintensity(con_image);
		int mi=minintensity(con_image);
	
	for(int x=0;x<con_image.length;x++)
	{
		for(int y=0;y<con_image[0].length;y++)
		{
			rescale1[x][y]=(int)(Math.round(con_image[x][y]-mi)*(((double)255/(double)(ma-mi))));
		}}
	return rescale1;
	}	
	static	int[][]Convolution(int[][]arrayimage,int[][]f_padded,double[][]kernel)
		{
		int a=(kernel.length-1)/2;
		int b=(kernel[0].length-1)/2;
		
		for(int x=0;x<arrayimage.length;x++)
		{
			for(int y=0;y<arrayimage[0].length;y++)
			{
				for(int s=-a;s<=a;s++)
				{
					for(int t=-b;t<=b;t++)
					{
						double v=kernel[s+a][t+b];
						arrayimage[x][y]=(int)(arrayimage[x][y]+v*f_padded[(a+x)-s][(b+y)-t]);
					}
				}
			}
		}
		return arrayimage;
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
	 
	 static int maxintensity(int[][]f )
	 {
	 	 int max=0;
	 	for(int x=0;x<f.length;x++)
	 	{
	 		for(int y=0;y<f[0].length;y++)
	 		{
	 			if(f[x][y]>max)
	 			{
	 				max=f[x][y];
	 			}
	 		}
	 		}
	 	return max;
	 }
	 static int minintensity(int[][]f)
	 {
	 	int min=255;
	 	for(int x=0;x<f.length;x++)
	 		{for(int y=0;y<f[0].length;y++)
	 		{
	 			if(f[x][y]<min)
	 			{
	 				min=f[x][y];
	 			}
	 			}
	 		}
	 	return min;
	 } 
	static  int[][]threshold(int[][] f,int value)
		{
		
      for(int x=0;x<f.length;x++) 
		
			for(int y=0;y<f[0].length;y++)
			{
				if(f[x][y]>=value)
				{
					f[x][y]=255;
			}
				else
				{
					f[x][y]=0;
				}
		}
			return f;	
}
}
