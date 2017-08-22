// BOSS录像
jba.setRecord(true);
jba.setDao(new BossChallengeVideoDao(u.getUserId(), 67));

use rpg;
INSERT INTO `NewTopsDefine` (`Name`, `TopNum`, `ChangeDate`) VALUES ('BossVideo_67', 60, '2017-08-16 16:11:10');
INSERT INTO `ActivityTopsDefine` (`TopName`, `TopNum`, `ChangeDate`) VALUES
('BossVideo_67', 50, '2017-08-16 16:11:10');
use rpgact;
INSERT INTO `BossVideoDefine` (`BossId`, `Description`) VALUES ('67', '始祖巨龙挑战');
