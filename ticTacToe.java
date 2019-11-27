
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random; 

/**
 *
 * @author milus
 */

public class ticTacToe {
    
	private final char[][]gameBrd;
	private List<String> rows;
	private List<String> moves;
	private List<String> aiMoves;
	private int counter;
	private int randomMove;
	public static int TIME;
	private final char EMPTY='-';
	private final char PlayerChar='X';
	private final char OponentChar='O';
	private int SIZE=8;
	private int MaxMovesPos=32;
	private static final int maxDepth = 3;
	private static final int MIN=Integer.MIN_VALUE;
	private static final int MAX=Integer.MAX_VALUE;

public ticTacToe(){
	Random rand = new Random(); 
	moves=new ArrayList<>();
	aiMoves=new ArrayList<>();
	rows =  new ArrayList<>();
	gameBrd=new char[SIZE][SIZE];
	counter=0;
	randomMove=rand.nextInt(2)+1;
	setup();
	String s="";
	for(int i=0;i<MaxMovesPos;i++){
	moves.add(s);
	aiMoves.add(s);
	}
}
public  void setup(){
	for(int i=0;i<SIZE;i++)
	    for(int j=0;j<SIZE;j++){
	    gameBrd[i][j]=EMPTY;
	    }
}
public void printBoard(int firstM){
	rows.clear();
	String[] label={"A","B","C","D","E","F","G","H"};
	
	for(int i=0;i<SIZE;i++)  
	{   
	    if(i==0){
	    System.out.print("  1 2 3 4 5 6 7 8  ");
	    System.out.println(" Player vs. Oponent ");   
	    }
	    String row = "";   
	    for (int j = 0; j < SIZE; j++) {
	        if (j == 0) {
	            row = label[i]+" " + gameBrd[i][j] + " ";
	        }
	        else {
	            row += gameBrd[i][j] + " ";
	        }
	    }
	    if(row.length()%18==0){
	        rows.add(row);
	    }
	}
	for(int k=0;k<SIZE;k++){
	    if(firstM==0){
	        System.out.println(rows.get(k));
	    }else if(firstM==1){
	        System.out.println(rows.get(k)+"    "+moves.get(k)+"   "+aiMoves.get(k));
	    }else if(firstM==2)
	System.out.println(rows.get(k)+"    "+aiMoves.get(k)+"   "+moves.get(k));
	    }if(counter>8){
	    for(int k=SIZE;k<counter;k++)
	        if(firstM==1){
	       System.out.println("                      "+moves.get(k)+"   "+aiMoves.get(k));}
	        else if(firstM==2){
	            System.out.println("                      "+aiMoves.get(k)+"   "+moves.get(k));
	        }
	    }
}
public void GetUserMoves(int firstM){
	char rightChar=' ';
		if(firstM==1){
			rightChar=PlayerChar;
		}else if(firstM==2){
			rightChar=OponentChar;
			}
	Scanner in= new Scanner(System.in);
	String move = null;
	char []moveVal;
	char rowVal;
	char columnVal;
	int rowInt=0;
	int columnInt=0;
	boolean notWonLoop=true;
	while(notWonLoop){
	System.out.println("Choose Opponent’s next move");
	move=in.nextLine();
	moveVal = move.toCharArray();
	String s="";
	
	rowVal=moveVal[0];
	columnVal=moveVal[1];
	
	if (!Character.isDigit(rowVal) && Character.isDigit(columnVal)) {
	                columnInt = Integer.parseInt(columnVal + "") - 1;
	               
	    if (rowVal >= 65 && rowVal <= 90) {
	    rowInt = rowVal - 65;
	    }else if (rowVal >= 97 && rowVal <= 122) {
	    rowInt = rowVal - 97;
	    }
	if (rowInt >= 0 && rowInt <= 7 && columnInt >= 0 && columnInt <= 7) {
	    if (gameBrd[rowInt][columnInt] == EMPTY) {
	        notWonLoop = false;
	        if(firstM==1){
	        counter++;
	        s=Integer.toString(counter)+". "+moveVal[0]+moveVal[1];
	        }else{
	            s=""+moveVal[0]+moveVal[1];
	        }
	        
	        gameBrd[rowInt][columnInt] = rightChar; // Make move
	            if(WinCheck(rightChar)==true){
	                moves.set(counter-1,s+" win");
	                printBoard(firstM);
	                System.exit(0);
	            }else
	                moves.set(counter-1,s);
	            printBoard(firstM);
	                
	    }else {
	        System.out.println("Move already made.");
	    }
	}
	else {
	System.out.println("Out of bounds.");
		}
	}
		else {
	System.out.println("Invalid format. Please enter a valid move.");
			}
		}
}
public boolean stopTimer(int startTimer) {
	if(System.currentTimeMillis()-startTimer>=TIME) {
		return true;
		}	
		return false;
}
public void AImove(int firstM){
	int charElement;
	int bestPos = MIN, depth = maxDepth, currentP, posI = 0 , posJ = 0;
	
	for(int row = 0; row < SIZE; row++)
	{
	    for(int column = 0; column<SIZE; column++)
	    {
	        if(gameBrd[row][column]== EMPTY)
	        {
	        	if(gameBrd[randomMove][randomMove]==EMPTY) {
	        		gameBrd[row][column] = OponentChar;
	        		posI=randomMove;
	        		posJ=randomMove;
	        	}
	        	else
	            gameBrd[row][column] = OponentChar;
	            currentP = max(depth-1, MIN, MAX);
	            if(currentP > bestPos)
	            {
	                posI = row;
	                posJ = column;
	                bestPos = currentP;
	            } 
	            gameBrd[row][column] = EMPTY;
	        }
	    }
	}
	charElement = posI + 97; 
	char rightChar=' ';
		if(firstM==2){
			rightChar=PlayerChar;
		}else if(firstM==1){
			rightChar=OponentChar;
		}
	
	String s="";
	
	if(firstM==2){
		counter++;
		s=Integer.toString(counter)+". "+((char)(charElement)) + (posJ + 1);
	}else if(firstM==1){
		s=""+((char)(charElement)) + (posJ + 1);
	    }
	gameBrd[posI][posJ] = rightChar; // Make move
	    if(WinCheck(rightChar)==true){
	        aiMoves.set(counter-1,s+" win");
	        printBoard(firstM);
	        System.exit(0);
	
	    }else
	    aiMoves.set(counter-1,s);
	    printBoard(firstM);           
}  

public boolean WinCheck(char val){
    for (int i = 0; i <SIZE; i++)
    {
        for (int j = 0; j <=(SIZE/2); j++)
        {
            if (((gameBrd[i][j] == val) && 
                (gameBrd[i][j + 1] == val) && 
                (gameBrd[i][j + 2] == val) && 
                (gameBrd[i][j + 3] == val))||
                    (gameBrd[j][i] == val) && 
                (gameBrd[j + 1][i] == val) && 
                (gameBrd[j + 2][i] == val) && 
                (gameBrd[j + 3][i] == val))
                    return true;     
        }
    }
    return false;
}
public boolean Winner(){
	if(WinCheck(PlayerChar)==true || WinCheck(OponentChar)==true){
		return true;
	}else if(WinCheck(EMPTY)!=false)return false;
	else {
	    System.out.println("Draw");
	    return false;
	     }
	}
public int evaluate() {
	
	int points = 0;
	char Case1=OponentChar;
	char Case2=PlayerChar;
	
	// Check for win 4 in a row horizontally
	for (int row = 0; row < SIZE; row++) {
       for (int column = 0; column < 4; column++) {
    	   //OOOO||XXXX
           if ((gameBrd[row][column] == Case1) && (gameBrd[row][column + 1] == Case1) 
                   && (gameBrd[row][column + 2] == Case1) && 
                   (gameBrd[row][column + 3] == Case1)) {
        	   if(Case1==OponentChar) {
        		   points += 10000; // AI advantage
               }else if(Case1==PlayerChar) {
            	   points-=10000;
               }
           }
           //EOOOE
           else if ((gameBrd[row][column] == EMPTY) && (gameBrd[row][column + 1] == Case1) 
                   && (gameBrd[row][column + 2] == Case1) && 
                   (gameBrd[row][column + 3] == Case1) && (gameBrd[row][column + 4] == EMPTY)) {
        	   if(Case1==OponentChar) {
                   points += 2000; // AI advantage
               }else if(Case1==PlayerChar) {
            	   points-= 2000;
                   }
           }
           //EOOE||EXXE
           else if ((gameBrd[row][column] == EMPTY) && (gameBrd[row][column + 1] == Case1) 
                   && (gameBrd[row][column + 2] == Case1) && 
                   (gameBrd[row][column + 3] == EMPTY)) {
        	   if(Case1==OponentChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar) {
            	   points-= 1500;
                   } // Player advantage
           }//OOOE || EOOO
           else if ((gameBrd[row][column] == Case1) && (gameBrd[row][column + 1] == Case1) 
                   && (gameBrd[row][column + 2] == Case1)&&(gameBrd[row][column + 3] == EMPTY)||
                   (gameBrd[row][column] == EMPTY) && (gameBrd[row][column + 1] == Case1) 
                   && (gameBrd[row][column + 2] == Case1)&&(gameBrd[row][column + 3] == Case1)) {
        	   if(Case1==OponentChar) {
                   points += 1000; // AI advantage
               }else if(Case1==PlayerChar) {
            	   points-= 1000;
                   } 
           } 
       }
           
   }
   // Check for win 4 in a row vertically
	for (int row = 0; row < SIZE; row++) {
		   for (int column = 0; column < 4; column++) {
			   //XXXX||OOOO
		       if ((gameBrd[column][row] == Case1) && (gameBrd[column + 1][row] == Case1) 
		               && (gameBrd[column + 2][row] == Case1) && 
		               (gameBrd[column + 3][row] == Case1)) {
		    	   if(Case1==OponentChar) {
	                   points += 10000; // AI advantage
	               }else if(Case1==PlayerChar) {
	            	   points-= 10000;
	                   } // Player advantage
		       }//EOOOE||EXXXE
		       else  if ((gameBrd[column][row] == EMPTY) && (gameBrd[column + 1][row] == Case1) 
		               && (gameBrd[column + 2][row] == Case1) && 
		               (gameBrd[column + 3][row] == Case1) && (gameBrd[column + 4][row] == EMPTY)) {
		    	   if(Case1==OponentChar) {
	                   points += 2000; // AI advantage
	               }else if(Case1==PlayerChar) {
	            	   points-= 2000;
	                   }
		       }//EOOE||EXXE
		       else if ((gameBrd[column][row] == EMPTY) && (gameBrd[column + 1][row] == Case1) 
		               && (gameBrd[column + 2][row] == Case1) && 
		               (gameBrd[column + 3][row] == EMPTY)) {
		    	   if(Case1==OponentChar) {
	                   points += 1000; // AI advantage
	               }else if(Case1==PlayerChar) {
	            	   points-= 1000;
	                   }
		       }
		   }
   }
// Check for block advantage horizontally
	for (int row = 0; row < SIZE; row++) {
	   for (int column = 0; column < 5; column++) {
		   //OXXX||XOOO
	       if ((gameBrd[row][column] == Case1) && (gameBrd[row][column + 1] == Case2) 
	               && (gameBrd[row][column + 2] == Case2) && (gameBrd[row][column + 3] == Case2)) {
	    	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
	       }//XXXO||OOOX
	       else if ((gameBrd[row][column] == Case2) && (gameBrd[row][column + 1] == Case2) 
	               && (gameBrd[row][column + 2] == Case2) && (gameBrd[row][column + 3] == Case1)) {
	    	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 2500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 2500;
                   }
	       }//XOXX||OXOO
	       else if ((gameBrd[row][column] == Case2) && (gameBrd[row][column + 1] == Case1) 
	               && (gameBrd[row][column + 2] == Case2) && (gameBrd[row][column + 3] == Case2)) {
	    	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
	       }//XXOX||OOXO
	       else if ((gameBrd[row][column] == PlayerChar) && (gameBrd[row][column + 1] == PlayerChar) 
	               && (gameBrd[row][column + 2] == OponentChar) && (gameBrd[row][column + 3] == PlayerChar)) {
	    	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
	       }//OXXE || EXXO
	       else if ((gameBrd[row][column] == Case1) && (gameBrd[row][column + 1] == Case2) 
	               && (gameBrd[row][column + 2] == Case2)&& (gameBrd[row][column + 2] == EMPTY)||
	               (gameBrd[row][column] == EMPTY) && (gameBrd[row][column + 1] == Case2) 
	               && (gameBrd[row][column + 2] == Case2)&& (gameBrd[row][column + 2] == Case1)) {
	    	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
	       }//OXX||XXO
	       else if ((gameBrd[row][column] == Case1) && (gameBrd[row][column + 1] == Case2) 
	               && (gameBrd[row][column + 2] == Case2)||(gameBrd[row][column] == Case2) && (gameBrd[row][column + 1] == Case2) 
	               && (gameBrd[row][column + 2] == Case1)) {
	    	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1000; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1000;
                   }
	       }//EXOXE||EOXOE
	       else if ((gameBrd[row][column] == EMPTY) && (gameBrd[row][column + 1] == Case2) 
	               && (gameBrd[row][column + 2] == Case1) && (gameBrd[row][column + 3] == Case2)
	               && (gameBrd[row][column + 4] == EMPTY)) {
	    	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
	       }
	   	}
	 }

	// Check for block advantage vertically
	for (int row = 0; row < SIZE; row++) {
       for (int column = 0; column < 5; column++) {
  
          //XXXO||OOOX
          if ((gameBrd[column][row] == Case2) && (gameBrd[column + 1][row] == Case2) 
                   && (gameBrd[column + 2][row] == Case2) && (gameBrd[column + 3][row] == Case1) ||
                   (gameBrd[column][row] == Case1) && (gameBrd[column + 1][row] == Case2) 
                   && (gameBrd[column + 2][row] == Case2) && (gameBrd[column + 3][row] == Case2)) {
        	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 2500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 2500;
                   }
          }//OXX||XOO
	       else if ((gameBrd[column][row] == Case1) && (gameBrd[column + 1][row] == Case2) 
	               && (gameBrd[column + 2][row] == Case2)) {
	    	   if(Case1==OponentChar && Case2==PlayerChar) {
	               points += 1000; // AI advantage
	           }else if(Case1==PlayerChar && Case2==OponentChar) {
	        	   points-= 1000;
	               }
	       }//XOXX||OXOO
           else if ((gameBrd[column][row] == Case2) && (gameBrd[column + 1][row] == Case1) 
                   && (gameBrd[column + 2][row] == Case2) && (gameBrd[column + 3][row] == Case2)) {
        	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
           }//XXOX||OOXO
           else if ((gameBrd[column][row] == Case2) && (gameBrd[column + 1][row] == Case2) 
                   &&(gameBrd[column + 2][row] == Case1) && (gameBrd[column + 3][row] == Case2)) {
        	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
           }//EXOXE||EOXOE
           else if ((gameBrd[column][row] == EMPTY) && (gameBrd[column + 1][row] == Case2) 
                   &&(gameBrd[column + 2][row] == Case1) && (gameBrd[column + 3][row] == Case2)
                   && (gameBrd[column + 4][row] == EMPTY)) {
        	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
           }//EXXOE||EOOXE
           else if ((gameBrd[column][row] == EMPTY) && (gameBrd[column + 1][row] == Case2) 
                   &&(gameBrd[column + 2][row] == Case2) && (gameBrd[column + 3][row] == Case1)
                   && (gameBrd[column + 4][row] == EMPTY)) {
        	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
           }//OXOXE||XOXOE
           else if ((gameBrd[column][row] == Case1) && (gameBrd[column + 1][row] == Case2) 
                   &&(gameBrd[column + 2][row] == Case1) && (gameBrd[column + 3][row] == Case2)
                   && (gameBrd[column + 4][row] == EMPTY)||(gameBrd[column][row] == EMPTY) && (gameBrd[column + 1][row] == Case2) 
                   &&(gameBrd[column + 2][row] == Case1) && (gameBrd[column + 3][row] == Case2)
                   && (gameBrd[column + 4][row] == Case1)) {
        	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
           }//EOXX||EXOO
           else if ((gameBrd[column][row] == EMPTY) && (gameBrd[column + 1][row] == Case2) 
                   &&(gameBrd[column + 2][row] == Case2) &&(gameBrd[column + 3][row] == Case2)||
                   (gameBrd[column][row] == Case2) && (gameBrd[column + 1][row] == Case2) 
                   &&(gameBrd[column + 2][row] == Case1) &&(gameBrd[column + 3][row] == EMPTY)) {
        	   if(Case1==OponentChar && Case2==PlayerChar) {
                   points += 1500; // AI advantage
               }else if(Case1==PlayerChar && Case2==OponentChar) {
            	   points-= 1500;
                   }
           }
       }
   }   
	return points;
}
public int min(int depth, int alpha, int beta){
	int bestPos = MAX, currentS;
	if (Winner() != true);
	if (depth == 0 || stopTimer(TIME)) 
	        return (evaluate());
	    for(int i = 0; i < SIZE; i++)
	    {
	        for(int j = 0; j<SIZE; j++)
	        {
	            if(gameBrd[i][j]== EMPTY)
	            {
	                gameBrd[i][j] = OponentChar;
	                currentS = Math.min(bestPos,max(depth - 1,alpha,beta));
	                if(currentS < bestPos)
	                {
	                    bestPos = currentS;
	                }
	                gameBrd[i][j] = EMPTY;
	            }                
	        }
	    }
    return bestPos;
}

