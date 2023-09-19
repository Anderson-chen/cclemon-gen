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
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class CodeGenerator {

    /**
     * 
     * 產生entity的程式碼
     * 
     * @param tableName
     * @param metaDataDTOList
     * @param destination
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateEntityCode(String tableName, List<MetaDataDTO> metaDataDTOList, String destination)
            throws IOException, TemplateException {

        Template template = getTemplate("entityCode.ftl");

        String entityClassName = StringUtils.capitalize(tableName);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("metaDataDTOList", metaDataDTOList);

        File outputDirectory = new File(destination + "/" + tableName + "/entity");
        outputDirectory.mkdirs();

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
     * @throws IOException
     * @throws ParseException
     * @throws MalformedTemplateNameException
     * @throws TemplateNotFoundException
     * @throws TemplateException
     */
    public static void generateRepositoryCode(String tableName, List<MetaDataDTO> metaDataDTOList,
            String destination) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException,
            IOException, TemplateException {

        Template template = getTemplate("repositoryCode.ftl");

        String entityClassName = StringUtils.capitalize(tableName);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("metaDataDTOList", metaDataDTOList);

        File outputDirectory = new File(destination + "/" + tableName + "/repository");
        outputDirectory.mkdirs();

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
     * @throws TemplateNotFoundException
     * @throws MalformedTemplateNameException
     * @throws ParseException
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateDynamicQueryCode(String tableName, List<MetaDataDTO> metaDataDTOList,
            String destination)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
            TemplateException {

        Template template = getTemplate("specificationCode.ftl");

        String entityClassName = StringUtils.capitalize(tableName);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("metaDataDTOList", metaDataDTOList);

        File outputDirectory = new File(destination + "/" + tableName + "/specification");
        outputDirectory.mkdirs();

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
