package org.cclemongen.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DynamicQueryCodeGenerator {

    public static void main(String[] args) throws IOException, TemplateException {
        // 获取实体类的 Class 对象（假设你的实体类是 User）
        // Class<?> entityClass = User.class;

        // 使用 FreeMarker 生成查询代码
        generateQueryCode();
    }

    private static void generateQueryCode() throws IOException, TemplateException {
        // 配置 FreeMarker
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setClassForTemplateLoading(DynamicQueryCodeGenerator.class, "/ftl");

        // 创建 FreeMarker 模板
        Template template = configuration.getTemplate("queryMethod.ftl");

        // 获取实体类的字段信息
        // Field[] fields = entityClass.getDeclaredFields();
        // List<String> fieldNames = new ArrayList<>();

        // for (Field field : fields) {
        // fieldNames.add(field.getName());
        // }

        // 准备模板数据
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", "ANDY");
        dataModel.put("fieldNames", "GENDER");

        // 创建输出目录
        File outputDirectory = new File("src/main/java/org/cclemongen/generator/output");
        outputDirectory.mkdirs();

        // 生成查询方法代码文件
        try (FileWriter fileWriter = new FileWriter(
                new File(outputDirectory, "ANDY" + "Repository.java"))) {
            template.process(dataModel, fileWriter);
        }

        System.out.println("Query method code generated successfully!");
    }
}
