-- 1create search_invoice_view --
create or replace view search_invoice_view 
as 
select distinct i.id, i.invoice_number, i.vendor_id, v.vendor_name, i.ban_id, b.account_number, 
i.invoice_date, i.invoice_due_date, i.invoice_total_due, b.currency_id, i.invoice_status_id, s.invoice_status_name, 
i.invoice_receive_date, if((0 < (select count(d.id)  from dispute d where d.id = i.id)),1,0) AS flag_dispute, 
if((i.invoice_total_due > (select cast(c.value as decimal(18,2))  from sys_config c where (c.parameter='invoice_review_threshold'))),1,0) AS flag_large,i.flag_workspace 
from (((invoice i join vendor v) join ban b) join invoice_status s) where ((i.vendor_id = v.id) and (i.ban_id = b.id) and (i.invoice_status_id = s.id) and (i.rec_active_flag = _latin1'Y')) order by i.id;

-- UPDATE invoice SET invoice_date = '2010-04-01'; --
-- UPDATE invoice SET invoice_due_date = '2010-05-01' ;--
-- UPDATE invoice SET invoice_receive_date = '2010-04-15'; --

-- 2create search_payment_view --

create or replace view search_payment_view 
as 
SELECT distinct p.id AS id,i.id AS iid,i.invoice_number AS invoice_number,
i.invoice_due_date AS invoice_due_date,i.vendor_id AS vendor_id,
v.vendor_name AS vendor_name,i.ban_id AS ban_id,
b.account_number AS account_number,s.payment_status_name AS payment_status_name,
p.payment_amount AS payment_amount,p.payment_date AS payment_date,
p.payment_reference_code AS payment_reference_code,p.paid_date AS paid_date,
p.payment_transaction_number AS payment_transaction_number,
p.created_by AS created_by,
p.payment_status_id AS payment_status_id,
p.flag_workspace 
from (((((payment p join invoice i on (p.invoice_id = i.id)) join vendor v on (i.vendor_id = v.id)) join ban b on (i.ban_id = b.id)) join payment_status s on (p.payment_status_id = s.id)) join user u on (u.id = p.created_by))
where (p.rec_active_flag = _latin1'Y') 
order by p.id;

--  UPDATE payment SET rec_active_flag = _latin1'Y' WHERE rec_active_flag = _latin1'N';--
--  UPDATE payment SET payment_date = '2010-04-01' WHERE payment_date = '0000-00-00';--
--  UPDATE payment SET paid_date = '2010-04-01' WHERE paid_date = '0000-00-00';--


-- 3create search_dispute_view by lizhiguo --
create or replace view search_dispute_view 
as
select distinct d.id,d.dispute_number,d.ticket_number,d.dispute_status_id,s.dispute_status_name,d.dispute_reason_id,r.dispute_reason_name,v.vendor_name,i.vendor_id,i.ban_id,b.account_number,d.claim_number,d.created_timestamp,u.user_name,i.assigned_analyst_id,d.flag_shortpaid,d.flag_recurring
from ((((((dispute d join invoice i) join vendor v)join ban b) left join dispute_status s on (d.dispute_status_id = s.id)) left join dispute_reason r on(d.dispute_reason_id = r.id)) join user u) 
where ((d.invoice_id = i.id) and (i.ban_id = b.id) and (i.vendor_id = v.id) and (u.id = i.assigned_analyst_id) and (d.rec_active_flag = _latin1'Y') and u.system_user_flag='N') order by d.id;

-- 4create dispute_detail_view by lizhiguo --
create or replace view dispute_detail_view
as
select distinct d.id,i.invoice_number,v.vendor_name,b.account_number,d.dispute_amount,d.created_timestamp,d.ticket_number,d.dispute_balance,d.claim_number,d.dispute_number
from (((dispute d join invoice i) join vendor v) join ban b) 
where ((d.invoice_id = i.id) and (i.ban_id = b.id) and (i.vendor_id = v.id)) order by d.id;

-- 5create reconcile_detail_view by lizhiguo --
create or replace view reconcile_detail_view
as
select distinct r.id,dispute_id,r.rec_active_flag,r.reconcile_amount,c.id as credit_number,c.notes as c_notes,r.notes r_notes, reconcile_status_name,r.created_timestamp,u.user_name
from user u join reconcile r left join credit c on r.credit_id = c.id join reconcile_status rs
where r.created_by = u.id  and rs.id = reconcile_status_id and u.system_user_flag='N' order by r.id;

