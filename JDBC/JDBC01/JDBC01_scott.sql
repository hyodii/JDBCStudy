SELECT USER
FROM DUAL;
--==>> SCOTT

DROP TABLE TBL_MEMBER;
--==>> Table TBL_MEMBER��(��) �����Ǿ����ϴ�.

--�� �ǽ� ���̺� ����
CREATE TABLE TBL_MEMBER
( SID   NUMBER
, NAME  VARCHAR2(30)
, TEL   VARCHAR2(60)
, CONSTRAINT MEMBER_SID_PK PRIMARY KEY(SID)
);
--==>> Table TBL_MEMBER��(��) �����Ǿ����ϴ�.

-- ����Ŭ�� ��Ŭ���� �Դٰ��� �Ҷ��� Ŀ�� ���� �߿�!!!

--�� ���� ������ �Է�
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES (1, '��ȿ��', '010-7791-9736');
--==>> 1 �� ��(��) ���ԵǾ����ϴ�.


--�� Ȯ��
SELECT *
FROM TBL_MEMBER;
--==>> 1	��ȿ��	010-7791-9736

--�� Ŀ��
COMMIT;
--==>> Ŀ�� �Ϸ�.

--�� �ڹٿ��� Test003 Ŭ���� ���� �� �ٽ� Ȯ��
SELECT *
FROM TBL_MEMBER;
--==>>
/*
1	��ȿ��	010-7791-9736
2	ä����	010-2222-2222
*/


--�� �ڹٿ��� Test004 Ŭ���� ���� �� �ٽ� Ȯ��
SELECT *
FROM TBL_MEMBER;
--==>>
/*
1	��ȿ��	010-7791-9736
2	ä����	010-2222-2222
3	��ҿ�	010-3333-3333
4	������	010-4444-4444
5	�մ���	010-5555-5555
*/


--�� ������ ��ȸ ������ �غ�
SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--> �� �� ����
SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID
;







