package org.cclemongen.service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cclemongen.dto.MetaDataDTO;
import org.cclemongen.generator.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MetaDataService {

    private static final List<String> excludeScema = new ArrayList<>();
    private final static List<String> baseField = new ArrayList<>();
    static {
        excludeScema.add("information_schema");
        excludeScema.add("mysql");
        excludeScema.add("performance_schema");
        excludeScema.add("sys");
        baseField.add("id");
        baseField.add("last_modified_user_id");
        baseField.add("last_modified_time");
        baseField.add("deleted");
        baseField.add("create_user_id");
        baseField.add("create_time");
        baseField.add("version");
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * codeGen主流程
     * 
     * 
     * @param schema
     * @param tableName
     * @param destination
     * @param string
     * @throws Exception
     */
    public void codeGen(String schema, String tableName, String destination, String groupId)
            throws Exception {

        // 取得metaData資訊
        List<MetaDataDTO> metaDataDTOList = getMetadata(schema, tableName);

        if (metaDataDTOList.size() == 0) {
            throw new Exception("DB資訊錯誤");
        }

        String[] types = { "entity", "repository", "specification" };

        // 根據types去產生所需程式碼
        for (String type : types) {
            CodeGenerator.generateCode(tableName, metaDataDTOList, destination, type, groupId);
        }

    }

    /**
     * 指定schema 和 table 取得欄位資訊
     * 
     * @param schemaPattern
     * @param tableNamePattern
     * @return
     * @throws SQLException
     */
    private List<MetaDataDTO> getMetadata(String schemaPattern, String tableNamePattern) throws SQLException {

        // 透過jdbcTemplate取得metaData
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();

        // 指定table的欄位資訊
        ResultSet columns = metaData.getColumns(null, schemaPattern, tableNamePattern, null);

        // 宣告儲存metaData資訊的DTO
        List<MetaDataDTO> metaDataDTOList = new ArrayList<>();

        // 遍歷指定table的欄位資訊
        while (columns.next()) {

            if (baseField.contains(columns.getString("COLUMN_NAME").toLowerCase())) {
                continue;
            }

            MetaDataDTO metaDataDTO = new MetaDataDTO();

            metaDataDTO.setColumnName(columns.getString("COLUMN_NAME"));// 欄位名稱
            metaDataDTO.setColumnType(columns.getString("TYPE_NAME"));// 欄位類型
            metaDataDTO.setColumnSize(columns.getInt("COLUMN_SIZE"));// 欄位大小
            metaDataDTO.setRemark(columns.getString("REMARKS"));// 欄位說明
            metaDataDTO.setIsNullable(columns.getString("IS_NULLABLE"));// nullable

            metaDataDTOList.add(metaDataDTO);
        }

        return metaDataDTOList;

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
        ResultSet tablesResultSet = metaData.getTables(pattern, pattern, null, new String[] { "TABLE" });

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

            if (!excludeScema.contains(schema)) {
                schemas.add(schema);
            }
        }

        return schemas;
    }

}
