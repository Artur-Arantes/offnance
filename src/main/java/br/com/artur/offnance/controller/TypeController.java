package br.com.artur.offnance.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import br.com.artur.offnance.service.TypeService;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/type", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeController {

  private final TypeService typeService;

  @RequestMapping(method = POST, value = "/")
  @Transactional
  public TypeOutputDto create(@NonNull  final HttpServletResponse response,
                              @NonNull @RequestBody final TypeDto dto,
                              @AuthenticationPrincipal @NonNull final User user) {
    return typeService.create(dto, user);
  }
  @RequestMapping(method = GET,value = "/")
  @Transactional
  public Page<TypeOutputDto> findAll(@NonNull final HttpServletResponse response,
                                  @NonNull @RequestBody final TypeDto dto,
                                  @AuthenticationPrincipal @NonNull final User user,
                                  @RequestParam int page, @RequestParam int quantity){

    return typeService.findAll( page,  quantity);
  }
  @RequestMapping(method = GET, value = "/find")
  @Transactional
  public TypeOutputDto findById(Long id){
    return typeService.findById(id);
  }
}
