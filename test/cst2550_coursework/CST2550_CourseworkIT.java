/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst2550_coursework;

import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ammar
 */
public class CST2550_CourseworkIT {
    
    public CST2550_CourseworkIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class CST2550_Coursework.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        Stage primaryStage = null;
        CST2550_Coursework instance = new CST2550_Coursework();
        instance.start(primaryStage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class CST2550_Coursework.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CST2550_Coursework.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
