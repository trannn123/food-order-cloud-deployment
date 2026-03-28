package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import cit.ctu.QLDTAN.NguoiDung;
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

        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Quản lý tài khoản</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".custom-navbar{background:#fff;padding:18px 0;border-bottom:1px solid #eee;}");
        out.println(".navbar-brand-custom{font-size:22px;font-weight:700;color:#ff6b2c;text-decoration:none;display:flex;align-items:center;gap:10px;}");
        out.println(".navbar-brand-custom:hover{color:#ff6b2c;}");
        out.println(".nav-pill{display:inline-flex;align-items:center;gap:6px;padding:10px 18px;border-radius:999px;font-size:15px;font-weight:500;text-decoration:none;transition:all .2s ease;}");
        out.println(".nav-outline-red{border:1px solid #f1b0b7;color:#dc3545;background:#fff;}");
        out.println(".nav-outline-red:hover{background:#fff5f5;color:#dc3545;}");
        out.println(".nav-solid-red{border:1px solid #dc3545;background:#dc3545;color:#fff;}");
        out.println(".nav-solid-red:hover{background:#bb2d3b;color:#fff;}");
        out.println(".nav-outline-gray{border:1px solid #9aa4af;color:#6c757d;background:#fff;}");
        out.println(".nav-outline-gray:hover{background:#f8f9fa;color:#495057;}");
        out.println(".nav-hello{color:#dc3545;font-size:15px;font-weight:600;white-space:nowrap;}");
        out.println(".card-box{background:white;border-radius:14px;border:1px solid #eee;transition:all .25s;cursor:pointer;width:100%;height:130px;display:flex;flex-direction:column;justify-content:center;align-items:center;}");
        out.println(".card-box:hover{transform:translateY(-5px);box-shadow:0 10px 25px rgba(0,0,0,0.08);border-color:#ff6b2c;}");
        out.println(".icon-box{font-size:34px;color:#ff6b2c;}");
        out.println(".row .col-md-4{display:flex;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<nav class='custom-navbar'>");
        out.println("<div class='container d-flex justify-content-between align-items-center'>");
        out.println("<a href='TrangChu' class='navbar-brand-custom'>");
        out.println("<i class='bi bi-bag-heart-fill'></i>");
        out.println("<span>Quản Lý Đặt Thức Ăn</span>");
        out.println("</a>");

        out.println("<div class='d-flex align-items-center gap-2'>");
        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm rounded-pill px-3'>");
        out.println("<i class='bi bi-house-door-fill me-1'></i>Trang chủ");
        out.println("</a>");

        if (nd != null) {
            out.println("<span class='nav-hello ms-2'>Xin chào, <b>" + nd.getHoTen() + "</b></span>");
            out.println("<a href='DangXuat' class='btn btn-outline-secondary btn-sm rounded-pill px-3 ms-2'>");
            out.println("<i class='bi bi-box-arrow-right me-1'></i>Đăng xuất");
            out.println("</a>");
        }

        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container d-flex justify-content-center align-items-center' style='min-height:80vh;'>");
        out.println("<div class='bg-white p-5 rounded shadow' style='max-width:650px;width:100%;'>");
        out.println("<h4 class='text-center mb-4 fw-bold' style='color:#ff6b2c;'>Quản lý tài khoản</h4>");
        out.println("<div class='row g-4'>");

        out.println("<div class='col-md-4'>");
        out.println("<a href='XemThongTin' class='text-decoration-none text-dark d-block w-100'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-person'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Xem thông tin</div>");
        out.println("</div>");
        out.println("</a>");
        out.println("</div>");

        out.println("<div class='col-md-4'>");
        out.println("<a href='CapNhatThongTin' class='text-decoration-none text-dark d-block w-100'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-pencil'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Chỉnh sửa</div>");
        out.println("</div>");
        out.println("</a>");
        out.println("</div>");

        out.println("<div class='col-md-4'>");
        out.println("<a href='DoiMatKhau' class='text-decoration-none text-dark d-block w-100'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-key'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Đổi mật khẩu</div>");
        out.println("</div>");
        out.println("</a>");
        out.println("</div>");

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
