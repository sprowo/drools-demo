package com.prowo.drools;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * Created by linan on 2017/8/15.
 */
public class PersonDrools {
    private StatefulKnowledgeSession ksession;
    private KnowledgeBase kbase;

    public KnowledgeBase getKbase() {
        return kbase;
    }

    public void setKbase(KnowledgeBase kbase) {
        this.kbase = kbase;
    }

    public StatefulKnowledgeSession getKsession() {
        return ksession;
    }

    public void setKsession(StatefulKnowledgeSession ksession) {
        this.ksession = ksession;
    }

    public PersonDrools() {
    }

    public void runRules() throws Exception {
        Person p1 = new Person("白展堂", 68);
        Person p2 = new Person("李大嘴", 32);
        Person p3 = new Person("佟湘玉", 18);
        Person p4 = new Person("郭芙蓉", 8);

        System.out.println("before p1 : " + p1);
        System.out.println("before p2 : " + p2);
        System.out.println("before p3 : " + p3);
        System.out.println("before p4 : " + p4);
        ksession.insert(p1);
        ksession.insert(p2);
        ksession.insert(p3);
        ksession.insert(p4);

        int count = ksession.fireAllRules();//执行 规则
        System.out.println("总执行了" + count + "条规则");
        System.out.println("after p1 : " + p1);
        System.out.println("after p2 : " + p2);
        System.out.println("after p3 : " + p3);
        System.out.println("after p4 : " + p4);
        ksession.dispose(); //清空kiesession   注：无状态的不需要这样的操作

    }

}
