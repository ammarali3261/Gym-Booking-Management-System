package cst2550_coursework;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This is the MultiTHreaded server for the Gym program All the computations are
 * done here The server also connects to the database that stores all the Gym
 * records This server gets data and writes data to the database upon the
 * client's requests Once the required operation has been performed, the server
 * sends the results back to the client so that they can see the results on the
 * GUI The Server also has the option to run as a standalone application if 
 * command line arguments are provided.
 *
 * @author Ammar
 */
public class Server implements Runnable {

    //creating some global variables which will be used un the program
    boolean isLoggedIn = false;
    boolean isOptionSelected = false;
    String option = "";

    //creatinf socket required for connection
    Socket connectionSocket;
    static Connection conn = null;

    //constructor of the class that takes Socket as a parameter
    Server(Socket csocket) {
        this.connectionSocket = csocket;

    }

    //main method of the class which creates a ServerSocket and is always 
    //listening and accepting new clients that try to connect to it.
    public static void main(String args[]) {
        try {
            try {
                conn = DBConnection.getConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
            //if no arguments from command line then function as a multitheaded safe server for clients
            if (args.length == 0) {
                //creates a server socket with port 67898
                ServerSocket server = new ServerSocket(6789);
                System.out.println("Listening on port 6789");

                while (true) {
                    //accepting client connections
                    Socket sock = server.accept();
                    System.out.println("Connected");
                    new Thread(new Server(sock)).start();
                }
                //else behave as a standalone command line application that takes command line arguments  
            } else {
                //switch cases to interpret what the user has input and then work accordingly
                Scanner input = new Scanner(System.in);
                switch (args[0].toUpperCase()) {
                    case "DELETE": {
                        int bookingID = 100000000;
                        boolean check = true;
                        do {
                            try {
                                //ask for the user input
                                System.out.println("Please enter the booking ID (Integer expected): ");
                                bookingID = input.nextInt();
                                //method to delete booking
                                Delete(bookingID);
                                check = false;
                            } catch (NumberFormatException | InputMismatchException ex) {
                                input.nextLine();
                            }
                        } while (check == true);
                        break;
                    }
                    case "ADD": {
                        boolean check = true;
                        do {
                            try {
                                //getting user inputs
                                Connection conn = DBConnection.getConnection();
                                System.out.println("Please enter staff ID (String expected eg. S001) ");
                                String staffID = input.next().toUpperCase();

                                System.out.println("Please enter client ID (String expected eg. C001) ");
                                String clientID = input.next().toUpperCase();

                                System.out.println("Please enter the month of booking (integer value between 1 and 12 expected eg. 2)");
                                int month = input.nextInt();

                                System.out.println("Please enter the day of booking (integer value between 1 and 31 expected eg. 23)");
                                int day = input.nextInt();
                                if ((month > 12 || month < 1) || (day > 31 || day < 1)) {
                                    throw new Exception();
                                }

                                String date = "2020-" + month + "-" + day;

                                System.out.println("Please enter start time (integer between 6 and 22 expected) ");
                                int startTime = input.nextInt();

                                System.out.println("Please enter end Time (integer between 7 and 23 expected) ");
                                int endTime = input.nextInt();
                                if ((startTime > 22 || startTime < 6) || (endTime > 23 || endTime < 7 || endTime <= startTime)) {
                                    throw new NullPointerException();
                                }

                                System.out.println("Please enter focus\nEnter 1 for Weight-Loss\n2 for Muscle-Gain\n3 for Flexibility ");
                                int option = input.nextInt();
                                String focus = "";
                                if (option == 1) {
                                    focus = "Weight-Loss";
                                }
                                if (option == 2) {
                                    focus = "Muscle-Gain";
                                }
                                if (option == 3) {
                                    focus = "Flexibility";
                                }
                                //method for adding the booking
                                Add(staffID, clientID, date, startTime, endTime, focus, conn);
                                check = false;
                            } catch (NumberFormatException | InputMismatchException ex) {
                                System.out.println("\n!!!!!!!!\nERROR\nENTER ALL DETAILS AGAIN IN CORRECT FORMAT\n");
                                input.nextLine();
                            } catch (SQLException ex) {
                                System.out.println("ERROR: " + ex.getMessage());
                            } catch (NullPointerException ex) {
                                System.out.println("\n!!!!!!!\nERROR\nInvalid time value\n!!!!!!!\n");
                                input.nextLine();
                            } catch (Exception ex) {
                                System.out.println("\n!!!!!!!\nERROR\nInvalid day or month value\n!!!!!!!\n");
                                input.nextLine();
                            }
                        } while (check == true);
                        break;
                    }
                    case "UPDATE": {
                        boolean check = true;
                        do {
                            try {
                                //getting user inputs
                                Connection conn = DBConnection.getConnection();
                                System.out.println("Please enter booking ID (integer expected eg. 21) ");
                                int bookingId = input.nextInt();

                                System.out.println("Please enter staff ID (String expected eg. S001) ");
                                String staffID = input.next().toUpperCase();

                                System.out.println("Please enter client ID (String expected eg. C001) ");
                                String clientID = input.next().toUpperCase();

                                System.out.println("Please enter the month of booking (integer value between 1 and 12 expected eg. 2)");
                                int month = input.nextInt();

                                System.out.println("Please enter the day of booking (integer value between 1 and 31 expected eg. 23)");
                                int day = input.nextInt();
                                if ((month > 12 || month < 1) || (day > 31 || day < 1)) {
                                    throw new Exception();
                                }

                                String date = "2020-" + month + "-" + day;

                                System.out.println("Please enter start time (integer between 6 and 22 expected) ");
                                int startTime = input.nextInt();

                                System.out.println("Please enter end Time (integer between 7 and 23 expected) ");
                                int endTime = input.nextInt();
                                if ((startTime > 22 || startTime < 6) || (endTime > 23 || endTime < 7) || endTime <= startTime) {
                                    throw new NullPointerException();
                                }

                                System.out.println("Please enter focus\nEnter 1 for Weight-Loss\n2 for Muscle-Gain\n3 for Flexibility ");
                                int option = input.nextInt();
                                String focus = "";
                                if (option == 1) {
                                    focus = "Weight-Loss";
                                }
                                if (option == 2) {
                                    focus = "Muscle-Gain";
                                }
                                if (option == 3) {
                                    focus = "Flexibility";
                                }

                                //call to method to update booking
                                Update(bookingId, staffID, clientID, date, startTime, endTime, focus);
                                check = false;

                            } catch (NumberFormatException | InputMismatchException ex) {
                                System.out.println("\n!!!!!!!!\nERROR\nENTER ALL DETAILS AGAIN IN CORRECT FORMAT\n");
                                input.nextLine();
                            } catch (SQLException ex) {
                                System.out.println("ERROR " + ex.getMessage());
                            } catch (NullPointerException ex) {
                                System.out.println("\n!!!!!!!\nERROR\nInvalid time value\n!!!!!!!\n");
                                input.nextLine();
                            } catch (Exception ex) {
                                System.out.println("\n!!!!!!!\nERROR\nInvalid day or month value\n!!!!!!!\n");
                                input.nextLine();
                            }
                        } while (check == true);
                        break;
                    }
                    case "LISTAll":
                        //call to method to list all bookings
                        LISTALL();
                        break;
                    case "LISTPT":
                        //getting user input
                        System.out.println("Please enter the trainer id (expected format eg. S001)");
                        String trainerId = input.next().toUpperCase();
                        //call to method to list bookings by trainer id
                        LISTPT(trainerId);
                        break;
                    case "LISTCLIENT":
                        //getting user input
                        System.out.println("Please enter the client id (expected format eg. C001)");
                        String clientId = input.next().toUpperCase();
                        //call to method to list bookings by client id
                        LISTCLIENT(clientId);
                        break;
                    case "LISTDAY": {
                        boolean check = false;
                        do {
                            try {
                                //getting user inputs
                                System.out.println("Please enter the month (integer value between 1 and 12 expected eg. 2)");
                                int month = input.nextInt();

                                System.out.println("Please enter the day (integer value between 1 and 31 expected eg. 23)");
                                int day = input.nextInt();
                                if ((month > 12 || month < 1) || (day > 31 || day < 1)) {
                                    throw new Exception();
                                }
                                String date = "2020-" + month + "-" + day;
                                //call to method to list bookings by date
                                LISTDAY(date);
                                check = true;
                            } catch (Exception ex) {
                                System.out.println("\n!!!!!!!\nERROR\nInvalid day or month value\n!!!!!!!\n");
                                input.nextLine();
                            }
                        } while (check == false);
                        break;
                    }
                    default:
                        System.out.println("USAGE: only options allowed are:\nADD\nUPDATE\nLISTALL\nLISTPT\nLISTCLIENT\nLISTDAY\nDELETE");
                        break;
                }
            }

        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    // run method of the Thread. This is the method that runs once the thread has started
    @Override
    public void run() {
        try {
            //connecting to the database using the DBConnection Class
            conn = DBConnection.connect();
            System.out.println("DataBase connection successfull");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        boolean stop = false;
        DataInputStream dis5 = null;
        try {
            //creating data input stream and reading from it if its not empty. 
            dis5 = new DataInputStream(connectionSocket.getInputStream());
            if (dis5.available() != 0) {
                if (dis5.readUTF().equals("stop")) {
                    //if the client sends 'stop' the client will be disconnected from the server
                    stop = true;
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        //The client will be connected to the server as long as the dont send 'stop'
        while (stop == false) {
            try {
                //checking if the client has Logged in
                if (!isLoggedIn) {
                    //if the client has not logged in, the server reads the username and password from the client.
                    DataInputStream dis = new DataInputStream(connectionSocket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                    String s = "";
                    String[] user_pass = new String[2];
                    int check = 0;
                    while (dis.available() > 0) {
                        s = dis.readUTF();
                        //spliting the string based on the "," delimeter
                        user_pass = s.split(",");
                        check = 1;
                    }
                    //if the username and password have been read by the server then only will it go inside this block of code
                    if (check == 1) {
                        //checks if the username and password are correct by using isCorrectPassword method
                        if (isCorrectPassword(user_pass[0], user_pass[1], conn)) {
                            //if credentials are correct then isLoggedIn is set to be true
                            isLoggedIn = true;
                            dos.writeUTF("true");
                            dos.flush();

                            //getting trainer names from the database using getTrainerNames method and storing them in an arrayList
                            ArrayList<String> trainerNames = getTrainerNames(conn);
                            //converts the arraylist into a string seperated by ',' and sends it to the client
                            String namesToSend = "";
                            for (int i = 0; i < trainerNames.size(); i++) {
                                if (i < trainerNames.size() - 1) {
                                    namesToSend = namesToSend + trainerNames.get(i) + ",";
                                } else {
                                    namesToSend = namesToSend + trainerNames.get(i);
                                }
                            }

                            //getting trainer specialities from the database using getTrainerFocus method and storing them in an arrayList
                            ArrayList<String> trainerFocus = getTrainerFocus(conn);
                            //converts the specialities of each trainer into a string separated by ':'
                            String focusToSend = "";
                            for (int i = 0; i < trainerFocus.size(); i++) {
                                if (i < trainerFocus.size() - 1) {
                                    focusToSend = focusToSend + trainerFocus.get(i) + ":";
                                } else {
                                    focusToSend = focusToSend + trainerFocus.get(i);
                                }
                            }
                            //sending trainer names to the client  
                            dos.writeUTF(namesToSend);
                            dos.flush();

                            //sending trainers' specialities to the client
                            dos.writeUTF(focusToSend);
                            dos.flush();

                            //sending clientnames from database to the client
                            dos.writeUTF(getClientNames(conn));
                            dos.flush();

                        } else {
                            //if credentials are wrong then 'fasle' is sent to inform the client about this
                            dos.writeUTF("false");
                        }
                    }
                }

                //if the user has logged in but has not yet selected an opton from the homescreen only then will the code inside this block get executed
                if (isLoggedIn && !isOptionSelected) {
                    try {
                        //reading data from client and storing it in a global variable called option
                        //this option tells us what operation the user has selected from the home screen. eg ADD, UPDATE , etc
                        DataInputStream dis2 = new DataInputStream(connectionSocket.getInputStream());
                        //read only if the stream is not empty
                        if (dis2.available() != 0) {
                            option = dis2.readUTF();
                            isOptionSelected = true;
                        }
                    } catch (EOFException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }

                //if else statements to check what the value of the option variable is and then proceed accordingly
                if (option.equals("ADD")) {
                    //Calling ADD() method
                    ADD(conn);
                } else if (option.equals("UPDATE")) {
                    //Calling UPDATE() method
                    UPDATE();

                } else if (option.equals("DELETE")) {
                    //Calling DELETE() method
                    DELETE();

                } else if (option.equals("LIST")) {
                    //Calling LIST() method
                    LIST();

                } else if (option.equals("stop")) {
                    //if the option is 'stop' then the server breaks out of the while loop and the client is disconnected
                    break;
                }

            } catch (IOException | SQLException ex) {
                //System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }
    //synchronized method to update bookings of the clients.
    //It takes all the details as parameters and then updates that particular record in the database

    public static synchronized void updateBookingMethod(String staffId, String clientId, String date, int startTime, int endTime, String focus, int bookingId) throws SQLException {
        //Adding data to the database using a prepared statement
        String sqlInsert = "UPDATE booking SET staffId = ?,clientId = ?,date = ?,startTime = ?,endTime = ?,focus = ? WHERE bookingId = ?";

        //setting valus for the ?s
        PreparedStatement ps = conn.prepareStatement(sqlInsert);
        ps.setString(1, staffId);
        ps.setString(2, clientId);
        ps.setString(3, date);
        ps.setInt(4, startTime);
        ps.setInt(5, endTime);
        ps.setString(6, focus);
        ps.setInt(7, bookingId);

        //executing the sql statement
        ps.executeUpdate();
    }

    //main UPDATE method with calls the other methods to update bookings
    public synchronized void UPDATE() throws IOException, SQLException {
        try {
            boolean isSame = false;
            boolean doesNotExist = false;
            //reads object from client 
            ObjectInputStream ois = new ObjectInputStream(connectionSocket.getInputStream());
            //type casting the object into Booking object
            Booking b = (Booking) ois.readObject();

            //if statement to check if the user clicked the back button
            if (!b.getTrainerName().equals("back")) {

                String clientIdFromClient = getClientId(b.getClientName(), conn);

                //SQL query to select all columns where booking ID is equal to the given booking id.
                String SQL = "SELECT * FROM booking WHERE bookingId = ?";
                PreparedStatement ps = conn.prepareStatement(SQL);
                ps.setInt(1, b.getBookindId());
                ResultSet rs = ps.executeQuery();

                //creating temporary variable to hold data from the columns in database
                String clientId = "", staffId = "", date = "", focus = "";
                int startTime = 0, endTime = 0;

                //if there is a booking with that ID then store the column data into the temporary variables.
                if (rs.next()) {
                    staffId = rs.getString("staffId");
                    clientId = rs.getString("clientId");
                    date = rs.getString("date");
                    startTime = rs.getInt("startTime");
                    endTime = rs.getInt("endTime");
                    focus = rs.getString("focus");
                } else {
                    //if no record exists with that booking id than doesNotExist = true
                    doesNotExist = true;
                }
                //this block of code will be executed if the booking exists
                if (!doesNotExist) {
                    //checking if the details in the DB are the same as those sent by the client
                    if (staffId.equals(getStaffId(b.getTrainerName(), conn)) && clientId.equals(clientIdFromClient) && date.equals(b.getDate())
                            && startTime == b.getStartTime() && endTime == b.getEndTime() && focus.equals(b.getFocus())) {
                        isSame = true;
                        System.out.println("b");
                        //sends an object to the client informing about duplicate booking
                        ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream());
                        b.setFocus("Duplicate Booking");
                        oos.writeObject(b);
                    } else {
                        //calls the Update booking method
                        updateBookingMethod(null, null, null, 0, 0, null, b.getBookindId());

                        //calls the isDuplicate method to see is it is a duplicate booking
                        if (!isDuplicateBooking(b.getTrainerName(), clientIdFromClient, b.getDate(), b.getStartTime(), b.getEndTime(), b.getFocus())) {
                            //if the booking is not duplicate then update the record in the database
                            updateBookingMethod(getStaffId(b.getTrainerName(), conn), clientIdFromClient, b.getDate(), b.getStartTime(), b.getEndTime(), b.getFocus(), b.getBookindId());
                            //send booking id to the client
                            ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream());
                            b.setBookindId(b.getBookindId());
                            oos.writeObject(b);
                        } else {
                            
                            //if it is a duplicate booking then the client is informed about this 
                            updateBookingMethod(staffId, clientId, date, startTime, endTime, focus, b.getBookindId());
                            ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream());
                            b.setFocus("Duplicate Booking");
                            oos.writeObject(b);
                        }
                    }
                } else {
                    
                    //this code runs when the booking id does not exist in the DB 
                    ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream());
                    b.setFocus("Invalid BookingID");
                    oos.writeObject(b);
                }

            } else {
                //if the user clicks on the back button this block of code executes
                isOptionSelected = false;
                option = "";
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    //method to list all the bookings from the DB
    public synchronized void listAll() {
        try {
            //SQL query to select all bookings
            String SQL = "SELECT * FROM booking ";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            String listResults = "";
            //converting the results into a string and sending them to the client
            listResults = "BookingId   ClientId          ClientName      Date        StartTime    EndTime    Focus        StaffId                 StaffName\n"
                    + "---------------------------------------------------------------------------------------------------------------------------------------------";
            //getting the booking details from the booking table as well as the names of the clients and the trainers from the database
            while (rs.next()) {
                String clientId = rs.getString("clientId");
                String staffId = rs.getString("staffId");
                String SQL3 = "SELECT fName FROM client WHERE clientId = '" + clientId + "'";
                String SQL4 = "SELECT fName FROM staff WHERE staffId = '" + staffId + "'";

                ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
                String clientName = "";
                if (rs3.next()) {
                    clientName = rs3.getString("fName");
                }

                ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
                String staffName = "";
                if (rs4.next()) {
                    staffName = rs4.getString("fName");
                }
                //storing the results in a string format
                listResults = listResults + "\n     " + rs.getInt("bookingId") + "             " + rs.getString("clientId") + "             " + clientName + "       " + rs.getString("date") + "     "
                        + rs.getInt("startTime") + "            " + rs.getInt("endTime") + "         " + rs.getString("focus") + "          " + rs.getString("staffId") + "\t" + "             " + staffName;
            }
            //sending the results to the client
            DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
            dos.writeUTF(listResults);
            dos.flush();

        } catch (IOException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    //method to list bookings according to date
    public synchronized void listByDate() {
        try {
            //reading date from the client
            DataInputStream dis = new DataInputStream(connectionSocket.getInputStream());
            String date = dis.readUTF();

            //SQL statement to select bookings according to date
            String SQL = "SELECT * FROM booking WHERE date = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            //getting the booking details from the booking table as well as the names of the clients and the trainers from the database
            String listResults = "";
            if (rs.next()) {
                ResultSet rs2 = ps.executeQuery();
                listResults = "BookingId   ClientId          ClientName      Date        StartTime    EndTime    Focus        StaffId                 StaffName\n"
                        + "---------------------------------------------------------------------------------------------------------------------------------------------";
                while (rs2.next()) {
                    String clientId = rs2.getString("clientId");
                    String staffId = rs2.getString("staffId");
                    String SQL3 = "SELECT fName FROM client WHERE clientId = '" + clientId + "'";
                    String SQL4 = "SELECT fName FROM staff WHERE staffId = '" + staffId + "'";

                    ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
                    String clientName = "";
                    if (rs3.next()) {
                        clientName = rs3.getString("fName");
                    }

                    ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
                    String staffName = "";
                    if (rs4.next()) {
                        staffName = rs4.getString("fName");
                    }
                    //storing the results as a string
                    listResults = listResults + "\n     " + rs2.getInt("bookingId") + "             " + rs2.getString("clientId") + "             " + clientName + "       " + rs2.getString("date") + "     "
                            + rs2.getInt("startTime") + "            " + rs2.getInt("endTime") + "         " + rs2.getString("focus") + "          " + rs2.getString("staffId") + "\t" + "             " + staffName;
                }

                //sending data to the client
                DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                dos.writeUTF(listResults);
                dos.flush();
            } else {
                //if there are no records for the requested date
                //inform the user about this
                DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                dos.writeUTF("No records with Date : " + date);
                dos.flush();
            }

        } catch (IOException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    //method to list bookings according to trainer id
    public synchronized void listByTrainerID() {
        try {
            //reading trainer id from the client
            DataInputStream dis = new DataInputStream(connectionSocket.getInputStream());
            String trainerId = dis.readUTF();

            //SQL statement to select bookings for the given trainer id
            String SQL = "SELECT * FROM booking WHERE staffId = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, trainerId);
            ResultSet rs = ps.executeQuery();
            String listResults = "";
            if (rs.next()) {
                ResultSet rs2 = ps.executeQuery();
                listResults = "BookingId   ClientId          ClientName      Date        StartTime    EndTime    Focus        StaffId                 StaffName\n"
                        + "---------------------------------------------------------------------------------------------------------------------------------------------";
                //getting the booking details from the booking table as well as the names of the clients and the trainers from the database
                while (rs2.next()) {
                    String clientId = rs2.getString("clientId");
                    String staffId = rs2.getString("staffId");
                    String SQL3 = "SELECT fName FROM client WHERE clientId = '" + clientId + "'";
                    String SQL4 = "SELECT fName FROM staff WHERE staffId = '" + staffId + "'";

                    ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
                    String clientName = "";
                    if (rs3.next()) {
                        clientName = rs3.getString("fName");
                    }

                    ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
                    String staffName = "";
                    if (rs4.next()) {
                        staffName = rs4.getString("fName");
                    }
                    //storing the results as a string
                    listResults = listResults + "\n     " + rs2.getInt("bookingId") + "             " + rs2.getString("clientId") + "             " + clientName + "       " + rs2.getString("date") + "     "
                            + rs2.getInt("startTime") + "            " + rs2.getInt("endTime") + "         " + rs2.getString("focus") + "          " + rs2.getString("staffId") + "\t" + "             " + staffName;
                }

                //sending data to the client
                DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                dos.writeUTF(listResults);
                dos.flush();
            } else {
                //if there are no records for the requested trainer id
                //inform the user about this
                DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                dos.writeUTF("No records with Trainer ID : " + trainerId);
                dos.flush();
            }

        } catch (IOException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    //method to list bookings according to client id
    public synchronized void listByClientID() {
        try {
            //reading client id from the client
            DataInputStream dis = new DataInputStream(connectionSocket.getInputStream());
            String clientId = dis.readUTF();

            //SQL statement to get bookings which have the given client ID
            String SQL = "SELECT * FROM booking WHERE clientId = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, clientId);
            ResultSet rs = ps.executeQuery();
            String listResults = "";
            if (rs.next()) {
                ResultSet rs2 = ps.executeQuery();
                listResults = "BookingId   ClientId          ClientName      Date        StartTime    EndTime    Focus        StaffId                 StaffName\n"
                        + "---------------------------------------------------------------------------------------------------------------------------------------------";
                //getting the booking details from the booking table as well as the names of the clients and the trainers from the database
                while (rs2.next()) {
                    String clientId2 = rs2.getString("clientId");
                    String staffId = rs2.getString("staffId");
                    String SQL3 = "SELECT fName FROM client WHERE clientId = '" + clientId2 + "'";
                    String SQL4 = "SELECT fName FROM staff WHERE staffId = '" + staffId + "'";

                    ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
                    String clientName = "";
                    if (rs3.next()) {
                        clientName = rs3.getString("fName");
                    }

                    ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
                    String staffName = "";
                    if (rs4.next()) {
                        staffName = rs4.getString("fName");
                    }
                    //storing the results in a string
                    listResults = listResults + "\n     " + rs2.getInt("bookingId") + "             " + rs2.getString("clientId") + "             " + clientName + "       " + rs2.getString("date") + "     "
                            + rs2.getInt("startTime") + "            " + rs2.getInt("endTime") + "         " + rs2.getString("focus") + "          " + rs2.getString("staffId") + "\t" + "             " + staffName;
                }
                //sending data back to the client
                DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                dos.writeUTF(listResults);
                dos.flush();
            } else {
                //if there are no records for the requested client id
                //inform the user about this
                DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                dos.writeUTF("No records with Client ID : " + clientId);
                dos.flush();
            }
        } catch (IOException | SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    //main method that handles what happens when the user selects list operation 
    public synchronized void LIST() {
        try {
            //reading data from the client
            DataInputStream dis = new DataInputStream(connectionSocket.getInputStream());
            String listOption = dis.readUTF();

            //switch case to check what the client has requested and cater to that.
            switch (listOption) {
                case "By Client ID":
                    listByClientID();
                    break;
                case "By Date":
                    listByDate();
                    break;
                case "By Trainer/Staff ID":
                    listByTrainerID();
                    break;
                case "List All":
                    listAll();
                    break;
                case "back":
                    isOptionSelected = false;
                    option = "";
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            //System.out.println("ERROR: " + ex.getMessage());
        }
    }

    //method that handles what happens when delete button s pressed by the client
    public synchronized void DELETE() {

        ObjectInputStream ois = null;
        try {
            //reading object from the client
            ois = new ObjectInputStream(connectionSocket.getInputStream());
            Booking b = (Booking) ois.readObject();
            if (b.getBookindId() != 0000) {
                int bookingId = b.getBookindId();
                //SQL statement to get all columns from the booking table with given booking ID
                String SQL = "SELECT * FROM booking WHERE bookingId = ?";
                PreparedStatement ps = conn.prepareStatement(SQL);
                ps.setInt(1, bookingId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    //SQL Query to delete the booking
                    String SQL2 = "DELETE FROM booking WHERE bookingId = ?";
                    PreparedStatement ps2 = conn.prepareStatement(SQL2);
                    ps2.setInt(1, bookingId);
                    ps2.executeUpdate();

                    //inform the user about the deletion
                    DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                    dos.writeUTF("Deleted");
                    dos.flush();
                } else {
                    //if no there is no boking with the given booking id in the DB
                    DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
                    dos.writeUTF("No such Booking Exists!!");
                    dos.flush();
                }
            } else {
                //if back button is pressed
                isOptionSelected = false;
                option = "";
            }
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            //System.out.println("ERROR: " + ex.getMessage());
        }
    }

    //method that checks that the username given by the user is a valid one
    //returns true if the username is valid
    public synchronized boolean isCorrectUserName(String str, Connection conn) throws SQLException {
        String SQL = "SELECT UPPER(username) FROM staff WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(SQL);
        ps.setString(1, str);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }

    //this method checks that the password provided by the user matches the password
    //that is associated with the username.
    public synchronized boolean isCorrectPassword(String str, String pass, Connection conn) throws SQLException {
        //first checks if the username is correct
        if (isCorrectUserName(str, conn)) {
            String SQL = "SELECT password FROM staff WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, str);
            ResultSet rs = ps.executeQuery();
            rs.next();
            //returns true if the username and password are correct and else false
            if (rs.getString("password").equals(pass)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //method to get staff id from trainer name
    public synchronized String getStaffId(String trainerName, Connection conn) throws SQLException {
        String SQL = "SELECT staffId FROM staff WHERE fName = ?";
        PreparedStatement ps = conn.prepareStatement(SQL);
        ps.setString(1, trainerName);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String staffId = rs.getString("staffId");
        rs.close();
        return staffId;
    }

    //method to add booking
    public static synchronized int addBookingMethod(String staffId, String clientId, String date, int startTime, int endTime, String focus) throws SQLException {

        //Adding data to the database using a prepared statement
        String sqlInsert = "insert into booking(staffId,clientId,date,startTime,endTime,focus) values(?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sqlInsert);
        ps.setString(1, staffId);
        ps.setString(2, clientId);
        ps.setString(3, date);
        ps.setInt(4, startTime);
        ps.setInt(5, endTime);
        ps.setString(6, focus);

        //executing the sql statement
        ps.execute();

        //selects booking from the DB and gets the booking ID
        String SQL3 = "SELECT bookingId FROM booking WHERE staffId = ? and clientId = ? and date = ? and startTime = ? and endTime = ? and focus = ?";
        PreparedStatement ps2 = conn.prepareStatement(SQL3);
        ps2.setString(1, staffId);
        ps2.setString(2, clientId);
        ps2.setString(3, date);
        ps2.setInt(4, startTime);
        ps2.setInt(5, endTime);
        ps2.setString(6, focus);
        ResultSet rs3 = ps2.executeQuery();
        rs3.next();

        int bookingId = rs3.getInt("bookingId");
        return bookingId;
    }

    //method to get all the staffids 
    public synchronized ArrayList getStaffId(Connection conn) {
        //arraylist to store all the staff IDs
        ArrayList<String> staffId = new ArrayList<>();
        try {
            //select unique staff ids from the trainer table
            String SQL = "SELECT DISTINCT staffId FROM trainer order by staffId";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            //add all staff id to the arraylist 
            while (rs.next()) {
                staffId.add(rs.getString("staffId"));
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return staffId;
    }

    //method to get trainers' specialities 
    public synchronized ArrayList getTrainerFocus(Connection conn) throws SQLException {
        //creating arraylists to store staffids and trainer specialities
        ArrayList<String> staffId = getStaffId(conn);
        ArrayList<String> trainerFocus = new ArrayList<>();

        //adding staff id 
        for (int i = 0; i < staffId.size(); i++) {
            String SQL = "SELECT specialityId FROM trainer WHERE staffId = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, staffId.get(i));
            ResultSet rs = ps.executeQuery();

            //getting speciality ids and storing them
            ArrayList specialityId = new ArrayList<>();
            while (rs.next()) {
                specialityId.add(rs.getString(1));
            }

            //getting speciality names of the corresponding speciality ids
            ArrayList trainer1Focus = new ArrayList<>();
            for (int j = 0; j < specialityId.size(); j++) {
                String SQL2 = "SELECT specialityName FROM speciality WHERE specialityId ='" + specialityId.get(j) + "'";
                ResultSet rs2 = conn.createStatement().executeQuery(SQL2);
                rs2.next();
                trainer1Focus.add(rs2.getString(1));
            }

            //storing focus of a trainer in string
            String toStoreT1F = "";
            for (int a = 0; a < trainer1Focus.size(); a++) {
                if (a < trainer1Focus.size() - 1) {
                    toStoreT1F = toStoreT1F + trainer1Focus.get(a).toString() + ",";
                } else {
                    toStoreT1F = toStoreT1F + trainer1Focus.get(a).toString();
                }

            }

            trainerFocus.add(toStoreT1F);

        }
        return trainerFocus;
    }

    //main method which handles what happens when the client presses the add booking button
    public synchronized void ADD(Connection conn) throws IOException, SQLException {
        try {
            //reading object from the client
            ObjectInputStream ois = new ObjectInputStream(connectionSocket.getInputStream());
            Booking b = (Booking) ois.readObject();
            if (!b.getTrainerName().equals("back")) {

                String clientId = getClientId(b.getClientName(), conn);
                //this block of code is executed only if the booking is not duplicate
                if (!isDuplicateBooking(b.getTrainerName(), clientId, b.getDate(), b.getStartTime(), b.getEndTime(), b.getFocus())) {
                    int bookingNumber = addBookingMethod(getStaffId(b.getTrainerName(), conn), clientId, b.getDate(), b.getStartTime(), b.getEndTime(), b.getFocus());
                    //sending object to the client
                    ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream());
                    b.setBookindId(bookingNumber);
                    oos.writeObject(b);
                } else {
                    //this block of code is executed only if the booking is duplicate
                    ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream());
                    b.setFocus("Duplicate Booking");
                    oos.writeObject(b);
                }
            } else {
                //executes when the client presses the back button of the GUI
                isOptionSelected = false;
                option = "";
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    //method to check id=f the client name and clientId are correct according to what is stored in the client table in the DB 
    public synchronized boolean isCorrectNameAndId(String clientName, String clientId) {
        try {
            //SQL query to select name which have the given client id 
            String SQL = "SELECT fName FROM client WHERE clientId = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, clientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String fName = rs.getString("fName");
                if (fName.equalsIgnoreCase(clientName)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return false;
    }

    //method to get trainer names from the DB
    public synchronized ArrayList getTrainerNames(Connection conn) {

        //array list to store trainer names
        ArrayList<String> trainerNames = new ArrayList<>();
        try {
            //arraylist to store staff ids
            ArrayList staffId = getStaffId(conn);
            //storing trainer names into arraylist 
            for (int i = 0; i < staffId.size(); i++) {
                //SQL query to get name of the trainer from the staff table
                String SQL2 = "SELECT fName from staff where staffId = '" + staffId.get(i) + "'";
                ResultSet rs2 = conn.createStatement().executeQuery(SQL2);
                rs2.next();
                trainerNames.add(rs2.getString("fName"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return trainerNames;
    }

    //method that checks if the booking is a duplicate.
    //returns true if it is a duplicate
    public synchronized boolean isDuplicateBooking(String trainerName, String clientId, String date, int startTime, int endTime, String focus) throws SQLException {

        //getting trainer id from his name
        String staffId = getStaffId(trainerName, conn);
        //SQL query to check if the booking overlaps a previous booking by a trainer on a given date and time 
        String SQL = "SELECT * FROM booking WHERE endTime > '" + startTime + "' and startTime < '" + endTime + "' and (staffId = '" + staffId + "' or clientId = '" + clientId + "') and date = '" + date + "'";
        ResultSet rs = conn.createStatement().executeQuery(SQL);
        if (rs.next()) {
            return true;

        } else {
            return false;
        }
    }

    //method to get 'full' gym client names from the database
    public synchronized String getClientNames(Connection conn) {
        String list = "";
        String newList = "";
        try {
            //sql query to get names of all the gym clients
            String SQL = "SELECT fName, lName FROM client";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                //storing results in a string
                String name = rs.getString("fName") + " " + rs.getString("lName") + ",";
                list = list + name;
            }
            //excluding the last ',' from the list and storing it in a new variable
            newList = list.substring(0, list.length() - 1);
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return newList;
    }

    //method that fetches client id from the database when given the Full client name
    public synchronized String getClientId(String name, Connection conn) {
        String clientId = "";
        try {
            //spliting the name into first name and last name
            List<String> fullName = Arrays.asList(name.split(" "));
            //getting id from the database.
            String SQL = "SELECT clientId FROM client WHERE fName = '" + fullName.get(0) + "' and lName = '" + fullName.get(1) + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            rs.next();
            clientId = rs.getString("clientId");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return clientId;
    }

    public static synchronized void Delete(int bookingID) {

        try {
            Connection conn = DBConnection.getConnection();
            //SQL statement to get all columns from the booking table with given booking ID
            String SQL = "SELECT * FROM booking WHERE bookingId = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, bookingID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //SQL Query to delete the booking
                String SQL2 = "DELETE FROM booking WHERE bookingId = ?";
                PreparedStatement ps2 = conn.prepareStatement(SQL2);
                ps2.setInt(1, bookingID);
                ps2.executeUpdate();

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!\n\nBooking " + bookingID + " deleted successfully\n\n!!!!!!!!!!!!!!!!!!!!!");
            } else {
                //if no there is no boking with the given booking id in the DB
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nNo such Booking Exists\n!!!!!!!!!!!!!!!!!!!!!!!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    //method to add booking from command line
    public static synchronized void Add(String staffId, String clientId, String date, int startTime, int endTime, String focus, Connection conn) throws IOException, SQLException {
        Scanner input = new Scanner(System.in);
        boolean validClientId = false;
        //query to check if the clientId is valid
        String SQL3 = "SELECT * FROM client WHERE clientId = '" + clientId + "'";
        ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
        if (rs3.next()) {
            validClientId = true;
        }
        //get the user input again unless the client id is correct
        while (validClientId == false) {
            System.out.println("\n!!!!!!!!!!\nERROR\nEnter the client ID again");
            clientId = input.next();
            String SQL4 = "SELECT * FROM client WHERE clientId = '" + clientId + "'";
            ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
            if (rs4.next()) {
                validClientId = true;
            }
        }
        //query to check if staffid is valid/correct
        boolean validStaffId = false;
        String SQL2 = "SELECT * FROM staff WHERE staffId = '" + staffId + "'";
        ResultSet rs2 = conn.createStatement().executeQuery(SQL2);
        if (rs2.next()) {
            validStaffId = true;
        }
        //get the user input again unless the staff id is correct
        while (validStaffId == false) {
            System.out.println("Enter the trainer id again");
            staffId = input.next();
            String SQL5 = "SELECT * FROM staff WHERE staffId = '" + staffId + "'";
            ResultSet rs5 = conn.createStatement().executeQuery(SQL5);
            if (rs5.next()) {
                validStaffId = true;
            }
        }
        //creating arraylist to store the specialities
        ArrayList<String> speciality = new ArrayList<>();
        ArrayList<String> specialityName = new ArrayList<>();
        boolean validFocus = false;
        String result = "";
        String SQL6 = "SELECT specialityId from trainer where staffId = '" + staffId + "'";
        ResultSet rs6 = conn.createStatement().executeQuery(SQL6);
        while (rs6.next()) {
            speciality.add(rs6.getString("specialityId"));
        }
        //getting and storing speciality name
        for (int i = 0; i < speciality.size(); i++) {
            String SQL7 = "SELECT specialityName from speciality where specialityId = '" + speciality.get(i) + "'";
            ResultSet rs7 = conn.createStatement().executeQuery(SQL7);
            if (rs7.next()) {
                specialityName.add(rs7.getString("specialityName"));
            }
        }
        //checking if the trainer's speciality matches the one selected by the user.
        for (int i = 0; i < specialityName.size(); i++) {
            if (focus.equals(specialityName.get(i))) {
                validFocus = true;
            }
        }
        //this code exceutes when the user enters wrong focus and is asked to enter again
        int option = 0;
        while (validFocus == false) {
            System.out.println("\n!!!!!!!!!!!!\nERROR\nThe trainer does not have this speciality\nPlease choose another option\nEnter 1 for Weight-Loss\n2 for Muscle-Gain\n3 for Flexibility\n!!!!!!!!!!!!\n");
            option = input.nextInt();
            if (option == 1) {
                focus = "Weight-Loss";
            }
            if (option == 2) {
                focus = "Muscle-Gain";
            }
            if (option == 3) {
                focus = "Flexibility";
            }
            for (int i = 0; i < specialityName.size(); i++) {
                if (focus.equals(specialityName.get(i))) {
                    validFocus = true;
                }
            }
        }
        //checks if the booking is duplicate
        boolean duplicate = false;
        String SQL = "SELECT * FROM booking WHERE endTime > '" + startTime + "' and startTime < '" + endTime + "' and (staffId = '" + staffId + "' or clientId = '" + clientId + "') and date = '" + date + "'";
        ResultSet rs = conn.createStatement().executeQuery(SQL);
        if (rs.next()) {
            duplicate = true;
        } else {
            duplicate = false;
        }

        //this block of code is executed only if the booking is not duplicate
        if (!duplicate) {
            int bookingNumber = addBookingMethod(staffId, clientId, date, startTime, endTime, focus);
            System.out.println("\n\n!!!!!!!!!!!!!!!!!\nBooking Successful\nBooking ID: " + bookingNumber + "!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
        } else {
            //this block of code is executed only if the booking is duplicate
            System.out.println("\n\n!!!!!!!!!!!!!!!!!\nDuplicate Booking\nBooking for these details already exists in the DataBase\n!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
        }

    }

    public static synchronized void Update(int bookingId, String staffId, String clientId, String date, int startTime, int endTime, String focus) {

        try {
            boolean doesNotExist = false;
            //SQL query to select all columns where booking ID is equal to the given booking id.
            String SQL = "SELECT * FROM booking WHERE bookingId = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            String clientId1 = "", staffId1 = "", date1 = "", focus1 = "";
            int startTime1 = 0, endTime1 = 0;
            //if there is a booking with that ID then store the column data into the temporary variables.
            if (rs.next()) {
                staffId1 = rs.getString("staffId");
                clientId1 = rs.getString("clientId");
                date1 = rs.getString("date");
                startTime1 = rs.getInt("startTime");
                endTime1 = rs.getInt("endTime");
                focus1 = rs.getString("focus");
            } else {
                //if no record exists with that booking id than doesNotExist = true
                doesNotExist = true;
            }
            //this block of code will be executed if the booking exists
            if (!doesNotExist) {
                //checking if the details in the DB are the same as those sent by the client
                if (staffId1.equals(staffId) && clientId1.equals(clientId) && date1.equals(date)
                        && startTime1 == startTime && endTime1 == endTime && focus1.equals(focus)) {
                    System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!\nNothing to Update\nThe exact booking exists in the DB\n!!!!!!!!!!!!");
                } else {
                    //calls the Update booking method
                    updateBookingMethod(null, null, null, 0, 0, null, bookingId);

                    //checks if the booking is duplicate
                    boolean duplicate = false;
                    String SQL2 = "SELECT * FROM booking WHERE endTime > '" + startTime + "' and startTime < '" + endTime + "' and (staffId = '" + staffId + "' or clientId = '" + clientId + "') and date = '" + date + "'";
                    ResultSet rs2 = conn.createStatement().executeQuery(SQL2);
                    if (rs2.next()) {
                        duplicate = true;
                    } else {
                        duplicate = false;
                    }
                    //calls the isDuplicate method to see is it is a duplicate booking
                    if (duplicate == false) {
                        //if the booking is not duplicate then update the record in the database
                        updateBookingMethod(staffId, clientId, date, startTime, endTime, focus, bookingId);
                        System.out.println("!!!!!!!!\nBooking updated Successfully\n!!!!!!!!!!");
                    } else {
                        //if it is a duplicate booking then the client is informed about this
                        updateBookingMethod(staffId1, clientId1, date1, startTime1, endTime1, focus1, bookingId);
                        System.out.println("!!!!!!!!!!!!!!!!!\nERROR\nDUPLICATE BOOKING\n!!!!!!!!!!!!!!!!!!!!!");
                    }
                }
            } else {
                System.out.println("g");
                //this code runs when the booking id does not exist in the DB
                System.out.println("!!!!!!!!!!!!!!!!!!!!\nERROR\nBooking ID : " + bookingId + " does not exist\n!!!!!!!!!!!!!!!");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

    //method to list all the bookings from the DB
    public static synchronized void LISTALL() {
        try {
            //SQL query to select all bookings
            String SQL = "SELECT * FROM booking ";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            String listResults = "";
            //converting the results into a string and sending them to the client
            listResults = "BookingId   ClientId          ClientName      Date        StartTime    EndTime    Focus        StaffId                 StaffName\n"
                    + "---------------------------------------------------------------------------------------------------------------------------------------------";
            //getting the booking details from the booking table as well as the names of the clients and the trainers from the database
            while (rs.next()) {
                String clientId = rs.getString("clientId");
                String staffId = rs.getString("staffId");
                String SQL3 = "SELECT fName FROM client WHERE clientId = '" + clientId + "'";
                String SQL4 = "SELECT fName FROM staff WHERE staffId = '" + staffId + "'";

                ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
                String clientName = "";
                if (rs3.next()) {
                    clientName = rs3.getString("fName");
                }

                ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
                String staffName = "";
                if (rs4.next()) {
                    staffName = rs4.getString("fName");
                }
                //storing the results in a string format
                listResults = listResults + "\n     " + rs.getInt("bookingId") + "             " + rs.getString("clientId") + "             " + clientName + "       " + rs.getString("date") + "     "
                        + rs.getInt("startTime") + "            " + rs.getInt("endTime") + "         " + rs.getString("focus") + "          " + rs.getString("staffId") + "\t" + "             " + staffName;
            }
            //sending the results to the client
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------\n" + listResults);

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public static synchronized void LISTPT(String trainerId) {
        try {

            //SQL statement to select bookings for the given trainer id
            String SQL = "SELECT * FROM booking WHERE staffId = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, trainerId);
            ResultSet rs = ps.executeQuery();
            String listResults = "";
            if (rs.next()) {
                ResultSet rs2 = ps.executeQuery();
                listResults = "BookingId   ClientId          ClientName      Date        StartTime    EndTime    Focus        StaffId                 StaffName\n"
                        + "---------------------------------------------------------------------------------------------------------------------------------------------";
                //getting the booking details from the booking table as well as the names of the clients and the trainers from the database
                while (rs2.next()) {
                    String clientId = rs2.getString("clientId");
                    String staffId = rs2.getString("staffId");
                    String SQL3 = "SELECT fName FROM client WHERE clientId = '" + clientId + "'";
                    String SQL4 = "SELECT fName FROM staff WHERE staffId = '" + staffId + "'";

                    ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
                    String clientName = "";
                    if (rs3.next()) {
                        clientName = rs3.getString("fName");
                    }

                    ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
                    String staffName = "";
                    if (rs4.next()) {
                        staffName = rs4.getString("fName");
                    }
                    //storing the results as a string
                    listResults = listResults + "\n     " + rs2.getInt("bookingId") + "             " + rs2.getString("clientId") + "             " + clientName + "       " + rs2.getString("date") + "     "
                            + rs2.getInt("startTime") + "            " + rs2.getInt("endTime") + "         " + rs2.getString("focus") + "          " + rs2.getString("staffId") + "\t" + "             " + staffName;
                }

                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------\n" + listResults);
            } else {
                //if there are no records for the requested trainer id
                //inform the user about this
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!\nNo Records with this trainer ID\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public static synchronized void LISTCLIENT(String clientId) {
        try {

            //SQL statement to get bookings which have the given client ID
            String SQL = "SELECT * FROM booking WHERE clientId = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, clientId);
            ResultSet rs = ps.executeQuery();
            String listResults = "";
            if (rs.next()) {
                ResultSet rs2 = ps.executeQuery();
                listResults = "BookingId   ClientId          ClientName      Date        StartTime    EndTime    Focus        StaffId                 StaffName\n"
                        + "---------------------------------------------------------------------------------------------------------------------------------------------";
                //getting the booking details from the booking table as well as the names of the clients and the trainers from the database
                while (rs2.next()) {
                    String clientId2 = rs2.getString("clientId");
                    String staffId = rs2.getString("staffId");
                    String SQL3 = "SELECT fName FROM client WHERE clientId = '" + clientId2 + "'";
                    String SQL4 = "SELECT fName FROM staff WHERE staffId = '" + staffId + "'";

                    ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
                    String clientName = "";
                    if (rs3.next()) {
                        clientName = rs3.getString("fName");
                    }

                    ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
                    String staffName = "";
                    if (rs4.next()) {
                        staffName = rs4.getString("fName");
                    }
                    //storing the results in a string
                    listResults = listResults + "\n     " + rs2.getInt("bookingId") + "             " + rs2.getString("clientId") + "             " + clientName + "       " + rs2.getString("date") + "     "
                            + rs2.getInt("startTime") + "            " + rs2.getInt("endTime") + "         " + rs2.getString("focus") + "          " + rs2.getString("staffId") + "\t" + "             " + staffName;
                }

                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------\n" + listResults);

            } else {
                //if there are no records for the requested client id
                //inform the user about this
                System.out.println("!!!!!!!!!!!!!!!!\nNO RECORDS WITH THIS CLIENT ID\n!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public static synchronized void LISTDAY(String date) {
        try {

            //SQL statement to select bookings according to date
            String SQL = "SELECT * FROM booking WHERE date = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            //getting the booking details from the booking table as well as the names of the clients and the trainers from the database
            String listResults = "";
            if (rs.next()) {
                ResultSet rs2 = ps.executeQuery();
                listResults = "BookingId   ClientId          ClientName      Date        StartTime    EndTime    Focus        StaffId                 StaffName\n"
                        + "---------------------------------------------------------------------------------------------------------------------------------------------";
                while (rs2.next()) {
                    String clientId = rs2.getString("clientId");
                    String staffId = rs2.getString("staffId");
                    String SQL3 = "SELECT fName FROM client WHERE clientId = '" + clientId + "'";
                    String SQL4 = "SELECT fName FROM staff WHERE staffId = '" + staffId + "'";

                    ResultSet rs3 = conn.createStatement().executeQuery(SQL3);
                    String clientName = "";
                    if (rs3.next()) {
                        clientName = rs3.getString("fName");
                    }

                    ResultSet rs4 = conn.createStatement().executeQuery(SQL4);
                    String staffName = "";
                    if (rs4.next()) {
                        staffName = rs4.getString("fName");
                    }
                    //storing the results as a string
                    listResults = listResults + "\n     " + rs2.getInt("bookingId") + "             " + rs2.getString("clientId") + "             " + clientName + "       " + rs2.getString("date") + "     "
                            + rs2.getInt("startTime") + "            " + rs2.getInt("endTime") + "         " + rs2.getString("focus") + "          " + rs2.getString("staffId") + "\t" + "             " + staffName;
                }

                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------\n" + listResults);

            } else {
                //if there are no records for the requested date
                //inform the user about this
                System.out.println("!!!!!!!!!!!!!!!!\nNO RECORDS WITH THIS DATE ID\n!!!!!!!!!!!!!!!!!!!!!!!!");

            }

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
