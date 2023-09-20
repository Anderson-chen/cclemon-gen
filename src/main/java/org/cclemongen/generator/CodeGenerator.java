package org.cclemongen.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cclemongen.dto.MetaDataDTO;
import org.springframework.util.StringUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

public class CodeGenerator {

    /**
     * 
     * 產生程式碼主流程
     * 
     * @param tableName
     * @param metaDataDTOList
     * @param destination
     * @param groupId
     * @throws Exception
     */
    public static void generateCode(String tableName, List<MetaDataDTO> metaDataDTOList, String destination,
            String type, String groupId)
            throws Exception {

        Template template = getTemplate(type + "Code.ftl");

        String entityClassName = StringUtils.capitalize(tableName.toLowerCase());

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("groupId", groupId);
        dataModel.put("metaDataDTOList", metaDataDTOList);

        File outputDirectory = new File(destination + "/" + entityClassName + "/" + type);

        outputDirectory.mkdirs();

        System.out.println(type.toUpperCase() + " CODE START CREATE...");

        try (FileWriter fileWriter = new FileWriter(
                new File(outputDirectory, entityClassName + StringUtils.capitalize(type) + ".java"))) {
            template.process(dataModel, fileWriter);

        }

        System.out.println(
                outputDirectory + "\\" + entityClassName + StringUtils.capitalize(type) + ".java" + " CREATE DONE");

    }

    private static Template getTemplate(String templateName)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setClassForTemplateLoading(CodeGenerator.class, "/templates");
        Template template = configuration.getTemplate(templateName);
        return template;
    }

}
