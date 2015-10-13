package com.unrc.app;

import org.javalite.activejdbc.Model;

//----------------------------------------------------------------------------------------------------------------------------------------------------------
/*

el estado sera un valor entero donde:

	0 = Disponible
	1 = Ocupada por jugador 1
	2 = Ocupada por jugador 2

las variables x,y indican la posicion en la matriz

*/

public class Cell extends Model{

	//Estado de la celda 
	private int state;
            
	//Coordenadas de la celda
	private int x;	/* fila */
	private int y;	/* columna */


//----------------------------------------------------------------------------------------------------------------------------------------------------------
//Constructores

	public Cell(){

		state = 0;
		x = 0;
		y = 0;
	}

	public Cell(int x, int y){

		state = 0;
		this.x = x;
		this.y = y;
	}

	public Cell(int x, int y, int state){

		this.state = state;
		this.x = x;
		this.y = y;
	}


//----------------------------------------------------------------------------------------------------------------------------------------------------------
//Gets

	/* getx() retorna la  fila donde se encuentra la celda
	@returns un entero con la fila
	*/
	public int getx(){

		return this.x;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* gety() retorna la  columna donde se encuentra la celda
	@returns un entero con la columna
	*/
	public int gety(){

		return this.y;  
	}


//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* getState() retorna el estado de la celda
	@returns un entero con el estado
	*/
	public int getState(){

		return this.state; 
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------
//Sets

	/* setx() setea la fila de la celda
	*/
	public void setx(int x){

		this.x = x;	
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* sety() setea la columna de la celda
	*/
	public void sety(int y){

		this.y = y;
	}	

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* setState() setea el estado de la celda
	*/
	public void setState(int state){

		this.state = state;	
	}


//----------------------------------------------------------------------------------------------------------------------------------------------------------

	
}