-- 6create search_credit_view by xinyu.liu --
create or replace view search_credit_view 
as
select distinct c.id, c.credit_balance,c.credit_amount,cs.credit_status_name,v.vendor_name,c.ticket_number,b.account_number,
c.ban_id,c.vendor_id,i.invoice_number, c.dispute_number,c.credit_status_id,c.credit_date,c.reference_number
from ((((credit c join invoice i) join vendor v) join ban b) join credit_status cs)
where ((c.invoice_id = i.id) and (c.ban_id = b.id) and (c.vendor_id = v.id) and (c.credit_status_id = cs.id) and (c.rec_active_flag = _latin1'Y')) order by c.id;

-- 7create PendingDispute by pengfei.wang--
create or replace view pending_dispute_view 
as
select distinct d.id,d.ticket_number, d.dispute_balance,d.dispute_amount, d.dispute_number,s.dispute_status_name,r.dispute_reason_name,v.vendor_name,b.account_number,
i.invoice_number,i.ban_id,i.vendor_id,i.invoice_due_date,d.created_by,d.claim_number,d.dispute_reason_id,d.dispute_status_id,d.created_timestamp,u.user_name
from ((((((dispute d join invoice i) join vendor v) join ban b) join dispute_status s) join dispute_reason r ) join user u) 
where ((d.dispute_balance > 0)and(d.invoice_id = i.id) and (i.ban_id = b.id) and (i.vendor_id = v.id) and (d.dispute_status_id = s.id) and (d.dispute_reason_id = r.id) and (u.id = d.created_by)) order by d.id;

-- 8create pending_credit_view By pengfei.wang--
create or replace view pending_credit_view 
as
select distinct c.id, c.credit_balance,c.credit_amount,cs.credit_status_name,v.vendor_name,c.ticket_number,b.account_number,c.claim_number,
c.ban_id,c.vendor_id,i.invoice_number, c.dispute_number,c.credit_status_id,c.credit_date,c.reference_number 
from ((((credit c join invoice i) join vendor v) join ban b) join credit_status cs)
where ((c.credit_balance > 0)and(c.invoice_id = i.id) and (c.ban_id = b.id) and (c.vendor_id = v.id) and (c.credit_status_id = cs.id)) order by c.id;

-- 9create search_reconcile_view By wei.su--
create or replace view search_reconcile_view 
as
select distinct (r.id), r.reconcile_date, r.created_by, r.reconcile_amount,d.ticket_number,c.ban_id,c.vendor_id,b.account_number,v.vendor_name,i.invoice_number,d.claim_number,d.dispute_number,u.user_name,c.id as credit_id,d.id as dispute_id
from ((((((reconcile r join credit c)join user u) join ban b) join vendor v) join dispute d) join invoice i)
where ((r.credit_id = c.id) and (r.dispute_id = d.id) and (c.ban_id = b.id) and (c.vendor_id = v.id) and (r.created_by = u.id) and (d.invoice_id = i.id) and (r.rec_active_flag not like 'N')) order by r.id;

-- 10create search_ticketDetail_view By xinyu.Liu--
create or replace view search_ticketdetail_view 
as
select t.id,th.id th_id,t.priority_id,p.priority_name,t.severity_id,s.severity_name,t.ticket_status_id,ts.ticket_status_name,t.title,t.content,t.modified_by modified_name,
t.created_timestamp,t.modified_timestamp,th.created_timestamp th_created_timestamp,t.rec_active_flag,th.comment,t.created_by,u.user_name created_name,th.created_by th_created_by, us.user_name th_created_name,priority_id_from pid_from,priority_id_to pid_to,severity_id_from sid_from,severity_id_to sid_to,ticket_status_id_from tsid_from,ticket_status_id_to tsid_to,s2.severity_name severity_id_from_name,p2.priority_name priority_id_from_name,ts2.ticket_status_name ticket_status_id_from_name
from (((((((((ticket t join priority p) join severity s) join ticket_status ts) join user u) join user us) join ticket_history th) join priority p2) join severity s2) join ticket_status ts2)
where ((t.priority_id = p.id) and (t.severity_id = s.id) and (t.ticket_status_id = ts.id) and (t.id = th.ticket_id) and (t.created_by = u.id) and (th.created_by = us.id) and (th.priority_id_from = p2.id) and (th.severity_id_from = s2.id ) and (th.ticket_status_id_from = ts2.id)) order by t.created_timestamp;

