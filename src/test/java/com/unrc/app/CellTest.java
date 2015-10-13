package com.unrc.app;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.unrc.app.*;

import static org.junit.Assert.assertEquals;
import static org.javalite.test.jspec.JSpec.the;


public class CellTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_test", "root", "root");
        System.out.println("CellTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("CellTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void notEmptyCell(){
        Cell c = new Cell();
        c.set("state", 1);
        assertEquals(c.getState(),1);        
    }

    @Test
    public void emptyCell(){
        Cell c = new Cell();
        c.set("state", 0);
        assertEquals(c.getState(),0); 
    }
}