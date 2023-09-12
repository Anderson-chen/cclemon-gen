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

    public static void generateEntityCode(String tableName, List<MetaDataDTO> metaDataDTOList)
            throws IOException, TemplateException {

        Template template = getTemplate("entityCode.ftl");

        String entityClassName = StringUtils.capitalize(tableName);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("metaDataDTOList", metaDataDTOList);

        File outputDirectory = new File("src/main/resources/output");
        outputDirectory.mkdirs();

        try (FileWriter fileWriter = new FileWriter(
                new File(outputDirectory, entityClassName + "Repository.java"))) {
            template.process(dataModel, fileWriter);
        }

        System.out.println("CODE GEN DONE");
    }

    private static Template getTemplate(String templateName)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setClassForTemplateLoading(CodeGenerator.class, "/templates");
        Template template = configuration.getTemplate(templateName);
        return template;
    }
}
