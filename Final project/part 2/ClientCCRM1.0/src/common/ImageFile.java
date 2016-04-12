package common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ImageFile implements Serializable {
	
	public String fileName=null;	
	public int size=0;
	public  byte[] mybytearray;
	
	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}

	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}
	
	public static ImageFile makeImageFile(File imageF) {
		try {
			File newFile = new File(imageF.getPath());  // source file
			byte[] mybytearray = new byte[(int) imageF.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);

			ImageFile imf = new ImageFile();

			imf.initArray(mybytearray.length);
			imf.size = mybytearray.length;
			imf.fileName = imageF.getName();
			bis.read(imf.mybytearray, 0, mybytearray.length);
			bis.close();
			return imf;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static boolean saveImageFile(ImageFile imf, String imagesDirectory, String imageFileName) {
		try {
			File newFile = new File(imagesDirectory + "/" + imageFileName + ".jpg");

			FileOutputStream fis = new FileOutputStream(newFile);
			BufferedOutputStream bis = new BufferedOutputStream(fis);
			ImageFile mfnew = new ImageFile();

			mfnew.fileName = imageFileName;

			mfnew.size = imf.size;
			mfnew.initArray(imf.size);

			mfnew.setMybytearray(imf.mybytearray);
			bis.write(mfnew.mybytearray, 0, mfnew.size);
			bis.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}

