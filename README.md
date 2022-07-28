[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#<your-project-url>)

# Spring-Ecom

Chúng ta sẽ phát triển các microservices ecommerce đơn giản sử dụng Spring Framework với mục đích học tập.

## Models

Đây là module chứa các model dùng chung cho các microservices.

## Restlib

Đây là module chứa các thư viện, class dùng chung cho các project expose Rest APIs. Trong đó có 1 số phần quan trọng:

* GeneralExceptionHandler: Xử lý exception chung cho các Controller, return về ErrorObject khi có lỗi, diễn giải tường
  mình các lỗi.
* AutoValidateResolverConfig: Đây là tricky để tự động validate hết các params mà không cần thêm @valid.

## Inventory

Đây là module quản lý sản phẩm, quản lý giá của sản phẩm.

### Inventory's Entity

Trong inventory sẽ có các entity:

* Category: Danh mục sản phẩm (ví dụ: Điện thoại, Tivi, Phụ kiện điện thoại...)
* Product: Thông tin cơ bản của Product (metadata như Tên, danh mục, sku...)
* Price: Thông tin giá của sản phẩm. Khi cập nhật giá của sản phẩm thì sẽ thêm 1 bản ghi mới vào Price. Khi lấy giá sản
  phẩm sẽ lấy giá mới nhất.

Quan hệ giữa các entities:

* Product (n) < = > (n) Category
* Product (1) < = > (n) Price

### Inventory's Rest APIs

Trong inventory có các controller sau:

* Category controller: Expose Rest APIs để CRUD category.
* Product controller: Expose Rest APIs để CRUD product, bao gồm cả thêm hoặc bớt danh mục của 1 sản phẩm hoặc cập nhật
  giá của sản phẩm.

## Database

Để cho đơn giản, và dễ tiếp cận, dự án sẽ sử dụng H2 database, với develop (run/debug) thì sẽ dùng persistent H2 (có lưu
ra file). Với Test thì ta dùng memory db.

### Đường dẫn đến file DB

Với mỗi db sẽ có 2 file được tạo ra, đường dẫn sẽ theo cấu hình trong file application.properties. Ví dụ với Inventory ta có
* application.properties: `jdbc:h2:file:./database/inventory;AUTO_SERVER=TRUE`
* File data: `database/inventory.mv.db`
* File Lock: `database/inventory.lock.db`

### Clean/Xoá db

* Nếu muốn clean up db thì có thể dừng chương trình và xoá 2 file này là được.
* Ngoài ra bạn có thể sử dụng câu lệnh SQL để xoá (truncate)

### Connect & Query

* Với Intellij Ultimate thì có thể sử dụng tính năng data source để connect và query.
* Ngoài ra có thể sử dụng 1 công cụ free là DBeaver để connect và query.

ConnectionString để connect:

```plaintext
jdbc:h2:file:{đường_dẫn_đến_file_h2db};AUTO_SERVER=TRUE
ví dụ với inventory
jdbc:h2:file:C:/Users/Tam/Projects/Playground/java/new-ecom/database/inventory;AUTO_SERVER=TRUE
```
User và Password trong file application.properties (mặc định là rỗng)

## Build & Run

### Build & Run với Intellij Ultimate

* Mở cửa sổ service (View -> Tool windows -> services hoặc tìm ở bottom bar)
* Spring Boot -> Not started -> InventoryApplication -> Chuột phải chọn Run hoặc Debug

### Build & Run với Intellij Commodity

* Mở file *Application.java (ví dụ InventoryApplication.java)
* Chuột phải vào Icon play màu xanh trong file chọn Run hoặc Debug

### Build & Run với CLI (cmd, powershell, bash...)

Build

```bash
mvn clean install
``` 

Run Inventory

```bash
mvn -pl inventory spring-boot:run
```

## Todos

### Tuần 8

1. Phát triển các APIs cho Product Controller
    1. Hiện tại tính năng thêm mới, cật nhật sản Product hỗ trợ Product có kèm Category, cần phát triển, mở rộng tính
       năng thêm mới Product để có thể submit được sản phẩm có kèm Category.
    2. Hiện tại tính năng thêm mới, cật nhật sản Product hỗ trợ Product có kèm Price, cần phát triển, mở rộng tính năng
       thêm mới Product để có thể submit được sản phẩm có kèm Category.
        * Bảng Price không bao giờ được chỉnh sửa hay xoá, chỉ được thêm mới.
    3. Phát triển thêm tính năng tìm kiếm sản phẩm:
        * Filter theo Category
        * Filter theo tên
        * Order theo giá giảm dần/tăng dần
        * Order theo tên giảm dần/tăng dần
        * Paging
2. Một số yêu cầu khác
    1. Các APIs này phải có API Docs mô tả trong swagger, phải có validation
    2. Phải có unit test, integration test với junit
        * Xem thêm ví dụ về integration test (Test controller):
            * Module Inventory
            * [Hướng dẫn của spring](https://spring.io/guides/gs/testing-web/)
   