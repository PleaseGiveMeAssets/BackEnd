# 1. Gradle 빌드를 위한 기본 이미지 설정 (multi-stage build)
FROM gradle:8.0-jdk17 AS build

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 캐시를 위해 build.gradle 및 settings.gradle 복사
COPY build.gradle settings.gradle ./

# 4. Gradle 캐시 다운로드
RUN gradle build -x test --no-daemon || return 0

# 5. 소스 코드 복사
COPY . .

# 6. 애플리케이션 빌드
RUN gradle build --no-daemon -x test

# 7. 실행을 위한 경량 OpenJDK 이미지 사용
FROM openjdk:17-jdk-slim

# 8. 작업 디렉토리 설정
WORKDIR /app

# 9. 빌드된 JAR 또는 WAR 파일을 복사
COPY --from=build /app/build/libs/*.war /app/app.war

# 10. 컨테이너에서 사용할 포트 설정
EXPOSE 8080

# 11. WAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app/app.war"]
