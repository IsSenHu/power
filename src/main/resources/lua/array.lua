local array = {"Lua", "Tutorial"}

for i = 0, 2 do
    print(array[i])
end

local array2 = {}

for i = - 2, 2 do
    array2[i] = i * 2    
end

for i = -2, 2 do
    print(array2[i])
end

for key, value in pairs(array) do
    print(key, value)
end

for key, value in pairs(array2) do
    print(key, value)
end

-- 无状态的迭代器
local function square(iteratorMaxCount, currentNumber)
    if currentNumber < iteratorMaxCount 
    then
        currentNumber = currentNumber + 1
        return currentNumber, currentNumber * currentNumber    
    end
end

for key, value in square, 3, 0 do
    print(key, value)    
end

-- 迭代器实现
local function iter(a, i)
    i = i + 1
    local v = a[i]
    if v then
        return i, v
    end
end

local function ipairs(a)
    return iter, a, 0
end

for key, value in ipairs(array) do
    print(key, value)
end