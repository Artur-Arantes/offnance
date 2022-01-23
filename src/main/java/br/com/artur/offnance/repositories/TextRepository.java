package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Text;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TextRepository extends CrudRepository<Text, Long> {

  List<Text> findAllByTextIn(List<String> text);

  @Query(value = "select * from texto t where regexp_like(?1, t.tex_tex)", nativeQuery = true)
  List<Text> getText(String text);
}