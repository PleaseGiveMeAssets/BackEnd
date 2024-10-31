#!/bin/bash

TARGET_DIR="."

find "$TARGET_DIR" -type f \( -name "*.java" -o -name "*.xml" \) | while read -r FILE; do
  sed -i 's/MemberDetails/UserDetails/g' "$FILE"
  sed -i 's/getMembername/getUsername/g' "$FILE"
  sed -i 's/memberDetails/userDetails/g' "$FILE"
  sed -i 's/memberdetails/userdetails/g' "$FILE"

  echo "파일 업데이트 완료: $FILE"
done

echo "현재 디렉토리와 모든 하위 디렉토리의 MemberDetails를 UserDetails 로 변경했습니다."
