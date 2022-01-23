package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.Tag;
import br.com.artur.offnance.domain.TagPagedList;
import br.com.artur.offnance.domain.Text;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.domain.dto.TagPageRequest;
import br.com.artur.offnance.exceptions.PersonNotFoundException;
import br.com.artur.offnance.exceptions.TypeNotFoundException;
import br.com.artur.offnance.repositories.PersonRepository;
import br.com.artur.offnance.repositories.TagRepository;
import br.com.artur.offnance.repositories.TextRepository;
import br.com.artur.offnance.repositories.TypeRepository;
import br.com.artur.offnance.service.TagService;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import  static org.apache.commons.collections4.IterableUtils.toList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagServiceImp implements TagService {

  private final TagRepository tagRepository;
  private final PersonRepository personRepository;
  private final TypeRepository typeRepository;
  private final TextRepository textRepository;

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
    final var fetched = textRepository.findAllByTextIn(dto.getTexts());

    final var notCreatedTexts = dto.getTexts().stream()
        .filter(txt -> fetched.stream().noneMatch(entity -> txt.equals(entity.getText())))
        .distinct().map(txt -> Text.builder().text(txt).build())
        .collect(Collectors.toList());

    fetched.addAll(toList(textRepository.saveAll(notCreatedTexts)));

    var tag = Tag.builder().name(dto.getName())
        .user(user)
        .person(personOptional.get())
        .type(typeOptional.get())
        .percentage(dto.getPercentage())
        .build();
    tag = tagRepository.save(tag);

    tag.addTexts(fetched);

    tag = tagRepository.save(tag);

    return tag.toOutPutDto();
  }

  @Override
  @Transactional
  public TagPagedList find(@NonNull final TagPageRequest pageRequest) {
    Page<Tag> tagPages;
    if (pageRequest.hasText()) {
      tagPages = textRepository.getText(pageRequest.getText())
          .stream().findFirst()
          .map(txt -> tagRepository.findAllByIdIn(
              txt.getTags().stream().map(tag -> tag.getId()).collect(Collectors.toList()),
              pageRequest
          )).orElseThrow(RuntimeException::new);
    } else if (pageRequest.hasText()) {
      tagPages = tagRepository.findAllByIdIn(Arrays.asList(pageRequest.getId()), pageRequest);
    } else {
      tagPages = tagRepository.findAll(pageRequest);
    }
    TagPagedList tagPagedList = new TagPagedList(tagPages.getContent()
        .stream()
        .map(t -> t.toOutPutDto())
        .collect(Collectors.toList()), PageRequest.of(tagPages.getPageable().getPageNumber(),
        tagPages.getPageable().getPageSize()), tagPages.getTotalElements());
    return tagPagedList;

  }
}
