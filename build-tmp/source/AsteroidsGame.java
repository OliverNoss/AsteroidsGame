import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class AsteroidsGame extends PApplet {

//Oliver Noss CompSci Block4
//make a marked for death function in bullet class, remove all remaining listofDeath stuff
SpaceShip voyager = new SpaceShip();
Star [] galaxy = new Star[200];
ArrayList <Asteroid> belt = new ArrayList <Asteroid>();
ArrayList <Bullet> magazine = new ArrayList <Bullet>();
boolean gameover = false;
boolean win;
int asterstroyed;
boolean godmode=false;
public void setup() 
{
  asterstroyed=0;
  gameover = false;
  size(700,700);
  background(0);
  stroke(255);
  
  for (int i = 0; i < galaxy.length; i++) {
    galaxy[i] = new Star();
  }
  // for (int i = 0; i < 44; i++) {
    while (belt.size()<80)
    {
    belt.add(new Asteroid());
  }
}
public void draw() 
{
  if (!gameover)
  {// System.out.println(magazine.size());
    background(0);
    for (int i = 0; i < galaxy.length; i++) {
      galaxy[i].show();
    }
    for (int i = 0; i < belt.size(); i++) {
      belt.get(i).show();
      belt.get(i).move();
    }
    strokeWeight(1);
    voyager.rockets();
    voyager.show();
    voyager.move();
    for (int i = 0; i < magazine.size(); i++) { 
      magazine.get(i).show();
      magazine.get(i).move();
      if (magazine.get(i).bulletEdge() || magazine.get(i).getDeath())
        magazine.remove(i);
    } 
    collide();

  // fade();
}
else {
  if (win)
    {
      stroke(0,255,0);
      fill(0,255,0);
      textSize(20);
      textAlign(CENTER);
      text("You somehow managed to win, cheater", 370,200);
      
    }
  else if(!win)
  {
    
    stroke(255,0,0);
      fill(255,0,0);
      textSize(20);
      textAlign(CENTER);
      text("You destroyed "+ asterstroyed + " asteroids before your inevitable demise.",370,200);
  }
}
}
public void mousePressed()
{
  if (gameover)
  {
    setup();
    gameover=false;
    voyager.setLives(5);
  }
}
public void keyPressed()
{
  if (key == 'a')
  {
    voyager.rotate(-5);
  }
  if (key == 'd')
  {
    voyager.rotate(5);
  }
  if (key == 'w')
  {
    voyager.accelerate(-.1f);
  }
  if (key == 's')
  {
    voyager.accelerate(.05f);
  }
  if (key == 'h')
    voyager.hyperspace();
  if (key== ' ')
    magazine.add(new Bullet(voyager));
  if(godmode)
  {
  if (key == CODED)
  {
    if (keyCode == UP)
      belt.add(new Asteroid());
    if (keyCode==DOWN && belt.size()>0)
      belt.remove(belt.size()-1);
  }
}
  if(key=='O')
  {
    godmode=!godmode;
  }
}
class SpaceShip extends Floater  
{
  private int lives;
  public SpaceShip()
  {
    corners=5;
    xCorners = new int[corners];
    yCorners = new int[corners];
    xCorners[0] = -10;
    xCorners[1] = 10;
    xCorners[2] = 5;
    xCorners[3] = 5;
    xCorners[4] = 10;
    yCorners[0] = 0;
    yCorners[1] = 8;
    yCorners[2] = 4;
    yCorners[3] = -4;
    yCorners[4] = -8;
    myColor = color(0,100,255);
    myCenterX = 350;
    myCenterY = 350;
    myDirectionX = 0;
    myDirectionY = 0;
    myPointDirection = 0;
    lives=5;
  }
  public void hyperspace()
  {
    myDirectionY=0;
    myDirectionX=0;
    myCenterX=(int)(Math.random()*700);
    myCenterY=(int)(Math.random()*700);
    myPointDirection=(int)(Math.random()*360);
  }
  public void rockets()
  {
    if(keyPressed && key=='w')
    {
      noStroke();
      fill(220,100,0,28);
      // ellipse((float)(myCenterX+6*Math.cos(myPointDirection*(Math.PI/180))), (float)(myCenterY+6*Math.sin(myPointDirection*(Math.PI/180))),8,8);
      fade((float)(myCenterX+8*Math.cos(myPointDirection*(Math.PI/180))), (float)(myCenterY+8*Math.sin(myPointDirection*(Math.PI/180))),15);
    }
    if(keyPressed && key=='s')
    {
     noStroke();
     fill(200,100,0,50);
     fade((float)(myCenterX-10*Math.cos(myPointDirection*(Math.PI/180))), (float)(myCenterY-10*Math.sin(myPointDirection*(Math.PI/180))),7);
   }
 }
 public void setX(int x) {myCenterX=x;}  
 public int getX() {return (int)myCenterX;}
 public void setY(int y) {myCenterY=y;}   
 public int getY() {return (int)myCenterY;}   
 public void setDirectionX(double x) {myDirectionX=x;}   
 public double getDirectionX() {return myDirectionX;}   
 public void setDirectionY(double y) {myDirectionY=y;}   
 public double getDirectionY() {return myDirectionY;}   
 public void setPointDirection(int degrees) {myPointDirection=degrees;}   
 public double getPointDirection() {return myPointDirection;} 
 public void setLives(int x) {lives=x;}  
 public int getLives() {return lives;}
}//
abstract class Floater //Do NOT modify the Floater class! Make changes in the SpaceShip class 
{   
  protected int corners;  //the number of corners, a triangular floater has 3   
  protected int[] xCorners;   
  protected int[] yCorners;   
  protected int myColor;   
  protected double myCenterX, myCenterY; //holds center coordinates   
  protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel   
  protected double myPointDirection; //holds current direction the ship is pointing in degrees    
  abstract public void setX(int x);  
  abstract public int getX();   
  abstract public void setY(int y);   
  abstract public int getY();   
  abstract public void setDirectionX(double x);   
  abstract public double getDirectionX();   
  abstract public void setDirectionY(double y);   
  abstract public double getDirectionY();   
  abstract public void setPointDirection(int degrees);   
  abstract public double getPointDirection(); 

  //Accelerates the floater in the direction it is pointing (myPointDirection)   
  public void accelerate (double dAmount)   
  {          
    //convert the current direction the floater is pointing to radians    
    double dRadians =myPointDirection*(Math.PI/180);     
    //change coordinates of direction of travel    
    myDirectionX += ((dAmount) * Math.cos(dRadians));    
    myDirectionY += ((dAmount) * Math.sin(dRadians));       
  }   
  public void rotate (int nDegreesOfRotation)   
  {     
    //rotates the floater by a given number of degrees    
    myPointDirection+=nDegreesOfRotation;   
  }   
  public void move ()   //move the floater in the current direction of travel
  {      
    //change the x and y coordinates by myDirectionX and myDirectionY       
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;     

    //wrap around screen    
    if(myCenterX >width)
    {     
      myCenterX = 0;    
    }    
    else if (myCenterX<0)
    {     
      myCenterX = width;    
    }    
    if(myCenterY >height)
    {    
      myCenterY = 0;    
    }   
    else if (myCenterY < 0)
    {     
      myCenterY = height;    
    }   
  }   
  public void show ()  //Draws the floater at the current position  
  {             
    fill(myColor);   
    stroke(myColor);    
    //convert degrees to radians for sin and cos         
    double dRadians = myPointDirection*(Math.PI/180);                 
    int xRotatedTranslated, yRotatedTranslated;    
    beginShape();         
    for(int nI = 0; nI < corners; nI++)    
    {     
      //rotate and translate the coordinates of the floater using current direction 
      xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);     
      yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);      
      vertex(xRotatedTranslated,yRotatedTranslated);    
    }   
    endShape(CLOSE);  
  }   
} 
class Star
{
  private float myX,myY,myDiam; 
  public Star() 
  {
    myX = (float)(Math.random()*700);
    myY = (float)(Math.random()*700);
    myDiam = (float)(Math.random()*4 + 1);
  }
  public void show()
  {
    stroke(255);
    strokeWeight(1);
    point(myX,myY);
    noStroke();
    fill(255,255,255,50);
    fade(myX,myY,myDiam);
    
    
  }
}//
//fade
public void fade(float x, float y, double d)
{
  // private int diameter;
  // System.out.println(1);
  // stroke(255,255,255,10);
  noStroke();
  // fill(255,255,255,5);
  for(double diameter = d; diameter>1; diameter--)
  {
    ellipse(x,y,(float)diameter,(float)diameter);
  }
}
class Asteroid extends Floater
{
  private int mySize, type, spin;
  // private int type;
  Asteroid()
  {
    type = (int)(Math.random()*3+1);
    mySize = (int)(Math.random()*6+2);
    myCenterX=(int)(Math.random()*500);
    myCenterY=(int)(Math.random()*500);
    myDirectionX=Math.random()*6-3;
    myDirectionY=Math.random()*6-3;
    spin=(int)(Math.random()*10-5);
    while (spin == 0) {spin=(int)(Math.random()*10-5);}
    myPointDirection=(int)(Math.random()*360);
    myColor=color(110,40,0);
    //initialize variables and then design asteroid on graph paper, then make different sizes/shapes
    
    // xCorners=new int[corners];
    // yCorners=new int[corners];
    int [] x1 = {3*mySize,4*mySize,2*mySize,-2*mySize,-3*mySize,-4*mySize,-3*mySize,-2*mySize,0*mySize,1*mySize,2*mySize,4*mySize};
    int [] y1 = {0*mySize,2*mySize,4*mySize,3*mySize,2*mySize,0*mySize,-2*mySize,-2*mySize,-4*mySize,-4*mySize,-3*mySize,-2*mySize};
    int [] x2 = {0*mySize,1*mySize,3*mySize,4*mySize,4*mySize,2*mySize,1*mySize,-1*mySize,-2*mySize,-2*mySize,-4*mySize,-5*mySize,-3*mySize};
    int [] y2 = {-4*mySize,-3*mySize,-2*mySize,1*mySize,3*mySize,4*mySize,2*mySize,2*mySize,3*mySize,4*mySize,3*mySize,-1*mySize,-3*mySize};
    int [] x3 = {3*mySize,3*mySize,1*mySize,-2*mySize,-3*mySize,-4*mySize,-2*mySize,0*mySize,3*mySize,4*mySize,5*mySize};
    int [] y3 = {0*mySize,2*mySize,4*mySize,4*mySize,3*mySize,-1*mySize,-1*mySize,-3*mySize,-4*mySize,-3*mySize,-1*mySize};
    if(type==1)
    {
      corners=12;
      xCorners=x1;
      yCorners=y1;
    }
    else if(type==2)
    {
      corners=13;
      xCorners=x2;
      yCorners=y2;
    }
    else if(type==3)
    {
      corners=11;
      xCorners=x3;
      yCorners=y3;
    }
  }
  public void setX(int x) {myCenterX=x;}  
  public int getX() {return (int)myCenterX;}
  public void setY(int y) {myCenterY=y;}   
  public int getY() {return (int)myCenterY;}   
  public void setDirectionX(double x) {myDirectionX=x;}   
  public double getDirectionX() {return myDirectionX;}   
  public void setDirectionY(double y) {myDirectionY=y;}   
  public double getDirectionY() {return myDirectionY;}   
  public void setPointDirection(int degrees) {myPointDirection=degrees;}   
  public double getPointDirection() {return myPointDirection;} 
  public int getSize() {return mySize;}
  public void move()
  {
    rotate(spin);
    super.move();
  }
  public void destroy()
  {
    // System.out.println("Boom");
    fill(225,80,0,20);
    fade((float)myCenterX,(float)myCenterY,mySize*11);
    asterstroyed++;
    // ellipse((float)myCenterX,(float)myCenterY,mySize*11,mySize*11);
  }

}//
// 
public void collide()
{
  for (int i = 0; i < belt.size(); i++)//turn into normal for loop so remove works
  {
    if (dist(belt.get(i).getX(),belt.get(i).getY(),voyager.getX(),voyager.getY()) < belt.get(i).getSize()*3+10)
    {
      belt.get(i).destroy();
      belt.remove(i);
      if (!godmode)
      voyager.setLives(voyager.getLives()-1);
      if (voyager.getLives()==0)
      {
        gameover=true;
        win=false;
      }
      else if (voyager.getLives()>0 && belt.size()==0)
      {
       gameover=true;
       win=true;
     }
   }
 }
  for (int j = 0; j < magazine.size(); j++)//turn into normal for loop so remove works
  {
    for (int k = 0; k < belt.size(); k++)
    {
      // System.out.println("2nd for");
      if (dist(belt.get(k).getX(),belt.get(k).getY(),magazine.get(j).getX(),magazine.get(j).getY()) < belt.get(k).getSize()*3+2)
      {
        belt.get(k).destroy();
        magazine.get(j).setDeath(true);
        belt.remove(k);
        if (voyager.getLives()>0 && belt.size()==0)
      {
       gameover=true;
       win=true;
     }
      }

    }

  }
}
class Bullet extends Floater
{
  private boolean isDead;
  public Bullet(SpaceShip shipName)
  {
    myCenterX=shipName.getX();
    myCenterY=shipName.getY();
    myPointDirection=shipName.getPointDirection();
    double dRadians = myPointDirection*(Math.PI/180);
    myDirectionX = -5*Math.cos(dRadians)+shipName.getDirectionX();
    myDirectionY = -5*Math.sin(dRadians)+shipName.getDirectionY();
    isDead=false;
  }
  public void show()
  {
      // stroke(0,255,0);
      fill(0,250,0);
      ellipse((float)myCenterX,(float)myCenterY,5,5);
    }
  //   public void move()
  //   {
  //     myCenterX += myDirectionX;    
  //     myCenterY += myDirectionY;     

