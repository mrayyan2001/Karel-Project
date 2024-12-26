import stanford.karel.KarelWorld;
import stanford.karel.SuperKarel;

import java.awt.*;

public class Homework extends SuperKarel {
    // Defining constants for directions
    public static final int NORTH = 0;
    public static final int EAST = 90;
    public static final int SOUTH = 180;
    public static final int WEST = 270;

    // Defining constants for map dimension
    public static final int MIN_DIMENSION = 3;

    /* You fill the code here */
    // Block of codes to run when game is started
    {
        System.out.println("The Game is Started...");
        this.setBeepersInBag(1000); // Initialize the beepers in the bag to 1,000
    }

    // Default position and metrics
    static Point currentLocation = new Point(1, 1);
    static int height = 1, width = 1; // map dimension
    static int moveCounter = 0; // Counter for moves
    static int usedBeepersCounter = 0; // Counter for used beepers

    public void resetAll() {
        height = 1;
        width = 1;
        currentLocation = new Point(1, 1);
        moveCounter = 0;
        usedBeepersCounter = 0;
    }

    public void initialState() {
        facingInto(WEST);
        while (frontIsClear())
            move();
        facingInto(SOUTH);
        while (frontIsClear())
            move();
        facingInto(EAST);
    }

    @Override
    public void run() {
        // Reset all measures
        resetAll();
        // get width and height of the map
        getMapDimension();
        // divide the map
        divideMap();
    }

    @Override
    public void putBeeper() {
        // Override putBeeper method to monitor number of used beepers
        super.putBeeper();
        usedBeepersCounter++;
    }

    @Override
    public void move() {
        // override move method to monitor number of steps, update current location, and print the stats
        super.move();
        moveCounter++;
        updateLocation();
        printStats();
    }

    private static void printStats() {
        System.out.println("------------------------------------------");
        System.out.printf("Number of Steps: %d\n", moveCounter);
        System.out.printf("Position (%d, %d)\n", currentLocation.x, currentLocation.y);
        System.out.printf("Used beepers: %d\n", usedBeepersCounter);
    }

    private void updateLocation() {
        if (facingNorth())
            currentLocation.y++;
        else if (facingSouth())
            currentLocation.y--;
        else if (facingEast())
            currentLocation.x++;
        else
            currentLocation.x--;
    }

    public void getMapDimension() {
        // TODO: Optimize the method if karel in any other corner and if not look first if in top, bottom, right or left (e.g if in the top then go to most left or right no need to go to the bottom)
        // initial state in point(1,1) bottom left corner
        initialState();
        while (frontIsClear()) {
            move();
            width++;
        }
        turnLeft();
        while ((frontIsClear())) {
            move();
            height++;
        }
    }

    public int currentFacing() {
        if (facingNorth())
            return NORTH;
        if (facingSouth())
            return SOUTH;
        if (facingEast())
            return EAST;
        return WEST;
    }

    public void facingInto(int newFacing) {
        // TODO: Optimize the method to use the lowest number of turning
        while (currentFacing() != newFacing)
            turnLeft();
    }

    public void moveHorizontally(int targetX, boolean putBeeper) {
        if (targetX > currentLocation.x) {
            facingInto(EAST);
        } else {
            facingInto(WEST);
        }
        while (currentLocation.x != targetX) {
            if (putBeeper && noBeepersPresent())
                putBeeper();
            move();
        }
    }

    public void moveVertically(int targetY, boolean putBeeper) {
        if (targetY > currentLocation.y) {
            facingInto(NORTH);
        } else {
            facingInto(SOUTH);
        }
        while (currentLocation.y != targetY) {
            if (putBeeper && noBeepersPresent())
                putBeeper();
            move();
        }
    }

    public void moveInto(Point targetPoint, boolean putBeeper) {
        if (targetPoint == null)
            return;
        moveHorizontally(targetPoint.x, putBeeper);
        moveVertically(targetPoint.y, putBeeper);
        if (putBeeper && noBeepersPresent())
            putBeeper();
    }

    public void moveInto(Point targetPoint) {
        // Example of using method overloading to achieve default value for boolean
        moveInto(targetPoint, false);
    }

    public int getNumberOfChambers(int dimension) {
        if ((dimension - MIN_DIMENSION) % 4 == 0 || (dimension - 6) % 4 == 0) {
            return 4;
        } else if ((dimension - 2) % 3 == 0 || (dimension - 4) % 3 == 0) {
            return 3;
        } else {
            return 2;
        }
    }

    public void divideMap() {
        if (height < MIN_DIMENSION && width < MIN_DIMENSION) {
            System.out.println("The Map Can't be Divided!");
        } else if (height >= MIN_DIMENSION && width >= MIN_DIMENSION) {
            divideVerticallyIntoChambers(2);
            divideHorizontallyIntoChambers(2);
            // TODO: if you use double line you can optimize the code to decrease the number of steps by define punch of points to move on it (you need to make new function take a list of points and karel will got through these point in order) also you need a function to get these points.
        } else if (height < MIN_DIMENSION) {
            divideVerticallyIntoChambers(getNumberOfChambers(width));
        } else {
            divideHorizontallyIntoChambers(getNumberOfChambers(height));
        }
    }

    public void divideVerticallyIntoChambers(int numberOfChambers) {
        boolean doubleLine = (width - (numberOfChambers - 1) * 2) % numberOfChambers == 0;
        int chamberSize = (width - (doubleLine ? ((numberOfChambers - 1) * 2) : (numberOfChambers - 1))) / numberOfChambers;

        for (int i = (numberOfChambers - 1); i > 0; i--) { // 3 times = 3 lines
            int nextX = i * (chamberSize + (doubleLine ? 2 : 1));
            moveInto(new Point(nextX, height));
            moveInto(new Point(nextX, (currentLocation.y == 1) ? height : 1), true);
            if (doubleLine) {
                nextX--;
                moveInto(new Point(nextX, currentLocation.y));
                moveInto(new Point(nextX, (currentLocation.y == 1) ? height : 1), true);
            }
        }
    }

    public void divideHorizontallyIntoChambers(int numberOfChambers) {
        // Determine if double lines are needed
        boolean doubleLine = (height - (numberOfChambers - 1) * 2) % numberOfChambers == 0;
        int chamberSize = (height - (doubleLine ? ((numberOfChambers - 1) * 2) : (numberOfChambers - 1))) / numberOfChambers;

        for (int i = (numberOfChambers - 1); i > 0; i--) { // Loop to draw dividing lines
            int nextY = i * (chamberSize + (doubleLine ? 2 : 1));
            moveInto(new Point(width, nextY));
            moveInto(new Point((currentLocation.x == 1) ? width : 1, nextY), true);

            if (doubleLine) {
                // Add the second line for double-line divisions
                nextY--;
                moveInto(new Point(currentLocation.x, nextY));
                moveInto(new Point((currentLocation.x == 1) ? width : 1, nextY), true);
            }
        }
    }
}
