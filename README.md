# Dawn-11-15
The first final assignment of the programming class 1. 

This is a modified version of the board game DAWN 11/15 by Prof. Dr. Ingo Althöfer (https://www.althofer.de/dawn-11-15.htm).

The board is 11 x 15 fields big and there are two players (Nature and Mission Control). Nature controls the game pieces Vesta and Ceres, Mission Control controls pieces with the length of 2, 3, 4, 5, 6 and a piece DAWN with the length of 7. The coordinate 0;0 is at the top left, 10;14 is on the bottom right. 

Following commands are part of the program:

### pointed brackets are not part of the commands ### 

state<m>;<n>
    m and n are the coordinates of the field you'd like to get the current state of (- for an empty field, + if the field is occupied by a piece of Mission Control, V for Vesta, C for Ceres).

print
    Prints the current board.
    
set-vc <m>;<n>
    Sets either Vesta or Ceres to the field with the coordinates m;n.

roll <symbol>
    "Rolls" a number. The number has to be given by the user. symbol must be either 2, 3, 4, 5, 6 or DAWN.
  
place <x1>;<y1>:<x2>;<y2>
    Places a piece of Misison Control on the board. The beginning of the piece is x1;y1 the end of the piece is x2;y2.
  
move <m1>;<n1>:<mi>;<ni>
    Moves either Vesta, Ceres or a piece of Mission Control due to the rules of the game. For example: move 1;1:1;2:1;3:1;2

show-result 
    Shows the end result of the game.
    
reset
    Resets the game and starts a new one.
    
quit
    Terminates the program.
