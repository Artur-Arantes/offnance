package br.com.artur.offnance.domain;

import java.time.ZonedDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@MappedSuperclass
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Generated
@ToString
public abstract class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Version
  protected Integer version;

  protected ZonedDateTime createdAt;

  private ZonedDateTime updatedAt;

  @PrePersist
  protected void prePersist() {
    createdAt = updatedAt = ZonedDateTime.now();
  }

  @PreUpdate
  protected void preUpdate() {
    createdAt = ZonedDateTime.now();
  }

}
