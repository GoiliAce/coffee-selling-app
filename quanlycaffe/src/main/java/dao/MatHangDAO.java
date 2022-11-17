package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import connectDb.ConnectDB;
import entity.MatHang;

public class MatHangDAO {
	private Connection con;
	private LoaiHangDAO lhDAO;
	public MatHangDAO() {
		con = ConnectDB.getInstance().getConnection();
		lhDAO = new LoaiHangDAO();
	}
	public boolean themMatHang(MatHang mh) throws Exception {
		if(timMatHang(mh.getMaMatHang())!= null)
			return false;
		String sql = "INSERT INTO [dbo].[MatHang]\r\n"
				+ "           ([maMatHang]\r\n"
				+ "           ,[donGia]\r\n"
				+ "           ,[soLuongTon]\r\n"
				+ "           ,[trangThai]\r\n"
				+ "           ,[maLoaiHang]\r\n"
				+ "           ,[linkAnh]\r\n"
				+ "           ,[tenMatHang])\r\n"
				+ "     VALUES\r\n"
				+ "           (?,?,?,?,?,?,?)";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
		stmt.setString(1, mh.getMaMatHang());
		stmt.setDouble(2, mh.getDonGia());
		stmt.setInt(3, mh.getSoLuongTon());
		stmt.setBoolean(4, mh.isTrangThai());
		stmt.setString(5, mh.getLoaiHang().getMaLoaiHang());
		stmt.setString(6, mh.getLinkAnh());
		stmt.setString(7,mh.getTenMatHang());
		n = stmt.executeUpdate();}
		return n>0;
	}
	
	public MatHang timMatHang(String maMatHang) throws Exception {
		String sql = "select * from MatHang where maMatHang = ?";
		MatHang mh = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maMatHang);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				mh = new MatHang(rs.getString(1), rs.getString(7), rs.getDouble(2), rs.getInt(3), rs.getBoolean(4),
						lhDAO.timLoaiHang(rs.getString(5)), rs.getString(6));

			}
		}

		return mh;
	}
	public List<MatHang> layDanhSachMatHang() throws Exception{
		List<MatHang> dsMH = new ArrayList<>();
		String sql = "select * from MatHang";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			MatHang mh = new MatHang(rs.getString(1),rs.getString(7),rs.getDouble(2),rs.getInt(3),rs.getBoolean(4),lhDAO.timLoaiHang(rs.getString(5)),rs.getString(6));
			dsMH.add(mh);
		}}
		return dsMH;
	}
	public boolean capNhatMatHang(MatHang mh) throws Exception {
		if(timMatHang(mh.getMaMatHang())!=null) {
			
			String sql = "UPDATE [dbo].[MatHang]\r\n"
					+ "   SET "
					+ "      [donGia] = ? "
					+ "      ,[soLuongTon] = ? "
					+ "      ,[trangThai] = ? "
					+ "      ,[maLoaiHang] = ? "
					+ "      ,[linkAnh] = ? "
					+ "      ,[tenMatHang] = ? "
					+ " WHERE maMatHang = ?";
			int n =0;
			
			try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setDouble(1, mh.getDonGia());
			stmt.setInt(2, mh.getSoLuongTon());
			stmt.setBoolean(3, mh.isTrangThai());
			stmt.setString(4, mh.getLoaiHang().getMaLoaiHang());
			stmt.setString(5, mh.getLinkAnh());
			stmt.setString(6, mh.getTenMatHang());
			stmt.setString(7, mh.getMaMatHang());
			n = stmt.executeUpdate();
			return n>0;
			}
		}
		return false;
	}
	public boolean deleteMatHang(String maMatHang) throws Exception {
		String sql = "DELETE FROM [dbo].[MatHang] "
				+ "      WHERE maMatHang = ? ";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maMatHang);
			n = stmt.executeUpdate();
		}
		return n>0;
	}
	public String getMaMatHangCaoNhat() throws Exception {
		String sql = "SELECT MAX(maMatHang) FROM MatHang";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
		}
		return null;
	}
}
