package student.collection;

import student.Student;

import java.util.AbstractList;
import java.util.Arrays;

public class StudentList extends AbstractList<Student> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public StudentList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    public StudentList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Начальная ёмкость не может быть отрицательной");
        }
        this.elements = new Object[Math.max(initialCapacity, DEFAULT_CAPACITY)];
    }

    @Override
    public Student get(int index) {
        checkIndex(index);
        return (Student) elements[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Student set(int index, Student element) {
        checkIndex(index);
        Student old = (Student) elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, Student element) {
        checkIndex(index);
        if (size == elements.length) {
            grow();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
        modCount++;
    }

    @Override
    public Student remove(int index) {
        checkIndex(index);
        Student old = (Student) elements[index];
        int moved = size - index - 1;
        if (moved > 0) {
            System.arraycopy(elements, index + 1, elements, index, moved);
        }
        elements[--size] = null;
        modCount++;
        return old;
    }

    @Override
    public void clear() {
        Arrays.fill(elements, 0, size, null);
        size = 0;
        modCount++;
    }

    private void grow() {
        int newCapacity = elements.length + (elements.length >> 1);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(String.format("Индекс: %d, размер: %d", index, size()));
        }
    }
}