package com.example.leetdoce.compilator;

import com.example.leetdoce.entity.TestCaseEntity;
import lombok.experimental.UtilityClass;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

@UtilityClass
public class Java {

    public ResponseCompilator run_Java(String source, List<TestCaseEntity> myDBList) throws Exception {
        ResponseCompilator responseCompilator = new ResponseCompilator();

        File folder = new File("./src");
        File sourceFile = new File(folder, "Solution.java");

        try {
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            responseCompilator.setError(e.getMessage());
            responseCompilator.setPassed(false);
            return responseCompilator;
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.out.println("JDK required (running inside of JRE)");
        } else {
            System.out.println("you got it!");
        }
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream consoleStream = new PrintStream(outputStream);
            PrintStream originalOut = System.out;
            PrintStream originalErr = System.err;
            System.setOut(consoleStream);
            System.setErr(consoleStream);

            int compilationResult = compiler.run(null, null, null, sourceFile.getPath());

            System.setOut(originalOut);
            System.setErr(originalErr);
//            outputStream.toString().trim();
            if (compilationResult == 0) {
                System.out.println(CompileResponseStatus.COMPILATION_SUCCESS.getMessage());
            } else {
                System.out.println(CompileResponseStatus.COMPILATION_ERROR.getMessage());
                responseCompilator.setError(outputStream.toString().trim());
                responseCompilator.setPassed(false);
                return responseCompilator;
            }
        }catch (Throwable  e){
            responseCompilator.setError(e.getMessage());
            responseCompilator.setPassed(false);
            return responseCompilator;
        }
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{folder.toURI().toURL()});
            Class<?> cls = Class.forName("Solution", true, classLoader);
            Object instance = cls.newInstance();

            String[] collections_util = {"List", "ArrayList", "LinkedList", "Vector", "Stack", "Map", "HashMap",
                    "HashSet", "Arrays", "ArrayDeque", "TreeSet", "TreeMap", "Queue", "LinkedHashSet",
                    "LinkedHashMap", "Hashtable", "Deque", "Collection"};

            String[] collection_lang = {"String", "Integer", "Object", "Void", "StringBuilder", "StringBuffer",
                    "Short", "Long", "Double", "Float", "Boolean", "Byte", "Char", "Character"};

            Map<Integer, String> referenceTypes = myDBList.get(0).getReferenceTypes();

            Class<?>[] paramClasses = new Class<?>[referenceTypes.size()];
            int count = 0;
            for (String type : referenceTypes.values()) {
                boolean allow = true;
                for (String coll : collections_util) {
                    if (type.startsWith(coll)) {
                        paramClasses[count] = Class.forName("java.util." + coll);
                        allow = false;
                        break;
                    }
                }
                if (allow) {
                    for (String coll : collection_lang) {
                        if (type.startsWith(coll)) {
                            paramClasses[count] = Class.forName("java.lang." + type);
                            allow = false;
                            break;
                        }
                    }
                }
                if (allow) {
                    if (type.equals("boolean")) {
                        paramClasses[count] = boolean.class;
                    } else if (type.equals("int")) {
                        paramClasses[count] = int.class;
                    } else if (type.equals("float")) {
                        paramClasses[count] = float.class;
                    } else if (type.equals("char")) {
                        paramClasses[count] = char.class;
                    } else if (type.equals("byte")) {
                        paramClasses[count] = byte.class;
                    } else if (type.equals("short")) {
                        paramClasses[count] = short.class;
                    } else if (type.equals("double")) {
                        paramClasses[count] = double.class;
                    } else if (type.equals("boolean[]")) {
                        paramClasses[count] = boolean[].class;
                    } else if (type.equals("int[]")) {
                        paramClasses[count] = int[].class;
                    } else if (type.equals("float[]")) {
                        paramClasses[count] = float[].class;
                    } else if (type.equals("char[]")) {
                        paramClasses[count] = char[].class;
                    } else if (type.equals("byte[]")) {
                        paramClasses[count] = byte[].class;
                    } else if (type.equals("short[]")) {
                        paramClasses[count] = short[].class;
                    } else if (type.equals("double[]")) {
                        paramClasses[count] = double[].class;
                    } else if (type.equals("int[][]")) {
                        paramClasses[count] = int[][].class;
                    } else if (type.equals("float[][]")) {
                        paramClasses[count] = float[][].class;
                    } else if (type.equals("char[][]")) {
                        paramClasses[count] = char[][].class;
                    } else if (type.equals("byte[][]")) {
                        paramClasses[count] = byte[][].class;
                    } else if (type.equals("short[][]")) {
                        paramClasses[count] = short[][].class;
                    } else if (type.equals("double[][]")) {
                        paramClasses[count] = double[][].class;
                    } else {
                        throw new IllegalArgumentException("Unknown parameter type: " + type);
                    }
                }
                count++;
            }
            int countPassed = 0;
            String message = null;
            String errorMessage = null;
            long runtime = 0;
            boolean came = true;
            for (TestCaseEntity myDB : myDBList) {
                Map<Integer, Object> convertedValues = new HashMap<>();
                Map<Integer, String> values = myDB.getTestCases();
                Map<Integer, String> reference = myDB.getReferenceTypes();
                for (Map.Entry<Integer, String> entry : values.entrySet()) {
                    Integer key = entry.getKey();
                    String value = entry.getValue();
                    String type = reference.get(key);

                    convertedValues.put(key, ProportionalTypes.change_String_corresponding(type, value));
                }

                Method method = cls.getDeclaredMethod(myDBList.get(0).getMethodName(), paramClasses);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream consoleStream = new PrintStream(outputStream);
                PrintStream originalOut = System.out;
                System.setOut(consoleStream);

                Long startTime = System.currentTimeMillis();
                try {
                    Object invoke = method.invoke(instance, getMethodArguments(convertedValues));
                    Object resultString = ProportionalTypes.change_String_corresponding(myDB.getReturnType(), myDB.getResponse());

//                    Object invokeResult = change_String_corresponding(myDB.getReturnType(), invoke.toString());
                    if (!myDB.getReturnType().endsWith("[]") && resultString.equals(invoke) ) {
                        countPassed++;
                    } else if (myDB.getReturnType().equals("boolean[]") && Arrays.toString((boolean[])resultString).equals(Arrays.toString((boolean[]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("byte[]") && Arrays.toString((byte[])resultString).equals(Arrays.toString((byte[]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("byte[][]") && Arrays.toString((byte[][])resultString).equals(Arrays.toString((byte[][]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("short[]") && Arrays.toString((short[])resultString).equals(Arrays.toString((short[]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("short[][]") && Arrays.toString((short[][])resultString).equals(Arrays.toString((short[][]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("int[]") && Arrays.toString((int[])resultString).equals(Arrays.toString((int[]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("int[][]") && Arrays.toString((int[][])resultString).equals(Arrays.toString((int[][]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("long[]") && Arrays.toString((long[])resultString).equals(Arrays.toString((long[]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("long[][]") && Arrays.toString((long[][])resultString).equals(Arrays.toString((long[][]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("float[]") && Arrays.toString((float[])resultString).equals(Arrays.toString((float[]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("float[][]") && Arrays.toString((float[][])resultString).equals(Arrays.toString((float[][]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("double[]") && Arrays.toString((double[])resultString).equals(Arrays.toString((double[]) invoke))){
                        countPassed++;
                    }else if (myDB.getReturnType().equals("double[][]") && Arrays.toString((double[][])resultString).equals(Arrays.toString((double[][]) invoke))){
                        countPassed++;

                    }else {
                        responseCompilator.setPassed(false);
                        responseCompilator.setExpected(myDB.getResponse());
                        responseCompilator.setYouResult(invoke.toString());
                        responseCompilator.setProvided(myDB.getTestCases());
                        break;
                    }
                    Long finishTime = System.currentTimeMillis();
                    runtime+=finishTime-startTime;
                    if (came) {
                        System.setOut(originalOut);
                        message = outputStream.toString().trim();
                        came=false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMessage = e.getMessage();
                }
                convertedValues.clear();
            }
            responseCompilator.setRuntime(runtime);
            responseCompilator.setConsoleText(message);
            if (errorMessage!=null){
                responseCompilator.setPassed(false);
            }
            responseCompilator.setError(errorMessage);
            responseCompilator.setPassedTestCases("" + countPassed + "/" + myDBList.size());
            return responseCompilator;
        } catch (Exception e) {
            e.printStackTrace();
            responseCompilator.setError("java.lang.NoSuchMethodException : "+e.getMessage());
            responseCompilator.setPassed(false);
            return responseCompilator;
//            System.out.println("Something wrong");
//            throw new Exception("Something wrong");
        }
    }


    protected Object[] getMethodArguments(Map<Integer, Object> map_variable) {
        Object[] arguments = new Object[map_variable.size()];
        int index=0;
        for (Object value : map_variable.values()) {
            arguments[index]=value;
            index++;
        }
        return arguments;
    }

//    protected   Object change_String_corresponding(Object type, String value) {
//        Object convertedValue;
//
//        if (type.equals("ArrayList<Integer>")) {// Convert to ArrayList<Integer>
//            List<Integer> arrayList = new ArrayList<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                arrayList.add(Integer.parseInt(element));
//            }
//            convertedValue = arrayList;
//        } else if (type.equals("ArrayList<String>")) {// Convert to ArrayList<String>
//            String[] elements = split_String_Value(value);
//            convertedValue = new ArrayList<>(Arrays.asList(elements));
//        } else if (type.equals("ArrayList<Float>")) {// Convert to ArrayList<Float>
//            List<Float> arrayList = new ArrayList<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                arrayList.add(Float.parseFloat(element));
//            }
//            convertedValue = arrayList;
//        } else if (type.equals("ArrayList<Double>")) {// Convert to ArrayList<Double>
//            List<Double> arrayList = new ArrayList<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                arrayList.add(Double.parseDouble(element));
//            }
//            convertedValue = arrayList;
//        } else if (type.equals("LinkedList<Integer>")) {// Convert to LinkedList<>()<Integer>
//            List<Integer> arrayList = new LinkedList<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                arrayList.add(Integer.parseInt(element));
//            }
//            convertedValue = arrayList;
//        } else if (type.equals("LinkedList<String>")) {// Convert to LinkedList<>()<String>
//            String[] elements = split_String_Value(value);
//            convertedValue = new LinkedList<>(Arrays.asList(elements));
//        } else if (type.equals("LinkedList<Float>")) {// Convert to LinkedList<>()<Float>
//            List<Float> arrayList = new LinkedList<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                arrayList.add(Float.parseFloat(element));
//            }
//            convertedValue = arrayList;
//        } else if (type.equals("LinkedList<Double>")) {// Convert to LinkedList<>()<Double>
//            List<Double> arrayList = new LinkedList<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                arrayList.add(Double.parseDouble(element));
//            }
//            convertedValue = arrayList;
//        } else if (type.equals("Vector<Integer>")) {// Convert to Vector<>()<Integer>
//            Vector<Integer> vector =new Vector<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                vector.add(Integer.parseInt(element));
//            }
//            convertedValue = vector;
//        } else if (type.equals("Vector<String>")) {// Convert to Vector<>()<String>
//            String[] elements = split_String_Value(value);
//            convertedValue = new Vector<>(Arrays.asList(elements));
//
//        } else if (type.equals("Vector<Float>")) {// Convert to Vector<>()<Float>
//            Vector<Float> vector =new Vector<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                vector.add(Float.parseFloat(element));
//            }
//            convertedValue = vector;
//        } else if (type.equals("Vector<Double>")) {// Convert to Vector<>()<Double>
//            Vector<Double> vector =new Vector<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                vector.add(Double.parseDouble(element));
//            }
//            convertedValue = vector;
//        } else if (type.equals("Stack<Integer>")) {// Convert to Stack<>()<Integer>
//            Stack<Integer> stack =new Stack<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                stack.push(Integer.parseInt(element));
//            }
//            convertedValue = stack;
//        } else if (type.equals("Stack<String>")) {// Convert to Stack<>()<String>
//            Stack<String> stack =new Stack<>();
//            String[] elements = split_String_Value(value);
//            for (String element : elements) {
//                stack.push(element);
//            }
//            convertedValue = stack;
//        } else  if (type.equals("String")) {
//            convertedValue = value;
//        } else if (type.equals("boolean") || type.equals("Boolean")) {
//            convertedValue = Boolean.parseBoolean(value);
//        } else if (type.equals("char") || type.equals("Character")) {
//            convertedValue = value.charAt(0);
//        } else if (type.equals("int") || type.equals("Integer")) {
//            convertedValue = Integer.parseInt(value);
//        } else if (type.equals("byte") || type.equals("Byte")) {
//            convertedValue = Byte.parseByte(value);
//        } else if (type.equals("short") || type.equals("Short")) {
//            convertedValue = Short.parseShort(value);
//        } else if (type.equals("long") || type.equals("Long")) {
//            convertedValue = Long.parseLong(value);
//        } else if (type.equals("float") || type.equals("Float")) {
//            convertedValue = Float.parseFloat(value);
//        } else if (type.equals("double") || type.equals("Double")) {
//            convertedValue = Double.parseDouble(value);
//
//        } else if (type.equals("String[]")) { // [] li xolat
//            convertedValue = split_String_Value(value);
//        } else if (type.equals("boolean[]") || type.equals("Boolean[]")) {
//            String[] elements = split_String_Value(value);
//            boolean[] subArray = new boolean[elements.length];
//            for (int i = 0; i < elements.length; i++) {
//                subArray[i] = Boolean.parseBoolean(elements[i]);
//            }
//            convertedValue = subArray;
//        } else  if (type.equals("char[]") || type.equals("Character[]")) {
//            String[] elements = split_String_Value(value);
//            char[] subArray = new char[elements.length];
//            for (int i = 0; i < elements.length; i++) {
//                subArray[i] = elements[i].charAt(0);
//            }
//            convertedValue = subArray;
//        } else if (type.equals("int[]") || type.equals("Integer[]")) {
//            String[] elements = split_String_Value(value);
//            int[] subArray = new int[elements.length];
//            for (int i = 0; i < elements.length; i++) {
//                subArray[i] = Integer.parseInt(elements[i]);
//            }
//            convertedValue = subArray;
//        } else if (type.equals("byte[]") || type.equals("Byte[]")) {
//            String[] elements = split_String_Value(value);
//            byte[] subArray = new byte[elements.length];
//            for (int i = 0; i < elements.length; i++) {
//                subArray[i] = Byte.parseByte(elements[i]);
//            }
//            convertedValue = subArray;
//        } else if (type.equals("short[]") || type.equals("Short[]")) {
//            String[] elements = split_String_Value(value);
//            short[] subArray = new short[elements.length];
//            for (int i = 0; i < elements.length; i++) {
//                subArray[i] = Short.parseShort(elements[i]);
//            }
//            convertedValue = subArray;
//        } else if (type.equals("long[]") || type.equals("Long[]")) {
//            String[] elements = split_String_Value(value);
//            long[] subArray = new long[elements.length];
//            for (int i = 0; i < elements.length; i++) {
//                subArray[i] = Long.parseLong(elements[i]);
//            }
//            convertedValue = subArray;
//        } else if (type.equals("float[]") || type.equals("Float[]")) {
//            String[] elements = split_String_Value(value);
//            float[] subArray = new float[elements.length];
//            for (int i = 0; i < elements.length; i++) {
//                subArray[i] = Float.parseFloat(elements[i]);
//            }
//            convertedValue = subArray;
//        } else if (type.equals("double[]") || type.equals("Double[]")) {
//            String[] elements = split_String_Value(value);
//            double[] subArray = new double[elements.length];
//            for (int i = 0; i < elements.length; i++) {
//                subArray[i] = Double.parseDouble(elements[i]);
//            }
//            convertedValue = subArray;
//        } else if (type.equals("String[][]")) { // [][] li xolat
//            value = value.substring(1, value.length() - 1);
//            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
//            String[][] changedString = new String[arrays.length][];
//            for (int i = 0; i < arrays.length; i++) {
//                String[] elements = arrays[i].split(","); // Split by comma
//                changedString[i] = elements;
//            }
//            convertedValue = changedString;
//        } else if (type.equals("char[][]") || type.equals("Character[][]")) {
//            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
//            char[][] charArray = new char[arrays.length][];
//            for (int i = 0; i < arrays.length; i++) {
//                String[] elements = arrays[i].split(", ");
//                char[] subArray = new char[elements.length];
//                for (int j = 0; j < elements.length; j++) {
//                    subArray[j] = elements[j].charAt(0);
//                }
//                charArray[i] = subArray;
//            }
//            convertedValue = charArray;
//        } else if (type.equals("int[][]") || type.equals("Integer[][]")) {
//            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
//            int[][] intArray = new int[arrays.length][];
//            for (int i = 0; i < arrays.length; i++) {
//                String[] elements = arrays[i].split(", ");
//                int[] subArray = new int[elements.length];
//                for (int j = 0; j < elements.length; j++) {
//                    subArray[j] = Integer.parseInt(elements[j]);
//                }
//                intArray[i] = subArray;
//            }
//            convertedValue = intArray;
//        } else if (type.equals("byte[][]") || type.equals("Byte[][]")) {
//            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
//            byte[][] intArray = new byte[arrays.length][];
//            for (int i = 0; i < arrays.length; i++) {
//                String[] elements = arrays[i].split(", ");
//                byte[] subArray = new byte[elements.length];
//                for (int j = 0; j < elements.length; j++) {
//                    subArray[j] = Byte.parseByte(elements[j]);
//                }
//                intArray[i] = subArray;
//            }
//            convertedValue = intArray;
//        } else if (type.equals("short[][]") || type.equals("Short[][]")) {
//            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
//            short[][] intArray = new short[arrays.length][];
//            for (int i = 0; i < arrays.length; i++) {
//                String[] elements = arrays[i].split(", ");
//                short[] subArray = new short[elements.length];
//                for (int j = 0; j < elements.length; j++) {
//                    subArray[j] = Short.parseShort(elements[j]);
//                }
//                intArray[i] = subArray;
//            }
//            convertedValue = intArray;
//        } else if (type.equals("long[][]") || type.equals("Long[][]")) {
//            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
//            long[][] intArray = new long[arrays.length][];
//            for (int i = 0; i < arrays.length; i++) {
//                String[] elements = arrays[i].split(", ");
//                long[] subArray = new long[elements.length];
//                for (int j = 0; j < elements.length; j++) {
//                    subArray[j] = Long.parseLong(elements[j]);
//                }
//                intArray[i] = subArray;
//            }
//            convertedValue = intArray;
//        } else if (type.equals("float[][]") || type.equals("Float[][]")) {
//            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
//            float[][] intArray = new float[arrays.length][];
//            for (int i = 0; i < arrays.length; i++) {
//                String[] elements = arrays[i].split(", ");
//                float[] subArray = new float[elements.length];
//                for (int j = 0; j < elements.length; j++) {
//                    subArray[j] = Float.parseFloat(elements[j]);
//                }
//                intArray[i] = subArray;
//            }
//            convertedValue = intArray;
//        } else if (type.equals("double[][]") || type.equals("Double[][]")) {
//            String[] arrays = value.substring(2, value.length() - 2).split("\\], \\[");
//            double[][] intArray = new double[arrays.length][];
//            for (int i = 0; i < arrays.length; i++) {
//                String[] elements = arrays[i].split(", ");
//                double[] subArray = new double[elements.length];
//                for (int j = 0; j < elements.length; j++) {
//                    subArray[j] = Double.parseDouble(elements[j]);
//                }
//                intArray[i] = subArray;
//            }
//            convertedValue = intArray;
//        } else {
//            // Handle unsupported types or throw an exception
//            throw new IllegalArgumentException("Unsupported type: " + type);
//        }
//        return convertedValue;
//    }
}
