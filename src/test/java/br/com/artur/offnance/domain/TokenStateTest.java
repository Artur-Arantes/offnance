package br.com.artur.offnance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenStateTest {

  TokenState tokenState= mock(TokenState.class);

  @Test
  @DisplayName("testando")
  void equalsAndHashCode(){
    TokenState token= new TokenState("a",1L);
    assertThat(tokenState.hashCode()).isNotEqualTo(token.hashCode());
  }
}
