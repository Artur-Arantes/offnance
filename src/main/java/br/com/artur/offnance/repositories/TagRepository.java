package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Tag;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
  Page<Tag> findAll(Pageable pages);
}
