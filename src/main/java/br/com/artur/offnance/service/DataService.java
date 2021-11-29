package br.com.artur.offnance.service;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

public interface DataService {

  DataOutPutDto create(HttpServletResponse response, DataDto dto, User user);

  Page<DataOutPutDto> findAll(int page, int quantity);

  DataOutPutDto findById(Long id);
}
