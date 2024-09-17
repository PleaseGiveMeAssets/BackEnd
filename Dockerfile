# 1. Base image for Maven build
FROM maven:3.8.5-openjdk-17 AS build

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. pom.xml 파일을 복사하고 의존성 다운로드
COPY pom.xml .
RUN mvn dependency:go-offline

# 4. 소스 코드 복사
COPY src /app/src

# 5. 애플리케이션 빌드
RUN mvn package -DskipTests

# 6. Run stage with Tomcat
FROM tomcat:9.0-jdk17-slim

# 7. 작업 디렉토리 설정
WORKDIR /usr/local/tomcat/webapps

# 8. 빌드된 WAR 파일을 Tomcat webapps 디렉토리로 복사
COPY --from=build /app/target/your-application.war /usr/local/tomcat/webapps/your-application.war

# 9. 포트 설정 (Tomcat의 기본 포트 8080)
EXPOSE 8080

# 10. Tomcat 실행
CMD ["catalina.sh", "run"]
