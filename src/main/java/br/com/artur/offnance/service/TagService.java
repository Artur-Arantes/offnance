package br.com.artur.offnance.service;


import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import javax.servlet.http.HttpServletResponse;

public interface TagService {

TagOutPutDto create(HttpServletResponse response, User user, TagDto dto);

}