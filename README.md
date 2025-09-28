# 🛒 Web Store Demo (Angular + Spring Boot)
This is a full-stack e-commerce demo project built with Angular (frontend) and Spring Boot (backend). It showcases a complete shopping experience: browsing products, adding to cart/wishlist, placing orders, and admin product management.

⚠️ Note: This is a demo project — no real payments or transactions are processed.

## ✨ Features

👤 Authentication & Accounts

Register & login with JWT authentication

Role-based access:

ROLE_USER → shopping features

ROLE_ADMIN → manage products

Account page with order history & wishlist

🛍️ Shopping

Product list with search & pagination

Product detail page

Wishlist (local persistence)

Cart with quantity controls and checkout

Order placement (with fake demo banner)

Order history for logged-in users

💸 Sales & Discounts

Products can be marked On Sale with original + discounted price

SALE badge on product cards

On Sale page listing all discounted products

🛠️ Admin (light version)

Admin-only access to add/edit/delete products

Image upload support

Role check for secure access

🎨 UI/UX

Responsive mobile-first design (Bootstrap + SCSS)

Styled components (cart, checkout, orders, wishlist, account)

Toast notifications for cart/wishlist actions

Dismissible top demo banner

Custom theme colors ($orange, $light-blue, $light-orange)

## 🖥️ Tech Stack
Frontend

Angular 17 (standalone components)

Bootstrap 5 + SCSS for styling

RxJS BehaviorSubject for cart/wishlist state

Angular Router

Backend

Spring Boot 3

Spring Security (JWT authentication)

Hibernate / JPA with MySQL

REST API

## ⚡ Getting Started
Backend (Spring Boot)

Clone repo and open backend project.

Configure application.properties (MySQL, JWT secret).

Run the backend:

./mvnw spring-boot:run

API runs at http://localhost:8080/api

Frontend (Angular)

Clone frontend project.

Install dependencies:

npm install

Run dev server:

ng serve

App runs at http://localhost:4200

📸 Screenshots

🏠 Home page (featured products, navigation)

🛍️ Product list with pagination

🔎 Product detail

🧺 Cart & checkout

❤️ Wishlist

📦 Orders

⚙️ Admin panel

## 🚀 Roadmap / Possible Improvements
Implement full admin dashboard (categories, stock management)

Add product reviews & ratings

Email notifications (order confirmation)

Real payment integration (Stripe/PayPal)

Docker setup for easy deployment

## 📜 License
MIT License — free to use and adapt.
