//Name: Devon Guinane
//creates a Sudoku board with width and height of N*N
//can verify if a sudoku is solved or not


import java.util.ArrayList;
import java.util.Random;

public class Sudoku 
{
	protected final int N = 3; //size of sections on board(3x3) = 9x9 board
	protected int[][] board;
	
	//creates Sudoku board of size N*N and initializes all cells to 0(empty)
	public Sudoku()
	{
		int size = N * N; //width and height of board
		
		this.board = new int[size][size];
		
		//initialize array slots to 0
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				this.setSquare(i, j, 0);
			}
		}
	}
	
	//sets a square on the board to a value
	public void setSquare(int i, int j, int val)
	{
		this.board[i][j] = val;
	}
	
	//gets the value of a particular square on the board
	public int getSquare(int i, int j)
	{
		return this.board[i][j];
	}
	
	//populates the grid with numbers
	public void fill(String[] lines)
	{
		//for each string in lines array
		for(int i = 0; i < lines.length; i++)
		{		
			//for each character in string
			for(int j = 0; j < lines[i].length(); j++)
			{
				//get each char in string
				char c = lines[i].charAt(j);
				
				int num = Character.digit(c, 10);

				setSquare(i, j, num);
			}
		}
	}
	
	public void fancyFill(String[] lines)
	{
		//for each string in lines array
		for(int i = 0; i < lines.length; i++)
		{		
			//for each character in string
			for(int j = 0; j < lines[i].length(); j++)
			{
				//get each char in string
				char c = lines[i].charAt(j);
				
				if(c == '|' || c == '+' || c == '-' || c == ' ')
				{
					continue;
				}
				else
				{
					int num = Character.digit(c, 10);

					setSquare(i, j, num);
				}
			}
		}
	}
	
	//encode each row, col, and section into 9 character string 
	private ArrayList<String> makeCollection(int[][] board)
	{
		ArrayList<String> collection = new ArrayList<String>();
		
		//for each row in board
		for(int i = 0; i < board.length; i++)
		{
			//encode columns into string format
			String col ="";
			int z = i;
			for(int y = 0; y < board[i].length; y++)
			{
				col += board[y][z];	
			}
			
			collection.add(col);
			
			//encode rows into string format
			String row = "";
			
			//for each column in board
			for(int j = 0; j < board[i].length; j++)
			{
				row += board[i][j];
			}
			
			collection.add(row);
		}
		
		collection = makeSections(board, collection);
		
		return collection;
	}
	
	//encode 3x3 sections into 9 character string
	private ArrayList<String> makeSections(int[][] board, ArrayList<String> collection)
	{
		int xOffset = 3;
		int yOffset = 3;
		int startX = 0;
		int startY = 0;
		int count = 0;
		
			//for each row of sections
			for(int i = 0; i < 3; i++)
			{
				//for each column of sections
				for(int f = 0; f < 3; f++)
				{
					String section = "";
					//for each row in section
					for(int x = startX; x < xOffset; x++)
					{
						//for each column in section
						for(int j = startY; j < yOffset; j++)
						{
							section += board[x][j];
						}		
					}
					
					collection.add(section);
					startY += 3;
					yOffset += 3;
				}
				startX += 3;
				xOffset += 3;
				yOffset = 3;
				startY = 0;
			}
		return collection;
	}
	
	//check to see if a Sudoku board is solved
	public boolean isSolution(int[][] board)
	{
		
		ArrayList<String> collection = makeCollection(board);
		//for each string
		for(int i = 0; i < collection.size(); i++)
		{
			//for each character in string
			for(int j = 0; j < collection.get(i).length(); j++)
			{
				//get each character in string
				char c = collection.get(i).charAt(j);
				
				//if there is a square with a 0, then board isn't filled in completely
				if(c == '0')
				{
					return false;
				}
			}

			//if a row, col, or section has duplicates, return false
			if(hasDuplicates(collection.get(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	//check row, column, or section encoded string for duplicates
	private boolean hasDuplicates(String value)
	{
		char[] values = {'0', '0', '0', '0', '0', '0', '0', '0', '0'};
		
		//for each character in string
		for(int i = 0; i < value.length(); i++)
		{
			//get each character in string
			char c = value.charAt(i);

			for(int j = 0; j < values.length; j++)
			{
				//System.out.println("VALUES: " + values[j]);
				if(values[j] == c)
				{
					return true;
				}
			}
			
			values[i] = c;
		}
		return false;
		
	}
	
	//display Sudoku board
	public void show()
	{
		for(int i = 0; i < this.board.length; i++)
		{ 
			for(int j = 0; j < this.board[i].length; j++)
			{
				int value = this.getSquare(i, j);
				if(value == 0)
				{
					System.out.print(".");
				}
				else
				{
					System.out.print(value);
				}
				
				//separate blocks with |
				if((j + 1) % 3 == 0)
				{
					System.out.print("|");
				}
			}
			
			System.out.println("");
			if( i < 8)
			{
				if((i + 1) % 3 == 0)
				{
					for(int x = 0; x < 9; x++)
					{
						//create horizontal lines
						System.out.print("-");
						if((x +1 ) % 3 == 0)
						{
							if(x < 8)
							{
								System.out.print("+");
							}
						}
					}
					System.out.println("");
				}
			}
		}
	}
	
	public static void main(String[] args) 
	{
		//create an unsolved sudoku puzzle
		Sudoku s1 = new Sudoku();
		
		//create test board with a little less than half of the squares filled with random numbers
		Random r = new Random();
		for(int i = 0; i < s1.board.length; i++)
		{
			for(int j = 0; j < s1.board[i].length; j++)
			{
				int randInt = r.nextInt(2);
				if(randInt == 1)
				{
					s1.setSquare(i, j, r.nextInt(10));
				}
			}
		}
		
		s1.show();
		System.out.println(s1.isSolution(s1.board) + " : is not a solution");
		
		//create a solved sudoku puzzle
		Sudoku s2 = new Sudoku();
		String[] boardValues = {"435269781", "682571493", "197834562", "826195347", "374682915", "951743628", "519326874", "248957136", "763418259"};
		s2.fancyFill(boardValues);
		
		System.out.println("");
		s2.show();
		System.out.println(s2.isSolution(s2.board) + " : is solution");
	}
}
