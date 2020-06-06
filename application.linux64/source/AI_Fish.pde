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
float SmallFishXMovement = 0.1;
float SmallFishYMovement = 0.1;

float MediumFishXMovement = 0.1;
float MediumFishYMovement = 0.1;

float HardFishXMovement = 0.1;
float HardFishYMovement = 0.1;

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
  void Display()
  {
    image(SmallFishImg, x, y, Diameter, Diameter);
  }

  //This function handles the logic behind the movement of the small fish
  void Move()
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
  void Display()
  {
    image(MediumFishImg, x, y, Diameter, Diameter);
  }

  //This move function handles the logic behind moving the fish
  void Move()
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
  void SmallFishCollision()
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
  void Display()
  {
    image(LargeFishImg, x, y, Diameter, Diameter);
  }

  //Function to handle the logic behind how the large fish moves
  void Move()
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
  void SmallFishCollision()
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
  void MediumFishCollision()
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
  void AIFishSpawning()
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
  void HandleAIFishInteractions()
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
  void HandleBubbleInteraction()
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
  void UpdateAiFishMovement()
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
        SmallFishXMovement = SmallFishXMovement + random(-0.1, 0.1);
        SmallFishYMovement = SmallFishYMovement + random(-0.1, 0.1);
         //Set the limit of the move speed to be 6 + or - and if it goes over set it back to 0
        if (MediumFishXMovement > 6 || MediumFishXMovement < -6 || MediumFishYMovement > 6 || MediumFishYMovement < -6)
        {
          MediumFishXMovement = 0;
          MediumFishYMovement = 0;
        }
        //else randomise it
        MediumFishXMovement = MediumFishXMovement + random(-0.1, 0.1);
        MediumFishYMovement = MediumFishYMovement + random(-0.1, 0.1);

         //Set the limit of the move speed to be 6 + or - and if it goes over set it back to 0
        if (HardFishXMovement > 6 || HardFishXMovement < -6 || HardFishYMovement > 6 || HardFishYMovement < -6)
        {
          HardFishXMovement = 0;
          HardFishYMovement = 0;
        }
        //else randomised it
        HardFishXMovement = HardFishXMovement + random(-0.1, 0.1);
        HardFishYMovement = HardFishYMovement + random(-0.1, 0.1);
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
        SmallFishXMovement = SmallFishXMovement + random(-0.1, 0.1);
        SmallFishYMovement = SmallFishYMovement + random(-0.1, 0.1);

        //Set the limit of the move speed to be 2 + or - and if it goes over set it back to 0
        if (MediumFishXMovement > 2 || MediumFishXMovement < -2 || MediumFishYMovement > 2 || MediumFishYMovement < -2)
        {
          MediumFishXMovement = 0;
          MediumFishYMovement = 0;
        }
        //Randomise the fish movement speed
        MediumFishXMovement = MediumFishXMovement + random(-0.1, 0.1);
        MediumFishYMovement = MediumFishYMovement + random(-0.1, 0.1);
        
        //Set the limit of the move speed to be 2 + or - and if it goes over set it back to 0
        if (HardFishXMovement > 2 || HardFishXMovement < -2 || HardFishYMovement > 2 || HardFishYMovement < -2)
        {
          HardFishXMovement = 0;
          HardFishYMovement = 0;
        }
        //else randomise the speed
        HardFishXMovement = HardFishXMovement + random(-0.1, 0.1);
        HardFishYMovement = HardFishYMovement + random(-0.1, 0.1);
      }
    }
  }
}