--  11create search_ticket_view By xinyu.Liu--
create or replace view search_ticket_view 
as
select t.id,t.priority_id,p.priority_name,t.severity_id,s.severity_name,t.ticket_status_id,ts.ticket_status_name,t.title,t.content, 
t.created_timestamp,t.modified_timestamp,t.rec_active_flag,t.created_by,t.modified_by,u1.user_name created_name,u2.user_name modified_name
from (((((ticket t join priority p) join severity s) join ticket_status ts) join user u1) join user u2)
where ((t.priority_id = p.id) and (t.severity_id = s.id) and (t.ticket_status_id = ts.id) and (t.created_by = u1.id) and (t.modified_by = u2.id)) order by t.id;

-- 12creditDetailList By Chao.liu
create or replace view search_creditDetail_view 
as
select d.id did,
       d.dispute_number disputeNumber,       
       d.dispute_balance disputeBalance,       
       d.claim_number claimNnmber,       
       d.ticket_number ticketNumber,       
       i.id iid 
from dispute d,invoice i 
where (d.invoice_id=i.id) and (d.rec_active_flag=_latin1'Y') and (d.dispute_balance>0) order by d.id;
	  
-- 13creditDetailReconcile By Chao.liu --
-- creditDetailReconcile By Chao.liu
create or replace view search_creditDetailReconcile_view 
as
select r.id rid,
       r.dispute_id did,       
       r.credit_id cid,       
       r.reconcile_amount ramout,       
       r.created_timestamp rdate,       
       d.notes dnotes,    
       d.dispute_number dnumber,   
       u.user_name uname
from reconcile r,dispute d,user u,credit c
where (r.dispute_id=d.id)       
      and (r.credit_id = c.id)
      and (r.created_by = u.id)      
      and (r.rec_active_flag != 'N')      
union 
select r.id rid,
       r.dispute_id did,       
       r.credit_id cid,       
       r.reconcile_amount ramout,       
       r.created_timestamp rdate,        
       NULL dnotes,       
       NULL dnumber,
       u.user_name uname
from reconcile r,user u,credit c
where (r.dispute_id is NULL)
      and (r.credit_id = c.id)
      and (r.created_by = u.id)      
      and (r.rec_active_flag != 'N');
      
      
-- 14ap_header_view and ap_detail_view By Qiao.Yang

create or replace view ap_header_view 
as 
SELECT distinct 
i.invoice_number as invoice_number,p.id as payment_id, p.payment_amount as payment_amount,i.invoice_date as invoice_date,b.ap_supplier_number as vendor_num
,b.ap_supplier_name as vendor_name,b.ap_supplier_site as vendor_site_code,c.currency_name as invoice_currency_code,pt.payment_term_code as terms_name,b.account_number as ban,pm.payment_method_code as payment_method_lookup_code,pg.payment_group_code as pay_group_lookup_code,i.invoice_receive_date as invoice_receive_date, ps.id as payment_status_id, b.line_of_business as description
from (((((((payment p join invoice i on (p.invoice_id = i.id)) join ban b on (i.ban_id = b.id)) join currency c on (b.currency_id = c.id)) join payment_status ps on (p.payment_status_id = ps.id)) left join payment_term pt on (b.payment_term_id = pt.id)) left join payment_method pm on (b.payment_method_id = pm.id)) left join payment_group pg on (b.payment_group_id = pg.id))
where (p.rec_active_flag = 'Y') and b.ban_status_id = 1 and b.master_ban_flag = 'y' order by p.id asc;


create or replace view ap_detail_view 
as 
SELECT distinct i.invoice_number as invoice_number, pd.id as payment_detail_id, pd.line_type_lookup_code as line_type_lookup_code,pd.amount as amount,tc.tax_code as tax_code,ac.account_code_name as scoa, ac.account_code_description as description
from ((((payment_detail pd join payment p on (pd.payment_id = p.id)) join invoice i on (p.invoice_id = i.id))join account_code ac on (pd.account_code_id = ac.id)) left join tax_code tc on (pd.tax_code_id = tc.id)) where (pd.rec_active_flag = _latin1'Y') order by pd.id;


