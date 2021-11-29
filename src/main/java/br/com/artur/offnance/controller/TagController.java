package br.com.artur.offnance.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.service.TagService;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @RequestMapping(method = GET, value = "/")
  @Transactional
  public Page<TagOutPutDto> findAll(@NonNull final HttpServletResponse response,
                                     @NonNull @RequestBody final TagDto dto,
                                     @AuthenticationPrincipal @NonNull User user,
                                     @RequestParam int page,@RequestParam int quantity){

    return tagService.findAll(page, quantity);
  }
  @RequestMapping
  @Transactional
  public TagOutPutDto findById(@NonNull final HttpServletResponse response,
                               @NonNull @RequestBody final TagDto tagDto,
                               @AuthenticationPrincipal @NonNull User user,
                               final Long id){
  return tagService.findById(id);
  }
}
