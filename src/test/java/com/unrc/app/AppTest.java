package com.unrc.app;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ UserTest.class })

public class AppTest {
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Master setup");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Master tearDown");
    }
}


