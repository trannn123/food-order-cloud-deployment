package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_CHUNG_THUC;
import static cit.ctu.Constants.BASE_API_URL_NGUOI_DUNG;

/**
 * Servlet implementation class DangNhap
 */
@WebServlet("/DangNhap")
public class DangNhap extends HttpServlet {
	private static final long serialVersionUID = 1L;    
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri_CHUNGTHUC =
            UriBuilder.fromUri(BASE_API_URL_CHUNG_THUC).build();
	private static final URI uri_NGUOIDUNG =
            UriBuilder.fromUri(BASE_API_URL_NGUOI_DUNG).build();
    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target_ChungThuc = client.target(uri_CHUNGTHUC);
    WebTarget target_NguoiDung = client.target(uri_NGUOIDUNG);
    public DangNhap() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Đăng nhập</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{");
        out.println("background:#f8f9fa;");
        out.println("height:100vh;");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("justify-content:center;");
        out.println("font-family:system-ui;");
        out.println("}");
        out.println(".login-card{");
        out.println("width:400px;");
        out.println("border:none;");
        out.println("border-radius:5px;");
        out.println("box-shadow:0 10px 25px rgba(0,0,0,0.08);");
        out.println("}");
        out.println(".title{");
        out.println("color:#ff6b2c;");
        out.println("font-weight:700;");
        out.println("}");
        out.println(".btn-main{");
        out.println("background:#ff6b2c;");
        out.println("color:white;");
        out.println("border:none;");
        out.println("}");
        out.println(".btn-main:hover{");
        out.println("background:#ff5722;");
        out.println("}");
        out.println(".form-control:focus{");
        out.println("border-color:#ff6b2c;");
        out.println("box-shadow:0 0 0 0.1rem rgba(255,107,44,0.25);");
        out.println("}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='card login-card p-5'>");
        out.println("<h3 class='text-center title mb-4'>Đăng nhập</h3>");
        String error = (String) request.getAttribute("error");
        if(error != null){
            out.println("<div class='alert alert-danger text-center'>");
            out.println(error);
            out.println("</div>");
        }
        out.println("<form method='post'>");
        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Tên đăng nhập</label>");
        out.println("<input name='tenDangNhap' class='form-control'>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Mật khẩu</label>");
        out.println("<input type='password' name='matKhau' class='form-control'>");
        out.println("</div>");

        out.println("<button class='btn btn-main w-100'>Đăng nhập</button>");
        out.println("</form>");
        
        out.println("<div class='text-center mt-3'>");
        out.println("Chưa có tài khoản? <a href='DangKy'>Đăng ký</a>");
        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");
        
        String error = null;
        if (tenDangNhap != null && matKhau != null) {
            try {
            	NguoiDung nd = new NguoiDung();
            	nd.setTenDangNhap(tenDangNhap);
            	nd.setMatKhau(matKhau);

            	NguoiDung result = target_ChungThuc
                        .path("rest")
                        .path("chungthuc")
                        .path("DangNhap")
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(nd, MediaType.APPLICATION_JSON), NguoiDung.class);
            	
                if (result != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", result); 
                    response.sendRedirect("TrangChu");
                    return;
                }
                else {
                    error = "Sai tài khoản hoặc mật khẩu!";
                }
            }
            catch (Exception e) {
            	error = "Sai tài khoản hoặc mật khẩu!";
            }
            request.setAttribute("error", error);
            doGet(request, response);
        }
        else {
            error = "Vui lòng nhập đầy đủ thông tin!";
            request.setAttribute("error", error);
            doGet(request, response);
            return;
        }
	}
}
