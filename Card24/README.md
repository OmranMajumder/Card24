# Playing Card 24 Game
This Java application was designed using JavaFX and SceneBuilder. 
___
### Getting Started
It is recommended to create a new repository for any new project.

We utilized JDK15 when developing this project on Apache Netbeans, which is an IDE (integrated development environment) software. 

After pulling the code down from GitHub, you may need to perform the following steps to ensure that the application functions correctly:
1. Once the project is listed on your IDE, right click the project name and select properties.
2. Under "Categories" on the left side, select "Libraries".
3. Add the JavaFX library to the Classpath under the "Compile" tab.
    - Click on the "+" and select "Add Library...".
    - Find the JavaFX library (names may vary).
4. Switch from the "Compile" tab over to the "Run" tab.
5. Add the JavaFX library to the Modulepath.
6. On the left side menu (Categories), select "Run".
7. Add the following to the VM Options textbox:
    - --add-modules javafx.controls,javafx.base,javafx.fxml
        - *Ensure that there are no additional spaces or returns before or after this code*.
        - ****Please note:*** Not all users will require the VM Options code to be added. It may depend on your OS and IDE, so you may need to adjust accordingly*.
8. Once completed, click the "OK" button at bottom to confirm updates. 
___
### Object of the game
Four cards will be randomly selected and provided on the screen. Using the cards provided, create a mathematical expression that equals 24.
___
### Gameplay
The values for each card are as follows: 
> - Aces are worth 1  
> - Number cards (2-10) are the same as their value shown  
> - Jacks are worth 11  
> - Queens are worth 12  
> - Kings are worth 13  
  
- All four card values should be used, but each number can only be used once in the expression.  
- Card values can be added, subtracted, multiplied, or divided.  
   -*Mathematical operators can be used more than once, but ***all operators do not need to be used***.*  
   -*Parenthesis can be used in your expression to signify order of precedence.*
