package com.xiaohai.generator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 未用
 * @author wangchenghai
 * @date 2023/01/16 9:33:32
 */
public class FreemarkerUtil {
    private static String ftlPath = System.getProperty("user.dir")+"/xiaohai-generator/src/main/resources/templates";
    private static Template temp;

    public static void initConfig(String ftlName) throws IOException {
        Configuration conf = new Configuration(Configuration.VERSION_2_3_29);
        conf.setDirectoryForTemplateLoading(new File(ftlPath));
        conf.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        temp = conf.getTemplate(ftlName + ".java.ftl");
    }

    public static void generator(Map<String, Object> map, String fileName) throws IOException, TemplateException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(map, bw);
        bw.flush();
        fw.close();
    }

    private static String toControllerPath = System.getProperty("user.dir")+"/xiaohai-generator/src/main/java/com/xiaohai/generator/";

    public static void main(String[] args) throws Exception {

        Map<String, Object> map = new HashMap<>(3);
        map.put("Domain", "Section");
        map.put("domain", "section");
        map.put("module", "section");
        map.put("tableNameCn", "section");
        // 生成 controller
        FreemarkerUtil.initConfig("test");
        FreemarkerUtil.generator(map, toControllerPath +"test.java");
    }

}
