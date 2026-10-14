# AirportFlow — Viva Guide

**Project:** AirportFlow — DSA-3 Hackathon  
**Language:** Java only (no database, no external APIs)  
**Architecture:** Model → Repository → Service → DSA → Main (menu UI)

---

## How to Answer "Tell me about your project"

AirportFlow is a console-based Java application that simulates airport operations. It stores flights, gates, and events in memory using ArrayLists. A menu-driven UI lets the user view flights, search flights, assign gates, analyze passenger flow, sort flights, and process events. Each feature demonstrates a specific DSA algorithm. The project has three layers: Model (data classes), Repository (in-memory storage), and Service (business logic that calls DSA algorithms). All algorithms are implemented from scratch in the `dsa/` package.

---

## Option 1: View All Flights

### What the user does
Selects option 1 from the menu. No additional input is needed.

### What happens internally
`Main.handleViewFlights()` calls `flightService.getAllFlights()`, which returns a copy of the internal flight list from the repository. Each flight's `toString()` method formats it into a table row. The method prints all 10 flights and a total count.

### Java classes involved
`Main`, `FlightService`, `FlightRepository`, `Flight`

### DSA algorithm used
None. This is a simple data display feature.

### Why that algorithm is used
No algorithm is needed. The feature simply reads and displays stored data.

### Example
User enters `1`. All 10 sample flights are shown in a table with columns: Flight, Airline, Source, Dest, Time, Status, Passengers, Gate.

### Expected output
```
=== View All Flights ===

Flight     | Airline      | Source   | Dest     | Time         | Status     | Passengers | Gate
-----------|--------------|----------|----------|--------------|------------|------------|--------
AI101      | Air India    | Delhi    -> Mumbai   | 08:00        | On Time    | Passengers: 180 | Gate: G1
6E202      | IndiGo       | Bangalore -> Chennai  | 09:15        | Delayed    | Passengers: 150 | Gate: G2
...
Total flights: 10
```

---

## Option 2: Search Flight

### What the user does
Selects option 2, then chooses a search type (1 = Flight Number, 2 = Airline Name, 3 = Route/City), then types a search query.

### What happens internally
`Main.handleSearchFlight()` reads the user's choice and query. It calls one of three methods on `SearchService`:
- `searchByFlightNumber(query)` — uses KMP
- `searchByAirline(query)` — uses Rabin-Karp
- `searchByRoute(query)` — uses Z-Function

Each method iterates over all flights in the repository and applies the string algorithm to check if the flight's relevant field contains the query. Matching flights are collected and returned.

### Java classes involved
`Main`, `SearchService`, `FlightRepository`, `Flight`, `KMP`, `RabinKarp`, `ZFunction`

### DSA algorithms used
- **KMP** (Knuth-Morris-Pratt) for flight number search — O(n + m) time
- **Rabin-Karp** for airline name search — O(n + m) average time using rolling hash
- **Z-Function** for route/city search — O(n + m) time

### Why those algorithms are used
These are classic string matching algorithms. Each flight field is treated as a text string, and the query is a pattern. The algorithms find all positions where the pattern appears in the text without using Java's built-in `contains()`.

### Example
User enters `2` (search by airline), then `1` (IndiGo), then `6E` (query). Rabin-Karp checks each flight's airline field ("Air India", "IndiGo", "SpiceJet") for the pattern "6E". It finds matches in flights 6E202, 6E505, and 6E808.

### Expected output
```
=== Search Flight ===
Search by:
  1. Flight Number (KMP)
  2. Airline Name (Rabin-Karp)
  3. Route/City (Z-Function)
Enter choice: 2
Enter search term: 6E

Found 3 flight(s):
  6E202 | IndiGo | Bangalore -> Chennai | 09:15 | Delayed | Passengers: 150 | Gate: G2
  6E505 | IndiGo | Chennai -> Bangalore | 13:00 | On Time | Passengers: 160 | Gate: G4
  6E808 | IndiGo | Pune -> Nagpur | 16:50 | Delayed | Passengers: 130 | Gate: Unassigned
```

---

## Option 3: String Algorithm Demonstrations

### What the user does
Selects option 3. The program shows four string algorithms working on a hardcoded airport text sample. The user is prompted to enter a pattern for KMP and Rabin-Karp.

