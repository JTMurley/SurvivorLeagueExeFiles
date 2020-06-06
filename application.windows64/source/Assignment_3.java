import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Assignment_3 extends PApplet {

/*
This is the base Root File and should only be changed when you need to add or remove user interactions
Or add in new class objects or resources into the main setup function as you can see below. Furthermore this root file
Should serve as a container for all smaller functions to add onto the game and possible extensions might be to create a specific
file setup tab just for resources however due to having very few external resources we have them in this setup function directly
Futhermore, all librarys used should be included at the top of this file under the libarays section
*/

//Libarys ----- -------------

//Importing the entire sound libaray

//Creating a variable to hold the start screen music
SoundFile StartScreenMusic;
SoundFile ChompFishEat;
// -----------------------------

public void setup() 
{
  //Setting the framerate to 30 as this works best with the current players fish gif
  frameRate(30);
  
  //Enabling smooth just to make the game look that tiny bit neater
    
  
  //Setting the Size of the screen
  
  
  //Importing fish images we are going to use
  SmallFishImg = loadImage("data/smallFish.png");
  MediumFishImg = loadImage("data/MediumFish.png");
  LargeFishImg = loadImage("data/LargeFish.png");
  
  //Importing the  music used in the game
  StartScreenMusic = new SoundFile(this, "data/StartScreenMusic.mp3");
  ChompFishEat = new SoundFile(this, "data/Chomp.mp3");
  
  //Appling the text font we use within the select difficulty buttons
  PFont font = createFont("data/DoHyeon-Regular.ttf", 60);
  textFont(font);
  
  //Aligning the text center of center
  textAlign(CENTER, CENTER);
  
  //Class object for the start screen
  StartUpScreen = new StartScreen();
  
  //Class object for players Fish
  PFish = new PlayersFish();
  
  //Class object for the spawning of background objects like the bubbles
  BackGroundSpawn = new SpawnBackGroundObjects();
  
  //Calling the AIFishSpawning function to intially spawn in all the fish
  BackGroundSpawn.AIFishSpawning();

  //Calling the startup functions for seperate classes, we have made it like this to not only make
  //This start up function smaller and cleaner but to make the code modular
  PFish.PlayerFishSetUp();
  StartUpScreen.StartUpScreenSetUpVariables();
}

public void draw() 
{
  //Constantly setting our background to add layers to our game to remove or add objects as we want 
  background(0, 169, 204);
  
  //Calling the seperate functions to handle the bubble and fish interactions
  BackGroundSpawn.HandleBubbleInteraction();
  BackGroundSpawn.HandleAIFishInteractions();
  BackGroundSpawn.UpdateAiFishMovement();
  
  //Calling our functions that need to be updated constantly in the players fish file
  PFish.Update();
  
  //Calling the select your difficulty function in the draw constantly to enable us to layer the objects inside and technically remove them from the screen
  StartUpScreen.SelectYourDifficulty();
  
  //Calling other functions in the startup screen that need to be updated constantly
  StartUpScreen.Update();
 
}


/*
All functions to control the users fish should be added below
*/

//This function just checks to see wheter or not which button has been pressed and when to apply the tint or not
public void mousePressed() {
  //First check to see if the screen has been cleared or not and this reduces the overhead from going through all the if statements
  if (ClearedScreens == false)
  {
    //If the users mouse is over the easy diff button and is clicked then apply easy settings
    if (EasyDiffOver == true)
    {
      //Clear the screen
      EasyDiffSelected = true;
      StartUpScreen.ClearScreen();
    }
    //If the users mouse is over the medium diff button and is clicked then apply medium settings
    else if (MediumDiffOver == true)
    {
      //Clear the screen
      MediumDiffSelected = true;
      StartUpScreen.ClearScreen();
    }
    //If the users mouse is over the hard diff button and is clicked then apply hard settings
    else if (HardDiffOver == true)
    {
      //Clear the screen
      HardDiffSelected = true;
      StartUpScreen.ClearScreen();
    }
  }
}

//This key pressed function handles the players speed and movement throughout the game
public void keyPressed()
{
  //If down on keypad, send the players fish down the screen

  if ((keyCode == DOWN ) || (keyCode == 'S'))
  {
    //Setting a limit of the players fish speed
    if (playYSpeed <=10)
    {
      //Increasing thier speed in the correct direction
      playYSpeed += 1;
    }
  }

  //If up on key pad, send the ball up the screen
  else if ((keyCode == UP) || (keyCode == 'W'))
  {
    //Setting a limit of the players fish speed
    if (playYSpeed >= -10)
    {
      //Increasing thier speed in the correct direction
      playYSpeed -= 1;
    }
  }

  //Decrease the X speed to make it move left
  else if ((keyCode == LEFT) || (keyCode == 'A'))
  {
    //Setting a limit of the players fish speed
    if (playXSpeed >= -10)
    {
      //Increasing thier speed in the correct direction
      playXSpeed -= 1;
    }
  }

  //Increase the X speed to make it move right
  else if ((keyCode == RIGHT) || (keyCode == 'D'))
  {
    //Setting a limit of the players fish speed
    if (playXSpeed <= 10)
    {
      //Increasing thier speed in the correct direction
      playXSpeed += 1;
    }
  }

  //If user pressed X, stop the fish completely completely
  else if (key =='x')
  {
    //Set both X and Y values to 0 to stop the players fish
    playXSpeed = 0;
    playYSpeed = 0;
  }
}
/*
This class holds all information relating to the Autonomous fish, this includes
 Spawning them, the movement for each class, Collission between other fish classes as
 well as regulating their own speed and ensuring that they are always on the screen
 
 */

//Setting up the variables to hold the AI fish images
PImage SmallFishImg, MediumFishImg, LargeFishImg;

//Declaring how many Fish we want in each class
int NumSmallFish = 5;
int NumMediumFish = 5;
int NumLargeFish = 5;

//Declaring the intial movement speed of the AI fish
float SmallFishXMovement = 0.1f;
float SmallFishYMovement = 0.1f;

float MediumFishXMovement = 0.1f;
float MediumFishYMovement = 0.1f;

float HardFishXMovement = 0.1f;
float HardFishYMovement = 0.1f;

//Creating the class object for the BackGroundSpawn Class
SpawnBackGroundObjects BackGroundSpawn;

//Creating the arrays to hold the fish ID's and other relevant information
SmallFish[] SmallFishies = new SmallFish[NumSmallFish];

MediumFish[] MediumFishies = new MediumFish[NumMediumFish];

LargeFish[] LargeFishies = new LargeFish[NumLargeFish];

//This class holds all information relating to the small fish class
class SmallFish
{
  //Declaring all necessary variables that we need to spawn in the fish

  //X and Y location of each fish
  float x, y;
  //Diamter of each fish
  float Diameter;
  //Unique ID for each fish
  int id;
  //Declaring the array as others just to distinguish from the main smallfish array used in other parts
  SmallFish[] Others;
  //Creating the function to spawn and create each of the fish
  SmallFish(float xin, float yin, float din, int idin, SmallFish[] oin)
  {
    //Setting the X and Y locations
    x = xin;
    y = yin;

    //Setting the diameter
    Diameter = din;
    //Setting the ID
    id = idin;
    //Setting others to the array of values
    Others = oin;
  }

  //Basic function to actually visualise each of the fish from the variables above
  public void Display()
  {
    image(SmallFishImg, x, y, Diameter, Diameter);
  }

  //This function handles the logic behind the movement of the small fish
  public void Move()
  {
    //Loop through every ID in the array
    for (int i = id; i < NumSmallFish; i++)
    {
      //If the user has selected the hard difficulty
      if (HardDiffSelected == true)
      {
        //Set the fish to be reactive to the players movements ;D
        Others[i].x = Others[i].x + (playXSpeed * playXDirection );
        Others[i].y = Others[i].y + (playYSpeed * PlayYDirection );
      }
      //If the user has selected the medium difficulty
      if (MediumDiffSelected == true)
      {
        //Set the fish's speed to be the fish's unique speed and direction
        Others[i].x = Others[i].x + SmallFishXMovement * playXDirection;
        Others[i].y = Others[i].y + SmallFishYMovement * PlayYDirection;
      } else
      {
        //Default to the original function, that statement above is if the user wants
        //To change how the medium difficulty functions
        Others[i].x = Others[i].x + SmallFishXMovement * playXDirection;
        Others[i].y = Others[i].y + SmallFishYMovement * PlayYDirection;
      }
    }

    //Ensuring the fish cant go off the screen
    //This just stops the fish at the borders of the screen
    //Left
    if (x + Diameter/2 > width)
    {
      x = width - Diameter/2;
    } 
    //Right
    else if (x - Diameter/2 < 0)
    {
      x = Diameter/2;
    }
    //Top
    if (y + Diameter/2 > height)
    {
      y = height - Diameter/2;
    }
    //Bottom
    else if (y - Diameter/2 < 0)
    {
      y = Diameter/2;
    }
  }
}

//This class holds all information relating to the small fish class
class MediumFish
{
  //X and Y location of the medium fish
  float x, y;
  //Diamter of the fish
  float Diameter;
  //Unique ID of the fish
  int id;
  //Declaring the array as others just to distinguish from the main smallfish array used in other parts
  MediumFish[] Others;
  MediumFish(float xin, float yin, float din, int idin, MediumFish[] oin)
  {
    //Setting the X and Y locations
    x = xin;
    y = yin;
    //Setting the diameter
    Diameter = din;
    //Setting the unique ID
    id = idin;
    //Setting the others array to be equal to the proper array 
    Others = oin;
  }

  //Basic display function to create the fish
  public void Display()
  {
    image(MediumFishImg, x, y, Diameter, Diameter);
  }

  //This move function handles the logic behind moving the fish
  public void Move()
  {
    //Looping through each ID in the array
    for (int i = id; i < NumMediumFish; i++)
    {
      //If the hard difficulty is selected
      if (HardDiffSelected == true)
      {
        //Setting the fish's movements to be the players input
        Others[i].x = Others[i].x + (playXSpeed * playXDirection );
        Others[i].y = Others[i].y + (playYSpeed * PlayYDirection );
      }

      //If the medium difficulty is selected
      if (MediumDiffSelected == true)
      {
        //Set the fish's speed to be their unique speed values
        Others[i].x = Others[i].x + MediumFishXMovement * playXDirection;
        Others[i].y = Others[i].y + MediumFishYMovement * PlayYDirection;
      } else
      {
        //Like before, medium difficulty is included just incase the user wants to add
        //A unique function
        Others[i].x = Others[i].x + MediumFishXMovement * playXDirection;
        Others[i].y = Others[i].y + MediumFishYMovement * PlayYDirection;
      }
    }

    //Prevents the fish from leaving the screen as well as randomises thier speed
    //Making the game that much harder to actually win as the medium fish
    //Now have a higher chance of spawning and dieing by the larger fish and
    //Possibly spawning ontop of you
    //Left
    if (x + Diameter/2 > width)
    {
      MediumFishXMovement = random(-6, 6);
      MediumFishYMovement = random(-6, 6);
      x = width - Diameter/2;
    } 
    //Right
    else if (x - Diameter/2 < 0)
    {
      MediumFishXMovement = random(-6, 6);
      MediumFishYMovement = random(-6, 6);
      x = Diameter/2;
    }
    //Top
    if (y + Diameter/2 > height)
    {
      MediumFishXMovement = random(-6, 6);
      MediumFishYMovement = random(-6, 6);
      y = height - Diameter/2;
    } 
    //Bottom
    else if (y - Diameter/2 < 0)
    {
      MediumFishXMovement = random(-6, 6);
      MediumFishYMovement = random(-6, 6);
      y = Diameter/2;
    }
  }
  //Checking to see if the medium fish collides with a smaller fish
  public void SmallFishCollision()
  {
    //If the user has selected a difficulty and screens been cleared
    if (ClearedScreens == true)
    {
      //Loop through every small fish
      for (int i = 0; i < NumSmallFish; i++)
      {
        //Loop through every medium fish
        for (int ii = 0; ii < NumMediumFish; ii++)
        {
          //If a collision is detected between the two fish, note this is a very basic collision detection at the moment that does not take into account the white space in the images
          if (MediumFishies[ii].y + MediumFishies[ii].Diameter > SmallFishies[i].y - SmallFishies[i].Diameter && MediumFishies[ii].y < SmallFishies[i].y + SmallFishies[i].Diameter && MediumFishies[ii].x + MediumFishies[ii].Diameter > SmallFishies[i].x - SmallFishies[i].Diameter && MediumFishies[ii].x < SmallFishies[i].x + SmallFishies[i].Diameter)
          {
            //Respawn the small fish at a random location
            SmallFishies[i].x = random(1800);
            SmallFishies[i].y = random(930);
          }
        }
      }
    }
  }
}

//THis is the class for the large fish and handles all information relating to the hard fish
class LargeFish
{
  //Setting the X and Y variables for the fish
  float x, y;
  //Setting the diameter varibles
  float Diameter;
  //Setting the unique ID for the fish
  int id;
  //Declaring the array as others just to distinguish from the main smallfish array used in other parts
  LargeFish[] Others;
  //Function to create the large fish
  LargeFish(float xin, float yin, float din, int idin, LargeFish[] oin)
  {
    //Setting the X and Y varibles
    x = xin;
    y = yin;
    //Setting the diamter of the fish
    Diameter = din;
    //Setting the ID
    id = idin;
    //Setting the others array to equal the large fish array
    Others = oin;
  }

  //Basic Display function to display the fish
  public void Display()
  {
    image(LargeFishImg, x, y, Diameter, Diameter);
  }

  //Function to handle the logic behind how the large fish moves
  public void Move()
  {
    //Looping through every hard fish
    for (int i = id; i < NumLargeFish; i++)
    {
      //If the hard difficulty is selected
      if (HardDiffSelected == true)
      {
        //Set the hard fish movements to be the same as the players
        Others[i].x = Others[i].x + (playXSpeed * playXDirection );
        Others[i].y = Others[i].y + (playYSpeed * PlayYDirection );
      }
      //if the medium difficulty is selected
      if (MediumDiffSelected == true)
      {
        //Set their movement to be their own specific movement variable
        Others[i].x = Others[i].x + HardFishXMovement * playXDirection;
        Others[i].y = Others[i].y + HardFishYMovement * PlayYDirection;
      } else
      {
        //Note that the medium difficulty above like before was only put in place
        //In case someone else wants to modify the medium difficulty
        Others[i].x = Others[i].x + HardFishXMovement * playXDirection;
        Others[i].y = Others[i].y + HardFishYMovement * PlayYDirection;
      }
    }

    //Ensuring that the Fish dosnt go off the edge of the screen as well as randomise
    //Their movement when they hit to make the game that much harder
    //Left
    if (x + Diameter/2 > width)
    {
      HardFishXMovement = random(-6, 6);
      HardFishYMovement = random(-6, 6);
      x = width - Diameter/2;
    } 
    //Right
    else if (x - Diameter/2 < 0)
    {
      HardFishXMovement = random(-6, 6);
      HardFishYMovement = random(-6, 6);
      x = Diameter/2;
    }
    //Bottom
    if (y + Diameter/2 > height)
    {
      HardFishXMovement = random(-6, 6);
      HardFishYMovement = random(-6, 6);
      y = height - Diameter/2;
    } else if (y - Diameter/2 < 0)
    {
      //Top
      HardFishXMovement = random(-6, 6);
      HardFishYMovement = random(-6, 6);
      y = Diameter/2;
    }
  }

  //Function to see if a large fish has collided with a smaller fish
  public void SmallFishCollision()
  {
    //If the game has started and the screen is clear
    if (ClearedScreens == true)
    {
      //Loop through each of the smaller fish
      for (int i = 0; i < NumSmallFish; i++)
      {
        //Loop through each larger fish
        for (int ii = 0; ii < NumLargeFish; ii++)
        {
          //if a collision is detected at a very basic level
          if (LargeFishies[ii].y + LargeFishies[ii].Diameter > SmallFishies[i].y - SmallFishies[i].Diameter && LargeFishies[ii].y < SmallFishies[i].y + SmallFishies[i].Diameter && LargeFishies[ii].x + LargeFishies[ii].Diameter > SmallFishies[i].x - SmallFishies[i].Diameter && LargeFishies[ii].x < SmallFishies[i].x + SmallFishies[i].Diameter)
          {
            //Respawn the small fish at a random location on the screen
            SmallFishies[i].x = random(1800);
            SmallFishies[i].y = random(930);
          }
        }
      }
    }
  }

  //Function to see if a collision has been detected between a large and medium sized fish
  public void MediumFishCollision()
  {
    // if the screen has been cleared and the playing is playing the game
    if (ClearedScreens == true)
    {
      //Loop through every medium fish
      for (int i = 0; i < NumMediumFish; i++)
      {
        //Loop through every large fish
        for (int ii = 0; ii < NumLargeFish; ii++)
        {
          //If a collision is detected at a very level between the two fish
          if (LargeFishies[ii].y + LargeFishies[ii].Diameter > MediumFishies[i].y - MediumFishies[i].Diameter && LargeFishies[ii].y < MediumFishies[i].y + MediumFishies[i].Diameter && LargeFishies[ii].x + LargeFishies[ii].Diameter > MediumFishies[i].x - MediumFishies[i].Diameter && LargeFishies[ii].x < MediumFishies[i].x + MediumFishies[i].Diameter)
          {
            //Respawn the mediun fish at a random location
            MediumFishies[i].x = random(1800);
            MediumFishies[i].y = random(930);
          }
        }
      }
    }
  }
}

//This class handles the spawning and iterating through of each class to actually spawn and handle the
//Functions between each class
class SpawnBackGroundObjects
{
  //Function which jusr handles spawning all objects
  public void AIFishSpawning()
  {
    //Creating all the bubbles
    for (int i = 0; i < NumBubbles; i++)
    {
      //Using the bubble function within the floating balls start screen
      Bubbles[i] = new Bubble(random(width), random(height), random(30, 70), i, Bubbles);
      //Filling it that unique color to provide the bubble effect
      fill(0, 169, 204);
    }

    //Creating all small fish
    for (int i = 0; i < NumSmallFish; i++)
    {
      //Using the small fish function we made above
      SmallFishies[i] = new SmallFish(random(width), random(height), random(30, 50), i, SmallFishies);
    }
    //Creating all Medium fish
    for (int i = 0; i < NumMediumFish; i++)
    {
      //Using the medium fish function we made above
      MediumFishies[i] = new MediumFish(random(width), random(height), random(50, 70), i, MediumFishies);

    }

    //Creating all large fish
    for (int i = 0; i < NumSmallFish; i++)
    {
      //Using the large fish function we made above
      LargeFishies[i] = new LargeFish(random(width), random(height), random(70, 90), i, LargeFishies);

    }
  }

  //This function handles the iteractions between each of the class's through calling
  //Each of the functions on every single one of the fish in said class
  public void HandleAIFishInteractions()
  {
    //Going through the entire small fish 
    for (SmallFish SF : SmallFishies)
    {
      //Calling each of the relevant functions
      SF.Move();
      SF.Display();
    }
    //Going through each of the medium fish 
    for (MediumFish MF : MediumFishies)
    {
      //Calling each of the relevant functions
      MF.Move();
      MF.Display();
      MF.SmallFishCollision();
    }
    //Going through each of the large fish
    for (LargeFish LF : LargeFishies)
    {
      //Calling each of the relevant functions
      LF.Move();
      LF.Display();
      LF.SmallFishCollision();
      LF.MediumFishCollision();
    }
  }

  //THis function handles the bubble interactions
  public void HandleBubbleInteraction()
  {
    //Looping through all the bubles
    for (Bubble bubble : Bubbles)
    {  
      //Calling the relevant functions
      bubble.Collide();
      bubble.Move();
      bubble.Display();
    }
  }
  
  //This function updates and randdomises the AI fish movements every 5 seconds
  public void UpdateAiFishMovement()
  {
    //Creating a 5 second timer
    if (millis() > Time + 5000)
    {
      //If the medium difficulty is selected
      if (MediumDiffSelected == true)
      {
        //Set the limit of the move speed to be 6 + or - and if it goes over set it back to 0
        if (SmallFishXMovement > 6 || SmallFishXMovement < -6 || SmallFishYMovement > 6 || SmallFishYMovement < -6)
        {
          SmallFishXMovement = 0;
          SmallFishYMovement = 0;
        }
        //else randomise the speed
        SmallFishXMovement = SmallFishXMovement + random(-0.1f, 0.1f);
        SmallFishYMovement = SmallFishYMovement + random(-0.1f, 0.1f);
         //Set the limit of the move speed to be 6 + or - and if it goes over set it back to 0
        if (MediumFishXMovement > 6 || MediumFishXMovement < -6 || MediumFishYMovement > 6 || MediumFishYMovement < -6)
        {
          MediumFishXMovement = 0;
          MediumFishYMovement = 0;
        }
        //else randomise it
        MediumFishXMovement = MediumFishXMovement + random(-0.1f, 0.1f);
        MediumFishYMovement = MediumFishYMovement + random(-0.1f, 0.1f);

         //Set the limit of the move speed to be 6 + or - and if it goes over set it back to 0
        if (HardFishXMovement > 6 || HardFishXMovement < -6 || HardFishYMovement > 6 || HardFishYMovement < -6)
        {
          HardFishXMovement = 0;
          HardFishYMovement = 0;
        }
        //else randomised it
        HardFishXMovement = HardFishXMovement + random(-0.1f, 0.1f);
        HardFishYMovement = HardFishYMovement + random(-0.1f, 0.1f);
      } 
      //If medium not selected
      else
      {
         //Set the limit of the move speed to be 2 + or - and if it goes over set it back to 0
        if (SmallFishXMovement > 2 || SmallFishXMovement < -2 || SmallFishYMovement > 2 || SmallFishYMovement < -2)
        {
          SmallFishXMovement = 0;
          SmallFishYMovement = 0;
        }
        //Randomise the speed
        SmallFishXMovement = SmallFishXMovement + random(-0.1f, 0.1f);
        SmallFishYMovement = SmallFishYMovement + random(-0.1f, 0.1f);

        //Set the limit of the move speed to be 2 + or - and if it goes over set it back to 0
        if (MediumFishXMovement > 2 || MediumFishXMovement < -2 || MediumFishYMovement > 2 || MediumFishYMovement < -2)
        {
          MediumFishXMovement = 0;
          MediumFishYMovement = 0;
        }
        //Randomise the fish movement speed
        MediumFishXMovement = MediumFishXMovement + random(-0.1f, 0.1f);
        MediumFishYMovement = MediumFishYMovement + random(-0.1f, 0.1f);
        
        //Set the limit of the move speed to be 2 + or - and if it goes over set it back to 0
        if (HardFishXMovement > 2 || HardFishXMovement < -2 || HardFishYMovement > 2 || HardFishYMovement < -2)
        {
          HardFishXMovement = 0;
          HardFishYMovement = 0;
        }
        //else randomise the speed
        HardFishXMovement = HardFishXMovement + random(-0.1f, 0.1f);
        HardFishYMovement = HardFishYMovement + random(-0.1f, 0.1f);
      }
    }
  }
}
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
float Spring = 0.0001f;
//Declaring the gravity of each bubble
Float Gravity = 0.0003f;
//Setting the friction of each bubbles
float Friction = -0.001f;

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
  public void Collide()
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
  public void Move()
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
  public void Display()
  {
    ellipse(x,y, Diameter, Diameter);
  }
}
/*
This file, handles all information relating to the players fish
THis includes the GIF, as well as the start and game over screen information
and all the logic behind how the player works itself
*/

