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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


  @Override
  @Transactional
  public Page<TypeOutputDto> findAll(int page, int quantity) {
    Pageable pages = PageRequest.of(page, quantity);
    final var type = typeRepository.findAll(pages);
    return type.map(t -> t.toOutput());
  }

  @Override
  @Transactional
  public TypeOutputDto findById(Long id) {
    return typeRepository.findById(id).get().toOutput();
  }
}
