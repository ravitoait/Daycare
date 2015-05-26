import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh
 * Daycare.java
 */

/*
 * Class for creating Node
 */
class cell{

    int weight; // for indicating the weight
    boolean edgeTaken; // for indicating if the edge has been draw
    boolean selected; // if the edge is selected
    int vertexNumber; // the vertex number

    cell(){
        this.weight =0;
        this.edgeTaken = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void setVertexNumber(int vertexNumber) {
        this.vertexNumber = vertexNumber;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setEdgeTaken(boolean edgeTaken) {
        this.edgeTaken = edgeTaken;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isEdgeTaken() {
        return edgeTaken;
    }
}

/*
 * Class for creating Queue
 */
class QueueArray<cell> {
    private class Node {
        public cell data;
        public Node next;
        public Node(cell data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node head = null;
    private Node tail = null;
    // enqueue in the queue
    public void enqueue(cell item) {
        Node newNode = new Node(item, null);
        if (isEmpty()) {head = newNode;} else {tail.next = newNode;}
        tail = newNode;
    }
    // dequeue from the queue

    public cell dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        cell item = head.data;
        if (tail == head) {
            tail = null;
        }
        head = head.next;
        return item;
    }
    // getting from front of the queue
    public cell peek() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.data;
    }
    // checking if the queue is empty
    public boolean isEmpty() {
        return head == null;
    }
    // if queue is empty
    public int size() {
        int count = 0;
        for (Node node = head; node != null; node = node.next) {
            count++;
        }
        return count;
    }
}

/*
 * Class for creating Node
 */
public class Daycare {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String str = ""; // for printing the selected edge
        boolean result;
        int numberOfStudent = sc.nextInt(); // number of student
        int numberOfHats = sc.nextInt(); // number of Hats
        int numberOfMittens = sc.nextInt(); // number of Mittens
        int numberOfJackets = sc.nextInt(); // number of Jackets
        int numberOfVertex = numberOfHats + numberOfMittens + numberOfJackets + 2; // total vertex to draw
        String[] arrayOfResult = new String[4 * numberOfStudent]; // used in printing the edges
        cell[][] graph = new cell[numberOfVertex][numberOfVertex]; // the 2D graph
        int[][] arraysOfArrays = new int[3 * numberOfStudent][];
        int[] sum = new int[numberOfStudent]; // for checking if the number of option of a student is less
        int indexforArray = 0;
        String checking;
        boolean found = false;
        int index = 0;

        if (numberOfHats < numberOfStudent || numberOfJackets < numberOfStudent || numberOfMittens < numberOfStudent) {
            System.out.println("NO");
        } else {
            // intilizing the arrayResult
            for (int i = 0; i < arrayOfResult.length; i++) {
                arrayOfResult[i] = " ";
            }

            // intilizing the graph
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph.length; j++) {
                    graph[i][j] = new cell();
                    graph[i][j].setEdgeTaken(false);
                    graph[i][j].setVertexNumber(i);
                    graph[i][j].setSelected(false);
                }
            }
            // reading the inputs
            sc.nextLine();

            // intilizing the arraysOfArrays
            for (int i = 0; i < arraysOfArrays.length; i++) {
                String[] lineForHats = sc.nextLine().split(" ");
                arraysOfArrays[i] = new int[lineForHats.length - 1];
                for (int j = 0; j < lineForHats.length - 1; j++) {
                    arraysOfArrays[i][j] = Integer.parseInt(lineForHats[j]);
                }
            }

            // populating the array of number of options of a student
            int numStudentForArray = 0;
            for (int i = 0; i < arraysOfArrays.length; i = i + 3) {
                sum[numStudentForArray] = arraysOfArrays[i].length + arraysOfArrays[i + 1].length + arraysOfArrays[i + 2].length;
                numStudentForArray++;
            }

            int countOfStudent = numberOfStudent;
            do {
                int forCheck = Integer.MAX_VALUE;
                int i;
                int l = 0;

                for (i = 0; i < sum.length; i++) {
                    if (sum[i] < forCheck) {
                        forCheck = sum[i];
                    }
                }
                // checking the student with least options
                for (l = 0; l < sum.length; l++) {
                    if (sum[l] == forCheck) {
                        break;
                    }
                }
                // filling the graph based on the student with least option aviable
                if (fillingGraph(graph, numberOfStudent, numberOfHats, numberOfMittens, numberOfJackets, arraysOfArrays, l)) {
                    result = true;
                } else {
                    // if returns false
                    result = false;
                    System.out.println("NO");
                    break;
                }
                sum[l] = Integer.MAX_VALUE;
                countOfStudent--;
                int resultIndex;

                // for printing the results
                for (int k = 0; k < graph.length; k++) {
                    for (int j = 0; j < graph.length; j++) {
                        if (graph[k][j].isEdgeTaken() == true && graph[k][j].getWeight() > 0 && graph[k][j].isSelected() == true) {
                            checking = k + " " + j;
                            for (int h = 0; h < arrayOfResult.length; h++) {
                                if (!arrayOfResult[h].equals(checking)) {
                                    found = false;
                                    continue;
                                } else {
                                    found = true;
                                    break;
                                }
                            }
                            if (found == false) {
                                arrayOfResult[indexforArray] = checking;
                                indexforArray++;
                            }
                        }
                    }
                }

                for (int h = index; h < index + 4; h++) {
                    int des = Integer.parseInt(arrayOfResult[h].split(" ")[1]);
                    if (des < graph.length - 1) {
                        if (des <= numberOfHats) {
                            str = str.concat(des + " ");
                        } else if (des >= numberOfHats + 1 && des <= numberOfHats + numberOfMittens) {
                            resultIndex = des - numberOfHats;
                            str = str.concat(resultIndex + " ");
                        } else if (des >= numberOfHats + numberOfMittens + 1) {
                            resultIndex = des - (numberOfHats + numberOfMittens);
                            str = str.concat(resultIndex + "");
                        }
                    }
                }
                index = index + 4;
                str = str.concat("\n");
            } while (countOfStudent > 0);
            if (result == true) {
                System.out.println("YES");
                System.out.print(str);
            }
        }
    }

