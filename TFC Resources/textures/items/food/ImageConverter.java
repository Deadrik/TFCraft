import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
public class ImageConverter
{
    public static void main (String[] args)
    {
	BufferedImage img = null;
	try
	{
	    img = ImageIO.read (new File ("FoodSprites.png"));
	}
	catch (IOException e)
	{
	}
	for (int i = 0 ; i < img.getHeight () / 16 ; i++)
	{
	    for (int j = 0 ; j < img.getWidth () / 16 ; j++)
	    {
		try
		{
		    BufferedImage img2 = img.getSubimage (j*16, i*16, 16, 16);
		    File outputfile = new File ("img"+(int)(j+(i*16))+".png");
		    ImageIO.write (img2, "png", outputfile);
		}
		catch (IOException e)
		{
		}
	    }
	}
    }
}
