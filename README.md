# MyProject
Application Theme: 
Web site with musical tabs to songs.

Application Description:
There is a two types of roles: guest and admin. (not done yet)

Unregistered user can:
- watch all public tabs from users (/tabs/all)
- get public tabs of the specific user (/tabs/user/{id})
- find all public tabs by artist, title or both (/tabs/search).
- get specific public tab by id (/tabs/{id})
- watch comments to public tabs. (/tabs/{id}/comments) (not done yet)

Registered user can:
- add tabs (/tabs/add) and remove them (/tabs/remove/{id})
- can see his private tabs (/tabs/hidden) (like /tabs/user/{id} but returns only private with self userId) (not done yet)
- edit self tabs (like changing "hidden" flag of tab)
- Can comment public tabs and delete self comments (/tabs/{id}/addcomment or /tabs/{id}/removecomment) (not done yet)
- Have a favourites list (/tabs/favourites) (he can add every tab to this list and remove it from list in any time) and can get list of all tabs from it. 

Admin can:
- remove every comment (not done yet)
- watch all tabs (include private)
- remove and edit all tabs 
- lock/unlock user (?)

Задание:
- Добавить spring security с использованием JWT и регистрацию пользователя для входа в систему. Создать роли user и admin. Простой пользователь может добавлять/удалять/редактировать свои табы и делать их скрытыми (чтобы их мог видеть только тот пользователь, который их и опубликовал). Админ может просматривать скрытые табы других пользователей, и так же редактировать или удалять. 
- Добавить пользователям личный список избранных табов. Возможность добавления/удаления таба из списка. Написать Необходимый контроллер, сервис, конвертер, дто, репозиторий, и написать тесты для добавленных методов.

(какие действия доступны определенной роли - описано выше)
