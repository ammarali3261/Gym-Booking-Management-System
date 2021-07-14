
package cst2550_coursework;

import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ammar
 */
public class ServerIT {

    public ServerIT() {
    }

    /**
     * Test of isCorrectUserName method, of class Server.
     */
    @Test
    public void testIsCorrectUserName() throws Exception {
        Connection conn = new DBConnection().getConnection();
        System.out.println("isCorrectUserName");
        String str = "frank";
        Server instance = new Server(new Socket());
        boolean expResult = true;
        boolean result = instance.isCorrectUserName(str, conn);
        assertEquals(expResult, result);
    }
    /**
     * Test of isCorrectUserName method, of class Server.
     */
    @Test
    public void testIsCorrectUserName1() throws Exception {
        Connection conn = new DBConnection().getConnection();
        System.out.println("isCorrectUserName");
        String str = "abc";
        Server instance = new Server(new Socket());
        boolean expResult = false;
        boolean result = instance.isCorrectUserName(str, conn);
        assertEquals(expResult, result);
    }

    /**
     * Test of isCorrectPassword method, of class Server.
     */
    @Test
    public void testIsCorrectPassword() throws Exception {
        Connection conn = new DBConnection().getConnection();
        System.out.println("isCorrectPassword");
        String str = "frank";
        String pass = "frank";
        Server instance = new Server(new Socket());
        boolean expResult = true;
        boolean result = instance.isCorrectPassword(str, pass, conn);
        assertEquals(expResult, result);

    }

    /**
     * Test of getStaffId method, of class Server.
     */
    @Test
    public void testGetStaffId_String() throws Exception {
        Connection conn = DBConnection.getConnection();
        System.out.println("getStaffId");
        String trainerName = "ALEXENDAR";
        Server instance = new Server(new Socket());
        String expResult = "S001";
        String result = instance.getStaffId(trainerName, conn);
        assertEquals(expResult, result);
    }

    /**
     * Test of getStaffId method, of class Server.
     */
    @Test
    public void testGetStaffId_0args() throws Exception{
        Connection conn = DBConnection.getConnection();
        System.out.println("getStaffId");
        Server instance = new Server(new Socket());
        ArrayList expResult = new ArrayList<>();
        expResult.add(0, "S001");
        expResult.add(1, "S002");
        expResult.add(2, "S003");
        ArrayList result = instance.getStaffId(conn);
        assertEquals(expResult, result);

    }

    /**
     * Test of getTrainerFocus method, of class Server.
     */
    @Test
    public void testGetTrainerFocus() throws Exception {
        Connection conn = new DBConnection().getConnection();
        System.out.println("getTrainerFocus");
        Server instance = new Server(new Socket());
        ArrayList expResult = new ArrayList<>();
        expResult.add(0, "Weight-Loss,Muscle-Gain");
        expResult.add(1, "Weight-Loss,Flexibility");
        expResult.add(2, "Muscle-Gain,Flexibility");
        ArrayList result = instance.getTrainerFocus(conn);
        assertEquals(expResult, result);

    }

   
    /**
     * Test of getTrainerNames method, of class Server.
     */
    @Test
    public void testGetTrainerNames() throws Exception {
        Connection conn = DBConnection.getConnection();
        System.out.println("getTrainerNames");
        Socket connectionSocket = new Socket();
        Server instance = new Server(connectionSocket);
        ArrayList expResult = new ArrayList<>();
        expResult.add(0, "ALEXENDAR");
        expResult.add(1, "BRAD");
        expResult.add(2, "TANCREDI");
        
        ArrayList result = instance.getTrainerNames(conn);
        assertEquals(expResult, result);

    }
//
    /**
     * Test of getClientNames method, of class Server.
     */
    @Test
    public void testGetClientNames() throws Exception {
        Connection conn = DBConnection.getConnection();

        System.out.println("getClientNames");
        Socket connectionSocket = new Socket();
        Server instance = new Server(connectionSocket);
        String expResult = "MICHAEL SCOFIELD,SAMAAM ALI,SARA TANCREDI,HENRY BRAD,VERONICA BURROWS";
        String result = instance.getClientNames(conn);
        assertEquals(expResult, result);
    }

    /**
     * Test of getClientId method, of class Server.
     */
    @Test
    public void testGetClientId() throws Exception {
        Connection conn = DBConnection.getConnection();
        System.out.println("getClientId");
        String name = "MICHAEL SCOFIELD";
        Socket connectionSocket = new Socket();
        Server instance = new Server(connectionSocket);
        String expResult = "C001";
        String result = instance.getClientId(name, conn);
        assertEquals(expResult, result);

    }
}
