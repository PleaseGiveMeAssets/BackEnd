# 1. Gradle 빌드를 위한 기본 이미지 설정 (multi-stage build)
FROM gradle:7.5-jdk17 AS build

# 2. 캐싱을 위한 종속성 단계 실행
COPY build.gradle settings.gradle /app/
WORKDIR /app

# 3. 종속성 캐시 다운로드
RUN gradle build -x test --no-daemon || return 0

# 4. 소스 코드 복사
COPY . /app

# 5. 애플리케이션 빌드
RUN gradle build --no-daemon -x test

# 6. 실행을 위한 경량 OpenJDK 이미지 사용
FROM openjdk:17-jdk-slim

# 7. 작업 디렉토리 설정
WORKDIR /app

# 8. 빌드된 JAR 또는 WAR 파일을 복사
COPY --from=build /app/build/libs/*.war /app/app.war

# 9. 컨테이너에서 사용할 포트 설정
EXPOSE 8080

# 10. WAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app/app.war"]
