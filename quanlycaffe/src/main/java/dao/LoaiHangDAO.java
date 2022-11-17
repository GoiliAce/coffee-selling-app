package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import connectDb.ConnectDB;
import entity.LoaiHang;

public class LoaiHangDAO {
	private Connection con;

	public LoaiHangDAO() {
		con = ConnectDB.getInstance().getConnection();
	}

	public boolean themLoaiHang(LoaiHang lh) throws Exception {
		if (timLoaiHang(lh.getMaLoaiHang()) != null)
			return false;
		String sql = "INSERT INTO [dbo].[LoaiHang]\r\n" + "           ([maLoaiHang]\r\n"
				+ "           ,[tenLoaiHang])\r\n" + "     VALUES\r\n" + "           (?,?)";
		int n = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, lh.getMaLoaiHang());
			stmt.setString(2, lh.getTenLoaiHang());
			n = stmt.executeUpdate();

		}
		return n > 0;
	}
	public LoaiHang timTheoTenLoaiHang(String tenLoaiHang) throws Exception {
		String sql = "select * from LoaiHang where tenLoaiHang = ? ";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, tenLoaiHang);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return new LoaiHang(rs.getString(1), rs.getString(2));
			}
		}
		return null;
	}
	public LoaiHang timLoaiHang(String maLoaiHang) throws Exception {
		String sql = "select * from LoaiHang where maLoaiHang = ? ";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maLoaiHang);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return new LoaiHang(rs.getString(1), rs.getString(2));
			}
		}
		return null;
	}

	public List<LoaiHang> layDanhSachLoaiHang() throws Exception {
		List<LoaiHang> dsLH = new ArrayList<>();
		String sql = "select * from LoaiHang";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dsLH.add(new LoaiHang(rs.getString(1), rs.getString(2)));
			}
		}
		return dsLH;
	}

	public boolean capNhatLoaiHang(LoaiHang lh) throws Exception {
		if (timLoaiHang(lh.getMaLoaiHang()) != null) {
			String sql = "UPDATE [dbo].[LoaiHang]\r\n" + "   SET [tenLoaiHang] = ? " + " WHERE maLoaiHang = ? ";
			try (PreparedStatement stmt = con.prepareStatement(sql)) {
				stmt.setString(1, lh.getTenLoaiHang());
				stmt.setString(2, lh.getMaLoaiHang());
				int n = stmt.executeUpdate();
				return n > 0;
			}
		}
		return false;
	}

	public boolean deleteLoaiHang(String maLoaiHang) throws Exception {
		String sql = "DELETE FROM [dbo].[LoaiHang] " + "      WHERE maLoaiHang = ? ";
		int n = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maLoaiHang);
			n = stmt.executeUpdate();
		}
		return n > 0;
	}
}
