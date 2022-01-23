
package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

  Page<Tag> findAll(Pageable page);

  Page<Tag> findAllByIdIn(List<Long> ids, Pageable page);


}