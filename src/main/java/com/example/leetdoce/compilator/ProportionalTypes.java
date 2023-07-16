package com.example.leetdoce.compilator;

import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class ProportionalTypes {

    protected String[] split_String_Value(String value) {
        return value.replaceAll("[\\[\\]\\s]", "").split(",");
    }
    protected   Object change_String_corresponding(Object type, String value) {
        Object convertedValue;

        if (type.equals("ArrayList<Integer>")) {// Convert to ArrayList<Integer>
            List<Integer> arrayList = new ArrayList<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                arrayList.add(Integer.parseInt(element));
            }
            convertedValue = arrayList;
        } else if (type.equals("ArrayList<String>")) {// Convert to ArrayList<String>
            String[] elements = split_String_Value(value);
            convertedValue = new ArrayList<>(Arrays.asList(elements));
        } else if (type.equals("ArrayList<Float>")) {// Convert to ArrayList<Float>
            List<Float> arrayList = new ArrayList<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                arrayList.add(Float.parseFloat(element));
            }
            convertedValue = arrayList;
        } else if (type.equals("ArrayList<Double>")) {// Convert to ArrayList<Double>
            List<Double> arrayList = new ArrayList<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                arrayList.add(Double.parseDouble(element));
            }
            convertedValue = arrayList;
        } else if (type.equals("LinkedList<Integer>")) {// Convert to LinkedList<>()<Integer>
            List<Integer> arrayList = new LinkedList<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                arrayList.add(Integer.parseInt(element));
            }
            convertedValue = arrayList;
        } else if (type.equals("LinkedList<String>")) {// Convert to LinkedList<>()<String>
            String[] elements = split_String_Value(value);
            convertedValue = new LinkedList<>(Arrays.asList(elements));
        } else if (type.equals("LinkedList<Float>")) {// Convert to LinkedList<>()<Float>
            List<Float> arrayList = new LinkedList<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                arrayList.add(Float.parseFloat(element));
            }
            convertedValue = arrayList;
        } else if (type.equals("LinkedList<Double>")) {// Convert to LinkedList<>()<Double>
            List<Double> arrayList = new LinkedList<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                arrayList.add(Double.parseDouble(element));
            }
            convertedValue = arrayList;
        } else if (type.equals("Vector<Integer>")) {// Convert to Vector<>()<Integer>
            Vector<Integer> vector =new Vector<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                vector.add(Integer.parseInt(element));
            }
            convertedValue = vector;
        } else if (type.equals("Vector<String>")) {// Convert to Vector<>()<String>
            String[] elements = split_String_Value(value);
            convertedValue = new Vector<>(Arrays.asList(elements));

        } else if (type.equals("Vector<Float>")) {// Convert to Vector<>()<Float>
            Vector<Float> vector =new Vector<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                vector.add(Float.parseFloat(element));
            }
            convertedValue = vector;
        } else if (type.equals("Vector<Double>")) {// Convert to Vector<>()<Double>
            Vector<Double> vector =new Vector<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                vector.add(Double.parseDouble(element));
            }
            convertedValue = vector;
        } else if (type.equals("Stack<Integer>")) {// Convert to Stack<>()<Integer>
            Stack<Integer> stack =new Stack<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                stack.push(Integer.parseInt(element));
            }
            convertedValue = stack;
        } else if (type.equals("Stack<String>")) {// Convert to Stack<>()<String>
            Stack<String> stack =new Stack<>();
            String[] elements = split_String_Value(value);
            for (String element : elements) {
                stack.push(element);
            }
            convertedValue = stack;
        } else  if (type.equals("String")) {
            convertedValue = value;
        } else if (type.equals("boolean") || type.equals("Boolean")) {
            convertedValue = Boolean.parseBoolean(value);
        } else if (type.equals("char") || type.equals("Character")) {
            convertedValue = value.charAt(0);
        } else if (type.equals("int") || type.equals("Integer")) {
            convertedValue = Integer.parseInt(value);
        } else if (type.equals("byte") || type.equals("Byte")) {
            convertedValue = Byte.parseByte(value);
        } else if (type.equals("short") || type.equals("Short")) {
            convertedValue = Short.parseShort(value);
        } else if (type.equals("long") || type.equals("Long")) {
            convertedValue = Long.parseLong(value);
        } else if (type.equals("float") || type.equals("Float")) {
            convertedValue = Float.parseFloat(value);
        } else if (type.equals("double") || type.equals("Double")) {
            convertedValue = Double.parseDouble(value);

        } else if (type.equals("String[]")) { // [] li xolat
            convertedValue = split_String_Value(value);
        } else if (type.equals("boolean[]") || type.equals("Boolean[]")) {
            String[] elements = split_String_Value(value);
            boolean[] subArray = new boolean[elements.length];
            for (int i = 0; i < elements.length; i++) {
                subArray[i] = Boolean.parseBoolean(elements[i]);
            }
            convertedValue = subArray;
        } else  if (type.equals("char[]") || type.equals("Character[]")) {
            String[] elements = split_String_Value(value);
            char[] subArray = new char[elements.length];
            for (int i = 0; i < elements.length; i++) {
                subArray[i] = elements[i].charAt(0);
            }
            convertedValue = subArray;
        } else if (type.equals("int[]") || type.equals("Integer[]")) {
            String[] elements = split_String_Value(value);
            int[] subArray = new int[elements.length];
            for (int i = 0; i < elements.length; i++) {
                subArray[i] = Integer.parseInt(elements[i]);
            }
            convertedValue = subArray;
        } else if (type.equals("byte[]") || type.equals("Byte[]")) {
            String[] elements = split_String_Value(value);
            byte[] subArray = new byte[elements.length];
            for (int i = 0; i < elements.length; i++) {
                subArray[i] = Byte.parseByte(elements[i]);
            }
            convertedValue = subArray;
        } else if (type.equals("short[]") || type.equals("Short[]")) {
            String[] elements = split_String_Value(value);
            short[] subArray = new short[elements.length];
            for (int i = 0; i < elements.length; i++) {
                subArray[i] = Short.parseShort(elements[i]);
            }
            convertedValue = subArray;
        } else if (type.equals("long[]") || type.equals("Long[]")) {
            String[] elements = split_String_Value(value);
            long[] subArray = new long[elements.length];
            for (int i = 0; i < elements.length; i++) {
                subArray[i] = Long.parseLong(elements[i]);
            }
            convertedValue = subArray;
        } else if (type.equals("float[]") || type.equals("Float[]")) {
            String[] elements = split_String_Value(value);
            float[] subArray = new float[elements.length];
            for (int i = 0; i < elements.length; i++) {
                subArray[i] = Float.parseFloat(elements[i]);
            }
            convertedValue = subArray;
        } else if (type.equals("double[]") || type.equals("Double[]")) {
            String[] elements = split_String_Value(value);
            double[] subArray = new double[elements.length];
            for (int i = 0; i < elements.length; i++) {
                subArray[i] = Double.parseDouble(elements[i]);
            }
            convertedValue = subArray;
        } else if (type.equals("String[][]")) { // [][] li xolat
            value = value.substring(1, value.length() - 1);
            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
            String[][] changedString = new String[arrays.length][];
            for (int i = 0; i < arrays.length; i++) {
                String[] elements = arrays[i].split(","); // Split by comma
                changedString[i] = elements;
            }
            convertedValue = changedString;
        } else if (type.equals("char[][]") || type.equals("Character[][]")) {
            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
            char[][] charArray = new char[arrays.length][];
            for (int i = 0; i < arrays.length; i++) {
                String[] elements = arrays[i].split(", ");
                char[] subArray = new char[elements.length];
                for (int j = 0; j < elements.length; j++) {
                    subArray[j] = elements[j].charAt(0);
                }
                charArray[i] = subArray;
            }
            convertedValue = charArray;
        } else if (type.equals("int[][]") || type.equals("Integer[][]")) {
            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
            int[][] intArray = new int[arrays.length][];
            for (int i = 0; i < arrays.length; i++) {
                String[] elements = arrays[i].split(", ");
                int[] subArray = new int[elements.length];
                for (int j = 0; j < elements.length; j++) {
                    subArray[j] = Integer.parseInt(elements[j]);
                }
                intArray[i] = subArray;
            }
            convertedValue = intArray;
        } else if (type.equals("byte[][]") || type.equals("Byte[][]")) {
            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
            byte[][] intArray = new byte[arrays.length][];
            for (int i = 0; i < arrays.length; i++) {
                String[] elements = arrays[i].split(", ");
                byte[] subArray = new byte[elements.length];
                for (int j = 0; j < elements.length; j++) {
                    subArray[j] = Byte.parseByte(elements[j]);
                }
                intArray[i] = subArray;
            }
            convertedValue = intArray;
        } else if (type.equals("short[][]") || type.equals("Short[][]")) {
            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
            short[][] intArray = new short[arrays.length][];
            for (int i = 0; i < arrays.length; i++) {
                String[] elements = arrays[i].split(", ");
                short[] subArray = new short[elements.length];
                for (int j = 0; j < elements.length; j++) {
                    subArray[j] = Short.parseShort(elements[j]);
                }
                intArray[i] = subArray;
            }
            convertedValue = intArray;
        } else if (type.equals("long[][]") || type.equals("Long[][]")) {
            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
            long[][] intArray = new long[arrays.length][];
            for (int i = 0; i < arrays.length; i++) {
                String[] elements = arrays[i].split(", ");
                long[] subArray = new long[elements.length];
                for (int j = 0; j < elements.length; j++) {
                    subArray[j] = Long.parseLong(elements[j]);
                }
                intArray[i] = subArray;
            }
            convertedValue = intArray;
        } else if (type.equals("float[][]") || type.equals("Float[][]")) {
            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
            float[][] intArray = new float[arrays.length][];
            for (int i = 0; i < arrays.length; i++) {
                String[] elements = arrays[i].split(", ");
                float[] subArray = new float[elements.length];
                for (int j = 0; j < elements.length; j++) {
                    subArray[j] = Float.parseFloat(elements[j]);
                }
                intArray[i] = subArray;
            }
            convertedValue = intArray;
        } else if (type.equals("double[][]") || type.equals("Double[][]")) {
            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
            double[][] intArray = new double[arrays.length][];
            for (int i = 0; i < arrays.length; i++) {
                String[] elements = arrays[i].split(", ");
                double[] subArray = new double[elements.length];
                for (int j = 0; j < elements.length; j++) {
                    subArray[j] = Double.parseDouble(elements[j]);
                }
                intArray[i] = subArray;
            }
            convertedValue = intArray;
        } else {
            // Handle unsupported types or throw an exception
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
        return convertedValue;
    }
}
