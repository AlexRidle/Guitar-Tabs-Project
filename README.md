# MyProject
Application Theme: 
Web site with musical tabs to songs.

Application Description:
There is a two types of roles: guest and admin.

Unregistered user can:
- watch all public tabs from users (/tabs/all)
- get public tabs of the specific user (/tabs/user/{id})
- find all public tabs by artist, title or both.
- get specific tab by id (/tabs/{id})
- watch comments to public tabs. (/tabs/{id}/comments)

Registered user (have to active account by following the link from email) can:
- add tabs (/tabs/add) and remove them (/tabs/remove/{id})
- can see his private tabs (/tabs/private) 
- swap flags from private to public and vice versa (/tabs/makePrivate/{id} or /tabs/makePublic/{id})
- Can comment public tabs and delete self comments (/tabs/{id}/addcomment or /tabs/{id}/removecomment). 
- Have a favourites list (/favourites) (he can add every tab to this list and remove it from list in any time) and can get list of all tabs from it. 

Admin can:
- remove every comment
- watch all tabs (include private)
- remove tabs
- lock/unlock user (?)

Задание:
- Добавить аутентификацию с ролями user и admin, и изменить методы, чтобы в зависимости от роли и аккаунта можно было изменять/удалять свои табы или других пользователей.
- Добавить регистрацию пользователя 
- Добавить возможность комментирования табов (отображение, изменение, удаление). 


(какие методы и действия доступны определенной роли написаны выше)
