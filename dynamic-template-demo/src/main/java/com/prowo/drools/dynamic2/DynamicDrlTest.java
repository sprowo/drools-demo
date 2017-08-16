package com.prowo.drools.dynamic2;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

/**
 * Created by linan on 2017/8/15.
 */
public class DynamicDrlTest {
    private static final String RULESFILE_NAME = "rules.drl";

    /**
     * 规则文件内容（可以从数据库中加载）
     */
    private static final String rules = "package com.caicongyang.drools.test; import com.caicongyang.drools.fact.Message; rule \"Hello World \" when message:Message (status == \"0\") then System.out.println(\"hello, Drools!\"); end";

    public static void main(String[] args) throws Exception {

        KieServices kieServices = KieServices.Factory.get();

        /**
         * 指定kjar包
         */
        final ReleaseId releaseId = kieServices.newReleaseId("com.prowo", "drools.drl", "1.0.0");

        // 创建初始化的kjar
        InternalKieModule kJar = DroolsUtils.initKieJar(kieServices, releaseId);
        KieRepository repository = kieServices.getRepository();
        repository.addKieModule(kJar);
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        KieSession session = kieContainer.newKieSession();
        Message message = new Message();
        message.setStatus("0");
        System.err.println("before:" + message);
        //同一个fact第一次不命中
        try {
            session.insert(message);
            System.err.println("执行规则条数:" + session.fireAllRules());
        } catch (Exception e) {
        } finally {
            session.dispose();
        }
        System.err.println("after:" + message);
        System.out.println("-----first fire end-------");

        //新增一个规则文件
        kJar = DroolsUtils.createKieJar(kieServices, releaseId,
                new ResourceWrapper(ResourceFactory.newByteArrayResource(rules.getBytes()), RULESFILE_NAME));
        repository.addKieModule(kJar);
        kieContainer.updateToVersion(releaseId);

        //同一个fact再次过滤规则：命中
        session = kieContainer.newKieSession();
        System.err.println("before:" + message);
        try {
            session.insert(message);
            System.err.println("执行规则条数:" + session.fireAllRules());
        } catch (Exception e) {
        } finally {
            session.dispose();
        }
        System.err.println("after:" + message);
        System.out.println("-----senond fire end-------");

    }
}
