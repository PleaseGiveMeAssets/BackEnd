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

# 7. Tomcat 이미지를 사용하여 서블릿 컨테이너 설정
FROM tomcat:9.0-jdk17

# 8. WAR 파일을 Tomcat의 webapps 폴더에 ROOT.war로 복사
COPY --from=build /app/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

# 9. Tomcat이 기본적으로 사용하는 포트를 노출
EXPOSE 8080

# 10. Tomcat 서버를 실행
CMD ["catalina.sh", "run"]
