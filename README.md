# Description of the project
This repository is a final version of the Chat System app. 

After executing the app (see below), you will be presented a login page and asked for a username.
You must use a unique username or else you won't be able to connect to the ChatSystem.

Upon connection, you can :
* Change your username
* See who is connected on the network on the left side of the window
* Chat with other connected users (if any) by clicking on their names
* Disconnect from the ChatSystem at any time by clicking on the exit button.


## Notes
* If you are having a conversation with a connected user, and he suddenly disconnects,
you won't be able to chat with him anymore (the conversation view will disappear) until his next connection. 


* If a username changes his name during a conversation, his username will change inside the conversation.
However, the previous messages will keep the former username.


* Please keep in mind that the history database is stored in /tmp/ repository. Thus, if 
the machines reboots all conversations will be lost. You can avoid this by changing the DB_URL parameter
in DatabaseManager class.


## Instruction to compile the ChatSystem app
    mvn package

## How to execute ChatSystem
    mvn exec:java -Dexec.mainClass="MainController" 

