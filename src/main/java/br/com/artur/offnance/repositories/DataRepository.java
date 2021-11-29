package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DataRepository extends CrudRepository<Data, Long> {
  Page<Data> findAll(Pageable page);
}
