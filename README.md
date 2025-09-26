# 📂 File Management & Storage API

A **Spring Boot backend project** that provides a file storage and management system (like Google Drive clone).
This system supports uploading, downloading, organizing files/folders, user authentication, sharing files, and admin management.

---

## ⚡ Features Implemented

✅ **User Authentication & Authorization**

* User registration & login
* Role-based access control (e.g., `USER`, `ADMIN`)

✅ **Folder Management**

* Create, update, delete folders
* Nested subfolders support
* Soft-delete (Trash) and restore

✅ **File Management**

* Upload files into folders
* Download files as attachments
* Soft-delete and restore files
* Permanent deletion of files

✅ **File Sharing**

* Share files with other users
* View list of users a file is shared with
* Revoke sharing access

✅ **Search & Metadata**

* Search files by name
* Get metadata of a specific file (name, size, type, owner, etc.)

✅ **Admin Module**

* List all users
* Delete users
* View all files in the system

---

## 🔮 Upcoming Features

🚀 **Cloud Integration (AWS S3)**

* Store files directly in **Amazon S3 bucket** instead of local disk
* Generate **pre-signed URLs** for secure uploads & downloads
* Automatic file versioning support

🔒 **Enhanced Security**

* JWT refresh tokens
* Role-based permissions for shared files

📊 **User Dashboard APIs**

* Show storage used per user
* File statistics (count, size, last accessed)

📧 **Email Notifications**

* Notify users when a file is shared with them
* Alerts for storage limits

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Security (JWT)**
* **Spring Data JPA (Hibernate)**
* **PostgreSQL** (Database)
* **ModelMapper** (Entity ↔ DTO mapping)
* **AWS SDK** (S3 integration – upcoming)

---

## 📡 API Endpoints

### 🔐 Authentication

* `POST /auth/register` → Register user
* `POST /auth/login` → Login & get JWT

### 📁 Folder Management

* `POST /folders` → Create folder
* `PATCH /folders/{id}` → Rename folder
* `DELETE /folders/{id}` → Soft delete
* `PATCH /folders/{id}/restore` → Restore

### 📂 File Management

* `POST /files/upload` → Upload file
* `GET /files/{id}/download` → Download file
* `DELETE /files/{id}` → Soft delete
* `PATCH /files/{id}/restore` → Restore
* `DELETE /files/{id}/permanent` → Permanent delete

### 🔗 File Sharing

* `POST /files/{id}/share?userId=X` → Share file with user
* `GET /files/{id}/shared` → Get shared users list
* `DELETE /files/{id}/share/{userId}` → Revoke sharing

### 🔍 Search & Metadata

* `GET /files/search?name=xyz` → Search files
* `GET /files/{id}/metadata` → Get file metadata

### 👨‍💻 Admin

* `GET /admin/users` → List all users
* `DELETE /admin/users/{id}` → Delete user
* `GET /admin/files` → List all files

---

## 🚀 Running Locally

### 1️⃣ Clone the Repo

```bash
git clone https://github.com/your-username/file-management-storage.git
cd file-management-storage
```

### 2️⃣ Configure Database (PostgreSQL)

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/file_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Run the Application

```bash
mvn spring-boot:run
```

### 4️⃣ Test APIs in Postman

* Base URL: `http://localhost:8080/api`
* Import the Postman collection (to be added)

---

## 🌩️ AWS S3 Setup (Upcoming)

* Create an **S3 bucket**
* Configure AWS credentials in `application.properties`
* Add AWS SDK integration in `FileService`
* Replace local file storage with S3 storage

---

## 👨‍💻 Author

Built with ❤️ by **Panda** (Backend Developer)
