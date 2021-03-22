ALTER TABLE `base_mala_direta`.`advogado` 
CHANGE COLUMN `dataCadastro` `dataCadastro` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `idlogin` `idlogin` INT(11) NULL DEFAULT NULL ;

ALTER TABLE `base_mala_direta`.`empresa` 
CHANGE COLUMN `dataCadastro` `dataCadastro` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `idlogin` `idlogin` INT(11) NULL DEFAULT NULL ;

ALTER TABLE `base_mala_direta`.`fases` 
CHANGE COLUMN `dataCadastro` `dataCadastro` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `idlogin` `idlogin` INT(11) NULL DEFAULT NULL ;

ALTER TABLE `base_mala_direta`.`motivos` 
CHANGE COLUMN `dataCadastro` `dataCadastro` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `idlogin` `idlogin` INT(11) NULL DEFAULT NULL ;

ALTER TABLE `base_mala_direta`.`pessoa` 
CHANGE COLUMN `dataCadastro` `dataCadastro` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `idlogin` `idlogin` INT(11) NULL DEFAULT NULL ;

ALTER TABLE `base_mala_direta`.`processo`
CHANGE COLUMN `dataCadastro` `dataCadastro` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `idlogin` `idlogin` INT(11) NULL DEFAULT NULL ;

UPDATE `base_mala_direta`.`advogado` SET `data_da_expedicao` = NULL WHERE `data_da_expedicao` = '0000-00-00';