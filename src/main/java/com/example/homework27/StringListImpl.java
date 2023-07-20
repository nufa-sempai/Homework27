package com.example.homework27;

import com.example.homework27.exceptions.BadIndexException;
import com.example.homework27.exceptions.InvalidItemException;
import com.example.homework27.exceptions.StorageIsFullException;

import java.util.Arrays;

public class StringListImpl implements StringList {

    private String[] storage;
    private int size;

    public StringListImpl() {
        storage = new String[4];
    }

    public StringListImpl(int storageCapacity) {
        storage = new String[storageCapacity];
    }

    @Override
    public String add(String item) {
        checkSize();
        checkItem(item);
        storage[size++] = item;
        return item;
    }

    @Override
    public String add(int index, String item) {
        checkSize();
        checkItem(item);
        checkIndex(index);

        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = item;
        return item;
    }

    @Override
    public String set(int index, String item) {
        checkItem(item);
        checkIndex(index);
        storage[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        checkItem(item);
        int index = indexOf(item);

        if (index == -1) {
            throw new InvalidItemException();
        }

        if (index != size) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
            size--;
        }

        size--;
        return item;
    }

    @Override
    public String remove(int index) {
        checkIndex(index);
        String item = storage[index];

        if (index != storage.length - 1) {
            System.arraycopy(storage, index + 1, storage, index, storage.length - size);
            size--;
        }

        size--;
        return item;

    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        checkItem(item);
        for (int i = 0; i < size; i++) {
            if (item.equals(storage[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        checkItem(item);
        for (int i = size - 1; i >= 0; i--) {
            if (item.equals(storage[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        checkIndex(index);
        return storage[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        return Arrays.equals(this.toArray(), otherList.toArray());
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
    public void clear() {
        size = 0;
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(storage, size);
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new BadIndexException();
        }
    }

    private void checkItem(String item) {
        if (item == null) {
            throw new InvalidItemException();
        }
    }

    private void checkSize() {
        if (size == storage.length) {
            throw new StorageIsFullException();
        }
    }
}