//Name: Devon Guinane
//Solves Sudoku puzzles using recursive backtracking

public class SudokuSolver 
{
	int backtracks = 0;
	
	public static void main(String[] args)
	{
		SudokuSolver solver = new SudokuSolver();
		
		//create a solved sudoku puzzle
		Sudoku s = new Sudoku();
		String[] boardValues = {"530070000", "600195000", "098000060", "800060003", "400803001", "700020006", "060000280", "000419005", "000080079"};
		s.fill(boardValues);
		
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				solver.Solve(s, i, j);
			}
		}
			
		s.show();
		System.out.println("\n" + s.isSolution(s.board) + ": is solution");
		System.out.println("Number of backtracks: " + solver.backtracks);
	}
	
	//solve the sudoku
	public boolean Solve(Sudoku s, int row, int col)
	{	
		//if board is full, sudoku is successfully solved(sudokuSolver only places values if it is a legal move)
		if(boardIsFull(s, row, col))
		{
			return true;
		}
		
		//for each number value 1-9
		for(int val = 1; val <= 9; val++)
		{
			if(moveIsValid(s, row, col, val))
			{
				//place value on board
				s.setSquare(row, col, val);

				int[] nextSquare = findNextEmpty(s, row, col);
				int nextX = nextSquare[0];
				int nextY = nextSquare[1];
				
				//if solved, then stop
				if(Solve(s, nextX, nextY))
				{
					return true;
				}
				
				//undo move and try again
				s.setSquare(row, col, 0);
				this.backtracks += 1;
				
			}
		}

		//backtrack
		return false;
	}

	//find next empty square in the board to see where the next spot is to start placing values
	private int[] findNextEmpty(Sudoku s, int row, int col) 
	{
		int[] square = new int[2]; //holds an x,y coordinate for next empty square

		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				//if square is empty
				if(s.board[i][j] == 0)
				{
					square[0] = i;
					square[1] = j;
					return square;
				}
			}
		}

		return square;	
	}

	private boolean moveIsValid(Sudoku s, int row, int col, int val)
	{
		//if there is already a number in the square
		if(isOccupied(s, row, col))
		{ 
			return false;
		}
		
		//if row will have duplicates by placing val in the square
		if(rowHasDuplicates(s, row, val))
		{
			return false;
		}
		
		//if col will have duplicates by placing val in the square
		if(colHasDuplicates(s, col, val))
		{
			return false;
		}
		
		//if section will have duplicates by placing val in the square
		if(sectionHasDuplicates(s, row, col, val))
		{
			return false;
		}
		
		//System.out.println(row + ", " + col + val + " true");
		
		return true;
	}
	
	//check for duplicate values in a section (it figures out the section by passing row and col to it)
	private boolean sectionHasDuplicates(Sudoku s, int row, int col, int val)
	{
		int[] values = new int[10]; //an array to test for duplicates
		
		int x, y;
		x = row / 3;
		y = col / 3;
		
		int section = (3 * x) + y;	                   
		
		int topLeftX = section - (section % 3); //x-coordinate of top left square in section
		int topLeftY = (section % 3) * s.N;     //y-coordinate of top left square in section

		//for each row in section
		for(int i = topLeftX; i < topLeftX + s.N; i++)
		{
			for(int j = topLeftY; j < topLeftY + s.N; j++)
			{
				int squareVal = s.board[i][j];
				
				//for each slot in values array
				for(int k = 0; k < values.length; k++)
				{
					if(values[k] == val)
					{
						return true;
					}
					else
					{
						values[k] = squareVal;
					}
				}
			}
		}
		
		return false;
	}

	//check if a square contains a value besides 0
	private boolean isOccupied(Sudoku s, int row, int col) 
	{
		if(s.board[row][col] == 0)
		{
			return false;
		}
		return true;
	}

	//check for duplicate values in row
	private boolean rowHasDuplicates(Sudoku s, int row, int val)
	{
		int[] values = new int[10];
		
		int stop = row + 1;
		
		for(int i = row; i < stop; i++)
		{
			//for each number in row
			for(int j = 0; j < s.N * s.N; j++)
			{
				int squareVal = s.board[i][j];
				
				//for each slot in values array
				for(int k = 0; k < values.length; k++)
				{
					if(values[k] == val)
					{
						return true;
					}
					else
					{
						values[k] = squareVal;
					}
				}
				
			}
		}
		return false;
	}

	//check for duplicates in column
	private boolean colHasDuplicates(Sudoku s, int col, int val)
	{
		int[] values = new int[9];
		
		int stop = col + 1;
		
		for(int i = col; i < stop; i++)
		{
			//for each number in col
			for(int j = 0; j < s.N * s.N; j++)
			{
				int squareVal = s.board[j][i];
				
				//for each slot in values array
				for(int k = 0; k < 9; k++)
				{
					if(values[k] == val)
					{
						return true;
					}
					else
					{
						values[k] = squareVal;
					}
				}
				
			}
		}
		return false;
	}
	
	//check to see if the board is totally filled in
	private boolean boardIsFull(Sudoku s, int row, int col)
	{
		int size = s.N * s.N;
		
		for(int i = row; i < size; i++)
		{
			for(int j = col; j < size; j++)
			{
				if(s.board[i][j] == 0)
				{
					return false;
				}
			}
		}

		return true;
	}

}
