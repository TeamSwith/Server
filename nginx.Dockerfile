# Use the official NGINX base image
FROM nginx:1.18.0

# Install dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    wget \
    curl \
    build-essential \
    libpcre3-dev \
    zlib1g-dev \
    libmaxminddb-dev \
    git \
    && rm -rf /var/lib/apt/lists/*

# Download and extract NGINX source code
ARG NGINX_VERSION=1.18.0
WORKDIR /usr/src
RUN wget http://nginx.org/download/nginx-${NGINX_VERSION}.tar.gz && \
    tar -zxvf nginx-${NGINX_VERSION}.tar.gz && \
    rm nginx-${NGINX_VERSION}.tar.gz

# Clone ngx_http_geoip2_module
RUN git clone https://github.com/leev/ngx_http_geoip2_module.git

# Build NGINX with geoip2 module
WORKDIR /usr/src/nginx-${NGINX_VERSION}
RUN ./configure \
    --with-compat \
    --add-dynamic-module=../ngx_http_geoip2_module && \
    make modules

# Copy the built module to the NGINX modules directory
RUN mkdir -p /etc/nginx/modules/
RUN cp objs/ngx_http_geoip2_module.so /etc/nginx/modules/

# Clean up
RUN apt-get purge -y --auto-remove \
    wget \
    curl \
    build-essential \
    git && \
    rm -rf /usr/src/*

# Update NGINX configuration to load the module
# RUN echo 'load_module /etc/nginx/modules/ngx_http_geoip2_module.so;' > /etc/nginx/nginx.conf

# Copy default configuration files
#COPY ./nginx.conf /etc/nginx/nginx.conf

# Expose ports and run NGINX
#EXPOSE 80 443
#CMD ["nginx", "-g", "daemon off;"]