  //   //wrap around screen    
  //   if(myCenterX > width+5)
  //   {     
  //     myCenterX = 0;    
  //   }    
  //   else if (myCenterX < 0-5)
  //   {     
  //     myCenterX = width;    
  //   }    
  //   if(myCenterY > height+5)
  //   {    
  //     myCenterY = 0;    
  //   }   
  //   else if (myCenterY < 0-5)
  //   {     
  //     myCenterY = height;    
  //   } 
  // }  
  public void setX(int x) {myCenterX=x;}  
  public int getX() {return (int)myCenterX;}
  public void setY(int y) {myCenterY=y;}   
  public int getY() {return (int)myCenterY;}   
  public void setDirectionX(double x) {myDirectionX=x;}   
  public double getDirectionX() {return myDirectionX;}   
  public void setDirectionY(double y) {myDirectionY=y;}   
  public double getDirectionY() {return myDirectionY;}   
  public void setPointDirection(int degrees) {myPointDirection=degrees;}   
  public double getPointDirection() {return myPointDirection;} 
  public void setDeath(boolean bTemp) {isDead=bTemp;}
  public boolean getDeath() {return isDead;}
  public void move()
  {
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;  
  }
  
  public boolean bulletEdge()
  {
  // boolean temp = false;
  // boolean secondcrossing = false;  
  if (myCenterX>width||myCenterX<0||myCenterY>height||myCenterY<0)
   return true;
 else 
  return false;

}
}//stop the bullets
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
