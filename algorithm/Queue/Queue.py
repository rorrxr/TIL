# Queue (큐)
# FIFO (선입선출) 구조
# collections.deque 사용

from collections import deque

queue = deque()

queue.append(1)
queue.append(2)
queue.append(3)
print("현재 큐:", queue)

print("popleft:", queue.popleft())
print("popleft:", queue.popleft())
print("남은 큐:", queue)
