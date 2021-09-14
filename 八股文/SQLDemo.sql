CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    declare m INT;
    set m=N-1; 
  RETURN (
      # Write your MySQL query statement below.
      select ifnull((select distinct Salary from Employee as E order by Salary desc limit m,1),null)
  );
END

# Write your MySQL query statement below
select Score,dense_rank() over (order by Score desc) as 'Rank' from Scores;

# Write your MySQL query statement below
select distinct Num 'ConsecutiveNums'
from (
    select Num,
        (row_number() over (order by id asc)-
            row_number() over (partition by Num order by id asc)) as series_id
    from Logs
) tab
group by Num,series_id
having count(1)>=3;

# Write your MySQL query statement below
select Department,Employee,Salary
from(
    select d.Name as Department,e.Name as Employee,e.Salary as Salary,
    rank() over (partition by e.DepartmentId order by e.Salary desc) as r
    from Employee as e join Department as d on 
    e.DepartmentId=d.Id
) t
where r=1;

# Write your MySQL query statement below
select player_id,event_date,  
sum(games_played) over(partition by player_id order by event_date) as games_played_so_far
from Activity;

# Write your MySQL query statement below
select round(avg(a.event_date is not null),2) fraction
from(
    select player_id,min(event_date) as login
    from Activity
    group by player_id
)p left join Activity a
on p.player_id=a.player_id and datediff(a.event_date,p.login)=1;

# Write your MySQL query statement below
select a.Name as Name 
from(
    select b.ManagerId from Employee b 
    group by b.ManagerId
    having count(b.Id)>=5
)t join Employee a
on a.Id=t.ManagerId;

# Write your MySQL query statement below
select name from Candidate as c 
join(
    select CandidateId
    from Vote
    group by CandidateId
    order by count(*) desc
    limit 1
)as k
on k.CandidateId=c.id;

# Write your MySQL query statement below
select question_id as survey_log 
from survey_log
group by question_id
order by sum(if(action="answer",1,0))/sum(if(action="show",1,0)) desc
limit 1;

# Write your MySQL query statement below
select d.dept_name,count(s.student_id) as student_number
from department as d 
    left outer join student as s
on d.dept_id=s.dept_id
group by d.dept_id
order by student_number desc,dept_name asc;

# Write your MySQL query statement below
select round(sum(TIV_2016),2) as TIV_2016
from(
    select *,
    count(*) over(partition by TIV_2015) as c1,
    count(*) over(partition by LAT,LON) as c2
    from insurance
) t
where t.c1>1 and t.c2<2;

# Write your MySQL query statement below
select t.id as id,count(*) as num
from(
    select requester_id as id from request_accepted
    union all 
    select accepter_id as id from request_accepted
) t
group by id 
order by 2 desc
limit 1;

# Write your MySQL query statement below
select id,
    case when p_id is null then "Root"
        when id in(select p_id from tree) then "Inner"
        else "Leaf" end 
    as Type
from tree
order by id;

# Write your MySQL query statement below
select round(sqrt(min((pow(a.x-b.x,2)+pow(a.y-b.y,2)))),2) as shortest
from
point_2d a join point_2d b 
on 
(a.x<=b.x and a.y<b.y) or (a.x<=b.x and a.y>b.y) or (a.x<b.x and a.y=b.y);

# Write your MySQL query statement below
select a.follower follower,count(distinct b.follower) as num
from follow a join follow b
on a.follower=b.followee
group by b.followee
order by a.follower;

# Write your MySQL query statement below
select id,if(id%2=1,lead(student,1,student) over (order by id),lag(student,1) over (order by id)) student
from seat;

# Write your MySQL query statement below
select t1.customer_id 
from(
    select c.customer_id as customer_id ,count(distinct p.product_key) as num
    from Customer c join Product p 
    on c.product_key=p.product_key
    group by c.customer_id
) t1 join (
    select count(distinct product_key) as num
    from Product
)t2
on t1.num=t2.num;

# Write your MySQL query statement below
select product_id,year as first_year,quantity,price
from(
    select product_id,year,quantity,price,dense_rank() over(partition by product_id order by year asc)as rk 
    from sales
)as n
where rk=1;

# Write your MySQL query statement below
select project_id,employee_id
from(
    select p.project_id,p.employee_id,dense_rank() over (partition by p.project_id order by e.experience_years desc)as rk 
    from Project p left join Employee e
    on p.employee_id=e.employee_id
)t
where rk=1;

