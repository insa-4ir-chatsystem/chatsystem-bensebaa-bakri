# ChatSystem Report

## Tech Stack
I used the following technologies to develop my ChatSystem application.
* Use of UDP for the Discovery System library : 
  * Sending broadcast to alert connected users requires UDP protocol
  * Connection establishment is useless when sending only username.
  
  <br>
  
* Reasons for using TCP when establishing conversation sessions with connected users :
  * We have to make sure that the recipient receives the message (Quality of service)
  * Ensure that the message arrives uncorrupted to its destination unlike UDP
  
  <br>
* Reasons for using the swing library for the project : 
  * Easy to use : intuitive handling of the interactions between a frame and panels.
  * Had a previous experience with java swing 
  
  <br>
* Reasons for using jdbc sql lite : 
  * Easy to handle : simple query to retrieve a specific conversation from database rather than
  managing conversations in a file
  * Respects the specification document : to develop a decentralized ChatSystem, every has to be
  local to the user rather than a database server

## Testing Policy
* Network tests aim to test that TCPClient and TCPServer classes function properly. In other words, we make sure that
we receive the right messages after sending them with TCPClient to TCPServer. The same is done for
UDPServer and UDPClient.


* For database management, we need two things, add messages to the database and retrieve them when stating a conversation.
DatabaseTests makes sure those two functions work correctly.


* Contact List Tests are very basic tests like adding and removing elements from the list.

## Highlights
I believe that the great part of the code is the loose coupling and the separation between the GUI, the discovery system and
the chat controller. <br>
We can see that MainController is never referenced in the classes that he uses. 
Moreover, the classes that are used by the View interact with the View via observers. 
The same can be said about ChatController (TCPServer interacts with ChatController via an observer). 

The classes that I want to highlight are TCPServer and TCPClient. They're concise and 
clear. TCPServer only listens on a port and can handle several connections at the same time.
The messages received aren't handled in the class but through an observer as it should be done.