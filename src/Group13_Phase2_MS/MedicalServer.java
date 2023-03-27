package Group13_Phase2_MS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MedicalServer {

    public static void main(String[] args) throws IOException , ClassNotFoundException {

        // Creat an object from the Action_interface to access the GUI printing method in the class
        Action_interface alert= new Action_interface();
        alert.setVisible(true);
        //Creat a string (action) to store the action that appear on the (alert) GUI
        String action;
        
        // Enter the port number here
        int serverPort = 800;

        // create a server socket
        ServerSocket serverSocket = new ServerSocket(serverPort);

        while (true) {

            // Listen for a connection request
            Socket socket = serverSocket.accept();

            // Create an input stream to recieve objects from the client
            ObjectInputStream objFromClient = new ObjectInputStream(socket.getInputStream());

            // Create BufferedReader to read message from Personal Server 
            BufferedReader msgToRead = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // Convert received object to an array of string
            String[] sensorsData = (String[]) objFromClient.readObject();

            if (sensorsData != null) {
                // Declare variables with corresponding values in the array
                String date = sensorsData[0];
                String time = sensorsData[1];
                double Temperture = Double.parseDouble(sensorsData[2]);
                double HeartRate = Double.parseDouble(sensorsData[3]);
                double OxygenLevel = Double.parseDouble(sensorsData[4]);

                // Store and print the message taken from the personal server
                String msgFromClient = msgToRead.readLine() + "\n";
                msgFromClient += msgToRead.readLine() + "\n";
                msgFromClient += msgToRead.readLine();

                System.out.println(msgFromClient);
                System.out.print("ACTION: ");

                // Decides the appropriate action
                if (Temperture >= 39 & HeartRate > 100 & OxygenLevel < 95) {
                    
                    //Store the alert in the (action) variable
                    action= "Send an ambulance to the patient!";
                    
                    // Print the action on the consloe
                    System.out.println(action);
                    
                    // Send the action to the (Action_interface) to appear on the GUI
                    alert.toString(action);
                    
                    // Make the GUI visible
                    alert.setVisible(true);
                    
                } else if (Temperture >= 38 & Temperture <= 38.9 & HeartRate >= 95 & HeartRate <= 98 & OxygenLevel < 80) {
                    
                    action= "Call the patient's family!";
                    System.out.println(action);
                    alert.toString(action);
                    alert.setVisible(true);
                    
                } else {
                    
                    action= "Warning, advise patient to make a checkup appointment!";
                    System.out.println(action);
                    alert.toString(action);
                    alert.setVisible(true);
                    
                }
            }
            System.out.println("");
        }
    }
}