public int max(int depth, int alpha, int beta){
	int bestPos = MIN, currentS;
	if (Winner() != true) ;
	if (depth == 0 || stopTimer(TIME)) 
	        return (evaluate());
	    for(int i = 0; i < SIZE; i++)
	    {
	        for(int j = 0; j<SIZE; j++)
	        {
	            if(gameBrd[i][j]== EMPTY)
	            {
	                gameBrd[i][j] = OponentChar;
	                currentS = Math.max(bestPos,min(depth - 1,alpha,beta));
	                if(currentS > bestPos)
	                {
	                    bestPos = currentS;
	                }
	                gameBrd[i][j] = EMPTY;
	            }
	        }
	    }
    return bestPos;
}

public static int InputValidation(int validationOne,int validationTwo) {
    int number;
    Scanner sc=new Scanner(System.in);
    do {
        while (!sc.hasNextInt()) {
            System.out.println("That's not a number!");
            sc.next();
        }
        number = sc.nextInt();
    } while (number <validationOne || number>validationTwo);
    return number;
}
/**
* @param args the command line arguments
*/
public static void main(String[] args) {
    Scanner kb=new Scanner(System.in);
    ticTacToe game=new ticTacToe();
    game.setup();
    int m=0;
    System.out.print("Please enter the cut off time for AI in seconds(1 to 18): ");
    int timing= InputValidation(1,18);
    System.out.println("");
    game.printBoard(m);
    System.out.println("\nPress 1 for for player to start first, 2 for AI");
    System.out.print("-> ");int firstMove=InputValidation(1,2);
    do{
        if(firstMove==1){
    game.GetUserMoves(firstMove);
    try {
		Thread.sleep(timing * 1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    game.AImove(firstMove);
        }else if(firstMove==2){
        	try {
        		Thread.sleep(timing * 1000);
        	} catch (InterruptedException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
    game.AImove(firstMove);
    game.GetUserMoves(firstMove);
        	}
       }while(game.Winner()==false);
    // TODO code application logic here
    kb.close();
    }

}
