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




