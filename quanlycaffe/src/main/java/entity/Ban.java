package entity;

import java.util.Objects;

public class Ban {
	private int maBan;
	private boolean trangThai;
	public int getMaBan() {
		return maBan;
	}
	public void setMaBan(int maBan) {
		this.maBan = maBan;
	}
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	public Ban(boolean trangThai) {
		super();
		this.trangThai = trangThai;
		// TODO Auto-generated constructor stub
	}
	public Ban(int maBan, boolean trangThai) {
		super();
		this.maBan = maBan;
		this.trangThai = trangThai;
	}
	@Override
	public java.lang.String toString() {
		return "Ban [maBan=" + maBan + ", trangThai=" + trangThai + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maBan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ban other = (Ban) obj;
		return Objects.equals(maBan, other.maBan);
	}
	
}
