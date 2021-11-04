package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.Tag;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.exceptions.TypeNotFoundException;
import br.com.artur.offnance.repositories.PersonRepository;
import br.com.artur.offnance.repositories.TagRepository;
import br.com.artur.offnance.repositories.TypeRepository;
import br.com.artur.offnance.service.TagService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagServiceImp implements TagService {

  private final TagRepository tagRepository;
  private final PersonRepository personRepository;
  private final TypeRepository typeRepository;


  @Override
  @Transactional
  public TagOutPutDto create(HttpServletResponse response, User user, TagDto dto) {
    final var personOptional = personRepository.findById(dto.getIdPerson());
    if (!personOptional.isPresent()) {
      throw new NullPointerException();
    }
    final var typeOptional = typeRepository.findById(dto.getIdType());
    if (!typeOptional.isPresent()) {
      throw new TypeNotFoundException(user.getUsername(), dto.getIdType());
    }
    var tag = Tag.builder().name(dto.getName())
        .user(user)
        .person(personOptional.get())
        .type(typeOptional.get())
        .percentage(dto.getPercentage())
        .build();
    tag = tagRepository.save(tag);
    return tag.toOutPutDto();
  }
}
