import stanford.karel.KarelWorld;
import stanford.karel.SuperKarel;

import java.awt.*;

public class Homework extends SuperKarel {

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

    @Override
    public void run() {
        resetAll();
        getMapDimension();
        divideMap();
    }

    @Override
    public void putBeeper() {
        super.putBeeper();
        usedBeepersCounter++;
    }

    @Override
    public void move() {
        super.move();
        moveCounter++;
        updateLocation();
        printStats();
    }

    private static void printStats() {
        System.out.println("-");
        System.out.printf("Number of Movement: %d\n", moveCounter);
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
        while (frontIsClear()) {
            move();
            width++;
        }
        turnLeft();
        while ((frontIsClear())) {
            move();
            height++;
        }
//        System.out.printf("Width: %d, Height: %d\n", width, height);
    }

    public void divideMap() {
        // Special case when the height or/and width is less than 3
        if (height < 3 && width < 3) {
            System.out.println("The Map Can't be Divided!");
        } else if (height < 3) {
            // The map can't be divided vertically (using horizontal line)
            // divide horizontally

            // check divide into 4 using 3 or 6 lines
            if (((width - 3) % 4 == 0 || (width - 6) % 4 == 0) && (width > 3)) {
                // divide into 4 champers
                if ((width - 3) % 4 == 0) {
                    // use one line
                    int chamberSize = (width - 3) / 4;
                    for (int i = 3; i > 0; i--) { // 3 times = 3 lines
                        int nextX = i * (chamberSize + 1);
                        moveInto(new Point(nextX, currentLocation.y));
                        moveInto(new Point(nextX, (currentLocation.y == 1) ? height : 1), true);
                    }
                } else {
                    // use double line
                    int chamberSize = (width - 6) / 4;
                    for (int i = 3; i > 0; i--) { // 3 times = 3 lines
                        int nextX = i * (chamberSize + 2);
                        moveInto(new Point(nextX, currentLocation.y));
                        moveInto(new Point(nextX, (currentLocation.y == 1) ? height : 1), true);
                        nextX--;
                        moveInto(new Point(nextX, currentLocation.y));
                        moveInto(new Point(nextX, (currentLocation.y == 1) ? height : 1), true);
                    }
                }
            } else if (((width - 2) % 3 == 0 || (width - 4) % 3 == 0) && (width > 4)) {
                // divide into 3 chambers
                if ((width - 2) % 3 == 0) {
                    // use one line
                    int chamberSize = (width - 2) / 3;
                    for (int i = 2; i > 0; i--) { // 3 times = 3 lines
                        int nextX = i * (chamberSize + 1);
                        moveInto(new Point(nextX, currentLocation.y));
                        moveInto(new Point(nextX, (currentLocation.y == 1) ? height : 1), true);
                    }
                } else {
                    // use double line
                    // Important: there is no need for this code
                    // why?
                    // this case will go to divide into 4
                    // how?
                    // the room size will be 3,6,9, ...
                    // divide by 2 double lines -> 4
                    // 3+4 in the divide into 4 case using 3 single line (3+4)-3 = 4 -> 4%4 = 0
                    // and so on ...
                }
            } else {
                // divide into 2 using one line
                // even -> double
                // odd -> single
                divideVertically();
            }
        } else if (width < 3) {
            // The map can't be divided horizontally (using vertical line)
            // divide vertically
        } else {
            // divide map to 4 champers using vertical and horizontal line
            // check when use double lines of beepers
            divideVertically();
            divideHorizontally();
        }
        // We should use double line of beepers when the measure is even

        // divide horizontally
        // Based on the width

        // divide vertically
        // Based on the height
    }

    public void moveInto(Point targetPoint) {
        // Example of using method overloading to achieve default value for boolean
        moveInto(targetPoint, false);
    }

    public void moveInto(Point targetPoint, boolean putBeeper) {
        // on the x-axis
        if (targetPoint.x > currentLocation.x) {
            while (!facingEast())
                turnLeft();
        } else {
            while (!facingWest())
                turnLeft();
        }
        while (currentLocation.x != targetPoint.x) {
            if (putBeeper && noBeepersPresent())
                putBeeper();
            move();
        }

        // on the y-axis
        if (targetPoint.y > currentLocation.y) {
            while (!facingNorth())
                turnLeft();
        } else {
            while (!facingSouth())
                turnLeft();
        }
        while (currentLocation.y != targetPoint.y) {
            if (putBeeper && noBeepersPresent())
                putBeeper();
            move();
        }
        if (putBeeper && noBeepersPresent())
            putBeeper();


    }

    public void divideVertically() {
// Based on width
        // into 2
        if (width % 2 == 0) {
            // double line of beepers
            moveInto(new Point(width / 2 + 1, height));
            moveInto(new Point(width / 2 + 1, 1), true);
            moveInto(new Point(width / 2, 1));
            moveInto(new Point(width / 2, height), true);
        } else {
            moveInto(new Point(width / 2 + 1, height));
            moveInto(new Point(width / 2 + 1, 1), true);
        }
    }

    public void divideHorizontally() {
        // Based on height
        // into 2
        if (height % 2 == 0) {
            // double line of beepers
            moveInto(new Point(width, height / 2 + 1));
            moveInto(new Point(1, height / 2 + 1), true);
            moveInto(new Point(1, height / 2));
            moveInto(new Point(width, height / 2), true);

            // we can decrease number of steps by use list of points like this
            // p1 ---> p2
        } else {
            moveInto(new Point(1, height / 2 + 1));
            moveInto(new Point(width, height / 2 + 1), true);
        }
    }

// function to check dimension to how many champers can be divided

// function to check when use double lines of beepers

// Reset on load new world

}
