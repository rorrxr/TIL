import java.util.*;

public class bfs {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 노드 수
        int M = sc.nextInt(); // 간선 수
        int V = sc.nextInt(); // 시작 노드

        int[][] graph = new int[N + 1][N + 1];
        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a][b] = graph[b][a] = 1;
        }

        boolean[] visited = new boolean[N + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(V);
        visited[V] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");
            for (int i = 1; i <= N; i++) {
                if (!visited[i] && graph[current][i] == 1) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }
        sc.close();
    }
}
