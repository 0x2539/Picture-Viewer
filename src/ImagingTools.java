
import de.matthiasmann.twl.utils.PNGDecoder;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class ImagingTools {
	
	public static int glLoadTextureLinear(String location)
	{
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_RECTANGLE_ARB, texture);
		InputStream in = null;
		
		try {
			in = new FileInputStream(location);
			PNGDecoder decoder = new PNGDecoder(in);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			
			buffer.flip();
			in.close();
			
			glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexImage2D(GL_TEXTURE_RECTANGLE_ARB, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
//			System.out.println(location + ": " + decoder.getWidth() + " " + decoder.getHeight());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File was not found.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail to load the texture file.");
		}
		
		glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
		
		return texture;
	}

}
