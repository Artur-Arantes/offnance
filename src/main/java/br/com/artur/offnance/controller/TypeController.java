package br.com.artur.offnance.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import br.com.artur.offnance.service.TypeService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/type", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeController {

  private final TypeService typeService;

  @RequestMapping(method = POST, value = "/")
  public TypeOutputDto create(HttpServletResponse response, @RequestBody TypeDto dto) {
    return typeService.create(dto);
  }
}
