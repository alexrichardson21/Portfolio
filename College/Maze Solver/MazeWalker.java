import java.util.Arrays;
/**
 * MazeWalker is the object that is responsible for staking out a path down some
 * maze. Given a 2D array of maze cells and a starting location, it calculates
 * the next "legal" move such that the walker can eventually cover every square
 * in the maze that is reachable from that starting location.
 */
public class MazeWalker {
    /**
     * The possible states of the current "walk" through a maze.
     */
    public enum WalkerState {
        /**
         * Indicates that the maze walker has reached its assigned destination.
         */
        THERE_ALREADY,

        /**
         * Indicates that the maze walker has concluded that it is impossible to
         * reach its destination.
         */
        IMPOSSIBLE_TO_GET_THERE,

        /**
         * Indicates that the maze walker would like to move left.
         */
        MOVE_LEFT,

        /**
         * Indicates that the maze walker would like to move up.
         */
        MOVE_UP,

        /**
         * Indicates that the maze walker would like to move right.
         */
        MOVE_RIGHT,

        /**
         * Indicates that the maze walker would like to move down.
         */
        MOVE_DOWN
    }

    /**
     * Initializes the MazeWalker, providing it with the maze to use and the
     * walker's destination.
     */
    public MazeWalker(Maze maze, int destinationX, int destinationY, boolean smart) {
        this.maze = maze;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.smart = smart;

        // The path stack starts out empty.
        path = new WalkerState[this.maze.getMazeWidth() * this.maze.getMazeHeight()];
        pathIndex = -1;
        moveTally = 0;

        // The "been-there" array starts off completely clear.
        beenThere = new boolean[this.maze.getMazeHeight()][this.maze.getMazeWidth()];
        for (int row = 0; row < beenThere.length; row++) {
            for (int column = 0; column < beenThere[row].length; column++) {
                beenThere[row][column] = false;
            }
        }
    }

