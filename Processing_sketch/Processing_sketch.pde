import processing.serial.*;
import http.requests.*;
Serial myPort;
String val;




void setup()
{
  // I know that the first port in the serial list on my mac
  // is Serial.list()[0].
  // On Windows machines, this generally opens COM1.
  // Open whatever port is the one you're using.
  String portName = Serial.list()[0]; //change the 0 to a 1 or 2 etc. to match your port
  myPort = new Serial(this, portName, 115200);
}
void draw()
{
  if ( myPort.available() > 0) 
  {  // If data is available,
  val = myPort.readStringUntil('\n');  // read it and store it in val
  } 
  
  if(val != null){
    println("sending post...");
    PostRequest post = new PostRequest("http://braindrain.mtantwerpen.eu");
    post.addData("arduino_id", val);
    post.send();
    println("Reponse Content: " + post.getContent());
    //println("Reponse Content-Length Header: " + post.getHeader("Content-Length"));
    println("...post is send");

    val = null;
    delay(2000);
      
  }
}
