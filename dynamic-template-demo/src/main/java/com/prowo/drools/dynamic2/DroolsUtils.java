package com.prowo.drools.dynamic2;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.core.io.impl.ClassPathResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linan on 2017/8/15.
 */
public class DroolsUtils {
    /**
     * 默认规则文件所在路径
     */
    private static final String RULES_PATH = "/Users/prowo/Documents/githubRepo/drools-demo/config";

    /**
     * 获取规定目录下的规则文件
     *
     * @return
     * @throws IOException
     */
    private static List<File> getRuleFiles() throws IOException {
        List<File> list = new ArrayList<File>();
//        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String filePath = "/Users/prowo/Documents/githubRepo/drools-demo/config";
        File rootDir = new File(RULES_PATH);
        File[] files = rootDir.listFiles();
        for (File itemFile : files) {
//            if (itemFile.isDirectory() && itemFile.getName().equals(RULES_PATH)) {
            if (itemFile.isDirectory()) {
                for (File f : itemFile.listFiles()) {
                    if (f.getName().endsWith(".drl")) {
                        list.add(f);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 初始化一个kjar：把原有的drl包含进新建的kjar中
     *
     * @param ks
     * @param releaseId
     * @return
     * @throws IOException
     */
    public static InternalKieModule initKieJar(KieServices ks, ReleaseId releaseId) throws IOException {
        KieFileSystem kfs = createKieFileSystemWithKProject(ks, true);
        kfs.writePomXML(getPom(releaseId));
        for (File file : getRuleFiles()) {
            String path = "/Users/prowo/Documents/githubRepo/drools-demo/config/template/template.drl";
            Resource resource = ResourceFactory.newClassPathResource(path, "UTF-8");
            resource = ResourceFactory.newByteArrayResource(path.getBytes("utf-8"));
            InputStream inputStream = resource.getInputStream();
//            ClassPathResource classPathResource = new ClassPathResource(path);
//            System.err.println(classPathResource.isDirectory());
//            System.err.println(classPathResource.getPath());
//            System.err.println(classPathResource.getInputStream());
            String resPath = "src/main/resources/" + file.getName();
            kfs.write(resPath, resource);
        }
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);

//        if (!kieBuilder.buildAll().getResults().getMessages().isEmpty()) {
//            throw new IllegalStateException("Error creating KieBuilder.");
//        }
        return (InternalKieModule) kieBuilder.getKieModule();
    }

    public static InternalKieModule createKieJar(KieServices ks, ReleaseId releaseId, ResourceWrapper resourceWrapper) {
        KieFileSystem kfs = createKieFileSystemWithKProject(ks, true);
        kfs.writePomXML(getPom(releaseId));
        kfs.write("src/main/resources/" + resourceWrapper.getTargetResourceName(), resourceWrapper.getResource());
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);
        if (!kieBuilder.getResults().getMessages().isEmpty()) {
            System.out.println(kieBuilder.getResults().getMessages());
            throw new IllegalStateException("Error creating KieBuilder.");
        }
        return (InternalKieModule) kieBuilder.getKieModule();
    }

    /**
     * 创建默认的kbase和stateful的kiesession
     *
     * @param ks
     * @param isdefault
     * @return
     */
    public static KieFileSystem createKieFileSystemWithKProject(KieServices ks, boolean isdefault) {
        KieModuleModel kproj = ks.newKieModuleModel();
        KieBaseModel kieBaseModel1 = kproj.newKieBaseModel("KBase").setDefault(isdefault)
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY).setEventProcessingMode(EventProcessingOption.STREAM);
        // Configure the KieSession.
        kieBaseModel1.newKieSessionModel("KSession").setDefault(isdefault)
                .setType(KieSessionModel.KieSessionType.STATEFUL);
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.writeKModuleXML(kproj.toXML());
        return kfs;
    }

    /**
     * 创建kjar的pom
     *
     * @param releaseId
     * @param dependencies
     * @return
     */
    public static String getPom(ReleaseId releaseId, ReleaseId... dependencies) {
        String pom = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n"
                + "  <modelVersion>4.0.0</modelVersion>\n" + "\n" + "  <groupId>" + releaseId.getGroupId()
                + "</groupId>\n" + "  <artifactId>" + releaseId.getArtifactId() + "</artifactId>\n" + "  <version>"
                + releaseId.getVersion() + "</version>\n" + "\n";
        if (dependencies != null && dependencies.length > 0) {
            pom += "<dependencies>\n";
            for (ReleaseId dep : dependencies) {
                pom += "<dependency>\n";
                pom += "  <groupId>" + dep.getGroupId() + "</groupId>\n";
                pom += "  <artifactId>" + dep.getArtifactId() + "</artifactId>\n";
                pom += "  <version>" + dep.getVersion() + "</version>\n";
                pom += "</dependency>\n";
            }
            pom += "</dependencies>\n";
        }
        pom += "</project>";
        return pom;
    }
}
