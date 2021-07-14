
package cst2550_coursework;

/**
 *
 * @author Ammar
 */
public class Client {
    private String clientId;
    private String fName;
    private String lName;
    private char gender;
    private String DOB;
    private int phoneNum;

    public Client(String clientId, String fName, String lName, char gender, String DOB, int phoneNum) {
        this.clientId = clientId;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.DOB = DOB;
        this.phoneNum = phoneNum;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Client{" + "clientId=" + clientId + ", fName=" + fName + ", lName=" + lName + ", gender=" + gender + ", DOB=" + DOB + ", phoneNum=" + phoneNum + '}';
    }
    
    
}
