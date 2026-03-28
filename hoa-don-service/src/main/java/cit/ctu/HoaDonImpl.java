package cit.ctu;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import static cit.ctu.Constant.BASE_API_URL_MON_AN;
import static cit.ctu.Constant.BASE_API_URL_NGUOI_DUNG;

public class HoaDonImpl implements IHoaDon {
	private static final URI URI_NGUOIDUNG = UriBuilder.fromUri(BASE_API_URL_NGUOI_DUNG).build();
	private static final URI URI_MONAN = UriBuilder.fromUri(BASE_API_URL_MON_AN).build();
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target_NguoiDung = client.target(URI_NGUOIDUNG);
	WebTarget target_MonAn = client.target(URI_MONAN);
	
	@Override
	public int taoHoaDon(HoaDon hd) {
	    int id = -1;
	    try {
	    	Connection conn = DBConnection.getConnection();
	    	Response res = target_NguoiDung.path("rest")
                    .path("nguoidung")
                    .path("id")
                    .path(String.valueOf(hd.getNguoiDungId())) 
                    .request()
                    .get();
	    	if (res.getStatus() != 200) {
	    	    System.out.println("Người dùng không tồn tại");
	    	    return -1;
	    	}
	    	NguoiDung nd = res.readEntity(NguoiDung.class);
	    	if (nd == null) {
	            System.out.println("Người dùng không tồn tại, không tạo hóa đơn");
	            return -1;
	        }
	        String sql = "INSERT INTO hoadon(nguoi_dung_id, trang_thai) VALUES(?,?)";
	        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ps.setInt(1, hd.getNguoiDungId());
	        ps.setString(2, hd.getTrangThai());
	        ps.executeUpdate();
	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            id = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return id;
	}

	@Override
	public boolean themChiTietHoaDon(ChiTietHoaDon ct) {
	    boolean result = false;
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sqlHoaDon = "SELECT id FROM hoadon WHERE id=?";
	        try (PreparedStatement psHoaDon = conn.prepareStatement(sqlHoaDon)) {
	            psHoaDon.setInt(1, ct.getHoaDonId());
	            try (ResultSet rs = psHoaDon.executeQuery()) {
	                if (!rs.next()) {
	                    System.out.println("Hóa đơn không tồn tại, không thể thêm chi tiết");
	                    return false;
	                }
	            }
	        }
	        Response res = target_MonAn.path("rest")
                    .path("monan")
                    .path("TimMon")
                    .path(String.valueOf(ct.getMonAnId()))
                    .request()
                    .get();
	        if (res.getStatus() != 200) {
	            System.out.println("Món ăn không tồn tại");
	            return false;
	        }
	        MonAn mon = res.readEntity(MonAn.class);
	        if (mon == null) {
	            System.out.println("Món ăn không tồn tại, không thể thêm chi tiết");
	            return false;
	        }
	        Response resDecrease = target_MonAn.path("rest")
                    .path("monan")
                    .path("GiamSoLuong")
                    .path(String.valueOf(ct.getMonAnId()))
                    .path(String.valueOf(ct.getSoLuong()))
                    .request(MediaType.APPLICATION_JSON)
                    .get();	        
	        if(resDecrease.getStatus() != 200) {
	        	System.out.println("Không thể giảm số lượng món ăn");
	        	return false;
	        }	        
	        String sql = "INSERT INTO chitiethoadon(hoa_don_id, mon_an_id, so_luong, don_gia_tai_thoi_diem_tao_hoa_don) VALUES(?,?,?,?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, ct.getHoaDonId());
	        ps.setInt(2, ct.getMonAnId());
	        ps.setInt(3, ct.getSoLuong());
	        ps.setDouble(4, mon.getGia());
	        result = ps.executeUpdate() > 0;
	        ps.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

    @Override
    public List<HoaDon> layDanhSachHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM hoadon";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("id"));
                hd.setNguoiDungId(rs.getInt("nguoi_dung_id"));
                Timestamp ts = rs.getTimestamp("ngay_dat");
                Calendar cal = Calendar.getInstance();
                cal.setTime(ts);               
                hd.setDanhSachChiTietHoaDon(layChiTietHoaDon(rs.getInt("id")));
                MyDate md = new MyDate(
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR)
                );               
                hd.setNgayDat(md);
                System.out.println("Ngay dat: " + hd.getNgayDat());
                hd.setTrangThai(rs.getString("trang_thai"));
                ds.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<ChiTietHoaDon> layChiTietHoaDon(int hoaDonId) {
        List<ChiTietHoaDon> ds = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM chitiethoadon WHERE hoa_don_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, hoaDonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setId(rs.getInt("id"));
                ct.setHoaDonId(rs.getInt("hoa_don_id"));
                ct.setMonAnId(rs.getInt("mon_an_id"));
                ct.setSoLuong(rs.getInt("so_luong"));
                ct.setDonGiaTaiThoiDiemTaoHoaDon(rs.getDouble("don_gia_tai_thoi_diem_tao_hoa_don"));
                ds.add(ct);
            }
        } catch (Exception e) {
        	System.out.println("Có lỗi lúc lấy chi tiết hóa đơn: " + e);
            e.printStackTrace();
        }
        return ds;
    }
    
    @Override
    public List<HoaDon> layHoaDonTheoNguoiDung(int nguoiDungId) {
        List<HoaDon> ds = new ArrayList<>();
        try {
        	Response res = target_NguoiDung.path("rest")
        	        .path("nguoidung")
        	        .path("id")
        	        .path(String.valueOf(nguoiDungId))
        	        .request()
        	        .get();
        	if (res.getStatus() != 200) {
        	    System.out.println("Người dùng không tồn tại");
        	    return ds;
        	}
        	NguoiDung nd = res.readEntity(NguoiDung.class);
        	if (nd == null) {
                System.out.println("Người dùng không tồn tại, không lấy hóa đơn");
                return ds; 
            }
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM hoadon WHERE nguoi_dung_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, nguoiDungId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("id"));
                hd.setNguoiDungId(rs.getInt("nguoi_dung_id"));
                Timestamp ts = rs.getTimestamp("ngay_dat");
                Calendar cal = Calendar.getInstance();
                cal.setTime(ts);     
                hd.setDanhSachChiTietHoaDon(layChiTietHoaDon(rs.getInt("id")));
                MyDate md = new MyDate(
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR)
                );             
                hd.setNgayDat(md);
                System.out.println("Ngay dat: " + hd.getNgayDat());
                hd.setTrangThai(rs.getString("trang_thai"));
                ds.add(hd);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public boolean capNhatSoLuongMonAnTrongChiTietHoaDon(int idChiTietHoaDon, int idMonAn, int soLuongMoi) {
        Connection conn = null;
        PreparedStatement psChiTiet = null;
        PreparedStatement psKho = null;
        PreparedStatement psUpdateChiTiet = null;
        PreparedStatement psUpdateKho = null;
        try {
            conn = DBConnection.getConnection();
            String sqlChiTiet = "SELECT so_luong FROM chitiethoadon WHERE id = ? AND mon_an_id = ?";
            psChiTiet = conn.prepareStatement(sqlChiTiet);
            psChiTiet.setInt(1, idChiTietHoaDon);
            psChiTiet.setInt(2, idMonAn);
            ResultSet rsChiTiet = psChiTiet.executeQuery();
            if (!rsChiTiet.next()) return false;
            int soLuongCu = rsChiTiet.getInt("so_luong");
            int delta = soLuongMoi - soLuongCu;
            String sqlKho = "SELECT so_luong, trang_thai FROM monan WHERE id = ?";
            psKho = conn.prepareStatement(sqlKho);
            psKho.setInt(1, idMonAn);
            ResultSet rsKho = psKho.executeQuery();
            if (!rsKho.next()) return false;
            int tonKho = rsKho.getInt("so_luong");
            String trangThaiKho = rsKho.getString("trang_thai");
            if (delta > 0 && delta > tonKho) {
                System.out.println("Không đủ tồn kho để tăng số lượng");
                return false;
            }
            String sqlUpdateChiTiet = "UPDATE chitiethoadon SET so_luong = ? WHERE id = ? AND mon_an_id = ?";
            psUpdateChiTiet = conn.prepareStatement(sqlUpdateChiTiet);
            psUpdateChiTiet.setInt(1, soLuongMoi);
            psUpdateChiTiet.setInt(2, idChiTietHoaDon);
            psUpdateChiTiet.setInt(3, idMonAn);
            psUpdateChiTiet.executeUpdate();
            String sqlUpdateKho = "UPDATE monan SET so_luong = so_luong - ? WHERE id = ?";
            psUpdateKho = conn.prepareStatement(sqlUpdateKho);
            psUpdateKho.setInt(1, delta); 
            psUpdateKho.setInt(2, idMonAn);
            psUpdateKho.executeUpdate();
            int tonKhoMoi = tonKho - delta;
            String trangThaiMoi;
            if (tonKhoMoi <= 0) {
                trangThaiMoi = "het";
            } else if (trangThaiKho.equals("het") && tonKhoMoi > 0) {
                trangThaiMoi = "con";
            } else {
                trangThaiMoi = trangThaiKho;
            }
            if (!trangThaiMoi.equals(trangThaiKho)) {
                String sqlUpdateStatus = "UPDATE monan SET trang_thai = ? WHERE id = ?";
                try (PreparedStatement psStatus = conn.prepareStatement(sqlUpdateStatus)) {
                    psStatus.setString(1, trangThaiMoi);
                    psStatus.setInt(2, idMonAn);
                    psStatus.executeUpdate();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (psChiTiet != null) psChiTiet.close();
                if (psKho != null) psKho.close();
                if (psUpdateChiTiet != null) psUpdateChiTiet.close();
                if (psUpdateKho != null) psUpdateKho.close();
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }

	@Override
	public boolean capNhatTrangThaiHoaDon(int id, String trangThai) {
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "UPDATE hoadon SET trang_thai = ? WHERE id = ?";	        
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, trangThai);
	        ps.setInt(2, id);	       
	        int rows = ps.executeUpdate();	        
	        conn.close();	        
	        return rows > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	@Override
    public boolean xoaHoaDon(int hoaDonId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM hoadon WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, hoaDonId);
            int row = ps.executeUpdate();
            return row > 0; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
    public boolean xoaChiTietHoaDon(int chiTietId) {
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdateMon = null;
        PreparedStatement psDelete = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); 
            String sqlSelect = "SELECT mon_an_id, so_luong FROM chitiethoadon WHERE id=?";
            psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, chiTietId);
            ResultSet rs = psSelect.executeQuery();
            if (!rs.next()) {
                return false; 
            }
            int monAnId = rs.getInt("mon_an_id");
            int soLuongChiTiet = rs.getInt("so_luong");
            String sqlUpdateMon = "UPDATE monan SET so_luong = so_luong + ?, trang_thai = CASE WHEN so_luong + ? > 0 THEN 'con' ELSE trang_thai END WHERE id=?";
            psUpdateMon = conn.prepareStatement(sqlUpdateMon);
            psUpdateMon.setInt(1, soLuongChiTiet);
            psUpdateMon.setInt(2, soLuongChiTiet);
            psUpdateMon.setInt(3, monAnId);
            psUpdateMon.executeUpdate();
            String sqlDelete = "DELETE FROM chitiethoadon WHERE id=?";
            psDelete = conn.prepareStatement(sqlDelete);
            psDelete.setInt(1, chiTietId);
            psDelete.executeUpdate();
            conn.commit(); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (psSelect != null) psSelect.close();
                if (psUpdateMon != null) psUpdateMon.close();
                if (psDelete != null) psDelete.close();
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }
	}
	
	@Override
	public double tinhTongTienHoaDon(int hoaDonId) {
	    double tong = 0;
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sql =
	            "SELECT so_luong, don_gia_tai_thoi_diem_tao_hoa_don " +
	            "FROM chitiethoadon WHERE hoa_don_id = ?";
	        PreparedStatement ps =
	            conn.prepareStatement(sql);
	        ps.setInt(1, hoaDonId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            int soLuong = rs.getInt("so_luong");
	            double donGia =
	                rs.getDouble(
	                  "don_gia_tai_thoi_diem_tao_hoa_don");
	            tong += soLuong * donGia;
	        }
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return tong;
	}
	
	@Override
	public int tinhTongSoLuongHoaDon(int hoaDonId) {
	    int tong = 0;
	    try {
	        Connection conn =
	            DBConnection.getConnection();
	        String sql =
	            "SELECT so_luong FROM chitiethoadon " +
	            "WHERE hoa_don_id = ?";
	        PreparedStatement ps =
	            conn.prepareStatement(sql);
	        ps.setInt(1, hoaDonId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            tong += rs.getInt("so_luong");
	        }
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return tong;
	}
}