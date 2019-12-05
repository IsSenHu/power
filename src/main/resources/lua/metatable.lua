local myTable = setmetatable({}, {})
print(getmetatable(myTable))

-- __index 元方法
local other = { foo = 3 }
-- 当你通过键来访问 table 的时候，如果这个键没有值，
-- 那么Lua就会寻找该table的metatable（假定有metatable）中的__index 键。
-- 如果__index包含一个表格，Lua会在表格中查找相应的键
local t = setmetatable({}, { __index = other })
print(t.foo)
print(t.bar)
-- 如果__index包含一个函数的话，Lua就会调用那个函数，table和键会作为参数传递给函数。
-- __index 元方法查看表中元素是否存在，如果不存在，返回结果为 nil；如果存在则由 __index 返回结果。
myTable = setmetatable({ key1 = "value1" }, { __index = function (myTable, key)
    if key == "key2" 
    then
        return "metatablevalue"
    else
        return nil
    end
end })

print(myTable.key1, myTable.key2)

-- __newindex 元方法
-- __newindex 元方法用来对表更新，__index则用来对表访问
-- 当你给表的一个缺少的索引赋值，解释器就会查找 __newindex 元方法：如果存在则调用这个函数而不进行赋值操作
local mymetatable = {}
local mytable = setmetatable({key1 = "value1"}, { __newindex = mymetatable })

-- 打印 value1
print(mytable.key1)

mytable.newkey = "新值2"
-- 打印 nil 新值2 因为 newkey 不存在会调用元方法，不赋值
print(mytable.newkey, mymetatable.newkey)

mytable.key1 = "新值1"
-- 打印 新值1 nil 因为 key1 存在会赋值，不会调用元方法
print(mytable.key1, mymetatable.key1)

-- 使用 rawset 函数来更新表
mytable = setmetatable({key1 = "value1"}, {__newindex = function (mytable, key, value)
    rawset(mytable, key, "\"" .. value .. "\"")
end})

mytable.key1 = "new value"
mytable.key2 = 4
-- new value    "4"
print(mytable.key1, mytable.key2)

-- 为表添加操作符

-- 计算表中最大值，table.maxn在Lua5.2以上版本中已无法使用
-- 自定义计算表中最大键值函数 table_maxn，即计算表的元素个数
local function table_maxn(t)
    local mn = 0
    for key, value in pairs(t) do
        if mn < key then
            mn = key
        end
    end
    return mn
end

-- 两表相加操作
mytable = setmetatable({ 1, 2, 3 }, {
    __add = function (mytable, newtable)
        for i = 1, table_maxn(newtable) do
            table.insert(mytable, table_maxn(mytable) + 1, newtable[i])
        end
        return mytable
    end
})

local secondtable = { 4, 5, 6 }
mytable = mytable + secondtable
for key, value in pairs(mytable) do
    print(key, value)
end

-- __call 元方法
mytable = setmetatable({ 10 }, {
    __call = function (mytable, newtable)
        local sum = 0
        for i = 1, table_maxn(mytable) do
            sum = sum + mytable[i]
        end
        for i = 1, table_maxn(newtable) do
            sum = sum + newtable[i]
        end
        return sum
    end
})

local newtable = { 10, 20, 30 }
print(mytable(newtable))

-- __tostring 方法
mytable = setmetatable({ 10, 20, 30 }, {
    __tostring = function (mytable)
        local sum = 0
        for key, value in pairs(mytable) do
            sum = sum + value
        end
        return "表所有元素的和为 " .. sum
    end
})

print(mytable)