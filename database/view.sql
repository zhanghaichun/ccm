delimiter $$

-- 3create search_dispute_view by lizhiguo --
create or replace view search_dispute_view 
as
select distinct d.id,d.dispute_number,d.dispute_amount,d.dispute_balance,d.ticket_number,d.dispute_status_id,s.dispute_status_name,d.dispute_reason_id,r.dispute_reason_name,v.vendor_name,i.vendor_id,i.ban_id,b.account_number,d.claim_number,d.created_timestamp,concat(first_name,' ',last_name) as analyst_user,i.assigned_analyst_id,d.flag_shortpaid,d.flag_recurring
from ((((((dispute d join invoice i) join vendor v)join ban b) left join dispute_status s on (d.dispute_status_id = s.id)) left join dispute_reason r on(d.dispute_reason_id = r.id)) join user u) 
where ((d.invoice_id = i.id) and (i.ban_id = b.id) and (i.vendor_id = v.id) and (u.id = i.assigned_analyst_id) and (d.rec_active_flag = _latin1'Y') and u.system_user_flag='N') order by d.id$$

-- 14ap_header_view and ap_detail_view By Qiao.Yang
create or replace view ap_header_view 
as 
SELECT distinct 
i.invoice_number as invoice_number,p.id as payment_id, p.payment_amount as payment_amount,i.invoice_date as invoice_date,b.ap_supplier_number as vendor_num
,b.ap_supplier_name as vendor_name,b.ap_supplier_site as vendor_site_code,c.currency_name as invoice_currency_code,pt.payment_term_code as terms_name,b.account_number as ban,pm.payment_method_code as payment_method_lookup_code,pg.payment_group_code as pay_group_lookup_code,i.invoice_receive_date as invoice_receive_date, ps.id as payment_status_id, b.line_of_business as description
from (((((((payment p join invoice i on (p.invoice_id = i.id)) join ban b on (i.ban_id = b.id)) left join currency c on (b.currency_id = c.id)) join payment_status ps on (p.payment_status_id = ps.id)) left join payment_term pt on (b.payment_term_id = pt.id)) left join payment_method pm on (b.payment_method_id = pm.id)) left join payment_group pg on (b.payment_group_id = pg.id))
where (p.rec_active_flag = 'Y') and b.ban_status_id = 1 and b.master_ban_flag = 'Y' order by p.id asc$$


create or replace view ap_detail_view 
as 
SELECT distinct p.id as payment_id, i.invoice_number as invoice_number, pd.id as payment_detail_id, pd.line_type_lookup_code as line_type_lookup_code,pd.amount as amount,tc.tax_code as tax_code,ac.account_code_name as scoa, ac.account_code_description as description
from ((((payment_detail pd join payment p on (pd.payment_id = p.id)) join invoice i on (p.invoice_id = i.id))join account_code ac on (pd.account_code_id = ac.id)) left join tax_code tc on (pd.tax_code_id = tc.id)) where (pd.rec_active_flag = 'Y') order by pd.id asc$$


