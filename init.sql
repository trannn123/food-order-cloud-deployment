-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: mysql
-- Thời gian đã tạo: Th3 30, 2026 lúc 09:55 AM
-- Phiên bản máy phục vụ: 8.0.45
-- Phiên bản PHP: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ThucAnNhanhdb`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `id` int NOT NULL,
  `hoa_don_id` int DEFAULT NULL,
  `mon_an_id` int DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `don_gia_tai_thoi_diem_tao_hoa_don` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `id` int NOT NULL,
  `nguoi_dung_id` int DEFAULT NULL,
  `ngay_dat` datetime DEFAULT CURRENT_TIMESTAMP,
  `trang_thai` enum('dang_xu_ly','thanh_cong','huy') COLLATE utf8mb4_unicode_ci DEFAULT 'dang_xu_ly'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `monan`
--

CREATE TABLE `monan` (
  `id` int NOT NULL,
  `ten_mon` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gia` decimal(10,2) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `trang_thai` enum('con','het') COLLATE utf8mb4_general_ci DEFAULT 'con',
  `mo_ta` text COLLATE utf8mb4_general_ci,
  `ngay_tao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `monan`
--

INSERT INTO `monan` (`id`, `ten_mon`, `gia`, `so_luong`, `trang_thai`, `mo_ta`, `ngay_tao`) VALUES
(10, 'Bún bò Huế', 48000.00, 20, 'con', 'Bún bò vị cay nhẹ, nước dùng thơm mùi sả.', '2026-03-15 12:29:08'),
(11, 'Bánh mì thịt nướng', 25000.00, 40, 'con', 'Bánh mì giòn, kẹp thịt nướng và rau sống.', '2026-03-15 12:29:08'),
(12, 'Gỏi cuốn tôm thịt dac biet', 20000.00, 3, 'con', 'Gỏi cuốn tươi với tôm, thịt, bún và rau sống.', '2026-03-15 12:29:08'),
(13, 'Bún chả Hà Nội', 90000.00, 30, 'con', 'Bún ăn kèm chả nướng và nước mắm chua ngọt.', '2026-03-15 12:29:08'),
(14, 'Hủ tiếu Nam Vang', 90000.00, 30, 'con', 'Hủ tiếu nước với tôm, thịt bằm và trứng cút.', '2026-03-15 12:29:08'),
(15, 'Mì xào hải sản', 60000.00, 10, 'con', 'Mì xào cùng tôm, mực và rau củ tươi.', '2026-03-15 12:29:08'),
(16, 'Cơm chiên hải sản', 58000.00, 15, 'con', 'Cơm chiên vàng đều với tôm, mực và cà rốt.', '2026-03-15 12:29:08'),
(17, 'Lẩu thái hải sản', 180000.00, 10, 'con', 'Lẩu thái chua cay với nhiều loại hải sản tươi.', '2026-03-15 12:29:08'),
(18, 'Gà rán giòn cay', 70000.00, 24, 'con', 'Gà rán lớp vỏ giòn, vị cay hấp dẫn.', '2026-03-15 12:29:08'),
(19, 'Pizza xúc xích phô mai', 120000.00, 11, 'con', 'Pizza đế mỏng phủ xúc xích và phô mai béo ngậy.', '2026-03-15 12:29:08'),
(20, 'Hamburger bò phô mai', 65000.00, 20, 'con', 'Burger bò kèm phô mai, rau xà lách và sốt đặc biệt.', '2026-03-15 12:29:08'),
(21, 'Trà sữa trân châu đường đen', 35000.00, 49, 'con', 'Trà sữa thơm béo với trân châu dai mềm.', '2026-03-15 12:29:08'),
(22, 'Nước cam ép', 25000.00, 45, 'con', 'Nước cam tươi nguyên chất, mát lạnh và giàu vitamin C.', '2026-03-15 12:29:08');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguoidung`
--

CREATE TABLE `nguoidung` (
  `id` int NOT NULL,
  `ten_dang_nhap` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `mat_khau` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `ho_ten` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `so_dien_thoai` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dia_chi` text COLLATE utf8mb4_general_ci,
  `vai_tro` enum('khachhang','nhanvien') COLLATE utf8mb4_general_ci DEFAULT 'khachhang',
  `trang_thai` enum('hoatdong','khoa') COLLATE utf8mb4_general_ci DEFAULT 'hoatdong',
  `ngay_tao` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nguoidung`
--

INSERT INTO `nguoidung` (`id`, `ten_dang_nhap`, `mat_khau`, `ho_ten`, `email`, `so_dien_thoai`, `dia_chi`, `vai_tro`, `trang_thai`, `ngay_tao`) VALUES
(9, 'admin', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Bao Tran', 'tranb2204974@student.ctu.edu.vn', '1', '1234', 'nhanvien', 'hoatdong', '2026-03-13 09:02:56');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mon_an_id` (`mon_an_id`),
  ADD KEY `chitiethoadon_ibfk_1` (`hoa_don_id`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nguoi_dung_id` (`nguoi_dung_id`);

--
-- Chỉ mục cho bảng `monan`
--
ALTER TABLE `monan`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ten_dang_nhap` (`ten_dang_nhap`),
  ADD UNIQUE KEY `ten_dang_nhap_2` (`ten_dang_nhap`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT cho bảng `monan`
--
ALTER TABLE `monan`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Ràng buộc đối với các bảng kết xuất
--

--
-- Ràng buộc cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`hoa_don_id`) REFERENCES `hoadon` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`mon_an_id`) REFERENCES `monan` (`id`);

--
-- Ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`nguoi_dung_id`) REFERENCES `nguoidung` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
