package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import connectDb.ConnectDB;
import entity.Ban;

public class BanDAO {
	private Connection con;
	public BanDAO(){
		con = ConnectDB.getInstance().getConnection();
	}
	public int themBan(Ban ban) throws Exception {
		if(timBan(ban.getMaBan())!= null) {
			throw new InputMismatchException("Ban da ton tai");
		}
		String sql = "\r\n"
				+ "INSERT INTO [dbo].[Ban]\r\n"
				+ "           ([trangThai])\r\n"
				+ "     VALUES (?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setBoolean(1, ban.isTrangThai());
		int n = stmt.executeUpdate();
		return n;
	}
	public Ban timBan(int i) throws Exception {
		String sql ="select * from Ban where maBan = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, i);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			return new Ban(Integer.parseInt(rs.getString(1)), rs.getBoolean(2));
		}
		return null;
	}
	public List<Ban> layDanhSachBan() throws Exception{
		List<Ban> dsBan = new ArrayList<>();
		String sql = "select * from Ban";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			dsBan.add(new Ban(Integer.parseInt(rs.getString(1)), rs.getBoolean(2)));
		}
		return dsBan;
	}

	public boolean deleteBan(int maBan) throws Exception {
		String sql = "DELETE FROM [dbo].[Ban] "
				+ "      WHERE maBan = ? ";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, maBan);
			n = stmt.executeUpdate();
		}
		return n>0;
	}
}
