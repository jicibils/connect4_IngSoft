package com.unrc.app;

import java.util.Date; 
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.List;
import com.unrc.app.User;
import com.unrc.app.Rank;
import com.unrc.app.App;
import org.javalite.activejdbc.Model;
import static spark.Spark.*;

public class Start extends Model {

	// Menu de inicio
	public static void begin (){
	}

    
	public static boolean registered (String nick,String name,String lastName,String mail,String pass,String dni,String age) {
	

		if(search(nick)){//EN AUX SE LLEVA EL NICK INGRESADO POR EL USUARIO
			return false; 
		}
		else{

			User u = new User();
			u.set("nickId",nick);
			u.set("nameUs",name);
			u.set("lastNameUs",lastName);
			u.set("email",mail);
			u.set("password",pass);
			u.set("DNI",dni);
			u.set("age",age);
			u.save();

			// cuando se crea un user se le crea automaticamente su ranking
			Rank r = new Rank();
			r.set("PG",0);
			r.set("PE",0);
			r.set("PP",0);
			r.set("PJ",0);
			r.set("points",0);

			//cuento cuantos renking hay cargados y le pongo 1 mayor al que estoy registrando(osea ultimo)
			long rankCount = Rank.count();
			r.set("nroRank",rankCount+1);
				
			r.save();
			u.add(r);

			return true;
		}
	}

	public static boolean search (String nickId) {

		List<User> u = User.where("nickId = ?", nickId);

		//Verificamos si encontramos un usuario
		if (u.isEmpty()) return false;		
		else return true;	
	}



    public static String getFechaActual() {
        Date ahora = new Date();
 		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        return formato.format(ahora);
    }

}



