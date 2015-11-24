package com.unrc.app;
import java.util.*;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.externalStaticFileLocation;

import com.unrc.app.User;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

import java.util.*;
import java.net.*;
import java.lang.Exception;
import java.lang.Runtime;
import java.lang.Thread;
import java.io.IOException;

import java.io.*;
public class App extends Thread{
    
      public App(){
      }

      public static void menu(){
            Menu.showApp();
      }

      public void run(){
         try {
            String [] cmd = {"node", "index.js"}; 
            Runtime.getRuntime().exec(cmd); 
          } catch (IOException ioe) {
            System.out.println (ioe);
          }
      }

    public static void main( String[] args ){
          App a=new App();

          try {
            String [] cmd = {"node", "index.js"}; 
            Runtime.getRuntime().exec(cmd); 
          } catch (IOException ioe) {
            System.out.println (ioe);
          }
          
          a.menu();
    
    }
      
}