//Creating a image array to handle each image of the gif
PImage [] PlayerFish = new PImage[5];
//Setting the variables for the wining and game over screens
PImage GameOverImage, WinScreenImage;
//Setting over important variables
int Time;
int GameOverImageX = 1820/2, GameOverImageY = 930/2;
int WinScreenImageX = 1820/2, WinScreenImageY = 930/2;
//Create class object
PlayersFish PFish;


int playRad = 120;        // Width of the player
float playPosX, playPosY;    // Starting position of the player    

float playXSpeed = 0;  // X axis Speed of the player
float playYSpeed = 0;  // Y axis Speed of the player 

//These values are used to send the direction of the ball the other way after a collison against a wall
int playXDirection = 1;  // Left or Right
int PlayYDirection = 1;  // Top to Bottom

//This is the only class within this file and handles all information realting to the players fish
class PlayersFish 
{

  //This setup function handles everything that would normally be called in the global setup function
  //We have split it up into specific functions to create our code more modular
  public void PlayerFishSetUp()
  {
    //Loading each of the gif images into the player fish array
    PlayerFish[0] = loadImage("data/FishGif/PlayerFish0.gif");
    PlayerFish[1] = loadImage("data/FishGif/PlayerFish1.gif");
    PlayerFish[2] = loadImage("data/FishGif/PlayerFish2.gif");
    PlayerFish[3] = loadImage("data/FishGif/PlayerFish3.gif");
    PlayerFish[4] = loadImage("data/FishGif/PlayerFish4.gif");

    //Setting the starting position for the player to be just under the difficulty buttons
    playPosX = width/2;
    playPosY = 800;

    //Loading in the images used within the game that is handled within the players fish
    GameOverImage = loadImage("data/GameOver.jpg");
    WinScreenImage = loadImage("data/WinScreen.jpg");
    //Resizing the winscreen to cover the entire screen
    WinScreenImage.resize(1820, 930);
  }

