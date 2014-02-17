import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;

import javax.swing.JTextArea;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

//import org.newdawn.slick.opengl.Texture;
//import org.newdawn.slick.opengl.TextureLoader;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;

public class Main {

	static int[] imaginiColor = new int[6];
	static int[] imaginiBW = new int[6];
	static int[] imaginiS = new int[6];
	
	int scaleSizeForGlass = 64;
	
	int[] typeOfColor = new int[6];
	
	int indexImagine = 0;
	static int rama = 0;
	
	int width=800, height=600;
	
	// coordonatele patratului desenat, cordw=weight, cordh=height
	int cordx,cordy,cordw,cordh;
	
	double angleX = 0, angleY = 0, angleZ = 0;
	double lastXM = 0, lastYM = 0;
	double radical = (double) Math.sqrt(3);

	
	boolean close=true;
	boolean close2=true;
	boolean nextPic = false, previousPic = false, 
			isMovingLeft = false, isMovingRight = false, canMove = true,
			alreadyClickedMouse = false, isEnlarging = false, isMinimizing = false,
			isHidingTheButtons = false, isShowingTheButtons = false;
	double unghiMiscat = 0;
	int currentPainting = 0, theRealCurrentPainting = 6;

	double a = 200, x = 0, y = 0, z = 0, movingZ = 0;
	double curX = x, curY = y, curZ = z, curAngle = 0;
	
	double maxOnX = 0, maxOnY = 0, minOnX = 0, minOnY = 0;
	
//	float[] albNegru = new float[]{0.1f, 0.1f, 0.1f, 1f};
	
	
	
	public Main()
	{
		try 
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("First");
			Display.create();
		}
		catch (LWJGLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set-up an orthographic presentation where (0, 0) is the upper-left corner and (640, 480) is the bottom right one.

//		glDepthFunc(GL_LESS);
		
//		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
//		GLU.gluPerspective(90f, (double)width / height, 1f, 10000f);
        gluPerspective((float) 270, (float) width / (float) height, 1, 10000);
//		glOrtho(0, width, height, 0, 1, 10000);//schimba sistemul xoy
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_DEPTH_TEST);		
		glDepthFunc(GL_LESS);
		