-- 15create by Jian.Dong
create or replace view search_payment_scoa_amount_view 
as 
(SELECT '14' flag, p.account_code_id, a.account_code_name, a.account_code_description,  p.invoice_id,    null ban_id, p.payment_amount,      p.description,    null date1,       null created_by, p.attachment_point_id FROM proposal p,account_code a WHERE p.account_code_id=a.id AND p.payment_amount is not NULL AND p.payment_amount!=0 AND p.rec_active_flag='Y' AND p.proposal_flag='1') 
union all 
(SELECT '5'  flag, r.account_code_id, a.account_code_name, a.account_code_description,  d.invoice_id,    i.ban_id,    r.reconcile_amount,    r.notes,          r.reconcile_date, r.created_by,    r.attachment_point_id FROM reconcile r,dispute d,invoice i,user u, account_code a WHERE r.dispute_id=d.id  AND d.invoice_id=i.id AND r.created_by=u.id AND r.account_code_id=a.id AND r.reconcile_status_id=7            AND r.rec_active_flag='Y' AND r.payment_id is NULL ) 
union all 
(SELECT '6'  flag, r.account_code_id, a.account_code_name, a.account_code_description,  i.id,            i.ban_id,    r.reconcile_amount*-1, r.notes,          r.reconcile_date, r.created_by,    r.attachment_point_id FROM reconcile r,invoice i,          user u, account_code a WHERE r.credit_invoice_id=i.id                 AND r.created_by=u.id AND r.account_code_id=a.id AND r.reconcile_status_id in (1,3,4,6) AND r.rec_active_flag='Y' AND r.payment_id is NULL )
union all 
(SELECT '23' flag, p.account_code_id, a.account_code_name, a.account_code_description,  p.invoice_id,    null ban_id, p.dispute_amount,      p.description,    null date1,       null created_by, p.attachment_point_id FROM proposal p, dispute d,account_code a  WHERE p.dispute_id=d.id AND p.account_code_id=a.id  AND d.flag_shortpaid='N'  AND p.invoice_item_id is not NULL  AND p.dispute_amount is not NULL  AND p.dispute_amount!=0  AND p.rec_active_flag='Y'  AND p.proposal_flag='1') 
union all 
(SELECT '23' flag, p.account_code_id, a.account_code_name, a.account_code_description,  p.invoice_id,    null ban_id, p.dispute_amount*-1,   p.description,    null date1,       null created_by, p.attachment_point_id FROM proposal p, dispute d,account_code a  WHERE p.dispute_id=d.id AND p.account_code_id=a.id  AND d.flag_shortpaid='Y'  AND p.invoice_item_id is not NULL      AND p.dispute_amount is not NULL  AND p.dispute_amount!=0  AND p.rec_active_flag='Y'  AND p.proposal_flag='1');


-- 16 PROCEDURE (upodate invoice_item) by xinyu.Liu
DROP PROCEDURE IF EXISTS SP_POPULATE_PROPOSAL_SUM;
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

END;

-- 17 create by Chao.Liu  
DROP VIEW IF EXISTS `search_ticket_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` 
   SQL SECURITY DEFINER VIEW `search_ticket_view` AS 
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
         order by `t`.`id`;




-- Update invoice Trigger By Jian.Dong
DROP TRIGGER IF EXISTS transaction_history_by_invoice_update;
CREATE TRIGGER transaction_history_by_invoice_update AFTER UPDATE ON invoice FOR EACH ROW 
BEGIN 
   IF (OLD.invoice_status_id!=NEW.invoice_status_id) THEN 
       INSERT INTO transaction_history 
       (invoice_id, payment_id, dispute_id, invoice_status_id, internal_invoice_status_id, payment_status_id, dispute_status_id, workflow_action_id, workflow_user_id, notes, attachment_point_id, created_timestamp, created_by, rec_active_flag, payment_amount, dispute_amount, misc_adjustment_amount) 
       VALUES 
       (NEW.id, NULL, NULL, NEW.invoice_status_id, NEW.internal_invoice_status_id, NULL, NULL, NEW.workflow_action_id, NEW.workflow_user_id, NEW.notes, NEW.attachment_point_id, NEW.modified_timestamp, NEW.modified_by, 'Y', NEW.payment_amount, NEW.dispute_amount, NEW.misc_adjustment_amount);
   END IF;
