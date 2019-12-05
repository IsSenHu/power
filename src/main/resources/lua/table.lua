-- 初始化表
local mytable = {}
-- 指定值
mytable[1] = "Lua"
-- 移除引用
mytable = nil
-- lua 垃圾回收会释放内存

mytable = {}
print("mytable 的类型是 ", type(mytable))

mytable[1] = "Lua"
mytable["wow"] = "修改前"
print("mytable 索引为 1 的元素是 ", mytable[1])
print("mytable 索引未 wow 的元素是 ", mytable["wow"])

-- alternatetable和mytable的是指同一个table
local alternatetable = mytable

print("alternatetable 索引为 1 的元素是 ", alternatetable[1])
print("mytable 索引为 wow 的元素是 ", alternatetable["wow"])

alternatetable["wow"] = "修改后"

print("mytable 索引为 wow 的元素是 ", mytable["wow"])

-- 释放变量
alternatetable = nil
print("alternatetable 是 ", alternatetable)

-- mytable 仍然可以访问
print("mytable 索引为 wow 的元素是 ", mytable["wow"])

mytable = nil
print("mytable 是 ", mytable)

-- table 连接
local fruits = {"banana", "orange", "apple"}
-- 返回table连接后字符串
print("连接后的字符串", table.concat(fruits))

-- 指定连接字符串
print("连接后的字符串", table.concat(fruits, ", "))

-- 指定索引来连接table
print("连接后的字符串", table.concat(fruits, ", ", 2, 3))

-- 插入和移除
-- 在末尾插入
table.insert(fruits, "mango")
print("索引为 4 的元素为 ", fruits[4])

-- 在索引为 2 的键处插入
table.insert(fruits, 2, "grapes")
print("索引为 2 的元素为 ", fruits[2])

print("最后一个元素为 ", fruits[5])
table.remove(fruits)
print("移除后最后一个元素为 ", fruits[5])

-- table 排序
print("排序前")
for key, value in pairs(fruits) do
    print(key, value)
end

table.sort(fruits)
print("排序后")
for key, value in pairs(fruits) do
    print(key, value)
end

-- table最大值
local function table_maxn(t)
    local mn = nil
    for key, value in pairs(t) do
        if mn == nil then
            mn = value
        end
        if mn < value then
            mn = value
        end
    end
    return mn
end

local tb1 = {[1] = 2, [2] = 6, [3] = 34, [26] = 5}
print("tb1 最大值: ", table_maxn(tb1))
print("tb1 长度: ", #tb1)
-- 当我们获取table的长度的时候无论是使用#还是table.getn其都会在索引中断的地方停止计数，而导致无法正确取得table的长度