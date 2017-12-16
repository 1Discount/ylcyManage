-- 运营监控中的“异常监控“去掉
DELETE FROM roletomenu  WHERE `menuID` = 'a724bad0-9ef8-4672-a217-c6c2dc92bdec';

-- SIM服务器维护—》卡池服务器维护
UPDATE menugroupinfo mg SET mg.`MenuGroupName` = '卡池服务器维护' WHERE mg.`MenuGroupName` = 'SIM服务器维护';

ALTER TABLE `ordersinfo`   
  CHANGE `serverCode` `serverCode` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci DEFAULT ''  NOT NULL  COMMENT '渠道商服务编号';