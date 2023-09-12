package org.cclemongen;

import java.io.IOException;

import org.cclemongen.generator.DynamicQueryCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import freemarker.template.TemplateException;

@SpringBootApplication
public class CclemongenApplication {

	public static void main(String[] args) {
		SpringApplication.run(CclemongenApplication.class, args);

		try {
			DynamicQueryCodeGenerator.generateQueryCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
