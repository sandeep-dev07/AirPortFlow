# AirportFlow --- System Architecture

## 1. Architecture Overview

AirportFlow will be implemented as a **Java-only application**. The
architecture separates user interaction, application services, airport
domain models, DSA algorithms, and data storage.

## 2. High-Level Architecture

``` text
+--------------------------------------------------+
|             Java User Interface                  |
|        Console UI / Java GUI if needed           |
+--------------------------+-----------------------+
                           |
                           v
+--------------------------------------------------+
|             Application / Services              |
| FlightService | SearchService | GateAssignment  |
| FlowAnalysisService | OptimizationService       |
| EventService                                      |
+--------------------------+-----------------------+
                           |
              +------------+------------+
              |                         |
              v                         v
+-------------------------+   +--------------------+
|      Domain Models      |   |   DSA Algorithms   |
| Flight                  |   | String Algorithms  |
| Gate                    |   | Suffix Structures |
| Passenger               |   | Dynamic Programming|
| AirportEvent            |   | Network Flow      |
| AirportResource         |   | NP/Approximation   |
+------------+------------+   | Randomized/Parallel|
             |                +---------+----------+
             +------------+-------------+
                          v
+--------------------------------------------------+
|              Data Layer                         |
|     In-memory Java Collections / Sample Data    |
+--------------------------------------------------+
```

## 3. Suggested Java Package Structure

``` text
src/main/java/airportflow/
├── Main.java
├── model/
│   ├── Flight.java
│   ├── Gate.java
│   ├── Passenger.java
│   ├── AirportEvent.java
│   └── AirportResource.java
├── service/
│   ├── FlightService.java
│   ├── SearchService.java
│   ├── GateAssignmentService.java
│   ├── FlowAnalysisService.java
│   ├── OptimizationService.java
│   └── EventService.java
├── dsa/
│   ├── string/
│   │   ├── KMP.java
│   │   ├── RabinKarp.java
│   │   ├── ZFunction.java
│   │   └── AhoCorasick.java
│   ├── suffix/
│   │   ├── SuffixArray.java
│   │   ├── SAIS.java
│   │   └── LCPKasai.java
│   ├── dp/
│   │   ├── LevenshteinDistance.java
│   │   ├── DamerauLevenshtein.java
│   │   ├── BitmaskDP.java
│   │   ├── MatrixChain.java
│   │   └── OptimalBST.java
│   ├── flow/
│   │   ├── FordFulkerson.java
│   │   ├── EdmondsKarp.java
│   │   ├── Dinic.java
│   │   └── BipartiteMatching.java
│   ├── np/
│   │   ├── SATSolver.java
│   │   ├── ReductionDemo.java
│   │   └── VertexCoverApproximation.java
│   └── randomized/
│       ├── RandomizedQuickSort.java
│       ├── ReservoirSampling.java
│       ├── BlellochScan.java
│       └── ParallelReduce.java
├── repository/
│   ├── FlightRepository.java
│   ├── GateRepository.java
│   └── EventRepository.java
└── util/
    ├── InputValidator.java
    └── SampleData.java
```

## 4. Layer Responsibilities

### UI Layer

Handles user interaction. The first version should preferably use a Java
console menu.

``` text
===== AIRPORTFLOW =====
1. View Flights
2. Search Flight
3. Assign Gates
4. Correct Airport Name
5. Analyze Passenger Flow
6. Sort Flights
7. View Airport Events
8. Exit
```

### Service Layer

Connects UI operations with models and DSA algorithms.

Example:

``` text
User
 ↓
SearchService
 ↓
KMP
 ↓
Search Result
```

Gate assignment:

``` text
User
 ↓
GateAssignmentService
 ↓
BipartiteMatching
 ↓
Flight → Gate assignments
```

### Model Layer

Contains simple Java classes representing airport data.

Example Flight fields:

``` java
String flightNumber;
String airline;
String source;
String destination;
String scheduledTime;
String status;
int passengerCount;
String assignedGate;
```

Example Gate fields:

``` java
String gateId;
String terminal;
boolean available;
String gateType;
```

Example Passenger fields:

``` java
String passengerId;
String name;
String flightNumber;
```

Example AirportEvent fields:

``` java
String eventId;
String flightNumber;
String eventType;
long timestamp;
```

## 5. DSA Layer

DSA classes should contain algorithm logic only. They should not read
user input or print menus.

Better design:

``` java
class KMP {
    public int search(String text, String pattern) {
        // algorithm only
    }
}
```

Services handle input/output and call the algorithm classes.

## 6. M1 --- String Algorithms

-   **KMP:** Search flight numbers and airport alerts.
-   **Rabin-Karp:** Search booking/baggage codes.
-   **Z-Function:** Identify repeated operational messages.
-   **Aho-Corasick:** Search multiple airport alert/status patterns.

## 7. M2 --- Suffix Structures

