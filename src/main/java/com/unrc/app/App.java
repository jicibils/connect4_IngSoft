package com.unrc.app;

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




public class App{

  
      public static void main( String[] args ){

            before((request, response) -> {
              Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
            });

            
            after((request, response) -> {
                  Base.close();
            });        
              //ingresa a la pantalla principal
            get("/Connect4", (request, response) -> {
                  Map<String, Object> attributes = new HashMap<>();
                  List <User> users = User.findAll();
                  attributes.put("users",users);

                  String player1 = request.queryParams("comboboxUs1");
                  String player2 = request.queryParams("comboboxUs2");
                  attributes.put("us1",player1);
                  attributes.put("us2",player2);
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

                        String player1 = request.queryParams("comboboxUs1");
                        String player2 = request.queryParams("comboboxUs2");

                        attributes.put("us1",player1);
                        attributes.put("us2",player2);
                        return new ModelAndView(attributes, "Connect4.moustache");                                   
                  }
                  else{
                        return new ModelAndView(null, "registered.moustache"); 
                  }
               
            }, new MustacheTemplateEngine());


            //ingresa a la pantalla de Jugar despues de crear un nuevo game y grid
            post("/play", (request, response) -> {
                  Map<String, Object> attributes = new HashMap<>();
                  String player1 = request.queryParams("comboboxUs1");
                  String player2 = request.queryParams("comboboxUs2");

                  // control de usuarios diferentes
                  if(player1.equals(player2)){
                        List <User> users = User.findAll();
                        attributes.put("users",users);

                        attributes.put("us1",player1);
                        attributes.put("us2",player2);
                        return new ModelAndView(attributes, "Connect4.moustache");                                   
                  }
                  else{
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
            }, new MustacheTemplateEngine());
            


            get("/play", (request, response) -> {
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

                  String table = request.queryParams("table");
                  table = game.getGrid().toStringTable(); 
                  attributes.put("table", table);
                  
                  if (c!=null){

                        c.set("X",c.getx());    
                        c.set("Y",c.gety());    
                        c.set("state",c.getState());  
                        c.save();
                        g.add(c);

                  }
                  attributes.put("turno",turno);
                  
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
                  }, new MustacheTemplateEngine());

      
	

	 


 
            //ingresa a la pantalla que te muestra los ranking
            get("/rank", (request, response) -> {
                  Map<String, Object> attributes = new HashMap<>();
                  // List <Rank> ranking = Rank.findAll()
                  // .orderBy("points desc");

                  List <Rank> position = Rank.findAll()
                  .orderBy("nroRank asc");

                  // attributes.put("ranking",ranking);
                  attributes.put("pos",position);

                  return new ModelAndView(attributes, "rank.moustache");
            }, new MustacheTemplateEngine());


            get("/volverConnect4", (request, response) -> {
                  Map<String, Object> attributes = new HashMap<>();
                  List <User> users = User.findAll();
                  attributes.put("users",users);

                  String player1 = request.queryParams("comboboxUs1");
                  String player2 = request.queryParams("comboboxUs2");

                  attributes.put("us1",player1);
                  attributes.put("us2",player2);
                  return new ModelAndView(attributes, "Connect4.moustache");                                   
            }, new MustacheTemplateEngine());

            post("/loadplayer", (request, response) -> {
                  
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
                  
                  attributes.put("us1",player1);
                  attributes.put("us2",player2);
                  attributes.put("turno",turno);     
                  attributes.put("game_id",g.get("id"));
                  attributes.put("table", table);

                 return new ModelAndView(attributes, "play.moustache");
            }, new MustacheTemplateEngine());



            post("/load", (request, response) -> {
                  
                  Map<String, Object> attributes = new HashMap<>();

                  String player1 = request.queryParams("comboboxUs1");
                  String player2 = request.queryParams("comboboxUs2");
                  List<User> lu1  = User.where("nickId = ?",player1);
                  User u1 = lu1.get(0);
                  List<User> lu2  = User.where("nickId = ?",player2);
                  User u2 = lu2.get(0);

                  List<Game> juegos = Game.where("player1_id = '"+u1.get("id")+"' AND player2_id = '"+u2.get("id")+"' AND dateEnd IS NULL");
                  attributes.put("juegos",juegos);
                  attributes.put("us1",player1);
                  attributes.put("us2",player2);

                  List <User> users = User.findAll();
                  attributes.put("users",users);

                  if (player1.equals(player2)) return new ModelAndView(attributes,"load.moustache");

                  return new ModelAndView(attributes, "loadplayer.moustache");
            }, new MustacheTemplateEngine());

            get("/load", (request, response) -> {

                  Map<String, Object> attributes = new HashMap<>();
                  List <User> users = User.findAll();
                  attributes.put("users",users);

                  String player1 = request.queryParams("comboboxUs1");
                  String player2 = request.queryParams("comboboxUs2");
                  String juegos = "";

                  attributes.put("us1",player1);
                  attributes.put("us2",player2);
                  attributes.put("juegos",juegos);
                                    
                return new ModelAndView(attributes, "load.moustache");
            }, new MustacheTemplateEngine());

            post("/save", (request, response) -> {
                return new ModelAndView(null, "play.moustache");
            }, new MustacheTemplateEngine());





            //ingresa a la pantalla de salir
            get("/bye", (request, response) -> {
                  return new ModelAndView(null, "bye.moustache");                                   
            }, new MustacheTemplateEngine());


      }





  

// FALTA PARA EL JUEVES:
      // >> TEST
      // >> POWER POINT
      // >> LIMPIAR CODIGO


}