# Write your MySQL query statement below
select b.book_id,name
from Books b left join Orders o 
on b.book_id=o.book_id and dispatch_date>='2018-06-23'
where available_from<'2019-05-23'
group by b.book_id
having ifnull(sum(quantity),0)<10;

# Write your MySQL query statement below
select login_date,count(user_id) user_count
from(
    select user_id,min(activity_date) login_date from Traffic
    where activity='login'
    group by user_id
) t 
where datediff('2019-06-30',login_date)<=90
group by login_date;

# Write your MySQL query statement below
select student_id,course_id,grade
from(
    select student_id,course_id,grade,rank() over (partition by student_id order by grade desc,course_id) rk
    from Enrollments
)t
where rk=1
order by student_id;

# Write your MySQL query statement below
select business_id 
from (
    select *,avg(occurences) over (partition by event_type) as avg_o
    from Events
)as a
where occurences>avg_o
group by 1
having count(event_type)>1;

# Write your MySQL query statement below
select round(avg(daily_percent),2) as average_daily_percent
from(
    select count(distinct r.post_id)/count(distinct a.post_id)*100 as daily_percent 
    from Actions as a 
    left join 
    Removals as r 
    on a.post_id=r.post_id
    where 
    extra='spam'
    group by action_date
)as a;

# Write your MySQL query statement below
select distinct viewer_id as id 
from Views
group by view_date,viewer_id
having count(distinct article_id)>1
order by viewer_id;

# Write your MySQL query statement below
select user_id buyer_id,join_date,count(order_id) orders_in_2019
from Users u
left join orders o 
on u.user_id=o.buyer_id
and year(order_date)='2019'
group by user_id;

# Write your MySQL query statement below
select distinct p.product_id,ifnull(a.new_price,10) as price 
from Products p 
left join
(select product_id,new_price,rank() over (partition by product_id order by change_date desc) rk 
from Products
where change_date<='2019-08-16'
) a  
on p.product_id=a.product_id
and a.rk=1;

# Write your MySQL query statement below
select round(sum(order_date=customer_pref_delivery_date)/count(*)*100,2) as immediate_percentage
from Delivery 
where(customer_id,order_date) in (
    select customer_id,min(order_date)
    from Delivery
    group by customer_id
);

# Write your MySQL query statement below
select date_format(trans_date,'%Y-%m') as month,country,count(*) as trans_count,
    count(if(state='approved',1,null)) as approved_count,
    sum(amount) as trans_total_amount,
    sum(if(state='approved',amount,0)) as approved_total_amount
from Transactions
group by month,country;

# Write your MySQL query statement below
select person_name from 
(select person_name,sum(weight) over(order by turn asc) as sumweight from queue)as t 
where sumweight<=1000
order by sumweight desc
limit 1;

# Write your MySQL query statement below
select left(trans_date,7) as month,country,count(case when state='approved' then id end) as approved_count,
    ifnull(sum(case when state='approved' then amount end),0) as approved_amount,
    count(case when state='chargeback' then id end) as chargeback_count,
    ifnull(sum(case when state='chargeback' then amount end),0) as chargeback_amount
from(
    select id,country,'chargeback' as state,amount,c.trans_date
    from transactions as t 
    inner join chargebacks as c 
    on t.id=c.trans_id
    union
    select *
    from transactions
)as a 
group by 1,2
having count(case when state='approved' or state='chargeback' then id end)>0;

# Write your MySQL query statement below
select t.team_id,team_name,ifnull(sum(points),0) as num_points
from Teams as t 
left join (
    select host_team as team_id,
        case when host_goals>guest_goals then 3
             when host_goals=guest_goals then 1
             else 0 end
        as points
    from Matches
    union all
    select guest_team as team_id,
        case when host_goals<guest_goals then 3
             when host_goals=guest_goals then 1
             else 0 end 
        as points
    from Matches
)as a 
on t.team_id=a.team_id
group by 1
order by 3 desc,1;

# Write your MySQL query statement below
select distinct page_id as recommended_page
from Likes
where user_id in(
    select user1_id as user_id from Friendship where user2_id=1
    union all
    select user2_id as user_id from Friendship where user1_id=1
)and page_id not in (
    select page_id from Likes where user_id=1 
);

