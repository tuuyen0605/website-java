-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 17, 2026 at 11:53 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parksmart`
--

--
-- Dumping data for table `gia_ve`
--

INSERT INTO `gia_ve` (`id`, `loai_xe`, `loai_ve`, `gia`) VALUES
(1, 'Xe máy', 'Vé tháng', 150000),
(2, 'Xe máy', 'Vé quý', 405000),
(3, 'Xe máy', 'Vé năm', 1440000),
(4, 'Xe ô tô', 'Vé tháng', 1000000),
(5, 'Xe ô tô', 'Vé quý', 2700000),
(6, 'Xe ô tô', 'Vé năm', 9600000),
(7, 'Xe đạp', 'Vé tháng', 50000),
(8, 'Xe đạp', 'Vé quý', 135000),
(9, 'Xe đạp', 'Vé năm', 480000);

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`id`, `da_doc`, `ngay_nhac`, `noi_dung`, `ticket_id`, `user_id`) VALUES
(3, b'1', '2026-04-28', 'Vé Xe máy của bạn sẽ hết hạn vào ngày 08/05/2026. Còn 10 ngày, hãy gia hạn sớm!', 15, 1),
(4, b'1', '2026-05-03', 'Vé Xe máy của bạn sẽ hết hạn vào ngày 08/05/2026. Chỉ còn 5 ngày!', 15, 1),
(5, b'1', '2026-04-08', 'Bạn đã đăng ký vé Xe máy - Vé tháng tại Nhà để xe số 2 - Cổng Trần Đại Nghĩa, cạnh giảng đường C thành công!', 15, 1),
(6, b'1', '2026-04-28', 'Vé Xe máy của bạn sẽ hết hạn vào ngày 08/05/2026. Còn 10 ngày, hãy gia hạn sớm!', 16, 1),
(7, b'1', '2026-05-03', 'Vé Xe máy của bạn sẽ hết hạn vào ngày 08/05/2026. Chỉ còn 5 ngày!', 16, 1),
(8, b'1', '2026-04-08', 'Bạn đã đăng ký vé Xe máy - Vé tháng tại Nhà để xe số 2 - Cổng Trần Đại Nghĩa, cạnh giảng đường C thành công!', 16, 1),
(9, b'1', '2026-05-05', 'Vé Xe đạp của bạn sẽ hết hạn vào ngày 15/05/2026. Còn 10 ngày, hãy gia hạn sớm!', 17, 1),
(10, b'1', '2026-05-10', 'Vé Xe đạp của bạn sẽ hết hạn vào ngày 15/05/2026. Chỉ còn 5 ngày!', 17, 1),
(11, b'1', '2026-04-15', 'Bạn đã đăng ký vé Xe đạp - Vé tháng tại Nhà để xe số 2 - Cổng Trần Đại Nghĩa, cạnh giảng đường C thành công!', 17, 1);

--
-- Dumping data for table `parking_lots`
--

INSERT INTO `parking_lots` (`id`, `dia_chi`, `gio_dong`, `gio_mo`, `ten`, `trang_thai`, `gia_o_to_gio`, `gia_xe_dap`, `gia_xe_may_sau`, `gia_xe_may_truoc`, `tien_ich`) VALUES
(1, 'Cổng 207 Giải Phóng, cạnh tòa A2 - Đại học Kinh tế Quốc dân', '22:00', '5:00', 'Nhà để xe số 1', 'Mở cửa', 15000, 3000, 7000, 3000, 'mai_che,bao_ve,xe_dap'),
(2, 'Cổng Trần Đại Nghĩa, cạnh giảng đường C - Đại học Kinh tế Quốc dân', '22:00', '5:00', 'Nhà để xe số 2', 'Mở cửa', 15000, 3000, 7000, 3000, 'bao_ve,xe_dap'),
(3, 'Cổng Trần Đại Nghĩa, cạnh giảng đường C - Đại học Kinh tế Quốc dân', '22:00', '5:00', 'Nhà để xe số 4', 'Mở cửa', 15000, 3000, 7000, 3000, 'bao_ve,xe_dap'),
(4, 'Tòa KTX Đại học Kinh tế Quốc dân', '22:00', '5:00', 'Nhà để xe KTX', 'Mở cửa', 15000, 3000, 7000, 3000, 'bao_ve,xe_dap'),
(5, 'Tầng hầm B2 nhà Trung tâm đào tạo, trường Đại học Kinh tế Quốc dân', '22:00', '5:00', 'Hầm để xe B2', 'Mở cửa', 15000, 3000, 7000, 3000, 'mai_che,camera,bao_ve');

--
-- Dumping data for table `system_notifications`
--

INSERT INTO `system_notifications` (`id`, `ngay_tao`, `noi_dung`, `tieu_de`, `admin_id`) VALUES
(3, '2026-04-14 13:46:08.000000', 'Thực hiện theo kế hoạch đào tạo và thông báo số 758/TB-ĐHKTQD ngày 31/03/2026 của Nhà trường, Ban Quản lý Bãi đỗ xe thông báo lịch điều chỉnh hoạt động cụ thể như sau:<div><b>&nbsp;1. Đợt nghỉ Giỗ Tổ Hùng Vương (10/3 Âm lịch)&nbsp;</b></div><div>- Thời gian nghỉ: 02 ngày, từ <b><font color=\"#ea1010\" style=\"background-color: rgb(255, 255, 0);\">Chủ Nhật (26/04/2026) đến hết Thứ Hai (27/04/2026)</font></b>.&nbsp;</div><div><b>2. Hoạt động bãi xe:&nbsp;</b></div><div>- Tạm dừng đăng ký mới/gia hạn vé tháng tại quầy trực tiếp. Hệ thống Website/App vẫn duy trì trạng thái hoạt động để người dùng đăng ký vé.&nbsp;</div><div>- Không nhận trông giữ xe: Trong suốt thời gian nghỉ lễ, bãi xe KHÔNG tiếp nhận trông giữ phương tiện xuyên lễ dưới mọi hình thức (kể cả đối với xe có vé tháng).&nbsp;</div><div>Ban Quản lý Bãi đỗ xe NEU đề nghị các bạn sinh viên và cán bộ giảng viên nắm rõ lịch trình để chủ động trong việc gửi và lấy phương tiện.&nbsp;</div><div>Trân trọng thông báo.</div>', 'Nghỉ lễ Giổ tổ Hùng Vương', 6),
(4, '2026-04-14 13:51:50.000000', '<div><b>1. Thời gian nghỉ lễ</b></div>- Thời gian nghỉ:&nbsp;Từ <font color=\"#f00000\" style=\"background-color: rgb(255, 255, 0);\"><b>30/04 đến hết 03/05 (Thứ Năm đến Chủ Nhật)</b></font>.<div>- Thời gian hoạt động trở lại:<b>&nbsp;<font color=\"#f00000\" style=\"background-color: rgb(255, 255, 0);\">sáng Thứ Hai, ngày 04/05/2026</font>.</b></div><div><b>2. Quy định</b></div><div><div>- Ngưng tiếp nhận xe: Bắt đầu từ 21:00 ngày 29/04/2026, bãi xe sẽ <b>KHÔNG </b>tiếp nhận bất kỳ phương tiện nào vào gửi.&nbsp;</div><div>- Lưu ý: Ban Quản lý không chịu trách nhiệm bảo quản đối với các phương tiện cố tình để lại bãi trong thời gian nghỉ lễ.</div></div><div><b>3. Hoạt động trên Hệ thống Website:</b></div><div>- Đăng ký/Gia hạn: Người dùng vẫn có thể đăng ký vé tháng trực tuyến trên App xuyên suốt kỳ nghỉ.</div><div>- Xử lý dữ liệu: Hệ thống sẽ tự động chốt danh sách xe gia hạn thành công vào lúc 23:59 ngày 03/05 để phục vụ việc ra vào bãi vào sáng ngày hôm sau.</div>', 'Nghỉ lễ 30/4 và 1/5', 6);

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`id`, `bien_so`, `gia`, `loai_ve`, `loai_xe`, `ngay_dang_ky`, `ngay_het_han`, `trang_thai`, `user_id`, `khu_vuc`, `thanh_toan`) VALUES
(15, '29BD-06555', 150000, 'Vé tháng', 'Xe máy', '2026-04-08', '2026-05-08', 'Hoạt động', 1, 'Nhà để xe số 2 - Cổng Trần Đại Nghĩa, cạnh giảng đường C', b'1'),
(16, '29BD-06555', 150000, 'Vé tháng', 'Xe máy', '2026-04-08', '2026-05-08', 'Hoạt động', 1, 'Nhà để xe số 2 - Cổng Trần Đại Nghĩa, cạnh giảng đường C', b'0'),
(17, '', 50000, 'Vé tháng', 'Xe đạp', '2026-04-15', '2026-05-15', 'Hoạt động', 1, 'Nhà để xe số 2 - Cổng Trần Đại Nghĩa, cạnh giảng đường C', b'1');

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `full_name`, `email`, `password`, `role`) VALUES
(1, 'Vũ Tú Uyên', '11247243@st.neu.edu.vn', 'tuuyenvu', 'Sinh viên'),
(2, 'Bùi Thị Thu Trang', '11245296@st.neu.edu.vn', 'thutrangbui', 'Sinh viên'),
(3, 'Lê Hoàng Hải Yến', '11245313@st.neu.edu.vn', 'haiyen', 'Sinh viên'),
(4, 'Dương Phương Anh', '11247378@st.neu.edu.vn', 'phuonganh', 'Sinh viên'),
(5, 'Phạm Thị Thu Trang', '11241842@st.neu.edu.vn', 'pttrang', 'Sinh viên'),
(6, 'Admin', 'admin@st.neu.edu.vn', 'admin123', 'Admin');