  //Basic function to create the players fish and loop through the frame counter to
  // Create a interesting animation for the players fish
  public void CreatePlayersFish()
  {
    image(PlayerFish[frameCount%5], playPosX, playPosY, playRad, playRad);
  }

  //Function to control the player
  public void PlayerControler()
  {
    // Update the position of the shape
    playPosX = playPosX + ( playXSpeed * playXDirection );
    playPosY = playPosY + ( playYSpeed * PlayYDirection );

    // Test to see if the shape exceeds the boundaries of the screen
    // If it does, reverse its direction
    //Check right of the screen
    if (playPosX > width-10) { 
      playPosX = width - 10;
      playXSpeed = playXSpeed - 10;
    }

    //Check left of the screen
    if (playPosX < 10) {
      playPosX = 10;
      playXSpeed = playXSpeed + 10;
    }

    //Check bottom of the screen
    if (playPosY > height-10) {
      playPosY = height-10;
      playYSpeed -= 10;
    }

    //Check top of the screen
    if (playPosY < 10) {
      playPosY = 10;
      playYSpeed += 10;
    }
  }

  //Little function to let the user test out game mechanics on the background bubbles
  public void CheckBubbleCollision()
  {
    //If the game hasnt started yet and is still on the main menu screen
    if (ClearedScreens == false)
    {
      //Loop through each bubble
      for (int i = 0; i < NumBubbles; i++)
      {
        //If a very basic collision is detected between the player and the bubble
        if (playPosY + playRad > Bubbles[i].y - Bubbles[i].Diameter && playPosY < Bubbles[i].y + Bubbles[i].Diameter && playPosX+playRad > Bubbles[i].x - Bubbles[i].Diameter && playPosX < Bubbles[i].x + Bubbles[i].Diameter)
        {
          //Reset the bubble location to top left to create a cool waterfall effect
          Bubbles[i].x = 0;
          Bubbles[i].y = 0;
          //If the players fish isnt the max size increase its size but if it is dont
          //let the player grow anymore
          if (playRad < 151)
          {
            playRad = playRad + 1;
          }
        }
      }
    }
  }

