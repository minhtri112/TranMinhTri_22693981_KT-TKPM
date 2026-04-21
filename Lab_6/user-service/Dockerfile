# ==========================================
# STAGE 1: BUILD ỨNG DỤNG
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copy file cấu hình Maven trước
COPY pom.xml .

# 2. Tải toàn bộ thư viện về trước (Bước này sẽ được Docker cache lại)
# Lần sau nếu bạn chỉ sửa code (không sửa pom.xml) thì bước này sẽ chạy trong 1 giây
RUN mvn dependency:go-offline -B

# 3. Copy toàn bộ source code vào
COPY src ./src

# 4. Build ra file .jar (bỏ qua bước chạy Test để build nhanh hơn)
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: CHẠY ỨNG DỤNG
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 1. Chỉ copy đúng file .jar đã build thành công từ Stage 1 sang Stage 2
# Dùng bản alpine giúp dung lượng image siêu nhẹ (chỉ khoảng hơn 100MB thay vì gần 1GB)
COPY --from=build /app/target/*.jar app.jar

# 2. Mở port 8080
EXPOSE 8080

# 3. Lệnh chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]