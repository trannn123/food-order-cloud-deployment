package cit.ctu.QLDTAN;

public class MyDate {

    private int ngay;
    private int thang;
    private int nam;

    public MyDate() {}

    public MyDate(int ngay, int thang, int nam) {
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
    }

    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }
    
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", ngay, thang, nam);
    }
}