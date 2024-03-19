# Live Lurker

## Description

It's a simple bot that uses your Twitch account to lurk in a channel. <br>
It will not write any messages in the chat, it will only listen to the chat and the events of the channel. <br>
All the messages and events will be printed in the console. <br> <br>

## How to use

You need to have Java 17 installed in your computer, you can download it
from [here](https://www.oracle.com/uk/java/technologies/downloads/#java17). <br>
You need your Twitch Account Token, you can get it from [here](https://twitchapps.com/tmi/).<br> <br>
Keep in mind that your token can change from to time. <br>
But remember **don't EVER POST or SHARE your token anywhere**!!! <br> <br>

### 1. Clone the repository

You need to have Git installed in your computer, you can download it from [here](https://git-scm.com/downloads). <br>
Clone the repository using the following command: <br>
```git clone https://www.github.com/MCmoderSD/LiveLurker.git ``` <br> <br>

### 2. Edit the configuration file

You need to create two JSON files in ```/src/main/resources/config/``` folder. <br> <br>
The first file is ```Config.json``` and it should have the following structure: <br>

```json
{
  "username": "YOUR_TWITCH_USERNAME",
  "token": "YOUR_TWITCH_TOKEN",
  "prefix": "!"
}
```

The prefix is the character that the bot will use to recognize commands. <br> <br>
The second file is ```Channel.list``` and it should have the following structure: <br>

```
CHANNEL_NAME
OTHER_CHANNEL_NAME
```

You can add as many channels as you want. <br> <br>

### 3. Black and White list commands

You can edit the ```blacklist.json``` and ```whitelist.json```  files in the ```/src/main/resources/config/```
folder. <br>

It should have the following structure: <br>

```json
{
  "COMMAND_NAME": "Channel1; Channel2; Channel3",
  "OTHER_COMMAND_NAME": "Channel1; Channel2; Channel3"
}
```

You can add as many channels or commands as you want. <br>
The way for black and whitelist works the same. <br> <br>

### 4. Compile and run the bot

After you compiled the bot into a .jar file, you can run it using the following command: <br>
```java -jar NAME_OF_THE_JAR_FILE.jar``` <br> <br>

## Features

- [x] Listen to chat messages
- [x] Listen to channel events
- [x] Print messages and events in the console
- [x] !status command to check if the bot is running
- [x] !play command for Marbles on Stream
- [x] !join command for joining a raffle
- [x] Graphical User Interface
- [x] Add a way to send messages to the chat
- [x] Add a database to store the messages and events
- [ ] Add a web interface 