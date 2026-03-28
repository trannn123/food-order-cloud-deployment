package cit.ctu;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hoadon")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HoaDonWS {

    IHoaDon service = new HoaDonImpl();

    @POST
    @Path("/TaoHoaDon")
    public Response taoHoaDon(HoaDon hd) {
        int id = service.taoHoaDon(hd);
        if (id > 0) {
            return Response.ok(id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Tạo hóa đơn thất bại")
                .build();
    }

    @POST
    @Path("/ThemChiTietHoaDon")
    public Response themChiTietHoaDon(ChiTietHoaDon ct) {
        boolean kq = service.themChiTietHoaDon(ct);
        if (kq) {
            return Response.ok("Thêm chi tiết hóa đơn thành công").build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Thêm chi tiết thất bại")
                .build();
    }

    @GET
    @Path("/LayDanhSachHoaDon")
    public Response layDanhSachHoaDon() {
        List<HoaDon> ds = service.layDanhSachHoaDon();

        return ds != null
                ? Response.ok(ds).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/LayChiTietHoaDon/{id}")
    public Response layChiTietHoaDon(@PathParam("id") int id) {
        List<ChiTietHoaDon> ds = service.layChiTietHoaDon(id);

        return ds != null
                ? Response.ok(ds).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("/LayHoaDonTheoNguoiDung/{nguoiDungId}")
    public Response layHoaDonTheoNguoiDung(@PathParam("nguoiDungId") int nguoiDungId) {
        List<HoaDon> ds = service.layHoaDonTheoNguoiDung(nguoiDungId);

        return ds != null
                ? Response.ok(ds).build()
                : Response.status(Response.Status.BAD_REQUEST)
                        .entity("Không lấy được hóa đơn theo người dùng")
                        .build();
    }
    
    @PUT
    @Path("/CapNhatSoLuong")
    @Consumes(MediaType.TEXT_PLAIN) 
    @Produces(MediaType.TEXT_PLAIN)
    public Response capNhatSoLuong(
            @QueryParam("chiTietHoaDonId") int idChiTietHoaDon,
            @QueryParam("monAnId") int monAnId,
            @QueryParam("soLuong") int soLuong) {
        boolean result = service.capNhatSoLuongMonAnTrongChiTietHoaDon(idChiTietHoaDon, monAnId, soLuong);

        if (result) {
            return Response.ok("Cập nhật thành công").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cập nhật thất bại hoặc vượt quá tồn kho")
                    .build();
        }
    }
    
    @PUT
    @Path("/CapNhatTrangThaiHoaDon")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response capNhatTrangThaiHoaDon(
            @QueryParam("hoaDonId") int hoaDonId,
            @QueryParam("trangThai") String trangThai) {
        boolean result = service.capNhatTrangThaiHoaDon(hoaDonId, trangThai);

        if (result) {
            return Response.ok("Cập nhật trạng thái hóa đơn thành công").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cập nhật trạng thái thất bại")
                    .build();
        }
    }
    
    @DELETE
    @Path("/XoaHoaDon/{id}")
    public Response xoaHoaDon(@PathParam("id") int hoaDonId) {
        boolean result = service.xoaHoaDon(hoaDonId);
        if (result) {
            return Response.ok("Đã xóa hóa đơn #" + hoaDonId).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Hóa đơn không tồn tại").build();
        }
    }

    @DELETE
    @Path("/XoaChiTietHoaDon/{chiTietId}")
    public Response xoaChiTietHoaDon(@PathParam("chiTietId") int chiTietId) {
        boolean result = service.xoaChiTietHoaDon(chiTietId);
        if (result) {
            return Response.ok("Đã xóa chi tiết hóa đơn #" + chiTietId).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Chi tiết hóa đơn không tồn tại").build();
        }
    }
    
    @GET
    @Path("/TongTienHoaDon/{id}")
    public Response tinhTongTienHoaDon(
            @PathParam("id") int id) {
        double tong =
                service.tinhTongTienHoaDon(id);
        return Response
                .ok(String.valueOf(tong))
                .build();
    }
    
    @GET
    @Path("/TongSoLuongHoaDon/{id}")
    public Response tinhTongSoLuongHoaDon(
            @PathParam("id") int id) {
        int tong =
                service.tinhTongSoLuongHoaDon(id);
        return Response
                .ok(String.valueOf(tong))
                .build();
    }
}