FROM nginx:latest

# 필수 패키지 설치
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    libmaxminddb-dev \
    build-essential \
    gcc \
    make \
    zlib1g-dev \
    libpcre3 libpcre3-dev \
    openssl libssl-dev \
    && apt-get clean

# Build GeoIP2 dynamic module
WORKDIR /usr/src
RUN wget http://nginx.org/download/nginx-1.25.0.tar.gz && \
    tar -zxvf nginx-1.25.0.tar.gz && \
    cd nginx-1.25.0 && \
    git clone https://github.com/leev/ngx_http_geoip2_module.git && \
    ./configure --with-compat --add-dynamic-module=ngx_http_geoip2_module && \
    make modules && \
    cp objs/ngx_http_geoip2_module.so /etc/nginx/modules/


# Start NGINX
CMD ["nginx", "-g", "daemon off;"]