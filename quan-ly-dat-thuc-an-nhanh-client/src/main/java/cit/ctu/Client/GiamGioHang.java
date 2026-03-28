package cit.ctu.Client;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import cit.ctu.QLDTAN.ItemGioHang;
/**
 * Servlet implementation class GiamGioHang
 */
@WebServlet("/GiamGioHang")
public class GiamGioHang extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiamGioHang() {
        super();
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String from = request.getParameter("from");
        
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

        HttpSession session = request.getSession();
        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");

        if (cart != null) {
            Iterator<ItemGioHang> iterator = cart.iterator();
            while (iterator.hasNext()) {
                ItemGioHang g = iterator.next();
                if (g.getMon() != null && g.getMon().getId() == id) {
                    if (g.getSoLuong() > 1) {
                        g.setSoLuong(g.getSoLuong() - 1);
                    } else {
                        iterator.remove();
                    }
                    break;
                }
            }

            session.setAttribute("cart", cart);
        }
        if ("cart".equals(from)) {
            response.sendRedirect("XemGioHang");
        } else {
            response.sendRedirect("DanhSachMonAn");
        }
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
