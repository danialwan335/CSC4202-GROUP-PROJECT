import java.util.*;

/**
 * CSC4202 Group Project — Semester 2, 2025/2026
 * Landslide Medical Evacuation Route Optimiser
 * Algorithm : Dijkstra's Shortest Path Algorithm (Graph Paradigm)
 * Scenario : Post-landslide ambulance routing, Hulu Selangor, Malaysia
 *
 * Problem: A landslide has blocked the main highway.
 * Find the fastest alternate route through secondary logging roads
 * from Hospital Kuala Kubu Bharu (Node 0) to Kampung Sungai Lui (Node 7).
 */
public class LandslideEvacuation {

    /** Human-readable names for each node (road junction / landmark). */
    static final String[] NODE_NAMES = {
            "Hospital Kuala Kubu Bharu", // 0 <-- SOURCE
            "Pekan KKB Town Junction", // 1
            "Simpang Empat Batang Kali Fork", // 2
            "Sungai Chilling Forest Reserve", // 3
            "Ladang Ulu Bernam (Plantation)", // 4
            "Pos Gertak (Orang Asli Settlement)", // 5
            "Hulu Bernam Highlands Crossroad", // 6
            "Kampung Sungai Lui" // 7 <-- DESTINATION
    };

    // ─────────────────────────────────────────────────────────────────
    // Graph: weighted undirected adjacency list
    // ─────────────────────────────────────────────────────────────────
    static class Graph {
        int V;
        List<int[]>[] adj; // adj[u] = list of {v, weight}

        @SuppressWarnings("unchecked")
        Graph(int vertices) {
            this.V = vertices;
            adj = new ArrayList[vertices];
            for (int i = 0; i < vertices; i++)
                adj[i] = new ArrayList<>();
        }

        /** Add an undirected edge (u -- v) with travel time 'weight' minutes. */
        void addEdge(int u, int v, int weight) {
            adj[u].add(new int[] { v, weight });
            adj[v].add(new int[] { u, weight });
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // Dijkstra's Algorithm (min-heap implementation)
    // Returns: dist[] — shortest travel time from source to every node
    // prev[] — predecessor array for path reconstruction
    // ─────────────────────────────────────────────────────────────────
    static int[] dist;
    static int[] prev;

    static void dijkstra(Graph g, int src) {
        dist = new int[g.V];
        prev = new int[g.V];
        boolean[] visited = new boolean[g.V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[src] = 0;

        // PQ entry: {current_dist, node_id}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[] { 0, src });

        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int d = top[0], u = top[1];

            if (visited[u])
                continue; // skip stale entries
            visited[u] = true;

            // Relax all edges from u
            for (int[] edge : g.adj[u]) {
                int v = edge[0], w = edge[1];
                if (!visited[v] && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                    pq.offer(new int[] { dist[v], v });
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // Path Reconstruction via back-tracking through prev[]
    // ─────────────────────────────────────────────────────────────────
    static List<Integer> getPath(int dest) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int cur = dest; cur != -1; cur = prev[cur])
            path.addFirst(cur);
        return path;
    }

    // ─────────────────────────────────────────────────────────────────
    // Formatted Output
    // ─────────────────────────────────────────────────────────────────
    static void printResult(Graph g, int src, int dest, String label) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║  " + label);
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.printf("║  Source      : Node %-2d = %-37s -> %n", src, NODE_NAMES[src]);
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");

        if (dist[dest] == Integer.MAX_VALUE) {
            System.out.println("║   NO PATH FOUND : village is completely cut off.               ║");
            System.out.println("║   Recommend: RMAF/AADK helicopter evacuation.                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════╝");
            return;
        }

        List<Integer> path = getPath(dest);
        System.out.printf("║   Shortest Travel Time : %-38s║%n", dist[dest] + " minutes");
        System.out.print("║  Optimal Route         : ");
        StringBuilder routeSb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            routeSb.append(path.get(i));
            if (i < path.size() - 1)
                routeSb.append(" -> ");
        }
        System.out.printf("%-38s║%n", routeSb.toString());

        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.println("║  Segment Breakdown                                               ║");
        System.out.println("║  From -> To   Time (min)   Road Segment                          ║");
        System.out.println("║  ──────────────────────────────────────────────────────────────  ║");

        int total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int u = path.get(i), v = path.get(i + 1);
            int w = 0;
            for (int[] e : g.adj[u])
                if (e[0] == v) {
                    w = e[1];
                    break;
                }
            total += w;
            String seg = String.format("  %d -> %-6d%-11d%s -> %s",
                    u, v, w,
                    NODE_NAMES[u].substring(0, Math.min(13, NODE_NAMES[u].length())),
                    NODE_NAMES[v].substring(0, Math.min(14, NODE_NAMES[v].length())));
            System.out.printf("║  %-64s║%n", seg);
        }
        System.out.println("║  ──────────────────────────────────────────────────────────────  ║");
        System.out.printf("║  TOTAL                %-44s║%n", total + " minutes");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
    }

