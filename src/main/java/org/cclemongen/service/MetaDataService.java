package org.cclemongen.service;

import org.cclemongen.dto.FreeMakerGenDTO;
import org.cclemongen.dto.MetaDataDTO;
import org.cclemongen.generator.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetaDataService {

    private static final List<String> excludeSchemaList = new ArrayList<>();
    private static final List<String> baseFieldList = new ArrayList<>();
    private static final Map<String, String> needImportMap = new HashMap<>();

    static {

        excludeSchemaList.add("information_schema");
        excludeSchemaList.add("mysql");
        excludeSchemaList.add("performance_schema");
        excludeSchemaList.add("sys");

        baseFieldList.add("id");
        baseFieldList.add("last_modified_user_id");
        baseFieldList.add("last_modified_time");
        baseFieldList.add("deleted");
        baseFieldList.add("create_user_id");
        baseFieldList.add("create_time");
        baseFieldList.add("version");

        needImportMap.put("DECIMAL", "setHasBigDecimal");
        needImportMap.put("BIGINT", "setHasBigDecimal");
        needImportMap.put("DATETIME", "setHasLocalDateTime");
        needImportMap.put("TIMESTAMP", "setHasTimestamp");
        needImportMap.put("TIME", "setHasTime");
        needImportMap.put("DATE", "setHasDate");
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${codegen.types}")
    private String[] types;

    private static MetaDataDTO getMetaDataDTO(FreeMakerGenDTO freeMakerGenDTO, ResultSet columns) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        boolean isBaseField = baseFieldList.contains(columns.getString("COLUMN_NAME").toLowerCase());
        MetaDataDTO metaDataDTO = new MetaDataDTO();
        metaDataDTO.setIsBaseField(isBaseField);// 是否為baseEntity的屬性
        metaDataDTO.setColumnName(columns.getString("COLUMN_NAME"));// 欄位名稱
        metaDataDTO.setColumnType(columns.getString("TYPE_NAME"));// 欄位類型
        metaDataDTO.setColumnSize(columns.getInt("COLUMN_SIZE"));// 欄位大小
        metaDataDTO.setRemark(columns.getString("REMARKS"));// 欄位說明
        metaDataDTO.setIsNullable(columns.getString("IS_NULLABLE"));// nullable

        if (!isBaseField) { // baseEntity屬性以外的需要注意是否需要import敘述
            String setMethodName = needImportMap.get(columns.getString("TYPE_NAME"));
            if (StringUtils.hasText(setMethodName)) {// 若是需要import的類別會找到對應的setter方法
                Method method = freeMakerGenDTO.getClass().getDeclaredMethod(setMethodName,
                        Boolean.class);
                method.invoke(freeMakerGenDTO, true);
            }
        }
        return metaDataDTO;
    }

    /**
     * codeGen主流程
     *
     * @param schema
     * @param tableName
     * @param destination
     * @param string
     * @throws Exception
     */
    public void codeGen(FreeMakerGenDTO freeMakerGenDTO)
            throws Exception {

        // 取得metaData資訊
        getMetadata(freeMakerGenDTO);

        if (freeMakerGenDTO.getMetaDataDTOList().isEmpty()) {
            throw new Exception("DB資訊錯誤");
        }

        // 根據types去產生所需程式碼
        for (String type : types) {
            CodeGenerator.generateCode(freeMakerGenDTO, type);
        }

        //資料庫ddl檔案
        CodeGenerator.createDdl(freeMakerGenDTO);

    }

    /**
     * 指定schema 和 table 取得欄位資訊
     *
     * @param schemaPattern
     * @param tableNamePattern
     * @return
     * @throws SQLException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void getMetadata(FreeMakerGenDTO freeMakerGenDTO)
            throws SQLException, NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        // 透過jdbcTemplate取得metaData
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();

        //取得ddl資訊
        getDdl(freeMakerGenDTO);

        // 指定table的欄位資訊
        ResultSet columns = metaData.getColumns(null, freeMakerGenDTO.getSchema(), freeMakerGenDTO.getTableName(),
                null);

        // 宣告儲存metaData資訊的DTO
        List<MetaDataDTO> metaDataDTOList = new ArrayList<>();
        freeMakerGenDTO.setMetaDataDTOList(metaDataDTOList);

        // 遍歷指定table的欄位資訊
        while (columns.next()) {
            metaDataDTOList.add(getMetaDataDTO(freeMakerGenDTO, columns));
        }
    }

    private void getDdl(FreeMakerGenDTO freeMakerGenDTO) {
        String query = "SHOW CREATE TABLE " + "`" + freeMakerGenDTO.getSchema() + "`." + freeMakerGenDTO.getTableName();
        var ddl = jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getString(2));
        freeMakerGenDTO.setDdl(ddl);
    }

    /**
     * 指定schema 取得所有table名稱
     *
     * @param schemaPattern
     * @return
     * @throws SQLException
     */
    public List<String> getTableNames(String pattern) throws SQLException {

        List<String> tableNames = new ArrayList<>();

        // 透過jdbcTemplate取得metaData
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();

        // 指定table的欄位資訊
        ResultSet tablesResultSet = metaData.getTables(pattern, pattern, null, new String[]{"TABLE"});

        while (tablesResultSet.next()) {
            String tableName = tablesResultSet.getString("TABLE_NAME");
            tableNames.add(tableName);
        }

        return tableNames;
    }

    public List<String> getAllSchema() throws SQLException {
        List<String> schemas = new ArrayList<>();

        // 透過jdbcTemplate取得metaData
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();

        // 指定table的欄位資訊
        ResultSet schemaResultSet = metaData.getCatalogs();

        while (schemaResultSet.next()) {
            String schema = schemaResultSet.getString("TABLE_CAT");

            System.out.println("schema:" + schema);

            if (!excludeSchemaList.contains(schema)) {
                schemas.add(schema);
            }
        }

        return schemas;
    }

}
