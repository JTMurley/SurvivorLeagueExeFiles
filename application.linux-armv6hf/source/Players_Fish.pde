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
  void PlayerFishSetUp()
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
  void CreatePlayersFish()
  {
    image(PlayerFish[frameCount%5], playPosX, playPosY, playRad, playRad);
  }

  //Function to control the player
  void PlayerControler()
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
  void CheckBubbleCollision()
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
  void CheckSmallFishCollision()
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
  void CheckMediumFishCollision()
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
  void CheckLargeFishCollision()
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
  void WinnerScreen()
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
  void EndGameWinnerScreen()
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
  void GameOver()
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
  void EndGameDeathScreen()
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
  void ResetVariables()
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
    SmallFishXMovement = 0.1;
    SmallFishYMovement = 0.1;
    MediumFishXMovement = 0.1;
    MediumFishYMovement = 0.1;
    HardFishXMovement = 0.1;
    HardFishYMovement = 0.1;
  }

  //This function loops through every fish array and resets every fish to a new random location
  void ResetFishPositions()
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
  void Update()
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
