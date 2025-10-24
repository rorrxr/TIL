import java.util.*;

public class queue {
    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<>();

        q.offer(1);
        q.offer(2);
        q.offer(3);
        System.out.println("현재 큐: " + q);

        System.out.println("poll: " + q.poll());
        System.out.println("poll: " + q.poll());
        System.out.println("남은 큐: " + q);
    }
}
