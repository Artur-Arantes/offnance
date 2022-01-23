package br.com.artur.offnance.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import br.com.artur.offnance.domain.DataPagedList;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import br.com.artur.offnance.service.DataService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@RequestMapping(value = "api/data", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataController {

  private static final Integer DEFAULT_PAGE_NUMBER = 0;
  private static final Integer DEFAULT_PAGE_SIZE = 25;
  private final DataService dataService;


  @Transactional
  @RequestMapping(method = POST, value = "/")
  public DataOutPutDto create(@NonNull @RequestBody final DataDto dto,
                              @NonNull @AuthenticationPrincipal User user) {
    return dataService.create(dto, user);
  }

  @RequestMapping(method = GET, value = "/")
  @Transactional
  public DataPagedList findAll(@RequestParam(name = "pageNumber", required = false) int pageNumber,
                               @RequestParam(name = "pageSize", required = false) int pageSize
  ) {
    if (pageNumber < 0) {
      pageNumber = DEFAULT_PAGE_NUMBER;
    }
    if (pageSize < 1) {
      pageSize = DEFAULT_PAGE_SIZE;
    }
    final var page = PageRequest.of(pageNumber, pageSize);
    final var dataPagedList = dataService.findAll(page);
    return DataPagedList.fromPageable(dataPagedList);
  }

  @RequestMapping(method = GET, value = "/find")
  @Transactional
  public DataOutPutDto findById(@RequestParam @NonNull Long id) {
    return dataService.findById(id);
  }

}
