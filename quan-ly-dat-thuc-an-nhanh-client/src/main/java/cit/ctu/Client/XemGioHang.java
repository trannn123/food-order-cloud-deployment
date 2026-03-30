package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cit.ctu.QLDTAN.ItemGioHang;
/**
 * Servlet implementation class XemGioHang
 */
@WebServlet("/XemGioHang")
public class XemGioHang extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XemGioHang() {
        super();
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");
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
        out.println("body{background:linear-gradient(135deg,#fff8f5,#fff);font-family:system-ui,-apple-system,'Segoe UI',sans-serif;}");
        out.println(".cart-wrapper{max-width:1100px;margin:40px auto;}");
        out.println(".page-card{background:#fff;border:none;border-radius:22px;box-shadow:0 10px 30px rgba(0,0,0,0.08);overflow:hidden;}");
        out.println(".page-header{background:linear-gradient(135deg,#ff6b2c,#ff8a50);color:#fff;padding:24px 28px;}");
        out.println(".page-title{font-size:1.8rem;font-weight:700;margin:0;}");
        out.println(".page-subtitle{opacity:.95;margin-top:6px;margin-bottom:0;}");
        out.println(".cart-badge{display:inline-flex;align-items:center;justify-content:center;background:#fff;color:#dc3545;border-radius:999px;padding:6px 12px;font-weight:700;font-size:.95rem;margin-left:10px;}");
        out.println(".table thead th{background:#fff3ed;color:#dc3545;border-bottom:none;font-weight:700;vertical-align:middle;}");
        out.println(".table td{vertical-align:middle;}");
        out.println(".food-name{font-weight:700;color:#212529;}");
        out.println(".price-text{font-weight:600;color:#dc3545;}");
        out.println(".qty-control{display:flex;align-items:center;justify-content:center;gap:8px;}");
        out.println(".qty-btn{width:36px;height:36px;border:none;border-radius:10px;font-size:18px;font-weight:700;display:flex;align-items:center;justify-content:center;text-decoration:none;}");
        out.println(".btn-minus{background:#ffe5e5;color:#dc3545;}");
        out.println(".btn-minus:hover{background:#ffd4d4;color:#bb2d3b;}");
        out.println(".btn-plus{background:#ff6b2c;color:#fff;}");
        out.println(".btn-plus:hover{background:#e85a1f;color:#fff;}");
        out.println(".qty-number{min-width:42px;height:36px;display:flex;align-items:center;justify-content:center;background:#fff3cd;border:1px solid #ffe69c;border-radius:10px;font-weight:700;color:#856404;}");
        out.println(".btn-remove{border-radius:10px;}");
        out.println(".summary-box{background:#fff8f5;border:1px solid #ffe2d3;border-radius:18px;padding:20px;}");
        out.println(".summary-title{font-weight:700;color:#dc3545;margin-bottom:12px;}");
        out.println(".total-money{font-size:1.5rem;font-weight:800;color:#dc3545;}");
        out.println(".empty-box{padding:50px 20px;text-align:center;}");
        out.println(".empty-icon{font-size:64px;color:#ff6b2c;}");
        out.println(".action-btn{border-radius:12px;font-weight:600;padding:10px 18px;}");
        out.println(".invoice-btn{background:linear-gradient(135deg,#198754,#20c997);color:#fff;border:none;border-radius:12px;font-weight:600;padding:10px 18px;box-shadow:0 4px 12px rgba(25,135,84,.25);transition:all .25s ease;text-decoration:none;display:inline-block;text-align:center;}");
        out.println(".invoice-btn:hover{background:linear-gradient(135deg,#157347,#1aa179);color:#fff;transform:translateY(-2px);box-shadow:0 8px 18px rgba(25,135,84,.35);}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container cart-wrapper'>");
        out.println("<div class='page-card'>");
        out.println("<div class='page-header d-flex flex-wrap justify-content-between align-items-center'>");
        out.println("<div>");
        out.println("<h2 class='page-title'><i class='bi bi-cart3 me-2'></i>Giỏ hàng của bạn");
        if (tongSoLuong > 0) {
            out.println("<span class='cart-badge'>" + tongSoLuong + " món</span>");
        }
        out.println("</h2>");
        out.println("<p class='page-subtitle'>Kiểm tra món ăn và điều chỉnh số lượng trước khi đặt</p>");
        out.println("</div>");
        out.println("<div class='mt-2 mt-md-0'>");
        out.println("<a href='DanhSachMonAn' class='btn btn-light action-btn'><i class='bi bi-arrow-left me-1'></i>Tiếp tục mua</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='p-4'>");

