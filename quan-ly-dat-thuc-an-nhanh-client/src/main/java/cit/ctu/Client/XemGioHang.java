package cit.ctu.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import cit.ctu.QLDTAN.ItemGioHang;
import cit.ctu.QLDTAN.NguoiDung;

@WebServlet("/XemGioHang")
public class XemGioHang extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        NumberFormat vnd = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        int tongSoLuong = 0;
        double tongTien = 0;

        if (cart != null) {
            for (ItemGioHang g : cart) {
                tongSoLuong += g.getSoLuong();
                tongTien += g.getThanhTien();
            }
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<title>Giỏ hàng</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:linear-gradient(135deg,#fff8f5,#fff);font-family:system-ui;}");
        out.println(".navbar-custom{background:#fff;box-shadow:0 2px 12px rgba(0,0,0,0.06);}");
        out.println(".brand-text{color:#ff6b2c;font-size:1.4rem;font-weight:700;text-decoration:none;}");
        out.println(".cart-btn{position:relative;border-radius:12px;font-weight:600;}");
        out.println(".cart-badge{position:absolute;top:-8px;right:-8px;background:#dc3545;color:#fff;border-radius:999px;padding:4px 8px;font-size:12px;font-weight:700;}");
        out.println(".welcome-text{color:#dc3545;font-weight:500;}");
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

        out.println("<a href='HoaDonDaDat' class='btn btn-outline-danger btn-sm rounded-pill px-3'>");
        out.println("<i class='bi bi-receipt-cutoff me-1'></i>Hóa đơn đã đặt");
        out.println("</a>");

        out.println("<a href='XemGioHang' class='btn btn-danger btn-sm cart-btn'>");
        out.println("<i class='bi bi-cart3 me-1'></i>Giỏ hàng");
        if (tongSoLuong > 0) {
            out.println("<span class='cart-badge'>" + tongSoLuong + "</span>");
        }
        out.println("</a>");

        if (nd != null) {
            out.println("<span class='welcome-text ms-2'>Xin chào, <b>" + nd.getHoTen() + "</b></span>");
            out.println("<a href='DangXuat' class='btn btn-outline-secondary btn-sm rounded-pill px-3 ms-2'>");
            out.println("<i class='bi bi-box-arrow-right me-1'></i>Đăng xuất");
            out.println("</a>");
        }

        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");

        if (cart == null || cart.isEmpty()) {
            out.println("<div class='text-center mt-5'>");
            out.println("<i class='bi bi-bag-x' style='font-size:64px;color:#ff6b2c;'></i>");
            out.println("<h4 class='mt-3 fw-bold'>Giỏ hàng đang trống</h4>");
            out.println("<p class='text-muted'>Bạn chưa thêm món ăn nào.</p>");
            out.println("<a href='DanhSachMonAn' class='btn btn-danger'>Xem món ăn</a>");
            out.println("</div>");
        } else {

            out.println("<div class='table-responsive'>");
            out.println("<table class='table'>");

            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Tên món</th>");
            out.println("<th>Đơn giá</th>");
            out.println("<th>Số lượng</th>");
            out.println("<th>Thành tiền</th>");
            out.println("<th></th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");

            for (ItemGioHang g : cart) {
                out.println("<tr>");
                out.println("<td>" + g.getMon().getTenMon() + "</td>");
                out.println("<td>" + vnd.format(g.getMon().getGia()) + "</td>");
                out.println("<td>" + g.getSoLuong() + "</td>");
                out.println("<td>" + vnd.format(g.getThanhTien()) + "</td>");

                out.println("<td>");
                out.println("<a href='XoaGioHang?id=" + g.getMon().getId() + "' class='btn btn-danger btn-sm'>Xóa</a>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");

            out.println("<h5 class='text-end text-danger fw-bold'>Tổng: " + vnd.format(tongTien) + "</h5>");

            out.println("<div class='text-end mt-3'>");
            out.println("<a href='DatMon' class='btn btn-success'>Đặt món</a>");
            out.println("</div>");
        }

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}