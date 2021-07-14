/*
 * This the client side code for the Gym. This enables the users to ADD, UPDATE, DELETE, and LIST bookings through   
 * an inituitive GUI. It also has a Login page for security verification. The client connects to the server and 
 * requests the server for any information. The server does all the computations and sends the data to the client.
 * The client side then displays the results from the GUI.
 */
package cst2550_coursework;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * This the client side code for the Gym. This enables the users to ADD, UPDATE,
 * DELETE, and LIST bookings through an intuitive GUI. It also has a Login page
 * for security verification. The client connects to the server and requests the
 * server for any information. The server does all the computations and sends
 * the data to the client. The client side then displays the results from the
 * GUI.
 *
 * @author Ammar Ali Moazzam
 */
public class CST2550_Coursework extends Application {

    //creating global array list to store trainer names      
    List<String> trainerNamesList;
    List<String> clientNamesList;
    //creating global array list to store trainer speciality
    List<String> trainerFocusList;
    //creating socket for connecting to the server
    Socket clientSocket;
    String trainerName;
    String clientNameSelected = " ";

    @Override
    public void start(Stage primaryStage) {

        try {
            //initializing the Socket at the beginning of the client program
            clientSocket = new Socket("localhost", 6789);

            //Login Screen layout
            //Creating and styling the main Heading for the Screen
            Label title1 = new Label("Middlesex Gym");
            //Creating and setting font to the title
            Font f1 = new Font("System", 45);
            title1.setFont(f1);
            //specifying the posistion of the label
            title1.setLayoutX(40);
            title1.setLayoutY(10);

            //Adding a label that says "Staff Login"
            Label title2 = new Label("Staff Login");
            //Creating new font to be used. 
            Font f2 = new Font("System", 43);
            title2.setFont(f2);
            //specifying the posistion of the label
            title2.setLayoutX(110);
            title2.setLayoutY(130);

            //Cretaing a font to use for the labels in all the screens
            Font f5 = new Font("System", 14);

            //label for username 
            Label userName = new Label("Username: ");
            //specifying the posistion of the label
            userName.setLayoutX(80);
            userName.setLayoutY(225);
            //setting custom font to the label
            userName.setFont(f5);

            //Creating textfield to get username
            TextField userNameTF = new TextField();
            userNameTF.setPromptText("Enter username here");
            //specifying the posistion of the text field
            userNameTF.setLayoutX(180);
            userNameTF.setLayoutY(225);

            //Creating label for password
            Label password = new Label("Password: ");
            //specifying the posistion of the label
            password.setLayoutX(80);
            password.setLayoutY(285);
            //setting custom font to the label
            password.setFont(f5);

            //Creating password field to getpassword from the user.
            PasswordField passwordPF = new PasswordField();
            passwordPF.setPromptText("Enter password here");
            //specifying the posistion of the password field
            passwordPF.setLayoutX(180);
            passwordPF.setLayoutY(285);

            //Creating a button for login
            Button loginBtn = new Button("Login");
            loginBtn.setFont(f5);
            //specifying the posistion of the button
            loginBtn.setLayoutX(80);
            loginBtn.setLayoutY(350);
            //setting minimum size for the button
            loginBtn.setMinSize(250, 40);

////////////////////////////////////////////////////////    
            //'Add Booking' Screen Layout
            //Creating and styling the main Heading for this Screen
            Label newBookingTitle = new Label("New Booking");
            //Creating and setting font to the title
            Font f3 = new Font("System", 45);
            newBookingTitle.setFont(f3);
            //specifying the posistion of the label
            newBookingTitle.setLayoutX(58);
            newBookingTitle.setLayoutY(17);

            //Creating label for trainer id
            Label trainer = new Label("Trainer  ");
            //specifying the posistion of the label
            trainer.setLayoutX(77);
            trainer.setLayoutY(153);
            ////setting custom font to the label
            trainer.setFont(f5);

            //Creating ChoiceBox to get trainer id
            ChoiceBox trainerCB = new ChoiceBox();
            //specifying the position of the choice box.
            trainerCB.setLayoutX(161);
            trainerCB.setLayoutY(158);
            //specifying the size of the choice box
            trainerCB.setPrefSize(150, 16);

            //Creating label for client name
            Label clientName = new Label("Client Name ");
            //specifying the posistion of the label
            clientName.setLayoutX(77);
            clientName.setLayoutY(213);
            //setting custom font to the label
            clientName.setFont(f5);

            //creating choice box for client name choice box
            ChoiceBox clientNameCB = new ChoiceBox();
            ////specifying the posistion of the choice box
            clientNameCB.setLayoutX(161);
            clientNameCB.setLayoutY(214);
            //specifying the size of the choicebox
            clientNameCB.setPrefSize(150, 16);

            //Creating label for Date
            Label date = new Label("Date ");
            //specifying the posistion of the label
            date.setLayoutX(77);
            date.setLayoutY(263);
            //setting custom font to the label
            date.setFont(f5);

            //Creating date picker to get date
            DatePicker dp = new DatePicker();
            //setting today's date as the default date.
            dp.setValue(LocalDate.now());
            //specifying the position of the date picker
            dp.setLayoutX(161);
            dp.setLayoutY(264);
            //setting the size of the date picker
            dp.setPrefSize(150, 16);

            //Creating label for startTime 
            Label startTime = new Label("Start Time");
            //specifying the posistion of the label
            startTime.setLayoutX(77);
            startTime.setLayoutY(313);
            //setting custom font to the label
            startTime.setFont(f5);

            //Creating a choicebox menuu for startTime
            ChoiceBox startTimeCB = new ChoiceBox();
            //adding options to the choice box
            startTimeCB.getItems().addAll(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
            //specifying the posistion of the choice box
            startTimeCB.setLayoutX(161);
            startTimeCB.setLayoutY(314);
            //specifying the size of the choice box
            startTimeCB.setPrefSize(50, 16);

            //creating a label for "End Time"
            Label endTime = new Label("End Time");
            //setting custom font to the label
            endTime.setFont(f5);
            //specifying the postion of the label
            endTime.setLayoutX(220);
            endTime.setLayoutY(313);

            //creating choice box to get end time
            ChoiceBox endTimeCB = new ChoiceBox();
            //specifying the position of the choice box
            endTimeCB.setLayoutX(280);
            endTimeCB.setLayoutY(314);
            //specifying the size of the choice box
            endTimeCB.setPrefSize(50, 16);

            //Creating label for focus
            Label focus = new Label("Focus ");
            //specifying the posistion of the label
            focus.setLayoutX(77);
            focus.setLayoutY(351);
            //setting custom font to the label
            focus.setFont(f5);

            //Creating a choicebox menuu for focus
            ChoiceBox focusCB = new ChoiceBox();
            //specifying the posistion of the choice box
            focusCB.setLayoutX(161);
            focusCB.setLayoutY(354);
            //specifying the size of the choice box
            focusCB.setPrefSize(150, 16);

            //Creating a button to add bookings
            Button addBookingBtn = new Button("Add Booking");
            //setting custom font to the label
            addBookingBtn.setFont(f5);
            //specifying the posistion of the button
            addBookingBtn.setLayoutX(76);
            addBookingBtn.setLayoutY(404);
            //specifyiing the size of the button
            addBookingBtn.setMinSize(237, 40);

            //creating a label to let the user know whether the booking has been successful
            Label message = new Label("");
            //specifying the position of the label
            message.setLayoutX(150);
            message.setLayoutY(450);
            //Hiding the label from the user initially
            message.setVisible(false);

            //Creating a button to go to the previous screen
            Button backFromAddBooking = new Button("Back");
            //specifying the posistion of the button
            backFromAddBooking.setLayoutX(10);
            backFromAddBooking.setLayoutY(10);

            //piece of code that doesnt allow the user to select date from the past
            dp.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) < 0);
                }
            });

            //Event handler for start Time Choice box. Defines what happens when a user selects an option from the choice box
            startTimeCB.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //reoving items from end time choice box
                    endTimeCB.getItems().clear();
                    int startTiming = Integer.parseInt(startTimeCB.getValue().toString());
                    //adding values to end time choice box.
                    //these values begin with start time + 1 and end at 23
                    for (int i = startTiming + 1; i < 24; i++) {
                        endTimeCB.getItems().add(i);
                    }
                }
            });

            ////////////////////////////////////////////////////////////
            //event handler for the trainer choice box. Defines what happens when the user selects a trainer.
            trainerCB.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //creating a data output stream
                    DataOutputStream dos = null;
                    try {
                        //sets the gobal variable trainer name to the value that the user selected by selecting an option from the choice box.
                        trainerName = trainerCB.getValue().toString();
                        //removing everything from focus speciality
                        focusCB.getItems().clear();
                        //checking if the trainer name is equal to the trainer name in the array list.
                        //then takes that trainer's speciality from the arraylist and adds it to the choice box.
                        for (int i = 0; i < trainerNamesList.size(); i++) {
                            if (trainerName.equals(trainerNamesList.get(i))) {
                                List<String> list = Arrays.asList(trainerFocusList.get(i).split(","));
                                for (int j = 0; j < list.size(); j++) {
                                    focusCB.getItems().add(list.get(j));
                                }
                            }
                        }
                    } catch (NullPointerException ex) {
                    }
                }
            });

            //event handler for the client name choice box. Defines what happens when the user selects a trainer.
            clientNameCB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        //sets the gobal variable trainer name to the value that the user selected by selecting an option from the choice box.
                        clientNameSelected = clientNameCB.getValue().toString();
                    } catch (NullPointerException ex) {
                    }
                }
            });

            ////////////////////////////////////////////////////////////////  
            //Home Screen Layout
            //creating label for home screen
            Label homeScreenTitle = new Label("Middlesex Gym");
            //setting custom font to the label
            homeScreenTitle.setFont(f1);
            //specifying the position of the label
            homeScreenTitle.setLayoutX(41);
            homeScreenTitle.setLayoutY(20);

            //creating a font for the text in buttons
            Font f4 = new Font("System", 15);

            //Creating a button to add new bookings
            Button newBookingBtn = new Button("New Booking");
            //specifying the posistion of the button
            newBookingBtn.setLayoutX(14);
            newBookingBtn.setLayoutY(152);
            //specifying the size of the button
            newBookingBtn.setMinSize(169, 104);
            //setting custom font to the button
            newBookingBtn.setFont(f4);

            //Creating a button to update bookings
            Button updateBookingBtn = new Button("Update Booking");
            //specifying the posistion of the button
            updateBookingBtn.setLayoutX(14);
            updateBookingBtn.setLayoutY(302);
            //specifying the size of the button
            updateBookingBtn.setMaxSize(169, 110);
            updateBookingBtn.setPrefSize(169, 110);
            //setting custom font to the button
            updateBookingBtn.setFont(f4);
            updateBookingBtn.setWrapText(true);
            //center alligning the text inside the button
            updateBookingBtn.setTextAlignment(TextAlignment.CENTER);

            //Creating a button to delete bookings
            Button deleteBookingBtn = new Button("Delete Booking");
            //specifying the posistion of the button
            deleteBookingBtn.setLayoutX(205);
            deleteBookingBtn.setLayoutY(302);
            //specifying the size of the button
            deleteBookingBtn.setMaxSize(169, 110);
            deleteBookingBtn.setPrefSize(169, 110);
            //setting custom font to the button
            deleteBookingBtn.setFont(f4);
            deleteBookingBtn.setWrapText(true);
            //center alligning the text inside the button
            deleteBookingBtn.setTextAlignment(TextAlignment.CENTER);

            //Creating a button to add new bookings
            Button listBookingBtn = new Button("List Booking");
            //specifying the posistion of the button
            listBookingBtn.setLayoutX(205);
            listBookingBtn.setLayoutY(152);
            //specifying the size of the button
            listBookingBtn.setMinSize(169, 104);
            //setting custom font to the button
            listBookingBtn.setFont(f4);

            //Creating a button to disconnect from the server
            Button disconnectBtn = new Button("Disconnect");
            //specifying the posistion of the button
            disconnectBtn.setLayoutX(150);
            disconnectBtn.setLayoutY(430);
            //setting custom font to the button
            disconnectBtn.setFont(f5);
            disconnectBtn.setStyle("-fx-background-color: red");

            //event handler for disconnect button. Defines what happens when the disconnect button is pressed.
            disconnectBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //creating a data output stream to send data to the server
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                        // sending "stop" to the server
                        dos.writeUTF("stop");
                        //closing the application
                        System.exit(0);
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

            ////////////////////////////////////////////////////////    
            //'Update Booking' Screen Layout
            //Creating and styling the main Heading for this Screen
            Label updateBookingTitle = new Label("Update Booking");
            //setting font to the title
            updateBookingTitle.setFont(f3);
            //specifying the posistion of the label
            updateBookingTitle.setLayoutX(35);
            updateBookingTitle.setLayoutY(17);

            //Creating label for booking id
            Label updateBookingID = new Label("Booking ID ");
            //specifying the posistion of the label
            updateBookingID.setLayoutX(77);
            updateBookingID.setLayoutY(121);
            //setting custom font to the label
            updateBookingID.setFont(f5);

            //Creating textfield to get booking id
            TextField updateBookingIDTF = new TextField();
            updateBookingIDTF.setPromptText("Enter BookingID here");
            //specifying the posistion of the text field
            updateBookingIDTF.setLayoutX(161);
            updateBookingIDTF.setLayoutY(122);

            //Creating label for trainer id
            Label updateTrainerID = new Label("Trainer ");
            //specifying the posistion of the label
            updateTrainerID.setLayoutX(77);
            updateTrainerID.setLayoutY(173);
            //setting custom font to the label
            updateTrainerID.setFont(f5);

            //Creating choice box to get trainer id
            ChoiceBox updateTrainerCB = new ChoiceBox();
            //specifying the posistion of the choice box
            updateTrainerCB.setLayoutX(161);
            updateTrainerCB.setLayoutY(178);
            //specifying the size of the choice box
            updateTrainerCB.setPrefSize(150, 16);

            //Creating label for client name
            Label updateClientName = new Label("Client Name ");
            //specifying the posistion of the label
            updateClientName.setLayoutX(77);
            updateClientName.setLayoutY(225);
            //setting custom font to the label
            updateClientName.setFont(f5);

            //Creating drop-down to get client name
            ChoiceBox updateClientNameCB = new ChoiceBox();
            //specifying the posistion of the choice box
            updateClientNameCB.setLayoutX(161);
            updateClientNameCB.setLayoutY(225);
            //specifying the size of the choice box
            updateClientNameCB.setPrefSize(150, 16);

            //Creating label for Date
            Label updateDate = new Label("Date ");
            //specifying the posistion of the label
            updateDate.setLayoutX(77);
            updateDate.setLayoutY(273);
            //setting custom font to the label
            updateDate.setFont(f5);

            //Creating date picker to get date
            DatePicker updateDp = new DatePicker();
            updateDp.setValue(LocalDate.now());
            //specifying the posistion of the date picker
            updateDp.setLayoutX(161);
            updateDp.setLayoutY(274);
            //specifying the size of the date picker
            updateDp.setPrefSize(150, 16);

            //Creating label for startTime 
            Label updateStartTime = new Label("Start Time");
            //specifying the posistion of the label
            updateStartTime.setLayoutX(77);
            updateStartTime.setLayoutY(313);
            //setting custom font to the label
            updateStartTime.setFont(f5);

            //Creating a choicebox menu for startTime
            ChoiceBox updateStartTimeCB = new ChoiceBox();
            //adding options to the choice box
            updateStartTimeCB.getItems().addAll(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
            //specifying the posistion of the choice box
            updateStartTimeCB.setLayoutX(161);
            updateStartTimeCB.setLayoutY(314);
            //specifying the size of the choice box
            updateStartTimeCB.setPrefSize(50, 16);

            //creating a label for updating End time
            Label updateEndTime = new Label("End Time");
            //setting custom font to the label
            updateEndTime.setFont(f5);
            //specifying the posistion of the label
            updateEndTime.setLayoutX(220);
            updateEndTime.setLayoutY(313);

            //Creating a choicebox menu for end time
            ChoiceBox updateEndTimeCB = new ChoiceBox();
            //specifying the posistion of the choice box
            updateEndTimeCB.setLayoutX(280);
            updateEndTimeCB.setLayoutY(314);
            //specifying the size of the choice box
            updateEndTimeCB.setPrefSize(50, 16);

            //Creating label for focus
            Label updateFocus = new Label("Focus ");
            //specifying the posistion of the label
            updateFocus.setLayoutX(77);
            updateFocus.setLayoutY(351);
            //setting custom font to the label
            updateFocus.setFont(f5);

            //Creating a choicebox menuu for focus
            ChoiceBox updateFocusCB = new ChoiceBox();
            //specifying the posistion of the choice box
            updateFocusCB.setLayoutX(161);
            updateFocusCB.setLayoutY(354);
            //specifying the size of the choice box
            updateFocusCB.setPrefSize(150, 16);

            //Creating a button to update bookings
            Button updateBtn = new Button("Update Booking");
            //setting custom font to the button
            updateBtn.setFont(f5);
            //specifying the posistion of the button
            updateBtn.setLayoutX(76);
            updateBtn.setLayoutY(404);
            //specifying the size of the button
            updateBtn.setMinSize(237, 40);

            //Creating a button to go to the previous screen
            Button backFromUpdateBooking = new Button("Back");
            //specifying the posistion of the button
            backFromUpdateBooking.setLayoutX(10);
            backFromUpdateBooking.setLayoutY(10);

            //ceating a label for informinig the user about the result of the update
            Label updateMessage = new Label("");
            //specifying the position of the label
            updateMessage.setLayoutX(150);
            updateMessage.setLayoutY(450);
            //initially hiding the label from the user 
            updateMessage.setVisible(false);

            //piece of code that doesnt allow the user to select date from the past
            updateDp.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) < 0);
                }
            });

            //event handler for update client name choice box
            updateClientNameCB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        //sets the gobal variable trainer name to the value that the user selected by selecting an option from the choice box.
                        clientNameSelected = updateClientNameCB.getValue().toString();
                    } catch (NullPointerException ex) {
                    }
                }
            });

            //event handler defining whta should be done when the user selects an option from the start time
            updateStartTimeCB.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //removing all items from end time choice box.
                    updateEndTimeCB.getItems().clear();
                    //getting start time value from start time choice box
                    int updateStartTiming = Integer.parseInt(updateStartTimeCB.getValue().toString());
                    //adding values to end time choice box.
                    //these values begin with start time + 1 and end at 23
                    for (int i = updateStartTiming + 1; i < 24; i++) {
                        updateEndTimeCB.getItems().add(i);
                    }
                }
            });

            ////////////////////////////////////////////////////////////
            //event handler defining what needs to done when a trainer name is selected from the choice box.
            updateTrainerCB.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    DataOutputStream dos = null;
                    try {
                        //sets the gobal variable trainer name to the value that the user selected by selecting an option from the choice box.
                        trainerName = updateTrainerCB.getValue().toString();
                        //removing everything from the update focus choice box.
                        updateFocusCB.getItems().clear();
                        //checking if the trainer name is equal to the trainer name in the array list.
                        //then takes that trainer's specialities from the arraylist and adds it to the choice box.
                        for (int i = 0; i < trainerNamesList.size(); i++) {
                            if (trainerName.equals(trainerNamesList.get(i))) {
                                List<String> list = Arrays.asList(trainerFocusList.get(i).split(","));
                                for (int j = 0; j < list.size(); j++) {
                                    updateFocusCB.getItems().add(list.get(j));
                                }
                            }
                        }
                    } catch (NullPointerException ex) {

                    }
                }
            });

            //////////////////////////////////////////////////////////////
            //'List Booking' Screen layout
            //creating a heading for this screen
            Label listBookingTitle = new Label("List Booking");
            //setting font to the title
            listBookingTitle.setFont(f3);
            //specifying the posistion of the label
            listBookingTitle.setLayoutX(61);
            listBookingTitle.setLayoutY(21);

            //Creating label for the trainer id to search bookings for
            Label listTrainerID = new Label("Staff/Trainer ID ");
            //specifying the posistion of the label
            listTrainerID.setLayoutX(87);
            listTrainerID.setLayoutY(198);
            //setting custom font to the label
            listTrainerID.setFont(f5);
            //hiding the label from the users initially
            listTrainerID.setVisible(false);

            //Creating textfield to get trainer id
            TextField listTrainerIDTF = new TextField();
            listTrainerIDTF.setPromptText("Enter Staff/TrainerID here");
            //specifying the posistion of the text field
            listTrainerIDTF.setLayoutX(185);
            listTrainerIDTF.setLayoutY(194);
            //hiding the textField from the users initially
            listTrainerIDTF.setVisible(false);

            //Creating label for the client id to search bookings for
            Label listClientID = new Label("Client ID ");
            //specifying the posistion of the label
            listClientID.setLayoutX(87);
            listClientID.setLayoutY(198);
            //setting custom font to the label
            listClientID.setFont(f5);

            //Creating textfield to get client id
            TextField listClientIDTF = new TextField();
            listClientIDTF.setPromptText("Enter ClientID here");
            //specifying the posistion of the text field
            listClientIDTF.setLayoutX(165);
            listClientIDTF.setLayoutY(194);
            //hiding the textField from the users initially
            listClientIDTF.setVisible(true);

            //Creating label for the date to search bookings for
            Label listDate = new Label("Date ");
            //specifying the posistion of the label
            listDate.setLayoutX(87);
            listDate.setLayoutY(198);
            //setting custom font to the label
            listDate.setFont(f5);
            //hiding the label from the users initially
            listDate.setVisible(false);

            //Creating date picker to get date to show bookings for
            DatePicker listDp = new DatePicker();
            listDp.setValue(LocalDate.now());
            //specifying the posistion of the date picker
            listDp.setLayoutX(165);
            listDp.setLayoutY(194);
            //specifying the size of the date picker
            listDp.setPrefSize(149, 25);
            //hiding the date picker from the users initially
            listDp.setVisible(false);

            //Creating a button to list bookings
            Button listBtn = new Button("List Bookings");
            //setting custom font to the button text
            listBtn.setFont(f5);
            //specifying the posistion of the button
            listBtn.setLayoutX(82);
            listBtn.setLayoutY(282);
            //specifying the size of the button
            listBtn.setMinSize(237, 40);

            //creating a teext area for displaying the results
            TextArea listBookingsTA = new TextArea();
            //specifying the posistion and size of the text area
            listBookingsTA.setLayoutX(13);
            listBookingsTA.setLayoutY(333);
            //specifying the posistion of the text area
            listBookingsTA.setMaxSize(374, 153);
            listBookingsTA.setEditable(false);  // the text area cant be edited by the user
            listBookingsTA.setVisible(false);  //initially the textarea is not visible to the user

            //creating a toggle group for the radio buttons
            ToggleGroup toggleGroup = new ToggleGroup();

            //creating radio button for listing results by client id
            RadioButton byClientId = new RadioButton("By Client ID");
            //specifying the position of the radio button.
            byClientId.setLayoutX(12);
            byClientId.setLayoutY(133);
            //this radio button is selected by default
            byClientId.setSelected(true);

            //creating radio button for listing results by trainer id
            RadioButton byTrainerId = new RadioButton("By Trainer/Staff ID");
            //specifying the position of the radio button.
            byTrainerId.setLayoutX(114);
            byTrainerId.setLayoutY(133);

            //creating radio button for listing results by date
            RadioButton byDate = new RadioButton("By Date");
            //specifying the position of the radio button.
            byDate.setLayoutX(233);
            byDate.setLayoutY(133);

            //creating radio button for listing all bookings
            RadioButton listAll = new RadioButton("List All");
            //specifying the position of the radio button.
            listAll.setLayoutX(326);
            listAll.setLayoutY(133);

            //adding all the radio buttons to the toggle group
            byClientId.setToggleGroup(toggleGroup);
            byTrainerId.setToggleGroup(toggleGroup);
            byDate.setToggleGroup(toggleGroup);
            listAll.setToggleGroup(toggleGroup);

            //Creating a button to go to the previous screen
            Button backFromListBooking = new Button("Back");
            //specifying the posistion of the button
            backFromListBooking.setLayoutX(10);
            backFromListBooking.setLayoutY(10);

