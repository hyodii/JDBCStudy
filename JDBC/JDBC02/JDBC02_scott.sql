SELECT USER
FROM DUAL;
--==>> SCOTT


TRUNCATE TABLE TBL_MEMBER;
--==>> Table TBL_MEMBER이(가) 잘렸습니다.


CREATE SEQUENCE MEMBERSEQ
NOCACHE;
--==>> Sequence MEMBERSEQ이(가) 생성되었습니다.

drop SEQUENCE MEMBERSEQ;
--==>> Sequence MEMBERSEQ이(가) 삭제되었습니다.

--○ 데이터 입력 쿼리문 구성
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL,'김진희', '010-1111-1111')
;
--==>> 1 행 이(가) 삽입되었습니다.


--○ 인원수 확인 쿼리문 구성
SELECT COUNT(*) AS COUNT
FROM TBL_MEMBER;
--==>> 1
--> 한 줄 구성
SELECT COUNT(*) AS COUNT FROM TBL_MEMBER
;

--○ 전체 리스트 조회 쿼리문 구성
SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--==>> 1	김진희	010-1111-1111
--> 한 줄 구성
SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID
;

-- 시퀀스 삭제하고 커밋 !!!!CHECK~!!! 
COMMIT;



























