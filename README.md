# Remot-Health-Monitoring-System








#RHMS application describtion

Remote health monitoring Systems Utilizes technology to keep an eye on patients wherever they might be!

And help raising the patient's life quality when used in the treatment of chronic illnesses.

THE RHMSs usually consist of:

1- Monitoring gadget which needs a sensor that can wirelessly transmit data about physiological parameters.

2- Software that will analyse health data providing therapy suggestions and warnings.


#**Technologies Describtion**

#**Java TCP Sockets**

A Java socket is one endpoint of a two-way communication relationship between two programs running on the network.

For the TCP layer to recognize the application that data is intended for, a socket must be bound to a port number.

A port number and an IP address make up an endpoint. In this project we focus on TCP since it offers a reliable

connection.


#**Role of Client and Server in The RHMS application**

The main function of this system can only be accomplished through client and

server, which indicate the critical role of them in this project. The client should gather sensors

data to be sent to the server, this is the one and only mission for it. On the other hand, the

server in our system can be either a personal or a medical server. The personal server

represents the mind as it decides whether to take action based on the received information

from the client or not.


##**How to Run and Use the System**
 
The code attached is a 1-device code, but as you go through the files you'll find that the lines of code 
that allows it to work in several devices are commented, you can uncomment them and use each device ip to connect 
them.
We have created a client socket (Sensor Client Application), a client-server socket (Personal Server), and a server socket (Medical Server).
First, Sensor Client Application collects data from the sensors and sends it to the Personal Server's server-side via TCP sockets. 
Upon receiving the data, the Personal Server starts processing the information to decide if it needs to be sent to the caregiver.
If the information needs to be sent to the caregiver, it is sent to the Medical Server via TCP sockets. After that,
the Medical Server show the information and the corresponding action to be taken by the caregiver.

* to Start : run medical server-> personal server -> Sensor Client application 
* presss "Start the system" 
* Head back to terminal and enter the execution time in seconds (must be > 60s)
* The system will generate the data randomly
