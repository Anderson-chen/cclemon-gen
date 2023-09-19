package org.cclemongen.controller;

import org.cclemongen.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GeneratorController {

   @Autowired
   MetaDataService metaDataService;

   @GetMapping("/home")
   public String home() {

      return "generator.html";
   }

   @PostMapping("/codeGen")
   @ResponseBody
   public Response codeGen(@RequestBody Request req) throws Exception {

      log.info("req : {} ", req);

      metaDataService.codeGen(req.getSchema(), req.getTableName(), req.getDestination());

      return Response.builder().code(200).msg("產檔成功!").build();

   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<Response> handleException(Exception e) {

      log.error("{}", e.getMessage());

      return new ResponseEntity<Response>(Response.builder().code(200).msg("產檔案失敗!").build(), null, HttpStatus.OK);
   }

}

@Data
class Request {
   String schema;
   String tableName;
   String destination;
}

@Data
@Builder
class Response {
   private int code;
   private String msg;
}
