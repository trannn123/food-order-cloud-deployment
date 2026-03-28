package cit.ctu.Client;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cit.ctu.QLDTAN.ItemGioHang;
/**
 * Servlet implementation class XoaGioHang
 */
@WebServlet("/XoaGioHang")
public class XoaGioHang extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XoaGioHang() {
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

        if (session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("XemGioHang");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("XemGioHang");
            return;
        }

        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");

        if (cart != null) {
            Iterator<ItemGioHang> iterator = cart.iterator();

            while (iterator.hasNext()) {
                ItemGioHang g = iterator.next();
                if (g.getMon() != null && g.getMon().getId() == id) {
                    iterator.remove();
                    break;
                }
            }
            session.setAttribute("cart", cart);
        }
        response.sendRedirect("XemGioHang");
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
