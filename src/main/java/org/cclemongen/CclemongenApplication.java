package org.cclemongen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.cclemongen.dto.MetaDataDTO;
import org.cclemongen.generator.CodeGenerator;
import org.cclemongen.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import freemarker.template.TemplateException;

@SpringBootApplication
public class CclemongenApplication {

	@Autowired
	MetaDataService metaDataService;

	public static void main(String[] args) {
		SpringApplication.run(CclemongenApplication.class, args);
	}

	@PostConstruct
	private void init() throws SQLException, IOException, TemplateException {

		String schema = "cclemon";

		String tableName = "body";

		// 取得metaData資訊
		List<MetaDataDTO> metaDataDTOList = metaDataService.getMetadata(schema, tableName);

		// 依據產生檔案
		CodeGenerator.generateEntityCode(tableName, metaDataDTOList);
	}

}
