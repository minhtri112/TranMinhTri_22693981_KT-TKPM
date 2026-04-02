# ===== Stage 1: Build =====
FROM node:18 AS builder

WORKDIR /app

# Copy package trước để cache tốt hơn
COPY package*.json ./

# Cài dependencies
RUN npm install

# Copy toàn bộ source code
COPY . .



# ===== Stage 2: Run =====
FROM node:18-alpine

WORKDIR /app

# Chỉ copy những gì cần thiết từ stage build
COPY --from=builder /app ./

# Mở port (tuỳ app, thường là 3000)
EXPOSE 5000

# Chạy app
CMD ["npm", "start"]