package cit.ctu.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import cit.ctu.QLDTAN.HoaDon;
import cit.ctu.QLDTAN.NguoiDung;
import cit.ctu.QLDTAN.ItemGioHang;

import static cit.ctu.Constants.BASE_API_URL_HOA_DON;

@WebServlet("/HoaDonDaDat")
public class HoaDonDaDat extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_HOA_DON).build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        if (nd == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");
        int tongSoLuongTrongGio = 0;
        if (cart != null) {
            for (ItemGioHang g : cart) {
                tongSoLuongTrongGio += g.getSoLuong();
            }
        }

        List<HoaDon> dsHoaDon = null;

        try {
            dsHoaDon = target
                    .path("rest")
                    .path("hoadon")
                    .path("LayHoaDonTheoNguoiDung")
                    .path(String.valueOf(nd.getId()))
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<HoaDon>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Hóa đơn đã đặt</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        // ===== CSS CHUẨN =====
        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".navbar-custom{background:#fff;box-shadow:0 2px 12px rgba(0,0,0,0.06);}");
        out.println(".brand-text{color:#ff6b2c;font-size:1.4rem;font-weight:700;text-decoration:none;}");
        out.println(".cart-btn{position:relative;border-radius:12px;font-weight:600;padding-left:16px;padding-right:16px;}");
        out.println(".cart-badge{position:absolute;top:-8px;right:-8px;background:#dc3545;color:#fff;border-radius:999px;padding:4px 8px;font-size:12px;font-weight:700;min-width:24px;text-align:center;}");
        out.println(".welcome-text{color:#dc3545;font-weight:500;}");
        out.println(".invoice-btn{background:#fff0f0;color:#dc3545;border:1px solid #f5c2c7;border-radius:999px;font-weight:600;padding:6px 14px;}");
        out.println(".invoice-btn:hover{background:#dc3545;color:#fff;border-color:#dc3545;}");
        out.println(".card{border:none;border-radius:16px;box-shadow:0 4px 12px rgba(0,0,0,0.08);}");
        out.println(".table thead{background:#dc3545;color:white;}");
        out.println(".badge-trangthai{font-size:14px;padding:8px 12px;border-radius:20px;}");
        out.println(".page-title{font-weight:700;color:#333;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<nav class='navbar navbar-expand-lg navbar-custom mb-4'>");
        out.println("<div class='container py-2'>");

        out.println("<a class='brand-text' href='TrangChu'>");
        out.println("<i class='bi bi-bag-heart-fill me-2'></i>Quản Lý Đặt Thức Ăn");
        out.println("</a>");

        out.println("<div class='d-flex align-items-center gap-2 flex-wrap'>");

        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm rounded-pill px-3'>");
        out.println("<i class='bi bi-house-door-fill me-1'></i>Trang chủ");
        out.println("</a>");

        out.println("<a href='HoaDonDaDat' class='btn invoice-btn btn-sm me-2'>");
        out.println("<i class='bi bi-receipt-cutoff me-1'></i>Hóa đơn đã đặt");
        out.println("</a>");

        out.println("<a href='XemGioHang' class='btn btn-danger btn-sm cart-btn'>");
        out.println("<i class='bi bi-cart3 me-1'></i>Giỏ hàng");

        if (tongSoLuongTrongGio > 0) {
            out.println("<span class='cart-badge'>" + tongSoLuongTrongGio + "</span>");
        }

        out.println("</a>");

        out.println("<span class='welcome-text ms-2'>Xin chào, <b>" + nd.getHoTen() + "</b></span>");

        out.println("<a href='DangXuat' class='btn btn-outline-secondary btn-sm rounded-pill px-3 ms-2'>");
        out.println("<i class='bi bi-box-arrow-right me-1'></i>Đăng xuất");
        out.println("</a>");

        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");
        out.println("<div class='card p-4'>");
        out.println("<h3 class='mb-4 text-center page-title'><i class='bi bi-receipt-cutoff'></i> Hóa đơn đã đặt</h3>");

        if (dsHoaDon == null || dsHoaDon.isEmpty()) {
            out.println("<div class='alert alert-warning text-center'>Bạn chưa có hóa đơn nào.</div>");
        } else {
            out.println("<div class='table-responsive'>");
            out.println("<table class='table table-bordered table-hover align-middle text-center'>");

            out.println("<thead><tr>");
            out.println("<th>Mã hóa đơn</th>");
            out.println("<th>Ngày đặt</th>");
            out.println("<th>Tổng số lượng</th>");
            out.println("<th>Tổng tiền</th>");
            out.println("<th>Trạng thái</th>");
            out.println("<th>Thao tác</th>");
            out.println("</tr></thead><tbody>");

            for (HoaDon hd : dsHoaDon) {

                int tongSoLuong = 0;
                double tongTien = 0;

                try {
                    tongSoLuong = Integer.parseInt(
                            target.path("rest").path("hoadon")
                                    .path("TongSoLuongHoaDon")
                                    .path(String.valueOf(hd.getId()))
                                    .request().get(String.class)
                    );

                    tongTien = Double.parseDouble(
                            target.path("rest").path("hoadon")
                                    .path("TongTienHoaDon")
                                    .path(String.valueOf(hd.getId()))
                                    .request().get(String.class)
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String trangThai = hd.getTrangThai();
                String mau = "bg-secondary";
                String hienThi = trangThai;

                if ("dang_xu_ly".equalsIgnoreCase(trangThai)) {
                    mau = "bg-warning text-dark";
                    hienThi = "Đang xử lý";
                } else if ("thanh_cong".equalsIgnoreCase(trangThai)) {
                    mau = "bg-success";
                    hienThi = "Thành công";
                } else if ("huy".equalsIgnoreCase(trangThai)) {
                    mau = "bg-danger";
                    hienThi = "Hủy";
                }

                out.println("<tr>");
                out.println("<td><b>#" + hd.getId() + "</b></td>");
                out.println("<td>" + hd.getNgayDat() + "</td>");
                out.println("<td>" + tongSoLuong + "</td>");
                out.println("<td class='text-danger fw-bold'>" + tongTien + "</td>");
                out.println("<td><span class='badge " + mau + " badge-trangthai'>" + hienThi + "</span></td>");
                out.println("<td><a href='ChiTietHoaDon?id=" + hd.getId() + "' class='btn btn-sm btn-primary'><i class='bi bi-eye'></i> Xem</a></td>");
                out.println("</tr>");
            }

            out.println("</tbody></table></div>");
        }

        out.println("<div class='text-center mt-3'>");
        out.println("<a href='DanhSachMonAn' class='btn btn-danger'>Tiếp tục đặt món</a>");
        out.println("</div>");

        out.println("</div></div>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}