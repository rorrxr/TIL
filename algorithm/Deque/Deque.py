# Deque (덱)
# 양쪽 끝에서 삽입과 삭제가 가능한 자료구조

from collections import deque

dq = deque()

dq.append(1)          # 뒤에 삽입
dq.appendleft(2)      # 앞에 삽입
dq.append(3)

print("현재 덱:", dq)

print("popleft:", dq.popleft())   # 앞에서 제거
print("pop:", dq.pop())           # 뒤에서 제거
print("남은 덱:", dq)