    private static boolean fillingGraph(cell[][] graph, int numberOfStudent, int numberOfHats, int numberOfMittens, int numberOfJackets, int[][] arraysOfArrays, int index) {
        index =index*3;
        // filling the arrays
        for( int j =0 ; j < arraysOfArrays[index].length ;j++) {
            graph[0][arraysOfArrays[index][j]].weight = 1;
        }

        for( int i =0 ; i < arraysOfArrays[index].length ;i++){
            for( int j =0 ; j < arraysOfArrays[index+1].length ;j++) {
                graph[arraysOfArrays[index][i]][arraysOfArrays[index+1][j]+numberOfHats].weight = 1;
            }
        }

        for( int i =0 ; i < arraysOfArrays[index+1].length ;i++){
            for( int j =0 ; j < arraysOfArrays[index+2].length ;j++) {
                graph[arraysOfArrays[index+1][i]+numberOfHats][arraysOfArrays[index+2][j]+ numberOfHats+ numberOfMittens].weight = 1;
            }
        }

        for( int i =0; i < arraysOfArrays[index+2].length ;i++){
            graph[arraysOfArrays[index+2][i]+ numberOfHats+ numberOfMittens][graph.length-1].weight=1;
        }
        // finding the disjoint set from the source to destination
        if(findingTheDisjointSet(graph, graph[0][0], graph[graph.length-1][graph.length-1], arraysOfArrays, index)){
            return true;
        }
        return false;
    }

    private static boolean findingTheDisjointSet(cell[][] graph, cell source, cell destination, int[][] arraysOfArrays, int index) {
        // doing Breadth first search
        if(bfs(graph, source, destination, arraysOfArrays, index)){
            return true;
        }
        return false;
    }

    private static boolean bfs(cell[][] graph, cell source, cell destination, int[][] arraysOfArrays, int index) {
        boolean reachedToTheDestination= false;
        index = index*3;
        QueueArray<cell> queue = new QueueArray<cell>();
        // enquqing the source
        queue.enqueue(source);
        source.setEdgeTaken(true);
        while (!queue.isEmpty()){
            cell edgeChosen = queue.dequeue();
            for( int v =1;  v< graph.length ;v++){
                // if the edge is present and not an edge is make
                if( graph[edgeChosen.getVertexNumber()][v].weight>0 && graph[edgeChosen.getVertexNumber()][v].isEdgeTaken()== false ){
                    graph[edgeChosen.getVertexNumber()][v].setSelected(true);
                    queue.enqueue(graph[v][v]);
                    if( v==destination.getVertexNumber()){
                        reachedToTheDestination = true;
                        graph[edgeChosen.getVertexNumber()][v].setEdgeTaken(true);
                    }else {
                        for (int i = 0; i < v; i++) {
                            if ( v != destination.getVertexNumber()) {
                                graph[i][v].setEdgeTaken(true);
                            }
                        }
                    }
                    for( int p =0; p < graph.length;p++) {
                        if (graph[edgeChosen.getVertexNumber()][p].isSelected() == false) {
                            graph[edgeChosen.getVertexNumber()][p].weight = 0;
                        }
                    }
                    break;
                }

            }
        }
        // if the destination is reached
        for( int v =0 ; v < graph.length ;v++){
            if( graph[v][destination.getVertexNumber()].isEdgeTaken()==true && reachedToTheDestination==true){
                return true;
            }

        }
        return false;
    }
}