  //Function to check for a collision between a small fish and the player
  public void CheckSmallFishCollision()
  {
    //If the screens are cleared
    if (ClearedScreens == true)
    {
      //Loop through every small fish
      for (int i = 0; i < NumSmallFish; i++)
      {
        //If a collision is detected at a very basic level
        if (playPosY + playRad > SmallFishies[i].y - SmallFishies[i].Diameter && playPosY < SmallFishies[i].y + SmallFishies[i].Diameter && playPosX+playRad > SmallFishies[i].x - SmallFishies[i].Diameter && playPosX < SmallFishies[i].x + SmallFishies[i].Diameter)
        {
          //Play the chomp sound effect
          ChompFishEat.play();
          //Respawn the small fish at a random location
          SmallFishies[i].x = random(1800);
          SmallFishies[i].y = random(930);
          //Increase the players size
          playRad = playRad + 1;
        }
      }
      //If the players size is over 100 they win
      if (playRad > 100)
      {
        //Play the winning screen function
        WinnerScreen();
      }
    }
  }

  //Function to check for a collision between the players fish and medium sized fish
  public void CheckMediumFishCollision()
  {
    //if the screen is cleared and the player is currently playing
    if (ClearedScreens == true)
    {
      //If the player is over size 70 they can eat medium fish
      if (playRad > 70)
      {
        //Loop through every medium fish in the array
        for (int i = 0; i < NumMediumFish; i++)
        {
          //If a collison is detected at a basic level
          if (playPosY + playRad > MediumFishies[i].y - MediumFishies[i].Diameter && playPosY < MediumFishies[i].y + MediumFishies[i].Diameter && playPosX+playRad > MediumFishies[i].x - MediumFishies[i].Diameter && playPosX < MediumFishies[i].x + MediumFishies[i].Diameter)
          {
            //Play the chomp sound effect
            ChompFishEat.play();
            //Reset its location
            MediumFishies[i].x = random(1800);
            MediumFishies[i].y = random(930);
            //If the players fish isnt the max size increase its size
            playRad = playRad + 2;
          }
        }
      }
      //Else
      else
      {
        //Note that this second loop is here incase a developer in the future wants
        // Do modify the function signicantly and as a result wont have to now
        //Loop through every medium fish
        for (int i = 0; i < NumMediumFish; i++)
        {
          //if a collision is detected at a basic level
          if (playPosY + playRad > MediumFishies[i].y - MediumFishies[i].Diameter && playPosY < MediumFishies[i].y + MediumFishies[i].Diameter && playPosX+playRad > MediumFishies[i].x - MediumFishies[i].Diameter && playPosX < MediumFishies[i].x + MediumFishies[i].Diameter)
          {
            //Play the game over screen
            GameOver();
            print("Game Over");
          }
        }
      }
      //Double checking the players size again just incase processing skipped over the first
      //statement which it does sometimes
      if (playRad > 100)
      {
        //Play the winning screen
        WinnerScreen();
      }
    }
  }

