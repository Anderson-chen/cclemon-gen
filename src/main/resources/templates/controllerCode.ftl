package ${groupId}.${lowerCaseTableName}.controller;

import java.util.List;

import ${groupId}.${lowerCaseTableName}.dto.${entityClassName}DTO;
import ${groupId}.${lowerCaseTableName}.entity.${entityClassName}Entity;
import ${groupId}.${lowerCaseTableName}.service.${entityClassName}Service;
import ${groupId}.dto.CclemonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ${entityClassName}Controller {

    private final ${entityClassName}Service ${lowerCaseTableName}Service;

    @GetMapping("/${lowerCaseTableName}")
    public CclemonResult<List<BodyEntity>> query(@ModelAttribute ${entityClassName}DTO ${lowerCaseTableName}DTO) {

        List<${entityClassName}Entity> qRes = ${lowerCaseTableName}Service.query(${lowerCaseTableName}DTO);

        return CclemonResult.<List<${entityClassName}Entity>>builder().result(qRes).code(CclemonResult.SUCCESS_CODE).build();
    }

}