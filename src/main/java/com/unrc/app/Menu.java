package com.unrc.app;

import com.unrc.app.App;
import com.unrc.app.Play;
import com.unrc.app.Start;
import com.unrc.app.MenuPlayer;
import com.unrc.app.User;
import com.unrc.app.Rank;
import com.unrc.app.Grid;
import com.unrc.app.Cell;
import com.unrc.app.Game;
import com.unrc.app.Hit;
import com.unrc.app.Removed;

import org.javalite.activejdbc.Base;
import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Scanner;
import java.util.List;
import java.util.*;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.io.IOException;
import java.io.StringWriter;
import org.eclipse.jetty.io.RuntimeIOException;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.Console;
import java.net.*;
import java.lang.Exception;



public class Menu{ 

      private static final String sesion_actual = "username";

      public Menu(){}

      private static String getServerIp(){
          String ip="";
          try {
              Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
              while (interfaces.hasMoreElements()) {
                  NetworkInterface iface = interfaces.nextElement();
                  // filters out 127.0.0.1 and inactive interfaces
                  if (iface.isLoopback() || !iface.isUp())
                      continue;

                  Enumeration<InetAddress> addresses = iface.getInetAddresses();
                  while(addresses.hasMoreElements()) {
                      InetAddress addr = addresses.nextElement();
                      ip = addr.getHostAddress();
                      System.out.println(iface.getDisplayName() + " " + ip);
                  }
              }
          } catch (SocketException e) {
              throw new RuntimeException(e);
          }
            return ip;
      }