  //Checking for a collision between the players fish and the large fish
  public void CheckLargeFishCollision()
  {
    //If the screen is cleared and the player is currently playing the game
    if (ClearedScreens == true)
    {
      //If the player is above size 90
      if (playRad > 90)
      {
        //Loop thhrough every large fish
        for (int i = 0; i < NumLargeFish; i++)
        {
          //If a collision is detected at a very basic level
          if (playPosY + playRad > LargeFishies[i].y - LargeFishies[i].Diameter && playPosY < LargeFishies[i].y + LargeFishies[i].Diameter && playPosX+playRad > LargeFishies[i].x - LargeFishies[i].Diameter && playPosX < LargeFishies[i].x + LargeFishies[i].Diameter)
          {
            //Play the chomp sound effect
            ChompFishEat.play();
            //Reset the fishes location
            LargeFishies[i].x = random(1800);
            LargeFishies[i].y = random(930);
            //If the players fish isnt the max size increase its size
            playRad = playRad + 3;
          }
        }
      } 
      else
      {
        //Note we have done the same with this loop like the last one, just
        //incase someone wants to come and modify the game
        //Loop through every large fish
        for (int i = 0; i < NumLargeFish; i++)
        {
          //If it detects a collision at a very basic level
          if (playPosY + playRad > LargeFishies[i].y - LargeFishies[i].Diameter && playPosY < LargeFishies[i].y + LargeFishies[i].Diameter && playPosX+playRad > LargeFishies[i].x - LargeFishies[i].Diameter && playPosX < LargeFishies[i].x + LargeFishies[i].Diameter)
          {
            //Play the gameover screen
            GameOver();
          }
        }
      }
      //Just another if statement to check if max size as processing skips over them sometimes
      if (playRad > 100)
      {
        //Play the winning screen
        WinnerScreen();
      }
    }
  }
  //Function which handles everything in relation to the winning screen
  public void WinnerScreen()
  {
    //Setting time to be = to the current time from the IDE
    Time = millis();
    //Setting the control variable to be true
    WinGame = true;
    //Reset the players position back to the middle
    playPosX = width/2;
    playPosY = 800;
    //Reset its size
    playRad = 120;
    //Reset other control variables
    ClearedScreens = false;
  }

