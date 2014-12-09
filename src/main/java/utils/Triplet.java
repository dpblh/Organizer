package utils;

/**
 * @author Bayurov
 * Кортеж.
 * Удобная плюшка для хранения и передачи данных
 */
public class Triplet<A, B, C> {
    public A first;
    public B second;
    public C third;

    public Triplet(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