-   **Suffix Array:** Index flight/airport documents.
-   **SA-IS:** Efficient suffix-array construction.
-   **LCP/Kasai:** Identify repeated operational descriptions.
-   **Suffix Tree/Suffix Automaton:** Support substring queries.

For the MVP, use small sample documents so the feature remains
understandable.

## 8. M3 --- Dynamic Programming

-   **Levenshtein:** Correct airport/passenger input.
-   **Damerau-Levenshtein:** Handle transposition errors.
-   **Bitmask DP:** Evaluate small gate/resource combinations.
-   **Matrix-Chain Multiplication:** Demonstrate processing
    optimization.
-   **Optimal BST:** Demonstrate organization of frequently accessed
    flight information.

## 9. M4 --- Network Flow

### Bipartite Matching

``` text
Flights                 Gates
Flight A  ----------->  Gate 1
Flight A  ----------->  Gate 2
Flight B  ----------->  Gate 2
Flight C  ----------->  Gate 3
```

The matching algorithm finds a valid flight-gate assignment.

### Flow Algorithms

-   Ford-Fulkerson
-   Edmonds-Karp
-   Dinic

These can model passenger flow and capacity through a configured airport
network.

### Min-Cut

Used to demonstrate critical capacity connections.

## 10. M5 --- NP-Completeness and Approximation

The project description connects AirportFlow with:

``` text
SAT / 3-SAT
     ↓
   CLIQUE
     ↓
INDEPENDENT-SET
     ↓
VERTEX-COVER
```

Use this as a controlled academic demonstration with small
graphs/constraints rather than a huge real-world scheduling solver.

## 11. M6 --- Randomized and Parallel Algorithms

-   **Randomized QuickSort:** Rank flight records.
-   **Reservoir Sampling:** Maintain a sample from continuous airport
    events.
-   **Blelloch Scan:** Demonstrate cumulative passenger statistics.
-   **Parallel Reduce:** Demonstrate airport-statistics aggregation.
-   **Brent's Theorem:** Analyze parallel-processing performance.
-   **Miller-Rabin:** Include only where a meaningful large-number
    demonstration is defined.

## 12. Data Flow Examples

### Flight Search

``` text
User enters flight number
        ↓
SearchService
        ↓
KMP / Rabin-Karp
        ↓
FlightRepository
        ↓
Matching flight
        ↓
Display result
```

### Gate Assignment

``` text
Flights + Available Gates
          ↓
Eligibility Graph
          ↓
Bipartite Matching
          ↓
Flight-Gate Assignment
          ↓
Update data
          ↓
Display assignment
```

### Passenger Flow

``` text
Airport Areas
     ↓
Build Capacity Graph
     ↓
Flow Algorithm
     ↓
Maximum Flow
     ↓
Display capacity/bottleneck information
```

### Input Correction

``` text
User Input
    ↓
Levenshtein Distance
    ↓
Compare with airport names
    ↓
Find closest candidate
    ↓
Display suggestion
```

## 13. Data Storage

For the first version, use in-memory Java collections:

``` java
List<Flight> flights;
List<Gate> gates;
List<Passenger> passengers;
List<AirportEvent> events;
```

Maps can be used where direct lookup is useful. A database is not
required for the MVP.

## 14. Application Flow

``` text
Start Application
       ↓
Load Sample Data
       ↓
Main Menu
       ↓
+-----------------------------+
| Search Flight               |
| Assign Gates                |
| Correct Input               |
| Analyze Flow                |
| Optimize Resources          |
| Sort Flights                |
| Process Events              |
+-----------------------------+
       ↓
Run Relevant DSA
       ↓
Display Result
       ↓
Return to Main Menu
```

## 15. Implementation Order

``` text
1. Java project setup
2. Models
3. Sample data
4. Flight management
5. KMP search
6. Levenshtein correction
7. Bipartite gate matching
8. Network flow
9. Randomized QuickSort
10. Event processing
11. M2/M5/M6 extended demonstrations
12. UI improvements
```

## 16. Testing Strategy

Each algorithm should have small, clear test cases.

``` text
KMP
Input: "AI202 delayed", "AI202"
Expected: Found

Levenshtein
Input: "Hydrabad", "Hyderabad"
Expected: Small edit distance

Bipartite Matching
Input: 3 flights, 3 eligible gates
Expected: Valid matching

Max Flow
Input: Small airport network
Expected: Correct maximum flow
```

Also test invalid input and cases where no valid gate or matching
exists.

## 17. Hackathon Demo Architecture

``` text
Flight Dashboard
      ↓
Search Flight
      ↓
Correct Airport Input
      ↓
Assign Flights to Gates
      ↓
Show Passenger Flow
      ↓
Sort Flights
      ↓
Show Event Processing
```

The final demonstration should prioritize visible results and clear
explanations.

## 18. Architecture Goal

**Simple Java application + clear domain models + separate DSA
implementations + small services + understandable demo flow.**
