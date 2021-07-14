package cst2550_coursework;

import java.io.Serializable;

/**
 * This is the booking class that will store details of the bookings.
 *
 * @author Ammar
 */
public class Booking implements Serializable {

    //private variables to store data
    private int bookindId;
    private String clientId;
    private String clientName;
    private String trainerName;
    private String trainerId;
    private String date;
    private int startTime;
    private int endTime;
    private String focus;

    //constructor for creating an object
    public Booking(int bookindId, String clientId, String clientName, String trainerName, String trainerId, String date, int startTime, int endTime, String focus) {
        this.bookindId = bookindId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.trainerName = trainerName;
        this.trainerId = trainerId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.focus = focus;
    }

    //default constructor with no parameters
    public Booking() {

    }

    //Getters and Setter methods for the variables
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public int getBookindId() {
        return bookindId;
    }

    public void setBookindId(int bookindId) {
        this.bookindId = bookindId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    //To String method to print the details of all the variables in this class.
    @Override
    public String toString() {
        return "Booking{" + "bookindId=" + bookindId + ", clientId=" + clientId + ", trainerName=" + trainerName + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + ", focus=" + focus + '}';
    }

}
