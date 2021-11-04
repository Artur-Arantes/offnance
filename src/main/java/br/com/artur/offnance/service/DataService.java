package br.com.artur.offnance.service;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import javax.servlet.http.HttpServletResponse;

public interface DataService {

  public DataOutPutDto create(HttpServletResponse response, DataDto dto, User user);
}
