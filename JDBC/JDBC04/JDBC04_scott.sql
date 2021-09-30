SELECT USER
FROM DUAL;
--==>> SCOTT

TRUNCATE TABLE TBL_SCORE;
--==>> Table TBL_SCORE이(가) 잘렸습니다.

DROP SEQUENCE SCORESEQ;
--==>>Sequence SCORESEQ이(가) 삭제되었습니다.

--○ 시퀀스 생성
CREATE SEQUENCE SCORESEQ
NOCACHE;
--==>> Sequence SCORESEQ이(가) 생성되었습니다.


--○ 쿼리문 준비

-- 1. 데이터 입력 쿼리문
INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT)
VALUES(SCORESEQ.NEXTVAL, '이다영', 90, 80, 70);
--> 한 줄 구성
INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES(SCORESEQ.NEXTVAL, '이다영', 90, 80, 70)
;
--==>> 1 행 이(가) 삽입되었습니다.

COMMIT;
--==>> 커밋 완료.

-- 2. 리스트 출력 쿼리문 구성(총점, 평균, 석차 포함)
SELECT SID, NAME, KOR, ENG,MAT
    , (KOR+ENG+MAT) AS TOT
    , (KOR+ENG+MAT)/3 AS AVG
    , RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK 
FROM TBL_SCORE
ORDER BY SID ASC;
--> 한 줄 구성
SELECT SID, NAME, KOR, ENG,MAT, (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG, RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK FROM TBL_SCORE ORDER BY SID ASC
;
--==>> 1	이다영	90	80	70	240	80	1


-- 3. 인원 수 조회 쿼리문 구성
SELECT COUNT(*) AS COUNT
FROM TBL_SCORE;
--> 한 줄 구성
SELECT COUNT(*) AS COUNT FROM TBL_SCORE
;
--==>> 1

-- 4. 이름 검색 쿼리문 구성
SELECT *
FROM
(
    SELECT SID, NAME, KOR, ENG, MAT
         , (KOR+ENG+MAT) AS TOT
         , (KOR+ENG+MAT)/3 AS AVG
         , RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK
    FROM TBL_SCORE
)
WHERE NAME = '이다영';
--> 한 줄 구성
SELECT * FROM (SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG, RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK FROM TBL_SCORE) WHERE NAME = '이다영'
;
--==>> 1	이다영	90	80	70	240	80	1


-- 5. 번호 검색 쿼리문 구성
-- (번호검색하는 것 필요 없는데 왜? → 성적 수정/삭제 할때 삭제하려는 번호 입력받기 위해!!)
SELECT *
FROM
(
    SELECT SID, NAME, KOR, ENG, MAT
         , (KOR+ENG+MAT) AS TOT
         , (KOR+ENG+MAT)/3 AS AVG
         , RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK
    FROM TBL_SCORE
)
WHERE SID = 1;
--> 한줄 구성
SELECT * FROM (SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG, RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK FROM TBL_SCORE) WHERE SID = 1
;
--==>> 1	이다영	90	80	70	240	80	1

-- 6. 데이터 수정 쿼리문 구성
UPDATE TBL_SCORE
SET NAME = '이지영', KOR=10, ENG=20, MAT=30
WHERE SID = 1;
--> 한 줄 구성
UPDATE TBL_SCORE SET NAME = '이지영', KOR=10, ENG=20, MAT=30 WHERE SID = 1
;
--==>> 1 행 이(가) 업데이트되었습니다.

SELECT *
FROM TBL_SCORE;
--==>> 1	이지영	10	20	30

COMMIT;
--==>> 커밋 완료.


-- 7. 데이터 삭제 쿼리문 구성
DELETE
FROM TBL_SCORE
WHERE SID=1;
--> 한 줄 구성
DELETE FROM TBL_SCORE WHERE SID=1
;
--==>> 1 행 이(가) 삭제되었습니다.

-- 이렇게 지운채로 이클립스가면 2번부터 들어가기 때문에 다시 롤백!
ROLLBACK;
--==>> 롤백 완료.

DESC TBL_SCORE;

SELECT *
FROM TBL_SCORE;
