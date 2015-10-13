package com.unrc.app;

import org.javalite.activejdbc.Model;
import java.util.ArrayList;
import java.util.List;





public class Game extends Model {


//----------------------------------------------------------------------------------------------------------------------------------------------------------

//Contructores---------------------------------------------------------------------------------------------------------------------------------------

	private Grid g;

	public Game(){

		g = new Grid();

	}

	public Game(int x, int y){

		g = new Grid(x,y);
	}


//----------------------------------------------------------------------------------------------------------------------------------------------------------

//Methods---------------------------------------------------------------------------------------------------------------------------------------------

	/* gameOver(Cell c) retorna el estado de la partida actual
	
	@param c es la celda donde se puso la ultima ficha
	@returns un numero, donde:

		>0 indica que el juego termino ya que no hay mas casilleros disponibles
		>1 indica que el jugador que hizo el ultimo movimiento gano
		>2 indica que no hay ganador, el juego continua
	*/
	public int gameOver(Cell c){

		if (c==null) return 2;
		/* si la grilla esta llena, retorno 0 */
		if ( g.gridFull() ) return 0;	
		/* si la grilla no esta llena, evaluo el estado de la partida */
		if (g.getCant()>=7){

			boolean state = false;	/* Se asume que la partida no tiene ganador */
			ArrayList<Cell> array = new ArrayList();  /*  Creamos un arreglo  */

			/* Verifico en un entorno de la ultima ficha ingresada */

			array = getHorizontal(c);
			state = check(array,c);

			if (state) return 1;

			array = getVertical(c);
			state = check(array,c);

			if (state) return 1;

			array = getLeftDiagonal(c);
			state = check(array,c);

			if (state) return 1;

			array = getRightDiagonal(c);
			state = check(array,c);

			if (state) return 1;
			
		}

		/* en caso que no encuentre ganador, retorno 2 */
		return 2;	
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------
//Metodos usados en gameOver(c);	
//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* getVertical(Cell c) retorna un arreglo con el entorno vertical de la utlima ficha ingresada
	@param c es la celda donde se puso la ultima ficha
	@returns un arreglo con las celdas
	*/
	public ArrayList<Cell> getVertical(Cell c){

		ArrayList<Cell> array = new ArrayList();	/* Arreglo a devolver */ /*ESTA MAL EL CONSTRUCTOR*/

		int x = c.getx(); 

		/* recorro la matriz verticalmente en un entorno de c */
		for (int i= -3; i<=3;i++){

			int k = c.gety() + i;	/* columna de la celda a ingresar en el arreglo */
			
			boolean in = (k>=0) && (k<=g.gety()); /* compruebo si la posicion de la columna es valida */

			if (in) array.add( g.getCell(x,k) ); /* si es valida, la guardo en el arreglo */
		}

		return array;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* getHorizontal(Cell c) retorna un arreglo con el entorno horizontal de la utlima ficha ingresada
	@param c es la celda donde se puso la ultima ficha
	@returns un arreglo con las celdas
	*/
	public ArrayList<Cell> getHorizontal(Cell c){

		ArrayList<Cell> array = new ArrayList();	/* Arreglo a devolver */

		int y = c.gety();

		/* recorro la matriz horizontalmente en un entorno de c */
		for (int i= -3; i<=3;i++){

			int k = c.getx() + i;	/* fila de la celda a ingresar en el arreglo */
			
			boolean in = (k>=0) && (k<=g.getx());  /* compruebo si la posicion de la fila es valida */

			if (in) array.add( g.getCell(k,y) );  /* si es valida, la guardo en el arreglo */
		}
		
		return array;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* getLeftDiagonal(Cell c) retorna un arreglo con el entorno diagonal (izquierda) de la utlima ficha ingresada
	@param c es la celda donde se puso la ultima ficha
	@returns un arreglo con las celdas
	*/
	public ArrayList<Cell> getLeftDiagonal(Cell c){

		ArrayList<Cell> array = new ArrayList();	/* Arreglo a devolver */
		
		/* recorro la matriz en diagonal en un entorno de c */
		for (int i= -3; i<=3;i++){

			int t = c.getx() + i;    /* fila de la celda a ingresar en el arreglo  */
			int k = c.gety() + i;   /* columna de la celda a ingresar en el arreglo */
						
			boolean in = (k>=0) && (k<=g.gety()); /* compruebo si la posicion de la columna es valida */
			in = in && ((t>=0) && (t<=g.getx()));   /* compruebo si la posicion de la fila es valida */
 
			if (in) array.add( g.getCell(t,k) ); /* si es valida, la guardo en el arreglo */
		}
		
		return array;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* getRightDiagonal(Cell c) retorna un arreglo con el entorno diagonal (derecha) de la utlima ficha ingresada
	@param c es la celda donde se puso la ultima ficha
	@returns un arreglo con las celdas
	*/
	public ArrayList<Cell> getRightDiagonal(Cell c){

		ArrayList<Cell> array = new ArrayList();	/* Arreglo a devolver */

		int x = c.getx();

		/* recorro la matriz verticalmente en un entorno de c */
		for (int i= -3; i<=3;i++){

			int t = c.getx()  -  i;   /* fila de la celda a ingresar en el arreglo  */
			int k = c.gety() + i;   /* columna de la celda a ingresar en el arreglo */
									
			boolean in = (k>=0) && (k<=g.gety()); /* compruebo si la posicion de la columna es valida */
			in = in && ((t>=0) && (t<=g.getx()));   /* compruebo si la posicion de la fila es valida */

			if (in) array.add( g.getCell(t,k) ); /* si es valida, la guardo en el arreglo */
		}
		
		return array;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* check(ArrayList<Cell> array) retorna si el entorno evaluado contiene una combinacion ganadora
	@param array es el arreglo que contiene las celdas en un entorno
	@param c es la celda donde se puso la ultima ficha
	@returns un booleano que indica si el jugador gano o no
	*/
	public boolean check(ArrayList<Cell> array, Cell c){

		int player = c.getState(); /* indica el jugador que inserto la ultima ficha */
		boolean state = false;	   /* indica el estado de la partida, true representa que el juego termino */
		int count = 0;		   /* variable que cuenta la cantidad de fichas consecutivas del mismo jugador */
		int i = 0;		   /* variable para recorrer el arreglo */

		while ((!state) && (i<array.size()) ){

			if ( player==array.get(i).getState() ) count++;	/* si hay fichas consecutivas incremento count */
			else count = 0;					/* si encuentro una ficha del adversario, vuelvo a cero */
			i++;						/* avanzo */
			if (count==4) state = true;			/* si encountre 4 fichas consecutivas, cambio el estado */
		} 
		return state;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* pushDisc(int y) pone una nueva ficha en la columna indicada
	@param y es la columna donde estara la nueva ficha	
	@param player es el jugador que esta insertando la ficha
	@returns la celda en donde se coloco la ficha
	*/
	public Cell pushDisc(int y,int player){

		Cell cell = g.getCell(0,y);	/* tomamos la celda de mas arriba del tablero, para ver si hay lugar en la columna */

		if (cell.getState()==0){  	/* si hay lugar en la columna indicada */

			cell = getNextFreeCell(y);	/* buscamos en que fila va la ficha */
			cell.setState(player);		/* asignamos jugador que pone la ficha */
			g.setCell(cell);			/* seteamos la celda */
			g.incCant();  			/* se incrementa la cantidad de fichas en uno */
			return cell;
		}

		else System.out.println("la columna esta llena!");
		return null;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	public void set_Cells(List<Cell> c){

		for (int i = 0 ; i<c.size() ; i++ ) {

			Cell cell = new Cell();
			cell.setx( (int) c.get(i).get("X")  );
			cell.sety(  (int) c.get(i).get("Y")  );
			cell.setState( (int) c.get(i).get("state")  );
			g.setCell(cell);
			g.incCant();
		}
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* getNextFreeCell(int y) devuelve la celda en donde se colocara la nueva ficha
	@param y es la columna donde estara la nueva ficha	
	@returns una celda
	*/
	public Cell getNextFreeCell(int y){

		int x = g.getx();	    /* asumimos que la ultima fila esta libre */
		Cell c = g.getCell(x,y);	    /* tomamos la celda en x,y */
		
		while (c.getState()!=0){   /* mientras no encuentre una celda vacia */

			x--;
			c = g.getCell(x,y); /* tomo la proxima celda */
		}	

		return c;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* getx() devuelve la cantidad de filas del tablero
	@returns un entero 
	*/
	public int getx(){

		return g.getx();
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* gety() devuelve la cantidad de columnas del tablero
	@returns un entero 
	*/
	public int gety(){

		return g.gety();
	}


   	public String toString (){
    	return this.getString("player1_id");
	}   
   	public String toString1 (){
    	return this.getString("player2_id");
	}   
   	public String toStringGame (){
    	return this.getString("id");
	}   

	public Grid getGrid(){

		return g;
	}

}