### What happens internally
`Main.handleSearchStringAlgorithms()` defines a sample text:
```
"Flight AI101 departed at 08:00. Flight 6E202 is delayed. Security alert for SG303. AI404 boarding now. 6E505 on time."
```
It then:
1. Calls `KMP.search(text, pattern)` — asks user for pattern, shows all match positions.
2. Calls `RabinKarp.search(text, pattern)` — asks user for pattern, shows match positions.
3. Calls `ZFunction.compute(text)` — computes and displays the entire Z-array, then finds the longest repeated substring.
4. Calls `searchService.searchMultiplePatterns(text, patterns)` with patterns `["AI101", "6E202", "DELAY", "BOARDING"]` — uses Aho-Corasick to find all pattern occurrences in one pass.

### Java classes involved
`Main`, `SearchService`, `KMP`, `RabinKarp`, `ZFunction`, `AhoCorasick`

### DSA algorithms used
- **KMP** — pattern matching using LPS (Longest Prefix Suffix) table
- **Rabin-Karp** — pattern matching using rolling hash
- **Z-Function** — computes Z-array for longest prefix matches at each position; also used for substring search
- **Aho-Corasick** — multi-pattern matching using a trie with failure links

### Why those algorithms are used
This option is a pure demonstration. It shows how all four string algorithms work on the same airport-themed text. KMP and Rabin-Karp each search for one pattern. Z-Function shows the full Z-array and finds repeated substrings. Aho-Corasick searches for multiple patterns simultaneously, which is more efficient than running KMP separately for each pattern.

### Example
User enters `3`. For KMP pattern "AI", output shows positions 7 and 83. For Rabin-Karp pattern "AI", output shows position 7. Z-array shows `[117, 0, ..., 7, ...]` and longest repeated substring is "Flight ". Aho-Corasick finds AI101 at position 7 and 6E202 at position 39.

### Expected output
```
=== String Algorithm Demonstrations ===
Sample airport text:
"Flight AI101 departed at 08:00. Flight 6E202 is delayed. ..."

--- KMP Search ---
Enter pattern to search (KMP): AI
Found at positions: [7, 83]

--- Rabin-Karp Search ---
Enter pattern to search (Rabin-Karp): AI
Found at positions: [7]

--- Z-Function Demo ---
Z-array: [117, 0, 0, ..., 7, ...]
Longest repeated substring: "Flight "

--- Aho-Corasick Multi-Pattern Search ---
Patterns: [AI101, 6E202, DELAY, BOARDING]
  Pattern 'AI101' found at position 7
  Pattern '6E202' found at position 39
```

---

## Option 4: Correct Airport Input

### What the user does
Selects option 4, then types a possibly misspelled airport name (e.g., "Hydrabad").

### What happens internally
`Main.handleCorrectInput()` reads the user's input and calls two methods on `OptimizationService`:
- `correctAirportName(input)` — uses Levenshtein Distance
- `correctAirportNameDamerau(input)` — uses Damerau-Levenshtein Distance

Both methods compute the edit distance between the input and each of the 10 valid airport names, then return the closest match. The edit distances are also displayed.

### Java classes involved
`Main`, `OptimizationService`, `LevenshteinDistance`, `DamerauLevenshtein`

### DSA algorithms used
- **Levenshtein Distance** — dynamic programming, O(m*n), counts insertions, deletions, and substitutions
- **Damerau-Levenshtein Distance** — dynamic programming, O(m*n), also counts adjacent character transpositions

### Why those algorithms are used
Airport names are often misspelled by passengers or staff. Edit distance algorithms find the closest valid name by counting the minimum number of single-character edits needed to transform the input into each candidate. Damerau-Levenshtein additionally handles swapped characters (e.g., "Hydrabad" → "Hyderabad" where 'r' and 'a' are adjacent).

### Example
User enters `Hydrabad`. Levenshtein computes distance 1 to "Hyderabad" (one insertion of 'e'). Damerau-Levenshtein also computes distance 1. Both correctly suggest "Hyderabad".

### Expected output
```
=== Input Correction (Edit Distance) ===
Enter an airport name (possibly misspelled): Hydrabad

Input: "Hydrabad"

Levenshtein Distance Correction:
  Closest match: "Hyderabad"
  Edit distance: 1

Damerau-Levenshtein Distance Correction:
  Closest match: "Hyderabad"
  Edit distance: 1

All valid airports: [Delhi, Mumbai, Bangalore, Chennai, Kolkata, Hyderabad, Goa, Pune, Nagpur, Ahmedabad]
```

