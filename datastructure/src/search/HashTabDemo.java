package search;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author lilibo
 * @create 2022-01-30 12:18 AM
 */
public class HashTabDemo {

    public static void main(String[] args) {
        int numOfTest = 1000;
        HashTab hashTab = new HashTab(10);
        Stream.iterate(0, count -> count + 1).limit(numOfTest).forEach(count -> {
            Employee employee = new Employee(count, "小野胖夫" + count);
            hashTab.put(count, employee);
        });
        hashTab.print();

        System.out.println("id = 33的员工为: " + hashTab.get(33));
        System.out.println("删除id = 33的员工...");
        hashTab.remove(33);
        System.out.println("id = 33的员工为: " + hashTab.get(33));
    }
}

class HashTab {
    private final int capacity;

    private final DoubleLinkedList[] array;

    public HashTab(int capacity) {
        this.capacity = capacity;
        this.array = new DoubleLinkedList[capacity];
        for (int i = 0; i < array.length; i++) {
            array[i] = new DoubleLinkedList();
        }
    }

    public void put(int id, Employee employee) {
        int hash = hash(id);
        array[hash].addById(employee);
    }

    public Employee get(int id) {
        int hash = hash(id);
        return array[hash].getEmployee(id);
    }

    public void remove(int id) {
        int hash = hash(id);
        array[hash].deleteEmployee(id);
    }

    public void print() {
        for (int i = 0; i < capacity; i++) {
            System.out.println("第" + i + "条链表为...");
            array[i].print();
        }
    }

    public int hash(int id) {
        return (Math.abs(id)) % capacity;
    }
}

class DoubleLinkedList {

    private Employee head;

    public void addById(Employee employee) {
        if (employee == null) {
            return;
        }
        if (head == null) {
            head = employee;
            return;
        }
        int no = employee.getId();
        Employee temp = head;
        while (temp.getNext() != null) {
            if (temp.getNext().getId() >= no) {
                break;
            }
            temp = temp.getNext();
        }
        if (temp.getNext() == null) {
            employee.setPre(temp);
            temp.setNext(employee);
        } else {
            Employee oldNext = temp.getNext();
            if (no < temp.getNext().getId()) {
                oldNext.setPre(employee);
                employee.setNext(oldNext);
                employee.setPre(temp);
                temp.setNext(employee);
            } else if (no == temp.getNext().getId()) {
                oldNext.setName(employee.getName());
            }
        }
    }

    public Employee getEmployee(int id) {
        if (head == null) {
            return null;
        }
        Employee temp = head;
        while (temp != null) {
            if (temp.getId() == id) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

    public void deleteEmployee(int id) {
        if (head == null) {
            return;
        }
        if (head.getId() == id) {
            Employee oldNext = head.getNext();
            oldNext.setPre(null);
            head = oldNext;
            return;
        }
        Employee temp = head;
        while (temp != null) {
            if (temp.getId() == id) {
                Employee oldNext = temp.getNext();
                Employee oldPre = temp.getPre();
                oldPre.setNext(oldNext);
                oldNext.setPre(oldPre);
                return;
            }
            temp = temp.getNext();
        }
    }

    public void print() {
        if (head == null) {
            return;
        }
        Employee temp = head;
        while (temp != null) {
            System.out.print(temp + "->");
            temp = temp.getNext();
        }
        System.out.println();
    }
}

class Employee {

    private int id;

    private String name;

    private Employee pre;

    private Employee next;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getPre() {
        return pre;
    }

    public void setPre(Employee pre) {
        this.pre = pre;
    }

    public Employee getNext() {
        return next;
    }

    public void setNext(Employee next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
