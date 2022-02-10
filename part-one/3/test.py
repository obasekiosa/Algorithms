# def getMaxUnits(boxes, unitsPerBox, truckSize):
#     # Write your code here
#     result = 0

#     packed = []
#     for i, j in zip(boxes, unitsPerBox):
#         packed.append((i, j))

#     sorted(packed, key=lambda x: x[1])

#     for e in packed:
#         boxCount = e[0]
#         while boxCount != 0:
#             if boxCount <= truckSize:
#                 result += e[1]
#                 truckSize -= 1
#                 boxCount -= 1
#             else:
#                 break
#         if truckSize <= 0:
#             break

#     return result


# print(getMaxUnits([1,2,3], [3,2,1], 3))
# print(getMaxUnits([1], [2], 1))

# def calculateLen(s, k, b, m):
#     return ((k * s + b) % m) + 1 + s


# def variantsCount(n, s0, k, b, m, a):
#     # Write your code here
#     s = [s0]
#     for i in range(n):
#         s.append(calculateLen(s[i], k, b, m))

#     print(s)
#     areas = []
#     i = 0
#     while i < len(s):
#         j = i
#         while j < len(s):
#             areas.append(s[i] * s[j])
#             j += 1
#         i += 1

#     result = 0
#     for e in areas:
#         if e <= a:
#             result += 1

#     return result
# print(variantsCount(3, 1, 1, 1, 2, 4))



# def numberOfUnique(numbers):
#     return len(set(numbers))

# def partitionArray(k, numbers):
#     partition = []
#     i = 0
#     rep = 0
#     while len(numbers) >= k + 1:
#         if numbers[i] not in partition:
#             e = numbers.pop(i)
#             partition.append(e)
#             i += 1 
#         else:
#             i += 1
#         rep += 1
        

        
#         if len(partition) == k:
#             # print(partition, numbers)
#             partition = []
#             rep = 0
#             i = 0


#         if rep >= len(numbers):
#             break
#         # uniqueCount = numberOfUnique(numbers)
#         # if uniqueCount == 0:
#         #     return "YES"
#         # if uniqueCount < k:
#         #     return "NO"

#     #no point in code below
#     print(partition, numbers)
#     if len(numbers) % k != 0:
#         return "NO"
#     else:
#         return "YES"

# print(partitionArray(3, [4,2,2,3]))
# print(partitionArray(2, [1,2,3,4]))
# print(partitionArray(2, [4,2,2,3]))


# def checkValidity(a, b, c, t):
#     if not (a < b and b < c):
#         return 0
#     if a + b + c <= t:
#         print(a,b,c)
#         return 1
#     else:
#         return 0

# N = 0

# def search(a, d):
#     ans = binary_search(d, a, 0, N)
#     if ans == -1:
#         return -1
#     while d[ans] == d[ans + 1]:
#         print(0)
#         ans += 1
#     return ans

    
# def binary_search(arr, x,  low, high):

#     # Check base case
#     if high >= low:

#         mid = (high + low) // 2

#         # If element is present at the middle itself
#         if arr[mid] == x:
#             return mid

#         # If element is smaller than mid, then it can only
#         # be present in left subarray
#         elif arr[mid] > x:
#             return binary_search(arr, x, low, mid - 1)

#         # Else the element can only be present in right subarray
#         else:
#             return binary_search(arr, x, mid + 1, high)

#     else:
#         # Element is not present in the array
#         return -1

# def triplets(t, d):
#     i = 0
#     N = len(d)
#     count = 0
#     d = sorted(d)
#     while i < N:
#         j = i + 1
#         while j < N:
#             pos = search(t - d[i] - d[j], d)
            
#             if pos == -1:
#                 j += 1
#                 continue
#             remove = 0
#             print(pos, d, i, j)
#             if i < pos:
#                 remove -= 1
#             if j < pos:
#                 remove -= 1
#             count += pos - remove + max(0, pos - 1)
#             print("count", count)
#             j += 1
#         i += 1
    
#     return count

# print(triplets(7, [3,1,2,4]))
# print(triplets(8, [1,2,3,4,5]))
# print(search(-3, [3, 4]))


# def calculate(s):
#     a = s.split()
#     budget = int(a[0])
#     cost = int(a[1])
#     turnInNum = int(a[2])

#     count = 0

    
#     count = budget // cost
#     total = count
#     extra = 0
#     while count >= turnInNum:
#         total += (count - extra) // turnInNum
#         extra = (count % turnInNum)
#         count = (count // turnInNum) + extra
#     return total

# def maximumContainers(scenarios):
#     answers = []
#     for scene in scenarios:
#         answers.append(calculate(scene))

#     return answers


# print(maximumContainers(["10 2 5", "12 4 4", "6 2 2"]))
# print(maximumContainers(["8 4 2", "7 2 3"]))


import math

def check() :
    x = float(input("enter x: "))
    y = math.sqrt(pow(x + 6, 2) + 25) + math.sqrt(pow(x - 6, 2) + 121)
    print(y)

check()