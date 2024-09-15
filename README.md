# N-Queens
This repository contains a Java-based solution to the classic N-Queens problem, where the goal is to place 8 queens on an 8x8 chessboard so that no two queens threaten each other. This means ensuring that no two queens share the same row, column, or diagonal.

## Features
- Hill climbing algorithm: A heuristic search algorithm that iteratively improves the solution by reducing conflicts between queens
- Random restarts: When the algorithm gets stuck in a local optimum, it performs a random restart to try a new initial configuration
- Efficient Heuristic Evaluation: Optimized calculation of conflicts between queens to guide the search toward a solution

## How to Run
- Clone the repository
- Compile and run the Java code

## Technologies Used
- Java: The programming language used to implement the solution
- Heuristic Search Algorithms: Techniques such as hill climbing with random restarts to efficiently solve the problem
  
## License
This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details
