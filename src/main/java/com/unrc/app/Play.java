package com.unrc.app;

import com.unrc.app.User;
import com.unrc.app.Rank;
import java.util.Scanner;
import org.javalite.activejdbc.Model;

public class Play extends Model{


	public static int playing(String player1, String player2,Game game,String col){

        int columna = Integer.parseInt(col);

		Cell c = null;					/* Celda usada para la insersion de fichas */
		// Scanner pos = new Scanner(System.in);	/* Scanner usado para lectura de datos */
		int y = game.gety();				/* cantidad de columnas del tablero */
		int disc = 0;					/* columna donde ira la ficha  */
		boolean turn = true;				/* indica que jugador debe jugar (turno) */
		boolean correct = false;				/* indica si un movimiento es posible */

		// String msj = "Ingrese la columna en donde ingresar la ficha, desde 0 hasta "+y+": ";
		
		/* mientras no termine el juego */
		while (game.gameOver(c)==2){

			while (!correct){
				// System.out.println("");
				// if  (turn) System.out.print(player1+" "+msj);
				// if (!turn) System.out.print(player2+" "+msj);	
			
				// disc = pos.nextInt();
				disc = columna;
				correct = check_column(disc,y);	/* Checkeamos si la columna es correcta */
				if (!correct) System.out.println("La columna no existe");
			}

			if (turn) c = game.pushDisc(disc,1);
			else 	 c = game.pushDisc(disc,2);

			if (c!=null){
				// turn = !turn;  si se pudo ingresar la ficha, cambio de turno 
				return game.gameOver(c);
			}
			else{
				correct = false;
			}
		}

		return game.gameOver(c);
	}

	/* check_column(int column, int y) devuelve si la columna en donde vamos a ingresar la ficha existe
	@param column es la columna a evaluar
	@param y es la cantidad de columnas del tablero
	@returns un booleano donde:

		true = columna valida
		false = columna invalida
	*/

	public static boolean check_column(int column, int y){

		return (column>=0) && (column<=y);

	}


	public static String turn(String p1,String p2,String turno){

       
		if (turno.equals(p1)){
			return p2;
		}		
		else{
			return p1;
		}

	}

	public static int player_actual(String p1,String p2,String turno){

		if (turno.equals(p1)) return 2;
		return 1;
	}


	public static String colorFicha(String p1,String p2,String turno){


        String fi;
		if (turno.equals(p1)){
			fi = "bgcolor="+"#0004FF";
			return fi;
		}		
		else{
			fi = "bgcolor="+"#FF0000";
			return fi;
		}

	}



}