# Write your MySQL query statement below
select e1.employee_id
from Employees e1 
join Employees e2 on e1.manager_id=e2.employee_id
join Employees e3 on e2.manager_id=e3.employee_id
where e1.employee_id!=1 and e3.manager_id=1;

# Write your MySQL query statement below
select min(log_id) start_id,max(log_id) end_id
from (
    select distinct log_id,log_id-row_number() over (order by log_id asc) r
    from Logs
)t
group by r 
order by start_id;

# Write your MySQL query statement below
select gender,day,sum(score_points) over (partition by gender order by day) as total
from Scores;

# Write your MySQL query statement below
select visited_on,amount,average_amount
from (
    select visited_on,round(sum(amount) over (order by visited_on rows 6 preceding),2) as amount,
        round(avg(amount) over (order by visited_on rows 6 preceding),2) as average_amount,
        rank() over (order by visited_on) as rk 
    from(
        select visited_on,sum(amount) as amount
        from Customer
        group by 1
    )as a 
)as b 
where rk>=7;

# Write your MySQL query statement below
select name as results from 
(
    select mr.movie_id,mr.user_id,u.name,count(mr.rating) ct 
    from Movie_Rating mr
    inner join Movies m
    on m.movie_id=mr.movie_id
    inner join Users u
    on u.user_id=mr.user_id
    group by mr.user_id
    order by ct desc,u.name asc
    limit 1
)a 
union 
select title as results from 
(
    select m.title,avg(mr.rating) as rt 
    from Movie_Rating mr 
    inner join Movies m
    on m.movie_id=mr.movie_id
    inner join Users u 
    on u.user_id=mr.user_id
    where mr.created_at between '2020-02-01' and '2020-02-29'
    group by m.movie_id
    order by rt desc,m.title asc 
    limit 1
)b

# Write your MySQL query statement below
select activity
from(
    select a.name as activity,count(f.id) as countId, max(count(f.id)) over() as maxCount,min(count(f.id)) over()   as minCount 
    from Activities as a 
    left join Friends as f
    on a.name =f.activity
    group by 1
)as b 
where countId<maxCount and countId>minCount;

# Write your MySQL query statement below
select invoice_id,ddl.customer_name,price,ifnull(ddl.contacts_cnt,0) as contacts_cnt,ifnull(ddl.trusted_contacts_cnt,0) as trusted_contacts_cnt
from(
    select cm.customer_id,cm.customer_name,count(ca.contact_name) as contacts_cnt,
        sum(case when ca.contact_email in(select email from Customers) then 1 else 0 end ) as trusted_contacts_cnt
    from Contacts ca right join Customers cm 
    on ca.user_id=cm.customer_id
    group by cm.customer_id   
)as ddl 
right join Invoices i on ddl.customer_id=i.user_id
order by invoice_id;

# Write your MySQL query statement below
select stock_name,sum(case when operation='Buy' then -price else price end)as capital_gain_loss
from Stocks
group by stock_name;

# Write your MySQL query statement below
select customer_id,customer_name
from Orders
join Customers
using(customer_id)
group by customer_id
having sum(product_name='A')>=1 and sum(product_name='B')>=1 and sum(product_name='C')<1
order by customer_id asc;

# Write your MySQL query statement below
select q.id,q.year,ifnull(npv,0) npv 
from Queries q 
left join 
NPV n 
using(id,year);

# Write your MySQL query statement below
select e.left_operand,e.operator,e.right_operand,
case e.operator
    when '>' then if(v1.value>v2.value,'true','false')
    when '<' then if(v1.value<v2.value,'true','false')
    else  if(v1.value=v2.value,'true','false')
end value    
from Expressions e
left join Variables v1 on v1.name = e.left_operand 
left join Variables v2 on v2.name = e.right_operand;

# Write your MySQL query statement below
select sale_date,sum(if(fruit='apples',1,-1)*sold_num) diff
from Sales 
group by sale_date
order by sale_date;

# Write your MySQL query statement below
select distinct a.*
from Accounts a 
join Logins L1 using(id)
join Logins L2 on L1.id=L2.id and datediff(L2.login_date,L1.login_date) between 0 and 4 
group by a.id,a.name,L1.login_date 
having count(distinct L2.login_date)=5;

# Write your MySQL query statement below
select p1.id as p1,p2.id as p2,abs(p1.x_value-p2.x_value)*abs(p1.y_value-p2.y_value) as area
from Points p1 join Points p2 
on p1.x_value!=p2.x_value
and p1.y_value!=p2.y_value
and p1.id<p2.id 
order by area desc,p1.id,p2.id;