---

## Option 5: Assign Flights to Gates

### What the user does
Selects option 5. The program shows available gates, runs the matching algorithm, and displays assignments plus the updated flight list.

### What happens internally
`Main.handleAssignGates()` calls `gateAssignmentService.assignFlightsToGates()`.

Inside `GateAssignmentService`:
1. Gets all non-cancelled flights and all available gates.
2. Builds a bipartite graph: for each flight-gate pair, calls `isCompatible()` which checks gate availability and type (domestic/international).
3. Creates a `BipartiteMatching` instance with the adjacency matrix.
4. Calls `maxMatching()` which uses BFS (to find augmenting paths at increasing distances) and DFS (to augment along those paths) until no more augmenting paths exist.
5. Returns the matching as a map of flight number → gate ID.
6. Updates each flight's `assignedGate` field in the repository.

### Java classes involved
`Main`, `GateAssignmentService`, `GateRepository`, `FlightRepository`, `Gate`, `Flight`, `BipartiteMatching`

### DSA algorithm used
**Bipartite Matching** — BFS-based augmenting path algorithm (Hopcroft-Karp inspired). Finds the maximum number of flight-gate pairs with no conflicts.

### Why that algorithm is used
The gate assignment problem is naturally a bipartite graph: flights on the left side, gates on the right. An edge exists if a flight is compatible with a gate (domestic flight → domestic or international gate, international flight → international gate only). The maximum matching gives the most flights assigned to gates simultaneously.

### Example
There are 6 available gates (G1-G7, minus G6 which is occupied) and 9 non-cancelled flights. The algorithm finds 6 compatible pairs, assigning each flight to a gate that matches its type.

### Expected output
```
=== Flight-to-Gate Assignment (Bipartite Matching) ===

Available Gates:
  G1 | Terminal 1 | Domestic | Domestic | Available
  G2 | Terminal 1 | Domestic | Domestic | Available
  G3 | Terminal 2 | International | International | Available
  ...

Running Bipartite Matching algorithm...

Flight-Gate Assignments:
  Flight     | Gate | Status
  -----------|------|--------
  SG303      | G3   | Assigned
  6E505      | G5   | Assigned
  AI707      | G7   | Assigned
  AI404      | G4   | Assigned
  6E202      | G2   | Assigned
  AI101      | G1   | Assigned
```

---

## Option 6: Passenger Flow Analysis

### What the user does
Selects option 6. The program displays the airport network and shows max flow results from three algorithms.

### What happens internally
`Main.handleFlowAnalysis()` calls three methods on `FlowAnalysisService`:
1. `getNetworkDescription()` — returns a formatted string describing the 5-node network.
2. `computeMaxFlowFordFulkerson()` — builds the network, runs DFS-based augmenting path search.
3. `computeMaxFlowEdmondsKarp()` — builds the network, runs BFS-based augmenting path search.
4. `computeMaxFlowDinic()` — builds the network, runs level graph + blocking flow.
5. `findMinCut()` — after max flow, runs BFS from Terminal to find all reachable nodes.

All three algorithms build the same network:
```
Terminal (0) → Security (1): 500
Security (1) → Gates (2): 400
Gates (2) → Baggage (3): 350
Baggage (3) → Exit (4): 300
Terminal (0) → Gates (2): 100 (VIP)
Security (1) → Baggage (3): 50
```

### Java classes involved
`Main`, `FlowAnalysisService`, `FordFulkerson`, `EdmondsKarp`, `Dinic`

### DSA algorithms used
- **Ford-Fulkerson** — uses DFS to find augmenting paths, O(E * maxFlow)
- **Edmonds-Karp** — uses BFS to find shortest augmenting paths, O(V * E^2)
- **Dinic** — uses level graph and blocking flow, O(V^2 * E)

### Why those algorithms are used
The airport is modeled as a flow network where nodes are facilities (Terminal, Security, Gates, Baggage, Exit) and edges are capacity constraints. Max flow tells us the maximum number of passengers that can travel from Terminal to Exit. The min-cut identifies the bottleneck (which edges are fully saturated). Three algorithms are shown to demonstrate their different approaches to the same problem.

### Example
The network has a bottleneck at Baggage → Exit (capacity 300). All three algorithms compute max flow = 300. The min-cut shows Terminal, Security, Gates, and Baggage as reachable from source, meaning the cut is between Baggage and Exit.

