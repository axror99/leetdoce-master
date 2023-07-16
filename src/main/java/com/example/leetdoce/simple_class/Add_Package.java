package com.example.leetdoce.simple_class;

public enum Add_Package {

    JAVA_PACKAGE("import java.lang.*;\n import java.util.*;\n");

    private final String packages;

    Add_Package(String packages) {
        this.packages = packages;
    }

    public String getPackages() {
        return packages;
    }
}
