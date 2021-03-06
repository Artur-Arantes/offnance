package br.com.artur.offnance.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import br.com.artur.offnance.domain.dto.TagPageRequest;
import br.com.artur.offnance.domain.TagPagedList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

  private final TagService tagService;
  private static final Integer DEFAULT_PAGE_NUMBER = 0;
  private static final Integer DEFAULT_PAGE_SIZE = 25;


  @RequestMapping(method = POST, value = "/")
  @Transactional
  public TagOutPutDto create(@NonNull final HttpServletResponse response,
                             @NonNull @RequestBody final TagDto dto,
                             @AuthenticationPrincipal @NonNull User user) {
    return tagService.create(response, user, dto);
  }

  @RequestMapping(method = GET, value = "/")
  @Transactional
  public TagPagedList find(@RequestParam(name = "pageNumber", required = false) int pageNumber,
                           @RequestParam(name = "pageSize", required = false) int pageSize,
                           @RequestParam(name = "text", required = false) String text,
                           @RequestParam(name = "id", required = false) Long id) {
    if (pageNumber < 0) {
      pageNumber = DEFAULT_PAGE_NUMBER;
    }
    if (pageSize < 1) {
      pageSize = DEFAULT_PAGE_SIZE;
    }
    final var page = TagPageRequest.of(pageNumber, pageSize,text,id);
    final var tagPagedList = tagService.find(page);
    return TagPagedList.fromPageable(tagPagedList);
  }
}