  //Function which handles the timer for the end game winingn screen
  public void EndGameWinnerScreen()
  {
    //Using the control varaible above
    if (WinGame == true)
    {
      //Display the winning image
      image(WinScreenImage, WinScreenImageX, WinScreenImageY);
      //Set a timer for 5 seconds
      if (millis() > Time + 5000)
      {
        //Reset the control varaibes
        WinGame = false;
        //Call are set up variables to reset the game state
        StartUpScreen.StartUpScreenSetUpVariables();
        //Reset all fish positons and all other variables as well
        ResetFishPositions(); 
        ResetVariables();
      }
    }
  }

  //This function handles the game over screen wtihin the game
  public void GameOver()
  {
    //Resetting the timer
    Time = millis();
    //Set the control varaible to be true
    GameOver = true;
    //Reset the players fish to the starting positon
    playPosX = width/2;
    playPosY = 800;
    //Reset the players size
    playRad = 120;
    //Reset the control variable
    ClearedScreens = false;
  }

  //This function handles the end game death screen with the timer
  public void EndGameDeathScreen()
  {
    //If the control variable is true
    if (GameOver == true)
    {
      //Display the game over image
      image(GameOverImage, GameOverImageX, GameOverImageY);
      //Set a 5 second timer
      if (millis() > Time + 5000)
      {
        //Reset the control variable
        GameOver = false;
        //Reset the screen with the start up variables
        StartUpScreen.StartUpScreenSetUpVariables();
        //Reset the fish positons and variables
        ResetFishPositions(); 
        ResetVariables();
      }
    }
  }

