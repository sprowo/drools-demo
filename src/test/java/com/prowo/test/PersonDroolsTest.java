package com.prowo.test;

import com.prowo.drools.Person;
import com.prowo.drools.PersonDrools;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext2.xml"})
public class PersonDroolsTest {

    @Autowired
    private PersonDrools personDrools;

    @Test
    public void runRules() throws Exception {
        personDrools.runRules();
    }

    public static void main(String[] args) throws IOException {
        StatefulKnowledgeSession kSession = null;
        try {
            File file = new File("/Users/prowo/Documents/githubRepo/drools-demo/config/person.drl");
            StringBuilder sb = new StringBuilder();
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt).append("\n");
            }
            read.close();
            System.err.println(sb.toString());

            KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
            //装入规则，可以装入多个
            kb.add(ResourceFactory.newByteArrayResource(sb.toString().getBytes("utf-8")), ResourceType.DRL);
//            kb.add(ResourceFactory.newByteArrayResource(rule2.getBytes("utf-8")), ResourceType.DRL);

            KnowledgeBuilderErrors errors = kb.getErrors();
            for (KnowledgeBuilderError error : errors) {
                System.err.println(error);
            }
            KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
            kBase.addKnowledgePackages(kb.getKnowledgePackages());

            kSession = kBase.newStatefulKnowledgeSession();

            Person p1 = new Person("白展堂", 68);
            Person p2 = new Person("李大嘴", 32);
            Person p3 = new Person("佟湘玉", 18);
            Person p4 = new Person("郭芙蓉", 8);

            System.out.println("before p1 : " + p1);
            System.out.println("before p2 : " + p2);
            System.out.println("before p3 : " + p3);
            System.out.println("before p4 : " + p4);
            kSession.insert(p1);
            kSession.insert(p2);

            int count = kSession.fireAllRules();//执行 规则
            System.out.println("总执行了" + count + "条规则");
            System.out.println("after p1 : " + p1);
            System.out.println("after p2 : " + p2);
            System.out.println("after p3 : " + p3);
            System.out.println("after p4 : " + p4);

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
            kSession = kBase.newStatefulKnowledgeSession();

            kSession.insert(p3);
            kSession.insert(p4);
            count = kSession.fireAllRules();//执行 规则
            System.out.println("总执行了" + count + "条规则");
            System.out.println("after p1 : " + p1);
            System.out.println("after p2 : " + p2);
            System.out.println("after p3 : " + p3);
            System.out.println("after p4 : " + p4);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (kSession != null)
                kSession.dispose();
        }
    }

}
