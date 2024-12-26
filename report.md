# Divide Karel Map

## Task
The goal is to divide Karel's map into 4 equal chambers. If the map cannot be divided into 4, then the solution should attempt to divide it into 3, 2, or 1 chamber, depending on the map’s dimensions.

---

## Workflow

1. **Map Dimension Calculation**:
    - Start at the left-bottom corner of the map (Point(1, 1)).
    - Karel moves to the farthest east to determine the map’s width, then moves north to determine the height.

2. **Override Methods**:
    - Override the `move` and `putBeeper` methods to monitor the number of steps Karel takes and the number of beepers used.

3. **Determine Number of Chambers**:
    - Analyze the dimensions to decide how to divide the map:
        - If both width and height are smaller than 3, divide the map into 1 chamber.
        - If both dimensions are at least 3, attempt to divide the map into 4 chambers using vertical and horizontal lines. Double lines of beepers are used if the dimensions are even.
        - If either dimension is less than 3, divide the map only in the larger dimension.

4. **Helper Functions**:
    - **Get Number of Chambers**: A function that determines how many chambers a dimension can be divided into (4, 3, or 2).
    - **Division Functions**: Separate functions to divide the map horizontally (`divideHorizontallyIntoChambers`) and vertically (`divideVerticallyIntoChambers`).
    - **Move and Facing Helpers**: Functions for changing Karel's facing direction (North, South, East, West) and moving Karel to specific points while optionally placing beepers along the way.
    - **Location Tracking**: A method that updates Karel’s current location after every move.

5. **Optimization**:
    - The code is optimized for minimizing the number of steps and beepers used while dividing the map. Double lines of beepers are added only when necessary.

