package cit.ctu.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import cit.ctu.QLDTAN.NguoiDung;
import cit.ctu.QLDTAN.ItemGioHang;
/**
 * Servlet implementation class QuanLyNguoiDung
 */
@WebServlet("/QuanLyNguoiDung")
public class QuanLyNguoiDung extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuanLyNguoiDung() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");
        int tongSoLuongTrongGio = 0;
        if (cart != null) {
            for (ItemGioHang g : cart) {
                tongSoLuongTrongGio += g.getSoLuong();
            }
        }

        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Quản lý tài khoản</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".navbar-custom{background:#fff;box-shadow:0 2px 12px rgba(0,0,0,0.06);}");
        out.println(".brand-text{color:#ff6b2c;font-size:1.4rem;font-weight:700;text-decoration:none;}");
        out.println(".cart-btn{position:relative;border-radius:12px;font-weight:600;padding-left:16px;padding-right:16px;}");
        out.println(".cart-badge{position:absolute;top:-8px;right:-8px;background:#dc3545;color:#fff;border-radius:999px;padding:4px 8px;font-size:12px;font-weight:700;min-width:24px;text-align:center;}");
        out.println(".welcome-text{color:#dc3545;font-weight:500;}");
        out.println(".invoice-btn{background:#fff0f0;color:#dc3545;border:1px solid #f5c2c7;border-radius:999px;font-weight:600;padding:6px 14px;}");
        out.println(".invoice-btn:hover{background:#dc3545;color:#fff;border-color:#dc3545;}");

        out.println(".card-box{background:white;border-radius:14px;border:1px solid #eee;transition:all .25s;cursor:pointer;width:100%;height:130px;display:flex;flex-direction:column;justify-content:center;align-items:center;}");
        out.println(".card-box:hover{transform:translateY(-5px);box-shadow:0 10px 25px rgba(0,0,0,0.08);border-color:#ff6b2c;}");
        out.println(".icon-box{font-size:34px;color:#ff6b2c;}");
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

        if(!nd.getVaiTro().equals("nhanvien")){
            out.println("<a href='HoaDonDaDat' class='btn invoice-btn btn-sm me-2'>");
            out.println("<i class='bi bi-receipt-cutoff me-1'></i>Hóa đơn đã đặt");
            out.println("</a>");

            out.println("<a href='XemGioHang' class='btn btn-danger btn-sm cart-btn'>");
            out.println("<i class='bi bi-cart3 me-1'></i>Giỏ hàng");

            if (tongSoLuongTrongGio > 0) {
                out.println("<span class='cart-badge'>" + tongSoLuongTrongGio + "</span>");
            }

            out.println("</a>");
        }

        out.println("<span class='welcome-text ms-2'>Xin chào, <b>" + nd.getHoTen() + "</b></span>");

        out.println("<a href='DangXuat' class='btn btn-outline-secondary btn-sm rounded-pill px-3 ms-2'>");
        out.println("<i class='bi bi-box-arrow-right me-1'></i>Đăng xuất");
        out.println("</a>");

        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");


        out.println("<div class='container d-flex justify-content-center align-items-center' style='min-height:80vh;'>");
        out.println("<div class='bg-white p-5 rounded shadow' style='max-width:650px;width:100%;'>");

        out.println("<h4 class='text-center mb-4 fw-bold' style='color:#ff6b2c;'>Quản lý tài khoản</h4>");

        out.println("<div class='row g-4'>");

        out.println("<div class='col-md-4'>");
        out.println("<a href='XemThongTin' class='text-decoration-none text-dark'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-person'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Xem thông tin</div>");
        out.println("</div></a></div>");

        out.println("<div class='col-md-4'>");
        out.println("<a href='CapNhatThongTin' class='text-decoration-none text-dark'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-pencil'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Chỉnh sửa</div>");
        out.println("</div></a></div>");

        out.println("<div class='col-md-4'>");
        out.println("<a href='DoiMatKhau' class='text-decoration-none text-dark'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-key'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Đổi mật khẩu</div>");
        out.println("</div></a></div>");

        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}