        glEnable(GL_BLEND);
        glEnable(GL_ALPHA_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

//		glEnable(GL_TEXTURE_2D);
		glEnable(GL_TEXTURE_RECTANGLE_ARB);
		
		

		loadTheNeededTextures();
		addglass();
//		createBuffers(100,100);

		glClearColor(0.5f, 0.7f, 1.f, 1);
		while(!Display.isCloseRequested()&& close)
		{ 
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			{
				close=false;
			}
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			render();
			
			setCurCoords(currentPainting);
			
			angleX += 1.5f;
			angleY += 0.1f;
			angleZ += 5.1f;
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		
	}

	
	double z1,z2,z3,z4,z5,z6;
	double x1,x2,x3,x4,x5,x6;
	double y1,y2,y3,y4,y5,y6;
	double u1,u2,u3,u4,u5,u6;
	
	void render()
	{
		glPushMatrix();
		{
			glTranslated(0, 0, movingZ);
//			glRotated(angleX / 10, 0, 1, 0);
			glRotated(-unghiMiscat, 0, 1, 0);
			KeysInput();
//			renderImagesInLine();
			renderTheHexagon();
			
			if(lupa.isClicked || lupa.alphaColor2 >0)
			{
				if(currentPainting == 0)
				{
					if(typeOfColor[5] == 0)
					{
						renderMagnSquareIMG1(imaginiColor[5]);
					}
					else
						if(typeOfColor[5] == 1)
						{
							renderMagnSquareIMG1(imaginiBW[5]);
						}
						else
							if(typeOfColor[5] == 2)
							{
								renderMagnSquareIMG1(imaginiS[5]);
							}
				}
				else
					if(currentPainting == 1)
					{
						if(typeOfColor[4] == 0)
						{
							renderMagnSquareIMG2(imaginiColor[4]);
						}
						else
							if(typeOfColor[4] == 1)
							{
								renderMagnSquareIMG2(imaginiBW[4]);
							}
							else
								if(typeOfColor[4] == 2)
								{
									renderMagnSquareIMG2(imaginiS[4]);
								}
					}
					else
						if(currentPainting == 2)
						{
							if(typeOfColor[3] == 0)
							{
								renderMagnSquareIMG3(imaginiColor[3]);
							}
							else
								if(typeOfColor[3] == 1)
								{
									renderMagnSquareIMG3(imaginiBW[3]);
								}
								else
									if(typeOfColor[3] == 2)
									{
										renderMagnSquareIMG3(imaginiS[3]);
									}
						}
						else
							if(currentPainting == 3)
							{
								if(typeOfColor[2] == 0)
								{
									renderMagnSquareIMG4(imaginiColor[2]);
								}
								else
									if(typeOfColor[2] == 1)
									{
										renderMagnSquareIMG4(imaginiBW[2]);
									}
									else
										if(typeOfColor[2] == 2)
										{
											renderMagnSquareIMG4(imaginiS[2]);
										}
							}
							else
								if(currentPainting == 4)
								{
									if(typeOfColor[1] == 0)
									{
										renderMagnSquareIMG5(imaginiColor[1]);
									}
									else
										if(typeOfColor[1] == 1)
										{
											renderMagnSquareIMG5(imaginiBW[1]);
										}
										else
											if(typeOfColor[1] == 2)
											{
												renderMagnSquareIMG5(imaginiS[1]);
											}
								}
								else
									if(currentPainting == 5)
									{
										if(typeOfColor[0] == 0)
										{
											renderMagnSquareIMG6(imaginiColor[0]);
										}
										else
											if(typeOfColor[0] == 1)
											{
												renderMagnSquareIMG6(imaginiBW[0]);
											}
											else
												if(typeOfColor[0] == 2)
												{
													renderMagnSquareIMG6(imaginiS[0]);
												}
									}
			}
			renderglass();
		}
		glPopMatrix();
	}
	
	void renderTheHexagon()
	{
		double[] cul = new double[4];
		cul[0] = 1;
		cul[1] = 1;
		cul[2] = 1;
		cul[3] = 1;
		
		x1 = x + a * 3 / 4.0;
		y1 = y;
		z1 = z - a * Math.sqrt(3) / 4;
		u1 = 60;
//		double xCopie = x1, zCopie = 0;
//		x1 = rotatePoint1(xCopie, zCopie, angleX);
//		z1 = rotatePoint2(xCopie, zCopie, angleX);
		
		
		x2 = x1;
		y2 = y;
		z2 = z + a * Math.sqrt(3) / 4;
		u2 = 120;
//		xCopie = x2; zCopie = 0;
//		x2 = rotatePoint1(xCopie, zCopie, angleX);
//		z2 = rotatePoint2(xCopie, zCopie, angleX);

		x3 = x;
		y3 = y;
		z3 = z + a * Math.sqrt(3) / 2;
		u3 = 0;
//		xCopie = x3; zCopie = 0;
//		x3 = rotatePoint1(xCopie, zCopie, angleX);
//		z3 = rotatePoint2(xCopie, zCopie, angleX);
		
		x4 = x - 3 * a / 4;
		y4 = y;
		z4 = z2;
		u4 = -120;
//		xCopie = x4; zCopie = 0;
//		x4 = rotatePoint1(xCopie, zCopie, angleX);
//		z4 = rotatePoint2(xCopie, zCopie, angleX);
		
		x5 = x4;
		y5 = y;
		z5 = z1;
		u5 = -60;
//		xCopie = x5; zCopie = 0;
//		x5 = rotatePoint1(xCopie, zCopie, angleX);
//		z5 = rotatePoint2(xCopie, zCopie, angleX);
		
		x6 = x3;
		y6 = y;
		z6 = z - a * Math.sqrt(3) / 2;
		u6 = 180;
//		xCopie = x6; zCopie = 0;
//		x6 = rotatePoint1(xCopie, zCopie, angleX);
//		z6 = rotatePoint2(xCopie, zCopie, angleX);
		
		
		glPushMatrix();
		{
			if(typeOfColor[0] == 0)
			{
				drawBSSquare(x1, y1, z1, a - 25, a - 25, u1, imaginiColor[0], cul);
			}
			else
				if(typeOfColor[0] == 1)
				{
					drawBSSquare(x1, y1, z1, a - 25, a - 25, u1, imaginiBW[0], cul);
				}
				else
					if(typeOfColor[0] == 2)
					{
						drawBSSquare(x1, y1, z1, a - 25, a - 25, u1, imaginiS[0], cul);
					}
		}
		glPopMatrix();

		glPushMatrix();
		{
			if(typeOfColor[1] == 0)
			{
				drawBSSquare(x2, y2, z2, a - 25, a - 25, u2, imaginiColor[1], cul);
			}
			else
				if(typeOfColor[1] == 1)
				{
					drawBSSquare(x2, y2, z2, a - 25, a - 25, u2, imaginiBW[1], cul);
				}
				else
					if(typeOfColor[1] == 2)
					{
						drawBSSquare(x2, y2, z2, a - 25, a - 25, u2, imaginiS[1], cul);
					}
		}
		glPopMatrix();			

		glPushMatrix();
		{
			if(typeOfColor[2] == 0)
			{
				drawBSSquare(x3, y3, z3, a - 25, a - 25, u3, imaginiColor[2], cul);
			}
			else
				if(typeOfColor[2] == 1)
				{
					drawBSSquare(x3, y3, z3, a - 25, a - 25, u3, imaginiBW[2], cul);
				}
				else
					if(typeOfColor[2] == 2)
					{
						drawBSSquare(x3, y3, z3, a - 25, a - 25, u3, imaginiS[2], cul);
					}
		}
		glPopMatrix();
		
		glPushMatrix();
		{
			if(typeOfColor[3] == 0)
			{
				drawBSSquare(x4, y4, z4, a - 25, a - 25, u4, imaginiColor[3], cul);
			}
			else
				if(typeOfColor[3] == 1)
				{
					drawBSSquare(x4, y4, z4, a - 25, a - 25, u4, imaginiBW[3], cul);
				}
				else
					if(typeOfColor[3] == 2)
					{
						drawBSSquare(x4, y4, z4, a - 25, a - 25, u4, imaginiS[3], cul);
					}
		}
		glPopMatrix();
		
		glPushMatrix();
		{
			if(typeOfColor[4] == 0)
			{
				drawBSSquare(x5, y5, z5, a - 25, a - 25, u5, imaginiColor[4], cul);
			}
			else
				if(typeOfColor[4] == 1)
				{
					drawBSSquare(x5, y5, z5, a - 25, a - 25, u5, imaginiBW[4], cul);
				}
				else
					if(typeOfColor[4] == 2)
					{
						drawBSSquare(x5, y5, z5, a - 25, a - 25, u5, imaginiS[4], cul);
					}
		}
		glPopMatrix();
		
		glPushMatrix();
		{
			if(typeOfColor[5] == 0)
			{
				drawBSSquare(x6, y6, z6, a - 25, a - 25, u6, imaginiColor[5], cul);
			}
			else
				if(typeOfColor[5] == 1)
				{
					drawBSSquare(x6, y6, z6, a - 25, a - 25, u6, imaginiBW[5], cul);
				}
				else
					if(typeOfColor[5] == 2)
					{
						drawBSSquare(x6, y6, z6, a - 25, a - 25, u6, imaginiS[5], cul);
					}
		}
		glPopMatrix();
	}
	
	
	public void drawBSSquare(double x, double y, double z, double w, double h, double unghi, int theTexture, double[] colors)
	{
		double angle = unghi;//angleX / 100;
		angle = Math.toRadians(angle);
		glTranslated(x, y, z);

        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, theTexture);
//        spritesheet = ImagingTools.glLoadTextureLinear(SPRITESHEET_IMAGE_LOCATION);
//		theTexture.bind();
		glBegin(GL_QUADS);
				/*se invarte pe Y
				glVertex3d(rotatePoint1(-w/2, +h/2, angle), rotatePoint2(-w/2, +h/2, angle), z);//top-left corner
				glTexCoord2f(0,0);//coordonatele imaginii

				glVertex3d(rotatePoint1(-w/2, -h/2, angle), rotatePoint2(-w/2, -h/2, angle), z);//bottom-left corner
				glTexCoord2f(1,0);//coordonatele imaginii

				glVertex3d(rotatePoint1(+w/2, -h/2, angle), rotatePoint2(+w/2, -h/2, angle), z);//bottom-right corner
				glTexCoord2f(1,1);// coordonatele imaginii

				glVertex3d(rotatePoint1(+w/2, +h/2, angle), rotatePoint2(+w/2, +h/2, angle), z);//top-right corner
				glTexCoord2f(0,1);//coordonatele imaginii
				*/

				glColor4d(colors[0], colors[1], colors[2], colors[3]);
				glVertex3d(rotatePoint1(-w/2, 0, angle), h/2, rotatePoint2(-w/2, 0, angle));//top-left corner
				glTexCoord2f(0,0);//coordonatele imaginii
		
				glVertex3d(rotatePoint1(-w/2, 0, angle), -h/2, rotatePoint2(-w/2, 0, angle));//bottom-left corner
				glTexCoord2f(512,0);//coordonatele imaginii
		
				glVertex3d(rotatePoint1(w/2, 0, angle), -h/2, rotatePoint2(w/2, 0, angle));//bottom-right corner
				glTexCoord2f(512,512);// coordonatele imaginii
		
				glVertex3d(rotatePoint1(w/2, 0, angle), h/2, rotatePoint2(w/2, 0, angle));//top-right corner
				glTexCoord2f(0,512);//coordonatele imaginii
				
				//ordinea pe care o dai lui Texcoord2f controleaza sensul intoarcerii imaginii
				//vezi pagina caiet info
		glEnd();
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
	}

