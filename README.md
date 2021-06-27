## Masters of Renaissance Board Game

<img src="http://www.craniocreations.it/wp-content/uploads/2019/06/Masters-of-Renaissance_box3D_ombra.png" width=300px height=300px align="right"/>

![Last Commit](https://img.shields.io/badge/last%20commit-June-orange?style=for-the-badge&logo=github)
![Intellijidea](https://img.shields.io/badge/Ide-Intellijidea%20Ultimate-blue?style=for-the-badge&logo=intellijidea)
![JavaFx](https://img.shields.io/badge/GUI-JavaFx-red?style=for-the-badge&logo=java)
![Build](https://img.shields.io/badge/Build-Maven-red?style=for-the-badge&logo=apachemaven)
![Parsing](https://img.shields.io/badge/Parser-gson-blue?style=for-the-badge&logo=google)
![Sonarqube](https://img.shields.io/badge/Code%20Analysis-SonarQube-yellow?style=for-the-badge&logo=sonarqube)
![UML](https://img.shields.io/badge/Uml-UmLet-red?style=for-the-badge)
![Release](https://img.shields.io/badge/Release-v1.0-green?style=for-the-badge)
![Codecov](https://img.shields.io/badge/Code%20Coverage-85%25-brightgreen?style=for-the-badge&logo=codecov)
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
    > java -jar Masters.jar server [ip.json]
```

* The clients can be runned with the following command:
  * CLI
  ```shell
    > java -jar GC24.jar cli [ip.json]
  ```
  * GUI
  ```shell
    > java -jar Masters.jar [gui] [ip.json]
  ```
 This commands can be followed by:
 - **ip.json** that is the configuration file, where it can be choosen the server port and server ip for clients
  ```
  {
  "server_ip": "device ip where the server is launched (127.0.0.1 if local)",
  "server_port": "(9876 default)"
  }
  ```

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

Install Java SE 15 (or newer).
Install Maven
Clone this repo
In the cloned repo folder, issue:  
    ```
       > mvn clean    
    ```  
    ```
      > mvn package  
    ```  
After these processes the compiled jar can be found in the *shade* folder.

## Test Coverage
Coverage criteria: Methods.

![Codecov](https://img.shields.io/badge/Controller%20Coverage-73%25-brightgreen?style=for-the-badge&logo=codecov)
![Codecov](https://img.shields.io/badge/Model%20Coverage-79%25-brightgreen?style=for-the-badge&logo=codecov)

The unit tests run automatically at each commit thanks to the [Continuous Integration pipeline](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/blob/main/.github/workflows/maven.yml).

<img width="711" alt="model_coverage" src="https://user-images.githubusercontent.com/25418541/119738638-4bff1900-be81-11eb-9cdc-0a28892f93b6.png"><br /><br />
<img width="711" alt="all_model_coverage" src="https://user-images.githubusercontent.com/25418541/119738652-515c6380-be81-11eb-8d4e-5b4ae20b3917.png"><br /><br /><br />
<img width="711" alt="controller_coverage" src="https://user-images.githubusercontent.com/25418541/119738674-591c0800-be81-11eb-82dc-88eba3b9eda3.png"><br /><br />


* **[Full project specifications (ITA)](https://github.com/ChristianRomeo/ing-sw-2021-Staiano-Tissot-Romeo/wiki/Specifications)**

![Cranio-Creations](https://www.justnerd.it/wp-content/uploads/2019/02/cranio-creations-cover-generica.jpg)
