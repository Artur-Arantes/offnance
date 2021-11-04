package br.com.artur.offnance.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import br.com.artur.offnance.service.DataService;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/data", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataController {

  private final DataService dataService;

  @RequestMapping(method = POST, value = "/")
  public DataOutPutDto create(@NonNull final HttpServletResponse response,
                              @NonNull @RequestBody final DataDto dto,
                              @NonNull @AuthenticationPrincipal  User user) {
    return dataService.create(response, dto, user);
  }
}
