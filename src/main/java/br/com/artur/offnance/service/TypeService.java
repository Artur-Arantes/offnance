package br.com.artur.offnance.service;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface TypeService {

  TypeOutputDto create(TypeDto dto, User user);

  Page<TypeOutputDto> findAll(PageRequest pageRequest);

  TypeOutputDto findById(Long id);
}
