public class LinkedList{

    private class Node {
        String data;
        Node next;

        public Node(String data) {
            this.data = data;
            this.next = null;        
        }

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
    Node head, tail;
    int size;
	
    public LinkedList() {
		head = null;
        tail = null;
        size = 0;
	} //constructor

    
    public String get(int index) {
        Node current = head;
        String currentd = "";
        
        for (int i = 0; i <= index; i++){
            if (current != null) {
                if (i == index)
                    currentd = (current.data);
                
                current = current.next;   
            } 
        }
        return currentd;
    
    } //get

    public void add(String data) {
        Node new_node = new Node(data);
        
        if (head == null)
            head = new_node;
        else {
            Node current = head;
            
            while (current.next != null) {
                current = current.next;
            }
            
            current.next = new_node;
		}
            size++;
    } 


    //Adds node to given position while there
   

    public void add(String obj, int pos) {
        Node new_node = new Node(obj, null);
        Node temp = null;
        Node current = head;
            
        for (int i = 0; i < pos; i++)
            if (current.next != null) {
                if (i == (pos - 1)){
                    if (pos == 0){

                    temp = head;
                    head = new_node;
                    new_node.next = temp;
                    }
                    
                    else{
                        temp = current;
                        current = new_node;
                        new_node.next = temp;
                        break; 
                    }
                }
            current = current.next;
            }
        size();
    } 

    //Removes node at given index number 
                    //remove

    public void remove(int index) {

        Node current = head;
        Node temp = null;
        Node prev = null;
        int counter = 0;

        while (current != null) {
            if (counter == index) {
                temp = current;
                break;

            }
            prev = current;
            current = current.next;
        }

        if (prev == null) {
            head = current.next;
            current = null;
        } else {
            prev.next = current.next;
            current = null;
        }
        size();
    } 

    //follows current.next until null, printing out the data and ->
                            //remove

    public void print() {
        Node current = head;
        
        while (current != null) {
            if (current.next != null)
                System.out.print(current.data + "->");
            else
                System.out.println(current.data);
            
            current = current.next;
        }
        System.out.println("Size of list: " + size());
    }
    
    // Iterates and keeps track of # of iterations in counter.
        //returns counter 
                    
                            //size

    public int size() {
        int counter = 0;
        Node current = head;
            
            while (current != null){  
                current = current.next;
                counter++;
            }
        return counter;
    }            

    
}