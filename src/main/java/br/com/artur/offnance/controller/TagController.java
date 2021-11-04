package br.com.artur.offnance.controller;


import static org.springframework.web.bind.annotation.RequestMethod.POST;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.service.TagService;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

  private final TagService tagService;

  @RequestMapping(method = POST, value = "/")
  @Transactional
  public TagOutPutDto create(@NonNull final HttpServletResponse response,
                             @NonNull @RequestBody final TagDto dto,
                             @AuthenticationPrincipal @NonNull User user) {
    return tagService.create(response, user, dto);
  }
}
