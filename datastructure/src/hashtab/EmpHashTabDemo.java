package hashtab;

/**
 * google 公司的一个上机题:
 * 有一个公司,当有新的员工来报道时,要求将该员工的信息加入(id,性别,年龄,名字,住址..),当输入该员工的 id 时, 要求查找到该员工的 所有信息.
 * 要求:
 * 1) 不使用数据库,,速度越快越好=>哈希表(散列)
 * 2) 添加时，保证按照 id 从低到高插入 [课后思考：如果 id 不是从低到高插入，但要求各条链表仍是从低到高，怎么解决?]
 * 3) 使用链表来实现哈希表, 该链表不带表头[即: 链表的第一个结点就存放雇员信息]
 * @author lilibo
 * @create 2021-08-09 8:51 PM
 */
public class EmpHashTabDemo {

    public static void main(String[] args) {
        EmpHashTab empHashTab = new EmpHashTab(5);
        for(int i = 0; i < 20; i++) {
            empHashTab.put(new Employee(i, "pig-" + i, "USA-" + i));
        }
        for(int i = 0; i< 20; i++){
            System.out.println("第" + i + "号员工为: " + empHashTab.get(i));
        }
        empHashTab.print();
        // 删除元素
        empHashTab.delete(3, 7, 16, 13, 11, 9);
        System.out.println("******删除元素后的链表为******");
        empHashTab.print();
    }

}

class EmpHashTab {
    private final int capacity;

    private EmployeeLinkedList[] array;

    public void delete(int... ids) {
        for (int id :ids){
            int index = hashFun(id);
            array[index].remove(id);
        }
    }

    EmpHashTab(int capacity) {
        this.capacity = capacity;
        //初始化链表数组
        array = new EmployeeLinkedList[capacity];
        for(int i = 0; i<capacity; i++){
            array[i] = new EmployeeLinkedList();
        }
    }

    public void put(Employee employee){
        int index = hashFun(employee.getId());
        array[index].add(employee);
    }

    public Employee get(int id){
        int index = hashFun(id);
        return array[index].getById(id);
    }

    public void print(){
        for(int i = 0; i < capacity; i++){
            System.out.println("第" + i + "条链表为:");
            array[i].print();
        }
    }

    private int hashFun(int id) {
        return Math.abs(id) % capacity;
    }
}

class EmployeeLinkedList {
    private Employee head;

    public void add(Employee node){
        if(head == null){
            head = node;
            return;
        }
        // 定义1个辅助指针
        Employee temp = head;
        while (temp.getNext()!=null){
            temp = temp.getNext();
        }
        // 设置前驱和后置
        node.setPre(temp);
        temp.setNext(node);
    }

    public void addById(Employee node){
        if(head == null){
            head = node;
            return;
        }
        if(node.getId() <= head.getId()){
            node.setNext(head);
            head = node;
            return;
        }
        // 定义1个辅助指针
        Employee temp = head;
        while (temp.getNext()!=null){
            if(temp.getNext().getId() >= node.getId()){
                break;
            }
            temp = temp.getNext();
        }
        Employee last = temp.getNext();
        node.setPre(temp);
        node.setNext(last);
        temp.setNext(node);
        if(last != null){
            last.setPre(node);
        }
    }

    public void print(){
        if(head == null){
            System.out.println("链表为空!");
            return;
        }
        Employee temp = head;
        while(temp!=null){
            System.out.printf(temp + " -> ");
            temp = temp.getNext();
        }
        System.out.println();
    }

    public void remove(int no) {
        if(head == null){
            System.out.println("链表为空!");
            return;
        }
        if(head.getId() == no){
            head = head.getNext();
            return;
        }
        Employee temp = head;
        while (temp!=null){
            if(temp.getId() == no){
                break;
            }
            temp = temp.getNext();
        }
        if(temp == null){
            System.out.println("没找到要删除的序号no = " + no);
            return;
        }
        Employee last = temp.getNext();
        Employee pre = temp.getPre();
        pre.setNext(last);
        if(last!=null){
            last.setPre(temp);
        }
    }

    public Employee getById(int no) {
        if(head == null){
            return null;
        }
        Employee temp = head;
        while (temp != null){
            if(temp.getId() == no) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

}

class Employee {
    private int id;

    private String name;

    private String address;

    private Employee pre;

    private Employee next;

    public Employee(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}