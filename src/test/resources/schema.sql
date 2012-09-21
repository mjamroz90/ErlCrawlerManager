-- -----------------------------------------------------
-- Table `erlang_crawler_managementDB`.`ecm_users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `ecm_users` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `login` varchar(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `is_admin` BINARY NULL DEFAULT 0 ,
  `firstname` VARCHAR(45) NULL ,
  `lastname` VARCHAR(45) NULL ,
  UNIQUE UQ_USER_1 (`firstname`, `lastname`),
  PRIMARY KEY (`ID`) );


-- -----------------------------------------------------
-- Table `erlang_crawler_managementDB`.`ecm_nodes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `ecm_nodes` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `address` VARCHAR(45) NOT NULL ,
  UNIQUE UQ_NODE_1 (`name`, `address`),
  PRIMARY KEY (`ID`) );

-- -----------------------------------------------------
-- Table `erlang_crawler_managementDB`.`ecm_stop_session_permissions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `ecm_stop_session_permissions` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `allowed_user_id` INT NOT NULL ,
  `allowing_user_id` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,  
  CONSTRAINT `FK_permissions_users`
    FOREIGN KEY (`allowed_user_id` )
    REFERENCES `ecm_users` (`ID` ),    
  CONSTRAINT `FK_permissions_users1`
    FOREIGN KEY (`allowing_user_id` )
    REFERENCES `ecm_users` (`ID` ));
    

-- -----------------------------------------------------
-- Table `erlang_crawler_managementDB`.`ecm_crawl_params`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `ecm_crawl_params` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `max_process_count` INT NOT NULL ,
  `buffer_size` INT NOT NULL ,
  `trigger_time` INT NULL ,
  PRIMARY KEY (`ID`) );


-- -----------------------------------------------------
-- Table `erlang_crawler_managementDB`.`ecm_policies`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `ecm_policies` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `init_url` VARCHAR(45) NOT NULL ,
  `width` INT NOT NULL ,
  `depth` INT NOT NULL ,
  `validity_time` INT NULL ,
  `created_by` INT NULL ,
  `crawl_params_id` INT NOT NULL ,  
  PRIMARY KEY (`ID`) , 
  CONSTRAINT `FK_policies_users`
    FOREIGN KEY (`created_by` )
    REFERENCES `ecm_users` (`ID` ),    
  CONSTRAINT `FK_policies_crawl_params`
    FOREIGN KEY (`crawl_params_id` )
    REFERENCES `ecm_crawl_params` (`ID` )
 );

-- -----------------------------------------------------
-- Table `erlang_crawler_managementDB`.`ecm_sessions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `ecm_sessions` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `started` DATE NOT NULL ,
  `started_by` INT NOT NULL ,
  `finished` DATE NULL DEFAULT NULL ,
  `finished_by` INT NULL DEFAULT NULL ,
  `policy_id` INT NOT NULL ,
  `domain_manager_node_id` INT NOT NULL ,
  `remote_manager_server_node_id` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,  
  CONSTRAINT `FK_sessions_users_finished`
    FOREIGN KEY (`finished_by` )
    REFERENCES `ecm_users` (`ID` ),
  CONSTRAINT `FK_sessions_users_started`
    FOREIGN KEY (`started_by` )
    REFERENCES `ecm_users` (`ID` ),    
  CONSTRAINT `FK_sessions_nodes_domain`
    FOREIGN KEY (`domain_manager_node_id` )
    REFERENCES `ecm_nodes` (`ID` ),    
  CONSTRAINT `FK_sessions_nodes_remote`
    FOREIGN KEY (`remote_manager_server_node_id` )
    REFERENCES `ecm_nodes` (`ID` ),    
  CONSTRAINT `FK_sessions_policies`
    FOREIGN KEY (`policy_id` )
    REFERENCES `ecm_policies` (`ID` )
    );

-- -----------------------------------------------------
-- Table `erlang_crawler_managementDB`.`ecm_sessions_nodes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `ecm_sessions_nodes` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `session_id` INT NOT NULL ,
  `node_id` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,  
  CONSTRAINT `FK_session_nodes_node`
    FOREIGN KEY (`node_id` )
    REFERENCES `ecm_nodes` (`ID` ),    
  CONSTRAINT `FK_session_nodes_session`
    FOREIGN KEY (`session_id` )
    REFERENCES `ecm_sessions` (`ID` )
    );