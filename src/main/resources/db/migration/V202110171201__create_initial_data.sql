INSERT INTO permissao(nom_per) VALUES('USER');
INSERT INTO permissao(nom_per) VALUES('ADMIN');
INSERT INTO pessoa(nom_pes,ver_pes,cre_at_pes,upd_at_pes) VALUES('artur',1,now(), now());
INSERT INTO usuario(sen_usu,log_usu,id_pes,acc_non_loc_usu,acc_non_exp_usu ,cre_non_exp_usu,ena_usu) VALUES( "$2a$04$UIeR96XNyDwQHJE4G2DWquYMZmopzZ0z5o5f1bc2KA.YgFhGU7/aW","admin",1,1,1,1,1);
INSERT INTO usuario_permissao(id_per, id_usu) VALUES(1,1);
INSERT INTO usuario_permissao(id_per,id_usu) VALUES(2,1);