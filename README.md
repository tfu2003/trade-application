# CPSC 210 Project

## Mock Trade Application

- What will the application do?


This application will allow users to create mock trades for their favorite sports teams.


- Who will use it?

Followers of specific sport teams can use it to make trades that they believe will greatly benefit their team,
and they would be able to share them with all their friends for their approval.

- Why is this project of interest to you?

Being a Toronto Raptors fan for 8+ years and being disappointed at their lack of success lately, I have come up with 
many potential trades that would make the Raptors better. Unfortunately, I did not record any of them, and as a result I 
forgot most of them. This project would allow me to properly record all the trades I think of and allow me to show them 
to others. 

## User Stories

- As a user, I want to be able to add a trade to a list of previously made trades.
- As a user, I want to be able to see existing trades.
- As a user, I want to be able to edit previously made trades.
- As a user, I want to be able to filter the trades by player and team.
- As a user, I want to be able to save the trades that I made.
- As a user, I want to be able to load all the trades that I made previously.

## Instructions for Grader 

Note: To use some buttons, you must click one of the elements in the list on the right. For example, if you want to
remove a trade, you need to click on the trade and then press "Remove a previously made trade".

- You can generate the first required event by clicking a button that has "create" in it.
- You can generate the second required event by clicking a button that filters trades by a player or team given by the user.
- You can locate my visual component by adding a new trade, team or player, or by doing a prohibited action.
- You can save the state of my application by pressing the "Save your trades" button.
- You can reload the state of my application by pressing the "Load previously made trades" button.

## Phase 4: Task 2
Tue Aug 09 14:21:25 PDT 2022

Added Trade 1

Tue Aug 09 14:21:25 PDT 2022

Added Trade 2

Tue Aug 09 14:21:26 PDT 2022

Added Trade 3

Tue Aug 09 14:21:44 PDT 2022

Added Team: Toronto Raptors to Trade 2

Tue Aug 09 14:21:50 PDT 2022

Added Team: Brooklyn Nets to Trade 2

Tue Aug 09 14:22:03 PDT 2022

Added Player: Pascal Siakam to Team: Toronto Raptors

Tue Aug 09 14:22:10 PDT 2022

Removed Player: Pascal Siakam from Team: Toronto Raptors

Tue Aug 09 14:22:15 PDT 2022

Removed Team: Toronto Raptors from Trade 2

Tue Aug 09 14:22:19 PDT 2022

Removed Trade 1

## Phase 4: Task 3

* I would make an abstract class and have "trade" and "team" extend this abstract class since they both have similar 
methods that only differ by the parameter being passed through.
* As I continued creating methods within a class, some methods had similar code to other ones. I would have created a 
helper method to improve readability and decrease redundancy. For example: I would have created a helper method to 
create the buttons used in my GUI.
* Also, my GUI has functionality that should belong to a different class. For instance, the GUI does the filtering, but
I believe it would be more appropriate to do it in the "trades" class to increase cohesion.