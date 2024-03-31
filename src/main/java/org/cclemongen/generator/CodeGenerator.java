package org.cclemongen.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.cclemongen.dto.FreeMakerGenDTO;
import org.cclemongen.dto.MetaDataDTO;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {

    /**
     * 1.選擇模板
     * 2.處理ftl檔案的動態參數
     * 3.產生檔案
     *
     * @param freeMakerGenDTO DTO
     * @param type            產檔類型
     * @throws IOException 產檔案失敗
     */
    public static void generateCode(FreeMakerGenDTO freeMakerGenDTO, String type) throws IOException {

        Template template = getTemplate(type + "Code.ftl");// 根據type來決定引用哪一個模板

        String lowerCaseTableName = MetaDataDTO.getFieldName(freeMakerGenDTO.getTableName());// 小駝峰
        String entityClassName = StringUtils.capitalize(lowerCaseTableName);// 大駝峰

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("tableName", freeMakerGenDTO.getTableName());
        dataModel.put("lowerCaseTableName", lowerCaseTableName);
        dataModel.put("entityClassName", entityClassName);
        dataModel.put("groupId", freeMakerGenDTO.getGroupId());
        dataModel.put("metaDataDTOList", freeMakerGenDTO.getMetaDataDTOList());
        // 判斷是否有包含需import的型別
        dataModel.put("hasBigDecimal", freeMakerGenDTO.getHasBigDecimal());
        dataModel.put("hasTime", freeMakerGenDTO.getHasTime());
        dataModel.put("hasTimestamp", freeMakerGenDTO.getHasTimestamp());
        dataModel.put("hasDate", freeMakerGenDTO.getHasDate());
        dataModel.put("hasLocalDateTime", freeMakerGenDTO.getHasLocalDateTime());

        // 在目的地產生以小駝峰命名的資料夾
        File outputDirectory = new File(freeMakerGenDTO.getDestination() + "/" + lowerCaseTableName + "/" + type);

        outputDirectory.mkdirs();

        System.out.println(type.toUpperCase() + " CODE START CREATE...");

        try (FileWriter fileWriter = new FileWriter(
                new File(outputDirectory, entityClassName + StringUtils.capitalize(type) + ".java"))) {
            template.process(dataModel, fileWriter);

        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }

        System.out.println(
                outputDirectory + "\\" + entityClassName + StringUtils.capitalize(type) + ".java"
                        + " CREATE DONE");

    }

    private static Template getTemplate(String templateName) throws IOException {

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

        configuration.setClassForTemplateLoading(CodeGenerator.class, "/templates");

        return configuration.getTemplate(templateName);
    }

    public static void createDdl(FreeMakerGenDTO freeMakerGenDTO) throws IOException {
        Template template = getTemplate("ddl.ftl");// 根據type來決定引用哪一個模板
        String lowerCaseTableName = MetaDataDTO.getFieldName(freeMakerGenDTO.getTableName());// 小駝峰
        String entityClassName = StringUtils.capitalize(lowerCaseTableName);// 大駝峰


        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("ddl", freeMakerGenDTO.getDdl());
        File outputDirectory = new File(freeMakerGenDTO.getDestination() + "/" + lowerCaseTableName + "/ddl");
        outputDirectory.mkdirs();

        System.out.println("DDL START CREATE...");

        try (FileWriter fileWriter = new FileWriter(
                new File(outputDirectory, freeMakerGenDTO.getTableName() + ".sql"))) {
            template.process(dataModel, fileWriter);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
