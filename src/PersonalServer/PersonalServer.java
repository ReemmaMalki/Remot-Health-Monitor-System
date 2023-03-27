
package PersonalServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PersonalServer {

    public static void main(String[] args) throws IOException , ClassNotFoundException {
        
        //Enter the port number here
        int serverPort = 600;

        showData_interface show = new showData_interface();

        //variables
        ServerSocket serverSocket = new ServerSocket(serverPort);
        String date;
        String time;
        double Temperture;
        double HeartRate;
        double OxygenLevel;
        String msgT, msgH, msgO;
        msgT = msgH = msgO = "";

        while (true) {

            // Waiting for incoming connection request
            System.out.println("Waiting for client on port number " + serverPort + "...");
            Socket socket = serverSocket.accept();

            // Create an input stream to recieve objects from the client
            ObjectInputStream objFromClient = new ObjectInputStream(socket.getInputStream());

            String[] sensorsData;

            System.out.println("Connected...");

            // While loop to send and receive data and messages
            while (true) {

                // Variable to indicate whether an object has been sent or not
                int sendAlert = 0;

                // Message to sent to the server
                String msg = "";

                //get object from Sensorclient
                sensorsData = (String[]) objFromClient.readObject();
                //if Sensorclient sent a null object then end the connection
                if (sensorsData == null) {
                    break;
                }

                //fill the variables with the values coming from the object
                date = sensorsData[0];
                time = sensorsData[1];
                Temperture = Double.parseDouble(sensorsData[2]);
                HeartRate = Double.parseDouble(sensorsData[3]);
                OxygenLevel = Double.parseDouble(sensorsData[4]);

                //if Temperture  high > Noraml Rate  send to Medical server
                if (Temperture > 38) {
                    
                    System.out.println("At date: " + date + ",time " + time + ", Temperature is high " + Temperture);
                    System.out.println("An alert massage is sent to the Medical Server");
                    msgT = "At date: " + date + ",time " + time + ", Temperature is high " + Temperture;
                    msg += msgT + "\n";
                    show.tempToString(msgT);
                    show.setVisible(true);
                    sendAlert = 1;

                } else {
                    
                    System.out.println("At date: " + date + ",time " + time + ", Temperature is normal");
                    msgT = "At date: " + date + ",time " + time + ", Temperature is normal";
                    msg += msgT + "\n";
                    show.tempToString(msgT);
                    show.setVisible(true);

                } //temp

                //HeartRate greater than Noraml Rate  send to Medical server
                if (HeartRate > 100) {
                    
                    System.out.println("At date: " + date + ",time " + time + ", Heart rate is above normal  " + HeartRate);
                    System.out.println("An alert massage is sent to the Medical Server");
                    msgH = "At date: " + date + ",time " + time + ", Heart rate is above normal  " + HeartRate;
                    msg += msgH + "\n";
                    show.heartToString(msgH);
                    show.setVisible(true);
                    sendAlert = 1;

                  //HeartRate less than Noraml Rate 
                } else if (HeartRate < 60) {
                    
                    System.out.println("At date: " + date + ",time " + time + ", Heart rate is below normal " + HeartRate);
                    System.out.println("An alert massage is sent to the Medical Server");
                    msgH = "At date: " + date + ",time " + time + ", Heart rate is below normal  " + HeartRate;
                    msg += msgH + "\n";
                    show.heartToString(msgH);
                    show.setVisible(true);
                    sendAlert = 1;

                } else {
                    
                    System.out.println("At date: " + date + ",time " + time + ", Heart rate is normal");
                    msgH = "At date: " + date + ",time " + time + ", Heart rate is normal";
                    msg += msgH + "\n";
                    show.heartToString(msgH);
                    show.setVisible(true);

                }//Heart

                //Oxagen level low send to Medical server 
                //Note : if the object(information like date,time,Oxygenlevel,...) never send to medical  ==>sendAlert=0 then send object send to medical srver sendAlert change to 1
                if (OxygenLevel < 95) {
                    
                    System.out.println("At date: " + date + ",time " + time + ", Oxygen saturation is low " + OxygenLevel);
                    System.out.println("An alert massage is sent to the Medical Server");
                    msgO = "At date: " + date + ",time " + time + ", Oxygen saturation is low " + OxygenLevel + "\n";
                    msg += msgO;
                    show.oxToString(msgO);
                    show.setVisible(true);
                    sendAlert = 1;

                } else {
                    
                    System.out.println("At date: " + date + ",time " + time + ", Oxygen is normal");
                    msgO = "At date: " + date + ",time " + time + ", Oxygen is normal";
                    msg += msgO + "\n";
                    show.heartToString(msgO);
                    show.setVisible(true);
                }
                
                
                System.out.println("");
                if (sendAlert == 1) {

                    /*
        
                    // Run the program on different machines:

                    InetAddress address = InetAddress.getByName("IP Address here..");
                    String serverName = address.getHostName();
                    Socket s = new Socket(serverName , serverPort);

                     */
                    
                     //Run the program on different machines:

                    InetAddress address = InetAddress.getByName("10.24.147.213");
                    String serverName = address.getHostName();
                    Socket Socket2 = new Socket(serverName , 800);
                    // Create socket, to connet to the Medical Server
                    //  Send request using the serverPort 800
                    // Socket Socket2 = new Socket("localhost" , 800);

                    // Create an output stream to send objects to the Mediaclserver
                    ObjectOutputStream obgToSend = new ObjectOutputStream(Socket2.getOutputStream());

                    // Create a buffered writer to send messages to the Mediaclserver
                    BufferedWriter msgToSend = new BufferedWriter(
                            new OutputStreamWriter(Socket2.getOutputStream()));

                    obgToSend.writeObject(sensorsData);
                    msgToSend.write(msg);
                    msgToSend.flush();
                    Socket2.close();
                }
            }
            socket.close();
            System.out.println("Connection ends...\n\n");
        }
    }
}