### Expected output
```
=== Passenger Flow Analysis ===

Airport Flow Network:
  Terminal (Source) -> Security: 500 capacity
  Terminal (Source) -> Gates (VIP): 100 capacity
  Security -> Gates: 400 capacity
  Security -> Baggage: 50 capacity
  Gates -> Baggage: 350 capacity
  Baggage -> Exit (Sink): 300 capacity

Computing maximum flows...

Ford-Fulkerson Max Flow: 300
Edmonds-Karp Max Flow:   300
Dinic Max Flow:          300

Min-Cut (reachable from Terminal):
  - Terminal
  - Security
  - Gates
  - Baggage
```

---

## Option 7: Resource Optimization

### What the user does 
Selects option 7, then chooses a sub-option (1 = Bitmask DP, 2 = Matrix-Chain, 3 = Optimal BST).

### Sub-option 1: Bitmask DP
User enters a budget (e.g., 500). The program finds the best subset of gates that maximizes total capacity while staying within the budget. Gates are converted to Resource objects with capacity (200 for domestic, 300 for international) and cost (50 + index * 30). The Bitmask DP algorithm tries all 2^n subsets and picks the best valid one.

### Sub-option 2: Matrix-Chain Multiplication
The program demonstrates how to find the optimal parenthesization for multiplying a chain of matrices. Uses hardcoded dimensions `[10, 20, 30, 15, 25]` (4 matrices). Shows minimum scalar multiplications and split points.

### Sub-option 3: Optimal BST
The program demonstrates building an optimal binary search tree from hardcoded flight status frequencies (On Time: 40, Delayed: 25, etc.). Shows the minimum expected search cost.

### Java classes involved
`Main`, `OptimizationService`, `BitmaskDP`, `MatrixChain`, `OptimalBST`, `GateRepository`, `Gate`

### DSA algorithms used
- **Bitmask DP** — exhaustive subset enumeration using bitmasks, O(n * 2^n)
- **Matrix-Chain Multiplication** — dynamic programming, O(n^3)
- **Optimal BST** — dynamic programming, O(n^2)

### Why those algorithms are used
- Bitmask DP is used for the 0/1 knapsack-style problem of selecting gates under a budget constraint. With up to 7 gates (2^7 = 128 subsets), exhaustive search is feasible and easy to understand.
- Matrix-Chain demonstrates how DP finds the optimal order for sequential operations, analogous to optimizing multi-step passenger processing chains.
- Optimal BST demonstrates minimizing expected search cost, analogous to organizing frequently accessed flight status information.

### Example (Bitmask DP)
User enters budget 500. Gates G1 (cost 50, cap 200), G2 (cost 80, cap 200), G3 (cost 110, cap 300), G6 (cost 200, cap 300) are selected. Total capacity = 1000, total cost = 440.

### Expected output (Bitmask DP)
```
=== Resource Optimization ===

1. Bitmask DP - Optimal Gate Selection
2. Matrix-Chain Multiplication Demo
3. Optimal BST Demo
Enter choice: 1

=== Bitmask DP - Optimal Gate/Resource Selection ===
Enter budget: 500

Optimal resource selection (Budget: 500):
  Selected Resources:
  - G1 : Capacity=200, Cost=50
  - G2 : Capacity=200, Cost=80
  - G3 : Capacity=300, Cost=110
  - G6 : Capacity=300, Cost=200

  Total Capacity: 1000
  Total Cost:     440
```

---

## Option 8: Sort Flights

### What the user does
Selects option 8, then chooses a sort key (1 = Flight Number, 2 = Scheduled Time, 3 = Passenger Count). The sorted list is displayed.

### What happens internally
`Main.handleSortFlights()` reads the sort choice and calls `flightService.sortFlights(sortBy)`.

Inside `FlightService.sortFlights()`:
1. Gets all flights from the repository as a List.
2. Copies them into a `Flight[]` array.
3. Calls `RandomizedQuickSort.sort(array, comparator)` with the appropriate comparator.
4. Copies the sorted array back into a List and returns it.

The `RandomizedQuickSort` picks a random pivot at each step to avoid worst-case O(n^2) behavior on already-sorted data.

### Java classes involved
`Main`, `FlightService`, `FlightRepository`, `Flight`, `RandomizedQuickSort`

