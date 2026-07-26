#!/bin/bash
set -e

echo "=============================="
echo " Tarot AI - 一键构建"
echo "=============================="

# 1. 后端 Maven 打包
echo ""
echo "[1/3] 后端 Maven 打包..."
cd tarot-api
mvn clean package -DskipTests -q
echo "      -> tarot-api-app.jar 构建完成"
cd ..

# 2. Docker 镜像构建
echo ""
echo "[2/3] Docker 镜像构建..."
docker compose build --no-cache
echo "      -> 镜像构建完成"

# 3. 提示启动
echo ""
echo "[3/3] 构建完成!"
echo ""
echo "启动命令:"
echo "  docker compose --env-file .env.production up -d"
echo ""
echo "查看日志:"
echo "  docker compose logs -f"
echo ""
echo "停止服务:"
echo "  docker compose down"
echo ""