	public void drawBSSquareCropped(double x, double y, double z,
			double xM, double yM,
			int wM, int hM,
			int w, int h, 
			double unghi, int theTexture, double[] colors)
	{
		double angle = unghi;//angleX / 100;
		angle = Math.toRadians(angle);
		glTranslated(x, y, z);
		glBindTexture(GL_TEXTURE_RECTANGLE_ARB, theTexture);
//		theTexture.bind();

		createBuffers((float)xM, (float)yM, wM, hM, w, h, (float)angle);
		glColor4d(colors[0], colors[1], colors[2], colors[3]);
        // vertices
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
		glVertexPointer(vertexSize, GL_FLOAT, 0, 0L);
		
		// texCoords
		glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
		glBufferData(GL_ARRAY_BUFFER, textureData, GL_STATIC_DRAW);
		glTexCoordPointer(textureSize, GL_FLOAT, 0, 0);
		
		// unbind VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		//aici ii spun ce este "QUADS", "TRIANGLES", etc.
		glDrawArrays(GL_QUADS, 0, amountOfVertices);

		glDisableClientState(GL_VERTEX_ARRAY);
		//glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
		
		delBuffers();
		/*
		glBegin(GL_QUADS);
	
				glColor4d(colors[0], colors[1], colors[2], colors[3]);
				glVertex3d(rotatePoint1(-w/2, 0, angle), -h/2, rotatePoint2(-w/2, 0, angle));//top-left corner
				glTexCoord2d(l,b);//coordonatele imaginii
		
				glVertex3d(rotatePoint1(-w/2, 0, angle), h/2, rotatePoint2(-w/2, 0, angle));//bottom-left corner
				glTexCoord2d(l,t);//coordonatele imaginii
		
				glVertex3d(rotatePoint1(w/2, 0, angle), h/2, rotatePoint2(w/2, 0, angle));//bottom-right corner
				glTexCoord2d(r,t);// coordonatele imaginii
		
				glVertex3d(rotatePoint1(w/2, 0, angle), -h/2, rotatePoint2(w/2, 0, angle));//top-right corner
				glTexCoord2d(r,b);//coordonatele imaginii
				
				//ordinea pe care o dai lui Texcoord2f controleaza sensul intoarcerii imaginii
				//vezi pagina caiet info
		glEnd();*/
	}
	
	
	int vboVertexHandle, vboTextureHandle;
	FloatBuffer vertexData, textureData;
    int amountOfVertices = 4;
    int vertexSize = 3;
    int textureSize = 2;
    
	
	public void createBuffers(float x, float y, int wM, int hM, int w, int h,
			float angle)
	{
		float x0 = x-wM, x20 = x + wM;
		float y0 = y-hM, y20 = y + hM;
        vboVertexHandle = glGenBuffers();
        vboTextureHandle = glGenBuffers();        
        
        //aici dau pozitia obiectului
        vertexData = BufferUtils.createFloatBuffer(amountOfVertices * vertexSize);
        vertexData.put(new float[]{
    		(float)rotatePoint1(-w/2, 0, angle), -h/2, (float)rotatePoint2(-w/2, 0, angle),  		
    		(float)rotatePoint1(-w/2, 0, angle), h/2, (float)rotatePoint2(-w/2, 0, angle),
    		(float)rotatePoint1(w/2, 0, angle), h/2, (float)rotatePoint2(w/2, 0, angle),
    		(float)rotatePoint1(w/2, 0, angle), -h/2, (float)rotatePoint2(w/2, 0, angle)});
        vertexData.flip();

        //aici dau pozitia texturii
        textureData = BufferUtils.createFloatBuffer(amountOfVertices * textureSize);
        textureData.put(new float[]{
									x20, y0,
									x20, y20,
        							x0, y20,
        							x0, y0});
        textureData.flip();
	}

