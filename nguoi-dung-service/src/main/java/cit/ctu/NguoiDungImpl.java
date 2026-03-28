package cit.ctu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NguoiDungImpl implements INguoiDung {

	public boolean dangKy(NguoiDung nd) {
	    boolean kq = false;
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "INSERT INTO nguoidung(ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, dia_chi, vai_tro, trang_thai) VALUES(?,?,?,?,?,?,?,?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, nd.getTenDangNhap());
	        String matKhauHash = PasswordUtil.hashPassword(nd.getMatKhau());
	        ps.setString(2, matKhauHash);
	        ps.setString(3, nd.getHoTen());
	        ps.setString(4, nd.getEmail());
	        ps.setString(5, nd.getSoDienThoai());
	        ps.setString(6, nd.getDiaChi());
	        ps.setString(7, "khachhang");
	        ps.setString(8, "hoatdong");
	        int n = ps.executeUpdate();
	        conn.close();
	        if (n > 0) kq = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return kq;
	}

	@Override
	public boolean capNhatNguoiDung(NguoiDung nd) {
		try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "UPDATE nguoidung SET ho_ten=?, email=?, so_dien_thoai=?, dia_chi=? WHERE id=?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, nd.getHoTen());
	        ps.setString(2, nd.getEmail());
	        ps.setString(3, nd.getSoDienThoai());
	        ps.setString(4, nd.getDiaChi());
	        ps.setInt(5, nd.getId());
	        boolean kq = ps.executeUpdate() > 0;
	        conn.close();
	        return kq;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	@Override
	public NguoiDung xemThongTin(String tenDangNhap) {
	    NguoiDung nd = null;
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "SELECT * FROM nguoidung WHERE ten_dang_nhap=?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, tenDangNhap);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            nd = new NguoiDung();
	            nd.setId(rs.getInt("id"));
	            nd.setTenDangNhap(rs.getString("ten_dang_nhap"));
	            nd.setHoTen(rs.getString("ho_ten"));
	            nd.setEmail(rs.getString("email"));
	            nd.setMatKhau(rs.getString("mat_khau"));
	            nd.setSoDienThoai(rs.getString("so_dien_thoai"));
	            nd.setDiaChi(rs.getString("dia_chi"));
	            nd.setVaiTro(rs.getString("vai_tro"));
	            nd.setTrangThai(rs.getString("trang_thai"));
	        }
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return nd;
	}
	
	@Override
	public boolean doiMatKhau(String tenDangNhap, String matKhauCu, String matKhauMoi) {
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sqlGet = "SELECT mat_khau FROM nguoidung WHERE ten_dang_nhap=?";
	        PreparedStatement psGet = conn.prepareStatement(sqlGet);
	        psGet.setString(1, tenDangNhap);
	        ResultSet rs = psGet.executeQuery();
	        if (rs.next()) {
	            String matKhauHashDB = rs.getString("mat_khau");
	            String matKhauCuHash = PasswordUtil.hashPassword(matKhauCu);
	            if (matKhauHashDB.equals(matKhauCuHash)) {
	                String sqlUpdate = "UPDATE nguoidung SET mat_khau=? WHERE ten_dang_nhap=?";
	                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
	                psUpdate.setString(1, PasswordUtil.hashPassword(matKhauMoi));
	                psUpdate.setString(2, tenDangNhap);
	                boolean kq = psUpdate.executeUpdate() > 0;
	                conn.close();
	                return kq;
	            }
	        }
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	@Override
	public boolean xoaNguoiDung(String tenDangNhap) {
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "DELETE FROM nguoidung WHERE ten_dang_nhap=?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, tenDangNhap);
	        boolean kq = ps.executeUpdate() > 0;
	        conn.close();
	        return kq;	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	@Override
	public NguoiDung timTheoId(int id) {
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "SELECT * FROM nguoidung WHERE id=?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	        	NguoiDung nd = new NguoiDung();
	        	nd.setId(rs.getInt("id"));
	            nd.setTenDangNhap(rs.getString("ten_dang_nhap"));
	            nd.setMatKhau(rs.getString("mat_khau"));
	            nd.setHoTen(rs.getString("ho_ten"));
	            nd.setEmail(rs.getString("email"));
	            nd.setSoDienThoai(rs.getString("so_dien_thoai"));
	            nd.setDiaChi(rs.getString("dia_chi"));
	            nd.setVaiTro(rs.getString("vai_tro"));
	            nd.setTrangThai(rs.getString("trang_thai"));
	            return nd;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
