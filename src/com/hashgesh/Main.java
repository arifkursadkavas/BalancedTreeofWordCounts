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
    }


    public static void insertHashMapToTree() {
        Iterator it = hmap.entrySet().iterator();
        while (it.hasNext()) {
            Entry pair = (Entry) it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            //it.remove(); // avoids a ConcurrentModificationException

            addToTree(head, pair);
        }
    }

    public static void addToTree(Node ptr, Entry pair) {
        if (ptr.left == null && ptr.right == null) {
            Node node = new Node(pair.getKey().toString(), (Integer) pair.getValue(), true, ptr, null, null);
            ptr.left = node;
            ptr.setCount((Integer) pair.getValue());
        } else if (ptr.left != null && ptr.right == null) {
            Node node = new Node(pair.getKey().toString(), (Integer) pair.getValue(), true, ptr, null, null);
            ptr.right = node;
            ptr.setCount(ptr.left.count + ptr.right.count);
        } else if (ptr.isFilled()) {

            if(getLeafValue(ptr.left) != getLeafValue(ptr.right)){
                Node subParent = new Node();
                subParent.isLeaf = false;
                subParent.left = ptr.right;
                ptr.right.parent = subParent;
                subParent.right = new Node(pair.getKey().toString(), (Integer) pair.getValue(), true, subParent, null, null);
                subParent.setCount(subParent.right.count + subParent.left.count);
                ptr.setCount(subParent.count + ptr.left.count);
                ptr.right = subParent;
                head = ptr;

            }else {
                Node node = new Node(pair.getKey().toString(), (Integer) pair.getValue(), true, null, null, null);

                Node newParent = new Node(null, ptr.count + node.count, false, null, ptr, node);

                node.parent = newParent;

                head = newParent;
            }
        }
    }

    public static Node leafExists(Node ptr, Integer value) {
        Node current = ptr;

        if (current == null)
            return null;

        if (current.count == value) {
            return current;
        }

        return leafExists(ptr.right, value);
    }

    public static Integer getLeafValue(Node ptr){
         Node current = ptr;
         if(current.isLeaf){
             return current.count;
         }
         return getLeafValue(current.left);
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
        head.isLeaf = false;
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
