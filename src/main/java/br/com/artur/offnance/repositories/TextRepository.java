package br.com.artur.offnance.repositories;

import br.com.artur.offnance.domain.Text;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TextRepository extends CrudRepository<Text, Long> {

  List<Text> findAllByText(List<String> text);
}