create or replace view ap_remittance_view 
as 
SELECT distinct 
i.invoice_number as feed_invoice_number, r.invoice_number as re_invoice_number, p.id as payment_id, p.payment_amount as feed_payment_amount, r.payment_amount as re_payment_amount, i.invoice_date as invoice_date, b.ap_supplier_number as feed_vendor_number
, r.ap_supplier_number as re_vendor_number, b.id as ban_id, b.account_number as feed_ban, ps.id as payment_status_id, r.payment_reference_code as payment_reference_code, r.id as remittance_id 
from ((((payment p join invoice i on (p.invoice_id = i.id)) join ban b on (i.ban_id = b.id)) join payment_status ps on (p.payment_status_id = ps.id)) join remittance r on (r.invoice_number = i.invoice_number))
where (p.rec_active_flag = 'Y') and (p.payment_status_id = 22) and (r.remittance_status = '1') and (b.ban_status_id = 1) and (b.master_ban_flag = 'Y') order by p.id asc$$
  
 
create or replace view ap_invoice_exception_view 
as 
SELECT distinct 
i.invoice_number as feed_invoice_number, r.invoice_number as re_invoice_number, p.id as payment_id, p.payment_amount as feed_payment_amount, r.payment_amount as re_payment_amount, i.invoice_date as invoice_date, b.ap_supplier_number as feed_vendor_number
, r.ap_supplier_number as re_vendor_number, b.id as ban_id, b.account_number as feed_ban, ps.id as payment_status_id, r.payment_reference_code as payment_reference_code, r.id as remittance_id 
from ((((payment p join invoice i on (p.invoice_id = i.id)) join ban b on (i.ban_id = b.id)) join payment_status ps on (p.payment_status_id = ps.id)) right join remittance r on (r.invoice_number = i.invoice_number))
where (r.remittance_status = '1') order by r.id asc$$
  
  
create or replace view ap_status_exception_view 
as 
SELECT distinct 
i.invoice_number as feed_invoice_number, r.invoice_number as re_invoice_number, p.id as payment_id, p.payment_amount as feed_payment_amount, r.payment_amount as re_payment_amount, i.invoice_date as invoice_date, b.ap_supplier_number as feed_vendor_number
, r.ap_supplier_number as re_vendor_number, b.id as ban_id, b.account_number as feed_ban, ps.id as payment_status_id, r.payment_reference_code as payment_reference_code, r.id as remittance_id 
from ((((payment p join invoice i on (p.invoice_id = i.id)) join ban b on (i.ban_id = b.id)) join payment_status ps on (p.payment_status_id = ps.id)) join remittance r on (r.invoice_number = i.invoice_number))
where (p.rec_active_flag = 'Y') and (p.payment_status_id <> 22) and (r.remittance_status = '1') and (b.ban_status_id = 1) and (b.master_ban_flag = 'Y') order by p.id asc$$


create or replace view ap_supplier_exception_view 
as 
SELECT distinct 
i.invoice_number as feed_invoice_number, r.invoice_number as re_invoice_number, p.id as payment_id, p.payment_amount as feed_payment_amount, r.payment_amount as re_payment_amount, i.invoice_date as invoice_date, b.ap_supplier_number as feed_vendor_number
, r.ap_supplier_number as re_vendor_number, b.id as ban_id, b.account_number as feed_ban, ps.id as payment_status_id, r.payment_reference_code as payment_reference_code, r.id as remittance_id 
from ((((payment p join invoice i on (p.invoice_id = i.id)) join ban b on (i.ban_id = b.id)) join payment_status ps on (p.payment_status_id = ps.id)) join remittance r on (r.invoice_number = i.invoice_number))
where (p.rec_active_flag = 'Y') and (r.ap_supplier_number <> b.ap_supplier_number) and (p.payment_status_id in (22,25)) and (r.remittance_status in ('1','2')) and (b.ban_status_id = 1) and (b.master_ban_flag = 'Y') order by p.id asc$$

-- 17 create by Chao.Liu  
DROP VIEW IF EXISTS `search_ticket_view`$$
CREATE VIEW `search_ticket_view` AS 
   select `t`.`id` AS `id`,
          `t`.`priority_id` AS `priority_id`,
          `p`.`priority_name` AS `priority_name`,
          `t`.`severity_id` AS `severity_id`,
          `s`.`severity_name` AS `severity_name`,
          `t`.`ticket_status_id` AS `ticket_status_id`,
          `ts`.`ticket_status_name` AS `ticket_status_name`,
          `t`.`title` AS `title`,
          `t`.`content` AS `content`,
          `t`.`created_timestamp` AS `created_timestamp`,
          `t`.`modified_timestamp` AS `modified_timestamp`,
          `t`.`rec_active_flag` AS `rec_active_flag`,`t`.
          `created_by` AS `created_by`,
          `t`.`modified_by` AS `modified_by`,
          concat(if(u1.first_name is null,'',u1.first_name),' ',if(u1.last_name is null,'',u1.last_name)) AS `created_name`,
          concat(if(u2.first_name is null,'',u2.first_name),' ',if(u2.last_name is null,'',u2.last_name)) AS `modified_name` 
   from (((((`ticket` `t` join `priority` `p`) join `severity` `s`) join `ticket_status` `ts`) join `user` `u1`) join `user` `u2`) 
   where ((`t`.`priority_id` = `p`.`id`) 
         and (`t`.`severity_id` = `s`.`id`) 
         and (`t`.`ticket_status_id` = `ts`.`id`) 
         and (`t`.`created_by` = `u1`.`id`) 
         and (`t`.`modified_by` = `u2`.`id`)) 
         order by `t`.`id`$$


