package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.Type;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import br.com.artur.offnance.repositories.TypeRepository;
import br.com.artur.offnance.repositories.UserRepository;
import br.com.artur.offnance.service.TypeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TypeServiceImp implements TypeService {

  private final TypeRepository typeRepository;

  private final UserRepository userRepository;

  @Override
  public TypeOutputDto create(@NonNull TypeDto dto) {
    final var user = userRepository.findById(dto.getIdUser());
    if (user.isPresent()) {
      final var usu = user.get();
      var type = Type.builder()
          .name(dto.getName())
          .user(usu)
          .build();
      type=typeRepository.save(type);
      return type.toOutput();
    }
    throw new RuntimeException();
  }
}
