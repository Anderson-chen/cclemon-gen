package org.cclemongen.service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cclemongen.dto.MetaDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MetaDataService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<MetaDataDTO> getMetadata(String schemaPattern, String tableNamePattern) throws SQLException {

        // 透過jdbcTemplate取得metaData
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();

        // 指定table的欄位資訊
        ResultSet columns = metaData.getColumns(null, schemaPattern, tableNamePattern, null);

        // 宣告儲存metaData資訊的DTO
        List<MetaDataDTO> metaDataDTOList = new ArrayList<>();

        // 遍歷指定table的欄位資訊
        while (columns.next()) {

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

}