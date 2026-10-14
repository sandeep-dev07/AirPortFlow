# AirportFlow --- Product Requirements Document (PRD)

## 1. Project Overview

**Project Name:** AirportFlow\
**Project Type:** DSA-3 Hackathon Project\
**Implementation Language:** Java only

AirportFlow is an airport operations and resource-management platform.
It models flights, flight schedules, gates, runways, passenger and
baggage information, operational reports and alerts, and continuously
generated airport-event data.

## 2. Problem Statement

Airports manage many flights, passengers, gates, and limited resources
at the same time. Searching information, assigning gates, handling
scheduling constraints, analyzing capacity, and processing continuous
airport events can be difficult. AirportFlow demonstrates DSA-based
solutions for these problems.

## 3. Main Objective

Build a simple Java application that demonstrates: - Flight and schedule
management - Fast flight searching - Input correction - Flight-to-gate
assignment - Passenger/capacity flow analysis - Resource and schedule
optimization - Flight record processing - Continuous airport-event
processing

## 4. Technology Constraint

The entire application must be implemented in **Java only**.

-   Java application logic
-   Java DSA implementations
-   Java collections where appropriate
-   Java-based UI only, if a UI is added

No Python, JavaScript/TypeScript, C/C++, or another-language backend is
part of the project.

## 5. Main Features

### Flight Management

Store and manage flight records such as flight number, airline, source,
destination, scheduled time, status, passenger count, and gate.

### Flight Search

Search flight numbers and airport-related information using KMP,
Rabin-Karp, and Aho-Corasick.

### Flight-to-Gate Assignment

Assign eligible flights to available gates using Bipartite Matching.

### Passenger and Capacity Analysis

Model passenger movement/capacity through airport facilities using
Ford-Fulkerson, Edmonds-Karp, Dinic, and Max-Flow/Min-Cut concepts.

### Input Correction

Correct incorrectly entered airport or passenger information using
Levenshtein and Damerau-Levenshtein distance.

### Resource Optimization

Evaluate small combinations of gates/resources using Bitmask DP and
demonstrate Matrix-Chain Multiplication or Optimal BST where
appropriate.

### Constraint and Conflict Handling

Represent airport scheduling/resource constraints using SAT/3-SAT and
demonstrate the specified graph reductions and Vertex-Cover
approximation.

### Flight Processing

Rank/sort flight records using Randomized QuickSort.

### Event Streams

Demonstrate continuous airport-event processing using Reservoir Sampling
and statistical processing concepts.

## 6. DSA-to-Feature Mapping

  -----------------------------------------------------------------------
  Module                  Algorithms              AirportFlow Use
  ----------------------- ----------------------- -----------------------
  M1                      KMP, Z-Function,        Flight numbers, alerts,
                          Rabin-Karp,             booking/baggage codes,
                          Aho-Corasick            status patterns

  M2                      Suffix Array, SA-IS,    Flight and airport
                          LCP/Kasai, Suffix       document indexing and
                          Tree/Suffix Automaton   substring queries

  M3                      Levenshtein,            Input correction,
                          Damerau-Levenshtein,    resource combinations,
                          Bitmask DP,             processing optimization
                          Matrix-Chain, Optimal   
                          BST                     

  M4                      Ford-Fulkerson,         Passenger flow,
                          Edmonds-Karp, Dinic,    capacity, flight-gate
                          Bipartite Matching,     assignment, conflicts
                          König's theorem         

  M5                      SAT/3-SAT, reductions,  Gate/scheduling
                          Vertex Cover            constraints and
                          approximation           operational conflicts

  M6                      Randomized QuickSort,   Flight ranking, event
                          Reservoir Sampling,     streams, statistics and
                          Miller-Rabin, Blelloch  parallel-processing
                          Scan, Parallel Reduce,  analysis
                          Brent's theorem         
  -----------------------------------------------------------------------

This mapping follows the AirportFlow entry in the supplied DSA-3 project
PDF.

## 7. Functional Requirements

-   **FR-01:** Store flight records.
-   **FR-02:** Search flight-related text efficiently.
-   **FR-03:** Assign flights to eligible available gates.
-   **FR-04:** Calculate edit distance and provide correction
    suggestions.
-   **FR-05:** Calculate flow/capacity for a configured airport network.
-   **FR-06:** Demonstrate small resource-selection optimization using
    DP.
-   **FR-07:** Demonstrate selected airport scheduling/resource
    constraints.
-   **FR-08:** Sort or rank flight records.
-   **FR-09:** Demonstrate continuously arriving airport events.
-   **FR-10:** Clearly display algorithm inputs and results.

## 8. Non-Functional Requirements

-   Java only.
-   Beginner-friendly, readable code.
-   DSA implementations separated from application logic.
-   Algorithms must be demonstrable.
-   Input validation for important fields.
-   Avoid unnecessary features that increase implementation time.
-   Use sample airport data; real airport APIs are not required.

## 9. MVP Scope

1.  Flight management
2.  Flight search
3.  Flight-to-gate assignment
4.  Levenshtein-based input correction
5.  Passenger/capacity flow demonstration
6.  Flight sorting
7.  Simple event-stream demonstration

Advanced M2, M5, and M6 demonstrations can be added after the MVP is
stable.

## 10. Out of Scope

-   Real airline integrations
-   Real-time airport APIs
-   Actual flight booking
-   Payment processing
-   Real baggage tracking
-   Real airport control
-   Production-grade aviation safety systems

AirportFlow is an academic DSA demonstration, not a real airport-control
system.

## 11. Success Criteria

The project is successful when the Java application runs correctly,
flights and gates can be represented, flight search works, gate
assignment works, text correction works, airport flow can be
demonstrated, records can be sorted, event processing can be
demonstrated, and the team can explain the DSA behind each feature.

## 12. Demo Flow

1.  Load sample flights.
2.  Display available gates.
3.  Search for a flight.
4.  Enter a misspelled airport name and show correction.
5.  Run flight-to-gate matching.
6.  Display airport flow/capacity analysis.
7.  Sort flights.
8.  Show airport-event processing.
9.  Explain the DSA used.

## 13. Project Principle

**Keep the system simple, make every DSA algorithm meaningful, and make
every feature easy to demonstrate.**
