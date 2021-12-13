package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DataRepository extends PagingAndSortingRepository<Data, Long> {
  Page<Data> findAll(Pageable page);
}
