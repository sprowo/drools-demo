package com.prowo.drools;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by linan on 2017/8/16.
 */
public class StringDroolsTest {
    public static void main(String[] args) throws IOException {
        StatefulKnowledgeSession kSession = null;
        try {
            String path1 = "/Users/prowo/Documents/githubRepo/drools-demo/config/person.drl";
            String path2 = "/Users/prowo/Documents/githubRepo/drools-demo/config/student.drl";

//            String rule1 = getRule(path1);
//            String rule2 = getRule(path2);

            KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
            //装入规则，可以装入多个
            kb.add(ResourceFactory.newByteArrayResource(Files.readAllBytes(Paths.get(path1))), ResourceType.DRL);
            kb.add(ResourceFactory.newByteArrayResource(Files.readAllBytes(Paths.get(path2))), ResourceType.DRL);
//            kb.add(ResourceFactory.newByteArrayResource(rule2.getBytes("utf-8")), ResourceType.DRL);

            KnowledgeBuilderErrors errors = kb.getErrors();
            for (KnowledgeBuilderError error : errors) {
                System.err.println(error);
            }
            KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
            kBase.addKnowledgePackages(kb.getKnowledgePackages());

            kSession = kBase.newStatefulKnowledgeSession();
//            kBase.newStatelessKnowledgeSession();

            Person p1 = new Person("白展堂", 68);
            Person p2 = new Person("李大嘴", 32);
            Person p3 = new Person("佟湘玉", 18);
            Person p4 = new Person("郭芙蓉", 1);
            Student s1 = new Student("qq", 21);
            Student s2 = new Student("ww", 51);

            System.out.println("before p1 : " + p1);
            System.out.println("before p2 : " + p2);
            System.out.println("before p3 : " + p3);
            System.out.println("before p4 : " + p4);
            System.out.println("before s1 : " + s1);
            System.out.println("before s2 : " + s2);
            kSession.insert(p1);
            kSession.insert(p2);
            kSession.insert(p3);
            kSession.insert(p4);
            kSession.insert(s1);
            kSession.insert(s2);

            int count = 0;
            count = kSession.fireAllRules();//执行 规则
            System.out.println("总执行了" + count + "条规则");
            System.out.println("after p1 : " + p1);
            System.out.println("after p2 : " + p2);
            System.out.println("after p3 : " + p3);
            System.out.println("after p4 : " + p4);
            System.out.println("after s1 : " + s1);
            System.out.println("after s2 : " + s2);


            System.err.println("---------------------------------");

            String append = "rule \"text\"\n" +
                    "    salience 5\n" +
                    "    when\n" +
                    "        eval(true)\n" +
                    "    then\n" +
                    "        System.err.println(\"xxxxxxxxxxxxxxxxxxxxx\");\n" +
                    "end";
            kb.add(ResourceFactory.newByteArrayResource(append.getBytes("utf-8")), ResourceType.DRL);
            kBase.addKnowledgePackages(kb.getKnowledgePackages());

            kSession.insert(p3);
            kSession.insert(p4);
            count = kSession.fireAllRules();//执行 规则
            System.out.println("总执行了" + count + "条规则");
            System.out.println("after p1 : " + p1);
            System.out.println("after p2 : " + p2);
            System.out.println("after p3 : " + p3);
            System.out.println("after p4 : " + p4);
            System.out.println("after s1 : " + s1);
            System.out.println("after s2 : " + s2);

            kSession.insert(p3);
            kSession.insert(p4);
            count = kSession.fireAllRules();//执行 规则
            System.out.println("总执行了" + count + "条规则");
            System.out.println("after p1 : " + p1);
            System.out.println("after p2 : " + p2);
            System.out.println("after p3 : " + p3);
            System.out.println("after p4 : " + p4);
            System.out.println("after s1 : " + s1);
            System.out.println("after s2 : " + s2);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (kSession != null)
                kSession.dispose();
        }
    }

    private static String getRule(String path) throws IOException {
        File file = new File(path);

        StringBuilder sb = new StringBuilder();
        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            sb.append(lineTxt).append("\n");
        }
        read.close();
        System.err.println(sb.toString());
        return sb.toString();
    }
}
