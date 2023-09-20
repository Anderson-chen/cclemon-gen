package org.cclemongen.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.cclemongen.dto.FreeMakerGenDTO;
import org.cclemongen.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GeneratorController {

   @Autowired
   MetaDataService metaDataService;

   @GetMapping(value = "/home")
   public String home(HttpServletResponse res) {
      return "generator.html";
   }

   @PostMapping("/codeGen")
   @ResponseBody
   public Response codeGen(@RequestBody Request req) throws Exception {

      log.info("req : {} ", req);

      FreeMakerGenDTO freeMakerGenDTO = FreeMakerGenDTO.builder().schema(req.getSchema()).tableName(req.getTableName())
            .destination(req.getDestination())
            .groupId(req.getGroupId()).build();

      metaDataService.codeGen(freeMakerGenDTO);

      return Response.builder().result(req).code(200).msg("產檔成功!").build();

   }

   @GetMapping("/getAllSchema")
   @ResponseBody
   public Response getAllSchema() throws Exception {

      List<String> schemas = metaDataService.getAllSchema();

      return Response.builder().result(schemas).code(200).msg("查詢成功").build();

   }

   @GetMapping("/getTableNames")
   @ResponseBody
   public Response getTableNames(@RequestParam("schema") String schema) throws Exception {

      log.info("schema : {} ", schema);

      List<String> tableNames = metaDataService.getTableNames(schema);

      return Response.builder().result(tableNames).code(200).msg("查詢成功").build();

   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<Response> handleException(Exception e) {

      log.error("{}", e.getMessage());

      return new ResponseEntity<Response>(Response.builder().code(200).msg("執行失敗!").build(), null, HttpStatus.OK);
   }

}

@Data
class Request {
   private String schema;
   private String tableName;
   private String destination;
   private String groupId;
}

@Data
@Builder
class Response {
   private int code;
   private String msg;
   private Object result;
}
