package br.com.artur.offnance.service;


import br.com.artur.offnance.domain.TagPagedList;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface TagService {
  TagPagedList findAll(PageRequest pageRequest);

  TagOutPutDto create(HttpServletResponse response, User user, TagDto dto);

  TagOutPutDto findById(Long id);


}
