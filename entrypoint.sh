#!/bin/bash
set -e

# 捕获终止信号，同时关闭 Java 和 Nginx
trap 'echo "Shutting down..."; kill -TERM $JAVA_PID 2>/dev/null; wait $JAVA_PID 2>/dev/null; exit 0' TERM INT

echo "=== Starting Spring Boot backend ==="
mkdir -p /app/uploads
java -jar /app/server.jar --spring.profiles.active=prod &
JAVA_PID=$!

echo "=== Waiting for backend to be ready ==="
for i in $(seq 1 30); do
    if curl -sf http://localhost:8080/api/health > /dev/null 2>&1; then
        echo "Backend is ready"
        break
    fi
    echo "  Attempt $i/30..."
    sleep 2
done

echo "=== Starting Nginx ==="
rm -f /etc/nginx/sites-enabled/default
cp /etc/nginx/conf.d/default.conf /etc/nginx/sites-enabled/default
/usr/sbin/nginx -g "daemon off;"
