package br.com.artur.offnance.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import br.com.artur.offnance.domain.TypePagedList;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import br.com.artur.offnance.service.TypeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
  private static final Integer DEFAULT_PAGE_NUMBER = 0;
  private static final Integer DEFAULT_PAGE_SIZE = 25;

  @RequestMapping(method = POST, value = "/")
  @Transactional
  public TypeOutputDto create(@NonNull @RequestBody final TypeDto dto,
                              @AuthenticationPrincipal @NonNull final User user) {
    return typeService.create(dto, user);
  }

  @RequestMapping(method = GET, value = "/")
  @Transactional
  public TypePagedList findAll(@RequestParam(name = "pageNumber", required = false) int pageNumber,
                               @RequestParam(name = "pageSize", required = false) int pageSize
  ) {
    if (pageNumber < 0) {
      pageNumber = DEFAULT_PAGE_NUMBER;
    }
    if (pageSize < 1) {
      pageSize = DEFAULT_PAGE_SIZE;
    }
    final var page = PageRequest.of(pageNumber, pageSize);
    final var typePagedList = typeService.findAll(page);
    return TypePagedList.fromPageable(typePagedList);
  }

  @RequestMapping(method = GET, value = "/find")
  @Transactional
  public TypeOutputDto findById(Long id) {
    return typeService.findById(id);
  }
}