### DSA algorithm used
**Randomized QuickSort** — average O(n log n), worst case O(n^2) but highly unlikely due to random pivot selection.

### Why that algorithm is used
QuickSort is a standard comparison-based sorting algorithm. The random pivot selection ensures unbiased partitioning and avoids the pathological O(n^2) case that occurs when the input is already sorted and the pivot is always the first or last element. This demonstrates how randomization improves algorithm reliability.

### Example
User chooses sort by Flight Number. The 10 flights are sorted alphabetically: 6E202, 6E505, 6E808, AI010, AI101, AI404, AI707, SG303, SG606, SG909.

### Expected output
```
=== Sort Flights (Randomized QuickSort) ===
Sort by:
  1. Flight Number
  2. Scheduled Time
  3. Passenger Count
Enter choice: 1

Sorted Flights:
Flight     | Airline      | Source   | Dest     | Time         | Status     | Passengers | Gate
-----------|--------------|----------|----------|--------------|------------|------------|--------
6E202      | IndiGo       | Bangalore -> Chennai  | 09:15        | Delayed    | Passengers: 150 | Gate: G2
6E505      | IndiGo       | Chennai -> Bangalore | 13:00        | On Time    | Passengers: 160 | Gate: G4
...
```

---

## Option 9: Airport Event Processing

### What the user does
Selects option 9. The program lists all events, then demonstrates reservoir sampling.

### What happens internally
`Main.handleEventProcessing()` calls `eventService.getAllEvents()` to display all 12 events. Then it calls `eventService.demonstrateReservoirSampling()`.

Inside `EventService`:
1. Clears the reservoir sampler.
2. Calls `reservoirSampling.sampleAll(events)` which processes each event one by one.
3. For the first 5 events, they are placed directly into the reservoir.
4. For each subsequent event, a random index in [0, count) is generated. If the index is less than the sample size (5), the new event replaces the event at that index.
5. Displays the 5 sampled events.

### Java classes involved
`Main`, `EventService`, `EventRepository`, `AirportEvent`, `ReservoirSampling`

### DSA algorithm used
**Reservoir Sampling** — O(1) per element, produces a uniform random sample of size k from a stream of unknown or large length.

### Why that algorithm is used
Airports generate a continuous stream of events (departures, delays, arrivals, etc.). Storing all events for analysis is wasteful. Reservoir Sampling maintains a fixed-size representative sample where every event has an equal probability of being included. This is ideal for streaming data analysis.

### Example
12 events are processed with a sample size of 5. The reservoir might contain EVT012 (BAGGAGE), EVT002 (DELAY), EVT003 (ARRIVAL), EVT007 (DEPARTURE), EVT008 (DELAY).

### Expected output
```
=== Airport Event Processing ===

All Events:
  [EVT001] DEPARTURE | Flight: AI101    | Flight AI101 departed on time
  [EVT002] DELAY | Flight: 6E202    | Flight 6E202 delayed by 30 minutes
  ...
  [EVT012] BAGGAGE | Flight: 6E202    | Baggage belt activated for 6E202

Total events: 12

=== Reservoir Sampling Demo ===
Airport context: Maintaining a representative sample from event stream.

Total events processed: 12
Sample size: 5
Sampled events:
  [EVT012] BAGGAGE
  [EVT002] DELAY
  [EVT003] ARRIVAL
  [EVT007] DEPARTURE
  [EVT008] DELAY
```

---

## Option 10: Suffix Structures

### What the user does
Selects option 10. The program builds a suffix array on a hardcoded text, lets the user search for a substring, shows the LCP array, and demonstrates SA-IS construction.

### What happens internally
`Main.handleSuffixStructures()` defines text = `"airportflightairportflightflow"`.

1. **Suffix Array:** Creates a `SuffixArray` object which sorts all suffixes lexicographically using `Arrays.sort()`. The result is an array of starting indices. The program then asks the user for a pattern (default "flight") and uses binary search on the suffix array to find all occurrences.

2. **LCP/Kasai:** Calls `LCPKasai.computeLCP(text, suffixArray)` which computes the Longest Common Prefix array in O(n) time. The LCP array shows the length of the common prefix between consecutive suffixes in the sorted order.

3. **SA-IS:** Calls `SAIS.demonstrate("airport")` which shows the SA-IS linear-time suffix array construction algorithm on the small string "airport".