    /**
     * Takes a step toward reaching the given destination from the given current
     * location, and returns either the direction of the next step, whether or
     * not that destination has been reached, or whether that destination is
     * impossible to reach.
     */
    public WalkerState areWeThereYet(int currentX, int currentY) {
        // Implement me!


//----------------------------SMART------------------------------

        if (this.smart == true){
            beenThere[currentY][currentX] = true;

            WalkerState[] directions = new WalkerState[4];
            directions[0] = WalkerState.MOVE_LEFT;
            directions[1] = WalkerState.MOVE_RIGHT;
            directions[2] = WalkerState.MOVE_UP;
            directions[3] = WalkerState.MOVE_RIGHT;


            // If there already
            if (currentX == destinationX && currentY == destinationY) {
                return WalkerState.THERE_ALREADY;
            }

            //Determines direction towards sword from adjacent spots
            double currentDist = Math.sqrt(Math.pow(destinationX - currentX, 2) + Math.pow(destinationY - currentY, 2));
            double leftDist = Math.sqrt(Math.pow(destinationX - (currentX - 1), 2) + Math.pow(destinationY - currentY, 2));
            double rightDist = Math.sqrt(Math.pow(destinationX - (currentX + 1), 2) + Math.pow(destinationY - currentY, 2));
            double upDist = Math.sqrt(Math.pow(destinationX - currentX, 2) + Math.pow(destinationY - (currentY - 1), 2));
            double downDist = Math.sqrt(Math.pow(destinationX - currentX, 2) + Math.pow(destinationY - (currentY + 1), 2));


            double[] distances = new double[4];
            distances[0] = leftDist;
            distances[1] = rightDist;
            distances[2] = upDist;
            distances[3] = downDist;
            for (double distance: distances) {
                System.out.println(distance);
            }
            /*for (int i = 0; i < distances.length; i++) {
                for (int j = 0; j < distances.length; j++) {
                    if (j != i) {
                        if (distances[i] < distances[j]) {
                            double tempDist = distances[i];
                            distances[i] = distances[j];
                            distances[j] = tempDist;

                            WalkerState tempState = directions[i];
                            directions[i] = directions[j];
                            directions[j] = tempState;
                        }
                    }
                }
            }
            /*
            for (int i = 0; i < distances.length; i++) {
                if (i > 0){
                    if (distances[i] == leftDist) {
                        int count = 0;
                        for (int j = 1; j <= i; j++) {
                            if (directions[i - j] == WalkerState.MOVE_LEFT) {
                                count++;
                            }
                        }
                        if (count == 0){
                            directions[i] = WalkerState.MOVE_LEFT;
                        }
                    } if (distances[i] == rightDist) {
                        int count = 0;
                        for (int j = 1; j <= i; j++) {
                            if (directions[i - j] == WalkerState.MOVE_RIGHT) {
                                count++;
                            }
                        }
                        if (count == 0){
                            directions[i] = WalkerState.MOVE_RIGHT;
                        }
                        directions[i] = WalkerState.MOVE_RIGHT;
                    } if (distances[i] == upDist) {
                        int count = 0;
                        for (int j = 1; j <= i; j++) {
                            if (directions[i - j] == WalkerState.MOVE_UP) {
                                count++;
                            }
                        }
                        if (count == 0){
                            directions[i] = WalkerState.MOVE_UP;
                        }
                    } if (distances[i] == downDist) {
                        int count = 0;
                        for (int j = 1; j <= i; j++) {
                            if (directions[i - j] == WalkerState.MOVE_DOWN) {
                                count++;
                            }
                        }
                        if (count == 0){
                            directions[i] = WalkerState.MOVE_DOWN;
                        }
                    }
                } else {
                    if (distances[i] == leftDist) {
                        directions[i] = WalkerState.MOVE_LEFT;
                    } else if (distances[i] == rightDist) {
                        directions[i] = WalkerState.MOVE_RIGHT;
                    } else if (distances[i] == upDist) {
                        directions[i] = WalkerState.MOVE_UP;
                    } else if (distances[i] == downDist) {
                        directions[i] = WalkerState.MOVE_DOWN;
                    }
                }
            } */
            for (int i = 0; i < directions.length; i++) {
                System.out.println(directions[i]);
            }

            //All the normal mazewalker stuff
            if (!maze.getLocation(currentX, currentY).getLeft().isLegal()
                    || !maze.getLocation(currentX, currentY).getLeft().isOpen()
                    || getBeenThere()[currentY][currentX - 1] == true) {
                leftDist = 1000;
            }
            if (!maze.getLocation(currentX, currentY).getRight().isLegal()
                    || !maze.getLocation(currentX, currentY).getRight().isOpen()
                    || getBeenThere()[currentY][currentX - 1] == true) {
                rightDist = 1001;
            }
            if (!maze.getLocation(currentX, currentY).getAbove().isLegal()
                    || !maze.getLocation(currentX, currentY).getAbove().isOpen()
                    || getBeenThere()[currentY - 1][currentX] == true) {
                upDist = 1002;
            }
            if (!maze.getLocation(currentX, currentY).getBelow().isLegal()
                    || !maze.getLocation(currentX, currentY).getBelow().isOpen()
                    || getBeenThere()[currentY + 1][currentX] == true) {
                downDist = 1003;
            }



            if (leftDist <= rightDist &&
                leftDist <= upDist &&
                leftDist <= downDist){

                        pathIndex++;
                        moveTally++;

                        path[pathIndex] = WalkerState.MOVE_LEFT;
                        return WalkerState.MOVE_LEFT;
            }

            if (rightDist <= leftDist &&
                rightDist <= upDist &&
                rightDist <= downDist){

                pathIndex++;
                moveTally++;

                path[pathIndex] = WalkerState.MOVE_RIGHT;
                return WalkerState.MOVE_RIGHT;
            }


            if (upDist <= rightDist &&
                upDist <= leftDist &&
                upDist <= downDist){


                pathIndex++;
                moveTally++;
                //beenThere[currentX][currentY - 1] = true;
                path[pathIndex] = WalkerState.MOVE_UP;
                return WalkerState.MOVE_UP;
            }

            if (downDist <= rightDist &&
                downDist <= upDist &&
                downDist <= leftDist){

                pathIndex++;
                moveTally++;
                //beenThere[currentX][currentY + 1] = true;
                path[pathIndex] = WalkerState.MOVE_DOWN;
                return WalkerState.MOVE_DOWN;
            } else if (pathIndex > 0) {

                moveTally++;
                pathIndex--;

                if (path[pathIndex + 1] == WalkerState.MOVE_LEFT){
                    return WalkerState.MOVE_RIGHT;
                } else if (path[pathIndex + 1] == WalkerState.MOVE_RIGHT){
                    return WalkerState.MOVE_LEFT;
                } else if (path[pathIndex + 1] == WalkerState.MOVE_UP){
                    return WalkerState.MOVE_DOWN;
                } else {
                    return WalkerState.MOVE_UP;
                }

            } else {
                return WalkerState.IMPOSSIBLE_TO_GET_THERE;
            }
        }



//------------------------STANDARD-------------------------------
        else {
            beenThere[currentY][currentX] = true;
            if (currentX == destinationX && currentY == destinationY) {

                return WalkerState.THERE_ALREADY;

                //LEFT
            } else if (maze.getLocation(currentX, currentY).getLeft().isLegal()
                    && maze.getLocation(currentX, currentY).getLeft().isOpen()
                    && getBeenThere()[currentY][currentX - 1] == false) {

                pathIndex++;
                moveTally++;

                path[pathIndex] = WalkerState.MOVE_LEFT;
                return WalkerState.MOVE_LEFT;

            //RIGHT
            } else if (maze.getLocation(currentX, currentY).getRight().isLegal()
                    && maze.getLocation(currentX, currentY).getRight().isOpen()
                    && getBeenThere()[currentY][currentX + 1] == false) {

                pathIndex++;
                moveTally++;

                path[pathIndex] = WalkerState.MOVE_RIGHT;
                return WalkerState.MOVE_RIGHT;

            //UP
            } else if (maze.getLocation(currentX, currentY).getAbove().isLegal()
                    && maze.getLocation(currentX, currentY).getAbove().isOpen()
                    && getBeenThere()[currentY - 1][currentX] == false) {

                pathIndex++;
                moveTally++;
                //beenThere[currentX][currentY - 1] = true;
                path[pathIndex] = WalkerState.MOVE_UP;
                return WalkerState.MOVE_UP;

            //DOWN
            } else if (maze.getLocation(currentX, currentY).getBelow().isLegal()
                    && maze.getLocation(currentX, currentY).getBelow().isOpen()
                    && getBeenThere()[currentY + 1][currentX] == false) {

                pathIndex++;
                moveTally++;
                //beenThere[currentX][currentY + 1] = true;
                path[pathIndex] = WalkerState.MOVE_DOWN;
                return WalkerState.MOVE_DOWN;

            } else if (pathIndex > 0) {

                moveTally++;
                pathIndex--;

                if (path[pathIndex + 1] == WalkerState.MOVE_LEFT){
                    return WalkerState.MOVE_RIGHT;
                } else if (path[pathIndex + 1] == WalkerState.MOVE_RIGHT){
                    return WalkerState.MOVE_LEFT;
                } else if (path[pathIndex + 1] == WalkerState.MOVE_UP){
                    return WalkerState.MOVE_DOWN;
                } else {
                    return WalkerState.MOVE_UP;
                }

            } else {
                return WalkerState.IMPOSSIBLE_TO_GET_THERE;
            }
        }
    }

