import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Logtransformation {

	public static void main(String[] args) {
		BufferedImage image,image1,image2;
		try
		{
			image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\320662173_551583520152599_593893823622786479_n.jpg"));
			//image=ImageIO.read(new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\gettyimages.jpg"));
			System.out.println("reading completed");
		}
		catch(IOException e)
		{
			System.out.println("Error:"+e);
			return;
		}
		
		 int[][] transform_Array=new int[image.getWidth()][image.getHeight()];
		 int[][]image_Array=new int[image.getWidth()][image.getHeight()];
		 int [][]histo_Array =new int[image_Array.length][image_Array[0].length];
		 int[][] rescale_Array=new int[image.getWidth()][image.getHeight()];
		// BufferedImage image =getBIfromarray(image_Array);
		 image_Array=getpixel(image);
		 image=getBIfromarray(image_Array);
	       display(image);
	       
	       
	    transform_Array= transform(image_Array);
	    int intmax= maxintensity(transform_Array);
		int intmin= minintensity(transform_Array);
		       // System.out.print(intmax+"  "+intmin);
	    rescale_Array=rescaling(transform_Array);
	    image1=getBIfromarray(rescale_Array);	
		display(image1);
			 
			 
		
		 int[]intensity_Array= new int[256];
	    	intensity_Array= intencount(image_Array);
	    	
	    	
			
	    	/*for(int x=0;x<256;x++)
    		{
    			System.out.println("x-->"+intensity_Array[x]);
    		}*/
	    	 histo_Array=prob(intensity_Array,image_Array.length*image_Array[0].length,image_Array);
	    	 image2=getBIfromarray(histo_Array);
		      display(image2);
		      
		     
		 
		 /* int intmax= maxintensity(g);
	        int intmin= minintensity(g);
	        System.out.print(intmax+"  "+intmin);*/
		/*for(int k=0;k<image_Array.length;k++)
	   		{
	   			for(int j=0;j<image_Array[0].length;j++)
	   			{
	   			System.out.print(histo_Array[k][j]+"\t");
	   			}
	   			System.out.print("\n");
	   		}*/
	      try
			{
				ImageIO.write(image, "jpg",new File("C:\\Users\\user\\eclipse-workspace\\Ippr_lab2\\greyed16.jpg"));
				System.out.println("Writing complete. ");
			}
			catch(IOException e)
			{
				System.out.println("Error: "+e);
				return;
			}
		  
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
	
 public static int[] intencount(int[][]f)
	 
	 { int[]h=new int[256];
		
		 for(int i=0;i<f.length;i++)
		 {
			 for(int j=0;j<f[0].length;j++)
			 {
				// int[]h=new int[256];
			      for(int k=0;k<256;k++)
			      {
			    	  if(f[i][j]==k)
			    	        h[k]++;
			    	//  System.out.println(h[k]);
			    	 
			      } 
			    //  System.out.println(h);
			     
			 }
		 }
	
		return h;
	 }
 static int[][]prob(int[]f,long total,int[][]ImAr)
 {
 	double[]p=new double[256];
 	int[]t=new int[256];
 	double sum=0;
 	for(int i=0;i<256;i++)
 	{	
 		p[i]=(sum+(double)f[i]/total);
 		sum=p[i];
 		//System.out.println(p[i]);
 	}
 	for(int k=0;k<256;k++)
 	{
 		for(int i=0;i<ImAr.length;i++)
 		{
 			for(int j=0;j<ImAr[0].length;j++)
 			{
 		t[k]=(int) Math.round((double)(255)*p[k]);
 		if(ImAr[i][j]==t[k])
 		{
 			ImAr[i][j]=k;
 		}
 	
 	}
 		}
 	}
 	
		return ImAr;
		
 }
	      
	   
	 static  int[][] transform(int[][] img)
		{
		
		 int[][]transarray=new int[img.length][img[0].length];
         for(int x=0;x<img.length;x++) 
      {
		
			for(int y=0;y<img[0].length;y++)
			{
				//int c=(int)(255/(Math.log(1+img[x][y])));
				transarray[x][y]=(int) ( Math.round(Math.log(img[x][y])));
			}
      }
     
	return transarray;
		}
	static int[][] rescaling(int[][] f)
	 
	 {
		int[][]retrans=new int[f.length][f[0].length];
		for(int x=0;x<f.length;x++)
		{
			for(int y=0;y<f[0].length;y++)
			{
				retrans[x][y]=(int)(Math.round((double)(f[x][y]-minintensity(f))/(maxintensity(f)-minintensity(f))*255));
				//System.out.println(retrans[x][y]);
			}
			
		}
		
		 
	 
	 return retrans;
	
}
static int maxintensity(int[][]f )
{
	 int intmax=0;
	for(int x=0;x<f.length;x++)
	{
		for(int y=0;y<f[0].length;y++)
		{
			if(f[x][y]>intmax)
			{
				intmax=f[x][y];
			}
		}
		}
	
	return intmax;
}
static int minintensity(int[][]f)
{
	int intmin=255;
	for(int x=0;x<f.length;x++)
		{for(int y=0;y<f[0].length;y++)
		{
			if(f[x][y]<intmin)
			{
				intmin=f[x][y];
			}
			
		}
		}
	
	return intmin;
}  
}
	
	

	


