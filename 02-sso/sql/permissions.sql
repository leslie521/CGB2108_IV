#业务:基于用户id获取用户可访问的资源的资源标识(用户权限)
#方案1:单表多次查询
select * from tb_users where id = 1 ;
select role_id from tb_user_roles where user_id = 1;
select menu_id from tb_role_menus where role_id in (1);
select permission from tb_menus where id in (1,2,3);

#方案2:多表嵌套查询
select permission from tb_menus where id in (
    select menu_id from tb_role_menus where role_id in (
        select role_id from tb_user_roles where user_id = 1));

#方案三:多表关联查询
select distinct m.permission from tb_user_roles ur
        join tb_role_menus rm
            on ur.role_id = rm.role_id
        join tb_menus m
            on rm.menu_id = m.id
where ur.user_id = 1