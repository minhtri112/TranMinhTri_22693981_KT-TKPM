# Base image
FROM node:20-alpine

# Thư mục làm việc
WORKDIR /app

# Copy package trước để cache tốt hơn
COPY package*.json ./

# Cài dependencies
RUN npm install

# Copy toàn bộ source
COPY . .

ENV HOST=0.0.0.0

# Expose port React
EXPOSE 5173

# Chạy app
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]