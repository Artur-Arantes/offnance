package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.Data;
import br.com.artur.offnance.domain.DataPagedList;
import br.com.artur.offnance.domain.Tag;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import br.com.artur.offnance.exceptions.TagNotFoundException;
import br.com.artur.offnance.repositories.DataRepository;
import br.com.artur.offnance.repositories.TagRepository;
import br.com.artur.offnance.service.DataService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataServiceImp implements DataService {

  private final DataRepository dataRepository;

  private final TagRepository tagRepository;

  @Override
  @Transactional
  public DataOutPutDto create(@NonNull final DataDto dto, @NonNull final User user) {
    List<Tag> tags = (List<Tag>) tagRepository.findAllById(dto.getIdTags());
    if (tags.isEmpty()) {
      throw new TagNotFoundException(dto.getIdTags(), dto);
    }
    var data = Data.builder()
        .name(dto.getName())
        .value(dto.getValue())
        .user(user)
        .status(dto.getStatus())
        .build();
    data = dataRepository.save(data);
    data.getTags().addAll(tags);
    final var dt = dataRepository.save(data);
    tags.stream().map(tag -> {
      tag.getDatas().add(dt);
      return tag;
    });
    tagRepository.saveAll(tags);
    return data.toOutput();
  }

  @Override
  @Transactional
  public DataPagedList findAll(PageRequest pageRequest) {
    final var dataPages = dataRepository.findAll(pageRequest);
    DataPagedList dataPagedList = new DataPagedList(dataPages.getContent()
        .stream()
        .map(t -> t.toOutput())
        .collect(Collectors.toList()), PageRequest.of(dataPages.getPageable().getPageNumber(),
        dataPages.getPageable().getPageSize()), dataPages.getTotalElements());
    return dataPagedList;
  }

  @Override
  @Transactional
  public DataOutPutDto findById(Long id) {
    return dataRepository.findById(id).get().toOutput();
  }


}
