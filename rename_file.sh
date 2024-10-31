#!/bin/bash

TARGET_DIR="."

find "$TARGET_DIR" -type f \( -name "*.java" -o -name "*.xml" \) | while read -r FILE; do
  NEW_FILE=$(echo "$FILE" | sed 's/User/Member/g; s/USER/MEMBER/g; s/user/member/g')
  if [ "$FILE" != "$NEW_FILE" ]; then
    mv "$FILE" "$NEW_FILE"
    echo "파일명 변경 완료: $FILE -> $NEW_FILE"
  fi
done

echo "현재 디렉토리와 모든 하위 디렉토리의 파일명 수정"
