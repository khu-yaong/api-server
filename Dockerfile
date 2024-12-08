FROM openjdk:17-jdk-slim

# 필요한 패키지 업데이트 및 설치
RUN apt-get update -y && apt-get install -y \
    wget \
    curl \
    unzip \
    fonts-liberation \
    libdrm2 \
    libgbm1 \
    libvulkan1 \
    libx11-6 \
    libxext6 \
    libxfixes3 \
    libxkbcommon0 \
    libxrandr2 \
    libgtk-3-0 \
    libnss3 \
    libxcomposite1 \
    libxdamage1 \
    libasound2 \
    libxshmfence1 \
    xdg-utils \
    --no-install-recommends && \
    rm -rf /var/lib/apt/lists/*

# Chrome 및 ChromeDriver 설치
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt-get install -y ./google-chrome-stable_current_amd64.deb && \
    rm -f google-chrome-stable_current_amd64.deb

# Chome 버전 확인
RUN google-chrome --version

# 환경변수 설정
ENV CHROME_BIN=/usr/bin/google-chrome

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]