# Write your MySQL query statement below
select 
    s.company_id, 
    s.employee_id, 
    s.employee_name, 
    round(s.salary*(1-companytaxrate.taxrate)) as salary
from salaries s join
(select 
    company_id, 
    case 
        when max(salary)<1000 then 0
        when max(salary) between 1000 and 10000 then 0.24
        when max(salary)>10000 then 0.49
    end as taxrate
from salaries
group by company_id) as companytaxrate on s.company_id=companytaxrate.company_id;


# Write your MySQL query statement below
# Write your MySQL query statement below
with a as (
    select caller_id caller, duration from Calls
    union all
    select callee_id caller, duration from Calls
)
select c.name country from a left join Person p on a.caller=p.id
left join Country c on left(p.phone_number, 3)=c.country_code
group by c.name
having avg(a.duration) > (select avg(duration) from a);


# Write your MySQL query statement below
select name as customer_name,customer_id,order_id,order_date 
from
(
select name,customer_id,order_id,order_date, row_number() over(partition by customer_id order by order_date desc) as rn 
from customers
join orders
using(customer_id)
) as newtable
where rn=1 or rn =2 or rn =3
order by customer_name asc,customer_id asc,order_date desc;


# Write your MySQL query statement below
select product_name,product_id,order_id,order_date
from
(select product_name,product_id,order_id,order_date,
dense_rank() over(partition by product_id order by order_date DESC) as rk 
from orders join products
using(product_id)
) as newtable
where rk<2
order by product_name ASC,product_id ASC,order_id ASC;


# Write your MySQL query statement below
# Write your MySQL query statement below
select 
    u.user_id,
    u.user_name,
    credit+ifnull(`amts`,0) credit,
    if((credit+ifnull(`amts`,0))<0,'Yes','No') credit_limit_breached 
from Users u 
    left join (
        select user_id,sum(amt) amts from (
            (select paid_by user_id,-sum(amount) amt from transactions group by paid_by) 
            union
            (select paid_to user_id,sum(amount) amt from transactions group by paid_to)
        ) t group by user_id) t
on u.user_id = t.user_id;


# Write your MySQL query statement below
SELECT customer_id, T.product_id, product_name 
FROM(
    SELECT customer_id, product_id,
    RANK() OVER(PARTITION BY customer_id ORDER BY COUNT(*) DESC ) AS RK
    FROM Orders o
    GROUP BY customer_id, product_id
) T
LEFT JOIN Products p on p.product_id = t.product_id 
WHERE RK=1;


# Write your MySQL query statement below
# Write your MySQL query statement below

-- declare @top int = SELECT MAX(CUSTOMER_ID) FROM CUSTOMER

WITH t1 as (
    SELECT 1 as a
    UNION ALL SELECT 2
    UNION ALL SELECT 3
    UNION ALL SELECT 4
    UNION ALL SELECT 5
    UNION ALL SELECT 6
    UNION ALL SELECT 7
    UNION ALL SELECT 8
    UNION ALL SELECT 9
),
t2 as (
    SELECT 0 as b
    UNION ALL SELECT 1
    UNION ALL SELECT 2
    UNION ALL SELECT 3
    UNION ALL SELECT 4
    UNION ALL SELECT 5
    UNION ALL SELECT 6
    UNION ALL SELECT 7
    UNION ALL SELECT 8
    UNION ALL SELECT 9
),
t3 as (
    SELECT 10*a + 1*b as NUMBERS FROM t1, t2
    UNION ALL SELECT 100
    UNION ALL SELECT a FROM t1
)

SELECT NUMBERS AS 'ids' FROM t3
WHERE NUMBERS < (SELECT MAX(customer_id) FROM Customers)
AND NUMBERS NOT IN (SELECT customer_id FROM Customers)
ORDER BY NUMBERS ASC;


# Write your MySQL query statement below
select p1 as person1,p2 as person2, count(*) as call_count,sum(duration) as total_duration
from
(
select if(from_id>to_id,to_id,from_id) as p1, if(from_id>to_id,from_id,to_id) as p2,duration from calls
) as newtable
group by p1,p2;


