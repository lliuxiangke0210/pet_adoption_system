#!/bin/bash
set -e

echo "=================================="
echo "  宠物领养系统 — 一键构建部署包"
echo "=================================="

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
DEPLOY_DIR="$PROJECT_DIR/deploy"

# 清理旧的构建产物
echo ""
echo "[1/4] 清理旧产物..."
rm -rf "$PROJECT_DIR/client/dist"
rm -rf "$PROJECT_DIR/client/node_modules"
rm -rf "$PROJECT_DIR/server/target"
rm -rf "$DEPLOY_DIR"

# 构建后端
echo ""
echo "[2/4] 构建后端..."
cd "$PROJECT_DIR/server"
mvn clean package -DskipTests -q
echo "  后端构建完成 → target/server-0.0.1-SNAPSHOT.jar"

# 构建前端
echo ""
echo "[3/4] 构建前端..."
cd "$PROJECT_DIR/client"
npm install --silent
npm run build
echo "  前端构建完成 → dist/"

# 整理部署目录
echo ""
echo "[4/4] 整理部署目录..."

mkdir -p "$DEPLOY_DIR/app"
mkdir -p "$DEPLOY_DIR/etc/nginx/conf.d"
mkdir -p "$DEPLOY_DIR/etc/nginx/sites-enabled"

# 拷贝前端 dist
cp -r "$PROJECT_DIR/client/dist" "$DEPLOY_DIR/app/dist"
echo "  ✓ app/dist"

# 拷贝后端 jar
cp "$PROJECT_DIR/server/target/server-0.0.1-SNAPSHOT.jar" "$DEPLOY_DIR/app/server.jar"
echo "  ✓ app/server.jar"

# 拷贝 nginx 配置（同时放到 conf.d 和 sites-enabled，兼容不同发行版）
cp "$PROJECT_DIR/nginx.conf" "$DEPLOY_DIR/etc/nginx/conf.d/default.conf"
cp "$PROJECT_DIR/nginx.conf" "$DEPLOY_DIR/etc/nginx/sites-enabled/default"
echo "  ✓ etc/nginx/conf.d/default.conf"
echo "  ✓ etc/nginx/sites-enabled/default"

# 拷贝启动脚本
cp "$PROJECT_DIR/entrypoint.sh" "$DEPLOY_DIR/entrypoint.sh"
chmod +x "$DEPLOY_DIR/entrypoint.sh"
echo "  ✓ entrypoint.sh"

echo ""
echo "=================================="
echo "  构建完成！"
echo "  部署目录：$DEPLOY_DIR"
echo ""
echo "  目录结构："
find "$DEPLOY_DIR" -type f | sort | while read f; do
    echo "    ${f#$DEPLOY_DIR/}"
done
echo ""
echo "  部署方法："
echo "  将 deploy/ 目录下所有文件上传到容器的 / 根目录"
echo "=================================="