END;

-- Insert invoice Trigger By Jian.Dong
DROP TRIGGER IF EXISTS transaction_history_by_invoice_insert;
CREATE TRIGGER transaction_history_by_invoice_insert AFTER INSERT ON invoice FOR EACH ROW 
BEGIN 
   INSERT INTO transaction_history 
   (invoice_id, payment_id, dispute_id, invoice_status_id, internal_invoice_status_id, payment_status_id, dispute_status_id, workflow_action_id, workflow_user_id, notes, attachment_point_id, created_timestamp, created_by, rec_active_flag, payment_amount, dispute_amount, misc_adjustment_amount) 
   VALUES 
   (NEW.id, NULL, NULL, NEW.invoice_status_id, NEW.internal_invoice_status_id, NULL, NULL, NEW.workflow_action_id, NEW.workflow_user_id, NEW.notes, NEW.attachment_point_id, NEW.created_timestamp, NEW.created_by, 'Y', NEW.payment_amount, NEW.dispute_amount, NEW.misc_adjustment_amount);
END;

-- Update proposal Trigger By Jian.Dong
DROP TRIGGER IF EXISTS proposal_history_by_proposal_update;
CREATE TRIGGER proposal_history_by_proposal_update AFTER UPDATE ON proposal FOR EACH ROW 
BEGIN 
   IF (OLD.account_code_id!=NEW.account_code_id 
      OR OLD.payment_amount!=NEW.payment_amount
      OR OLD.dispute_amount!=NEW.dispute_amount      
      OR OLD.credit_amount!=NEW.credit_amount
      OR OLD.balance_amount!=NEW.balance_amount
      OR OLD.dispute_reason_id!=NEW.dispute_reason_id) THEN 
       INSERT INTO proposal_history 
       (proposal_id, old_account_code_id, old_payment_amount, old_dispute_amount, old_credit_amount, old_balance_amount, old_dispute_reason_id,old_originator_id, new_account_code_id, new_payment_amount, new_dispute_amount, new_credit_amount, new_balance_amount, new_dispute_reason_id,new_originator_id,notes, created_timestamp, created_by, rec_active_flag) 
       VALUES 
       (NEW.id, OLD.account_code_id, OLD.payment_amount, OLD.dispute_amount, OLD.credit_amount, OLD.balance_amount, OLD.dispute_reason_id,OLD.originator_id, NEW.account_code_id, NEW.payment_amount, NEW.dispute_amount, NEW.credit_amount, NEW.balance_amount, NEW.dispute_reason_id, NEW.originator_id, NEW.description, NEW.modified_timestamp , NEW.modified_by, 'Y');
   END IF;
END;

-- Insert proposal Trigger By Jian.Dong
DROP TRIGGER IF EXISTS proposal_history_by_proposal_insert;
CREATE TRIGGER proposal_history_by_proposal_insert AFTER INSERT ON proposal FOR EACH ROW 
BEGIN 
   IF (NEW.account_code_id IS NOT NULL
      OR NEW.payment_amount  IS NOT NULL
      OR NEW.dispute_amount  IS NOT NULL
      OR NEW.credit_amount  IS NOT NULL
      OR NEW.balance_amount  IS NOT NULL
      OR NEW.dispute_reason_id  IS NOT NULL) THEN 
       INSERT INTO proposal_history 
       (proposal_id, old_account_code_id, old_payment_amount, old_dispute_amount, old_credit_amount, old_balance_amount, old_dispute_reason_id,old_originator_id, new_account_code_id, new_payment_amount, new_dispute_amount, new_credit_amount, new_balance_amount, new_dispute_reason_id,new_originator_id,notes, created_timestamp, created_by, rec_active_flag) 
       VALUES 
       (NEW.id, null, null, null, null, null, null,null, NEW.account_code_id, NEW.payment_amount, NEW.dispute_amount, NEW.credit_amount, NEW.balance_amount, NEW.dispute_reason_id,NEW.originator_id,NEW.description, NEW.created_timestamp , NEW.created_by, 'Y');
   END IF;
END;