# Write your MySQL query statement below
select user_id, max(diff) as biggest_window from
(select 
    user_id,
    datediff(    lead(visit_date,1,'2021-1-1')   over(partition by user_id    order by visit_date ASC),   visit_date) as diff
from uservisits  ) as newtable
group by user_id
order by user_id ;


# Write your MySQL query statement below
# Write your MySQL query statement below
select sum(B.apple_count + ifnull(C.apple_count, 0))   apple_count,
       sum(B.orange_count + IFNULL(C.orange_count, 0)) orange_count
from Boxes B
         left join Chests C on B.chest_id = C.chest_id;



# Write your MySQL query statement below
SELECT DISTINCT a.account_id
FROM LogInfo a
INNER JOIN LogInfo b
ON a.account_id = b.account_id AND a.ip_address <> b.ip_address AND ((a.login between b.login and b.logout) OR (a.logout between b.login and b.logout));


# Write your MySQL query statement below
select player_id,player_name, 
sum(
    (case when wimbledon = player_id then 1 else 0 end)+
    (case when Fr_open = player_id then 1 else 0 end)+
    (case when US_open = player_id then 1 else 0 end)+
    (case when Au_open = player_id then 1 else 0 end)
 ) as grand_slams_count
from championships cross join players
group by player_id
having grand_slams_count>0;


# Write your MySQL query statement below
# by xiaoweixiang
with 
a as(
    select contest_id,gold_medal as user_id from contests
    union all
    select contest_id,silver_medal as user_id from contests
    union all 
    select contest_id,bronze_medal as user_id from contests
    order by contest_id
),b as (
    select a0.user_id from a a0
    inner join a a1 on a1.contest_id=a0.contest_id+1 and a1.user_id=a0.user_id
    inner join a a2 on a2.contest_id=a0.contest_id+2 and a2.user_id=a0.user_id
),c as (
    select gold_medal as user_id
    from contests group by gold_medal
    having count(gold_medal)>=3
), d as (
    select user_id
    from b
    union select user_id from c
)
select name,mail from users where user_id in (select * from d);


# Write your MySQL query statement below
SELECT
    transaction_id
FROM transactions
WHERE (DATE(day), amount) in (
    SELECT DATE(day), MAX(amount)
    FROM transactions GROUP BY DATE(day)
)
ORDER BY 1;



# Write your MySQL query statement below
SELECT team_name, SUM(matches_played) AS matches_played, SUM(points) AS points
	, SUM(goal_for) AS goal_for, SUM(goal_against) AS goal_against
	, SUM(goal_for) - SUM(goal_against) AS goal_diff
FROM (
	(SELECT team_name, COUNT(*) AS matches_played, SUM(CASE 
			WHEN home_team_goals > away_team_goals THEN 3
			WHEN home_team_goals < away_team_goals THEN 0
			ELSE 1
		END) AS points
		, SUM(home_team_goals) AS goal_for, SUM(away_team_goals) AS goal_against
	FROM Matches m
		JOIN Teams t ON m.home_team_id = t.team_id
	GROUP BY home_team_id)
	UNION
	(SELECT team_name, COUNT(*) AS matches_played, SUM(CASE 
			WHEN home_team_goals > away_team_goals THEN 0
			WHEN home_team_goals < away_team_goals THEN 3
			ELSE 1
		END) AS points
		, SUM(away_team_goals) AS goal_for, SUM(home_team_goals) AS goal_against
	FROM Matches m
		JOIN Teams t ON m.away_team_id = t.team_id
	GROUP BY away_team_id)
) t
GROUP BY team_name
ORDER BY points DESC, goal_diff DESC, team_name;


# Write your MySQL query statement below
SELECT DISTINCT account_id
FROM
(SELECT c.account_id, c.date, (c.date - row_number() over (Partition by c.account_id ORDER BY c.date)) AS label
FROM
(SELECT account_id, EXTRACT(YEAR_MONTH FROM day) AS date, SUM(amount) AS total
FROM Transactions 
WHERE type = 'Creditor'
GROUP BY account_id, EXTRACT(YEAR_MONTH FROM day)) c
INNER JOIN Accounts d
ON c.account_id = d.account_id AND c.total > d.max_income) a
GROUP BY account_id, label
HAVING COUNT(date) >= 2;


# Write your MySQL query statement below
select
    order_id
from
    OrdersDetails
group by
    order_id
having
    max(quantity) > 
all((
    select
        sum(quantity) / count(distinct product_id)
    from 
        OrdersDetails
    group by
        order_id
));





















