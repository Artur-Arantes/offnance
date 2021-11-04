package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.Data;
import br.com.artur.offnance.domain.Tag;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import br.com.artur.offnance.repositories.DataRepository;
import br.com.artur.offnance.repositories.TagRepository;
import br.com.artur.offnance.service.DataService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataServiceImp implements DataService {
  private final DataRepository dataRepository;
  private final TagRepository tagRepository;

  @Override
  @Transactional
  public DataOutPutDto create(@NonNull final HttpServletResponse response,
                              @NonNull final DataDto dto, @NonNull final User user) {
    List<Tag> tags = (List<Tag>) tagRepository.findAllById(dto.getIdTags());
    if (tags.isEmpty()) {
      throw new NullPointerException();
    }
    Data data = Data.builder()
        .name(dto.getName())
        .value(dto.getValue())
        .user(user)
        .status(dto.getStatus())
        .build();
    data = dataRepository.save(data);
    data.getTags().addAll(tags);
    data = dataRepository.save(data);
    return data.toOutput();
  }
}