-- 16 PROCEDURE (upodate invoice_item) by xinyu.Liu
DROP PROCEDURE IF EXISTS SP_POPULATE_PROPOSAL_SUM$$
CREATE PROCEDURE SP_POPULATE_PROPOSAL_SUM(IN IN_INVOICE_ID INT)
BEGIN

  DECLARE V_STOP BOOLEAN DEFAULT FALSE;
  DECLARE V_INVOICE_STRUCTURE_ID INT;
  DECLARE V_ITEM_TYPE_ID INT;
  DECLARE V_PARENT_ITEM_ID BIGINT;
  DECLARE V_ITEM_ID BIGINT;
  DECLARE V_PAYMENT_SUM DOUBLE;
  DECLARE V_DISPUTE_SUM DOUBLE;  
  DECLARE V_CREDIT_SUM DOUBLE;
  DECLARE V_PAYMENT_AMOUNT DOUBLE;
  DECLARE V_DISPUTE_AMOUNT DOUBLE;  
  DECLARE V_CREDIT_AMOUNT DOUBLE;
  DECLARE V_COUNT INT;
  
  DECLARE cur1 CURSOR FOR
    SELECT DISTINCT parent_item_type_id FROM item_structure
      WHERE invoice_structure_id = V_INVOICE_STRUCTURE_ID
        AND proposal_flag = 1 ORDER BY 1 DESC;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET V_STOP = TRUE;

  SELECT invoice_structure_id INTO V_INVOICE_STRUCTURE_ID
    FROM invoice
    WHERE id = IN_INVOICE_ID;
    
  SET V_STOP = FALSE;

  OPEN cur1;
  LAB1: WHILE NOT V_STOP DO

    FETCH cur1 INTO V_ITEM_TYPE_ID;
      IF V_STOP THEN
        LEAVE LAB1;
      END IF;

    SELECT COUNT(0) INTO V_COUNT
      FROM proposal
      WHERE item_type_id = V_ITEM_TYPE_ID
        AND invoice_id = IN_INVOICE_ID;
    IF V_COUNT = 1 THEN
      SELECT id INTO V_PARENT_ITEM_ID 
        FROM proposal
        WHERE item_type_id = V_ITEM_TYPE_ID
          AND invoice_id = IN_INVOICE_ID;
    END IF;

    LAB2: WHILE (V_PARENT_ITEM_ID IS NOT NULL) AND (V_PARENT_ITEM_ID <> 0) DO
    
      SET V_PAYMENT_SUM = 0;
      SET V_DISPUTE_SUM = 0;      
      SET V_CREDIT_SUM = 0;
      SET V_PAYMENT_AMOUNT = 0;
      SET V_DISPUTE_AMOUNT = 0;
      SET V_CREDIT_AMOUNT = 0;      

      SELECT COUNT(0) INTO V_COUNT
        FROM proposal WHERE parent_proposal_id = V_PARENT_ITEM_ID AND rec_active_flag='Y';
      IF V_COUNT > 0 THEN
        SELECT SUM(payment_amount), SUM(dispute_amount), SUM(credit_amount) INTO V_PAYMENT_SUM, V_DISPUTE_SUM, V_CREDIT_SUM
          FROM proposal WHERE parent_proposal_id = V_PARENT_ITEM_ID AND rec_active_flag='Y';
      END IF;
    
      UPDATE proposal SET payment_amount = ifnull(V_PAYMENT_SUM,0),
        dispute_amount = ifnull(V_DISPUTE_SUM,0),
        credit_amount = ifnull(V_CREDIT_SUM,0) WHERE id = V_PARENT_ITEM_ID;      
    
      SET V_ITEM_ID = V_PARENT_ITEM_ID;
      SELECT parent_proposal_id INTO V_PARENT_ITEM_ID FROM proposal
        WHERE id = V_ITEM_ID;

    END WHILE LAB2;  

  END WHILE LAB1;
  CLOSE cur1;

END$$


-- Update invoice Trigger By Jian.Dong

DROP TRIGGER IF EXISTS transaction_history_by_invoice_update$$
CREATE TRIGGER transaction_history_by_invoice_update AFTER UPDATE ON invoice FOR EACH ROW 
BEGIN 
   IF (OLD.invoice_status_id!=NEW.invoice_status_id) THEN 
       INSERT INTO transaction_history 
       (invoice_id, payment_id, dispute_id, invoice_status_id, internal_invoice_status_id, payment_status_id, dispute_status_id, workflow_action_id, workflow_user_id, notes, attachment_point_id, created_timestamp, created_by, rec_active_flag, payment_amount, dispute_amount, misc_adjustment_amount) 
       VALUES 
       (NEW.id, NULL, NULL, NEW.invoice_status_id, NEW.internal_invoice_status_id, NULL, NULL, NEW.workflow_action_id, NEW.workflow_user_id, NEW.notes, NEW.attachment_point_id, NEW.modified_timestamp, NEW.modified_by, 'Y', NEW.payment_amount, NEW.dispute_amount, NEW.misc_adjustment_amount);
   END IF;
