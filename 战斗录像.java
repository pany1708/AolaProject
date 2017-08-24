// BOSS录像
jba.setRecord(true);
jba.setDao(new BossChallengeVideoDao(u.getUserId(), 67));

use rpg;
INSERT INTO `NewTopsDefine` (`Name`, `TopNum`, `ChangeDate`) VALUES ('BossVideo_67', 60, '2017-08-16 16:11:10');
INSERT INTO `ActivityTopsDefine` (`TopName`, `TopNum`, `ChangeDate`) VALUES
('BossVideo_67', 50, '2017-08-16 16:11:10');
use rpgact;
INSERT INTO `BossVideoDefine` (`BossId`, `Description`) VALUES ('67', '始祖巨龙挑战');

--------------------------------------------------------------------------------------------------------------------

官方录像:
产品发的邮件：
录像ID4567
推荐阵容：传奇冰雪女皇（id：3191；推图鉴；推荐战斗力：24000）+ 雪无冰姬（id：3190；推图鉴；推荐战斗力：20000）+传奇神龙（id：3178；推图鉴；推荐战斗力：24000）

1. 登录linux跑那个Video.java里的2个注释:更改Id为4567.

2. 使用fileZilla把那2个生成的脚本拷贝到本机.

3.在MustWinLineUp添加新纪录RaceId，注意service字段

4.
REPLACE INTO `MustWinLineUp` (`ActId`, `PetIndex`, `RaceId`, `petName`, `Service`, `MaxCE`, `ActName`) VALUES (47, 0, 3191, '传奇冰雪女皇', -1, 24000, '始祖巨龙挑战');
REPLACE INTO `MustWinLineUp` (`ActId`, `PetIndex`, `RaceId`, `petName`, `Service`, `MaxCE`, `ActName`) VALUES (47, 1, 3190, '雪无冰姬', -1, 20000, '始祖巨龙挑战');
REPLACE INTO `MustWinLineUp` (`ActId`, `PetIndex`, `RaceId`, `petName`, `Service`, `MaxCE`, `ActName`) VALUES (47, 2, 3178, '传奇神龙', 0, 24000, '始祖巨龙挑战');

把ActId发给前端,这个是官方录像的Id

5. 修改签下来的2个VideoContentForAct.sql和VideoDetailForAct.java
INSERT语句修改第一个Id为ActId, REPLACE
