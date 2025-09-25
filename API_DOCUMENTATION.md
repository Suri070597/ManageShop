# Shop Management Web Service - API Documentation

## Overview
This is a complete Spring Boot REST API for a shop management system with JWT authentication, built with Java 21, Spring Boot 3.5.6, and H2 database.

## Features Implemented

### 1. Authentication & Authorization
- JWT-based authentication
- Role-based access control (ADMIN, USER)
- Password encryption with BCrypt

### 2. User Management
- User registration and login
- Admin can view all users and change user status

### 3. Category Management
- CRUD operations for product categories
- Admin-only access for modifications

### 4. Product Management
- CRUD operations for products
- Products linked to categories
- Admin-only access for modifications

### 5. Shopping Cart
- Add products to cart
- Update product quantities
- Remove products from cart
- Clear entire cart

### 6. Order Management
- Checkout process (create order from cart)
- View order history
- Update order status (admin only)
- View order details

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login (returns JWT token)

### User Management
- `GET /api/users` - Get all users (Admin only)
- `PUT /api/users/{id}/status` - Change user status (Admin only)

### Category Management
- `GET /api/categories` - Get all categories (Public)
- `POST /api/categories` - Create category (Admin only)
- `PUT /api/categories/{id}` - Update category (Admin only)
- `PUT /api/categories/{id}/status` - Change category status (Admin only)

### Product Management
- `GET /api/products` - Get all products (Public)
- `POST /api/products` - Create product (Admin only)
- `PUT /api/products/{id}` - Update product (Admin only)
- `PUT /api/products/{id}/status` - Change product status (Admin only)

### Shopping Cart
- `GET /api/cart` - Get user's cart (Authenticated)
- `POST /api/cart` - Add product to cart (Authenticated)
- `PUT /api/cart/products/{productId}?quantity={số lượng}` - Update quantity (Authenticated)
- `DELETE /api/cart/products/{productId}` - Remove product (Authenticated)
- `DELETE /api/cart` - Clear cart (Authenticated)

### Order Management
- `GET /api/orders` - Get user's orders (Authenticated)
- `POST /api/orders` - Create new order (Authenticated)
- `POST /api/orders/checkout` - Checkout cart to create order (Authenticated)
- `PUT /api/orders/{orderId}/status` - Update order status (Admin only)
- `GET /api/orders/{orderId}/details` - Get order details (Authenticated)

## Database Schema

The application uses H2 in-memory database with the following entities:
- **users**: User information with roles
- **roles**: User roles (ADMIN, USER)
- **category**: Product categories
- **product**: Products linked to categories
- **shopping_cart**: User shopping cart items
- **orders**: Customer orders
- **order_detail**: Order line items

## Sample Data

The application includes sample data:

### Users:
- **Admin**: admin@shop.com / admin123 (ROLE_ADMIN)
- **User**: user@shop.com / user123 (ROLE_USER)

### Categories:
- Electronics
- Clothing
- Books

### Products:
- Gaming Laptop ($999.99)
- Smartphone ($699.99)
- Cotton T-Shirt ($19.99)
- Blue Jeans ($49.99)
- Programming Book ($39.99)

## Configuration

### Database
- **Development**: H2 in-memory database
- **Production**: Configurable to MySQL (commented in application.properties)

### Security
- JWT secret key for token signing
- Token expiration: 24 hours
- CORS enabled for all origins

## How to Run
1. Application starts on `http://localhost:8080`

## Testing the API

### 1. Register a new user:
```bash
POST /api/auth/register
{
  "fullName": "Test User",
  "email": "test@example.com", 
  "password": "password123",
  "address": "123 Test St",
  "phone": "1234567890"
}
```

### 2. Login to get JWT token:
```bash
POST /api/auth/login
{
  "email": "test@example.com",
  "password": "password123"
}
```

### 3. Use the token in Authorization header:
```bash
Authorization: Bearer <your-jwt-token>
```
### Task 3
Thực hiện viết API các bộ lọc tìm kiếm phân trang, sắp xếp sản phẩm 

# Product Search API

API hỗ trợ tìm kiếm, lọc, phân trang và sắp xếp sản phẩm.  
Base URL: `http://localhost:8080/api/products/search`

## Tham số query hỗ trợ
- `name` : Tìm theo tên (chứa từ khóa, không phân biệt hoa thường)
- `categoryId` : Lọc theo ID danh mục
- `minPrice` / `maxPrice` : Lọc theo khoảng giá
- `status` : Lọc theo trạng thái sản phẩm (`true` = đang hoạt động, `false` = ngừng bán)
- `page` : Số trang (bắt đầu từ 0)
- `size` : Số sản phẩm mỗi trang
- `sort` : Sắp xếp theo trường và thứ tự (`asc` hoặc `desc`), ví dụ: `sort=price,desc`

## Ví dụ gọi API

| Mục đích | URL mẫu | Ghi chú |
|----------|---------|---------|
| Lấy tất cả sản phẩm (không filter) | `GET /api/products/search?page=0&size=10` | Mặc định sort theo `id ASC` |
| Tìm theo tên chứa “phone” | `GET /api/products/search?name=phone&page=0&size=5` | Chỉ filter theo tên |
| Tìm theo tên + category | `GET /api/products/search?name=phone&categoryId=1&page=0&size=5` | Lọc theo tên & loại |
| Tìm theo khoảng giá | `GET /api/products/search?minPrice=100&maxPrice=1000&page=0&size=5` | Giá từ 100 đến 1000 |
| Tìm theo status = true | `GET /api/products/search?status=true&page=0&size=5` | Chỉ lấy sản phẩm đang active |
| Kết hợp nhiều điều kiện | `GET /api/products/search?name=phone&categoryId=1&minPrice=100&maxPrice=1000&status=true&page=0&size=5` | Lọc theo nhiều tham số |
| Sắp xếp theo giá giảm dần | `GET /api/products/search?sort=price,desc&page=0&size=5` | Sort theo `price` giảm dần |
| Sắp xếp theo tên tăng dần | `GET /api/products/search?sort=name,asc&page=0&size=5` | Sort theo `name` tăng dần |
| Trang kế tiếp nếu có nhiều sản phẩm | `GET /api/products/search?name=phone&page=1&size=5&sort=price,desc` | Ví dụ sang page 1 |

## Kết quả trả về mẫu (Page)
```json
{
  "content": [
    {
      "id": 2,
      "productName": "Smartphone",
      "price": 699.99,
      "stock": 20,
      "categoryId": 1,
      "categoryName": "Electronics",
      "status": true
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true
}

