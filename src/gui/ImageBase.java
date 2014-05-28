package gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageBase {
	private HashMap<String,BufferedImage> myImages = new HashMap<String,BufferedImage>();
	private static final String end = ".png";
	private static final String[] tags = {"-F", "-O", "-T"};
	private static final String[] states = {
		"Alabama", 		"Alaska", 		"Arizona", 		"Arkansas", 	"California", 	"Colorado", 	
		"Florida", 		"Georgia", 		"Hawaii", 		"Idaho", 	
		"Illinois", 	"Indiana", 		"Iowa", 		"Kansas", 		"Kentucky", 	"Louisiana", 	
		"Maine", 		"MD", 			"MA", 			"Michigan", 	"Minnesota", 	
		"Mississippi", 	"Missouri", 	"Montana", 		"Nebraska", 	"Nevada", 		"New_Hampshire", 	
		"New_Jersey", 	"New_Mexico", 	"New_York", 	"North_Carolina", 				"North_Dakota", 	
		"Ohio", 		"Oklahoma", 	"Oregon", 		"Pennsylvania", 	
		"South_Carolina", 				"South_Dakota", 				"Tennessee", 	"Texas", 	
		"Utah", 		"Vermont", 		"Virginia", 	"Washington", 	"West_Virginia", 	
		"Wisconsin", 	"Wyoming", 
	};

	public ImageBase() {

		BufferedImage in = null;
		try {	
			
			in = ImageIO.read(getClass().getResourceAsStream("/map/RISC.png"));

			BufferedImage myMap = new BufferedImage(in.getWidth(), in.getHeight(),BufferedImage.TYPE_INT_ARGB);
			myImages.put("RISC" + end, myMap);
			Graphics2D g = myMap.createGraphics();
			g.drawImage(in, 0, 0, null);
			g.dispose();

			for (String state : states){
				for (String tag : tags){
					
					in = ImageIO.read(getClass().getResourceAsStream("/map/"+state+tag+".png"));

					BufferedImage myState = new BufferedImage(in.getWidth(), in.getHeight(),BufferedImage.TYPE_INT_ARGB);
					myImages.put(state + tag + end, myState);
					Graphics2D g2 = myState.createGraphics();
					g2.drawImage(in, 0, 0, null);
					g2.dispose();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to read map file");
		}
	}

	public BufferedImage getImage(String path){
		if (!myImages.keySet().contains(path))
			System.out.println(path + " was never loaded!");
		return myImages.get(path);
	}

}
