// Names:
// x500s:
//Victor Wedden WEDDE015
import java.lang.reflect.GenericDeclaration;
import java.util.Random;
import java.util.Scanner;

public class MyMaze{
    Cell[][] maze;
    int startRow;
    int endRow;

    public MyMaze(int rows, int cols, int startRow, int endRow) throws InvalidArgumentException{

        if (rows < 5 || rows > 20 || cols < 5 || cols > 20)
        {
            throw new InvalidArgumentException("MyMaze()"); //Invalid arguments
        }
        else {
            this.maze = new Cell[rows][cols];

            for ( int i = 0; i < rows; i++) {
                for ( int j = 0; j < cols; j++) {
                    this.maze[i][j] = new Cell();
                }
            }

            this.startRow = startRow;
            this.endRow = endRow;
        }


    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols, int startRow, int endRow) {

        try {
            MyMaze myMaze = new MyMaze(rows, cols, startRow, endRow);

            //Create a stack and initialize it with start
            Stack1Gen<int[]> mazeStack = new Stack1Gen<>();
            myMaze.maze[startRow][0].setVisited(true);
            int[] start = {startRow,0};
            mazeStack.push(start);

            //Generate a random integer [0-3]
            Random rand = new Random();

            while (!allVisited(myMaze))
            {
                int randomInt = rand.nextInt(4);
                int[] top = mazeStack.top();

                if ( hasUnvisitedNeighbors(myMaze, top)) {
                    switch (randomInt) {
                        case 0: //Left
                            if (top[1] > 0) {
                                if (!myMaze.maze[top[0]][top[1] - 1].getVisited()) {
                                    int[] next = {top[0], top[1] - 1};
                                    mazeStack.push(next);
                                    myMaze.maze[top[0]][top[1] - 1].setVisited(true);
                                    myMaze.maze[top[0]][top[1] - 1].setRight(false);
                                }
                            }
                            break;
                        case 1: //Up
                            if (top[0] > 0) {
                                if (!myMaze.maze[top[0] - 1][top[1]].getVisited()) {
                                    int[] next = {top[0] - 1, top[1]};
                                    mazeStack.push(next);
                                    myMaze.maze[top[0] - 1][top[1]].setVisited(true);
                                    myMaze.maze[top[0] - 1][top[1]].setBottom(false);
                                }
                            }
                            break;
                        case 2: //Right
                            if (top[1] < myMaze.maze[0].length - 1) {
                                if (!myMaze.maze[top[0]][top[1] + 1].getVisited()) {
                                    int[] next = {top[0], top[1] + 1};
                                    mazeStack.push(next);
                                    myMaze.maze[top[0]][top[1] + 1].setVisited(true);
                                    myMaze.maze[top[0]][top[1]].setRight(false);
                                }
                            }
                            break;
                        case 3: //Down
                            if (top[0] < myMaze.maze.length - 1) {
                                if (!myMaze.maze[top[0] + 1][top[1]].getVisited()) {
                                    int[] next = {top[0] + 1, top[1]};
                                    mazeStack.push(next);
                                    myMaze.maze[top[0] + 1][top[1]].setVisited(true);
                                    myMaze.maze[top[0]][top[1]].setBottom(false);
                                }
                            }
                            break;
                    }
                }
                else {
                    if ( mazeStack.top() != null) {
                        mazeStack.pop();
                    }
                    else {
                        break;
                    }
                }


            }

            //Set all cells to not visited
            for ( int i = 0; i < myMaze.maze.length; i++)
            {
                for ( int j = 0 ; j < myMaze.maze[0].length; j++)
                {
                    myMaze.maze[i][j].setVisited(false);
                }
            }

            System.out.println("Empty maze:");
            myMaze.printMaze();

            return myMaze;

        }
        catch (InvalidArgumentException e )
        {
            System.out.println(e);
            return null;
        }

    }

    private static boolean hasUnvisitedNeighbors(MyMaze maze, int[] top) {

        if ( top[0] < maze.maze.length - 1 ) // Check down
        {
            if (!maze.maze[top[0]+1][top[1]].getVisited()){
               return true;
            }
        }

        if (top[1] > 0 ) // Check left
        {
            if ( !maze.maze[top[0]][top[1] - 1].getVisited() )
            {
                return true;
            }
        }

        if ( top[0] > 0 ) //check up
        {
            if (!maze.maze[top[0]-1][top[1]].getVisited()){
                return true;
            }
        }


        if ( top[1] < maze.maze[0].length - 1 ) // check right
        {
            if (!maze.maze[top[0]][top[1]+1].getVisited()){
                return true;
            }
        }

        return false;

    }