  //Thins function resets all information needed to start the game on a clean slate
  public void ResetVariables()
  {
    //Resetting the text and image postions
    TextHardX = 780;
    TextHardY = 620;

    TextMediumX = 814;
    TextMediumY = 520;

    TextEasyX = 780;
    TextEasyY = 420;

    TitleImageX = 910;
    TitleImageY = 200;
    //Resetting the difficulty selecting control variables
    EasyDiffSelected = false;
    MediumDiffSelected = false;
    HardDiffSelected = false;
    
    //Reset the AI fish movement variables
    SmallFishXMovement = 0.1f;
    SmallFishYMovement = 0.1f;
    MediumFishXMovement = 0.1f;
    MediumFishYMovement = 0.1f;
    HardFishXMovement = 0.1f;
    HardFishYMovement = 0.1f;
  }

  //This function loops through every fish array and resets every fish to a new random location
  public void ResetFishPositions()
  {
    //Reseting every small fish
    for (int i = 0; i < NumSmallFish; i++)
    {
      SmallFishies[i].x = random(1800);
      SmallFishies[i].y = random(930);
    }

    //Resetting every medium fish
    for (int i = 0; i < NumMediumFish; i++)
    {
      MediumFishies[i].x = random(1800);
      MediumFishies[i].y = random(930);
    }
    
    //Resetting every large fish
    for (int i = 0; i < NumLargeFish; i++)
    {
      LargeFishies[i].x = random(1800);
      LargeFishies[i].y = random(930);
    }
  }

  //THis function is a placeholder and allows for modular new functions to be added into the
  //Draw function without having to directly add to each function and each function in here
  //Should only be added if it needs to be looped through continuously in the draw
  public void Update()
  {
    EndGameWinnerScreen();
    EndGameDeathScreen();
    CheckSmallFishCollision();
    CheckMediumFishCollision();
    CheckLargeFishCollision();
    CheckBubbleCollision();
    CreatePlayersFish();
    PlayerControler();
  }
}
/*
This file handles all infromation in relation to the startscreen when you first load up the game
All code in this file should only be relevant to the start screen such as
specific image locations as well as unique interations if needed.
*/
//Creating specific variables for each button to use and handle
PShape EasyDiffButton, MediumDiffButton, HardDiffButton;
//Creating specfic image variables
PImage TitleImage;
//Creating specific int variables to hold the location of the difficulty buttons X and Y locations
int EasyDiffX, EasyDiffY, MediumDiffX, MediumDiffY, HardDiffX, HardDiffY;
//Setting the size of each button
int EasyDiffSize = 90, MediumDiffSize = 90, HardDiffSize = 90;  
//Setting the width of each button
int EasyDiffWidth = 445, MediumDiffWidth = 445, HardDiffWidth = 445;
//Setting the specific location of the title image
int TitleImageX = 910, TitleImageY = 200;
//Setting color variables for the buttons to use
int baseColor, EasyDiffColor, MediumDiffColor, HardDiffColor;
//Colour of each highlight color
int EasyDiffHighlight, MediumDiffHighlight, HardDiffHighlight;
//Setting the current color variable
int currentColor;
//Setting a variety of control variables
boolean EasyDiffOver = false;
boolean MediumDiffOver = false;
boolean HardDiffOver = false;
boolean ClearedScreens = false;
boolean EasyDiffSelected = false;
boolean MediumDiffSelected = false;
boolean HardDiffSelected = false;
boolean GameOver = false;
boolean WinGame = false;

//Setting the specific locations of the text within the buttons
int TextHardX = 780;
int TextHardY = 620;

int TextMediumX = 814;
int TextMediumY = 520;

int TextEasyX = 780;
int TextEasyY = 420;


//Creating the class object Start screen
StartScreen StartUpScreen;

//This class handles all information in relation to the startup screen
class StartScreen
{
  