    static void printAllDistances() {
        System.out.println("\n  ── All Shortest Distances from Hospital ─────────────────────────");
        System.out.printf("  %-6s  %-14s  %-35s%n", "NodeID", "Time (min)", "Location");
        System.out.println("  " + "─".repeat(60));
        for (int v = 0; v < NODE_NAMES.length; v++) {
            String t = dist[v] == Integer.MAX_VALUE ? "Unreachable" : dist[v] + " min";
            System.out.printf("  %-6d  %-14s  %-35s%n", v, t, NODE_NAMES[v]);
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("\n=== LANDSLIDE MEDICAL EVACUATION ROUTE OPTIMISER ===");
        System.out.println("    CSC4202 Algorithm Design and Analysis — Group Project");
        System.out.println("    Scenario: 14 November 2025, Hulu Selangor, Malaysia\n");

        // ── SCENARIO A: Base road network (main highway BLOCKED by landslide) ──
        Graph g1 = new Graph(8);
        g1.addEdge(0, 1, 8); // Hospital KKB → Pekan KKB junction
        g1.addEdge(1, 2, 10); // Pekan KKB → Batang Kali fork
        g1.addEdge(1, 3, 18); // Pekan KKB → Sungai Chilling entrance
        g1.addEdge(2, 4, 20); // Batang Kali → Plantation estate
        g1.addEdge(2, 5, 36); // Batang Kali → Pos Gertak (damaged, slow)
        g1.addEdge(3, 5, 17); // Sungai Chilling → Pos Gertak (forest road)
        g1.addEdge(4, 6, 10); // Plantation → Hulu Bernam crossroad
        g1.addEdge(5, 7, 12); // Pos Gertak → Kg. Sungai Lui
        g1.addEdge(6, 7, 14); // Hulu Bernam crossroad → Kg. Sungai Lui
        // NOTE: Direct 0→7 highway is BLOCKED — edge deliberately omitted

        dijkstra(g1, 0);
        printResult(g1, 0, 7, "SCENARIO A: Post-Landslide (Main Highway Blocked)          ");
        printAllDistances();

        // ── SCENARIO B: Additional blockage — Sungai Chilling road (1→3) also blocked
        // ──
        Graph g2 = new Graph(8);
        g2.addEdge(0, 1, 8);
        g2.addEdge(1, 2, 10);
        // g2.addEdge(1, 3, 18); <-- REMOVED: road 1→3 now also blocked
        g2.addEdge(2, 4, 20);
        g2.addEdge(2, 5, 36);
        g2.addEdge(3, 5, 17);
        g2.addEdge(4, 6, 10);
        g2.addEdge(5, 7, 12);
        g2.addEdge(6, 7, 14);

        dijkstra(g2, 0);
        printResult(g2, 0, 7, "SCENARIO B: Additional Road Closure (1→3 also blocked)    ");

        // ── SCENARIO C: All logging routes blocked — village cut off ──
        Graph g3 = new Graph(8);
        g3.addEdge(0, 1, 8);
        // All routes from 1 onward to village are blocked
        // Only 0→1 edge exists

        dijkstra(g3, 0);
        printResult(g3, 0, 7, "SCENARIO C: Village Completely Isolated (No Path Exists)  ");
    }
}
