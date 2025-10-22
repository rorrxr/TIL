import java.util.*;

public class dfs {
    static int[][] graph;
    static boolean[] visited;
    static int N;

    public static void dfs(int v) {
        visited[v] = true;
        System.out.print(v + " ");
        for (int i = 1; i <= N; i++) {
            if (graph[v][i] == 1 && !visited[i]) {
                dfs(i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        int M = sc.nextInt();
        int V = sc.nextInt();

        graph = new int[N + 1][N + 1];
        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a][b] = graph[b][a] = 1;
        }

        visited = new boolean[N + 1];
        dfs(V);
        sc.close();
    }
}
