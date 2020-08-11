package utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.stream.Stream;

public class PeekIterator<T> implements Iterator<T> {

    private static final int CACHE_SIZE = 10;
    private Iterator<T> it;
    private Deque<T> queueCache = new ArrayDeque<>();
    private Deque<T> stackPutBacks = new ArrayDeque<>();
    private T _endToken = null;

    public PeekIterator(Stream<T> stream) {
        this.it = stream.iterator();
    }

    // 为了之后的场景做的准备，即链表的最后一项是一个 token
    public PeekIterator(Stream<T> stream, T _endToken) {
        this(stream);
        this._endToken = _endToken;
    }

    public T peek() {
        if (!stackPutBacks.isEmpty()) {
            return stackPutBacks.getFirst();
        }
        if (!it.hasNext()) {
            return _endToken;
        }
        T value = next();
        pubBack();
        return value;
    }

    /**
     * 根据我们的 next() 方法，
     * 假设 queueCache 中是 A、B、C、D
     * 则表示 D 是最后拿出来的，所以 pubBack 的时候，要从 D 先放回
     */
    public void pubBack() {
        if (queueCache.isEmpty()) {
            return;
        }
        stackPutBacks.push(queueCache.pollLast());
    }

    @Override
    public boolean hasNext() {
        return _endToken != null || !stackPutBacks.isEmpty() || it.hasNext();
    }

    @Override
    public T next() {
        T value;
        if (stackPutBacks.isEmpty()) {
            if (!it.hasNext()) {
                T tmp = _endToken;
                _endToken = null;
                return tmp;
            }
            value = it.next();
        } else {
            value = stackPutBacks.pop();
        }
        // 最多保存 CACHE_SIZE 个
        while (queueCache.size() >= CACHE_SIZE) {
            queueCache.poll();
        }
        queueCache.add(value);
        return value;
    }
}
