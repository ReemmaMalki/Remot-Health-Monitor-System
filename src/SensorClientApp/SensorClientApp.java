package SensorClientApp;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Random;

public class SensorClientApp {
    

    
    public static void main(String[] args) throws IOException , InterruptedException {

        // Personal Server port number
        int serverPort = 600;
        /*
        
        // Run the program on different machines:
        
        InetAddress address = InetAddress.getByName("IP Address here..");
        String serverName = address.getHostName();
        Socket s = new Socket(serverName , serverPort);
        
         */

        // Enter the server name and port number
        String serverName = "localhost";

        // Create socket, to connect to the Personal Server
        // Send request using the "serverPort"
        Socket clientSocket = new Socket(serverName , serverPort);

        // Create an output stream to send objects to the server
        ObjectOutputStream obgToServer = new ObjectOutputStream(clientSocket.getOutputStream());

        // Create a scanner to take input from user if needed
        Scanner input = new Scanner(System.in);
        
        
        int inputUser;
        
        // Create a random variable to generate the sensors data randomly
        Random randNo = new Random();
        
        // Create an object from the main GUI and make it visible
        //MS_interface ms = new MS_interface();
        //ms.setVisible(true);
        
        // Creat an object from the showData_interface to be able to send the data to that GUI
        showData_interface show=new showData_interface();

        MS_interface ms = new MS_interface();
        ms.setVisible(true);
        
        
        System.out.println("Enter the time of execution in seconds..\n"
                + "(Note that the minimum time of execution is 60 seconds)");
        inputUser = input.nextInt();

        if (inputUser < 60) {
            inputUser = 60;
        }

        System.out.println("The Sensor Client server will send the data every 5 seconds to the Personal Server application");

        // Create array of {date, time, Temperature, HeartRate, OxygenLevel} 
        String date = java.time.LocalDate.now().toString();
        String time;
        double Temperature = 0;
        double HeartRate = 0;
        double OxygenLevel = 0;
        String[] sensorsData;
        
        // Loop for a persistent connection over a period of time
        LocalTime currentTime = java.time.LocalTime.now();
        LocalTime endTime = currentTime.plusSeconds(inputUser);

        
        while (currentTime.isBefore(endTime)) {

            // Save current time
            time = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Generate and save data
            Temperature = (int) ((36 + (41.1 - 36) * randNo.nextDouble()) * 10) / 10.0;
            HeartRate = (int) ((50 + (120.1 - 50) * randNo.nextDouble()) * 10) / 10.0;
            OxygenLevel = (int) ((60 + (100.1 - 60) * randNo.nextDouble()) * 10) / 10.0;

            // Save the data in the array
            sensorsData = new String[]{date , time , String.valueOf(Temperature) , String.valueOf(HeartRate) , String.valueOf(OxygenLevel)};

            // Store the sensed data in a vriable, print it on the console, and send it to the showData_interface to appear on the GUI
            String msgT= "At date: " + date + ", time " + time + ", sensed temperature is " + Temperature;
            System.out.println(msgT);
            show.tempToString(msgT);
            show.setVisible(true);
            
            
            String msgH="At date: " + date + ", time " + time + ", sensed heart rate is " + HeartRate;
            System.out.println(msgH);
            show.heartToString(msgH);
            show.setVisible(true);
            
            
            String msgO="At date: " + date + ", time " + time + ", sensed oxygen saturation is " + OxygenLevel;
            System.out.println(msgO);
            show.oxToString(msgO);
            show.setVisible(true);
            
            System.out.println();

            // Send the array to the Personal Server
            obgToServer.writeObject(sensorsData);

            // Wait for 5 seconds to generate next data if the data generated randomly
            Thread.sleep(5000);

            currentTime = java.time.LocalTime.now();
        }
        // Send a null object to the Personal server to end the connection
        obgToServer.writeObject(null);
        // Close objects
        obgToServer.close();
    }

}
