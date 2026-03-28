-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 28, 2026 lúc 03:55 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `thucannhanhdb`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `id` int(11) NOT NULL,
  `hoa_don_id` int(11) DEFAULT NULL,
  `mon_an_id` int(11) DEFAULT NULL,
  `so_luong` int(11) DEFAULT NULL,
  `don_gia_tai_thoi_diem_tao_hoa_don` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`id`, `hoa_don_id`, `mon_an_id`, `so_luong`, `don_gia_tai_thoi_diem_tao_hoa_don`) VALUES
(64, 54, 13, 1, 90000.00),
(65, 54, 12, 4, 30000.00),
(66, 55, 18, 2, 70000.00),
(67, 55, 16, 3, 58000.00),
(68, 55, 19, 1, 120000.00),
(69, 55, 21, 4, 35000.00),
(70, 56, 12, 1, 30000.00),
(71, 57, 12, 3, 30000.00),
(72, 58, 12, 1, 30000.00),
(74, 60, 12, 1, 30000.00),
(75, 61, 12, 3, 15000.00),
(77, 63, 12, 2, 10000.00),
(78, 64, 15, 1, 60000.00),
(79, 65, 12, 3, 10000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `id` int(11) NOT NULL,
  `nguoi_dung_id` int(11) DEFAULT NULL,
  `ngay_dat` datetime DEFAULT current_timestamp(),
  `trang_thai` enum('dang_xu_ly','thanh_cong','huy') DEFAULT 'dang_xu_ly'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`id`, `nguoi_dung_id`, `ngay_dat`, `trang_thai`) VALUES
(54, 17, '2026-03-26 00:52:58', 'dang_xu_ly'),
(55, 17, '2026-03-26 01:01:36', 'huy'),
(56, 14, '2026-03-26 07:20:54', 'dang_xu_ly'),
(57, 14, '2026-03-26 07:38:17', 'dang_xu_ly'),
(58, 14, '2026-03-26 07:40:23', 'dang_xu_ly'),
(59, 14, '2026-03-26 07:56:36', 'thanh_cong'),
(60, 14, '2026-03-26 08:22:24', 'dang_xu_ly'),
(61, 14, '2026-03-26 08:24:05', 'dang_xu_ly'),
(62, 14, '2026-03-26 08:25:16', 'dang_xu_ly'),
(63, 18, '2026-03-26 15:14:13', 'dang_xu_ly'),
(64, 18, '2026-03-26 15:14:21', 'dang_xu_ly'),
(65, 18, '2026-03-26 17:48:10', 'dang_xu_ly'),
(66, 18, '2026-03-26 17:57:21', 'dang_xu_ly');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `monan`
--

CREATE TABLE `monan` (
  `id` int(11) NOT NULL,
  `ten_mon` varchar(200) DEFAULT NULL,
  `gia` decimal(10,2) DEFAULT NULL,
  `so_luong` int(11) DEFAULT NULL,
  `trang_thai` enum('con','het') DEFAULT 'con',
  `mo_ta` text DEFAULT NULL,
  `ngay_tao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `monan`
--

INSERT INTO `monan` (`id`, `ten_mon`, `gia`, `so_luong`, `trang_thai`, `mo_ta`, `ngay_tao`) VALUES
(10, 'Bún bò Huế', 48000.00, 20, 'het', 'Bún bò vị cay nhẹ, nước dùng thơm mùi sả.', '2026-03-15 12:29:08'),
(11, 'Bánh mì thịt nướng', 25000.00, 0, 'het', 'Bánh mì giòn, kẹp thịt nướng và rau sống.', '2026-03-15 12:29:08'),
(12, 'Gỏi cuốn tôm thịt dac biet', 20000.00, 3, 'con', 'Gỏi cuốn tươi với tôm, thịt, bún và rau sống.', '2026-03-15 12:29:08'),
(13, 'Bún chả Hà Nội', 90000.00, 0, 'het', 'Bún ăn kèm chả nướng và nước mắm chua ngọt.', '2026-03-15 12:29:08'),
(14, 'Hủ tiếu Nam Vang', 90000.00, 0, 'het', 'Hủ tiếu nước với tôm, thịt bằm và trứng cút.', '2026-03-15 12:29:08'),
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
  `id` int(11) NOT NULL,
  `ten_dang_nhap` varchar(50) NOT NULL,
  `mat_khau` varchar(255) NOT NULL,
  `ho_ten` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `so_dien_thoai` varchar(20) DEFAULT NULL,
  `dia_chi` text DEFAULT NULL,
  `vai_tro` enum('khachhang','nhanvien') DEFAULT 'khachhang',
  `trang_thai` enum('hoatdong','khoa') DEFAULT 'hoatdong',
  `ngay_tao` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nguoidung`
--

INSERT INTO `nguoidung` (`id`, `ten_dang_nhap`, `mat_khau`, `ho_ten`, `email`, `so_dien_thoai`, `dia_chi`, `vai_tro`, `trang_thai`, `ngay_tao`) VALUES
(9, 'admin', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Bao Tran', 'tranb2204974@student.ctu.edu.vn', '1', '1234', 'nhanvien', 'hoatdong', '2026-03-13 09:02:56'),
(10, 'thuy duong', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 'duong', '11', '111', '111', 'khachhang', 'hoatdong', '2026-03-13 09:52:04'),
(11, 'a', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 'a', 'a', 'a', 'a', 'khachhang', 'hoatdong', '2026-03-13 09:52:48'),
(12, 'btran', '3b2a95838da50e7c8a47d36a54b64f129f2cce683ae77876e5488caf708e8408', 'bao tran', 'tranb2204974@student.ctu.edu.vn', '1234567896', 'aaafffg', 'khachhang', 'hoatdong', '2026-03-15 10:44:48'),
(13, 'BaoTran', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Bao Tran', 'tranb2204974@gmail.com', '1234567896', 'Vinh Long', 'khachhang', 'hoatdong', '2026-03-24 18:52:57'),
(14, 'tendangnhap', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Anh Hao', 'hao@gmail.com', '0777555454', 'Vinh Long', 'khachhang', 'hoatdong', '2026-03-24 21:14:48'),
(15, 'BaoTram', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Bao Tram', 'tranb2204974@student.ctu.edu.vn', '1234567896', 'Cần Thơ', 'khachhang', 'hoatdong', '2026-03-25 12:57:16'),
(16, 'Khach01', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Khach Hang', 'tranb2204974@student.ctu.edu.vn', '1234567896', 'Cần Thơ', 'khachhang', 'hoatdong', '2026-03-25 20:13:51'),
(17, 'anhhao', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'NguyenAnh', 'tranb2204974@student.ctu.edu.vn', '1234567896', 'Vinh Long', 'khachhang', 'hoatdong', '2026-03-25 23:26:36'),
(18, 'Khach02', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'Bao Tran', 'tranb2204974@student.ctu.edu.vn', '0900000001', 'Cần Thơ', 'khachhang', 'hoatdong', '2026-03-26 11:44:52');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `role` enum('customer','staff') DEFAULT 'customer',
  `created_at` datetime DEFAULT NULL,
  `status` enum('blocked','active') DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `fullname`, `email`, `phone`, `address`, `role`, `created_at`, `status`) VALUES
(1, 'admin', '123', 'Administrator', 'admin@gmail.com', NULL, NULL, 'customer', NULL, 'active');

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
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT cho bảng `monan`
--
ALTER TABLE `monan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`hoa_don_id`) REFERENCES `hoadon` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`mon_an_id`) REFERENCES `monan` (`id`);

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`nguoi_dung_id`) REFERENCES `nguoidung` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