  //This function is the placeholder where all start up variables in relation to the start screen should be placed
  //This was created to allow for more modular and extentable code
  public void StartUpScreenSetUpVariables()
  { 
    //Setting the diffrent colors for the buttons and hihglight colors, once again
    //Multiple variables so others can change them easily
    EasyDiffColor = color(0,255,0);
    EasyDiffHighlight = color(51);
    
    MediumDiffColor = color(0,255,0);
    MediumDiffHighlight = color(51);
    
    HardDiffColor = color(0,255,0);
    HardDiffHighlight = color(51);
    
    baseColor = color(102);
    currentColor = baseColor;
    
    //Setting the location of each of the buttons
    EasyDiffX = 690;
    EasyDiffY = 390;
    
    MediumDiffX = 690;
    MediumDiffY = 490;
    
    HardDiffX = 690;
    HardDiffY = 590;
    
    //Creating each of the buttons
    EasyDiffButton = createShape(RECT, EasyDiffX, EasyDiffY, EasyDiffWidth, EasyDiffSize);
    MediumDiffButton = createShape(RECT, MediumDiffX, MediumDiffY, MediumDiffWidth, MediumDiffSize);
    HardDiffButton = createShape(RECT, HardDiffX, HardDiffY, HardDiffWidth, HardDiffSize);
    
    //Creating the image and resizing it to be the correct shape
    TitleImage = loadImage("Title.png");
    TitleImage.resize(450,450);
    
    //Setting it to be centre
    imageMode(CENTER);
    //PLaying the start screen music
    StartScreenMusic.play();
   
  }
  //This function handles everything in relation to selecting your difficulty and the start screen in general
  public void SelectYourDifficulty()
  {
    //Creating the title images and calling the button functions
    image(TitleImage, TitleImageX, TitleImageY);
    Easy();
    Medium();
    Hard();
  }

  //Function handling the easy button
  public void Easy()
  {
    shape(EasyDiffButton);
  }

  //Function handling the medium button
  public void Medium()
  {
    shape(MediumDiffButton);
  }

  //Function handling the hard button
  public void Hard()
  {
    shape(HardDiffButton);
  }

  //Function in realtion to updating the game constantly and ensuring that the start screen is created or removed when needed
  public void Update() 
  {
    //Checking to see if the music has stopped playing on the start screen
    if (StartScreenMusic.isPlaying() == false && ClearedScreens == false)
    {
      //If it has, jump back to the start of the sound track
      StartScreenMusic.jump(0.0f);
    }
    
    //If the user has its mouse over the easy button\
    if (OverButton(EasyDiffX, EasyDiffY,EasyDiffWidth,EasyDiffSize))
    {
      //Change the colour and set the control variable
      EasyDiffButton.setFill(color(EasyDiffHighlight));
      EasyDiffOver = true;
      text("Easy", TextEasyX, TextEasyY);
      
    }
    else
    {
      //Change the colour and set the control variable
      EasyDiffOver = false;
      EasyDiffButton.setFill(color(EasyDiffColor));
      text("Easy", TextEasyX, TextEasyY);
    }
    //If the user has its mouse over the Medium button\
    if (OverButton(MediumDiffX, MediumDiffY, MediumDiffWidth, MediumDiffSize))
    {
      //Change the colour and set the control variable
      MediumDiffButton.setFill(color(MediumDiffHighlight));
      MediumDiffOver = true;
      text("Medium", TextMediumX, TextMediumY);
    }
    else
    {
      //Change the colour and set the control variable
      MediumDiffOver = false;
      MediumDiffButton.setFill(color(MediumDiffColor));
      text("Medium", TextMediumX, TextMediumY);
    }
    //If the user has its mouse over the hard button\
    if (OverButton(HardDiffX, HardDiffY, HardDiffWidth, HardDiffSize))
    {
      //Change the colour and set the control variable
      HardDiffButton.setFill(color(HardDiffHighlight));
      HardDiffOver = true;
      text("Hard", TextHardX, TextHardY);
    }
    else
    {
      //Change the colour and set the control variable
      HardDiffOver = false;
      HardDiffButton.setFill(color(HardDiffColor));
      text("Hard", TextHardX, TextHardY);
    }
    //showing the score
    fill(0);
    textSize(30);
    text("Score: " + (playRad - 50), 1700, 50);
    fill(0, 169, 204);
    textSize(60);
    
  }
  
  //Function to detect whether or not the user has its mouse over the button, very basic
  public boolean OverButton(int x, int y, int width, int height) {
    if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
      return true;
    } else {
      return false;
    }
  }
  
  //This function handles clearing the entire screen and ensures we can operate our game off a single screen
  public void ClearScreen()
  {
    //Setting a controller variable
    ClearedScreens = true;
    //Resetting the player size
    playRad = 50;
    //Removing all the displays from the screen
    EasyDiffButton = createShape(RECT, 10000, -10000, EasyDiffSize, EasyDiffSize);
    MediumDiffButton = createShape(RECT, 10000, -10000, EasyDiffSize, EasyDiffSize);
    HardDiffButton = createShape(RECT, 10000, -10000, EasyDiffSize, EasyDiffSize);
    TitleImageX = -100000;
    TitleImageY = -100000;
    TextHardX = -1000000;
    TextHardY = -1000000;
    TextMediumX = -100000;
    TextMediumY = -100000;
    TextEasyX = -100000;
    TextEasyY = -100000;
    //Stopping the music
    StartScreenMusic.stop();
  }
}
  public void settings() {  size(1820, 930);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Assignment_3" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
