package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
