package com.prowo.drools.dynamic1;

import com.prowo.drools.dynamic1.domain.Message;
import com.prowo.drools.dynamic1.template.MyDataProvider;
import org.drools.template.DataProviderCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by linan on 2017/8/15.
 */
public class DataProviderCompilerTest {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String[]> rows = new ArrayList<String[]>();
        rows.add(new String[]{"1", "status == 0"});

        String templatePath = "/Users/prowo/Documents/githubRepo/drools-demo/config/template/template.drl";
        File file = new File(templatePath);
        InputStream templateStream = new FileInputStream(file);

        MyDataProvider tdp = new MyDataProvider(rows);
        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(tdp, templateStream);

        System.err.println(drl);

        KieServices kieServices = KieServices.Factory.get();
        KieResources resources = kieServices.getResources();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();//1

        KieBaseModel baseModel = kieModuleModel.newKieBaseModel("FileSystemKBase").addPackage("rules");//2
        baseModel.newKieSessionModel("FileSystemKSession");//3
        KieFileSystem fileSystem = kieServices.newKieFileSystem();

        String xml = kieModuleModel.toXML();
        System.out.println(xml);//4
        fileSystem.writeKModuleXML(xml);//5

        fileSystem.write("src/main/resources/rules/rule1.drl", drl);

        KieBuilder kb = kieServices.newKieBuilder(fileSystem);
        kb.buildAll();//7
        if (kb.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }
        KieContainer kContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        KieSession kSession = kContainer.newKieSession("FileSystemKSession");

        Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);

        System.err.println("before:" + message);

        kSession.insert(message);
        int count = kSession.fireAllRules();
        System.err.println(count);
        kSession.dispose();

        System.err.println("after:" + message);
    }
}