    /**
     * Returns a representation of the locations which the walker has visited.
     * The 2D array's dimensions should correspond to those of the walker's
     * assigned maze.
     */
    public boolean[][] getBeenThere() {
        return beenThere;
    }

    /**
     * Returns the current path taken by the walker.
     */
    public WalkerState[] getCurrentPath() {
        WalkerState[] currentPath = new WalkerState[pathIndex + 1];
        for (int i = 0; i < pathIndex + 1; i++) {
            currentPath[i] = path[i];
        }
        return currentPath;
    }

    /**
     * Returns the number of moves performed.
     */
    public int getMoveTally() {
        return moveTally;
    }

    /**
     * The data structure for maintaining the current path.
     */
    private WalkerState[] path;

    /**
     * The index for the current node in the path.
     */
    private int pathIndex;

    /**
     * The data structure for keeping track of "passed" squares.
     */
    private boolean[][] beenThere;

    /**
     * The maze that is being walked.
     */
    private Maze maze;

    /**
     * The x-coordinate of the walker's destination.
     */
    private int destinationX;

    /**
     * The y-coordinate of the walker's destination.
     */
    private int destinationY;

    /**
     * The tally of the number of moves performed, including back-tracking moves.
     */
    private int moveTally;

    private boolean smart;

    private int horiDistance;

    private int vertDistance;
}