	public void delBuffers()
	{
		vertexData.clear();
		textureData.clear();
	}
	
	public double rotatePoint1(double v1, double v2, double angle)
	{
		return v1 * Math.cos(angle) - v2 * Math.sin(angle);
	}

	public double rotatePoint2(double v1, double v2, double angle)
	{
		return v1 * Math.sin(angle) + v2 * Math.cos(angle);
	}
	
	buton lupa =new buton();
	MagnificationSquare magnSquare = new MagnificationSquare();
	boolean clickedOnGlass = false;
	
	
	public void renderglass()
	{
		int newTexture = 0;
//		if(Mouse.getX() >= 15 && Mouse.getX() <= 91 &&
//		Mouse.getY() <= 576 && Mouse.getY() >= 506 && clickedOnGlass == false)
		//daca a apasat pe L atunci foloseste lupa si daca 
		//este aproape de imagine (canMove == false)
		if(Keyboard.isKeyDown(Keyboard.KEY_L) && clickedOnGlass == false &&
				canMove == false)
		{
//			if(Mouse.isButtonDown(0) == false)
//			{
//				newTexture = lupa.textureHover;
//			}
//			else
//			{
				if(lupa.isClicked == true)
				{
					newTexture = lupa.texture;
					lupa.isClicked = false;
				}
				else
					if(lupa.isClicked == false)
					{
						newTexture = lupa.textureDown;
						lupa.isClicked = true;
					}
				clickedOnGlass = true;
//			}
		}
//		else
//		{
//			if(lupa.isClicked == false)
//			{
//				newTexture = lupa.texture;
//			}
//			else
//				if(lupa.isClicked == true)
//				{
//					newTexture = lupa.textureDown;
//				}
//		}
		
//		if(Mouse.isButtonDown(0) == false)
		if(Keyboard.isKeyDown(Keyboard.KEY_L) == false)
		{
			clickedOnGlass = false;
		}
		
		//daca lupa este pornita atunci o fac sa apara sau sa dispara
		if(lupa.isClicked)
		{
			hideMagnGlass(1);
		}
		else
			if(lupa.isClicked == false)
			{
				hideMagnGlass(-1);
			}


		
//		double[] cul = new double[4];
//		cul[0] = 1;
//		cul[1] = 1;
//		cul[2] = 1;
//		cul[3] = lupa.alphaColor;
//		
//		glPushMatrix();
//		drawBSSquare(lupa.x - 10, lupa.y + 20, lupa.z - 90 - movingZ, lupa.w, lupa.h, 0, newTexture, cul);
//		glPopMatrix();
		
	}

	
	void renderMagnSquareIMG1(int texture)
	{
		double[] cull = new double[4];
		cull[0] = 1;
		cull[1] = 1;
		cull[2] = 1;
		cull[3] = lupa.alphaColor2;
		
		maxOnX = curX + 100;
		minOnX = curX - 100;
		
		maxOnY = curY + 100;
		minOnY = curY - 100;
		
		double irealMaxX = 200;
		double irealMaxY = 200;
		
		double actualX =(double) Mouse.getX() / width * irealMaxX + minOnX;
		double actualY =(double) Mouse.getY() / height * irealMaxY + minOnY;
//		actualX = actualX / 2;
//		curZ = curZ - actualX * Math.sqrt(3) / 4;
//		double curZ2 = curZ - (actualX - minOnX) * Math.sqrt(3)+100.5;
		
			glPushMatrix();
			drawBSSquareCropped(-actualX, -actualY, curZ, 
					(double)Mouse.getX() / width * 512, 
					(double)(600-Mouse.getY()) / height * 512, scaleSizeForGlass, scaleSizeForGlass, 100, 100, curAngle - 180, 
					texture, cull);

//			drawBSSquare(x6, y6, z6 + 10, a - 25, a - 25, u6, peste, cull);
//			drawBSSquare(curX, curY, curZ - movingZ,
//							500, 500, 180, 
//					tablou, cull);
			glPopMatrix();
			
			glPushMatrix();
//			glRotated(Math.toRadians(0), 0, 1, 0);
			drawBSSquare(-actualX, -actualY, curZ, 102, 102, curAngle, rama, cull);
			glPopMatrix();
			
	}

