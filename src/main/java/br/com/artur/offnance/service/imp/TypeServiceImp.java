package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.Type;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import br.com.artur.offnance.repositories.TypeRepository;
import br.com.artur.offnance.service.TypeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TypeServiceImp implements TypeService {

  private final TypeRepository typeRepository;


  @Override
  @Transactional
  public TypeOutputDto create(@NonNull final TypeDto dto, @NonNull final User user) {
    var type = Type.builder()
        .name(dto.getName())
        .user(user)
        .build();
    type = typeRepository.save(type);
    return type.toOutput();
  }
}
