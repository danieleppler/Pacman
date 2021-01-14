
# Overview

This projcet contains a small pacman game made in java languae. Pick one of 21 stages and collects as many point as you can in bounded time.

# Features
* The game have 21 stages you can chose from, sorted from the first stage that is the most easiest one to the 21-th stage which is the most difficult.
* The user can chose how many "pacmans" players will be placed on the board
* The game have 2 gaming modes:
  1.User-gameplay: the user can move each of the pacman players manualy.
  2.Automat gameplay where the "pacmnans" are moveing idealy to get as much points as possible.
* Users can extract game statistisc of other players from the server and compare their achivments to others.
* Users can play on google earth platform in any area that they desire.

# System Interfaces
* The graph is Directional graph, and i am using Djikstra algorithm the find the shortest path from node to node, which optimizing the pacman movement in automat gameplay       mod.
* I am using kml files to save the graph x's and y's location for brodcasting the data on google earth map.

# System dependencies
* Game GUI was built using Jframe and StdDRraw libraries
* server.jar file need to be updated and be able to extract data from.

