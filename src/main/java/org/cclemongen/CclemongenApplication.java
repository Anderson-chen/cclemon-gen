package org.cclemongen;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.cclemongen.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import freemarker.template.TemplateException;

@SpringBootApplication
public class CclemongenApplication {

	@Autowired
	MetaDataService metaDataService;

	@Value("${classpath:resources/output/entity}")
	String entityPath;

	@Value("${classpath:resources/output/repository}")
	String repositoryPath;

	@Value("${classpath:resources/output/dynamicQuery}")
	String dymamicQueryPath;

	public static void main(String[] args) {
		SpringApplication.run(CclemongenApplication.class, args);
	}

	@PostConstruct
	private void init() throws SQLException, IOException, TemplateException {

		String schema = "cclemon";

		String tableName = "body";

		String destination = "C:\\git";

		metaDataService.codeGen(schema, tableName, destination);
	}

}
