/*========================================
   			ScoreDTO.java
   	- 데이터 저장 및 전송 객체 구성 
==========================================*/

// 데이터가 많아지면 속성을 어떻게 구성하는지도 잘 생각해야함!
// 지금 예제에서는 속성을 DTO안에 구성해도 되고 안해도됨!
// 총점, 평균 값으로 기억해야 하면 무조건 DTO안에
// 하지만 총점, 평균 조회만 하고 말꺼면 메소드에서만 사용해도 됨!

package com.test;

public class ScoreDTO
{
	// 주요 속성 구성
	private int sid, kor, eng, mat, tot;
	private String name;
	private double avg;
	
	public int getSid()
	{
		return sid;
	}
	public void setSid(int sid)
	{
		this.sid = sid;
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
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getTot()
	{
		return tot;
	}
	public void setTot(int kor, int eng, int mat)
	{
		this.tot = kor + eng + mat;
	}
	public double getAvg()
	{
		return avg;
	}
	public void setAvg(int kor, int eng, int mat)
	{
		this.avg = (kor + eng + mat) / 3.0 ;
	}
}
