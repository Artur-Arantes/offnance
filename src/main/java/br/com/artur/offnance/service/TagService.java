package br.com.artur.offnance.service;


import br.com.artur.offnance.domain.TagPagedList;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.domain.dto.TagPageRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;

public interface TagService {

  TagOutPutDto create(HttpServletResponse response, User user, TagDto dto);

  TagPagedList find(@NonNull TagPageRequest pageRequest);
}
