# DFS (깊이 우선 탐색)
# 재귀를 이용해 깊은 노드부터 탐색

N, M, V = map(int, input().split())

graph = [[0]*(N+1) for _ in range(N+1)]
for _ in range(M):
    a, b = map(int, input().split())
    graph[a][b] = graph[b][a] = 1

visited = [0]*(N+1)

def dfs(V):
    visited[V] = 1
    print(V, end=' ')
    for i in range(1, N+1):
        if not visited[i] and graph[V][i] == 1:
            dfs(i)

dfs(V)
