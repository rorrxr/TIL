import java.util.*;

public class segment_tree {
    static int[] tree;
    static int[] arr;
    static int n;

    static int init(int node, int start, int end) {
        if (start == end) return tree[node] = arr[start];
        int mid = (start + end) / 2;
        return tree[node] = init(node*2, start, mid) + init(node*2+1, mid+1, end);
    }

    static int sum(int node, int start, int end, int left, int right) {
        if (right < start || end < left) return 0;
        if (left <= start && end <= right) return tree[node];
        int mid = (start + end) / 2;
        return sum(node*2, start, mid, left, right) + sum(node*2+1, mid+1, end, left, right);
    }

    static void update(int node, int start, int end, int idx, int diff) {
        if (idx < start || idx > end) return;
        tree[node] += diff;
        if (start != end) {
            int mid = (start + end) / 2;
            update(node*2, start, mid, idx, diff);
            update(node*2+1, mid+1, end, idx, diff);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        tree = new int[4 * n];
        init(1, 0, n - 1);

        System.out.println(sum(1, 0, n - 1, 0, n - 1));
        update(1, 0, n - 1, 2, 3);
        System.out.println(sum(1, 0, n - 1, 0, n - 1));

        sc.close();
    }
}
