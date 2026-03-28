package cit.ctu.QLDTAN;

import java.util.Date;

public class MonAn {

    private int id;
    private String tenMon;
    private double gia;
    private int soLuong;
    private String trangThai;
    private String moTa;
    private Date ngayTao;

    public MonAn() {}

    public MonAn(int id, String tenMon, double gia, int soLuong,
                 String trangThai, String moTa, Date ngayTao) {
        this.id = id;
        this.tenMon = tenMon;
        this.gia = gia;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }

    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }
}