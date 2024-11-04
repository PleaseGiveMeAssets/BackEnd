#!/bin/bash

TARGET_DIR="."

find "$TARGET_DIR" -type f \( -name "*.java" -o -name "*.xml" \) | while read -r FILE; do
  sed -i 's/User/Member/g' "$FILE"
  sed -i 's/USER/MEMBER/g' "$FILE"
  sed -i 's/user/member/g' "$FILE"
  echo "파일 업데이트 완료: $FILE"
done

echo "현재 디렉토리와 모든 하위 디렉토리의 .java와 .xml 파일에서 'user'를 포함한 모든 단어를 'member'로 변경했습니다."
