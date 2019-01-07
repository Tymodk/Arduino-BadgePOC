import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import http.requests.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Processing_sketch extends PApplet {



Serial myPort;
String val;
String text;
PFont f;


public void setup()
{
  
  f = createFont("Arial",16,true); // STEP 2 Create Font
  // I know that the first port in the serial list on my mac
  // is Serial.list()[0].
  // On Windows machines, this generally opens COM1.
  // Open whatever port is the one you're using.
  String portName = Serial.list()[0]; //change the 0 to a 1 or 2 etc. to match your port
  myPort = new Serial(this, portName, 115200);
  
  text = "";
}
public void draw()
{
  if ( myPort.available() > 0) 
  {  // If data is available,
  val = myPort.readStringUntil('\n');  // read it and store it in val
  }   
  background(255);  // STEP 5 Display Text
  textFont(f,16);                  // STEP 3 Specify font to be used
  fill(0);
  text(text,10,20);// STEP 4 Specify font color 
    
  if(val != null){
    text = val;
     
    println("sending post..." + val);
    PostRequest post = new PostRequest("http://badge.braindrain.mtantwerp.eu/api/new-presence");
    post.addData("arduino_id", val);
    post.send();
    println("Reponse Content: " + post.getContent());
    println("Reponse Content-Length Header: " + post.getHeader("Content-Length"));
    println("...post is send");

    val = null;
    delay(2000);
      
  }
}
  public void settings() {  size(200,30); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Processing_sketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
