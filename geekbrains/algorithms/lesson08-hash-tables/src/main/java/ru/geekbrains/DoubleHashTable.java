package ru.geekbrains;

public class DoubleHashTable<K, V> extends HashTableImpl<K, V> {

    public DoubleHashTable(int initialCapacity) {
        super(initialCapacity);
    }

    private int hashDoubleFunc(K key) {
        return 5 - (key.hashCode() % 5);
    }

    @Override
    protected int getStep(K key) {
        return hashDoubleFunc(key);
    }
}
