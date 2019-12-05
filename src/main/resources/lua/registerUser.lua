-- 变量定义
local a, b
-- 初始化
a = 10
b = 30
print("value of a:", a)
print("value of b:", b)
-- 交换变量的值
b, a = a, b
print("value of a:", a)
print("value of b:", b)
local f = 70.0/3.0
print("value of f:", f)

-- type函数
print(type("What is my type"))
local t = 10
print(type(5.8 * t))
print(type(true))
print(type(print))
print(type(type))
print(type(nil))
print(type(type(ABC)))

local A = 10
local B = 20
print(A == B)
print(A ~= B)

-- .. 连接两个字符串
print("aa" .. "bb")
-- # 一元运算符，返回字符串或者表的长度
print(#"Hello")

-- 循环
while true do
    print("This loop will run forever")
end

-- i从1变化到10，每次变化以2为步长递增
for i = 1, 10, 2 do
    print(i)
end

-- repeat until循环
local i = 0
repeat
    i = i + 1
until (i == 10)
print(i)

-- break
local c = 10
while (c < 20) do
    print("c 的值为:", c)
    c = c + 1
    if (c > 15) then
        break
    end
end

-- label
local d = 1
::label:: print("--- goto label ---")

a = a + 1
if (a < 3) then
    goto label
end

-- elseif
local e = 100
if (e > 20) 
then
    print("e 大于20")
elseif (e < 20) 
then
    print("e 小于20")
else
    print("e 等于20")
end

-- function
local function max(a, b)
    local result
    if (a > b) 
    then
        result = a
    else
        result = b
    end

    return result
end

print("两值比较最大值为 ", max(10, 4))

-- 将函数作为参数传递给函数
local myPrint = function (param)
    print("这是打印函数 -   ##", param, "##")
end

local function add(a, b, functionPrint)
    local result = a + b
    functionPrint(result)
end

add(13, 7, myPrint)

-- 多返回值
local s1, s2 = string.find("www.runoob.com", "runoob")
print(s1, s2)