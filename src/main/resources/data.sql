insert into account(account_id, email, password, nickname, block, signup_time, signup_ip)
values (1, 'kurien92@gmail.com', '$2a$10$MXm3otNpjn9QThVSSmpnTOsydqcFg9V0z80luDpEoa3UEYRH230rW', '김유렌', 0, '2021-04-19 14:02:12.000000', '127.0.0.1'),
       (2, 'kurien@naver.com', '$2a$10$6OuJoQcK3idZM3MUxFv5uecVsENHMkc35HHLFn2cgMOELE/PTDWG2', '김유렌2', 1, '2021-04-19 15:03:12.638273', '192.0.0.2');

insert into authority(authority_id, authority)
values (1, 'USER'),
       (2, 'ADMIN');

insert into account_authority(account_authority_id, account_id, authority_id, create_time)
values (1, 1, 2, '2021-04-22 11:01:35.000231'),
       (2, 2, 1, '2021-04-22 11:02:48.213823');

insert into content(id, name, title, content, view, create_time)
values (1, 'privacyPolicy', '개인정보처리방침', '내용', 1, '2021-04-19 14:02:12.000001');

insert into kreblog.category (category_id, category_parent_id, category_depth, category_order, category_key, category_name)
values  (6, 17, 1, 2, 'css', 'CSS'),
        (7, 17, 1, 3, 'javascript', 'JavaScript'),
        (10, null, 0, 0, 'project', 'Project'),
        (13, 10, 1, 1, 'blog', 'Blog'),
        (15, 10, 1, 9001, 'issue', 'Issue'),
        (17, null, 0, 3, 'develop', 'Develop'),
        (18, 17, 1, 5, 'java', 'Java'),
        (19, 10, 1, 2, 'kreFullImage', 'kreFullImage'),
        (21, 17, 1, 3001, 'mariadb', 'MariaDB'),
        (23, null, 0, 9001, 'systemIssue', 'System Issue');