package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
}
