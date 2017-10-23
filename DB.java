1.  IceFireFightDao：

对应的分库分表的sql:
CREATE TABLE `IceFireFight171027{0}` (
  `UserId` INT(10) UNSIGNED NOT NULL,
  `FrUserId` INT(10) UNSIGNED NOT NULL,
   PRIMARY KEY (`UserId`, `FrUserId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

2. 分库分表的执行工具: tools ——> partsql_executor

3. 
