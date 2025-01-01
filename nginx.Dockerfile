
# nginx.Dockerfile
FROM nginx:1.22.1

# GeoIP2 모듈 및 데이터베이스 설치
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        libnginx-mod-http-geoip2=1.22.1-9 \
        geoipupdate && \
    rm -rf /var/lib/apt/lists/*


EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]
