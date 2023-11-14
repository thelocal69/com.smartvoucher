# com.smartvoucher
### The project web site for community !
***
# Hướng dẫn setup môi trường để run project
## Yêu cầu
1. Mysql
2. IDE (Eclipse, Intellij)
3. Maven 3.x.x (sẽ được add sẵn vào Intellij)
4. Java jdk 11
5. Docker(optional)
6. Git
7. DBeaver (thay thế cho Mysql Workbend) [https://dbeaver.io/](https://dbeaver.io/download/)
***
### MYSQL 
- Bước 1: Vào link trang chủ [https://dev.mysql.com](https://dev.mysql.com/downloads/installer/) tải phiên bản 8.0.xx trở lên
và tải phiên bản đày đủ (mysql-installer-community-8.0.xx.msi).
- Bước 2: Mở file installer, bên trong màn hình wizard chọn **Setup Type**. Sau đó chọn **Full** rồi **Next**.
- Bước 3: Đến phần **Check Requirements** chọn **Execute** để cài đặt xong rồi nhấn **Next**.
- Bước 4: ![](https://static.javatpoint.com/mysql/images/installmysql5.png) Hiện ra bản này (nếu có) thì chọn **Yes**.
- Bước 5: Đến phần **Installation** chọn **Execute** chờ rồi **Next**.
- Bước 6: Nhất **Next**.
- Bước 7: Chọn **Standalone MySQL Server/Classic MySQL Replication** rồi chọn **Next**.
- Bước 8: Giữ nguyên mặc định rồi chọn **Next**.
- Bước 9: Chọn **Use strong password...** rồi chọn **Next**.
- Bước 10: Thiết lập **Password** cho dễ nhớ (Không cần phải giống password của người viết) rồi chọn **Next**.
- Bước 11: Để nguyên mặc định rồi chọn **Next**.
- Bước 12: Chọn **Execute** rồi chọn **Finish**.
- Bước 13: Chọn **Next** rồi chọn **Finish**.
- Bước 14: Nhập **Password** đã lập ở **bước 10** Username mặc định là **Root** rồi chọn **Check** sau đó hiện ra tích xanh là OK, chọn **Next**.
- Final: Chọn **Finish**.
***
### IDE(Eclipse, Intellij)
- Đối với Project này thì sẽ xài **Intellij** vì nó sẽ tích hợp hết các sẵn library như **Java JDK, Maven** chỉ cần download
về tự động trong **Intellij**.
- Bước 1: Vào trang chủ [https://www.jetbrains.com](https://www.jetbrains.com/idea/download/?section=windows) nếu có tài khoản sinh viên
thì tải bản **Ultimate** hoặc xài thử 30 ngày, còn không thì tải bản **Comunity** hoặc cr*ck.
- Bước 2: Cài đặt và chọn như hình ![](https://www.tutorialspoint.com/assets/questions/media/644826-1683889081.jpg) rồi chọn **Next**.
- Bước 3: **Install** chờ, chọn **Reboot Now**.
***
### JAVA 11 JDK
- Bước 1: Vào trang chủ [https://www.oracle.com](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html#license-lightbox) chọn (x64 Installer).
- Bước 2: Trong wizard chọn **Next**, đến phần **Destination folder** copy đường dẫn mình lưu file.
- Bước 3: Set biến môi trường, vào khung search của windown nhập **Edit the system environment variables**, rồi chọn **Environment Variables…**.
- Bước 4: Ở phần **System Variables** chọn phần **Path** rồi chọn **Edit**.
- Bước 5: Chọn **New** rồi copy đường dẫn đã lưu vào (đường dẫn mặc định: *C:\Program Files\Java\jdk-11.0.19\bin*) xong chọn **OK**.
- Bước 6: Thêm biến **JAVA_HOME**, ở phần **System Variables** chọn **New**, *variable name =* **JAVA_HOME** *variable value =* *C:\Program Files\Java\jdk-11.0.19* (mặc định) xong chọn **OK**.
- Bước 7: Mở **CMD** or **Terminal** gõ *java version* để hiện thị phiên bản java trong máy.
***
### Lưu ý
- Sau khi **Fork** project về bên git của mình thì **Clone** về máy, mở **Intellij**, khi mở nhớ để ý những dòng thông báo về việc
  **Intellij** sẽ tự động download những thư viện còn thiếu như **Maven** và nhớ cho phép nó thực thi, sau đó hãy run project lên 1 lần,
nếu có lỗi phát sinh vui lòng báo cho team để được hỗ trợ.

------- 
# SmartVoucher

**Validation rule of SmartVoucher BE**

1. Warehouse: 
- Một warehouse chỉ chứa 1 loại discount type. Không cho phép đổi loại discount type 
- Cho phép điều chỉnh tăng capacity của warehouse. Số lượng điều chỉnh tăng = số lượng serial được generate 
VD: WH đang có capacity = 100. Điều chỉnh tăng capacity = 200 -> có thể tạo thêm mới 100 serial và nhập vào WH này 


2. Serial 
- Thời hạn valid của serial dựa vào effective from - to của warehouse. wh_effective_from <=valid_time_of_serial <= wh_effective_to
- Rule generate serial: độ dài serial fix = 10 kí tự, bao gồm A-Z và 0-9. Serial gen random, không được generate serial theo pattern tăng dần để tránh cheating
- Số lượng serial có thể gen: max = capacity của wh
- Đối với wh đã có serial nhập kho, số lượng serial có thể gen = capacity của WH - số lượng serial đã nhập kho. VD: WH có capacity = 100, đã nhập kho: 50 -> số lượng serial có thể gen mới và nhập kho = 50 cái
- Khi generate serial, bắt buộc phải chỉ định warehouse cho serial đó
- Serial là unique trên toàn hệ thống voucher 
- Serial sẽ có các stt new, owned (khi user đã mua), used (user đã dùng), expired (hết hạn - tính theo effective time của warehouse)

3. Order
- Khi tạo order, kiểm tra stt && remaining_serial && effective time của warehouse. 
+ wh_stt = active -> allow to place order
+ wh_remaining_serial >0. wh_remaining_serial = capacity - số lượng serial đã bán
+ wh_effective_from <= order_time <= wh_effective_to -> allow to place order


4. Ticket
- Ticket sẽ có các stt: owned (khi user đã mua), used (user đã dùng), expired (hết hạn - tính theo effective time của warehouse)

5. Ticket_history: 
- Mỗi sự kiện sẽ cần ghi nhận lại một dòng data ở db. VD khi ticket có stt = owned, ghi nhận 1 record. Khi user sử dụng, ghi nhận thêm 1 dòng record

----- 
**Giải thích về nghiệp vụ e-voucher**
- Voucher là hình thức giảm giá dành cho user. Voucher có thể giảm theo số tiền cố định hoặc (VD: giảm 10.000VND) theo % (VD: giảm 10% tối đa 20.000 VND). Voucher có thể tồn tại ở dạng
hard-copy (phiếu giảm giá) hoặc ở trạng thái online (voucher gắn theo account của user ở app hoặc web)
- Voucher có thể được tặng hoặc được bán. Các đơn vị lớn bán voucher/ e-voucher ở Việt Nam có thể kể đến GotIt, Urbox, VinID

=> Dự án này sẽ phát triển web bán e-voucher cho user 
Các nghiệp vụ xử lý như sau
1. Nhập kho voucher 
- Table warehouse ở db. Warehouse chịu trách nhiệm gom nhóm những voucher có cùng thuộc tính để quản lý (thuộc tính: discount_type, issuer, acquirer, 
avaialble time...) 
- Warehouse có capacity là sức chứa tối đa của warehouse. Khi nhập kho, cần kiểm tra capacity của warehouse còn bao nhiêu chỗ trống. VD: warehouse có capacity = 100 -> số lượng voucher 
tối đa có thể nhập kho là 100
2. Nghiệp vụ bán voucher - xem voucher như 1 item bán online. Khi user vào web và tìm thấy voucher tương ứng với nhu cầu -> user trả tiền để mua voucher này và sử dụng. 
- Voucher có thể dùng online hoặc offline (thể hiện ở field voucher_channel) 
- Cần phân biệt giá bán và discount amount của voucher. VD: SmartVoucher bán voucher giảm 50k tại Phúc Long với giá bán = 20k. -> discount_amount = 50K. Khi user sử dụng voucher này 
tại Phúc Long, user được giảm 50k trên
tổng hóa đơn. Tuy nhiên, để sở hữu voucher, user cần trả 20k -> 20k = giá bán 
- Khi user muốn mua voucher, cần kiểm tra số lượng ở warehouse. Nếu số lượng còn lại >0 thì user được phép mua voucher đó
3. Nghiệp vụ kiểm tra tính hợp lệ của voucher
- Voucher có thời hạn sử dụng -> khi user sử dụng voucher, cần kiểm tra expiration_time của voucher có hợp lệ không. Nếu hợp lệ thì user được phép dùng, nếu voucher đã hết hạn 
hoặc đã dùng thì không được phép dùng tiếp
4. Nghiệp vụ quản lý giao dịch mua voucher của user
- Sau khi user mua, SmartVoucher thống kê các voucher đã mua của user theo status (đã mua, đã dùng hoặc đã hết hạn)