	void renderMagnSquareIMG6(int texture)
	{
		double[] cull = new double[4];
		cull[0] = 1;
		cull[1] = 1;
		cull[2] = 1;
		cull[3] = lupa.alphaColor2;
		
		maxOnX = curX + 50;
		minOnX = curX - 50;
		
		maxOnY = curY + 100;
		minOnY = curY - 100;
		
		double irealMaxX = 100;
		double irealMaxY = 200;
		
		double actualX =(double) Mouse.getX() / width * irealMaxX + minOnX;
		double actualY =(double) Mouse.getY() / height * irealMaxY + minOnY;
//		actualX = actualX / 2;
//		curZ = curZ - actualX * Math.sqrt(3) / 4;
		double curZ2 = curZ - (actualX - minOnX) * Math.sqrt(3)+88;
		
			glPushMatrix();
			drawBSSquareCropped(-actualX, -actualY, curZ2, 
					(double)(width - Mouse.getX()) / width * 512, 
					(double)(height - Mouse.getY()) / height * 512, scaleSizeForGlass, scaleSizeForGlass, 100, 100, curAngle - 180, 
					texture, cull);

//			drawBSSquare(x6, y6, z6 + 10, a - 25, a - 25, u6, peste, cull);
//			drawBSSquare(curX, curY, curZ - movingZ,
//							500, 500, 180, 
//					tablou, cull);
			glPopMatrix();
			
			glPushMatrix();
//			glRotated(Math.toRadians(0), 0, 1, 0);
			drawBSSquare(-actualX, -actualY, curZ2, 102, 102, curAngle, rama, cull);
			glPopMatrix();
			
	}

	void renderMagnSquareIMG2(int texture)
	{
		double[] cull = new double[4];
		cull[0] = 1;
		cull[1] = 1;
		cull[2] = 1;
		cull[3] = lupa.alphaColor2;
		
		maxOnX = curX + 50;
		minOnX = curX - 50;
		
		maxOnY = curY + 100;
		minOnY = curY - 100;
		
		double irealMaxX = 100;
		double irealMaxY = 200;
		
		double actualX =(double) Mouse.getX() / width * irealMaxX + minOnX;
		double actualY =(double) Mouse.getY() / height * irealMaxY + minOnY;
//		actualX = actualX / 2;
//		curZ = curZ - actualX * Math.sqrt(3) / 4;
		double curZ2 = curZ + (actualX - minOnX) * Math.sqrt(3)-86;
		
			glPushMatrix();
			drawBSSquareCropped(-actualX, -actualY, curZ2, 
					(double)(width-Mouse.getX()) / width * 512, 
					(double)(height-Mouse.getY()) / height * 512, scaleSizeForGlass, scaleSizeForGlass, 100, 100, curAngle - 180, 
					texture, cull);

//			drawBSSquare(x6, y6, z6 + 10, a - 25, a - 25, u6, peste, cull);
//			drawBSSquare(curX, curY, curZ - movingZ,
//							500, 500, 180, 
//					tablou, cull);
			glPopMatrix();
			
			glPushMatrix();
//			glRotated(Math.toRadians(0), 0, 1, 0);
			drawBSSquare(-actualX, -actualY, curZ2, 102, 102, curAngle, rama, cull);
			glPopMatrix();
			
	}

