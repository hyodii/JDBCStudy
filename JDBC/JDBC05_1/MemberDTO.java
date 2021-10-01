/*========================================
   			MemberDTO.java
   	- 데이터 저장 및 전송 객체 구성 
==========================================*/

package com.test;

public class MemberDTO
{
	// 주요 속성 구성
	private String empid, empname, ssn, cityid, tel, buseoid, jikweid;
	private String ibsadate;
	private int basicpay, sudang, salary;
	
	
	public String getEmpid()
	{
		return empid;
	}
	public void setEmpid(String empid)
	{
		this.empid = empid;
	}
	public String getEmpname()
	{
		return empname;
	}
	public void setEmpname(String empname)
	{
		this.empname = empname;
	}
	public String getSsn()
	{
		return ssn;
	}
	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}
	public String getCityid()
	{
		return cityid;
	}
	public void setCityid(String cityid)
	{
		this.cityid = cityid;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getBuseoid()
	{
		return buseoid;
	}
	public void setBuseoid(String buseoid)
	{
		this.buseoid = buseoid;
	}
	public String getJikweid()
	{
		return jikweid;
	}
	public void setJikweid(String jikweid)
	{
		this.jikweid = jikweid;
	}
	public String getIbsadate()
	{
		return ibsadate;
	}
	public void setIbsadate(String ibsadate)
	{
		this.ibsadate = ibsadate;
	}
	public int getBasicpay()
	{
		return basicpay;
	}
	public void setBasicpay(int basicpay)
	{
		this.basicpay = basicpay;
	}
	public int getSudang()
	{
		return sudang;
	}
	public void setSudang(int sudang)
	{
		this.sudang = sudang;
	}
	public int getSalary()
	{
		return salary;
	}
	public void setSalary(int basicpay, int sudang)
	{
		this.salary = (basicpay * 12) + sudang;
	}
}
