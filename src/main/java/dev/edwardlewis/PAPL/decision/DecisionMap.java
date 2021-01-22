package dev.edwardlewis.PAPL.decision;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DecisionMap {

    private DecisionNode head;
    private DecisionNode tail;

    public DecisionMap() throws FileNotFoundException {
        Scanner inFile = connectDataSet("src/main/resources/data.csv");
        buildUnorderedList(inFile);
        try {
            buildOrderedMap();
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
    }

    private void append(DecisionNode newNode) {
        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
            this.tail.setLinkedNode(null);

            return;
        }

        this.tail.setLinkedNode(newNode);
        this.tail = newNode;
    }


    public Scanner connectDataSet(String pathName) throws FileNotFoundException {
        File prc = new File(pathName);
        return new Scanner(prc);
    }

    public void buildUnorderedList(Scanner dataSet) {
        dataSet.useDelimiter(",");
        DecisionNode node;
        do {
            String line = dataSet.nextLine();
            node = buildNode(line);
            append(node);
        } while (dataSet.hasNext());
        dataSet.close();
    }

    private void buildOrderedMap() {
        if (head == null) {
            throw new NullPointerException();
        }

        DecisionNode nodeLinker = head;

        while (nodeLinker != null) {

            int pathOneID = nodeLinker.getPathOneID();
            int pathTwoID = nodeLinker.getPathTwoID();

            DecisionNode pathOneNode = nodeFetch(pathOneID);
            DecisionNode pathTwoNode = nodeFetch(pathTwoID);

            nodeLinker.setPathOne(pathOneNode);
            nodeLinker.setPathTwo(pathTwoNode);

            nodeLinker = nodeLinker.getLinkedNode();

        }

        cleanup();

    }

    private void cleanup() {
        if (head == null) return;

        DecisionNode currentNode = head;
        DecisionNode nextNode = head.getLinkedNode();

        while (nextNode != null) {

            currentNode.setLinkedNode(null);

            currentNode = nextNode;
            nextNode = currentNode.getLinkedNode();
        }
    }

    private DecisionNode buildNode(String line) {
        String[] stringArray = line.split(",");
        DecisionNode decisionNode = new DecisionNode();

        decisionNode.setNodeID(stringArray[0].equals("-") ? -1 : Integer.parseInt(stringArray[0]));
        decisionNode.setPathOneID(stringArray[1].equals("-") ? -1 : Integer.parseInt(stringArray[1]));
        decisionNode.setPathTwoID(stringArray[2].equals("-") ? -1 : Integer.parseInt(stringArray[2]));

        decisionNode.setDescription(stringArray[3]);
        decisionNode.setQuestionOne(stringArray[4]);
        decisionNode.setQuestionTwo(stringArray[5]);

        return decisionNode;
    }

    public DecisionNode entryPoint() {
        return head;
    }

    private DecisionNode nodeFetch(int nodeID) {

        DecisionNode nodeLinker = head;

        while (nodeLinker != null) {
            if (nodeLinker.getNodeID() == nodeID) {
                break;
            }
            nodeLinker = nodeLinker.getLinkedNode();
        }

        return nodeLinker;
    }

    private boolean isEmpty() {
        return this.head == null;
    }
}
