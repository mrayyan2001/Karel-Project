# Divide Karel Map

### **1. Introduction**
**Purpose**:
- Introduce the problem that the assignment is solving: dividing a map into 4 equal chambers. Mention the conditions under which the map cannot be divided into 4 chambers (i.e., if the map dimensions are not divisible evenly by 4). Also, describe the task of optimizing the solution, including minimizing the number of moves and beepers used.

**Overview of the Solution**:
- Briefly explain the approach you will be detailing in the report. Mention that the code is written in Java, using Karel’s reference card functionalities, and that the report will discuss the design, optimizations, and key methods used to solve the problem.

---

### **2. Problem Breakdown**

**Dividing the Map**:
- The main challenge is dividing the map into 4 equal chambers if possible. If not, the solution should divide the map into the maximum possible number of equal chambers (3, 2, or 1).

**Map Constraints**:
- The map should be divided into equal chambers as much as possible based on the map’s dimensions.
- Special cases like narrow maps (too small to divide into 4 chambers) must be handled.
- Beepers should be used efficiently to mark the divisions, ensuring that the solution uses the fewest beepers and steps.

---

### **3. Design and Approach**

**Initial State Setup**:
- Karel starts at the bottom left corner (1,1) of the map.
- The map's dimensions are determined by Karel moving to the right and up until it hits a wall, counting the width and height.

**Steps in the Solution**:
- **Map Dimension Calculation**:
    - First, Karel navigates the map to determine its dimensions (width and height).

- **Determine Number of Chambers**:
    - Based on the map dimensions, we decide the number of chambers:
        - 4 chambers if the dimensions are divisible by 4.
        - 3 chambers if divisible by 3.
        - 2 chambers if divisible by 2.
        - 1 chamber if no division is possible.

- **Dividing the Map**:
    - Karel uses the `moveHorizontally` and `moveVertically` methods to create dividing lines for chambers.
    - Double lines of beepers are used when needed to create boundaries between chambers.
    - Special optimizations are used to minimize the number of moves (such as when moving horizontally or vertically by iterating through all points that need beepers, reducing unnecessary steps).

---

### **4. Key Functions and Methods**

**`resetAll()`**:
- Resets all variables used for dimensions, location, and counters. Ensures that each run starts fresh.

**`getMapDimension()`**:
- Determines the map’s dimensions by having Karel move along the borders to count the width and height.

**`divideMap()`**:
- Decides how to divide the map based on its dimensions, calling either `divideVerticallyIntoChambers()` or `divideHorizontallyIntoChambers()` accordingly.

**`divideVerticallyIntoChambers(int numberOfChambers)`**:
- Divides the map vertically into the specified number of chambers, using beepers as dividers. Double lines of beepers are added if necessary to make the divisions.

**`divideHorizontallyIntoChambers(int numberOfChambers)`**:
- Similar to the vertical division, but applies to horizontal divisions of the map. It also handles the double-line beeper placement.

**`moveInto(Point targetPoint, boolean putBeeper)`**:
- A helper method that allows Karel to move to a specified point, optionally placing beepers along the way.

---

### **5. Optimizations**

**Optimized Beeper Usage**:
- Instead of placing beepers at every move, beepers are only placed when needed to mark chamber divisions. This reduces the number of beepers used.
- Double lines of beepers are used only when necessary, based on the number of chambers and the map dimensions.

**Minimized Movement**:
- The number of moves is minimized by determining the exact points where beepers need to be placed and going directly to those points rather than moving step-by-step.
- The `moveInto` method allows Karel to move directly to a target point, minimizing back-and-forth movement.

**Move and Beeper Counters**:
- The code includes counters (`moveCounter` and `usedBeepersCounter`) that track Karel's progress, helping to evaluate the optimization level.

---

### **6. Challenges and Special Cases**

**Special Case Handling**:
- **Small Maps**: If the map’s dimensions are too small (less than 3x3), Karel will not be able to divide the map into 4 chambers. The code handles this by reducing the number of chambers to 3, 2, or 1, based on the dimensions.

- **Efficiency with Beepers**:
    - Managing the placement of beepers was crucial to minimize their usage.
    - The algorithm adapts to the size of the map and places beepers only at necessary points.

---

### **7. Conclusion**

**Summary of Approach**:
- The task was successfully tackled by dividing the map into the maximum number of chambers (4, 3, 2, or 1) using optimized beeper placement and movement.

**Optimizations and Results**:
- The solution was optimized in terms of both beeper usage and the number of steps Karel had to take.
- By avoiding unnecessary steps and placing beepers only when necessary, the solution was made as efficient as possible.

---

### **8. Future Improvements**

**Further Optimizations**:
- The map dimension detection could be further optimized, especially in cases where Karel is not starting at the bottom left corner (e.g., if Karel is somewhere else on the map).
- More sophisticated algorithms for handling larger or irregular maps could be explored to improve the solution further.

---
