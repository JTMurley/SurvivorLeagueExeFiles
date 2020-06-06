/*
This is the base Root File and should only be changed when you need to add or remove user interactions
Or add in new class objects or resources into the main setup function as you can see below. Furthermore this root file
Should serve as a container for all smaller functions to add onto the game and possible extensions might be to create a specific
file setup tab just for resources however due to having very few external resources we have them in this setup function directly
Futhermore, all librarys used should be included at the top of this file under the libarays section
*/

//Libarys ----- -------------

//Importing the entire sound libaray
import processing.sound.*;
//Creating a variable to hold the start screen music
SoundFile StartScreenMusic;
SoundFile ChompFishEat;
// -----------------------------

void setup() 
{
  //Setting the framerate to 30 as this works best with the current players fish gif
  frameRate(30);
  
  //Enabling smooth just to make the game look that tiny bit neater
  smooth();  
  
  //Setting the Size of the screen
  size(1820, 930);
  
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

void draw() 
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
void mousePressed() {
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
void keyPressed()
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
