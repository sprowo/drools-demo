package com.prowo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class HelloDroolsTest {

    @KSession("helloKsession")   //注： 这里的值与配置文件中的值是一样的
    private KieSession ksession;
    @KSession("beyKsession")
    private KieSession beyKsession;

    @Test
    public void runRules() {
        int count = ksession.fireAllRules();
        System.out.println("总执行了" + count + "条规则");
        ksession.dispose();
        int count1 = beyKsession.fireAllRules();
        System.out.println("总执行了" + count1 + "条规则");
        beyKsession.dispose();
    }
}
