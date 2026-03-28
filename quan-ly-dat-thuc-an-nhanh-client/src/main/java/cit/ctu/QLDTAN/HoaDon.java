package cit.ctu.QLDTAN;

import java.util.List;

public class HoaDon {

    private int id;
    private String trangThai;
    private int nguoiDungId;
    private MyDate ngayDat;
    private List<ChiTietHoaDon> danhSachChiTietHoaDon;
    
    public HoaDon() {}

    public HoaDon(int id, int nguoiDungId, MyDate ngayDat, String trangThai, List<ChiTietHoaDon> danhSachChiTietHoaDon) {
	  this.id = id;
	  this.nguoiDungId = nguoiDungId;
	  this.ngayDat = ngayDat;
	  this.trangThai = trangThai;
	  this.danhSachChiTietHoaDon = danhSachChiTietHoaDon;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNguoiDungId() {
        return nguoiDungId;
    }

    public void setNguoiDungId(int nguoiDungId) {
        this.nguoiDungId = nguoiDungId;
    }

    public MyDate getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(MyDate ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public List<ChiTietHoaDon> getDanhSachChiTietHoaDon() {
        return danhSachChiTietHoaDon;
    }

    public void setDanhSachChiTietHoaDon(List<ChiTietHoaDon> danhSachChiTietHoaDon) {
        this.danhSachChiTietHoaDon = danhSachChiTietHoaDon;
    }
    
}