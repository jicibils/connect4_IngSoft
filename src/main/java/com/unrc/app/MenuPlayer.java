package com.unrc.app;


import com.unrc.app.User;
import com.unrc.app.Rank;
import java.util.List;
import java.util.Scanner;
import org.javalite.activejdbc.Model;

public class MenuPlayer extends Model{
	

	public static boolean newGame (String us1,String us2,Game g) {
		boolean respuesta = false;
		if(respuesta == false){
			g.set("dateBegin",Start.getFechaActual());
			User u1 = User.findFirst("nickId=?", us1);
			g.set("player1_id",u1.get("id"));
			User u2 = User.findFirst("nickId=?", us2);
			g.set("player2_id",u2.get("id"));
			g.save();

			Grid grid = g.getGrid();
			grid.set("X",6);
			grid.set("Y",5);
			grid.save();
			grid.add(g);
			return true;
		}
		else{
			return false;
		}	
	}
}