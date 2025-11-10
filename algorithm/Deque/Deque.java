import java.util.*;

public class deque {
    public static void main(String[] args) {
        Deque<Integer> dq = new LinkedList<>();

        dq.addFirst(2);
        dq.addLast(1);
        dq.addLast(3);
        System.out.println("현재 덱: " + dq);

        System.out.println("removeFirst: " + dq.removeFirst());
        System.out.println("removeLast: " + dq.removeLast());
        System.out.println("남은 덱: " + dq);
    }
}