//////////////////////////////////////////////////
            //'Delete Boking' Screen
            //creating a heading for this screen
            Label deleteBookingTitle = new Label("Delete Booking");
            //setting font to the title
            deleteBookingTitle.setFont(f3);
            //specifying the posistion of the label
            deleteBookingTitle.setLayoutX(35);
            deleteBookingTitle.setLayoutY(21);

            //Creating label for the client id to searh bookings for
            Label deleteBookingID = new Label("Booking ID ");
            //setting a custom font for the label
            deleteBookingID.setFont(f5);
            //specifying the posistion of the label
            deleteBookingID.setLayoutX(87);
            deleteBookingID.setLayoutY(185);

            //Creating textfield to get client id
            TextField deleteBookingIDTF = new TextField();
            deleteBookingIDTF.setPromptText("Enter Booking ID to delete");
            //specifying the position of the text field
            deleteBookingIDTF.setLayoutX(165);
            deleteBookingIDTF.setLayoutY(186);

            //Creating a button to delete bookings
            Button deleteBooking = new Button("Delete Booking");
            //setting custom font to the button
            deleteBooking.setFont(f5);
            //specifying the posistion of the button
            deleteBooking.setLayoutX(82);
            deleteBooking.setLayoutY(250);
            //specifying the size of the button.
            deleteBooking.setMinSize(237, 40);

            //Creating a button to go to the previous screen
            Button backFromDeleteBooking = new Button("Back");
            //specifying the posistion of the button
            backFromDeleteBooking.setLayoutX(10);
            backFromDeleteBooking.setLayoutY(10);

            //creating label to inform the user about th results of the delete operation
            Label deleteMessage = new Label();
            //specifying the position of the label
            deleteMessage.setLayoutX(150);
            deleteMessage.setLayoutY(450);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
            //Scene 1 --Login Screen
            Pane loginScreen = new Pane();
            //adding nodes onto the pane
            loginScreen.getChildren().addAll(title1, title2, userName, userNameTF, password, passwordPF, loginBtn);
            Scene loginScene = new Scene(loginScreen, 400, 500);
            loginScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
            loginScreen.setStyle("-fx-background: BEIGE;");

            //Scene 2 --'Add Bookng' Screen
            Pane addBookingScreen = new Pane();    //creating a new pane for scene 2
            //adding nodes onto the pane
            addBookingScreen.getChildren().addAll(newBookingTitle, trainer, clientName, date, startTime, startTimeCB, focus, trainerCB,
                    clientNameCB, dp, focusCB, addBookingBtn, backFromAddBooking, message, endTimeCB, endTime);
            //setting background color for the pane
            addBookingScreen.setStyle("-fx-background: BEIGE;");
            Scene addBookingScene = new Scene(addBookingScreen, 400, 500);  //setting up the scene
            //adding css styles sheets to the pane
            addBookingScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

            //Scene 3 --'Update Bookng' Screen
            Pane updateBookingScreen = new Pane();    //creating a new pane for scene 3
            //adding nodes onto the pane
            updateBookingScreen.getChildren().addAll(updateBookingID, updateBookingIDTF, updateBookingTitle, updateBtn, updateClientNameCB,
                    updateClientName, updateDate, updateDp, updateStartTime, updateStartTimeCB,
                    updateFocus, updateTrainerID, updateTrainerCB, updateFocusCB, backFromUpdateBooking, updateMessage, updateEndTimeCB, updateEndTime);
            updateBookingScreen.setStyle("-fx-background: BEIGE;");
            Scene updateBookingScene = new Scene(updateBookingScreen, 400, 500);  //setting up the scene
            updateBookingScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

            //Scene 4 --'Home' Screen
            Pane homeScreen = new Pane();    //creating a new pane for scene 4
            //adding nodes onto the pane
            homeScreen.getChildren().addAll(homeScreenTitle, newBookingBtn, updateBookingBtn, deleteBookingBtn, listBookingBtn, disconnectBtn);
            homeScreen.setStyle("-fx-background: BEIGE;");
            Scene homeScene = new Scene(homeScreen, 400, 500);  //setting up the scene
            homeScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

            //Scene 5 --'list Bookings' Screen
            Pane listBookingScreen = new Pane();    //creating a new pane for scene 5
            //adding nodes onto the pane
            listBookingScreen.getChildren().addAll(listBookingTitle, listClientID, listClientIDTF, listDate, listDp, listTrainerID, listTrainerIDTF,
                    listBtn, listBookingsTA, backFromListBooking, byClientId, byDate, byTrainerId, listAll);
            listBookingScreen.setStyle("-fx-background: BEIGE;");
            Scene listBookingScene = new Scene(listBookingScreen, 400, 500);  //setting up the scene
            listBookingScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

            //Scene 6 --'Delete Bookings' Screen
            Pane deleteBookingScreen = new Pane();    //creating a new pane for scene 6
            //adding nodes onto the pane
            deleteBookingScreen.getChildren().addAll(deleteBookingTitle, deleteBookingIDTF, deleteBookingID, deleteBooking, backFromDeleteBooking, deleteMessage);
            deleteBookingScreen.setStyle("-fx-background: BEIGE;");
            Scene deleteBookingScene = new Scene(deleteBookingScreen, 400, 500);  //setting up the scene
            deleteBookingScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

            primaryStage.setTitle("Middlesex GYM"); //title for the stage
            primaryStage.setScene(loginScene);
            primaryStage.show();

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Event handler for the login buttons
            loginBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //checkinng if the user has filled in all the text fields.
                        if (userNameTF.getText().isEmpty() || passwordPF.getText().isEmpty()) {
                            throw new NullPointerException();
                        } else {
                            //creating data output stream to send data to the server
                            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                            //sending username and password to the server
                            dos.writeUTF(userNameTF.getText().toUpperCase() + "," + passwordPF.getText());

                            //creating data input stream to read data from the server
                            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                            String reply = dis.readUTF();
                            //if the server repies with a 'true' it means the login is successful and the scene is changed
                            if (reply.equalsIgnoreCase("true")) {
                                primaryStage.setScene(homeScene);

                                //reading trainer names from the server and assigning it to a variable
                                String trainerNames = dis.readUTF();

                                //reading trainer focus from server and assigning it to a variable
                                String trainerFocus = dis.readUTF();

                                //splitting the trainer names string using ',' and storing it into an arraylist
                                trainerNamesList = Arrays.asList(trainerNames.split(","));
                                //splitting the trainer focus string using ':' and storing it into an arraylist
                                trainerFocusList = Arrays.asList(trainerFocus.split(":"));

                                //getting client names from the user and storing them in a list
                                String clientNames = dis.readUTF();
                                clientNamesList = Arrays.asList(clientNames.split(","));

                            } else {
                                //if the credentials are invalid then a pop up alert is shown to the user.
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Invalid username or Password");
                                alert.show();
                            }
                        }
                    } catch (NullPointerException ex) {
                        //if any text field is left empty by the user, an alert box is shown alerting the user
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please fill in all the details! ");
                        alert.show();
                    } catch (IOException ex) {
                        System.out.println("Error : " + ex.getMessage());
                    }
                }
            });

            //event handler for add booking button
            addBookingBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {

                        boolean isChoiceBoxSelected = false;
                        boolean isCorrectTimeSelected = false;

                        //checks if an option is selected from all the choice boxes
                        if (trainerCB.getSelectionModel().isEmpty() || focusCB.getSelectionModel().isEmpty() || startTimeCB.getSelectionModel().isEmpty() || endTimeCB.getSelectionModel().isEmpty() || clientNameCB.getSelectionModel().isEmpty()) {
                            throw new Exception();
                        } else {
                            isChoiceBoxSelected = true;
                        }
                        //making sure that the time selected by the user should not be less than the current time
                        if(Integer.parseInt(startTimeCB.getValue().toString()) <= getCurrentTime() && dp.getValue().toString().equals(LocalDate.now().toString())){
                            throw new ArithmeticException();
                        } else {
                            isCorrectTimeSelected = true;
                        }

                        //if everything is rightly filled in, then it creates a Booking object and sets all the user inputs to the object
                        if (isChoiceBoxSelected && isCorrectTimeSelected) {
                            Booking b1 = new Booking();
                            b1.setDate(dp.getValue().toString());
                            b1.setStartTime(Integer.parseInt(startTimeCB.getValue().toString()));
                            b1.setEndTime(Integer.parseInt((endTimeCB.getValue().toString())));
                            b1.setFocus(focusCB.getValue().toString());
                            b1.setTrainerName(trainerName);
                            b1.setClientName(clientNameSelected);

                            //sending the object to the server
                            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                            oos.writeObject(b1);
                            //reading Booking object from the server
                            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                            Booking b = (Booking) ois.readObject();

                            //checking the servers reply to see if the booking is valid or is a duplicate
                            if ((b.getFocus().equals("Duplicate Booking"))) {
                                //if the booking is a duplicate, then an alert is shown and the message label is set to "duplicate booking"
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Duplicate Booking\nBooking for these details already exists");
                                alert.setContentText("Please check the timings\nThe trainer is busy during those hours\nTry changing the time or the trainer");
                                alert.show();
                                message.setText("Duplicate Booking\nBooking for these details already exists");
                                message.setVisible(true);
                            } else {
                                //sets the message label on the user GUI to read SUCCESSFUL BOOKING
                                message.setText("Booking Successful\nBooking ID: " + b.getBookindId());
                                message.setVisible(true);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText("Booking Successful\nBooking ID: " + b.getBookindId());
                                alert.show();
                            }

                        }
                        // Below are the alert boxes that will be shown if any of the above validation checks are false. 
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please fill in the deatils in the correct format! ");
                        alert.show();
                    } catch (NullPointerException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please fill in all the details! ");
                        alert.show();
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    } catch (ArithmeticException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Cannot make a booking in the past!!!");
                        alert.setContentText("Please change the start time\nMake sure start time is not less than or equal to the current time.");
                        alert.show();
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please select a value from all the drop down boxes ");
                        alert.show();
                    }
                }
            });

            //event handler for update button
            updateBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {

                        boolean isChoiceBoxSelected = false;
                        boolean isCorrectTimeSelected = false;

                        //checks if an option is selected from all the choice boxes
                        if (updateBookingIDTF.getText().isEmpty() || updateTrainerCB.getSelectionModel().isEmpty() || updateFocusCB.getSelectionModel().isEmpty() || updateStartTimeCB.getSelectionModel().isEmpty() || updateEndTimeCB.getSelectionModel().isEmpty() || updateClientNameCB.getSelectionModel().isEmpty()) {
                            throw new Exception();
                        } else {
                            isChoiceBoxSelected = true;
                        }
                        //making sure that the time selected by the user should not be less than the current time
                        if(Integer.parseInt(updateStartTimeCB.getValue().toString()) <= getCurrentTime()&& updateDp.getValue().toString().equals(LocalDate.now().toString())){
                            throw new ArithmeticException();
                        } else {
                            isCorrectTimeSelected = true;
                        }

                        //if everything is rightly filled in, then it creates a Booking object and sets all the user inputs to the object
                        if (isChoiceBoxSelected && isCorrectTimeSelected) {
                            Booking b1 = new Booking();
                            b1.setBookindId(Integer.parseInt(updateBookingIDTF.getText().toUpperCase()));
                            b1.setDate(updateDp.getValue().toString());
                            b1.setStartTime(Integer.parseInt(updateStartTimeCB.getValue().toString()));
                            b1.setEndTime(Integer.parseInt((updateEndTimeCB.getValue().toString())));
                            b1.setFocus(updateFocusCB.getValue().toString());
                            b1.setTrainerName(trainerName);
                            b1.setClientName(clientNameSelected);

                            //sending the object to the server
                            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                            oos.writeObject(b1);
                            //reading the object from the server
                            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                            Booking b = (Booking) ois.readObject();

                            //checking the server's reply to see if the booking is valid or is a duplicate or is invalid
                            if ((b.getFocus().equals("Duplicate Booking"))) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Update Failed\nBooking for these details already exists");
                                alert.show();
                                updateMessage.setText("Duplicate Booking\nBooking for these details already exists");
                                updateMessage.setVisible(true);
                            } else if (b.getFocus().equals("Invalid BookingID")) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Invalid Booking ID\nNo record with this ID exists");
                                alert.show();
                                updateMessage.setText("Invalid Booking ID\nNo record with this ID exists");
                                updateMessage.setVisible(true);
                            }else {
                                //if the reply is not equal to invalid booking or duplicate booking then it means that the booking  update is valid 
                                updateMessage.setText("Update Successful\nBooking ID: " + b.getBookindId());
                                updateMessage.setVisible(true);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText("Update Successful\nBooking ID: " + b.getBookindId());
                                alert.show();
                            }
                        }
                        // Below are the alert boxes that will be shown if any of the above validation checks are false. 
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please fill in the deatils in the correct format! ");
                        alert.show();
                    } catch (NullPointerException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please fill in all the details! ");
                        alert.show();
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    } catch (ArithmeticException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Cannot make a booking in the past!!!");
                        alert.setContentText("Please change the start time\nMake sure start time is not less than or equal to the current time.");
                        alert.show();
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please select a value from all the drop down boxes ");
                        alert.show();
                    }
                }
            });

            //event handler for the new bookings button on the home screen
            newBookingBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //changing the scene to add booking scene
                    primaryStage.setScene(addBookingScene);

                    //creating data output stream to send data to the server
                    DataOutputStream dos = null;
                    try {
                        //sends "ADD" to the server
                        dos = new DataOutputStream(clientSocket.getOutputStream());
                        dos.writeUTF("ADD");

                        trainerCB.getItems().clear();
                        //add items/options to the trainer choice box by getting the values from the list
                        for (int i = 0; i < trainerNamesList.size(); i++) {
                            trainerCB.getItems().add(trainerNamesList.get(i));
                        }

                        clientNameCB.getItems().clear();
                        //add items/options to the client name choice box by getting the values from the list
                        for (int i = 0; i < clientNamesList.size(); i++) {
                            clientNameCB.getItems().add(clientNamesList.get(i));
                        }

                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

            //event handler for the update bookings button on the home screen
            updateBookingBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //changing the scene to update booking scene
                    primaryStage.setScene(updateBookingScene);
                    //creating data output stream to send data to the server
                    DataOutputStream dos = null;
                    try {
                        //sends "UPDATE" to the server
                        dos = new DataOutputStream(clientSocket.getOutputStream());
                        dos.writeUTF("UPDATE");

                        updateTrainerCB.getItems().clear();
                        //add items/options to the trainer choice box by getting the values from the list
                        for (int i = 0; i < trainerNamesList.size(); i++) {
                            updateTrainerCB.getItems().add(trainerNamesList.get(i));
                        }

                        updateClientNameCB.getItems().clear();
                        //add items/options to the client name choice box by getting the values from the list
                        for (int i = 0; i < clientNamesList.size(); i++) {
                            updateClientNameCB.getItems().add(clientNamesList.get(i));
                        }

                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

            //event handler for the list bookings button on the home screen
            listBookingBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //changing the scene to list booking scene
                        primaryStage.setScene(listBookingScene);
                        //creating data output stream to send data to the server
                        DataOutputStream dos = null;
                        //sends "LIST" to the server
                        dos = new DataOutputStream(clientSocket.getOutputStream());
                        dos.writeUTF("LIST");
                        dos.flush();

                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

            //event handler for byClientId radio button
            byClientId.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //setting all the nodes except the client id text field to invisible
                    listDp.setVisible(false);
                    listTrainerIDTF.setVisible(false);
                    listClientIDTF.setVisible(true);
                    listBookingsTA.setVisible(false);
                    listDate.setVisible(false);
                    listClientID.setVisible(true);
                    listTrainerID.setVisible(false);
                }
            });

            //event handler for byDate radio button
            byDate.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //setting all the nodes except the date picker to invisible
                    listDp.setVisible(true);
                    listTrainerIDTF.setVisible(false);
                    listClientIDTF.setVisible(false);
                    listBookingsTA.setVisible(false);
                    listDate.setVisible(true);
                    listClientID.setVisible(false);
                    listTrainerID.setVisible(false);
                }
            });

            //event handler for byTrainerID radio button
            byTrainerId.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //setting all the nodes except the trainer Id text field to invisible
                    listDp.setVisible(false);
                    listTrainerIDTF.setVisible(true);
                    listClientIDTF.setVisible(false);
                    listBookingsTA.setVisible(false);
                    listDate.setVisible(false);
                    listClientID.setVisible(false);
                    listTrainerID.setVisible(true);
                }
            });

            //event handler for listAll radio button
            listAll.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //setting all the nodes to be invisible
                    listDp.setVisible(false);
                    listTrainerIDTF.setVisible(false);
                    listClientIDTF.setVisible(false);
                    listBookingsTA.setVisible(false);
                    listDate.setVisible(false);
                    listClientID.setVisible(false);
                    listTrainerID.setVisible(false);
                }
            });

            //event handler for the listBtn on list screen
            listBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        // Switch cases to check whoch radio button is selected and then send and recieve data from the server accordingly
                        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

                        //checks if any of the radio button is selected
                        if (!byClientId.isSelected() && !byDate.isSelected() && !byTrainerId.isSelected() && !listAll.isSelected()) {
                            throw new Exception();
                        }

                        //switch to control the radio button selection
                        switch (selectedRadioButton.getText()) {
                            case "By Client ID":
                                if (listClientIDTF.getText().isEmpty()) {
                                    throw new NullPointerException();
                                }
                                dos.writeUTF("By Client ID");
                                dos.flush();
                                //sending client id to the server
                                String clientID1 = listClientIDTF.getText().toUpperCase();
                                dos.writeUTF(clientID1);
                                dos.flush();
                                dis = new DataInputStream(clientSocket.getInputStream());
                                String listResults = dis.readUTF();
                                //displaying the results on the text area
                                listBookingsTA.setText(listResults);
                                listBookingsTA.setVisible(true);
                                break;
                            case "By Date":
                                //check to make sure a date is selected
                                if (listDp.getValue().toString().equals("")) {
                                    throw new NullPointerException();
                                }
                                //writing to the server
                                dos.writeUTF("By Date");
                                dos.flush();

                                //sending date to the server
                                String date1 = listDp.getValue().toString();
                                dos.writeUTF(date1);
                                dos.flush();

                                //reading results from the  server and displaying them on the text area
                                listResults = dis.readUTF();
                                listBookingsTA.setText(listResults);
                                listBookingsTA.setVisible(true);
                                break;
                            case "By Trainer/Staff ID":
                                //check to make sure that trainer/Staff id text field is not empty
                                if (listTrainerIDTF.getText().isEmpty()) {
                                    throw new NullPointerException();
                                }
                                //write to the server
                                dos.writeUTF("By Trainer/Staff ID");
                                dos.flush();

                                //sending trainer id/Staff to the server.
                                String trainerId = listTrainerIDTF.getText().toUpperCase();
                                dos.writeUTF(trainerId);
                                dos.flush();

                                //reading results from the server and displaying the results on the text area
                                listResults = dis.readUTF();
                                listBookingsTA.setText(listResults);
                                listBookingsTA.setVisible(true);
                                break;
                            case "List All":
                                dos.writeUTF("List All");
                                dos.flush();

                                //reading results from the server and displaying the results on the text area
                                listResults = dis.readUTF();
                                listBookingsTA.setText(listResults);
                                listBookingsTA.setVisible(true);
                                break;
                        }
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    } catch (NullPointerException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please fill in all the fields! ");
                        alert.show();
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please select a radio button! ");
                        alert.show();
                    }
                }
            });

            //event handler for the delete bookings button on the home screen
            deleteBookingBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //changing the scene to delete booking scene 
                        primaryStage.setScene(deleteBookingScene);

                        //creating data output stream to send data to the server
                        DataOutputStream dos = null;
                        dos = new DataOutputStream(clientSocket.getOutputStream());
                        dos.writeUTF("DELETE");
                        dos.flush();
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

            //event handler for delete button on the delete booking scene
            deleteBooking.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //check to make sure that the text field is not empty
                        if (deleteBookingIDTF.getText().isEmpty()) {
                            throw new Exception();
                        }

                        //getting nooking id value from the text field coverting it into an integer 
                        // and then setting it in a booking object and finally sending the Booking object to the server
                        int bookingId = Integer.parseInt(deleteBookingIDTF.getText());
                        if (bookingId != 0) {
                            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                            Booking b = new Booking();
                            b.setBookindId(bookingId);
                            oos.writeObject(b);

                            //reading data from the server and checking the status of the operation
                            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                            String deleteReply = dis.readUTF();
                            //if the reply = "Deleted" then we display the message accordingly to the user
                            if (deleteReply.equals("Deleted")) {
                                deleteMessage.setText("Booking number: " + bookingId + " deleted successfully!!");
                                deleteMessage.setVisible(true);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText("Booking number: " + bookingId + " deleted successfully!!");
                                alert.show();
                            } else {
                                //else  we display the message to the user that the booking doesnt exist
                                deleteMessage.setText("Booking ID " + bookingId + " does not exist!!");
                                deleteMessage.setVisible(true);
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Booking ID " + bookingId + " does not exist!!");
                                alert.show();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("BookingID cannot be 0 ");
                            alert.show();
                        }

                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please fill in the BookingId in the correct format! ");
                        alert.show();
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Please fill in the BookingId field! ");
                        alert.show();
                    }
                }
            });

            ///////////////////////////////////////////////////
            //event handlers for back buttons    
            backFromAddBooking.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //sending data to the server
                        Booking b = new Booking();
                        b.setTrainerName("back");
                        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                        oos.writeObject(b);

                        //changing the scene
                        primaryStage.setScene(homeScene);
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

            //event handler for back button
            backFromDeleteBooking.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    ObjectOutputStream oos = null;
                    try {
                        //sending data to the server
                        oos = new ObjectOutputStream(clientSocket.getOutputStream());
                        Booking b = new Booking();
                        b.setBookindId(0000);
                        oos.writeObject(b);
                        oos.flush();

                        //changing the scene
                        primaryStage.setScene(homeScene);
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

            //event handler for back button
            backFromUpdateBooking.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //sending data to the server
                        Booking b = new Booking();
                        b.setTrainerName("back");
                        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                        oos.writeObject(b);

                        //changing the scene
                        primaryStage.setScene(homeScene);
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

            //event handler for back button
            backFromListBooking.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        //sending data to the server
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                        dos.writeUTF("back");
                        dos.flush();

                        //changing the scene
                        primaryStage.setScene(homeScene);
                    } catch (IOException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                }
            });

        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }
    
    public static int getCurrentTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String formattedTime = time.format(formatter);
        return Integer.parseInt(formattedTime);
    }
    

///////////////////////////////////////////////////////////////////////////////////
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
