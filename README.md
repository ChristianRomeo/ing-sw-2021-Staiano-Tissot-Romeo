## Masters of Renaissance Board Game

<img src="http://www.craniocreations.it/wp-content/uploads/2019/06/Masters-of-Renaissance_box3D_ombra.png" width=300px height=300px align="right"/>

![Last Commit](https://img.shields.io/badge/last%20commit-July-orange?style=for-the-badge&logo=github)
![Intellijidea](https://img.shields.io/badge/Ide-Intellijidea%20Ultimate-blue?style=for-the-badge&logo=intellijidea)
![JavaFx](https://img.shields.io/badge/GUI-JavaFx-red?style=for-the-badge&logo=java)
![Build](https://img.shields.io/badge/Build-Maven-red?style=for-the-badge&logo=apachemaven)
![Parsing](https://img.shields.io/badge/Parser-gson-blue?style=for-the-badge&logo=google)
![Sonarqube](https://img.shields.io/badge/Code%20Analysis-SonarQube-yellow?style=for-the-badge&logo=sonarqube)
![UML](https://img.shields.io/badge/Uml-UmLet-red?style=for-the-badge)
![Release](https://img.shields.io/badge/Release-v1.0-green?style=for-the-badge)
![Tests](https://img.shields.io/badge/Tests-✔%20100%25%20%7C%20✘%200%20%7C%20➟%200-red?style=for-the-badge&logo=codecov)

##
Masters of Renaissance JAVA edition is the final test of **Software Engineering** course of **"Computer Science Engineering"** held at Politecnico di Milano (2020/2021).<br /><br />
**Teacher** Gianpaolo Cugola.<br />

## The Students Team
###  Christian Romeo ([@ChristianRomeo](https://github.com/ChristianRomeo))<br>christian1.romeo@mail.polimi.it
###  Enrico Staiano ([@enricostaiano](https://github.com/enricostaiano))<br>enrico.staiano@mail.polimi.it
###  Tommaso Tissot ([@tommasotissot](https://github.com/tommasotissot))<br>tommaso.tissot@mail.polimi.it

## Implemented features

| Functionality | State |
| ------- | ----------- |
| Complete Rules | :heavy_check_mark: |
| CLI | :heavy_check_mark: |
| GUI | :heavy_check_mark: |
| Socket | :heavy_check_mark: |
| Multiple Games | :heavy_check_mark: |
| Reconnection | :x: |

## The Game
*Masters of Renaissance Board Game*, by CranioCreations. <br />
Here the full specifications: 
 <br />
[IT](http://www.craniocreations.it/wp-content/uploads/2021/04/Lorenzo_Cardgame_Rules_ITA_small-3.pdf)
 <br />
[ENG](https://craniointernational.com/2021/wp-content/uploads/2021/05/Masters-of-Renaissance_small.pdf)
 <br /><br />
 
The project consists in the implementation of a distributed system made of a single server capable of managing multiple games and multiple clients (one per player) using the MVC pattern (Model-View-Controller). The network is managed through the use of sockets.<br />
This project includes:
* Initial UML diagram; 
* Final UML diagram, generated from the code by automated tools;
* Javadocs;
* Working game implementation, rules compliant;
* Source code of the implementation;
* Source code of unity tests.

## Setup
**Requirements**
* Java SE SDK 15 (or newer) [for running]
* Maven framework 3.8 (or newer) [for building]
#
In the [deliverables](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/tree/main/deliverables) folder there is a multi-platfor jar file, that can be used both for Server and Clients.
* The Server can be runned with the following command, as defaults it runs on port 9876:
```shell
    > java -jar GC24.jar server [ip.json]
```

* The clients can be runned with the following command:
  * CLI
  ```shell
    > java -jar GC24.jar cli [ip.json]
  ```
  <img width="751" alt="CLI" src="https://user-images.githubusercontent.com/72473741/124113943-4a220a00-da6c-11eb-80b1-9d8083b4ec93.png">

  
  * GUI
  ```shell
    > java -jar GC24.jar [gui] [ip.json]
  ```

<img width="416" alt="GUI" src="https://user-images.githubusercontent.com/72473741/124114057-69b93280-da6c-11eb-88eb-3805d2fe9fc9.png">
<img width="960" alt="GUI1" src="https://user-images.githubusercontent.com/72473741/124114191-94a38680-da6c-11eb-82f8-361284ee2e48.png">

 This commands can be followed by:
 - **ip.json** that is the configuration file, where it can be choosen the server port and server ip for clients
  ```
  {
  "server_ip": "device ip where the server is launched (127.0.0.1 default)",
  "server_port": "(9876 default)"
  }
  ```

* In alternative you can use the .bat files to lauch the game, **be sure to have Windows Terminal installed**.
# 
**Warning**

For the best CLI experience, it's recommended to use native linux terminal or **WSL** (windows-subsystem for linux) in order to provide fonts, colors and emoji as the project was meant to show.<br />
*DejaVu Sans Mono for Powerline was the font originally used at size 12.*<br /><br />
For the best GUI experience, it's reccomended a minimum resolution of 2560 x 1440 (or 2160 x 1440).<br />
*at 1920 x 1080 the window will result almost fullscreen.*

In order to play, you'll have to launch at least one server and one client (either CLI or GUI).<br />
In order to play multiplayer from different locations port forwarding is needed.

## Build
The project was built with Maven, using *shade-plugin*.<br />

To run and compile the software:

Clone this repo<br />
In the cloned repo folder, issue:  
    ```
       > mvn clean    
    ```  
    ```
      > mvn package  
    ```  
After these processes the compiled jar can be found in the *shade* folder.

## UML
The following class diagrams represent respectively the initial model developed during the design phase and the final product diagrams.
* [Model - Initial](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/blob/main/deliverables/UML_Model_Initial.pdf)
* [Model - Final](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/blob/main/deliverables/UML_Model_Final.pdf)
* [Client + Network - Initial](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/blob/main/deliverables/UML_Client%2BNetwork_Initial.pdf)
* [Client + Network - Final](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/blob/main/deliverables/UML_Client%2BNetwork_Final.pdf)

## Javadocs
The following documentation includes a description for most of the classes and methods used and follows the Java documentation technique.<br />
It can be consulted [here](https://christianromeo.github.io/Masters-of-Renaissance-Javadocs/).

## Test Coverage
Coverage criteria: Methods.<br />
Tool used: Junit.

![Codecov](https://img.shields.io/badge/Controller%20Coverage-73%25-brightgreen?style=for-the-badge&logo=codecov)
![Codecov](https://img.shields.io/badge/Model%20Coverage-81%25-brightgreen?style=for-the-badge&logo=codecov)

The unit tests run automatically at each commit thanks to the [Continuous Integration pipeline](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/blob/main/.github/workflows/maven.yml).

<img width="1084" alt="model_coverage" src="https://user-images.githubusercontent.com/72473741/124113739-10e99a00-da6c-11eb-8593-a5f912040e88.png">
<img width="429" alt="controller_coverage" src="https://user-images.githubusercontent.com/72473741/124113821-28c11e00-da6c-11eb-807c-a6c1219df679.png">

# 
* **[Full project specifications (ITA)](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/wiki/Specifications)**
#
![Cranio-Creations](https://www.justnerd.it/wp-content/uploads/2019/02/cranio-creations-cover-generica.jpg)
