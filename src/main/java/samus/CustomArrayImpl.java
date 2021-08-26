package samus;

import java.util.ArrayList;
import java.util.Collection;

public class CustomArrayImpl<T> implements CustomArray<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;

    public CustomArrayImpl() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public CustomArrayImpl(int capacity) {
        if (capacity > 0) {
            data = new Object[capacity];
            size = capacity;
        } else {
            throw new IllegalArgumentException ("Некорректное значение capacity: " + capacity);
        }

    }

    public CustomArrayImpl(Collection<T> c) {
        nullCheck(c);
        data = c.toArray();
        size = data.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T item) {
        ensureCapacity(1);
        data[size] = item;
        size++;
        return true;
    }

    @Override
    public boolean addAll(T[] items) {
        nullCheck(items);

        int newSize = size + items.length;
        this.ensureCapacity(newSize);
        int n = 0;
        for (int i = size; i < newSize; i++, n++) {
            data[i] = items[n];
        }
        size = newSize;

        return true;

    }

    @Override
    public boolean addAll(Collection<T> items) {
        nullCheck(items);

        Object[] newData = items.toArray();
        int newSize = size + newData.length;
        this.ensureCapacity(newSize);
        int n = 0;
        for (int i = size; i < newSize; i++, n++) {
            data[i] = newData[n];
        }
        size = newSize;

        return true;
    }

    @Override
    public boolean addAll(int index, T[] items) {
        rangeCheck(index);
        nullCheck(items);

        int newSize = size + items.length;
        Object[] newData = new Object[newSize];

        for (int i = 0; i < index; i++) {
            newData[i] = data[i];
        }

        int addIndexStart = index;
        int addIndexFinish = index + items.length;
        for (int n = 0; addIndexStart < addIndexFinish; addIndexStart++, n++) {
            newData[addIndexStart] = items[n];
        }

        int nextIndex = addIndexFinish;
        int tmpIndex = index;
        for (int i = nextIndex; i < newSize; i++) {
            newData[i] = data[tmpIndex++];
        }

        data = newData;
        size = newSize;

        return true;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return (T) data[index];
    }

    @Override
    public T set(int index, T item) {
        rangeCheck(index);
        T oldValue = (T) data[index];
        data[index] = item;
        return oldValue;
    }

    @Override
    public void remove(int index) {
        rangeCheck(index);
        Object[] newData = new Object[size - 1];
        int n = 0;
        for (int i = 0; i < size; i++) {
            if (i != index) {
                newData[n++] = data[i];
            }
        }
        --size;
        data = newData;
    }

    @Override
    public boolean remove(T item) {
        Object[] newData = new Object[size - 1];
        int n = 0;
        int index = 0;
        boolean result = false;

        if (item == null) {
            for (; index < size; index++) {
                if (data[index] == null) {
                    result = true;
                    break;
                }
                if (index == size - 1) {
                    return false;
                }
                newData[n++] = data[index];
            }

        } else {
            for (; index < size; index++) {
                if (item.equals(data[index])) {
                    result = true;
                    break;
                }
                if (index == size - 1) {
                    return false;
                }
                newData[n++] = data[index];
            }

        }
        index++;
        for (; index < size; index++) {
            newData[n++] = data[index];
        }

        --size;
        data = newData;

        return result;
    }

    @Override
    public boolean contains(T item) {
        if (item == null) {
            for (int i = 0; i < size; i++)
                if (data[i] == null)
                    return true;
        } else {
            for (int i = 0; i < size; i++)
                if (item.equals(data[i]))
                    return true;
        }
        return false;
    }

    @Override
    public int indexOf(T item) {
        if (item == null) {
            for (int i = 0; i < size; i++)
                if (data[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (item.equals(data[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public void ensureCapacity(int newElementsCount) {

        int needSize = size + newElementsCount;
        if (needSize <= data.length) {
            return;
        }

        int newCapacity = data.length * 2;
        if (needSize > newCapacity) {
            newCapacity = needSize;
        }

        Object[] newData = new Object[newCapacity];
        for (int i = 0; i < data.length; ++i) {
            newData[i] = data[i];
        }
        data = newData;
    }

    @Override
    public int getCapacity() {
        return data.length;
    }

    @Override
    public void reverse() {
        Object[] reverseData = new Object[size];
        int n = 0;
        for (int i = size - 1; i >= 0; i--, n++) {
            reverseData[n] = data[i];
        }
        data = reverseData;
    }

    @Override
    public Object[] toArray() {
        Object[] newData = new Object[size];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        return newData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; ++i) {
            sb.append(" " + data[i]);
        }
        sb.append(" ]");
        return sb.toString();
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("" + index);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект для добавления не может быть null!");
        }
    }

}