        if (cart == null || cart.isEmpty()) {
            out.println("<div class='empty-box'>");
            out.println("<div class='empty-icon'><i class='bi bi-bag-x'></i></div>");
            out.println("<h4 class='mt-3 fw-bold'>Giỏ hàng đang trống</h4>");
            out.println("<p class='text-muted'>Bạn chưa thêm món ăn nào. Hãy chọn món yêu thích nhé.</p>");
            out.println("<a href='DanhSachMonAn' class='btn btn-danger action-btn'>");
            out.println("<i class='bi bi-grid-fill me-1'></i>Xem danh sách món ăn");
            out.println("</a>");
            out.println("</div>");

        } else {
            out.println("<div class='table-responsive mb-4'>");
            out.println("<table class='table align-middle'>");

            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Tên món</th>");
            out.println("<th>Đơn giá</th>");
            out.println("<th class='text-center'>Số lượng</th>");
            out.println("<th>Thành tiền</th>");
            out.println("<th class='text-center'>Thao tác</th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");

            for (ItemGioHang g : cart) {
                out.println("<tr>");
                out.println("<td>");
                out.println("<div class='food-name'>" + g.getMon().getTenMon() + "</div>");
                out.println("</td>");

                out.println("<td class='price-text'>" + vnd.format(g.getMon().getGia()) + "</td>");

                out.println("<td class='text-center'>");
                out.println("<div class='qty-control'>");
                out.println("<a href='GiamGioHang?id=" + g.getMon().getId() + "&from=cart' class='qty-btn btn-minus'>-</a>");
                out.println("<div class='qty-number'>" + g.getSoLuong() + "</div>");
                if (g.getSoLuong() < g.getMon().getSoLuong()) {
                    out.println("<a href='ThemGioHang?id=" + g.getMon().getId() + "&from=cart' class='qty-btn btn-plus'>+</a>");
                } else {
                    out.println("<span class='qty-btn btn-plus' style='opacity:0.5; pointer-events:none;'>+</span>");
                }
                out.println("</div>");
                out.println("</td>");
                out.println("<td class='price-text'>" + vnd.format(g.getThanhTien()) + "</td>");

                out.println("<td class='text-center'>");
                out.println("<a href='XoaGioHang?id=" + g.getMon().getId() + "' class='btn btn-outline-danger btn-sm btn-remove'>");
                out.println("<i class='bi bi-trash3'></i> Xóa");
                out.println("</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");

            out.println("<div class='row g-3'>");
            out.println("<div class='col-md-6'>");
            out.println("<div class='summary-box h-100'>");
            out.println("<div class='summary-title'><i class='bi bi-receipt me-2'></i>Tóm tắt đơn hàng</div>");
            out.println("<p class='mb-2'>Tổng số lượng: <b>" + tongSoLuong + "</b></p>");
            out.println("<p class='mb-0'>Tổng thanh toán:</p>");
            out.println("<div class='total-money'>" + vnd.format(tongTien) + "</div>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='col-md-6'>");
            out.println("<div class='summary-box h-100 d-flex flex-column justify-content-center gap-2'>");

            out.println("<a href='DatMon' class='btn btn-success action-btn'>");
            out.println("<i class='bi bi-bag-check-fill me-1'></i>Đặt món");
            out.println("</a>");

            out.println("<a href='HoaDonDaDat' class='invoice-btn'>");
            out.println("<i class='bi bi-receipt-cutoff me-1'></i>Xem danh sách hóa đơn");
            out.println("</a>");

            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
        }
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
