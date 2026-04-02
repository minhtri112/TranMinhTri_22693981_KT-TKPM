FROM redis:latest

COPY redis.conf /usr/local/etc/redis/redis.conf

# khai báo nơi lưu dữ liệu
VOLUME ["/data"]

# mở port
EXPOSE 6379

CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]