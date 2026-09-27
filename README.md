# BÀI THỰC HÀNH 2 - PHÁT TRIỂN ỨNG DỤNG WEB VỚI JAVA SPRING BOOT

Ứng dụng web bán hàng trực tuyến (**saleappv1**) được xây dựng bằng **Java 17**, **Spring Boot 3**, **Thymeleaf**, **HTML5/CSS3** và quản lý dữ liệu sản phẩm qua **JSON** với **Jackson Databind**.

---

## 📌 Bảng phân tích và chức năng đã thực hiện

| Yêu cầu | Mô tả | Trạng thái | Đường dẫn truy cập |
| :--- | :--- | :---: | :--- |
| **Yêu cầu 1** | Tạo project Spring Boot chuẩn cấu trúc `saleappv1`, sử dụng Java 17+, Maven, Thymeleaf, Spring Web | ✅ Hoàn thành | - |
| **Yêu cầu 2** | Trang chủ `index.html` hiển thị tiêu đề "BÁN HÀNG TRỰC TUYẾN" và các liên kết Tất cả, Điện thoại di động, Máy tính bảng | ✅ Hoàn thành | `http://localhost:8080/` |
| **Yêu cầu 3** | Đọc dữ liệu JSON (`products.json`, `categories.json`) bằng Jackson, hiển thị danh sách tất cả sản phẩm | ✅ Hoàn thành | `http://localhost:8080/products` |
| **Yêu cầu 4** | Xem chi tiết từng sản phẩm theo `id` (ảnh, tên, mô tả, giá, danh mục sản phẩm) | ✅ Hoàn thành | `http://localhost:8080/products/{productId}`<br>Ví dụ: `/products/1` |
| **Yêu cầu 5** | Tìm kiếm theo từ khóa tên sản phẩm, lọc theo danh mục, lọc theo khoảng giá `fromPrice` và `toPrice` | ✅ Hoàn thành | `/products?categoryId=1`<br>`/products?keyword=iphone`<br>`/products?fromPrice=5000000&toPrice=20000000` |
| **Yêu cầu 6** | Quản lý mã nguồn bằng Git/GitHub, cấu hình `.gitignore`, đẩy toàn bộ mã nguồn lên repository | ✅ Hoàn thành | `https://github.com/galassssss/b-n-h-ng-tr-c-tuy-n-.git` |

---

## 📂 Cấu trúc thư mục dự án

```text
saleappv1
├── .gitignore
├── pom.xml
├── README.md
└── src
    └── main
        ├── java
        │   └── com
        │       └── example
        │           └── saleappv1
        │               ├── Saleappv1Application.java
        │               ├── controller
        │               │   ├── HomeController.java
        │               │   └── ProductController.java
        │               ├── model
        │               │   ├── Category.java
        │               │   └── Product.java
        │               └── service
        │                   └── ProductService.java
        └── resources
            ├── application.properties
            ├── data
            │   ├── categories.json
            │   └── products.json
            ├── static
            │   ├── css
            │   │   └── style.css
            │   └── js
            │       └── main.js
            └── templates
                ├── index.html
                ├── product-detail.html
                └── products.html
```

---

## 🚀 Hướng dẫn cài đặt và chạy ứng dụng

### 1. Yêu cầu môi trường
- **Java Development Kit (JDK)**: Phiên bản 17 trở lên.
- **Apache Maven**: Phiên bản 3.8 trở lên (hoặc sử dụng tiện ích mở rộng Maven trong VS Code / IntelliJ IDEA).

### 2. Chạy ứng dụng bằng dòng lệnh (Terminal / Command Prompt)

Tại thư mục gốc của project (`saleappv1`):

```bash
# Build và chạy ứng dụng
mvn spring-boot:run
```

Sau khi ứng dụng khởi động thành công, mở trình duyệt và truy cập:
👉 **[http://localhost:8080](http://localhost:8080)**

### 3. Mở và chạy trong Visual Studio Code (VS Code)
1. Mở thư mục `saleappv1` trong VS Code (`File > Open Folder...`).
2. Cài đặt extension: **Extension Pack for Java** và **Spring Boot Extension Pack**.
3. Mở file `Saleappv1Application.java` và nhấn nút **Run** (hoặc phím tắt `F5`).

---

## 🌐 Các URL kiểm thử chức năng (Test Cases)

1. **Trang chủ**:
   - `http://localhost:8080/`
2. **Xem toàn bộ danh sách sản phẩm**:
   - `http://localhost:8080/products`
3. **Xem chi tiết sản phẩm cụ thể**:
   - `http://localhost:8080/products/1` (iPhone 15 Pro Max)
   - `http://localhost:8080/products/5` (iPad Pro M2)
4. **Lọc theo danh mục**:
   - `http://localhost:8080/products?categoryId=1` (Điện thoại di động)
   - `http://localhost:8080/products?categoryId=2` (Máy tính bảng)
5. **Tìm kiếm theo tên sản phẩm**:
   - `http://localhost:8080/products?keyword=iphone`
   - `http://localhost:8080/products?keyword=ipad`
6. **Lọc theo khoảng giá**:
   - `http://localhost:8080/products?fromPrice=5000000&toPrice=20000000`
   - `http://localhost:8080/products?fromPrice=20000000`
7. **Kết hợp đồng thời nhiều tiêu chí lọc**:
   - `http://localhost:8080/products?categoryId=1&keyword=iphone&fromPrice=10000000&toPrice=30000000`
