# 1. Base image for Gradle build
FROM gradle:7.5-jdk17 AS build

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 소스 코드 복사
COPY . /app

# 4. 애플리케이션 빌드
RUN gradle build --no-daemon

# 5. Run stage with OpenJDK
FROM openjdk:17-jdk-slim

# 6. 작업 디렉토리 설정
WORKDIR /app

# 7. 빌드된 JAR 파일을 복사
COPY --from=build /app/build/libs/your-application.jar /app/your-application.jar

# 8. 포트 설정
EXPOSE 8080

# 9. JAR 파일 실행
CMD ["java", "-jar", "/app/your-application.jar"]
