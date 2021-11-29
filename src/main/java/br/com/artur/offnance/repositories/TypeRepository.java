package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends CrudRepository<Type, Long> {
  Page<Type> findAll(Pageable pages);
}
