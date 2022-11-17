package entity;

import java.util.Objects;

public class DiaChi {
	private String maDC;
	private String tinhTP;
	private String quanHuyen;
	private String phuongXa;
	public String getMaDC() {
		return maDC;
	}
	public void setMaDC(String maDC) {
		this.maDC = maDC;
	}
	public String getTinhTP() {
		return tinhTP;
	}
	public void setTinhTP(String tinhTP) {
		this.tinhTP = tinhTP;
	}
	public String getQuanHuyen() {
		return quanHuyen;
	}
	public void setQuanHuyen(String quanHuyen) {
		this.quanHuyen = quanHuyen;
	}
	public String getPhuongXa() {
		return phuongXa;
	}
	public void setPhuongXa(String phuongXa) {
		this.phuongXa = phuongXa;
	}
	public DiaChi() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DiaChi(String maDC, String tinhTP, String quanHuyen, String phuongXa) {
		super();
		this.maDC = maDC;
		this.tinhTP = tinhTP;
		this.quanHuyen = quanHuyen;
		this.phuongXa = phuongXa;
	}
	@Override
	public String toString() {
		return String.format("%s,%s,%s", phuongXa,quanHuyen,tinhTP);
	}
	@Override
	public int hashCode() {
		return Objects.hash(maDC);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiaChi other = (DiaChi) obj;
		return Objects.equals(maDC, other.maDC);
	}
	
}
