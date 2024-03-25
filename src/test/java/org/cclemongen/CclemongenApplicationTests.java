package org.cclemongen;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.cclemongen.controller.GeneratorController;
import org.cclemongen.service.MetaDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GeneratorController.class)
class CclemongenApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MetaDataService service;

	@Test
	void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/home")).andDo(print()).andExpect(status().isOk());

		// this.mockMvc.perform(get("/getAllSchema")).andDo(print()).andExpect(status().isOk());

	}

}