	void renderMagnSquareIMG3(int texture)
	{
		double[] cull = new double[4];
		cull[0] = 1;
		cull[1] = 1;
		cull[2] = 1;
		cull[3] = lupa.alphaColor2;
		
		maxOnX = curX + 50;
		minOnX = curX - 50;
		
		maxOnY = curY + 100;
		minOnY = curY - 100;
		
		double irealMaxX = 100;
		double irealMaxY = 200;
		
		double actualX =(double) (width - Mouse.getX()) / width * irealMaxX + minOnX;
		double actualY =(double) Mouse.getY() / height * irealMaxY + minOnY;
//		actualX = actualX / 2;
//		curZ = curZ - actualX * Math.sqrt(3) / 4;
		double curZ2 = curZ - (actualX - minOnX) * Math.sqrt(3)+86;
		
			glPushMatrix();
			drawBSSquareCropped(-actualX, -actualY, curZ2, 
					(double)(width-Mouse.getX()) / width * 512, 
					(double)(height-Mouse.getY()) / height * 512, scaleSizeForGlass, scaleSizeForGlass, 100, 100, curAngle - 180, 
					texture, cull);

//			drawBSSquare(x6, y6, z6 + 10, a - 25, a - 25, u6, peste, cull);
//			drawBSSquare(curX, curY, curZ - movingZ,
//							500, 500, 180, 
//					tablou, cull);
			glPopMatrix();
			
			glPushMatrix();
//			glRotated(Math.toRadians(0), 0, 1, 0);
			drawBSSquare(-actualX, -actualY, curZ2, 102, 102, curAngle, rama, cull);
			glPopMatrix();
			
	}

	void renderMagnSquareIMG5(int texture)
	{
		double[] cull = new double[4];
		cull[0] = 1;
		cull[1] = 1;
		cull[2] = 1;
		cull[3] = lupa.alphaColor2;
		
		maxOnX = curX + 50;
		minOnX = curX - 50;
		
		maxOnY = curY + 100;
		minOnY = curY - 100;
		
		double irealMaxX = 100;
		double irealMaxY = 200;
		
		
		double actualX =(double) (width - Mouse.getX()) / width * irealMaxX + minOnX;
		double actualY =(double) Mouse.getY() / height * irealMaxY + minOnY;
//		actualX = actualX / 2;
//		curZ = curZ - actualX * Math.sqrt(3) / 4;
		double curZ2 = curZ + (actualX - minOnX) * Math.sqrt(3)-87;
		
			glPushMatrix();
			drawBSSquareCropped(-actualX, -actualY, curZ2, 
					(double)(width-Mouse.getX()) / width * 512, 
					(double)(height-Mouse.getY()) / height * 512, scaleSizeForGlass, scaleSizeForGlass, 100, 100, curAngle - 180, 
					texture, cull);

//			drawBSSquare(x6, y6, z6 + 10, a - 25, a - 25, u6, peste, cull);
//			drawBSSquare(curX, curY, curZ - movingZ,
//							500, 500, 180, 
//					tablou, cull);
			glPopMatrix();
			
			glPushMatrix();
//			glRotated(Math.toRadians(0), 0, 1, 0);
			drawBSSquare(-actualX, -actualY, curZ2, 102, 102, curAngle, rama, cull);
			glPopMatrix();
			
	}

	void renderMagnSquareIMG4(int texture)
	{
		double[] cull = new double[4];
		cull[0] = 1;
		cull[1] = 1;
		cull[2] = 1;
		cull[3] = lupa.alphaColor2;
		
		maxOnX = curX + 100;
		minOnX = curX - 100;
		
		maxOnY = curY + 100;
		minOnY = curY - 100;
		
		double irealMaxX = 200;
		double irealMaxY = 200;
		
		double actualX =(double) (width - Mouse.getX()) / width * irealMaxX + minOnX;
		double actualY =(double) Mouse.getY() / height * irealMaxY + minOnY;
//		actualX = actualX / 2;
//		curZ = curZ - actualX * Math.sqrt(3) / 4;
//		double curZ2 = curZ + (actualX - minOnX) * Math.sqrt(3)-87;
		
			glPushMatrix();
			drawBSSquareCropped(-actualX, -actualY, curZ, 
					(double)(Mouse.getX()) / width * 512, 
					(double)(height-Mouse.getY()) / height * 512, scaleSizeForGlass, scaleSizeForGlass, 100, 100, curAngle - 180, 
					texture, cull);

//			drawBSSquare(x6, y6, z6 + 10, a - 25, a - 25, u6, peste, cull);
//			drawBSSquare(curX, curY, curZ - movingZ,
//							500, 500, 180, 
//					tablou, cull);
			glPopMatrix();
			
			glPushMatrix();
//			glRotated(Math.toRadians(0), 0, 1, 0);
			drawBSSquare(-actualX, -actualY, curZ, 102, 102, curAngle, rama, cull);
			glPopMatrix();
			
	}
		
	
	
	
	public void hideLupa(int h)
	{
		if(h > 0)
		{	
			if(lupa.alphaColorSinValue < 90)
			{
				lupa.alphaColorSinValue+=3;
			}
			else
				if(lupa.alphaColorSinValue >= 90)
				{
					lupa.alphaColorSinValue = 90;
					isShowingTheButtons = false;
				}
			lupa.alphaColor = Math.sin(Math.toRadians(lupa.alphaColorSinValue));
		}
		if(h < 0)
		{
			if(lupa.alphaColorSinValue > 0)
			{
				lupa.alphaColorSinValue-=10;
			}
			else
				if(lupa.alphaColorSinValue <= 0)
				{
					lupa.alphaColorSinValue = 0;
					isHidingTheButtons = false;
				}
			lupa.alphaColor = Math.sin(Math.toRadians(lupa.alphaColorSinValue));
		}
		lupa.isClicked = false;
		clickedOnGlass = false;
		lupa.isDown = false;
	}
	