      public static void showApp(){

            before((request, response) -> {
              Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
            });

            
            after((request, response) -> {
                  Base.close();
            });        

              //ingresa al Login
            get("/main", (request, response) -> {
                  return new ModelAndView(null, "Login.moustache");                                   
            }, new MustacheTemplateEngine());


            get("/Login", (request, response) -> {
                  String nick = request.queryParams("nick");
                  String pass = request.queryParams("pass");
                  User u = User.findFirst("nickId=?", nick);
                  if (u != null){
                        System.out.println("AAAAAAAAAAAAAAAAAAA");
                        if ((nick.equals(u.get("nickId"))) && (pass.equals(u.get("password")))){
                        System.out.println("BBBBBBBBBBBBBBBBBBBbb");
                              if (nick != null) {
                        System.out.println("CCCCCCCCCCCCCCCCCCCCc");
                                    request.session().attribute(sesion_actual, nick);
                              }      
                        System.out.println("DDDDDDDDDDDDDDDDDDDdd");
                              response.redirect("/");
                        System.out.println("EEEEEEEEEEEEEEEEEEEEEeee");
                              return new ModelAndView(null, "main.moustache"); 

                        }
                        else{
                              return new ModelAndView(null, "main.moustache"); 
                        }
                  }
                  else{
                        return new ModelAndView(null, "main.moustache"); 
                  }
            }, new MustacheTemplateEngine());

            get("/", (request, response) -> {
                        System.out.println("111111111111111111111111111");
                  String session = request.session().attribute(sesion_actual);

                        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFf");
                  if (session == null) {
                        System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
                        return new ModelAndView(null, "main.moustache"); 
                  } else {
                        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                        Map<String, Object> attributes = new HashMap<>();
                        List <User> users = User.findAll();
                        attributes.put("users",users);

                        // String player1 = request.queryParams("comboboxUs1");
                        String player2 = request.queryParams("comboboxUs2");
                        System.out.println("sesion_actual "+session);

                        attributes.put("us1",session);
                        attributes.put("us2",player2);
                        attributes.put("sesion_actual",session);

                        return new ModelAndView(attributes, "Connect4.moustache");                                   
                  }
              }, new MustacheTemplateEngine());

            get("/cerrarSesion", (request, response) -> {
                  String session = request.session().attribute("sesion_actual");
                  if (session != null) {
                      request.session().removeAttribute("sesion_actual");
                      response.redirect("/bye");
                  }
                  return new ModelAndView(null, "bye.moustache");
            }, new MustacheTemplateEngine());
              //ingresa a la pantalla principal
            get("/Connect4", (request, response) -> {
                  String name = request.session().attribute(sesion_actual);
                  Map<String, Object> attributes = new HashMap<>();
                  List <User> users = User.findAll();
                  attributes.put("users",users);

                  String player1 = request.queryParams("us1");
                  String player2 = request.queryParams("comboboxUs2");
                  attributes.put("us1",player1);
                  attributes.put("us2",player2);
                  attributes.put("sesion_actual",name);
                  return new ModelAndView(attributes, "Connect4.moustache");                                   
            }, new MustacheTemplateEngine());


            //ingresa a la pantalla de registro
            get("/registered", (request, response) -> {
                  return new ModelAndView(null, "registered.moustache");                                   
            }, new MustacheTemplateEngine());


            //registra usuario 
            post("/registrar", (request, response) -> {
                  String nick = request.queryParams("nick");
                  String name = request.queryParams("nombre");
                  String lastName = request.queryParams("apellido");
                  String mail = request.queryParams("e-mail");
                  String pass = request.queryParams("pass");
                  String dni = request.queryParams("dni");
                  String age = request.queryParams("edad");
                  boolean reg = Start.registered(nick,name,lastName,mail,pass,dni,age);
                  if (reg){
                        Map<String, Object> attributes = new HashMap<>();
                        List <User> users = User.findAll();
                        attributes.put("users",users);

                        String player1 = request.queryParams("us1");
                        String player2 = request.queryParams("comboboxUs2");

                        attributes.put("us1",player1);
                        attributes.put("us2",player2);
                        return new ModelAndView(attributes, "Login.moustache");                                   
                  }
                  else{
                        return new ModelAndView(null, "registered.moustache"); 
                  }
               
            }, new MustacheTemplateEngine());


            //ingresa a la pantalla de Jugar despues de crear un nuevo game y grid
            post("/play", (request, response) -> {

                  String session = request.session().attribute(sesion_actual);

                        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");

                  if (session == null) {
                        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
                      return new ModelAndView(null, "main.moustache");
                  }
                  else{
                        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                        Map<String, Object> attributes = new HashMap<>();
                        String player1 = request.queryParams("us1");
                        String player2 = request.queryParams("comboboxUs2");

                        // control de usuarios diferentes
                        if(player1.equals(player2)){
                              List <User> users = User.findAll();
                              attributes.put("users",users);

                              attributes.put("sesion_actual",session);
                              attributes.put("us1",player1);
                              attributes.put("us2",player2);
                              return new ModelAndView(attributes, "Connect4.moustache");                                   
                        }
                        else{
                              if (player2.equals("CPU")) {
                                    //aplicar minimax
                                    return new ModelAndView(attributes, "Connect4.moustache");                                   
                              }      
                              else{
                                    attributes.put("sesion_actual",session);
                                    attributes.put("us1",player1);
                                    attributes.put("us2",player2);
                                    attributes.put("turno",player1);
                                    Game g = new Game();
                                    String table = g.getGrid().toStringTable();
                                    boolean reg = MenuPlayer.newGame(player1,player2,g);
                                    attributes.put("game_id",g.get("id"));
                                    attributes.put("table", table);
                                    if(reg){
                                      return new ModelAndView(attributes, "play.moustache");
                                    }
                                    else{
                                        return new ModelAndView(attributes, "Connect4.moustache");                                   
                                    }
                              }      
                        }      
                  }      
            }, new MustacheTemplateEngine());
            


            post("/game", (request, response) -> {

                  String name = request.queryParams("sesion_actual");

                  if (name == null) {
                      return new ModelAndView(null, "main.moustache");
                  }
                  else{
                        Map<String, Object> attributes = new HashMap<>();


                        String player1 = request.queryParams("us1");
                        String player2 = request.queryParams("us2");
                        String game_id = request.queryParams("game_id");
                        String turno = request.queryParams("turno");
                        String boton = request.queryParams("C");
                        
                        List<Game> ga  = Game.where("id = ?", game_id);
                        Game game = new Game();
                        game = ga.get(0); 


                        int id_grid = (int) game.get("grid_id");
                        List<Grid> grid = Grid.where("id = ?", id_grid);
                        List<Cell> celdas = Cell.where("grid_id = ?",id_grid);  
                        game.set_Cells(celdas);
                        Grid g = grid.get(0);               
                        attributes.put("sesion_actual",name);
                        attributes.put("us1",player1);
                        attributes.put("us2",player2);
                        attributes.put("game_id",game_id);
                        Cell c = null;
                        if (game.get("dateEnd") == null){
                              int y = Character.getNumericValue(boton.charAt(3));
                              turno = Play.turn(player1,player2,turno);
                              c = game.pushDisc(y,Play.player_actual(player1,player2,turno)); 
                              if (c==null) turno = Play.turn(player1,player2,turno);
                        }

                        
                        if (c!=null){


                              c.set("X",c.getx());    
                              c.set("Y",c.gety());    
                              c.set("state",c.getState());  
                              c.save();
                              g.add(c);


                        }
                        attributes.put("turno",turno);
                        String table = game.getGrid().toStringTable(); 
                        attributes.put("table", table);
                        
                        
                        //Check winner


                        int partida = game.gameOver(c);

                        //>0 indica que el juego termino ya que no hay mas casilleros disponibles
                        // >1 indica que el jugador que hizo el ultimo movimiento gano
                        // >2 indica que no hay ganador, el juego continua

                        if ((partida==0) || (game.get("dateEnd")!=null)){

                              String actual = Play.turn(player1,player2,turno);
                              String draw = "<h2> Empatee..!</h2>";
                              attributes.put("result",draw);

                              if (game.get("dateEnd")==null){

                                    List<User> l_us = User.where("nickId = ?", actual);
                                    User u1 = l_us.get(0);
                                    Rank.draw(u1);
                                    List<User> us = User.where("nickId = ?", turno);
                                    User u2 = us.get(0);
                                    Rank.draw(u2);     
                                    game.set("dateEnd",Start.getFechaActual());
                                    game.save();
                              }
                        }

                        if ((partida==1) || (game.get("dateEnd")!=null)){

                              String actual = Play.turn(player1,player2,turno);
                              String winner = "<h2> The Winner is:  "+actual +"</h2> ";  
                              attributes.put("result",winner);

                              if (game.get("dateEnd")==null){

                                    List<User> l_us = User.where("nickId = ?", actual);
                                    User u1 = l_us.get(0);
                                    Rank.win(u1);
                                    game.set("dateEnd",Start.getFechaActual());
                                    game.save();
                                    // u.add(game);
                                    List<User> us = User.where("nickId = ?", turno);
                                    User u2 = us.get(0);
                                    Rank.loser(u2);                            
                              }                       

                        }

                                                  
                         return new ModelAndView(attributes, "play.moustache");
                  }      
            }, new MustacheTemplateEngine());

 








            //ingresa a la pantalla que te muestra los ranking
            get("/rank", (request, response) -> {
                  String session = request.session().attribute(sesion_actual);
                  if (session == null) {
                      return new ModelAndView(null, "main.moustache");
                  }
                  else{

                        Map<String, Object> attributes = new HashMap<>();

                        List <Rank> position = Rank.findAll()
                        .orderBy("nroRank asc");

                        String player1 = request.queryParams("us1");

                        attributes.put("sesion_actual",session);
                        attributes.put("us1",player1);
                        attributes.put("pos",position);

                        return new ModelAndView(attributes, "rank.moustache");
                  }      
            }, new MustacheTemplateEngine());


            get("/volverConnect4", (request, response) -> {
                  String session = request.session().attribute(sesion_actual);
                  if (session == null) {
                      return new ModelAndView(null, "main.moustache");
                  }
                  else{

                        Map<String, Object> attributes = new HashMap<>();
                        List <User> users = User.findAll();
                        attributes.put("users",users);

                        String player1 = request.queryParams("us1");
                        String player2 = request.queryParams("comboboxUs2");

                        attributes.put("sesion_actual",session);
                        attributes.put("us1",player1);
                        attributes.put("us2",player2);
                        return new ModelAndView(attributes, "Connect4.moustache");                                   
                  }      
            }, new MustacheTemplateEngine());

            get("/volverLogin", (request, response) -> {
                  return new ModelAndView(null, "main.moustache");                                   
            }, new MustacheTemplateEngine());


            post("/loadplayer", (request, response) -> {
                  
                  String session = request.session().attribute(sesion_actual);
                  if (session == null) {
                      return new ModelAndView(null, "main.moustache");
                  }
                  else{

                        Map<String, Object> attributes = new HashMap<>();
                        String game_id = request.queryParams("game_id");
                        
                        List<Game> gamelist = Game.where("id = ?", game_id);
                        List<Cell> cellList  = Cell.where ("grid_id = ?", game_id); 

                        Game g = gamelist.get(0);
                        g.set_Cells(cellList);

                        int player1_id = (int) g.get("player1_id");
                        int player2_id = (int) g.get("player2_id");

                        List<User> lu1  = User.where("id = ?",player1_id);              
                        List<User> lu2  = User.where("id = ?",player2_id);

                        String player1 = (String) lu1.get(0).get("nickId");   
                        String player2 = (String) lu2.get(0).get("nickId");
                        String table = g.getGrid().toStringTable();
                        String turno = "";

                        if ((g.getGrid().getCant()%2)==0) turno = player1;
                        else turno = player2;
                        
                        attributes.put("sesion_actual",session);
                        attributes.put("us1",player1);
                        attributes.put("us2",player2);
                        attributes.put("turno",turno);     
                        attributes.put("game_id",g.get("id"));
                        attributes.put("table", table);

                       return new ModelAndView(attributes, "play.moustache");
                  }     
            }, new MustacheTemplateEngine());



            post("/load", (request, response) -> {
                  
                  String session = request.session().attribute(sesion_actual);
                  if (session == null) {
                      return new ModelAndView(null, "main.moustache");
                  }
                  else{

                        Map<String, Object> attributes = new HashMap<>();

                        String player1 = request.queryParams("us1");
                        String player2 = request.queryParams("comboboxUs2");
                        List<User> lu1  = User.where("nickId = ?",player1);
                        User u1 = lu1.get(0);
                        List<User> lu2  = User.where("nickId = ?",player2);
                        User u2 = lu2.get(0);

                        List<Game> juegos = Game.where("player1_id = '"+u1.get("id")+"' AND player2_id = '"+u2.get("id")+"' AND dateEnd IS NULL");
                        attributes.put("juegos",juegos);
                        attributes.put("sesion_actual",session);
                        attributes.put("us1",player1);
                        attributes.put("us2",player2);

                        List <User> users = User.findAll();
                        attributes.put("users",users);

                        if (player1.equals(player2)) return new ModelAndView(attributes,"load.moustache");

                        return new ModelAndView(attributes, "loadplayer.moustache");
                  }      
            }, new MustacheTemplateEngine());

            get("/load", (request, response) -> {

                  String session = request.session().attribute(sesion_actual);
                  if (session == null) {
                      return new ModelAndView(null, "main.moustache");
                  }
                  else{
                        Map<String, Object> attributes = new HashMap<>();
                        List <User> users = User.findAll();
                        attributes.put("users",users);

                        String player1 = request.queryParams("us1");
                        String player2 = request.queryParams("comboboxUs2");
                        String juegos = "";

                        attributes.put("sesion_actual",session);
                        attributes.put("us1",player1);
                        attributes.put("us2",player2);
                        attributes.put("juegos",juegos);
                                          
                      return new ModelAndView(attributes, "load.moustache");
                  }    
            }, new MustacheTemplateEngine());

            post("/save", (request, response) -> {
                return new ModelAndView(null, "play.moustache");
            }, new MustacheTemplateEngine());





            //ingresa a la pantalla de salir
            get("/bye", (request, response) -> {
                  return new ModelAndView(null, "bye.moustache");                                   
            }, new MustacheTemplateEngine());


            
/*----------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------*/
            //************ VERSION ONLINE ******************
            
            get("/menuOnline",(request,response)->{
                  // String session=request.session().attribute("SESSION_NAME");  
                  String session = request.session().attribute(sesion_actual);
                        System.out.println("11111111111111111111111");
                  if(session==null){
                        System.out.println("222222222222222222222222");
                      return new ModelAndView(null, "main.moustache");
                  }else{
                        System.out.println("333333333333333333333333333");
                        Map<String, Object> attributes = new HashMap<String,Object>();
                        System.out.println("4444444444444444444444");
                        attributes.put("ip",Menu.getServerIp().toString());
                        System.out.println("5555555555555555555555555");
                        User u = User.findFirst("nickId=?", session);
                        attributes.put("user",u.get("id"));
                        attributes.put("us1",u.get("nickId"));
                        System.out.println("6666666666666666666666666");
                        attributes.put("sesion_actual",session);
                        System.out.println("7777777777777777777777777");
                        return new ModelAndView(attributes, "menuOnline.moustache");
                  }
            },new MustacheTemplateEngine());


            post("/crearJuego",(request,response)->{
                  Map<String, Object> attributes = new HashMap<String,Object>();
                        System.out.println("88888888888888888888888888888888888888888");
                  String session = request.session().attribute(sesion_actual);
                  Game g = new Game();
                  String table = g.getGrid().toStringTable();
                  boolean reg = MenuPlayer.newGame(session,session,g);
                  g.set("channel",request.queryParams("channel")).saveIt();
                  if(reg){
                        attributes.put("user",session);
                        attributes.put("us1",session);
                        attributes.put("game",g.get("id"));
                        attributes.put("channel",request.queryParams("channel"));
                        attributes.put("ip",Menu.getServerIp());
                        return new ModelAndView(attributes, "subscriber.moustache");
                  }
                  else{
                      return new ModelAndView(attributes, "Connect4.moustache");                                   
                  }
            },new MustacheTemplateEngine());

            get("/buscarJuego", (request,response)->{
                  Map<String, Object> attributes = new HashMap<String,Object>();
                        System.out.println("9999999999999999999999999999999999999999");
                  List<Game> listGames = Game.where("player1_id=player2_id and channel<>0");
                  attributes.put("games",listGames);
                  attributes.put("user",request.session().attribute(sesion_actual));
                  attributes.put("us1",request.session().attribute(sesion_actual));

                  return new ModelAndView(attributes,"availableGames.moustache");
            },new MustacheTemplateEngine());
            
            post("/loading",(request,response)->{
                  Map<String, Object> attributes = new HashMap<String,Object>();
                        System.out.println("101010101010101010101010101010101010101010");
                  String session = request.session().attribute(sesion_actual);
                  User u = User.findFirst("nickId=?", session);
 
                  Game currentGame= Game.findFirst("id=?",request.queryParams("game"));
                  currentGame.set("player2_id",u.get("id")).saveIt();
                  attributes.put("user",request.session().attribute(sesion_actual));
                  attributes.put("us1",request.session().attribute(sesion_actual));
                  attributes.put("channel",request.queryParams("channel"));
                  attributes.put("game",request.queryParams("game"));
                  attributes.put("ip",Menu.getServerIp());
                  return new ModelAndView(attributes,"loading.moustache");
            },new MustacheTemplateEngine());


            post("/playOnline",(request,response)->{  
                  System.out.println("11 11  11 11 11 11 11 11 11 11 11 11 11");
                  String game=request.queryParams("game");
                  String session = request.session().attribute(sesion_actual);
                  String channel="chn"+request.queryParams("channel").toString();                                                               
                  Map<String, Object> attributes = new HashMap<>();
                  Game currentGame=Game.findFirst("id=?",game);
                        System.out.println("11 11  11 11 11 11 11 11 11 11 11 11 11");
                  String user1_id, user2_id, turn;


                        
                        System.out.println("LASKASSKSAKKSAKSAKSAKASKSAK");
                  List<Game> gamelist = Game.where("id=?",currentGame.get("id"));
                        System.out.println("ASKFASJFOJASOFJSAOJFOASJOFJASOFJ");
                  List<Cell> cellList  = Cell.where ("grid_id=?", currentGame.get("id")); 
                        System.out.println("ASKFASJFOJASdasfiaskfbaskjnfjkasbkfjbaskfbkasb");

                        System.out.println("ñammmmmmmmm");
                  Game g = gamelist.get(0);
                        System.out.println("ñññññññummmmmmmmmmmmmm");
                  g.set_Cells(cellList);
                        System.out.println("11 11  11 11 11 11 11 11 11 11 11 11 11");

                  int player1_id = (int) g.get("player1_id");
                  int player2_id = (int) g.get("player2_id");

                  List<User> lu1  = User.where("id = ?",player1_id);              
                  List<User> lu2  = User.where("id = ?",player2_id);

                  String player1 = (String) lu1.get(0).get("nickId");   
                  String player2 = (String) lu2.get(0).get("nickId");
                  String table = g.getGrid().toStringTable();
                  String turno = "";

                  if ((g.getGrid().getCant()%2)==0) turno = player1;
                  else turno = player2;


                  attributes.put("sesion_actual",session);
                  attributes.put("user1",player1);
                  attributes.put("user2",player2);
                  attributes.put("turno",turno);     
                  attributes.put("game_id",g.get("id"));
                  attributes.put("table", table);
                  attributes.put("channel",channel);
                  attributes.put("sesion_actual",session);

                  return new ModelAndView(attributes, "playOnline.moustache");



            }, new MustacheTemplateEngine());


            post("/gameOnline",(request,response)->{  

                  String name = request.queryParams("sesion_actual");

                  if (name == null) {
                      return new ModelAndView(null, "main.moustache");
                  }
                  else{
                        Map<String, Object> attributes = new HashMap<>();


                        String player1 = request.queryParams("user1");
                        String player2 = request.queryParams("user2");
                        String game_id = request.queryParams("game_id");
                        String turno = request.queryParams("turno");
                        String boton = request.queryParams("C");
                        String channel = request.queryParams("channel");
                        
                        List<Game> ga  = Game.where("id = ?", game_id);
                        Game game = new Game();
                        game = ga.get(0); 


                        int id_grid = (int) game.get("grid_id");
                        List<Grid> grid = Grid.where("id = ?", id_grid);
                        List<Cell> celdas = Cell.where("grid_id = ?",id_grid);  
                        game.set_Cells(celdas);
                        Grid g = grid.get(0);               
                        attributes.put("sesion_actual",name);
                        attributes.put("us1",player1);
                        attributes.put("us2",player2);
                        attributes.put("game_id",game_id);
                        Cell c = null;
                        if (game.get("dateEnd") == null){
                              int y = Character.getNumericValue(boton.charAt(3));
                              turno = Play.turn(player1,player2,turno);
                              c = game.pushDisc(y,Play.player_actual(player1,player2,turno)); 
                              if (c==null) turno = Play.turn(player1,player2,turno);
                        }

                        
                        if (c!=null){


                              c.set("X",c.getx());    
                              c.set("Y",c.gety());    
                              c.set("state",c.getState());  
                              c.save();
                              g.add(c);


                        }
                        attributes.put("turno",turno);
                        String table = game.getGrid().toStringTable(); 
                        attributes.put("table", table);
                        
                        
                        //Check winner


                        int partida = game.gameOver(c);

                        //>0 indica que el juego termino ya que no hay mas casilleros disponibles
                        // >1 indica que el jugador que hizo el ultimo movimiento gano
                        // >2 indica que no hay ganador, el juego continua

                        if ((partida==0) || (game.get("dateEnd")!=null)){

                              String actual = Play.turn(player1,player2,turno);
                              String draw = "<h2> Empatee..!</h2>";
                              attributes.put("result",draw);

                              if (game.get("dateEnd")==null){

                                    List<User> l_us = User.where("nickId = ?", actual);
                                    User u1 = l_us.get(0);
                                    Rank.draw(u1);
                                    List<User> us = User.where("nickId = ?", turno);
                                    User u2 = us.get(0);
                                    Rank.draw(u2);     
                                    game.set("dateEnd",Start.getFechaActual());
                                    game.save();
                              }
                        }

                        if ((partida==1) || (game.get("dateEnd")!=null)){

                              String actual = Play.turn(player1,player2,turno);
                              String winner = "<h2> The Winner is:  "+actual +"</h2> ";  
                              attributes.put("result",winner);

                              if (game.get("dateEnd")==null){

                                    List<User> l_us = User.where("nickId = ?", actual);
                                    User u1 = l_us.get(0);
                                    Rank.win(u1);
                                    game.set("dateEnd",Start.getFechaActual());
                                    game.save();
                                    // u.add(game);
                                    List<User> us = User.where("nickId = ?", turno);
                                    User u2 = us.get(0);
                                    Rank.loser(u2);                            
                              }                       

                        }

                  attributes.put("sesion_actual",name);
                  attributes.put("user1",player1);
                  attributes.put("user2",player2);
                  attributes.put("turno",turno);     
                  attributes.put("game_id",g.get("id"));
                  attributes.put("table", table);
                  attributes.put("channel",channel);
                                                  
                         return new ModelAndView(attributes, "playOnline.moustache");
                  }      
            },new MustacheTemplateEngine());
      




            get("/lostConnection",(request,response)-> {      
                  
                  return new ModelAndView(null,"main.moustache");
                  
            },new MustacheTemplateEngine());

      

      }

}