FROM nginx:latest

# Install required packages
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    build-essential \
    gcc \
    make \
    zlib1g-dev \
    libpcre3 libpcre3-dev \
    openssl libssl-dev \
    software-properties-common \
    libmaxminddb-dev \
    mmdb-bin \
    geoipupdate \
    && apt-get clean

RUN add-apt-repository ppa:maxmind/ppa && apt-get update

WORKDIR /usr/src

# Download and compile NGINX with GeoIP2 module
RUN wget http://nginx.org/download/nginx-1.25.0.tar.gz && \
    tar -zxvf nginx-1.25.0.tar.gz && \
    cd nginx-1.25.0 && \
    git clone https://github.com/leev/ngx_http_geoip2_module.git && \
    ./configure --with-compat --add-dynamic-module=ngx_http_geoip2_module && \
    make modules && \
    cp objs/ngx_http_geoip2_module.so /usr/lib/nginx/modules/

# GeoIP2 module configuration
RUN mkdir -p /etc/nginx/modules && \
    cp -vi objs/ngx_http_geoip2_module.so /etc/nginx/modules/ && \
    mkdir -p /etc/nginx/conf.d && \  # conf.d 디렉터리 생성 추가
    echo "load_module modules/ngx_http_geoip2_module.so;" > /etc/nginx/conf.d/geoip2.conf


# Start NGINX
CMD ["nginx", "-g", "daemon off;"]
