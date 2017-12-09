/*select mg.`sortCode` = '' from menugroupinfo mg where mg.`MenuGroupName` = '卡池管理';
SELECT * FROM menuinfo m WHERE m.`menuName` = '工单处理';
select * from roletomenu rm where rm.`menuID` = '800d783b-4e8c-4652-bb64-8474e63c24b4';
select m.`MenuInfoID` from menuinfo m where m.`menuGroupID` = 'af1bafa5-d7ac-44a9-b617-bd8709b091fe';
SELECT * FROM menuinfo m WHERE m.`MenuInfoID` = '2c6ee2f2-32fb-4780-92d3-e69a2fdaf116';*/


-- 客服监控中心
UPDATE menugroupinfo mg SET mg.`MenuGroupName` = '运营监控' WHERE mg.`MenuGroupName` = '客服监控中心';
UPDATE menuinfo m SET m.`menuName` = '实时在线' WHERE m.`menuName` = '实时在线设备';
UPDATE menuinfo m SET m.`menuName` = '历史在线' WHERE m.`menuName` = '历史在线设备';
UPDATE menuinfo m SET m.`menuName` = '异常监控' WHERE m.`menuName` = '异常中心';
DELETE FROM roletomenu  WHERE `menuID` = '7ea4682e-ad62-4192-817e-986b6fceb10e';
DELETE FROM roletomenu  WHERE `menuID` = '2c6ee2f2-32fb-4780-92d3-e69a2fdaf116';
DELETE FROM roletomenu  WHERE `menuID` = '598844bb-33f0-4aa2-9abb-ace040096aa5';

-- 客服查询中心
DELETE FROM roletomenu WHERE `menuID` = '666970c8-cb2e-46a4-8ef0-1d4cd0ff05a6';
DELETE FROM roletomenu WHERE `menuID` = '8955b466-e9c4-4bb7-a5f0-d43e3843f1f1';
UPDATE menuinfo m SET m.menuGroupID = 'e990f3c5-434b-49be-9a10-b81b132b76d1' WHERE m.`menuName` = '基站位置查询';
UPDATE menuinfo m SET m.menuGroupID = 'e990f3c5-434b-49be-9a10-b81b132b76d1' WHERE m.`menuName` = 'WIFI密码查询';
DELETE FROM roletomenu  WHERE `menuID` = '02dfed94-2482-4a13-8819-0dea0247fd6f';
DELETE FROM roletomenu  WHERE `menuID` = '9ab52aa7-63ad-4762-bf5a-1938bdc1aa16';
DELETE FROM roletomenu  WHERE `menuID` = 'ef688695-a562-4d16-b1ed-d168968c529e';
DELETE FROM roletomenu  WHERE `menuID` = 'acc7febb-b52d-4d57-9c80-67addae01b52';
DELETE FROM roletomenu  WHERE `menuID` = 'e9916c7c-73d4-4b43-aeed-ad3df9a88198';
DELETE FROM roletomenu  WHERE `menuID` = '638e5eee-e48c-4bc3-b08b-2320a0497fe9';

-- 远程服务
UPDATE menugroupinfo mg SET mg.`MenuGroupName` = '远程操作' WHERE mg.`MenuGroupName` = '远程服务';
UPDATE menuinfo m SET m.`menuName` = '远程操作' WHERE m.`menuName` = '远程服务操作';
UPDATE menuinfo m SET m.`menuName` = '远程升级' WHERE m.`menuName` = '远程升级';
DELETE FROM roletomenu WHERE `menuID` = '3d7c432f-f816-4c10-9f16-a43b84d90aa5';
UPDATE menuinfo m SET m.`menuName` = '提取终端日志' WHERE m.`menuName` = '终端日志历史';
UPDATE menuinfo m SET m.`menuName` = '升级文件上传' WHERE m.`menuName` = '升级文件上传';
UPDATE menuinfo m SET m.`menuName` = '终端升级配置' WHERE m.`menuName` = '设备升级配置';

-- 订单管理
DELETE FROM roletomenu WHERE `menuID` = '086b837a-65e2-4f9d-9ac2-156af235c632';
DELETE FROM roletomenu WHERE `menuID` = '03fecb4c-fc23-4bd1-ac32-c62494bd4660';

-- 订单工作流
DELETE FROM roletomenu WHERE `menuID` IN(SELECT m.`MenuInfoID` FROM menuinfo m WHERE m.`menuGroupID` = 'af1bafa5-d7ac-44a9-b617-bd8709b091fe');

-- 设备出入库管理
UPDATE menugroupinfo mg SET mg.`MenuGroupName` = '终端管理' WHERE mg.`MenuGroupName` = '设备出入库管理';
DELETE FROM roletomenu WHERE `menuID` = '84f2c677-02a8-4450-9e57-32e3786abdbc';
DELETE FROM roletomenu WHERE `menuID` = '4118c896-59ac-41f6-8a56-efa18bededd6';
DELETE FROM roletomenu WHERE `menuID` = '75c07eaf-9e10-46ac-9480-924d94894a93';
UPDATE menuinfo m SET m.`menuName` = '全部终端' WHERE m.`menuName` = '全部设备';
UPDATE menuinfo m SET m.`menuName` = '添加终端' WHERE m.`menuName` = '添加设备';
UPDATE menuinfo m SET m.`menuName` = '批量添加终端' WHERE m.`menuName` = '批量添加设备';
DELETE FROM roletomenu WHERE `menuID` = '37270b00-0b70-48ae-90e5-4517db277512';
DELETE FROM roletomenu WHERE `menuID` = '4975b586-d0aa-4054-ba49-a846eb9f8fda';
DELETE FROM roletomenu WHERE `menuID` = '75bac20a-4999-4c57-a828-af5cf8130fb0';
UPDATE menuinfo m SET m.`menuName` = '出厂测试记录' WHERE m.`menuName` = '工厂测试记录';

