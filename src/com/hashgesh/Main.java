package com.hashgesh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Main {

    static Node head;

    static HashMap<String, Integer> hmap;

    public static void main(String[] args) {

        List<String> list = readFile("source.txt");

        init();

        addContentToHashMap(list);

        hmap = sortHashMapByValues();

        insertHashMapToTree();

        TreePrinter printer = new TreePrinter();

        printer.printTree(head);
    }

    public static void insertHashMapToTree() {
        Iterator it = hmap.entrySet().iterator();
        while (it.hasNext()) {
            Entry pair = (Entry) it.next();
            addToTree(head, pair);
        }
    }

    public static void addToTree(Node ptr, Entry pair) {
        String word = pair.getKey().toString();
        Integer wordCount = (Integer) pair.getValue();

        if (ptr.getLeft() == null && ptr.getRight() == null) {
            Node node = new Node(word, wordCount, true, ptr, null, null);
            ptr.setLeft(node);
            ptr.setCount(wordCount);
        } else if (ptr.getLeft() != null && ptr.getRight() == null) {
            Node node = new Node(word, wordCount, true, ptr, null, null);
            ptr.setRight(node);
            ptr.setCount(ptr.getLeft().getCount() + ptr.getRight().getCount());
        } else if (ptr.isFilled()) {

            if (ptr.getLeft().getLeafValue(ptr.getLeft()) != ptr.getRight().getLeafValue(ptr.getRight())) {
                if (ptr.getRight().getLeafValue(ptr.getRight()).equals(wordCount)) {
                    Node subParent = new Node(null, 0, false, ptr, ptr.getRight(), null);
                    ptr.getRight().setParent(subParent);
                    subParent.setRight(new Node(word, wordCount, true, subParent, null, null));
                    subParent.setCount(subParent.getRight().getCount() + subParent.getLeft().getCount());
                    ptr.setCount(subParent.getCount() + ptr.getLeft().getCount());
                    ptr.setRight(subParent);
                    ptr.getLeft().setParent(ptr);

                    head = ptr;
                } else {
                    Node newLeaf = new Node(word, wordCount, true, null, null, null);
                    Node newBranch = new Node(null, wordCount, false, null, newLeaf, null);
                    newLeaf.setParent(newBranch);
                    Node newHead = new Node(null, wordCount + ptr.getCount(), false, null, ptr, newBranch);
                    newBranch.setParent(newHead);

                    head = newHead;
                }

            } else {
                Node node = new Node(word, wordCount, true, null, null, null);
                Node newParent = new Node(null, ptr.getCount() + node.getCount(), false, null, ptr, node);
                node.setParent(newParent);
                head = newParent;
            }
        }
    }

    public static void addContentToHashMap(List<String> lineList) {
        lineList.forEach(line -> {
            String[] words = tokenizeString(line);

            Arrays.stream(words).forEach(word -> {
                if (hmap.containsKey(word)) {
                    Integer value = hmap.get(word);
                    hmap.put(word, value + 1);
                } else {
                    hmap.put(word, 1);
                }
            });
        });
    }

    public static HashMap<String, Integer> sortHashMapByValues() {
        Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                Integer v1 = e1.getValue();
                Integer v2 = e2.getValue();
                return v1.compareTo(v2);
            }
        };

        List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(hmap.entrySet());

        Collections.sort(entries, valueComparator);

        LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<String, Integer>(entries.size());

        // copying entries from List to Map
        for (Entry<String, Integer> entry : entries) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        return sortedHashMap;
    }


    //#region Initializations
    public static void init() {
        initHead();
        initHMap();
    }

    public static void initHMap() {
        hmap = new HashMap<String, Integer>();
    }

    public static void initHead() {
        head = new Node();
        head.setLeaf(false);
    }
    //#endregion Initializations

    //#region Read File
    public static List<String> readFile(String filename) {
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("source.txt"))) {
            list = br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
    //#endregion

    //#region Utilities
    public static String[] tokenizeString(String str) {
        return str.split(" ");
    }
    //#endregion


}
