package com.unrc.app;

import org.javalite.activejdbc.Model;

public class Grid extends Model{

//----------------------------------------------------------------------------------------------------------------------------------------------------------
//Tablero
	 
	private Cell[][] grid;
	private int x; 		/* cantidad de filas */
	private int y;		/* cantidad de columnas */
	private int cant;	/* cantidad de celdas ocupadas por fichas en la grilla */

//----------------------------------------------------------------------------------------------------------------------------------------------------------
//Constructores

	//Defect (7x6)
	public Grid(){

		x = 6;
		y  = 5;
		grid = new Cell[x+1][y+1];
		initializeGrid(x,y);
		cant = 0;
	}

	public Grid(int x, int y){

		if ((x>=6) && (y>=5)){

			this.x = x;
			this.y  = y;
			grid = new Cell[x+1][y+1];
			initializeGrid(x,y);
			cant = 0;
		}
	}


//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/*  initializeGrid(int x, int y) inicializa el tablero con celdas vacias, donde su tamaño es x * y
	@param x es la cantidad de filas del tablero
	@param y es la cantidad de columnas del tablero
	*/
	private void initializeGrid(int x, int y){

		for(int i = 0; i<=x; i++){

			for(int j = 0; j<=y; j++){

				grid[i][j] = new Cell(i,j);
				
			}
		}
	} 


//----------------------------------------------------------------------------------------------------------------------------------------------------------
//Gets

	/* getx() retorna la cantidad de filas de la grilla 
	@returns un entero con la cantidad
	*/
	public int getx(){

		return x;	
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/* gety() retorna la cantidad de columnas de la grilla
	@returns un entero con la cantidad
	 */
	public int gety(){

		return y;	
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/*   getCell(int x, int y) retorna la celda indicada
	@param x es la fila de la celda
	@param y es la columna de la celda
	@returns una celda en la posicion x,y del tablero
	*/
	public Cell getCell(int x, int y){

		return grid[x][y];	

	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------
//Set

	/*   setCell(Cell c) setea la celda pasada como parametro
	@param c es la celda a setear
	*/
	public void setCell(Cell c){

		int x = c.getx();
		int y =  c.gety();
		grid[x][y] = c;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/*   incCant() incrementa en uno la cantidad de fichas dentro del tablero
	*/
	public void incCant(){

		cant++;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	/*   getCant() retorna la cantidad de fichas en el tablero
	@returns un entero con la cantidad de fichas
	*/
	public int getCant(){

		return cant;

	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/*   gridFull() retorna el estado del tablero
	@returns un booleano que indica si el tablero esta lleno o posee celdas libres
	*/
	public boolean gridFull(){

		return cant == (x+1)*(y+1);
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	public int getState(Cell c){

		int x = c.getx();
		int y = c.gety();
		return grid[x][y].getState();
	}


//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
 	public String returnCell(Cell c){
	
	    if (getState(c)==0)
	    	 return " bgcolor="+"\"#ffffff\""+">";

	    if (getState(c)==1)
	    	 return " bgcolor="+"\"#FF0000\""+">";
	    
	    return " bgcolor="+"\"#0004FF\""+">";
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------   
    
   	public String toStringTable(){
     	
	            String table ="<table align="+"center><tr>"+
		    
		    "<th><b><i>C0</i></b></th>" +
		    "<th><b><i>C1</i></b></th>" +
		    "<th><b><i>C2</i></b></th>" +
		    "<th><b><i>C3</i></b></th>" +
		    "<th><b><i>C4</i></b></th>" +
		    "<th><b><i>C5</i></b></th><tr>" +

	                "<td"+returnCell(getCell(0,0))+"&nbsp;</td>" +

	                "<td"+returnCell(getCell(0,1))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(0,2))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(0,3))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(0,4))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(0,5))+"&nbsp;</td>" +

	                "</tr>"+
	                
	                "<tr>" +		
	                "<td"+returnCell(getCell(1,0))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(1,1))+"&nbsp;</td>"+
	                "<td"+returnCell(getCell(1,2))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(1,3))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(1,4))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(1,5))+"&nbsp;</td>" +
	                "</tr>"+
	                "<tr>" +		
	                "<td"+returnCell(getCell(2,0))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(2,1))+"&nbsp;</td>"+
	                "<td"+returnCell(getCell(2,2))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(2,3))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(2,4))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(2,5))+"&nbsp;</td>" +
	                "</tr>"+
	                
	                "<tr>" +		
	                "<td"+returnCell(getCell(3,0))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(3,1))+"&nbsp;</td>"+
	                "<td"+returnCell(getCell(3,2))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(3,3))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(3,4))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(3,5))+"&nbsp;</td>" +
	                "</tr>"+
	                 "<tr>" +		
	                "<td"+returnCell(getCell(4,0))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(4,1))+"&nbsp;</td>"+
	                "<td"+returnCell(getCell(4,2))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(4,3))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(4,4))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(4,5))+"&nbsp;</td>" +
	                "</tr>"+
	                    "<tr>" +		
	                "<td"+returnCell(getCell(5,0))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(5,1))+"&nbsp;</td>"+
	                "<td"+returnCell(getCell(5,2))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(5,3))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(5,4))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(5,5))+"&nbsp;</td>" +
	                "</tr>"+
	                "<tr>" +		
	                "<td"+returnCell(getCell(6,0))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(6,1))+"&nbsp;</td>"+
	                "<td"+returnCell(getCell(6,2))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(6,3))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(6,4))+"&nbsp;</td>" +
	                "<td"+returnCell(getCell(6,5))+"&nbsp;</td>" +
	                "</tr></table>";

	            return table;
            }


}