package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    public int front, back, original;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        front = 0;
        back = 0;
        original = 4;
    }

    /**
     * convert the relative distance from the origin to the absolute index, mode 1 for front, 2 for back
     */
    public int Indexconvert(int mode, int distance) {
        if (mode == 1) {
            int max_room = items.length - original - 1; // remaining room for front
            if (distance <= max_room) {
                return original + distance;
            } else {
                int exceeded = distance - max_room;
                return exceeded - 1;
            }
        } else if (mode == 2) {
            int max_room = original;
            if (distance <= max_room) {
                return original - distance;
            } else {
                int exceeded = distance - max_room;
                return items.length - exceeded;
            }
        }
        return 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int new_original = capacity / 2;
        int max_room_front = items.length - original - 1;
        int max_room_back = original;
        if (front <= max_room_front && back <= max_room_back) {
            System.arraycopy(items, original + 1, a, new_original + 1, front);
            System.arraycopy(items, original - back, a, new_original - back, back);
        } else if (front > max_room_front) {
            System.arraycopy(items, original + 1, a, new_original + 1, max_room_front);
            System.arraycopy(items, 0, a, new_original + 1 + max_room_front, front - max_room_front);
            System.arraycopy(items, original - back, a, new_original - back, back);
        } else {
            System.arraycopy(items, original + 1, a, new_original + 1, front);
            System.arraycopy(items, 0, a, new_original - max_room_back, max_room_back);
            System.arraycopy(items, items.length - back + max_room_back, a, new_original - back, back - max_room_back);
        }
        original = new_original;
        items = a;
    }

    public void addFirst(T x) {
        if (size >= items.length - 1) {
            resize(items.length * 2);
        }
        front += 1;
        items[Indexconvert(1, front)] = x;
        size += 1;
    }

    public int size() {
        return size;
    }

    public void addLast(T x) {
        if (size >= items.length - 1) {
            resize(items.length * 2);
        }
        back += 1;
        items[Indexconvert(2, back)] = x;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        if (isEmpty()) {
            System.out.println("Empty deque");
        } else {
            if (front > 0) {
                for (int i = front; i > 0; i -= 1) {
                    System.out.print(items[Indexconvert(1, i)]);
                    System.out.print(" ");
                }
            }
            if (back > 0) {
                for (int i = 1; i <= back; i += 1) {
                    System.out.print(items[Indexconvert(2, i)]);
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T returned;
        double usage = (double) size / items.length;
        if (usage < 0.25 && items.length > 8) {
            resize(items.length / 2);
        }
        if (front > 0) {
            returned = items[Indexconvert(1, front)];
            items[Indexconvert(1, front)] = null;
            front -= 1;
        } else {
            returned = items[Indexconvert(2, 1)];
            items[Indexconvert(2, 1)] = null;
            if (original != 0) {
                original -= 1;
            } else {
                original = items.length - 1;
            }
            back -= 1;
        }
        size -= 1;
        return returned;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T returned;
        double usage = (double) size / items.length;
        if (usage < 0.25 && items.length > 8) {
            resize(items.length / 2);
        }
        if (back > 0) {
            returned = items[Indexconvert(2, back)];
            items[Indexconvert(2, back)] = null;
            back -= 1;
        } else {
            returned = items[Indexconvert(1, 1)];
            items[Indexconvert(1, 1)] = null;
            if (original != items.length - 1) {
                original += 1;
            } else {
                original = 0;
            }
            front -= 1;
        }
        size -= 1;
        return returned;
    }

    public T get(int index) {
        if (index + 1 <= front) {
            return items[Indexconvert(1, front - index)];
        } else {
            return items[Indexconvert(2, index + 1 - front)];
        }
    }
}


