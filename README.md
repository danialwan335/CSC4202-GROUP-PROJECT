# 🚑 Landslide Medical Evacuation Route Optimiser

A Java-based algorithmic solution to find the fastest ambulance route through a post-landslide road network using **Dijkstra's Shortest Path Algorithm** (Graph Paradigm).

## 📖 Scenario

Heavy monsoon rains cause a severe landslide in Hulu Selangor, blocking the main highway. An emergency ambulance must navigate a network of logging tracks and secondary roads to reach a cardiac patient in Kampung Sungai Lui.

**Algorithm Goal:** Find the minimum-time route from Hospital Kuala Kubu Bharu (Node 0) to Kampung Sungai Lui (Node 7) through the surviving road network.

---

## 📁 Table of Contents

### 📖 Part 1 — Introduction
- 1.1 Scenario and Problem Statement
- 1.2 Importance of Finding an Optimal Solution
- 1.3 Algorithm Suitability Review (Sorting / DAC / DP / Greedy / Graph)

### 🧠 Part 2 — Algorithm Design
- 2.0 Scenario Model Development
  - 2.1 Overview
  - 2.2 Route Value Prioritization
  - 2.3 Constraints and Challenges
- 3.0 Algorithm Design
  - 3.1 Overview (Problem Formulation)
  - 3.2 Graph Value Modelling (Edge Weight Table)
  - 3.3 Worked Example (Step-by-Step Dijkstra's Execution)
- 4.0 Algorithm Specification
  - 4.1 Problem Definition
  - 4.2 Input and Output Specification
  - 4.3 Mathematical Model (Recurrence Relation)
  - 4.4 Algorithm Approach (Why Dijkstra's?)
  - 4.5 Algorithm Description (Steps, Data Structures)

### 💻 Part 3 — Implementation
- 5.0 Algorithm Implementation
  - 5.1 Java Source Code
  - 5.2 Pseudocode
  - 5.3 Algorithm Descriptions
  - 5.4 Demonstration (3 Test Scenarios)
  - 5.5 Results
- 6.0 Analysis of Algorithm
  - 6.1 Correctness (Loop Invariant Proof)
  - 6.2 Time Complexity Analysis (Best / Average / Worst)

---

## 🚀 How to Run

```bash
cd src
javac LandslideEvacuation.java
java LandslideEvacuation
```

## 📊 Expected Output (Scenario A)

```
Shortest Travel Time : 55 minutes
Optimal Route        : 0 → 1 → 3 → 5 → 7
Segments: Hospital→Pekan KKB(8min)→Sg.Chilling(18min)→Pos Gertak(17min)→Kg.Sungai Lui(12min)
```

## 📚 References

- Dijkstra, E.W. (1959). A note on two problems in connexion with graphs. *Numerische Mathematik*, 1(1), 269–271.
- Cormen, T.H., Leiserson, C.E., Rivest, R.L., & Stein, C. (2009). *Introduction to Algorithms* (3rd ed.). MIT Press.
- Holmberg, M., et al. (2001). Survival after cardiac arrest outside hospital in Sweden. *Resuscitation*, 49(2), 137–145.
- Malaysian Civil Defence Department (AADK). (2023). *National Disaster Response Framework*. Putrajaya: APM.
- Ministry of Health Malaysia. (2022). *Emergency Medical Services Guidelines*. Putrajaya: MOH.