    public static boolean allVisited(MyMaze maze){
        boolean result = true;

        for ( int i = 0; i < maze.maze.length; i++) {
            for ( int j = 0; j < maze.maze[0].length; j++) {
                if (!maze.maze[i][j].getVisited())
                {
                    return false;
                }
            }
        }

        return result;

    }

    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze() {

        for ( int i = 0; i <= maze[0].length; i++ ) // Build top frame
        {
            if ( i > 0 )
            {
                System.out.print("---|");
            }
            else{
                System.out.print("|");
            }
        }
        System.out.println();

        for ( int i = 0; i < maze.length; i++ ) // Build middle
        {
            if (i == startRow) {
                System.out.print(" ");
            } else {
                System.out.print("|");
            }


            for (int j = 0; j < maze[0].length; j++)
            {
                if (j > 0) {
                    if (maze[i][j].getRight() && maze[i][j - 1].getRight()) {
                        if (maze[i][j].getVisited()) {
                            System.out.print("| * ");
                        }
                        else {
                            System.out.print("|   ");
                        }
                    }
                    else if (maze[i][j].getRight()) {
                        if (maze[i][j].getVisited()) {
                            System.out.print(" * ");
                        }
                        else {
                            System.out.print("   ");
                        }

                    }
                    else if (maze[i][j - 1].getRight()) {
                        if (maze[i][j].getVisited()) {
                            System.out.print("| *  ");
                        }
                        else {
                            System.out.print("|    ");
                        }
                    }
                    else {
                        if (maze[i][j].getVisited()) {
                            System.out.print(" *  ");
                        }
                        else {
                            System.out.print("    ");

                        }
                    }
                }
                else
                {
                    if (maze[i][j].getRight())
                    {
                        if ( maze[i][j].getVisited())
                        {
                            System.out.print(" * ");
                        }
                        else
                        {
                            System.out.print("   ");
                        }
                    }
                    else
                    {
                        if ( maze[i][j].getVisited())
                        {
                            System.out.print(" *  ");
                        }
                        else
                        {
                            System.out.print("    ");
                        }

                    }
                }

            }

            if ( i == endRow )
            {
                System.out.print(" ");
            }
            else
            {
                System.out.print("|");
            }

            System.out.println();
            System.out.print("|");
            for (int j = 0; j < maze[0].length; j++) {//Draw bottom boundaries and lower frame
                if (maze[i][j].getBottom())
                {
                    System.out.print("---|");
                }
                else
                {
                    System.out.print("   |");
                }
            }
            System.out.println();


        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {

        Q1Gen<int[]> mazeQueue = new Q1Gen<>();
        int[] start = {startRow,0};
        mazeQueue.add(start);

        while ( mazeQueue.length() > 0)
        {
            int[] currentIndex = mazeQueue.remove();
            this.maze[currentIndex[0]][currentIndex[1]].setVisited(true);

            if (currentIndex[0] == endRow && currentIndex[1] == this.maze[0].length - 1)
            {
                break;
            }

            //enqueue unvisited neighbors
            if ( currentIndex[0] < this.maze.length - 1 ) // Check down
            {
                if (!this.maze[currentIndex[0]+1][currentIndex[1]].getVisited()){
                    int[] next = {currentIndex[0]+1, currentIndex[1]};
                    mazeQueue.add(next);
                }
            }

            if (currentIndex[1] > 0 ) // Check left
            {
                if ( !this.maze[currentIndex[0]][currentIndex[1] - 1].getVisited() )
                {
                    int[] next = {currentIndex[0], currentIndex[1] - 1};
                    mazeQueue.add(next);
                }
            }

            if ( currentIndex[0] > 0 ) //check up
            {
                if (!this.maze[currentIndex[0]-1][currentIndex[1]].getVisited()){
                    int[] next = {currentIndex[0]-1, currentIndex[1]};
                    mazeQueue.add(next);
                }
            }


            if ( currentIndex[1] < this.maze[0].length - 1 ) // check right
            {
                if (!this.maze[currentIndex[0]][currentIndex[1]+1].getVisited()){
                    int[] next = {currentIndex[0], currentIndex[1] + 1};
                    mazeQueue.add(next);
                }
            }

        }

        System.out.println("Solved maze:");
        printMaze();

    }

    public static void main(String[] args){
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a number of rows for the maze:");
        int rows = scanner.nextInt();
        System.out.println("Please enter a number of columns for the maze:");
        int cols = scanner.nextInt();

        int startRow = rand.nextInt(rows); // generates a random in bound startRow
        int endRow = rand.nextInt((rows - startRow )) + startRow; //generates a random in bound endRow greater than startRow



//        for ( int i = 0; i < 10; i++) // Tests random number generation
//        {
//            startRow = rand.nextInt(rows); // generates a random in bound startRow
//            endRow = rand.nextInt((rows - startRow )) + startRow; //generates a random in bound endRow greater than startRow
//
//            System.out.println("Start Row " + i + ": " + startRow);
//            System.out.println("End Row " + i + ": " + endRow);
//        }

        MyMaze maze = makeMaze(rows,cols,startRow, endRow);


        assert maze != null;
        maze.solveMaze();





    }
}
