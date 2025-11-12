# Bubble Sort (버블 정렬)
# 인접한 두 원소를 비교하여 큰 값을 뒤로 보내는 방식
# 시간 복잡도: O(N^2)

def bubble_sort(arr):
    n = len(arr)
    for i in range(n - 1):
        for j in range(n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]

arr = [5, 3, 8, 4, 2]
bubble_sort(arr)
print("정렬 결과:", arr)