-- 流量套餐管理
DELETE FROM roletomenu WHERE `menuID` = '115f1918-1683-4a93-b4e6-5048192e1623';
DELETE FROM roletomenu WHERE `menuID` IN('26bf9d30-845b-4ec8-8b3c-415a41039bb1', '800d783b-4e8c-4652-bb64-8474e63c24b4');

-- 漫游SIM管理
DELETE FROM roletomenu WHERE `menuID` IN(SELECT m.`MenuInfoID` FROM menuinfo m WHERE m.`menuGroupID` = '3c3dfeba-8182-4ba2-8246-c9a9578eee16');

-- VPN管理
DELETE FROM roletomenu WHERE `menuID` IN(SELECT m.`MenuInfoID` FROM menuinfo m WHERE m.`menuGroupID` = 'd448ce4f-bae1-42c0-92c8-90c681411fe1');

-- 本地SIM卡管理
UPDATE menugroupinfo mg SET mg.`MenuGroupName` = '流量卡管理' WHERE mg.`MenuGroupName` = '本地SIM卡管理';
UPDATE menuinfo m SET m.`menuName` = '全部流量卡' WHERE m.`menuName` = '全部本地SIM卡';
UPDATE menuinfo m SET m.`menuName` = '添加流量卡' WHERE m.`menuName` = '添加本地SIM卡';
UPDATE menuinfo m SET m.`menuName` = '已删除流量卡' WHERE m.`menuName` = '已删除本地SIM卡';
UPDATE menuinfo m SET m.`menuName` = '批量导入流量卡' WHERE m.`menuName` = '批量导入SIM卡';
DELETE FROM roletomenu  WHERE `menuID` = '4d842d3b-37e4-42c5-8553-261180975d27';
DELETE FROM roletomenu  WHERE `menuID` = 'd5bdbca1-4c07-42b7-92b0-44a9c7d6a559';
DELETE FROM roletomenu  WHERE `menuID` = '2ac6ace3-4fc5-4bcd-ae3f-dd721572abe1';

-- 虚拟SIM卡管理
DELETE FROM roletomenu  WHERE `menuID` IN(SELECT m.`MenuInfoID` FROM menuinfo m WHERE m.`menuGroupID` = '8cec6b98-fef5-450f-8f17-d982c3442624');

-- 活动管理
DELETE FROM roletomenu  WHERE `menuID` IN(SELECT m.`MenuInfoID` FROM menuinfo m WHERE m.`menuGroupID` = '4a043ac4-a92b-4587-a911-5dbfbf426ce6');

-- 20.对账系统
DELETE FROM roletomenu  WHERE `menuID` = 'baad0d79-1f99-434f-8d36-7e2666464c6f';

-- 预警系统
DELETE FROM roletomenu WHERE `menuID` IN(SELECT m.`MenuInfoID` FROM menuinfo m WHERE m.`menuGroupID` = '038feb2e-ee67-4909-b204-6235fe49e631');

-- 内部人员管理
UPDATE menugroupinfo mg SET mg.`MenuGroupName` = '管理员管理' WHERE mg.`MenuGroupName` = '内部人员管理';


-- 调整顺序
UPDATE menugroupinfo mg SET mg.`sortCode` = '300'  WHERE mg.`MenuGroupName` = '运营监控';
UPDATE menugroupinfo mg SET mg.`sortCode` = '290'  WHERE mg.`MenuGroupName` = '远程操作';
UPDATE menugroupinfo mg SET mg.`sortCode` = '280'  WHERE mg.`MenuGroupName` = '订单管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '270'  WHERE mg.`MenuGroupName` = '流量卡管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '260'  WHERE mg.`MenuGroupName` = 'SIM服务器维护';
UPDATE menugroupinfo mg SET mg.`sortCode` = '250'  WHERE mg.`MenuGroupName` = '流量套餐管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '240'  WHERE mg.`MenuGroupName` = '终端管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '230'  WHERE mg.`MenuGroupName` = '客户管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '220'  WHERE mg.`MenuGroupName` = '渠道商管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '210'  WHERE mg.`MenuGroupName` = '对账系统';
UPDATE menugroupinfo mg SET mg.`sortCode` = '200'  WHERE mg.`MenuGroupName` = '渠道商权限管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '190'  WHERE mg.`MenuGroupName` = '经营分析报表';
UPDATE menugroupinfo mg SET mg.`sortCode` = '180'  WHERE mg.`MenuGroupName` = 'SIM卡使用率分析';
UPDATE menugroupinfo mg SET mg.`sortCode` = '170'  WHERE mg.`MenuGroupName` = '统计报表';
UPDATE menugroupinfo mg SET mg.`sortCode` = '160'  WHERE mg.`MenuGroupName` = '国家管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '150'  WHERE mg.`MenuGroupName` = '数据字典管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '140'  WHERE mg.`MenuGroupName` = '权限管理';
UPDATE menugroupinfo mg SET mg.`sortCode` = '130'  WHERE mg.`MenuGroupName` = '管理员管理';