### Java classes involved
`Main`, `SuffixArray`, `LCPKasai`, `SAIS`

### DSA algorithms used
- **Suffix Array** — sorted array of all suffixes, used for fast substring queries via binary search
- **LCP/Kasai** — computes the LCP array in O(n) time from the suffix array
- **SA-IS** — linear-time suffix array construction algorithm (demonstrated on a small string)

### Why those algorithms are used
Suffix arrays are fundamental string data structures used in text indexing, DNA sequence analysis, and data compression. In an airport context, they could index flight descriptions or log messages for fast substring search. The demonstration shows how the suffix array enables efficient pattern matching and how LCP helps find repeated patterns.

### Example
Text = `"airportflightairportflightflow"`. The suffix array starts with indices `[0, 13, 7, ...]` meaning suffix at index 0 ("airportflightairportflightflow") comes first alphabetically, then suffix at 13 ("flightflow"), then suffix at 7 ("flightairportflightflow"). Searching for "flight" finds it at positions 7 and 20.

### Expected output
```
=== Suffix Structures Demo ===

Sample airport text: "airportflightairportflightflow"

--- Suffix Array ---
Suffix Array indices:
[0, 13, 7, 20, 26, 10, 23, 11, 24, 9, 22, 1, 14, 8, 21, 27, 4, 17, 28, 3, 16, 2, 15, 5, 18, 12, 6, 19, 25, 29]

Enter substring to search (or press Enter for 'flight'): flight
Pattern "flight" found at positions: [7, 20]

--- LCP/Kasai Algorithm ---
LCP Array: [0, 13, 0, 6, 2, 0, 3, 0, 2, 0, 4, 1, 12, 0, 5, 1, 0, 9, 1, 0, 10, 0, 11, 1, 8, 0, 1, 7, 3, 0]

--- SA-IS Construction Demo ===
Input text: "airport"
SA[ 0] =  0 -> airport
SA[ 1] =  1 -> irport
SA[ 2] =  4 -> ort
SA[ 3] =  3 -> port
SA[ 4] =  2 -> rport
SA[ 5] =  5 -> rt
SA[ 6] =  6 -> t
```

---

## Option 11: NP-Completeness Demo

### What the user does
Selects option 11, then chooses a sub-option (1 = SAT/3-SAT Solver, 2 = Reduction Demo, 3 = Vertex Cover Approximation).

### Sub-option 1: SAT/3-SAT Solver
The program solves a hardcoded 3-CNF formula using brute-force exhaustive search. It checks all 2^n possible assignments and returns one satisfying assignment if one exists.

### Sub-option 2: Reduction Demo
The program explains how gate scheduling can be reduced to 3-SAT. It prints the conceptual mapping (flights → variables, gate-time assignments → literals, constraints → clauses) and then solves a demo instance.

### Sub-option 3: Vertex Cover Approximation
The program builds a hardcoded airport resource graph (5 vertices, 5 edges) and runs two greedy strategies:
1. Pick the first arbitrary edge, add both endpoints to the cover, remove covered edges.
2. Always pick the vertex that covers the most remaining edges (max coverage greedy).

### Java classes involved
`Main`, `SATSolver`, `ReductionDemo`, `VertexCoverApproximation`

### DSA algorithms used
- **Brute-force SAT solver** — O(2^n) exhaustive search over all boolean assignments
- **Vertex Cover 2-approximation** — greedy algorithm that picks edges arbitrarily or by max coverage

### Why those algorithms are used
SAT and Vertex Cover are classic NP-complete problems. The brute-force SAT solver demonstrates why these problems are hard for large inputs (exponential time). The vertex cover approximation shows a practical approach: instead of finding the optimal solution (NP-hard), use a greedy algorithm that guarantees a solution at most twice the optimal size.

### Example (SAT)
Formula: `(x1 OR x2 OR NOT x3) AND (NOT x1 OR x3 OR x4) AND (NOT x2 OR NOT x3 OR NOT x4)`
Solver finds: x1=false, x2=false, x3=false, x4=false (all clauses satisfied).

