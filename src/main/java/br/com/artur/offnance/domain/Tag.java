package br.com.artur.offnance.domain;

import br.com.artur.offnance.domain.dto.TagOutPutDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "id")
@Builder
@Getter
@ToString(callSuper = true, includeFieldNames = true)
@Table(name = "tags")
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id_tag")),
        @AttributeOverride(name = "version", column = @Column(name = "ver_tag")),
        @AttributeOverride(name = "createdAt", column = @Column(name = "cre_at_tag")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "upd_at_tag"))
})
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "id_usu")
    @JsonBackReference
    @ToString.Exclude
    private User user;

    @JoinColumn(name = "id_pes")
    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    private Person person;

    @Column(name = "nom_tag")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_tip")
    @JsonBackReference
    @ToString.Exclude
    private Type type;

    @Column(name = "per_tag")
    private BigDecimal percentage;

    @ManyToMany
    @JoinTable(name = "tags_dados", joinColumns = @JoinColumn(name = "id_tag", referencedColumnName = "id_tag"),
            inverseJoinColumns = @JoinColumn(name = "id_dad", referencedColumnName = "id_dad"))
    @JsonBackReference
    @ToString.Exclude
    List<Data> datas;


    public List<Data> getDatas() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        return datas;
    }

    public TagOutPutDto toOutPutDto() {

        final var tagOutPutDto = TagOutPutDto.builder()
                .name(name)
                .user(TagOutPutDto.UserOutPutDto.builder().username(user.getUsername()).build())
                .person(TagOutPutDto.PersonOutPutDto.builder().name(person.getName()).build())
                .type(TagOutPutDto.TypeOutputDto.builder().name(type.getName()).build())
                .id(getId())
                .percentage(getPercentage())
                .build();
        return tagOutPutDto;
    }
}
