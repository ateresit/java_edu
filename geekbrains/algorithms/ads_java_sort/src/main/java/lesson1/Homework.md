**По вопросам проверки и переноса обращаться к Игорю tg: @inchestnov**
### 1. Прочитать в книге "Грокаем алгоритмы" или в предложенных материалах про алгоритмы и О-большое.
###2. Определить сложность следующих алгоритмов:
-. Поиск элемента массива с известным индексом

-.Дублирование одномерного массива через foreach

-. Удаление элемента массива с известным индексом без сдвига

-. Удаление элемента массива с неизвестным индексом без сдвига

-. Удаление элемента массива с неизвестным индексом со сдвига
.
###3. Определить сложность следующих алгоритмов. Сколько произойдет итераций?
a)

```
        int n = 10000;
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j *= 2) {
                arrayList.add(i * j);
            }
        }
```
b)

```
        int n = 10000;
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < n; i += 2) {
            for (int j = i; j < n; j++) {
                arrayList.add(i * j);
            }
        }
```

с)

```
        int n = 10000;
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < n; j++) {
                arrayList.add(i * j);
				n--;
            }
        }
```

d)
```
		
		factorial(BigInteger.valueOf(10000))
	
    public static BigInteger factorial(BigInteger n) {
        if (n.equals(BigInteger.ONE)) {
            return n;
        }
        return n.multiply(factorial(n.subtract(BigInteger.valueOf(1))));
    }
```

e)
```
	
	fib(BigInteger.valueOf(50));

    public static BigInteger fib(BigInteger n) {
        if (n.equals(BigInteger.ONE)) {
            return BigInteger.ZERO;
        }
        if (n.equals(BigInteger.TWO)) {
            return BigInteger.ONE;
        }
        return fib(n.subtract(BigInteger.ONE)).add(fib(n.subtract(BigInteger.TWO)));
    }
```