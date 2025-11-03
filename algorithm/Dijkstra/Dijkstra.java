import java.util.*;

public class dijkstra {
    static class Node implements Comparable<Node> {
        int idx, cost;
        Node(int i, int c) { idx = i; cost = c; }
        public int compareTo(Node o) { return cost - o.cost; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt(); // 정점
        int E = sc.nextInt(); // 간선
        int start = sc.nextInt();

        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= V; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < E; i++) {
            int a = sc.nextInt(), b = sc.nextInt(), w = sc.nextInt();
            graph.get(a).add(new Node(b, w));
        }

        int[] dist = new int[V + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.cost > dist[cur.idx]) continue;

            for (Node next : graph.get(cur.idx)) {
                if (dist[next.idx] > dist[cur.idx] + next.cost) {
                    dist[next.idx] = dist[cur.idx] + next.cost;
                    pq.offer(new Node(next.idx, dist[next.idx]));
                }
            }
        }

        for (int i = 1; i <= V; i++) {
            System.out.println((dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
        }
        sc.close();
    }
}
