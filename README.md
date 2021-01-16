# 👨‍🍳 Restaurant Management System

[![Open Source Love svg1](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](#)
[![GitHub Forks](https://img.shields.io/github/forks/harismuneer/Restaurant-Management-System.svg?style=social&label=Fork&maxAge=2592000)](https://www.github.com/harismuneer/Restaurant-Management-System/fork)
[![GitHub Issues](https://img.shields.io/github/issues/harismuneer/Restaurant-Management-System.svg?style=flat&label=Issues&maxAge=2592000)](https://www.github.com/harismuneer/Restaurant-Management-System/issues)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat&label=Contributions&colorA=red&colorB=black	)](#)

## Project Description
DineOut is an **Android based Restaurant Management System** that aims to digitalize the process of various restaurant operations including **ordering, billing kitchen, hall and inventory management**. 

The main purpose is to **improve the performance** of the restaurant by eradicating the daily paperwork. With this system the tasks
would be performed in less amount of time and more efficiently. An additional benefit of this software is that during the rush hours the load can be balanced effectively, and restaurants would perform better than usual. In addition to this, human error that occurs when performing tasks manually is also minimized and presence of queues in the system to assign tasks to chefs can reduce congestion in the kitchen. The system would also result in reduction of labor which would result in the reduction of expenses of the restaurant. Feedback module would help the restaurant check for how well they are performing, and monthly/yearly figures can be checked by the billing module to see the trends in sales and profits. These benefits can potentially result in generation of more revenues for the restaurant. 

***For complete details regarding the Functional, Non-Functional Requirements and Analysis Models, you can refer to the detailed [Software Requirements Specification document](../master/documents/SRS%20(Latest).pdf).***

## Purpose
This application was developed as a final project for our **Software Engineering (CS303)** Course. The whole class was divided into teams of 4-5 members and each team was assigned a particular module of this Restaurant Management System. Besides coding our own module, at the end, **our team was also given the responsibility to meet other teams, discuss and resolve the issues that they are facing in the application and ultimately take their developed modules and integrate all the modules together into one working application**. This exercise made us familiar with the issues that occur during the integration of modules in real software projects like following consistent database schema, variable names, libraries versions etc.


---
## 👨🏼‍💻 Contributors
It was really fun to work with these awesome geeks to get the job done:

* [Ramsha Siddiqui](https://github.com/Rmsharks4)
* [Hassaan Elahi](https://github.com/Hassaan-Elahi)
* [Mahnoor Kashif](https://github.com/mahnoorkashif)
* [Shafaq Arshad](https://github.com/Shafaq15)
* [Marriam Sajid](https://github.com/marriamsajid)
* All members of the Software Engineering class of Spring 2018 :)

---


## User Classes and Characteristics
There are **five** types of users for our system. 

### 1. Customer Class
Customers interact with our system directly in order to place order, modify order, get bill and give feedback. We do not store any information related to customers in our system. The process of order taking starts from customers placing order and then the other series of events begin.

### 2. Head Chef/Kitchen Manager
Head Chef can mark a dish as prepared when a chef tells him to do so. He can approve the cancellation of an order whenever a customer edits or removes a dish from his order. He can also assign a dish to a particular chef based on the specialty of the chef.

### 3. Chef
Chefs don’t interact with the system. They just have to look at the dishes present in their queues and prepare the dishes accordingly. Chef’s name, address and specialty etc. are stored in the database.

### 4. Admin
Admin’s job is to manage the inventory and other information related to menu and chefs in the system.

### 5. Hall Manager
Hall Managers will provide its input when he marks the bill as paid when customers pay for their order or get the bill printed. Moreover, he gets a notification whenever a particular order is complete, or some customer asks for help through the system. Hall manager can also see tables in the hall and their status i.e. empty or filled.


## Screenshots of some Interfaces and their Description

<p align="middle">
  <img src="../master/images/1.PNG"/>
 </p>
 
 <p align="middle">
  <img src="../master/images/2.PNG"/>
 </p>

<p align="middle">
  <img src="../master/images/3.PNG"/>
 </p>
 
 <p align="middle">
  <img src="../master/images/4.PNG"/>
 </p>

## How to Run

In order to have a look at the code files and understand the working, simply download this repository and open Android Studio and browse to the downloaded project and open it. It will load the project files and the code will be ready to run. 

Before running the app, use your Google Account to register this Application on Firebase Console, with any name you want. I have provided a copy of the **Firebase Database** [Firebase Realtime DB Backup](../master/database/Firebase%20Realtime%20DB%20Backup.json). Download it and import it in the Firebase console. Then you will have a working Database for the app. After that using your Google Account, login to Android Studio. Then you can run the project.

The login credentials for Admin, Hall and Kitchen Manager's interfaces are:
- admin@gmail.com
- chef@gmail.com
- hallmanager@gmail.com

All have the password: 123456

<br>
<hr>
<a href="https://github.com/harismuneer"><img alt="views" title="Github views" src="https://komarev.com/ghpvc/?username=harismuneer&style=flat-square" width="125"/></a>

<h1 align="left">Hey there, I'm <a href="https://www.linkedin.com/in/harismuneer/">Haris </a><img src="https://media.giphy.com/media/hvRJCLFzcasrR4ia7z/giphy.gif" width="28"> 
 <a href="https://github.com/harismuneer/Ultimate-Facebook-Scraper"><img align="right" src="https://user-images.githubusercontent.com/30947706/79588950-17515780-80ee-11ea-8f66-e26da49fa052.png" alt="Ultimate Facebook Scraper (UFS)" width="200"/></a> - Maker of Things</h1> 


### Creator of <a href="https://github.com/harismuneer/Ultimate-Facebook-Scraper">Ultimate Facebook Scraper</a> (one of the best software to collect Facebook data for research & analysis) 

<hr>

<h2 align="left">🌐 Connect</h2>
<p align="left">
  <a href="https://www.linkedin.com/in/harismuneer/"><img title="Follow on LinkedIn" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"/></a>
  <a href="https://www.facebook.com/harismuneer99"><img title="Connect on Facebook" src="https://img.shields.io/badge/Facebook-1877F2?style=for-the-badge&logo=facebook&logoColor=white"/></a>
  <a href="https://twitter.com/harismuneer99"><img title="Follow on Twitter" src="https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white"/></a>
  <a href="mailto:haris.muneer5@gmail.com"><img title="Email" src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/></a>
  <a href="https://github.com/harismuneer"><img title="Follow on GitHub" src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"/></a>
  <a href="https://www.instagram.com/harismuneer99"><img title="Follow on Instagram" src="https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white"/></a>
  <a href="https://www.youtube.com/channel/UCZ-uBd7g0E2Bp-0tXtSlSjw?sub_confirmation=1"><img title="Subscribe on YouTube" src="https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white"/></a>
</p>


## 🤝 Consulting / Coaching
Stuck with some problem? Need help in solution development, guidance, training or capacity building? I am a Full Stack Engineer turned Project Manager with years of technical and leadership experience in a diverse range of technologies and domains. Let me know what problem you are facing at <b>haris.muneer5@gmail.com</b> and we can schedule a consultation meeting to help you get through it.

## 👨‍💻 Technical Skills & Expertise

- Development of Web Applications, Mobile Applications, and Desktop Applications
- Development of Machine Learning/Deep Learning models, and deployment 
- Web Scraping, Browser Automation, Python Scripting
<hr>
<br>

## Contributions Welcome
[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](#)

If you find any bug in the code or have any improvements in mind then feel free to generate a pull request.

## Issues
[![GitHub Issues](https://img.shields.io/github/issues/harismuneer/Restaurant-Management-System.svg?style=flat&label=Issues&maxAge=2592000)](https://www.github.com/harismuneer/Restaurant-Management-System/issues)

If you face any issue, you can create a new issue in the Issues Tab and I will be glad to help you out.

## License
[![MIT](https://img.shields.io/cocoapods/l/AFNetworking.svg?style=style&label=License&maxAge=2592000)](../master/LICENSE)

Copyright (c) 2018-present, harismuneer, rmsharks4, Hassaan-Elahi, mahnoorkashif, shafaq15, marriamsajid, Software Engineering class of Spring 2018                                                        
