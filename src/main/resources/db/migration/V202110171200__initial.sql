CREATE TABLE IF NOT EXISTS pessoa(
    id_pes BIGINT(20) NOT NULL AUTO_INCREMENT,
    nom_pes VARCHAR(255) NOT NULL,
    ver_pes INT(11) NOT NULL DEFAULT 1,
    cre_at_pes DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    upd_at_pes DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_pessoa PRIMARY KEY (id_pes)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS permissao(
    id_per BIGINT(20) NOT NULL AUTO_INCREMENT,
    nom_per VARCHAR(255) NOT NULL,
    ver_per INT(11) NOT NULL DEFAULT 1,
    cre_at_per DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    upd_at_per DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_permissao PRIMARY KEY(id_per)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS usuario(
    id_usu BIGINT(20) NOT NULL AUTO_INCREMENT,
    log_usu VARCHAR(10) NOT NULL,
    sen_usu VARCHAR(255) NOT NULL,
    id_pes BIGINT(20) NOT NULL,
    ver_usu INT(11) NOT NULL DEFAULT 1,
    acc_non_exp_usu BIT(1),
    ena_usu BIT(1),
    cre_non_exp_usu BIT(1),
    acc_non_loc_usu BIT(1),
    cre_at_usu DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    upd_at_usu DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_usuario PRIMARY KEY(id_usu),
    CONSTRAINT fk_usuario_pessoa FOREIGN KEY (id_pes) REFERENCES pessoa(id_pes)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS usuario_permissao(
    id_per BIGINT(20) NOT NULL,
    id_usu BIGINT(20) NOT NULL,
    CONSTRAINT pk_usuario_permissao PRIMARY KEY (id_per, id_usu),
    CONSTRAINT fk_usuario_permissao_permissao FOREIGN KEY(id_per) REFERENCES permissao(id_per),
    CONSTRAINT fk_usuario_permissao_usu FOREIGN KEY(id_usu) REFERENCES usuario(id_usu)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tipo(
    id_tip BIGINT(20) NOT NULL AUTO_INCREMENT,
    id_usu BIGINT(20) NOT NULL,
    nom_tip VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tipo PRIMARY KEY(id_tip),
    CONSTRAINT fk_tipo_usuario FOREIGN KEY (id_usu) REFERENCES usuario(id_usu)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS dados(
    id_dad BIGINT(20) NOT NULL AUTO_INCREMENT,
    nom_dad Varchar(255) NOT NULL,
    dat_dad DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    val_dad FLOAT(15) NOT NULL,
    sta_dad VARCHAR(20) NOT NULL,
    id_usu bigint(20) NOT NULL,
    CONSTRAINT pk_dados PRIMARY KEY (id_dad),
    CONSTRAINT fk_dados_usuario FOREIGN KEY (id_usu) REFERENCES usuario(id_usu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tags(
    id_usu BIGINT(20) NOT NULL,
    id_pes BIGINT(20) NOT NULL,
    id_tip BIGINT(20) NOT NULL,
    id_tag BIGINT(20) NOT NULL AUTO_INCREMENT,
    nom_tag VARCHAR(255) NOT NULL,
    per_tag FLOAT(4) NOT NULL,
    CONSTRAINT pk_tags PRIMARY KEY (id_tag),
    CONSTRAINT fk_tags_pessoa FOREIGN KEY (id_pes) REFERENCES pessoa(id_pes),
    CONSTRAINT fk_tags_tipo FOREIGN KEY (id_tip) REFERENCES tipo(id_tip),
    CONSTRAINT fk_tags_usuario FOREIGN KEY (id_usu) REFERENCES usuario(id_usu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tags_dados(
    id_dad BIGINT(20) NOT NULL,
    id_tag BIGINT(20) NOT NULL,
    CONSTRAINT pk_tags_dados PRIMARY KEY(ID_DAD, ID_TAG),
    CONSTRAINT fk_tags_dados_dados FOREIGN KEY(id_dad) REFERENCES dados(id_dad),
    CONSTRAINT fk_tags_dados_tags FOREIGN KEY(id_tag) REFERENCES tags(id_tag)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS tag_texto(
    id_tag BIGINT(20) NOT NULL,
    id_tag_tex BIGINT(20) NOT NULL AUTO_INCREMENT,
    tex_tag_tex VARCHAR(500),
    CONSTRAINT pk_tag_texto PRIMARY KEY (id_tag_tex),
    CONSTRAINT fk_tag_texto_tags  FOREIGN KEY(id_tag) REFERENCES tags(id_tag)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
