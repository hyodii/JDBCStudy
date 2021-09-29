SELECT USER
FROM DUAL;
--==>> SCOTT


TRUNCATE TABLE TBL_MEMBER;
--==>> Table TBL_MEMBER��(��) �߷Ƚ��ϴ�.


CREATE SEQUENCE MEMBERSEQ
NOCACHE;
--==>> Sequence MEMBERSEQ��(��) �����Ǿ����ϴ�.

drop SEQUENCE MEMBERSEQ;
--==>> Sequence MEMBERSEQ��(��) �����Ǿ����ϴ�.

--�� ������ �Է� ������ ����
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL,'������', '010-1111-1111')
;
--==>> 1 �� ��(��) ���ԵǾ����ϴ�.


--�� �ο��� Ȯ�� ������ ����
SELECT COUNT(*) AS COUNT
FROM TBL_MEMBER;
--==>> 1
--> �� �� ����
SELECT COUNT(*) AS COUNT FROM TBL_MEMBER
;

--�� ��ü ����Ʈ ��ȸ ������ ����
SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--==>> 1	������	010-1111-1111
--> �� �� ����
SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID
;

-- ������ �����ϰ� Ŀ�� !!!!CHECK~!!! 
COMMIT;



