### Expected output (Vertex Cover)
```
=== NP-Completeness Demo ===

1. SAT/3-SAT Solver
2. Reduction Demo (Gate Scheduling -> 3-SAT)
3. Vertex Cover Approximation
Enter choice: 3

=== Vertex Cover Approximation Demo ===
Airport context: Selecting minimum resources to cover all operations.

Airport resource graph (5 resources, 5 shared operations):
  Resource 0 -- Resource 1
  Resource 0 -- Resource 2
  Resource 1 -- Resource 3
  Resource 2 -- Resource 3
  Resource 3 -- Resource 4

Greedy Vertex Cover (pick arbitrary edge):
  Selected resources: [0, 1, 2, 3]
  Size: 4

Greedy Vertex Cover (max coverage):
  Selected resources: [3, 0]
  Size: 2
```

---

## Option 12: Randomized/Parallel Algorithms

### What the user does
Selects option 12, then chooses a sub-option (1 = Miller-Rabin, 2 = Blelloch Scan, 3 = Parallel Reduce, 4 = Brent's Theorem).

### Sub-option 1: Miller-Rabin
The program tests 8 large integers for primality using the Miller-Rabin probabilistic test with 10 iterations. Displays each number, whether it is probably prime or composite.

### Sub-option 2: Blelloch Scan
The program computes a prefix sum (cumulative sum) of a hardcoded passenger array `[120, 85, 200, 45, 160, 90]` using the Blelloch scan concept (up-sweep then down-sweep). Shows cumulative totals after each checkpoint.

### Sub-option 3: Parallel Reduce
The program demonstrates aggregation operations (sum, max, min, count) on hardcoded arrays of passenger counts and delay times. Shows total passengers, max delay, gates with delays, etc.

### Sub-option 4: Brent's Theorem
The program computes parallel time, speedup, and efficiency for processor counts 1, 2, 4, 8, 16 using hardcoded sequential time (100) and critical path (10). Displays a formatted table.

### Java classes involved
`Main`, `EventService`, `MillerRabin`, `BlellochScan`, `ParallelReduce`, `BrentTheorem`

### DSA algorithms used
- **Miller-Rabin** — probabilistic primality test, error probability 4^(-k) after k iterations
- **Blelloch Scan** — parallel prefix sum algorithm (up-sweep + down-sweep)
- **Parallel Reduce** — divide-and-conquer aggregation (demonstrated sequentially)
- **Brent's Theorem** — theoretical bound on parallel execution time

### Why those algorithms are used
- Miller-Rabin shows how randomization can solve number-theoretic problems efficiently. In an airport context, it could validate large reference numbers or codes.
- Blelloch Scan demonstrates how prefix sums can be computed in parallel, useful for cumulative passenger statistics.
- Parallel Reduce shows the concept of aggregating data using divide-and-conquer, applicable to computing total passengers, average delay, etc.
- Brent's Theorem provides a theoretical framework for understanding speedup and efficiency when scaling to more processors.

### Example (Brent's Theorem)
With sequential time 100 and critical path 10:
- 1 processor: parallel time = 110, speedup = 0.91, efficiency = 0.91
- 4 processors: parallel time = 35, speedup = 2.86, efficiency = 0.71
- 16 processors: parallel time = 16.25, speedup = 6.15, efficiency = 0.38

### Expected output (Brent's Theorem)
```
=== Randomized/Parallel Algorithms Demo ===

1. Miller-Rabin Primality Test
2. Blelloch Scan (Prefix Sum)
3. Parallel Reduce
4. Brent's Theorem
Enter choice: 4

=== Brent's Theorem Demo ===
Airport context: Analyzing parallel passenger processing.

Processors   | Parallel T   | Speedup      | Efficiency
-------------|--------------|--------------|--------------
1            | 110.00       | 0.91         | 0.91
2            | 60.00        | 1.67         | 0.83
4            | 35.00        | 2.86         | 0.71
8            | 22.50        | 4.44         | 0.56
16           | 16.25        | 6.15         | 0.38
```

---

## Quick-Reference Table

| Option | Feature | DSA Algorithm | Data Source |
|--------|---------|---------------|-------------|
| 1 | View All Flights | None (display only) | Repository |
| 2 | Search Flight | KMP, Rabin-Karp, Z-Function | Repository |
| 3 | String Demos | KMP, Rabin-Karp, Z-Function, Aho-Corasick | Hardcoded text |
| 4 | Correct Input | Levenshtein, Damerau-Levenshtein | Hardcoded airport list |
| 5 | Assign Gates | Bipartite Matching | Repository |
| 6 | Flow Analysis | Ford-Fulkerson, Edmonds-Karp, Dinic | Hardcoded network |
| 7 | Resource Optimization | Bitmask DP, Matrix-Chain, Optimal BST | Gates (Bitmask) / Hardcoded (others) |
| 8 | Sort Flights | Randomized QuickSort | Repository |
| 9 | Event Processing | Reservoir Sampling | Repository |
| 10 | Suffix Structures | Suffix Array, LCP/Kasai, SA-IS | Hardcoded text |
| 11 | NP-Completeness | SAT Solver, Vertex Cover Approximation | Hardcoded instances |
| 12 | Randomized/Parallel | Miller-Rabin, Blelloch Scan, Parallel Reduce, Brent's Theorem | Hardcoded data |

---

## Common Viva Questions and Short Answers

**Q: Why did you choose Java?**
A: Java is the required language for this project. It provides strong standard library support for collections (ArrayList, HashMap, Queue) which are used throughout the repositories and algorithms. The code is platform-independent and beginner-friendly.

**Q: Why no database?**
A: The project scope is to demonstrate DSA algorithms, not data persistence. Using ArrayLists keeps the code simple, portable, and focused on the algorithms. All data is loaded at startup from SampleData.java.

**Q: What is the time complexity of KMP?**
A: O(n + m) where n is the text length and m is the pattern length. The LPS table is built in O(m) and the search runs in O(n).

**Q: What is the difference between Ford-Fulkerson and Edmonds-Karp?**
A: Both find max flow, but Ford-Fulkerson uses DFS to find augmenting paths (can be slow with large capacities), while Edmonds-Karp uses BFS to find the shortest augmenting path (guaranteed O(V * E^2)).

**Q: What is Dinic's algorithm?**
A: Dinic uses a level graph (BFS to assign levels) and blocking flow (DFS to push all possible flow along level edges). It is faster than Edmonds-Karp for dense graphs: O(V^2 * E).

**Q: What is bipartite matching?**
A: It finds the maximum set of edges in a bipartite graph where no two edges share a vertex. In our project, it matches flights to gates. Uses BFS to find augmenting paths and DFS to augment them.

**Q: What is reservoir sampling?**
A: An algorithm to maintain a random sample of size k from a stream of unknown length. Each element has an equal probability of being in the sample. O(1) per element.

**Q: What is the Z-function?**
A: For a string S, Z[i] is the length of the longest substring starting at position i that is also a prefix of S. Computed in O(n) time. Used here for substring search and finding repeated patterns.

**Q: What is Aho-Corasick?**
A: A multi-pattern matching algorithm that builds a trie with failure links. It searches for all patterns in a text in O(n + m + z) time where z is the number of matches. More efficient than running KMP separately for each pattern.

**Q: What is bitmask DP?**
A: Uses binary representation to enumerate all subsets of a set. Each bit in an integer represents whether an element is included. Time complexity is O(n * 2^n), suitable for small n (up to ~20). Used here for optimal gate selection under a budget.

**Q: What is the 2-approximation for Vertex Cover?**
A: A greedy algorithm that picks an arbitrary edge, adds both endpoints to the cover, and removes all covered edges. The result is at most twice the size of the optimal vertex cover.

**Q: What is Miller-Rabin?**
A: A probabilistic primality test. It uses modular exponentiation and random witnesses. After k iterations, the error probability is at most 4^(-k). Used here to test large integers.

**Q: What is Blelloch Scan?**
A: A parallel prefix sum algorithm with two phases: up-sweep (build a reduction tree) and down-sweep (distribute values). In our sequential implementation, it demonstrates the concept of cumulative computation.

**Q: What is Brent's Theorem?**
A: It provides an upper bound on parallel execution time: T_p <= T_1/p + T_inf. It relates sequential time (T_1), critical path length (T_inf), and number of processors (p) to the parallel time and speedup.

**Q: What is SA-IS?**
A: A linear-time suffix array construction algorithm. It classifies suffixes as S-type or L-type and recursively constructs the suffix array. Demonstrated here on a small string for educational purposes.

---

## How the Project Compiles and Runs

**Compile:**
```
javac -d target/classes @sources.txt
```

**Run:**
```
java -cp target/classes airportflow.Main
```

**Or with Maven:**
```
mvn compile
mvn exec:java -Dexec.mainClass=airportflow.Main
```

The project has no external dependencies. All algorithms are implemented from scratch. Data is loaded from `SampleData.java` at startup.

---

*End of Viva Guide*
