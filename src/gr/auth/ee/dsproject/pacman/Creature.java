package gr.auth.ee.dsproject.pacman;

/*Alexandros Petridis AEM:9288, al1998petidis@gmail.com, 6949390540
 * Paulos Mpeltes AEM:9053, mpeltesp@ece.auth.gr, 6979291208
 */
public class Creature implements gr.auth.ee.dsproject.pacman.AbstractCreature
{

  public String getName ()
  {
    return "Mine";
  }

  private int step = 1;
  private boolean amPrey;

  public Creature (boolean isPrey)
  {
    amPrey = isPrey;

  }

  public int calculateNextPacmanPosition (Room[][] Maze, int[] currPosition)
  {

    int newDirection = -1;

    while (newDirection == -1) {

      int temp_dir = (int) (4 * Math.random());

      if (Maze[currPosition[0]][currPosition[1]].walls[temp_dir] == 1) {
        newDirection = temp_dir;
      }

    }
    step++;

    return newDirection;
  }
//oles oi epeksigiseis dinontai stin hlektroniki anafora.
  public int[] calculateNextGhostPosition (Room[][] Maze, int[][] currentPos)
  {

int[] ghostMoves = new int[PacmanUtilities.numberOfGhosts];	
	  
	  for(int i=0; i<PacmanUtilities.numberOfGhosts; i++)
		  ghostMoves[i] = -1;	  
	  if(step < PacmanUtilities.stepLimit) {	
		  for (int i=0; i<PacmanUtilities.numberOfGhosts; i++) {
			  while(ghostMoves[i] == -1) {
				  int temp_move = (int)(4*Math.random());
				  if (Maze[currentPos[i][0]][currentPos[i][1]].walls[temp_move] == 1)
					  ghostMoves[i] = temp_move;				  
			  }
		  }
		  
		  boolean[] a = checkCollision(ghostMoves, currentPos);
		  for(int i = 1; i<a.length; i++) {
			  int  temp_past = ghostMoves[i];
			  while(a[i]) {
				  int temp_move1 = (int)(4*Math.random());
				  while ((temp_move1 == ghostMoves[i]) || (temp_move1 == temp_past) || (Maze[currentPos[i][0]][currentPos[i][1]].walls[temp_move1] == 0)) 
					  temp_move1 = (int)(4*Math.random());
				  ghostMoves[i] = temp_move1;
				  a = checkCollision(ghostMoves, currentPos);
			  }			  
		  }
	  }
	  return ghostMoves;

  }

  public boolean[] checkCollision (int[] moves, int[][] currentPos)
  {
    boolean[] collision = new boolean[PacmanUtilities.numberOfGhosts];

    int[][] newPos = new int[4][2];

    for (int i = 0; i < moves.length; i++) {

      if (moves[i] == 0) {
        newPos[i][0] = currentPos[i][0];
        newPos[i][1] = currentPos[i][1] - 1;
      } else if (moves[i] == 1) {
        newPos[i][0] = currentPos[i][0] + 1;
        newPos[i][1] = currentPos[i][1];
      } else if (moves[i] == 2) {
        newPos[i][0] = currentPos[i][0];
        newPos[i][1] = currentPos[i][1] + 1;
      } else {
        newPos[i][0] = currentPos[i][0] - 1;
        newPos[i][1] = currentPos[i][1];
      }

      collision[i] = false;
    }

    for (int k = 0; k < moves.length; k++) {
    	//System.out.println("Ghost " + k + " new Position is (" + newPos[k][0] + "," + newPos[k][1] + ").");
    }

    for (int i = 0; i < moves.length; i++) {
      for (int j = i + 1; j < moves.length; j++) {
        if (newPos[i][0] == newPos[j][0] && newPos[i][1] == newPos[j][1]) {
          //System.out.println("Ghosts " + i + " and " + j + " are colliding");
          collision[j] = true;
        }

        if (newPos[i][0] == currentPos[j][0] && newPos[i][1] == currentPos[j][1] && newPos[j][0] == currentPos[i][0] && newPos[j][1] == currentPos[i][1]) {
          //System.out.println("Ghosts " + i + " and " + j + " are colliding");
          collision[j] = true;
        }

      }

    }
    return collision;
  }

}
