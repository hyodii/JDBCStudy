/*===============================
		MemberDTO.java
=================================*/

package com.test;

public class MemberDTO
{
	// 주요 속성 구성
	// 여기서는 뷰의 레코드 구조를 참조하는 것이 편할 것임!
	//--사번,이름,주민번호,입사일,지역,전화번호
	// ,부서,직위,기본급,수당,급여
	//★(오라클에서 컬럼값은 ID로 받지만 속성값에선느 NAME으로 하는 것이 불러올 때 좋음!)
	private int empId, basicPay, sudang, pay;
	private String empName,ssn,cityName,tel,buseoName,jikwiName;
	
	private String ibsaDate;	// ※ 입사일은 오라클에서 날짜 형식으로 처리되지만 문자열로 구성

	// getter / setter 구성
	public int getEmpId()
	{
		return empId;
	}

	public void setEmpId(int empId)
	{
		this.empId = empId;
	}

	public int getBasicPay()
	{
		return basicPay;
	}

	public void setBasicPay(int basicPay)
	{
		this.basicPay = basicPay;
	}

	public int getSudang()
	{
		return sudang;
	}

	public void setSudang(int sudang)
	{
		this.sudang = sudang;
	}

	public int getPay()
	{
		return pay;
	}

	public void setPay(int pay)
	{
		this.pay = pay;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getSsn()
	{
		return ssn;
	}

	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}

	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getBuseoName()
	{
		return buseoName;
	}

	public void setBuseoName(String buseoName)
	{
		this.buseoName = buseoName;
	}

	public String getJikwiName()
	{
		return jikwiName;
	}

	public void setJikwiName(String jikwiName)
	{
		this.jikwiName = jikwiName;
	}

	public String getIbsaDate()
	{
		return ibsaDate;
	}

	public void setIbsaDate(String ibsaDate)
	{
		this.ibsaDate = ibsaDate;
	}
	
	
}
