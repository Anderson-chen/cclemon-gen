package org.cclemongen;

import org.cclemongen.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
