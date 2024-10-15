-- 创建优惠券表
CREATE TABLE coupon (
                        cid int AUTO_INCREMENT PRIMARY KEY, -- 优惠券的唯一标识
                        code VARCHAR(50), -- 优惠券代码，必须唯一
                        type VARCHAR(20), -- 优惠券类型，如折扣券、满减券等
                        value VARCHAR(255) , -- 优惠券的折扣金额
                        start_date TIMESTAMP , -- 优惠券生效的起始日期
                        end_date TIMESTAMP , -- 优惠券失效的结束日期
                        quantity INT DEFAULT 0 -- 优惠券的总数量，默认值为 0

);
SELECT start_date, end_date FROM coupon WHERE cid = 6;

INSERT INTO coupon (code, type, value, start_date, end_date, quantity)
VALUES ('xinqiu', '折扣券', '85折',CONCAT(CURDATE(), ' 00:00:00'),CONCAT(CURDATE(), ' 23:59:59'),100);
SELECT @@global.time_zone, @@session.time_zone;

INSERT INTO coupon (code, type, value, start_date, end_date, quantity)
VALUES ('one', '折扣券', '1折', CONCAT(CURDATE(), ' 00:00:00'), DATE_ADD(CONCAT(CURDATE(), ' 00:00:00'), INTERVAL 1 DAY), 100);

DROP TABLE IF EXISTS coupon;
select * from coupon;
delete from coupon where cid = 1;
-- 用户优惠券关系表
CREATE TABLE user_coupon (
                             id INT AUTO_INCREMENT PRIMARY KEY, -- 唯一标识
                             uid INT, -- 用户ID
                             cid INT, -- 优惠券ID
                             get_date TIMESTAMP, -- 优惠券领取日期
                             status INT DEFAULT 1 -- 优惠券的使用状态，默认可用 0 不可用 1 可用 2 已使用 3 已过期 4 已失效
);
DROP TABLE IF EXISTS user_coupon;
select * from user_coupon;
