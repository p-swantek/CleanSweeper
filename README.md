# SweepClean

This application is a simulation for a fictional clean sweeper. You can know how the clean sweeper works through this application hopefully.

Module list:

1. GUI

  >Implementing all the User interface stuff. showing floor plan and cleansweeps and interacting with the users.
2. Simulator

  >Providing the data that all sensors need. 
3. Cleansweep

  >Implemnenting the control system of a Cleansweep. Including Reading the sensors data and handling these data.
4. Logging

  >Provide the method to log everything .
  
Module Dependency like the image below:

![](/portal/module.png)

The main Sequence Diagram between modules is like the following image :

![](/portal/sequence.png)

The main class Diagram is like the following image :

![](/portal/class.png)

Run Instruction:
1,Run GUI project  
2,Load a floor plan through the menu  
3,Add a clean sweeper through the menu  

if done, the ui should like this:
The yellow tile stands for charge station  
The dark tile is stair  
The light gray tile is low carpet floor  
The dark gray tile is high carpet floor   
The blue is bare floor  
We can add more charge stations and clean sweepers.   
![](/portal/demo.png)
