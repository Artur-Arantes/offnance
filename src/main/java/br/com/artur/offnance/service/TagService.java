package br.com.artur.offnance.service;


import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

public interface TagService {

TagOutPutDto create(HttpServletResponse response, User user, TagDto dto);

Page<TagOutPutDto> findAll(int page, int quantity);

TagOutPutDto findById(Long id);

List<Long> findAllById(Long listLong);

}
