package com.tosmart.xiexuming.libtsparser;


import com.tosmart.xiexuming.libtsparser.program.Program;

import java.util.List;

/**
 * @author xiexuming
 */

public class Test {

    public static void main(String[] args) {
        TransportStreamManager transportStreamManager = new TransportStreamManager();
        List<Program> programs = transportStreamManager.parseTsStream("E:\\tsPackage\\000.ts");
        System.out.println(programs);
        System.out.println("----------------------------------------------\n");
        programs = transportStreamManager.parseTsStream("E:\\tsPackage\\001.ts");
        System.out.println(programs);
    }
}