END$$

-- Update proposal Trigger By Jian.Dong

DROP TRIGGER IF EXISTS proposal_history_by_proposal_update$$
CREATE TRIGGER proposal_history_by_proposal_update AFTER UPDATE ON proposal FOR EACH ROW 
BEGIN 
   IF (NEW.modified_by!=0 AND (OLD.account_code_id!=NEW.account_code_id 
      OR OLD.payment_amount!=NEW.payment_amount 
      OR OLD.dispute_amount!=NEW.dispute_amount 
      OR OLD.credit_amount!=NEW.credit_amount 
      OR OLD.balance_amount!=NEW.balance_amount 
      OR OLD.dispute_reason_id!=NEW.dispute_reason_id 
      OR OLD.originator_id!=NEW.originator_id)) THEN 
       INSERT INTO proposal_history 
       (proposal_id, old_account_code_id, old_payment_amount, old_dispute_amount, old_credit_amount, old_balance_amount, old_dispute_reason_id,old_originator_id, new_account_code_id, new_payment_amount, new_dispute_amount, new_credit_amount, new_balance_amount, new_dispute_reason_id,new_originator_id,notes, created_timestamp, created_by, rec_active_flag) 
       VALUES 
       (NEW.id, OLD.account_code_id, OLD.payment_amount, OLD.dispute_amount, OLD.credit_amount, OLD.balance_amount, OLD.dispute_reason_id,OLD.originator_id, NEW.account_code_id, NEW.payment_amount, NEW.dispute_amount, NEW.credit_amount, NEW.balance_amount, NEW.dispute_reason_id, NEW.originator_id, NEW.notes, NEW.modified_timestamp , NEW.modified_by, 'Y');
   END IF;
END$$



set global log_bin_trust_function_creators = 1$$
DROP FUNCTION IF EXISTS toJSON$$
CREATE FUNCTION toJSON(v1 boolean, v2 varchar(65535), v3 varchar(65535)) RETURNS varchar(65535)
begin
	return if(v1, v2, replace(replace(replace(v3,'\\','\\\\'),'\"','\\\"'),'''','\\''') );
end$$




set global log_bin_trust_function_creators = 1$$
DROP FUNCTION IF EXISTS blank_space$$
CREATE FUNCTION blank_space() RETURNS varchar(1)
begin
	return ' ';
end$$


DROP TRIGGER IF EXISTS invoice_original_flag_by_original_insert$$
CREATE TRIGGER invoice_original_flag_by_original_insert AFTER INSERT ON original FOR EACH ROW 
BEGIN 
      update invoice i set i.original_flag=if((select count(1) from original o where (o.invoice_id=i.id or o.bar_code=i.bar_code) and o.rec_active_flag='Y')>0,'Y','N') where i.id=new.id or i.bar_code=new.bar_code;
END$$

DROP TRIGGER IF EXISTS invoice_original_flag_by_original_delete$$
CREATE TRIGGER invoice_original_flag_by_original_delete AFTER DELETE ON original FOR EACH ROW 
BEGIN 
      update invoice i set i.original_flag=if((select count(1) from original o where (o.invoice_id=i.id or o.bar_code=i.bar_code) and o.rec_active_flag='Y')>0,'Y','N') where i.id=old.id or i.bar_code=old.bar_code;
END$$

DROP TRIGGER IF EXISTS invoice_original_flag_by_original_update$$
CREATE TRIGGER invoice_original_flag_by_original_update AFTER UPDATE ON original FOR EACH ROW 
BEGIN 
   IF (NEW.rec_active_flag!=OLD.rec_active_flag)  
   THEN 
      update invoice i set i.original_flag=if((select count(1) from original o where (o.invoice_id=i.id or o.bar_code=i.bar_code) and o.rec_active_flag='Y')>0,'Y','N') where i.id=old.id or i.bar_code=old.bar_code;
   END IF;
END$$


delimiter ;
