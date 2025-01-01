FROM nginx:latest

# Install required packages
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    libmaxminddb-dev \
    build-essential \
    git \
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

# Copy NGINX configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Start NGINX
CMD ["nginx", "-g", "daemon off;"]