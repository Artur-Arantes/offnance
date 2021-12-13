package br.com.artur.offnance.service;

import br.com.artur.offnance.domain.DataPagedList;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import org.springframework.data.domain.PageRequest;

public interface DataService {

  DataOutPutDto create(DataDto dto, User user);

  DataPagedList findAll(PageRequest pageRequest);

  DataOutPutDto findById(Long id);


}
