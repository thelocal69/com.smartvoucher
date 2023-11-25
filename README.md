
# SMART VOUCHER PROJECT INTRODUCTION
Welcome to our cutting-edge e-commerce platform designed to revolutionize your shopping experience for electronic vouchers (e-vouchers). Our project focuses on providing users with a seamless platform to discover, purchase, and utilize e-vouchers efficiently. Once a user buys a voucher, they gain the flexibility to redeem it offline, coupled with a comprehensive overview of their voucher activity upon logging into the website. This project is collaboratively developed by four talented backend engineers 
- Trần Khánh Thiện
- Võ Đình Nhân
- Hà Mai Phụng
- Trần Minh Tâm

The technology stack driving our platform is a testament to our commitment to delivering a robust and user-friendly solution:
- Redis cache is employed to expedite query processing, ensuring swift responses to user interactions. 
- Leveraging Google API, users and administrators can effortlessly upload and store images associated with warehouses, categories, or user avatars. 
- The integration of Lombok streamlines code development, while JJWT bolsters our security measures, implementing refresh tokens to enhance user authentication.

Key features of our project include: 
- The utilization of Google API for image management, intelligent logic for generating serials and tickets based on user purchase requests, and meticulous checks for warehouse effectiveness, status, and capacity before processing orders. 
- Additional functionalities include email verification upon user signup, real-time verification of voucher validity during redemption, automatic status updates upon voucher expiration
- The seamless implementation of refresh tokens to optimize user sessions. 

This project epitomizes innovation and efficiency in the realm of e-commerce, ensuring a delightful experience for users navigating the diverse landscape of electronic vouchers.

# OVERVIEW SMARTVOUCHER.COM PAGE
## HOME PAGE
<img src="https://drive.google.com/uc?export=view&id=1XZpwCO7NtXihiEJ8WormyfVEyHFoHIxg" width="800" height="400" title="hover text">

## CONTENT BODY
<img src="https://drive.google.com/uc?export=view&id=1558mZ3hfAO3X_1sb7y5hCQoWWHEMk9SC" width="800" height="400" title="hover text">

## SIGIN PAGE
<img src="https://drive.google.com/uc?export=view&id=1fO27MirzMg2fsiRwu3RoepnYdt5jv2q9" width="800" height="400" title="hover text">

### AND MORE... !!!


# PROJECT DISCLAIMER
 Project Disclaimer: Given time constraints, certain aspects of e-voucher transactions are simulated. Please refer to below information:
- The payment flow is bypassed, assuming users have already paid when clicking "buy now." 
- We exclusively address real-time creation of serials and tickets concurrently. The typical operation allows creating serials first, then generating tickets upon user voucher purchase. 
- For simplicity, all vouchers are considered usable immediately upon clicking "use voucher," bypassing the display of barcodes or serial codes for merchant use. 
- Information showcased on the front end is purely for demonstration purposes and is unrelated to actual trading activities
- The project focuses on managing fundamental e-voucher operations


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
