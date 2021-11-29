package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.Tag;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.exceptions.PersonNotFoundException;
import br.com.artur.offnance.exceptions.TypeNotFoundException;
import br.com.artur.offnance.repositories.PersonRepository;
import br.com.artur.offnance.repositories.TagRepository;
import br.com.artur.offnance.repositories.TypeRepository;
import br.com.artur.offnance.service.TagService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public TagOutPutDto create(@NonNull final HttpServletResponse response, @NonNull final User user,
                             @NonNull final TagDto dto) {
    final var personOptional = personRepository.findById(dto.getIdPerson());
    if (!personOptional.isPresent()) {
      throw new PersonNotFoundException(dto.getIdPerson());
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

  @Override
  @Transactional
  public Page<TagOutPutDto> findAll(int page, int quantity) {
    Pageable pages = PageRequest.of(page, quantity);
    final var tag = tagRepository.findAll(pages);
    return tag.map(t -> TagOutPutDto.builder().build());
  }

  @Override
  @Transactional
  public TagOutPutDto findById(Long id) {
    return tagRepository.findById(id).get().toOutPutDto();
  }

  @Override
  public List<Long> findAllById(Long listLong) {
    List<Long> list = new ArrayList<>();
    tagRepository.findAllById(listLong).forEach(list::add);
    return list;
  }
}
