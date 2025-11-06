# Stack (스택)
# LIFO (후입선출) 구조
# append()로 push, pop()으로 pop

stack = []

stack.append(1)
stack.append(2)
stack.append(3)
print("현재 스택:", stack)

print("pop:", stack.pop())
print("pop:", stack.pop())
print("남은 스택:", stack)