	public void hideMagnGlass(int h)
	{
		if(h > 0)
		{	
			if(lupa.alphaColorSinValue2 < 90)
			{
				lupa.alphaColorSinValue2+=1;
			}
			else
				if(lupa.alphaColorSinValue2 >= 90)
				{
					lupa.alphaColorSinValue2 = 90;
				}
			lupa.alphaColor2 = Math.sin(Math.toRadians(lupa.alphaColorSinValue2));
		}
		if(h < 0)
		{
			if(lupa.alphaColorSinValue2 > 0)
			{
				lupa.alphaColorSinValue2-=1;
			}
			else
				if(lupa.alphaColorSinValue2 <= 0)
				{
					lupa.alphaColorSinValue2 = 0;
				}
			lupa.alphaColor2 = Math.sin(Math.toRadians(lupa.alphaColorSinValue2));
		}
	}
	
	
	public void addglass()
	{
		lupa.x=x + 115;
		lupa.y=y - 80;
		lupa.z=z;
		lupa.w=25;
		lupa.h=25;
		
		magnSquare.x = 0;
		magnSquare.y = 0;
		magnSquare.z = 100;
		magnSquare.h = 100;
		
//		try 
//		{	
			lupa.texture = ImagingTools.glLoadTextureLinear("pictures/glass.png");
//			lupa.texture=TextureLoader.getTexture("PNG", 
//			new FileInputStream(new File("materiale/glass.png")));

			lupa.textureHover = ImagingTools.glLoadTextureLinear("pictures/glassHover.png");
//			lupa.textureHover=TextureLoader.getTexture("PNG", 
//			new FileInputStream(new File("materiale/glassHover.png")));

			lupa.textureDown = ImagingTools.glLoadTextureLinear("pictures/glassDown.png");
//			lupa.textureDown=TextureLoader.getTexture("PNG", 
//			new FileInputStream(new File("materiale/glassDown.png")));

//			lupa.textureDown = tablou;
//		} 
//		catch (FileNotFoundException e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		catch (IOException e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	
	long val = 0;
	

	void KeysInput()
	{
		//daca apasa in sus maresc lupa, iar daca apasa in jos o micsorez
		if(lupa.isClicked == true)
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			{
				if(scaleSizeForGlass < 120)
				{
					scaleSizeForGlass++;
				}
			}
			else
				if(Keyboard.isKeyDown(Keyboard.KEY_UP))
				{
					if(scaleSizeForGlass > 0)
					{
						scaleSizeForGlass--;
					}
				}
		}
		
		//daca nu este pe imagine atunci ii dau nuanta de alb-negru, color, sepia
		if(canMove == false)
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_B))
			{
				typeOfColor[5 - currentPainting] = 1;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_C))
			{
				typeOfColor[5 - currentPainting] = 0;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				typeOfColor[5 - currentPainting] = 2;
			}

			//deschid aplicatia din C#
			if(Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				System.out.println(5 - currentPainting);
			}
		}
		
		//daca a apasat dreapta si se poate misca, atunci il misc spre dreapta
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && canMove == true)
		{
			if(isMovingRight == false && isMovingLeft == false)
			{
				nextPic = true;
				if(currentPainting == 0)
				{
					unghiMiscat = 0;
				}
				if(currentPainting < 6)
				{
					currentPainting++;
				}

				if(currentPainting == 6)
				{
					currentPainting = 0;
					unghiMiscat = -60;
				}
			}
		}

		//daca a apasat stanga si se poate misca, atunci il misc spre dreapta
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) && canMove == true)
		{
			if(isMovingLeft == false && isMovingRight == false)
			{
				previousPic = true;
				
				if(currentPainting > 0)
				{
					currentPainting--;
					setCurCoords(currentPainting);
				}
				else
					if(currentPainting == 0)
					{
						currentPainting = 5;
						setCurCoords(currentPainting);
						unghiMiscat = 360;
					}
			}
		}

		//daca se misca catre imaginea anterioara
		if(previousPic)
		{
			//micsorez unghiul pana cand a ajuns unde trebuie
			if(unghiMiscat > currentPainting * 60)
			{
				unghiMiscat--;
				isMovingLeft = true;
			}
			else
			{
				previousPic = false;
				isMovingLeft = false;
			}
		}

		//daca se misca catre imaginea urmatoare
		if(nextPic)
		{
			//maresc unghiul pana cand ajunge unde trebuie
			if(unghiMiscat < currentPainting * 60)
			{
				unghiMiscat++;
				isMovingRight = true;
			}
			else
			{
				nextPic = false;
				isMovingRight = false;
			}
		}
		
		
		//daca a apasat pe imagine sa se apropie sau departeze
		if(alreadyClickedMouse == true)
		{
			//daca se maresete, atunci ma apropii de imagine
			if(isEnlarging == true)
			{
				if(movingZ < 83)
				{
					movingZ++;
				}
				else
				{
					isEnlarging = false;
					isMinimizing = false;
					alreadyClickedMouse = false;
					isShowingTheButtons = true;
				}
			}
			else
				if(isMinimizing == true)
				{
					if(movingZ > 0)
					{
						movingZ--;
						
						isHidingTheButtons = true;
					}
					//inseamna ca a ajuns la final micsorarii si se poate misca
					//din nou printre imagini
					else
					{
						isEnlarging = false;
						isMinimizing = false;
						alreadyClickedMouse = false;
						canMove = true;
					}
				}
		}
		
		//daca ma apropii
		if(isShowingTheButtons == true)
		{
			hideLupa(1);
		}
		else
			//daca ma departez
			if(isHidingTheButtons == true)
			{
				hideLupa(-1);
			}
		
		//daca da click si nu se misca stanga sau dreapta si nu foloseste lupa
		if(Mouse.isButtonDown(0) && alreadyClickedMouse == false && 
				isMovingLeft == false && isMovingRight == false &&
				lupa.isClicked == false)
		{
			//daca se poate misca il maresc
			if(canMove == true)
			{
				isEnlarging = true;
				isMinimizing = false;
				
				canMove = false;
				alreadyClickedMouse = true;
			}
			else
				if(canMove == false)
				{
					//daca este cu mouseul pe lupa
//					if(Mouse.getX() >= 15 && Mouse.getX() <= 91 &&
//							Mouse.getY() <= 576 && Mouse.getY() >= 506)
					{
					}
					//daca nu este cu mouseul pe lupa micsoram
//					else
					{
						isEnlarging = false;
						isMinimizing = true;	
						alreadyClickedMouse = true;
					}
				}
		}

	}
	
	
	void setCurCoords(int curImage)
	{
		if(curImage == 0)
		{
			curX = x6;
			curY = y6;
			curZ = z6 + 0.1;
			curAngle = u6;
		}
		else
			if(curImage == 1)
			{
				curX = -x5;
				curY = y5;
				curZ = z5 + 0.1;
				curAngle = u5;
			}
			else
				if(curImage == 2)
				{
					curX = -x4;
					curY = y4;
					curZ = z4 - 0.1;
					curAngle = u4;
				}
				else
					if(curImage == 3)
					{
						curX = x3;
						curY = y3;
						curZ = z3 - 0.1;
						curAngle = u3;
					}
					else
						if(curImage == 4)
						{
							curX = -x2;
							curY = y2;
							curZ = z2 - 0.1;
							curAngle = u2;
						}
						else
							if(curImage == 5)
							{
								curX = -x1;
								curY = y1;
								curZ = z1 + 0.1;
								curAngle = u1;
							}
		
//		lupa.x = curX - 55;
//		lupa.y = curY;
//		lupa.z = curZ;
	}
	
	
	
	void loadTheNeededTextures()
	{
		cordx=150;
		cordy=150;
		cordw=300;
		cordh=300;
		
		for(int i = 0 ; i < 6; i++)
		{
			imaginiColor[i] = ImagingTools.glLoadTextureLinear("resources/Imagini/page" + String.valueOf(i + 1) +".png");
			imaginiBW[i] = ImagingTools.glLoadTextureLinear("resources/Imagini/page" + String.valueOf(i + 1) +"bw.png");
			imaginiS[i] = ImagingTools.glLoadTextureLinear("resources/Imagini/page" + String.valueOf(i + 1) +"s.png");
		}
		
//		try 
//		{
//			portret = ImagingTools.glLoadTextureLinear("resources/portret picasso.png");
			rama = ImagingTools.glLoadTextureLinear("resources/frame.png");
//			tablou = ImagingTools.glLoadTextureLinear("resources/tablou picasso.png");
//			peste = ImagingTools.glLoadTextureLinear("resources/017-koi-feeding-xl.png");
//			portret = TextureLoader.getTexture("JPG", new FileInputStream(new File("resources/portret picasso.jpg")));
//			tablou = TextureLoader.getTexture("JPG", new FileInputStream(new File("resources/tablou picasso.jpg")));
//			peste = TextureLoader.getTexture("JPG", new FileInputStream(new File("resources/017-koi-feeding-xl.jpg")));
//		} 
//		catch (FileNotFoundException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}

}
class buton
{
	double x=0,y=0,z=0,w=0,h=0;
	double alphaColor=0, alphaColorSinValue = 0;
	double alphaColor2=0, alphaColorSinValue2 = 0;
	int texture=0, textureHover=0, textureDown=0;
	boolean isDown=false, isUp=false, isClicked=false;
}

class MagnificationSquare
{
	double x=0,y=0,z=0,w=0,h=0;
	double alphaColor=0, alphaColorSinValue = 0;
}