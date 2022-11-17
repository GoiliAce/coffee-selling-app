package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDb.ConnectDB;
import entity.DiaChi;

public class DiaChiDAO {
	private Connection con;

	public DiaChiDAO() {
		con = ConnectDB.getInstance().getConnection();
	}
	public DiaChi getDiaChiTheoMa(String maDC) {
		String sql = "select * from diaChi where maDC = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1,maDC);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return new DiaChi(rs.getString("maDC"), rs.getString("tinhTP"), rs.getString("quanHuyen"), rs.getString("PhuongXa"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public DiaChi getDiaChiTheoTinhHuyenXa(String tinh, String huyen,String xa) {
		String sql = "select * from diaChi where tinhTP = ? and quanHuyen = ? and PhuongXa = ? ";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1,tinh);
			stmt.setString(2,huyen);
			stmt.setString(3,xa);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return new DiaChi(rs.getString("maDC"), rs.getString("tinhTP"), rs.getString("quanHuyen"), rs.getString("PhuongXa"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<String> layDanhSachTinh() {
		List<String> dsTinh = new ArrayList<>();
		String sql = "select tinhTP from diaChi";
		dsTinh.add("Tỉnh/Thành Phố");
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String tinh = rs.getString(1);
				if (dsTinh.contains(tinh) == false) {
					dsTinh.add(tinh);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsTinh;
	}

	public List<String> layDanhSachHuyen() {
		List<String> dsHuyen = new ArrayList<>();
		String sql = "select quanHuyen from diaChi";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String huyen = rs.getString(1);
				if (!dsHuyen.contains(huyen)) {
					dsHuyen.add(rs.getString(1));
				}
					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsHuyen;
	}

	public List<String> layDanhSachXa() {
		List<String> dsXa = new ArrayList<>();
		String sql = "select phuongXa from diaChi";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dsXa.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsXa;
	}

	public List<String> layDSHuyenTheoTinh(String tinh) {
		List<String> dsHuyen = new ArrayList<>();
		dsHuyen.add("Quận, Huyện");
		String sql = "select quanHuyen from diaChi where tinhTP = ? ";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setString(1, tinh);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String huyen = rs.getString(1);
				if (!dsHuyen.contains(huyen)) {
					dsHuyen.add(rs.getString(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsHuyen;
	}

	public List<String> layDSXaTheoHuyenTinh(String tinh, String huyen) {
		List<String> dsXa = new ArrayList<>();
		dsXa.add("Phường, Xã");
		String sql = "select phuongXa from diachi where tinhTP = ? and quanHuyen = ? ";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tinh);
			stmt.setString(2, huyen);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dsXa.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsXa;
	}
}
