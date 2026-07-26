#!/bin/bash
set -e

echo "=============================="
echo " Tarot AI - 服务器初始化"
echo "=============================="

# 1. 更新系统
echo ""
echo "[1/4] 更新系统包..."
apt update && apt upgrade -y

# 2. 安装 Docker
echo ""
echo "[2/4] 安装 Docker..."
apt install -y ca-certificates curl gnupg
install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
chmod a+r /etc/apt/keyrings/docker.gpg

echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo $VERSION_CODENAME) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

apt update
apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# 3. 验证
echo ""
echo "[3/4] 验证安装..."
docker --version
docker compose version

# 4. 启动 Docker 并设为开机自启
echo ""
echo "[4/4] 启动 Docker 服务..."
systemctl enable docker
systemctl start docker

echo ""
echo "=============================="
echo " 初始化完成!"
echo "=============================="
echo ""
echo "下一步:"
echo "  1. 将项目代码上传到服务器"
echo "  2. 配置 .env.production"
echo "  3. 运行 bash build.sh"
echo "  4. docker compose --env-file .env.production up -d"
echo ""
