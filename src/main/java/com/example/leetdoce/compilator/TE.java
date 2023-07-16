//package com.example.leetdoce.compilator;
//
//import org.python.core.Py;
//import org.python.core.PyList;
//import org.python.core.PyObject;
//import org.python.util.PythonInterpreter;
//
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//public class TE {
//    //    value();
//    public static void main(String[] args) {
////        value();
//        Map<Integer, String> testcase = new HashMap<>();
//        testcase.put(1, "[[1, 2, 3], [7, 8, 0]]");
//        Map<Integer, String> testcase1 = new HashMap<>();
//        testcase1.put(1, "[[12, 23, 33], [74, 84, 04]]");
////        testcase.put(2, "[aa,bb,cc]");
//        Map<Integer, String> reference = new HashMap<>();
//        reference.put(1, "int[][]");
//        Map<Integer, String> reference1 = new HashMap<>();
//        reference1.put(1, "int[][]");
////        reference.put(2, "ArrayList<String>");
//        MyDB myDB = new MyDB();
//        myDB.setMethodName("aa");
//        myDB.setReturnType("int[][]");
//        myDB.setTestCases(testcase);
//        myDB.setReferenceTypes(reference);
//        myDB.setResponse("[[1, 2, 3], [7, 8, 0]]");
//
//        MyDB myDB1 = new MyDB();
//        myDB1.setMethodName("aa");
//        myDB1.setReturnType("int[][]");
//        myDB1.setTestCases(testcase1);
//        myDB1.setReferenceTypes(reference1);
//        myDB1.setResponse("[[12, 23, 33], [74, 84, 4]]");
//
//        String method_1 = "class Solution:\n" +
//                "    def aa(self, array):\n" +
//                "        print('salom')\n" +
//                "        print('salom1')\n" +
//                "        print('salom2')\n" +
//                "        return array";
//        List<MyDB> myDBList = new ArrayList<>();
//        myDBList.add(myDB);
//        myDBList.add(myDB1);
//        System.out.println(compilator_For_Python(method_1, myDBList));
//    }
//
//    private static ResponseCompilator compilator_For_Python(String sourceCodes, List<MyDB> myDBList) {
//        ResponseCompilator responseCompilator = new ResponseCompilator();
//
//        PythonInterpreter pythonInterpreter = new PythonInterpreter();
//        pythonInterpreter.exec(sourceCodes);
//
//        // Access the Solution class and create an instance
//        PyObject solutionClass = pythonInterpreter.get("Solution");
//        PyObject solutionInstance = solutionClass.__call__();
//        int params = myDBList.get(0).getReferenceTypes().size();
//
//        boolean come = true;
//        String message = null;
//        int countPassed = 0;
//        long runtime = 0;
//        for (MyDB myDB : myDBList) {
//            PyObject[] args = new PyObject[params];
//            Map<Integer, String> referenceTypes = myDB.getReferenceTypes();
//            Map<Integer, String> testCases = myDB.getTestCases();
//            for (int i = 0; i < testCases.size(); i++) {
//                String type = referenceTypes.get(i + 1);
//                Object real_Var = Java_Comple.change_String_corresponding(type, testCases.get(i + 1));
//                if (type.endsWith("String>")) {
//                    System.out.println(real_Var);
//                    PyObject pyObject = convertToPythonList(real_Var);
//                    args[i] = pyObject;
//                } else if (type.endsWith(">")) {
//                    args[i] = pythonInterpreter.eval(real_Var.toString());
//                } else if (type.endsWith("[]")) {
//                    PyObject pyObject = convertToPython_One_Array(real_Var);
//                    args[i] = pyObject;
//                }
//            }
//
//            StringWriter output = new StringWriter();
//
//            pythonInterpreter.setOut(output);
//
//            long startTime = System.currentTimeMillis();
//            // exaption shuyerda sodir boladi
//            PyObject result = solutionInstance.invoke(myDB.getMethodName(), args);
//            if (come) {
//                message = output.toString();
//                come = false;
//            }
//            String invoke = result.toString();
////            String sum = (String) result.__tojava__(String.class);
//            if (invoke.equals(myDB.getResponse().toString())) {
//                countPassed++;
//            } else {
//                responseCompilator.setPassed(false);
//                responseCompilator.setExpected(myDB.getResponse());
//                responseCompilator.setYouResult(invoke.toString());
//                responseCompilator.setProvided(myDB.getTestCases());
//                break;
//            }
//            double finishTime = System.currentTimeMillis();
//            runtime += finishTime - startTime;
//        }
//        responseCompilator.setRuntime(runtime);
//        responseCompilator.setConsoleText(message);
//        responseCompilator.setPassedTestCases("" + countPassed + "/" + myDBList.size());
//        return responseCompilator;
//    }
//
//    private static PyObject convertToPythonList(Object o) {
//        List<?> list = (List<?>) o;
//        PyList pyList = new PyList();
//        for (Object element : list) {
//            pyList.add(Py.java2py(element));
//        }
//        return pyList;
//    }
//
//    private static PyObject convertToPython_One_Array(Object array) {
//        PyList pyList = new PyList();
//        if (array instanceof int[] intArray) {
//            for (int element : intArray) {
//                pyList.add(Py.java2py(element));
//            }
//        } else if (array instanceof String[] strArray) {
//            for (String element : strArray) {
//                pyList.add(Py.java2py(element));
//            }
//        } else if (array instanceof byte[] byteArray) {
//            for (byte element : byteArray) {
//                pyList.add(Py.java2py(element));
//            }
//        } else if (array instanceof short[] shortArray) {
//            for (short element : shortArray) {
//                pyList.add(Py.java2py(element));
//            }
//        } else if (array instanceof long[] longArray) {
//            for (long element : longArray) {
//                pyList.add(Py.java2py(element));
//            }
//        } else if (array instanceof float[] floatArray) {
//            for (float element : floatArray) {
//                pyList.add(Py.java2py(element));
//            }
//        } else if (array instanceof double[] doubleArray) {
//            for (double element : doubleArray) {
//                pyList.add(Py.java2py(element));
//            }
//        } else if (array instanceof char[] charArray) {
//            for (char element : charArray) {
//                pyList.add(Py.java2py(element));
//            }
//
//
//        } else if (array instanceof int[][] twoDArray) {
//            for (int[] innerArray : twoDArray) {
//                PyObject innerList = convertToPython_One_Array(innerArray);
//                pyList.add(innerList);
//            }
//        } else if (array instanceof String[][] twoDArray) {
//            for (String[] innerArray : twoDArray) {
//                PyObject innerList = convertToPython_One_Array(innerArray);
//                pyList.add(innerList);
//            }
//        }
//        if (array instanceof char[][] twoDArray) {
//            for (char[] innerArray : twoDArray) {
//                PyObject innerList = convertToPython_One_Array(innerArray);
//                pyList.add(innerList);
//            }
//        } else if (array instanceof short[][] twoDArray) {
//            for (short[] innerArray : twoDArray) {
//                PyObject innerList = convertToPython_One_Array(innerArray);
//                pyList.add(innerList);
//            }
//        } else if (array instanceof byte[][] twoDArray) {
//            for (byte[] innerArray : twoDArray) {
//                PyObject innerList = convertToPython_One_Array(innerArray);
//                pyList.add(innerList);
//            }
//        } else if (array instanceof long[][] twoDArray) {
//            for (long[] innerArray : twoDArray) {
//                PyObject innerList = convertToPython_One_Array(innerArray);
//                pyList.add(innerList);
//            }
//        } else if (array instanceof double[][] twoDArray) {
//            for (double[] innerArray : twoDArray) {
//                PyObject innerList = convertToPython_One_Array(innerArray);
//                pyList.add(innerList);
//            }
//        }
//        return pyList;
//    }
//}
