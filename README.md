# Чечуров Владимир БПИ227, КПО ДЗ-2 "Система управления заказами в ресторане"

## О работе

Данное приложение реализует консольное взаимодействие с пользователем, был реализован функционал на 10 баллов, программа написана с применением принципов ООП и SOLID, для приготовления блюд использовалось многопоточное программирование. Использовались шаблоны проектирования. Для хранения данных и удобства чтения данные об аккаунтах, счетах, блюдах и заказах хранятся соответственно в `accounts.json`, `bills.json`, `dishes.json`, `orders.json` находящихся в папке `res` проекта.

## Туториал

При запуске будет выведено следующее меню:

```
Select the option:
1 - Sign in
2 - Create account
3 - Exit program
```

При неудачной загрузки информации из файлов предупреждение появится здесь же, неудачным оно считается при невозможности доступа к файлам, или если файлы пустые.

Процесс регистрации выглядит следующим образом:

```
Enter login:
5
Enter password:
5
Specify type [ADMIN / CLIENT]:
admin
```

Если входные данные корректны (логин и пароль не пустые, нет пользователя с указанным логином, указанный тип аккаунта существует), то открывается диалоговое окно для администратора или клиента, в зависимости от типа указанного аккаунта.

Процесс входа в аккаунт выглядит так:

 ```
Enter login:
1
Enter password:
1
 ```

Если существует пользователь с указанными логином и паролем, то открывается диалоговое окно соответствующее типу его аккаунта.

### Диалоговое окно администратора

При входе за администратора отображается следующее меню:

```
Select the option:
1 - Edit menu
2 - Get statistics
3 - Print menu
4 - Exit to authorization menu
```

Данные пункту соответствуют возможностям:

1. Открыть меню по редактированию меню ресторана
2. Открыть меню по получению статистики ресторана
3. Вывести текущее меню
4. Выйти в меню авторизации

#### Меню по редактированию меню ресторана

```
Select the edit menu option:
1 - Add new dish
2 - Delete dish
3 - Edit price of the dish
4 - Edit time of the dish
5 - Edit name of the dish
6 - Edit the amount of the dish
7 - Edit the dish properties
8 - Cancel
```

1 Добавление нового блюда:

```
Enter the dish name:
dish1
Enter the dish price:
12
Enter the dish time complexity in seconds:
600
Enter the dish amount:
50
The dish was created!
```

2 Удаление блюда:

```
Enter the dish name:
dish1
The dish was deleted
```

3 Редактирование цены блюда:

```
Enter the dish name:
dish1
Enter new price:
1000
The price was changed
```

4 Редактирование временной сложности блюда

```
Enter the dish name:
dish1
Enter new time complexity in seconds:
12
The time complexity was changed
```

5  Редактирование имени блюда

```
Enter the dish name:
2
Enter new name:
dish2
The price was changed
```

6 Изменить доступное количество блюда

```
Enter the dish name:
dish1
Enter new amount:
10
The amount was changed
```

7 Изменить характеристики блюда (цены, временной сложности, количества)

```
Enter the dish name:
dish1
Enter the dish price:
10
Enter the dish time complexity in seconds:
1000
Enter the dish amount:
100
The price was changed
```

8 Выйти в главное меню администратора

Если в процессе будет некорректный ввод (например, введена строка с буквами, когда требуется только число) или будет обращение к несуществующему блюду, то будет выведено сообщение об ошибки и пользователь будет переведен в главное меню администратора.

#### Меню по получению статистики о ресторане

```
Select the statistic menu option:
1 - Get the best dish
2 - Get most popular dish
3 - Get the dish reviews
4 - Get all dish scores
5 - Get total number of orders
6 - Get number of orders in selected period
7 - Get total money received
8 - Cancel
```

1. Вывести лучшее блюдо (просчитывается исходя из отзывов, если отзывов нет, то выведется сообщение об отсутствии отзывов)
2. Вывести самое популярное блюдо (просчитывается исходя из сохраненных заказов, если таких нет, то выводится сообщение о отсутствии заказов)
3. Получить отзывы о блюде (необходимо будет ввести название)
4. Получить средние оценки каждого блюда (если оценок у блюда нет, будет стоять "-")
5. Вывести общее число заказов за все время (выводятся те, которые были приготовлены)
6. Вывести число заказов, которые были созданы в указанный период (выводятся те, которые были приготовлены)
7. Получить общую выручку ресторана (рассчитывается исходя из сохраненных счетов, а счет создается только для приготовленного заказа, если ни один заказ не был приготовлен, то выведется 0)
8. Выйти в главное меню администратора

### Диалоговое окно посетителя

При входе за посетителя отображается следующее меню:

```
Select the option:
1 - View menu
2 - Make an order
3 - Add dish to the last order
4 - View all previous orders (completed orders)
5 - View the not ready order
6 - Cancel last order
7 - Pay for the last order
8 - Leave a review for a dish
9 - Exit to authorization menu
```

