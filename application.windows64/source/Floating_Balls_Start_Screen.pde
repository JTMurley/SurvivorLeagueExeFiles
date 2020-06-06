/*
This code file handles all information in relation to the background bubbles
You see flying around the screen while you are play
This is to allow a more interesting background to look at rather than
just a plain background
Nevertheless, all changes in realtion to the bubbles should be made in this file



*/


//Declaring how many bubbles we want
int NumBubbles = 100;
//Declaring the sprint of the bubbles
float Spring = 0.0001;
//Declaring the gravity of each bubble
Float Gravity = 0.0003;
//Setting the friction of each bubbles
float Friction = -0.001;

//Declaring the array that will handle the bubbles
Bubble[] Bubbles = new Bubble[NumBubbles];

//This is the only class within this file and it handles all actions with the bubbles
class Bubble{
  
  //Set the X and Y location of the bubbles
  float x,y;
  //Set the diameter of each bubble
  float Diameter;
  //Set the veloicty of each bubble
  float vx;
  float vy;
  //Set a unique ID
  int id;
  //Once again, we use the bubble array nametype alot so we change it to others
  //Just to distinguish between the two
  Bubble[] Others;
  

  //Custom bubble function to create each of the bubbles
  Bubble(float xin, float yin, float din, int idin, Bubble[] oin)
  {
    //Setting the X and Y
    x = xin;
    y = yin;
    //Setting the diameter
    Diameter = din;
    //Setting the ID
    id = idin;
    //Setting the others array to be the actual bubble array
    Others = oin;
  }
  
  //Collision function between each of the bubbles
  void Collide()
  {
    //Loop through each of the bubbles
    for (int i = id + 1; i < NumBubbles; i++)
    {
      //Setting the direction of each bubble
      float dx = Others[i].x - x;
      float dy = Others[i].y - y;
      //Setting the distance
      float Distance = sqrt(dx*dx + dy*dy);
      //Setting the min distance 
      float MinDist = Others[i].Diameter/2 + Diameter/2;
      //If the distance is less than mindist
      if (Distance < MinDist)
      {
        //Declaring the angle of the bubble
        float Angle = atan2(dy, dx);
        //Setting the target X and Y
        float TargetX = x + cos(Angle) * MinDist;
        float TargetY = Y + sin(Angle) * MinDist;
        //Setting the accerleration X and Y
        float ax = (TargetX - Others[i].x) * Spring;
        float ay = (TargetY - Others[i].y) * Spring;
        //Changing the velocity based off of the accerlation
        vx -= ax;
        vy -= ay;
        //Setting the velocity to be the accleration
        Others[i].vx += ax;
        Others[i].vy += ay;
      }
    }
  }
  
  //Movement function for each of the bubbles
  void Move()
  {
    //Setting the velocity to add in the gravity
    vy += Gravity;
    //Setting the X and Y of the velocity
    x += vx;
    y += vy;
    
    //Ensuring that the bubbles cant go off the screen
    //Checking the left of the screen
    if (x + Diameter/2 > width)
    {
      //If at the left ensure it cant go left
      x = width - Diameter/2;
      vx *= Friction;
    }
    //Check the rihgt of the screen
    else if (x - Diameter/2 < 0)
    {
      //If ar the right of the screen stop it from going over
      x = Diameter/2;
      vx *= Friction;
    }
    //Checking the top of the screen
    if (y + Diameter/2 > height)
    {
      //If at the top stop it from going over
      y = height - Diameter/2;
      vy *= Friction;
    }
    //CHecking the bottom of the screen
    else if (y - Diameter/2 < 0)
    {
      //If at the bottom stop it from going over
      y = Diameter/2;
      vy *= Friction;
    }
  }
  
  //Basic display function to display the bubbles on the screen
  void Display()
  {
    ellipse(x,y, Diameter, Diameter);
  }
}
