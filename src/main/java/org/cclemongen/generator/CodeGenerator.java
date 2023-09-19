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
     * 產生entity的程式碼
     * 
     * @param tableName
     * @param metaDataDTOList
     * @param destination
     * @throws Exception
     */
    public static void generateEntityCode(String tableName, List<MetaDataDTO> metaDataDTOList, String destination)
            throws Exception {

        Template template = getTemplate("entityCode.ftl");

        String entityClassName = StringUtils.capitalize(tableName);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("metaDataDTOList", metaDataDTOList);

        File outputDirectory = new File(destination + "/" + tableName + "/entity");

        boolean isCreated = outputDirectory.mkdirs();

        if (!isCreated) {
            throw new Exception("找不到指定目錄");
        }

        try (FileWriter fileWriter = new FileWriter(
                new File(outputDirectory, entityClassName + ".java"))) {
            template.process(dataModel, fileWriter);
        }

        System.out.println("ENTITY CODE GEN DONE");
    }

    /**
     * 
     * 產生Repository的程式碼
     * 
     * @param tableName
     * @param metaDataDTOList
     * @param dymamicQueryPath
     * @throws Exception
     */
    public static void generateRepositoryCode(String tableName, List<MetaDataDTO> metaDataDTOList,
            String destination) throws Exception {

        Template template = getTemplate("repositoryCode.ftl");

        String entityClassName = StringUtils.capitalize(tableName);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("metaDataDTOList", metaDataDTOList);

        File outputDirectory = new File(destination + "/" + tableName + "/repository");

        boolean isCreated = outputDirectory.mkdirs();

        if (!isCreated) {
            throw new Exception("找不到指定目錄");
        }

        try (FileWriter fileWriter = new FileWriter(
                new File(outputDirectory, entityClassName + "repository.java"))) {
            template.process(dataModel, fileWriter);
        }

        System.out.println("DYNAMIC QUERY CODE GEN DONE");
    }

    /**
     * 
     * 產生動態查詢的程式碼
     * 
     * @param tableName
     * @param metaDataDTOList
     * @param destination
     * @throws Exception
     */
    public static void generateDynamicQueryCode(String tableName, List<MetaDataDTO> metaDataDTOList,
            String destination)
            throws Exception {

        Template template = getTemplate("specificationCode.ftl");

        String entityClassName = StringUtils.capitalize(tableName);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("metaDataDTOList", metaDataDTOList);

        File outputDirectory = new File(destination + "/" + tableName + "/specification");

        boolean isCreated = outputDirectory.mkdirs();

        if (!isCreated) {
            throw new Exception("找不到指定目錄");
        }

        try (FileWriter fileWriter = new FileWriter(
                new File(outputDirectory, entityClassName + "Specification.java"))) {
            template.process(dataModel, fileWriter);
        }

        System.out.println("DYNAMIC QUERY CODE GEN DONE");
    }

    private static Template getTemplate(String templateName)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setClassForTemplateLoading(CodeGenerator.class, "/templates");
        Template template = configuration.getTemplate(templateName);
        return template;
    }

}
