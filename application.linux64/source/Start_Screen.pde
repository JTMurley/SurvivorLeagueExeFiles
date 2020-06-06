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
color baseColor, EasyDiffColor, MediumDiffColor, HardDiffColor;
//Colour of each highlight color
color EasyDiffHighlight, MediumDiffHighlight, HardDiffHighlight;
//Setting the current color variable
color currentColor;
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
  void StartUpScreenSetUpVariables()
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
  void SelectYourDifficulty()
  {
    //Creating the title images and calling the button functions
    image(TitleImage, TitleImageX, TitleImageY);
    Easy();
    Medium();
    Hard();
  }

  //Function handling the easy button
  void Easy()
  {
    shape(EasyDiffButton);
  }

  //Function handling the medium button
  void Medium()
  {
    shape(MediumDiffButton);
  }

  //Function handling the hard button
  void Hard()
  {
    shape(HardDiffButton);
  }

  //Function in realtion to updating the game constantly and ensuring that the start screen is created or removed when needed
  void Update() 
  {
    //Checking to see if the music has stopped playing on the start screen
    if (StartScreenMusic.isPlaying() == false && ClearedScreens == false)
    {
      //If it has, jump back to the start of the sound track
      StartScreenMusic.jump(0.0);
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
  boolean OverButton(int x, int y, int width, int height) {
    if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
      return true;
    } else {
      return false;
    }
  }
  
  //This function handles clearing the entire screen and ensures we can operate our game off a single screen
  void ClearScreen()
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
