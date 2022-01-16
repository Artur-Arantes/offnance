package br.com.artur.offnance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tag_texto")
public class TagText {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_tag_tex")
  private Long id;

  @ManyToOne
  private Tag tag;

  @Column(name = "tex_tag_tex")
  private String text;
}
