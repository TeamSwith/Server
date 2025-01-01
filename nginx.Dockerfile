# GeoIP2 포함된 Nginx 이미지 빌드
FROM nginx:latest

# GeoIP2 모듈 및 데이터베이스 설치
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        libnginx-mod-http-geoip2 \
        geoipupdate && \
    rm -rf /var/lib/apt/lists/*

EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]
