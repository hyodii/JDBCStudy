/*===================================
	ScoreDTO.java
	- 데이터 보관 및 전송 전용 객체
=====================================*/

// DTO 개념 잘알아야 나중에 자바 빈 할때 안헷갈림!

package com.test;

public class ScoreDTO
{
	// 주요 속성 구성
	// 네줄에 전부 private 적용
	// alt + shift + a → 블럭잡고 값 입력!!
	private String sid, name;
	private int kor, eng, mat;
	private int tot, rank;
	private double avg;
	
	// getter / setter 구성 → 소스 제너레잇 !!셀렉트올!! 하면 전부 됨 
	public String getSid()
	{
		return sid;
	}
	public void setSid(String sid)
	{
		this.sid = sid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getKor()
	{
		return kor;
	}
	public void setKor(int kor)
	{
		this.kor = kor;
	}
	public int getEng()
	{
		return eng;
	}
	public void setEng(int eng)
	{
		this.eng = eng;
	}
	public int getMat()
	{
		return mat;
	}
	public void setMat(int mat)
	{
		this.mat = mat;
	}
	public int getTot()
	{
		return tot;
	}
	public void setTot(int tot)
	{
		this.tot = tot;
	}
	public int getRank()
	{
		return rank;
	}
	public void setRank(int rank)
	{
		this.rank = rank;
	}
	public double getAvg()
	{
		return avg;
	}
	public void setAvg(double avg)
	{
		this.avg = avg;
	}
	
	
}
