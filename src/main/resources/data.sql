INSERT INTO authority (authority_name) VALUES ('ROLE_USER');
INSERT INTO authority (authority_name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (authority_name) VALUES ('ROLE_OFFICE');

INSERT INTO user (id,email,name,pw,pnum,uimg,seller,report,marketing) VALUES (1,'1000playch@naver.com','admin','$2a$10$D99zvd9eSCquwrkA5ss7L.GiYshRHu2x.MEgvTbk80SpnGahGNKse','010-1234-1234',null,true,0,true);
INSERT INTO user (id,email,name,pw,pnum,uimg,seller,report,marketing) VALUES (2,'6candoit@naver.com','user','$2a$10$D99zvd9eSCquwrkA5ss7L.GiYshRHu2x.MEgvTbk80SpnGahGNKse','010-1234-1234',null,true,0,true);
INSERT INTO user (id,email,name,pw,pnum,uimg,seller,report,marketing,office_add,market_add) VALUES (3,'6candoit@gmail.com','office','$2a$10$D99zvd9eSCquwrkA5ss7L.GiYshRHu2x.MEgvTbk80SpnGahGNKse','010-5678-5678',null,true,0,true,"dk.jpg","aa.png");

INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (1,'의류/잡화','2022-11-23','market.png','한강 플리마켓','환영합니다',3,false,'영등포','2022-11-25','2022-11-21', 0);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (2,'먹거리','2022-12-01','market.png','구롱구롱 플리마켓','환영합니다',3,false,'구로','2022-12-05','2022-11-28', 10);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (3,'인테리어','2022-12-21','market.png','도리미 플리마켓','환영합니다',3,false,'신도림','2022-12-25','2022-12-17', 2);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (4,'문구/팬시','2022-12-23','market.png','잠실만 플리마켓','환영합니다',3,false,'잠실','2022-12-28','2022-12-20', 4);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (5,'수공예품','2022-11-13','market.png','강자만 남는 플리마켓','환영합니다',3,false,'강남','2022-11-16','2022-11-10', 8);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (6,'의류/잡화','2022-11-23','market.png','거꾸로해도 플리마켓','환영합니다',3,false,'역삼','2022-11-25','2022-11-20', 17);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (7,'의류/잡화','2022-11-20','market.png','홍시대박 플리마켓','환영합니다',3,false,'홍대','2022-11-23','2022-11-17', 1);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (8,'인테리어','2022-11-19','market.png','어청담 플리마켓','환영합니다',3,false,'청담','2022-11-27','2022-11-16', 0);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (9,'수공예품','2022-12-02','market.png','어디연남 플리마켓','환영합니다',3,false,'연남','2022-12-05','2022-11-30', 0);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (10,'먹거리','2022-12-04','market.png','마음이 공덕공덕 플리마켓','환영합니다',3,false,'공덕','2022-12-08','2022-12-01', 0);


INSERT INTO user_authority (id,authority_name) VALUES (1,'ROLE_ADMIN');
INSERT INTO user_authority (id,authority_name) VALUES (1,'ROLE_USER');
INSERT INTO user_authority (id,authority_name) VALUES (1,'ROLE_OFFICE');
INSERT INTO user_authority (id,authority_name) VALUES (2,'ROLE_USER');
INSERT INTO user_authority (id,authority_name) VALUES (3,'ROLE_OFFICE');

INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (1,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (2,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (3,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (4,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (5,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (6,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (7,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (8,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (9,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (10,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (11,1,'홍길동','질문있습니다1!','질문....질문.....','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (12,1,'홍길동','질문있습니다1!','질문....질문.....','status');

